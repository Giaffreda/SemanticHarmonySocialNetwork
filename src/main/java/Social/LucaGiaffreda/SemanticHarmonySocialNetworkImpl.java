package Social.LucaGiaffreda;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;


import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.FuturePut;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.BaseFutureAdapter;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.futures.FutureDirect;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.rpc.ObjectDataReply;
import net.tomp2p.storage.Data;

public class SemanticHarmonySocialNetworkImpl implements SemanticHarmonySocialNetwork {
		private Peer peer;
	 private PeerDHT _dht;
	 final private int DEFAULT_MASTER_PORT=4000;
	 public int peerId;
	 private String adress;
	private ArrayList<Object[]> friendList;

	 public SemanticHarmonySocialNetworkImpl(int id, String adress, MessageListener listner) throws Exception {
		 peerId=id;
		
		 this.adress=adress;
		 friendList=new ArrayList<Object[]>();
		
		 peer= new PeerBuilder(Number160.createHash(peerId)).ports(DEFAULT_MASTER_PORT+peerId).start();
		 	_dht = new PeerBuilderDHT(peer).start();
		 	FutureBootstrap fb = peer.bootstrap().inetAddress(InetAddress.getByName(adress)).ports(DEFAULT_MASTER_PORT).start();
	 		fb.awaitUninterruptibly();
	 		if(fb.isSuccess()) {
	 			peer.discover().peerAddress(fb.bootstrapTo().iterator().next()).start().awaitUninterruptibly();
	 		}else {
	 			fb.failedReason();
	 			throw new Exception("Error in master peer bootstrap." + fb.failedReason());
	 		}
	 		peer.objectDataReply(new ObjectDataReply() {
	 			
	 			public Object reply(PeerAddress sender, Object request) throws Exception {
	 				return listner.parseMessage(sender,request);
	 			}
	 		});
	}
	 
