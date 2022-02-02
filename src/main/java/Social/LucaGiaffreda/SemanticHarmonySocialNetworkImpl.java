package Social.LucaGiaffreda;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
	List<String>question;
	private Peer peer;
	 private PeerDHT _dht;
	 final private int DEFAULT_MASTER_PORT=4000;
	 public int peerId;
	 private String adress;
	private ArrayList<Object[]> friendList;

	 public SemanticHarmonySocialNetworkImpl(int id, String adress, MessageListener listner) throws Exception {
		 question=new ArrayList<String>();
		 peerId=id;
		 question.add("Ho una parola gentile per tutti");
		 question.add("Ho una parola gentile per tutti");
		 question.add("Ho una parola gentile per tutti");
		 question.add("Ho una parola gentile per tutti");
		 this.adress=adress;
		 friendList=new ArrayList<Object[]>();
		
		 peer= new PeerBuilder(Number160.createHash(peerId)).ports(DEFAULT_MASTER_PORT+peerId).start();
		 	_dht = new PeerBuilderDHT(peer).start();
		 	FutureBootstrap fb = peer.bootstrap().inetAddress(InetAddress.getByName(adress)).ports(DEFAULT_MASTER_PORT).start();
	 		fb.awaitUninterruptibly();
	 		if(fb.isSuccess()) {
	 			peer.discover().peerAddress(fb.bootstrapTo().iterator().next()).start().awaitUninterruptibly();
	 		}else {
	 			throw new Exception("Error in master peer bootstrap.");
	 		}
	 		peer.objectDataReply(new ObjectDataReply() {
	 			
	 			public Object reply(PeerAddress sender, Object request) throws Exception {
	 				System.out.println("obj reply");
	 				return listner.parseMessage(sender,request);
	 			}
	 		});
	}
	 
	@Override
	public List<String> getUserProfileQuestions() {
	/*	List <String> answer=new ArrayList<String>();
		for(int i=0;i<question.size();i++){
			TextIO textIO = TextIoFactory.getTextIO();
			TextTerminal terminal = textIO.getTextTerminal();
		answer.add( textIO.newStringInputReader().withDefaultValue("defaultValue").read(question.get(i)));
		}
		
		return answer;*/
		 question=new ArrayList<String>();
		question.add("Ho una parola gentile per tutti");
		 question.add("Ho una parola gentile per tutti");
		 question.add("Ho una parola gentile per tutti");
		 question.add("Ho una parola gentile per tutti");
		 return question;
	
	}

	@Override
	public String createAuserProfileKey(List<Integer> _answer) {
		String key="";
		for (int i=0;i<_answer.size();i++) {
			//if(_answer.get(i).equals(1))
			key=key+_answer.get(i);
		}
		return key;	
		
	}

	@Override
	public boolean join(String _profile_key, String _nick_name) {
		try {
			/* class MessageListenerImpl implements MessageListener{
			int peerid;
			
			public MessageListenerImpl(int peerid)
			{
				this.peerid=peerid;
				//type.valueOf(_nick_name);

			}
			public Object parseMessage(PeerAddress sender, Object obj) {
				
				TextIO textIO = TextIoFactory.getTextIO();
				TextTerminal terminal = textIO.getTextTerminal();
				;
				terminal.printf("\n"+peerid+"] (Direct Message Received) "+obj+"\n\n");
					try {
						App a = (App) obj;
						
						if(a.getMytype()==App.type.friends) {
							if(hammingDistance(a.getText(), _profile_key)<2) {
								Object newFriends[]= {a.getNickname(),a.getAdress()};
			 				if(!friendList.contains(newFriends))
			 					{
			 					terminal.printf("\n"+peerid+" invia response amico con i dati che ha i dati"+a+"con indirizzo"+a.getAdress()+"sender ="+sender+"\n\n");
			 					terminal.printf("\n"+peerid+" risultati getfreinds"+getFriends5b(_nick_name, a.getNickname(),sender)+"\n\n");
						
			 					friendList.add(newFriends);
			 					}
			 				}else {
			 					Object newFriends[]= {a.getNickname(),a.getAdress()};
			 					for (int i=0;i<friendList.size();i++) {
			 						if (friendList.get(i)[0].equals(newFriends[0])) {
			 							terminal.printf("\n"+peerid+" rimosso amico con i dati che ha i dati"+a+"\n\n");
			 	 			 			
		 			 					friendList.remove(newFriends);
			 						}
			 					}
			 					
			 					terminal.printf("\n"+peerid+" amicizia non corrisposta"+a+"\n\n");
			 				}
						//}
						}else if(a.getMytype()==App.type.chat){
							terminal.printf("\n"+peerid+"] (Direct Message Received) message"+a.getText()+"\n\n");
							}else if(a.getMytype()==App.type.multichat){
								terminal.printf("\n"+peerid+"] (Direct Message Received) message"+getmultichat(_nick_name, a.getNickname())+"\n\n");
								} else {
								terminal.printf("\n"+peerid+" aggiunge un nuovo amico che ha i dati"+a+"\n\n");
								Object newFriends[]= {a.getNickname(),a.getAdress()};
								friendList.add(newFriends);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				return "success";
			}
			 }*/
			
			//store(_nick_name, "ip");
			//get( _nick_name);
			if(peerId==0) {
				store("test", "ip");
				searchFriends2b("test", _nick_name, _profile_key);
				
			}else {
				//store("test", "ip");
				get( "test");
				System.out.println("nick name per search ="+_nick_name);
				searchFriends2b("test", _nick_name, _profile_key);
			}
			//get( "test");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
			
		return false;
		}

	@Override
	public List<String> getFriends() {
		FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
		futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
			 @Override
			 public void operationComplete(FutureGet future) throws Exception {
			  if(future.isSuccess()) { // this flag indicates if the future was successful
			   System.out.println("success");
			   
			  } else {
			   System.out.println("failure");
			  }
			 }
			}).awaitListenersUninterruptibly();
		if(futureGet.isSuccess()) {
		try {
			ArrayList<Object[]> fList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
			ArrayList<String> friendsName =new ArrayList<String>();
			for(Object[] friends:fList)
			{
				friendsName.add((String) friends[0]);
			}
			return friendsName;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else {
			 System.out.println("non esiste");
		}
		return null;
	}
	public int hammingDistance(String a, String b) {
		int count=0;
		System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAA"+a.length());
		for (int i=0; i<a.length();i++) {
			if(a.charAt(i)!=b.charAt(i))
				count++;
		}
		return count;
	}
	  public boolean getFriends5b(String name,String profile, PeerAddress adress) throws IOException {
		  App test;
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash(profile)).start();
				futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
					 @Override
					 public void operationComplete(FutureGet future) throws Exception {
					  if(future.isSuccess()) { // this flag indicates if the future was successful
					   System.out.println("success");
					   
					  } else {
					   System.out.println("failure");
					  }
					 }
					}).awaitListenersUninterruptibly();
				/* FuturePut future = _dht.put(Number160.createHash(profile))
                         .data(new Data(name)).start().awaitUninterruptibly();*/
				if (futureGet.isSuccess()) {
					
					test=new App("prova", peerId,name,_dht.peer().peerAddress());
					test.setMytype(App.type.response);
					FutureDirect futureDirect = _dht.peer().sendDirect(adress).object(test).start();
					
					futureDirect.awaitListenersUninterruptibly();
				
					return true;
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			}
	  public boolean getFriends5c(String name,String profile, PeerAddress adress) throws IOException {
		  App test;
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash(profile)).start();
				futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
					 @Override
					 public void operationComplete(FutureGet future) throws Exception {
					  if(future.isSuccess()) { // this flag indicates if the future was successful
					   System.out.println("success");
					   
					  } else {
					   System.out.println("failure");
					  }
					 }
					}).awaitListenersUninterruptibly();
				/* FuturePut future = _dht.put(Number160.createHash(profile))
                         .data(new Data(name)).start().awaitUninterruptibly();*/
				addFriends(profile, adress);
				if (futureGet.isSuccess()) {
					
					test=new App("prova", peerId,name,_dht.peer().peerAddress());
					test.setMytype(App.type.response);
					FutureDirect futureDirect = _dht.peer().sendDirect(adress).object(test).start();
					
					futureDirect.awaitListenersUninterruptibly();
				
					return true;
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			}
	  public boolean addFriends(String profile, PeerAddress adress) throws IOException {
		  App test;
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
				futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
					 @Override
					 public void operationComplete(FutureGet future) throws Exception {
					  if(future.isSuccess()) { // this flag indicates if the future was successful
					   System.out.println("success");
					   
					  } else {
					   System.out.println("failure");
					  }
					 }
					}).awaitListenersUninterruptibly();
				
				if (futureGet.isSuccess()) {
					ArrayList<Object[]> oldList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
					//ArrayList<Object[]> oldList=_dht.get(Number160.createHash("friendsList")).;
					/* FuturePut future = _dht.put(Number160.createHash(profile))
	                         .data(new Data(name)).start().awaitUninterruptibly();*/
					
					Object [] newfriends= {profile,adress};
					oldList.add(newfriends);
					_dht.put(Number160.createHash("friendsList"+peerId))
                    .data(new Data(oldList)).start().awaitListenersUninterruptibly();
					/*test=new App("prova", peerId,name,_dht.peer().peerAddress());
					test.setMytype(App.type.response);
					FutureDirect futureDirect = _dht.peer().sendDirect(adress).object(test).start();
					
					futureDirect.awaitListenersUninterruptibly();
				*/
					return true;
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			}
	  public boolean removeFriends(String profile, PeerAddress adress, int index) throws IOException {
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
				futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
					 @Override
					 public void operationComplete(FutureGet future) throws Exception {
					  if(future.isSuccess()) { // this flag indicates if the future was successful
					   System.out.println("success");
					   
					  } else {
					   System.out.println("failure");
					  }
					 }
					}).awaitListenersUninterruptibly();
				
				if (futureGet.isSuccess()) {
					ArrayList<Object[]> oldList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
					//ArrayList<Object[]> oldList=_dht.get(Number160.createHash("friendsList")).;
					/* FuturePut future = _dht.put(Number160.createHash(profile))
	                         .data(new Data(name)).start().awaitUninterruptibly();*/
					
					Object [] newfriends= {profile,adress};
					
					/*int i=0;
					while(!oldList.get(i)[0].equals(newfriends[0]))
						i++*/;
					oldList.remove(index);
					_dht.put(Number160.createHash("friendsList"+peerId))
                    .data(new Data(oldList)).start().awaitListenersUninterruptibly();
					/*test=new App("prova", peerId,name,_dht.peer().peerAddress());
					test.setMytype(App.type.response);
					FutureDirect futureDirect = _dht.peer().sendDirect(adress).object(test).start();
					
					futureDirect.awaitListenersUninterruptibly();
				*/
					return true;
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			}
	  public boolean getmultichat(String name, String profile) throws IOException {
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash(profile)).start();
				futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
					 @Override
					 public void operationComplete(FutureGet future) throws Exception {
					  if(future.isSuccess()) { // this flag indicates if the future was successful
					   System.out.println("success");
					   
					  } else {
					   System.out.println("failure");
					  }
					 }
					}).awaitListenersUninterruptibly();
				
				if (futureGet.isSuccess()&& (!profile.equals(name))) {
					if(futureGet.isEmpty() ) {
						System.out.println("is empty");
						return false;
					}
					HashSet<PeerAddress> peers_on_topic;
					peers_on_topic = (HashSet<PeerAddress>) futureGet.dataMap().values().iterator().next().object();
					peers_on_topic.add(_dht.peer().peerAddress());
					_dht.put(Number160.createHash(profile)).data(new Data(peers_on_topic)).start().awaitListenersUninterruptibly();
					for(PeerAddress peer:peers_on_topic){
						
						String message=name+"ha accettato";
						System.out.println("send response from "+name+" to "+profile);
						FutureDirect futureDirect = _dht.peer().sendDirect(peer).object(name+" si è unito alla chat di gruppo").start();
				
						futureDirect.awaitListenersUninterruptibly();
						
					}
					
					return true;
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
	  }
	  public void store(String name, String ip) throws IOException {
	    	try {
	    	FutureGet futureGet = _dht.get(Number160.createHash(name)).start();
			futureGet.awaitUninterruptibly();
			if (futureGet.isSuccess() && futureGet.isEmpty()) {
				HashSet<PeerAddress> peers_on_topic;
				 peers_on_topic=new HashSet<PeerAddress>();
	        //_dht.put(Number160.createHash(name)).data(new Data((new HashSet<PeerAddress>()))).start().awaitUninterruptibly();
	        peers_on_topic.add(_dht.peer().peerAddress());
	        _dht.put(Number160.createHash(name)).data(new Data(peers_on_topic)).start().awaitUninterruptibly();
	        System.out.print("put test");
			}
	    } catch (Exception e) {
			e.printStackTrace();
		}
	    }
	  public void store2(String name, String ip) throws IOException {
	    	try {
	    	FutureGet futureGet = _dht.get(Number160.createHash(name)).start();
			futureGet.awaitUninterruptibly();
			if (futureGet.isSuccess() && futureGet.isEmpty()) {
				HashSet<PeerAddress> peers_on_topic;
				 peers_on_topic=new HashSet<PeerAddress>();
	        _dht.put(Number160.createHash(name)).data(new Data((new HashSet<PeerAddress>()))).start().awaitUninterruptibly();
	        peers_on_topic.add(_dht.peer().peerAddress());
	        _dht.put(Number160.createHash(name)).data(new Data(peers_on_topic)).start().awaitUninterruptibly();
	        System.out.print("put test");
			}
	    } catch (Exception e) {
			e.printStackTrace();
		}
	    } 
	  public String get(String name) throws ClassNotFoundException, IOException {
	        FutureGet futureGet = _dht.get(Number160.createHash(name)).start();
	        futureGet.awaitUninterruptibly();
	        try {
	        if (futureGet.isSuccess()) {
	        
				
				
				HashSet<PeerAddress> peers_on_topic;
				peers_on_topic = (HashSet<PeerAddress>) futureGet.dataMap().values().iterator().next().object();
				peers_on_topic.add(_dht.peer().peerAddress());
				_dht.put(Number160.createHash(name)).data(new Data(peers_on_topic)).start().awaitUninterruptibly();
				return "x";
	        }}catch (Exception e) {
				// TODO: handle exception
			}
	        return "not found";
	    }
	  public void searchFriends2b(String name, String nickName, String profilekey) throws IOException {
	    	FutureGet futureGet = _dht.get(Number160.createHash(name)).start();
			futureGet.awaitUninterruptibly();
			//nickName="test";
			App test;
			try {
				if (futureGet.isSuccess()) {
				System.out.println("future search friends succes");
				HashSet<PeerAddress> peers_on_topic;
				peers_on_topic = (HashSet<PeerAddress>) futureGet.dataMap().values().iterator().next().object();
				test=new App(profilekey, peerId, nickName, _dht.peer().peerAddress());
				//_dht.put(Number160.createHash(nickName)).data(new Data(new HashSet<PeerAddress>())).start().awaitUninterruptibly();
				//peers_on_topic.add(_dht.peer().peerAddress());
				//_dht.put(Number160.createHash(nickName)).data(new Data(peers_on_topic)).start().awaitUninterruptibly();
				System.out.println("nick name per send di test ="+test.getNickname());
				test.setMytype(App.type.friends);
				//Number160 id= new Number160(peerId);
				// creazione della lista di amici 
				 FuturePut fp = _dht.put(Number160.createHash("friendsList"+peerId)).data(new Data(friendList))
                         .start().awaitUninterruptibly();
				for(PeerAddress peer:peers_on_topic){
					System.out.println("peer ="+peer.peerId()+" peeradress" +_dht.peer().peerAddress().peerId());
					if(!(peer.equals(_dht.peer().peerAddress()))) {
					if(!(peer.peerId().equals(_dht.peer().peerAddress().peerId()))) {
					FutureDirect futureDirect = _dht.peer().sendDirect(peer).object(test).start();
					futureDirect.awaitUninterruptibly();
					}
					}
				}
				//_dht.put(Number160.createHash(nickName)).data(new Data(new HashSet<PeerAddress>())).start().awaitUninterruptibly();
			}
			}catch (Exception e) {
				// TODO: handle exception
			}
	    	
	    }
	  public boolean createGroupChat(String chatName, ArrayList<PeerAddress> peerfreinds){
		  App test;
			try {
				FutureGet futureGet = _dht.get(Number160.createHash(chatName)).start();
				futureGet.awaitUninterruptibly();
				if (futureGet.isSuccess() && futureGet.isEmpty()) 
					System.out.println("future search friends succes");
				test=new App("grup chat", peerId, chatName, _dht.peer().peerAddress());
				_dht.put(Number160.createHash(chatName)).data(new Data(new HashSet<PeerAddress>())).start().awaitUninterruptibly();
				System.out.println("nick name per send di test ="+test.getNickname()+ "sixe of peer friend list"+ peerfreinds.size());
				test.setMytype(App.type.multichat);
				Number160 id= new Number160(peerId);
				for (int i=0;i<peerfreinds.size();i++) {
					FutureDirect futureDirect = _dht.peer().sendDirect(peerfreinds.get(i)).object(test).start();
					futureDirect.awaitUninterruptibly();
					
					
				
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	  public void groupChat() {
			ArrayList<PeerAddress> peerfreinds=new ArrayList<PeerAddress>();
			System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAA friends"+friendList.size());
			for (int i=0;i<friendList.size();i++) {
				System.out.println("peer ="+friendList.get(i)[0]+" peeradress" +friendList.get(i)[1]);
				peerfreinds.add((PeerAddress) friendList.get(i)[1]);
			}
			createGroupChat("gruppo", peerfreinds);
		}
	  /*public void message(String nick) {
			TextIO textIO = TextIoFactory.getTextIO();
		    TextTerminal terminal = textIO.getTextTerminal();
			String destination=textIO.newStringInputReader().withDefaultValue("default").read("destination");
		    //int destination=textIO.newIntInputReader().withDefaultValue(0).read("id destinazione");
			int i=0;
			/*while(!destination.equals(friendList.get(i)[0])) {
				i++;
			}*/
			/*String message=textIO.newStringInputReader().withDefaultValue("default").read("message");
			//con.sendMessage2((PeerAddress) friendList.get(i)[1], nick, message);
			sendMessagebyid(destination, nick, message);
			
		}*/
	  public boolean sendMessagebyid(String destination, String source,Object message) {

	    	FutureGet futureGet = _dht.get(Number160.createHash(destination)).start();
	        futureGet.awaitUninterruptibly();
	        try {
		        if (futureGet.isSuccess()) {
		        	HashSet<PeerAddress> peers_on_topic;
					peers_on_topic = (HashSet<PeerAddress>) futureGet.dataMap().values().iterator().next().object();
		        		for(PeerAddress peer:peers_on_topic)
					{
						FutureDirect futureDirect = _dht.peer().sendDirect(peer).object("message").start();
						futureDirect.awaitUninterruptibly();
					}
					return true;
		        }}catch (Exception e) {
					
				}
	    	return false;
	    }
	  public boolean sendMessage2(PeerAddress destination, String source,Object message) {

	    	FutureGet futureGet = _dht.get(Number160.createHash(source)).start();
	        futureGet.awaitUninterruptibly();
	        App test;
	        try {
		        if (futureGet.isSuccess()) {
		        	test=new App("prova", peerId,source,_dht.peer().peerAddress());
					test.setMytype(App.type.chat);
					FutureDirect futureDirect = _dht.peer().sendDirect(destination).object(message).start();
					
					futureDirect.awaitListenersUninterruptibly();
					return true;
		        }}catch (Exception e) {
					// TODO: handle exception
				}
	    	return false;
	    }
	  public void changeKey(String nickName) {
			List<String> question= getUserProfileQuestions();
	    	List <Integer> answer =new ArrayList<>();
	    	for (int i=0; i<question.size();i++) {
	    		answer.add(Integer.parseInt(question.get(i)));
	    	}
	    	String key= createAuserProfileKey(answer);
	    	//friendList.clear();
	    	try {
				searchFriends2b("test", nickName, key);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