	@Override
	public List<String> getUserProfileQuestions() {
		  FutureGet futureGet = _dht.get(Number160.createHash("question")).start();
		  /*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
			 @Override
			 public void operationComplete(FutureGet future) throws Exception {
			
					//System.out.println("complete" + profile);
			 }
			}).awaitListenersUninterruptibly();*/
		 
		futureGet.awaitUninterruptibly();
	        Message test;
	        try {
		        if (futureGet.isSuccess()) {
		        	if(!futureGet.isEmpty()) {
		        		ArrayList<String> questions= (ArrayList<String>) futureGet.dataMap().values().iterator().next().object();
		        		return questions;
		        	}else {
		        		return createQuestion();
		        	}
		        }
		        	} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	return null;
		        }
	 public List<String> createQuestion() {
		  FutureGet futureGet = _dht.get(Number160.createHash("question")).start();
	        futureGet.awaitUninterruptibly();
	        Message test;
	      
		        if (futureGet.isSuccess()) {
		        	if(futureGet.isEmpty()) {
		        		ArrayList<String> questions=new ArrayList<String>();
		        		questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Sono sempre preparato");
		       		 questions.add("Mi sento a mio agio con le persone");
		       		 questions.add("Evito di assumermi molte responsabilità");
		       		questions.add("Faccio amicizia facilmente");
		       		questions.add("Mi interessa il significato delle cose");
		       		questions.add("Ho una vivida immaginazione");
		       		
		       		
		        		 try {
							FuturePut fp = _dht.put(Number160.createHash("question")).data(new Data(questions))
							         .start().awaitUninterruptibly();
							if(fp.isSuccess()) {
								return questions;
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	}else {
		        		ArrayList<String> questions;
						try {
							questions = (ArrayList<String>) futureGet.dataMap().values().iterator().next().object();
							return questions;
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		
		        	}
		        }
		        return null;
	  }
	

	@Override
	public String createAuserProfileKey(List<Integer> _answer) {
		String key="";
		for (int i=0;i<_answer.size();i++) {
			
			String Message="";
			for(int j=0;j+_answer.get(i)<3;j++)
				Message=Message+"0";
			for(int k=0;k<_answer.get(i);k++)
				Message=Message+"1";
			
			key=key+Message;
		}
		return key;	
		
	}

	
	public boolean join(String _profile_key, String _nick_name) {
		FutureGet futureGet = _dht.get(Number160.createHash("userList")).start();
		/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
		 @Override
		 public void operationComplete(FutureGet future) throws Exception {
		
				//System.out.println("complete" + profile);
		 }
		}).awaitListenersUninterruptibly();*/
	 
	futureGet.awaitUninterruptibly();
		if(futureGet.isSuccess()&&!futureGet.isEmpty()) {
		try {
			Object [] me= {_nick_name,_profile_key ,_dht.peer().peerAddress()};
			ArrayList<Object[]> oldList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
			
			oldList.add(me);
			_dht.put(Number160.createHash("userList"))
            .data(new Data(oldList)).start().awaitUninterruptibly();
			return searchFriends( _nick_name, _profile_key);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		}else {
			Object [] me= {_nick_name,_profile_key ,_dht.peer().peerAddress()};
			ArrayList<Object[]> userList=new ArrayList<Object[]>();
			userList.add(me);
			try {
				_dht.put(Number160.createHash("userList"))
				.data(new Data(userList)).start().awaitUninterruptibly();
				return true;
			} catch (IOException e) {
					e.printStackTrace();
			}
		
		}
		return false;
			
	
		}
	@Override
	public List<String> getFriends() {
		FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
		/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
		 @Override
		 public void operationComplete(FutureGet future) throws Exception {
		
				//System.out.println("complete" + profile);
		 }
		}).awaitListenersUninterruptibly();*/
	 
	futureGet.awaitUninterruptibly();
		if(futureGet.isSuccess()&&!futureGet.isEmpty()) {
		try {
			ArrayList<Object[]> fList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
			ArrayList<String> friendsName =new ArrayList<String>();
			for(Object[] friends:fList)
			{
				friendsName.add((String) friends[0]);
			}
			return friendsName;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
		}else {
		}
		return null;
	}
	public int hammingDistance(String a, String b) {
		int count=0;
		
	if (a == null || b == null) {
		System.out.println("error");
        return 0;
	}
	for (int i=0; i<a.length();i++) {
		if(a.charAt(i)!=b.charAt(i))
			count++;
	}
		return count;
	}

	  public boolean setFriends(String name,String profile, PeerAddress adress) throws IOException {
		  Message test;
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash(profile)).start();
				/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
				 @Override
				 public void operationComplete(FutureGet future) throws Exception {
				
						//System.out.println("complete" + profile);
				 }
				}).awaitListenersUninterruptibly();*/
			 
			futureGet.awaitUninterruptibly();
					addFriends(profile, adress);
				if (futureGet.isSuccess()) {
					test=new Message("prova", peerId,name);
					test.setMytype(Message.type.response);
					System.out.println("response from"+name+" to "+ profile);
					FutureDirect futureDirect = _dht.peer().sendDirect(adress).object(test).start();
					
					futureDirect.awaitUninterruptibly();
				
					return true;
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			}
	  public boolean addFriends(String profile, PeerAddress adress) throws IOException {
		  Message test;
		  ArrayList<Object[]> oldList= new ArrayList<Object[]>();
		  Object [] newfriends= {profile,adress};
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
				/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
					 @Override
					 public void operationComplete(FutureGet future) throws Exception {
					
							//System.out.println("complete" + profile);
					 }
					}).awaitListenersUninterruptibly();*/
				 
				futureGet.awaitUninterruptibly();
				if (futureGet.isSuccess()) {
					
					if(!futureGet.isEmpty()) {
					 oldList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
					 
					 }
					
					oldList.add(newfriends);
					_dht.put(Number160.createHash("friendsList"+peerId))
                    .data(new Data(oldList)).start().awaitUninterruptibly();
				
					return true;
					
				
					}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			}
	  public boolean removeFriends(String profile, PeerAddress adress) throws IOException {
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
				/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
				 @Override
				 public void operationComplete(FutureGet future) throws Exception {
				
						//System.out.println("complete" + profile);
				 }
				}).awaitListenersUninterruptibly();*/
			 
			futureGet.awaitUninterruptibly();
				if (futureGet.isSuccess()) {
					ArrayList<Object[]> oldList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
					
					
					Object [] newfriends= {profile,adress};
					
					
					
					for(int i=0;i<oldList.size();i++) {
						if(oldList.get(i)[0].equals(profile)) {
							oldList.remove(i);
							_dht.put(Number160.createHash("friendsList"+peerId))
		                    .data(new Data(oldList)).start().awaitUninterruptibly();
							return true;
						}
					
					
					
				}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			}
	  public boolean getmultichat(String name, String profile) throws IOException {
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash(profile)).start();
				/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
				 @Override
				 public void operationComplete(FutureGet future) throws Exception {
				
						//System.out.println("complete" + profile);
				 }
				}).awaitListenersUninterruptibly();*/
			 
			futureGet.awaitUninterruptibly();
				if (futureGet.isSuccess()&& (!profile.equals(name))) {
					HashSet<PeerAddress> peers_on_topic;
					if(futureGet.isEmpty() ) {
						System.out.println("is empty");
						return false;
					}
					
					peers_on_topic = (HashSet<PeerAddress>) futureGet.dataMap().values().iterator().next().object();
					peers_on_topic.add(_dht.peer().peerAddress());
					_dht.put(Number160.createHash(profile)).data(new Data(peers_on_topic)).start().awaitUninterruptibly();
					/*for(PeerAddress peer:peers_on_topic){
						
						String message=name+"ha accettato";
						FutureDirect futureDirect = _dht.peer().sendDirect(peer).object(name+" si è unito alla chat di gruppo").start();
				
						futureDirect.awaitUninterruptibly();
						
					}*/
					addFriends(profile, null);
					return true;
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
	  }
	  public boolean searchFriends( String nickName, String profilekey) throws IOException {
	    	FutureGet futureGet = _dht.get(Number160.createHash("userList")).start();
			
	    	futureGet.awaitUninterruptibly();
			Message test;
			try {
				if (futureGet.isSuccess()) {
					ArrayList<Object []> userList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
					test=new Message(profilekey, peerId, nickName);
					test.setMytype(Message.type.friends);
						ArrayList<Object[]> vuota=new ArrayList<Object[]>();
					 FuturePut fp = _dht.put(Number160.createHash("friendsList"+peerId)).data(new Data(vuota))
	                         .start().awaitUninterruptibly();
					 if(fp.isSuccess()) {
					for(int i=0;i<userList.size();i++){
						if(hammingDistance(profilekey, (String) userList.get(i)[1])<10) {
						PeerAddress peer=(PeerAddress) userList.get(i)[2];
						if(!(peer.peerId().equals(_dht.peer().peerAddress().peerId()))) {
						FutureDirect futureDirect = _dht.peer().sendDirect(peer).object(test).start();
						futureDirect.awaitUninterruptibly();
						}
						}
					}
				}else {
					return false;
				}
					return true;
				}
				return false;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
	    	
	    }
	  public boolean createGroupChat(String chatName, ArrayList<PeerAddress> peerfreinds){
		  Message test;
			try {
				FutureGet futureGet = _dht.get(Number160.createHash(chatName)).start();
				futureGet.awaitUninterruptibly();
				if (futureGet.isSuccess() && futureGet.isEmpty()) {
					
				HashSet<PeerAddress> peers_on_topic=new HashSet<PeerAddress>();
				peers_on_topic.add(_dht.peer().peerAddress());
				test=new Message("grup chat", peerId, chatName);
				_dht.put(Number160.createHash(chatName)).data(new Data(peers_on_topic)).start().awaitUninterruptibly();
				addFriends(chatName, null);
				test.setMytype(Message.type.multichat);
				Number160 id= new Number160(peerId);
				for (int i=0;i<peerfreinds.size();i++) {
					FutureDirect futureDirect = _dht.peer().sendDirect(peerfreinds.get(i)).object(test).start();
					futureDirect.awaitUninterruptibly();
					
					
				
				}
				return true;
			}return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	  
	  public boolean groupChat2(String name, ArrayList<Integer> friends,ArrayList<String> nickFriends) {
			
			
			 	FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
			 	/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
			 @Override
			 public void operationComplete(FutureGet future) throws Exception {
			
			 }
			}).awaitListenersUninterruptibly();*/
			 	futureGet.awaitUninterruptibly();
		 if(futureGet.isSuccess()) {
		try {
			ArrayList<Object[]> fList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
			ArrayList<PeerAddress> friendsadress =new ArrayList<PeerAddress>();
			TextIO textIO = TextIoFactory.getTextIO();
			Scanner keyboard = new Scanner(System.in);
			
			if(fList.size()<friends.size())
				return false;
			for(int i=0;i<friends.size();i++) {
			if(fList.get(friends.get(i))[0].equals(nickFriends.get(i))) {
			
				friendsadress.add((PeerAddress) fList.get(friends.get(i))[1]);
			}
			}
			return createGroupChat(name, friendsadress);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		}else {
			 System.out.println("non esiste");
			 return false;
		}
		return false;
		}
	  
	  public boolean sendMessage(int destination, String source,Object message) {

	    	FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
	        futureGet.awaitUninterruptibly();
	        Message test;
	        try {
		        if (futureGet.isSuccess()) {
		        	ArrayList <Object[]> friends= (ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
		        	
		        	test=new Message((String)message, peerId,source);
					test.setMytype(Message.type.chat);
					if(friends.size()> destination) {
					if(friends.get(destination)[1]!=null) {
					FutureDirect futureDirect = _dht.peer().sendDirect((PeerAddress) friends.get(destination)[1]).object(test).start();
			
					futureDirect.awaitUninterruptibly();
					}else {
						FutureGet futureGet2 = _dht.get(Number160.createHash((String)friends.get(destination)[0])).start();
				        futureGet2.awaitUninterruptibly();
				        if (futureGet2.isSuccess()) {
				        	test.setNickname((String) friends.get(destination)[0]);
				        	test.setText(source+ " :"+test.getText());
				            HashSet<PeerAddress> peers_on_topic;
						peers_on_topic = (HashSet<PeerAddress>) futureGet2.dataMap().values().iterator().next().object();
			        		for(PeerAddress peer:peers_on_topic)
						{
			        			System.out.println("send group message");
							FutureDirect futureDirect = _dht.peer().sendDirect(peer).object(test).start();
							futureDirect.awaitUninterruptibly();
						}
						}else {
							System.out.println("group message gone wrong");
							return false;
						}
				        }
					}else {
						System.out.println("destination don't exist");
						return false;
					}
					return true;
		        }}catch (Exception e) {
					// TODO: handle exception
				}
	    	return false;
	    }

	  public boolean changeKey(String nickName, String key) {
			
		  try {
			  FutureGet futureGet = _dht.get(Number160.createHash("userList")).start();
			  /*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
				 @Override
				 public void operationComplete(FutureGet future) throws Exception {
				
						//System.out.println("complete" + profile);
				 }
				}).awaitListenersUninterruptibly();*/
			 
			futureGet.awaitUninterruptibly();
				if(futureGet.isSuccess()){
					try {
						ArrayList <Object[]> userList= (ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
						for (int k=0;k<userList.size();k++) {
							if (userList.get(k)[0].equals(nickName)&&userList.get(k)[2].equals(_dht.peer().peerAddress())) {
								userList.get(k)[1]=key;
								k=userList.size();
							}
							_dht.put(Number160.createHash("userList"))
							.data(new Data(userList)).start().awaitUninterruptibly();
						}
						
					} catch (ClassNotFoundException e) {
					
						e.printStackTrace();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			  FutureGet futureGet2 = _dht.get(Number160.createHash("friendsList"+peerId)).start();
		        futureGet2.awaitUninterruptibly();
		        if (futureGet2.isSuccess()&&!futureGet2.isEmpty()) {
		        	Message test;
		        	ArrayList <Object[]> friends= (ArrayList<Object[]>) futureGet2.dataMap().values().iterator().next().object();
		        	
		        	test=new Message("exit", peerId,nickName);
					test.setMytype(Message.type.friends);
					for (int i=0;i<friends.size();i++) {
						if (friends.get(i)[1]!=null) {
					FutureDirect futureDirect = _dht.peer().sendDirect((PeerAddress) friends.get(i)[1]).object(test).start();
			
					futureDirect.awaitUninterruptibly();
						}
						/*_dht.remove(Number160.createHash("friendsList"+peerId)).start();
				        futureGet2.awaitUninterruptibly();*/
					}
					
					}
			return searchFriends( nickName, key);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  return false;
		}
	  public boolean leaveNetwork(String nickname) {
		  FutureGet futureGet = _dht.get(Number160.createHash("userList")).start();
		  /*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
			 @Override
			 public void operationComplete(FutureGet future) throws Exception {
			
					//System.out.println("complete" + profile);
			 }
			}).awaitListenersUninterruptibly();*/
		 
		futureGet.awaitUninterruptibly();
			if(futureGet.isSuccess()){
				try {
					ArrayList <Object[]> userList= (ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
					for (int k=0;k<userList.size();k++) {
						if (userList.get(k)[0].equals(nickname)&&userList.get(k)[2].equals(_dht.peer().peerAddress())) {
							userList.remove(k);
							k=userList.size();
						}
						_dht.put(Number160.createHash("userList"))
						.data(new Data(userList)).start().awaitUninterruptibly();
					}
					
				} catch (ClassNotFoundException e) {
				
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		  FutureGet futureGet2 = _dht.get(Number160.createHash("friendsList"+peerId)).start();
	        futureGet2.awaitUninterruptibly();
	        Message test;
	        try {
		        if (futureGet2.isSuccess()) {
		        	ArrayList <Object[]> friends= (ArrayList<Object[]>) futureGet2.dataMap().values().iterator().next().object();
		        	
		        	test=new Message("exit", peerId,nickname);
					test.setMytype(Message.type.friends);
					for (int i=0;i<friends.size();i++) {
						if (friends.get(i)[1]!=null) {
					FutureDirect futureDirect = _dht.peer().sendDirect((PeerAddress) friends.get(i)[1]).object(test).start();
			
					futureDirect.awaitUninterruptibly();
						}
					}
					
					}
		        _dht.peer().announceShutdown().start().awaitUninterruptibly();
					return true;
		        }catch (Exception e) {
					
				}
	    	return false;
			
			
			
		}
	 
	  public boolean addSpam(ArrayList<String> profile) throws IOException {
		  Message test;
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash("spamList"+peerId)).start();
				/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
				 @Override
				 public void operationComplete(FutureGet future) throws Exception {
				
						//System.out.println("complete" + profile);
				 }
				}).awaitListenersUninterruptibly();*/
			 
			futureGet.awaitUninterruptibly();
				if (futureGet.isSuccess()) {
					ArrayList<String> oldList= new ArrayList<String>();
					if(!futureGet.isEmpty()) {
					 oldList=(ArrayList<String>) futureGet.dataMap().values().iterator().next().object();
					
					
					}
					for(String nick:profile) {
					oldList.add(nick);
					}
					_dht.put(Number160.createHash("spamList"+peerId))
                    .data(new Data(oldList)).start().awaitUninterruptibly();
					
					return true;
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			}
	  public boolean removeSpam(ArrayList<String> profile) throws IOException {
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash("spamList"+peerId)).start();
				/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
				 @Override
				 public void operationComplete(FutureGet future) throws Exception {
				
						//System.out.println("complete" + profile);
				 }
				}).awaitListenersUninterruptibly();*/
			 
			futureGet.awaitUninterruptibly();
				if (futureGet.isSuccess()&&!futureGet.isEmpty()) {
					ArrayList<String> oldList=(ArrayList<String>) futureGet.dataMap().values().iterator().next().object();
					
					for(String nick:profile) {
						oldList.remove(nick);
					}_dht.put(Number160.createHash("spamList"+peerId))
                    .data(new Data(oldList)).start().awaitUninterruptibly();
					
					return true;
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			}
		public List<String> getSpamList() {
			FutureGet futureGet = _dht.get(Number160.createHash("spamList"+peerId)).start();
			/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
			 @Override
			 public void operationComplete(FutureGet future) throws Exception {
			
					//System.out.println("complete" + profile);
			 }
			}).awaitListenersUninterruptibly();*/
		 
		futureGet.awaitUninterruptibly();
			if(futureGet.isSuccess()&&!futureGet.isEmpty()) {
			try {
				ArrayList<String> sList=(ArrayList<String>) futureGet.dataMap().values().iterator().next().object();
				ArrayList<String> friendsName =new ArrayList<String>();
				for(String friends:sList)
				{
					friendsName.add( friends);
				}
				return friendsName;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
			return new ArrayList<String>();
		}
	  
	  
}
