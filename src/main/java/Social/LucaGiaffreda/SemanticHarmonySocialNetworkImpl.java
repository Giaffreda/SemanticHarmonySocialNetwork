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
	List<String>question;
	private Peer peer;
	 private PeerDHT _dht;
	 final private int DEFAULT_MASTER_PORT=4000;
	 public int peerId;
	 private String adress;
	private ArrayList<Object[]> friendList;

	 public SemanticHarmonySocialNetworkImpl(int id, String adress, MessageListener listner) throws Exception {
		 //question=new ArrayList<String>();
		 peerId=id;
		 /*question.add("Ho una parola gentile per tutti");
		 question.add("Ho una parola gentile per tutti");
		 question.add("Ho una parola gentile per tutti");
		 question.add("Ho una parola gentile per tutti");*/
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
			String app="";
			for(int j=0;j+_answer.get(i)<3;j++)
				app=app+"0";
			for(int k=0;k<_answer.get(i);k++)
				app=app+"1";
			//key=key+_answer.get(i);
			key=key+app;
		}
		return key;	
		
	}

	/*@Override
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
			/*if(peerId==0) {
				store("test", "ip");
				return searchFriends2b("test", _nick_name, _profile_key);
				
			}else {
				//store("test", "ip");
				get( "test");
				System.out.println("nick name per search ="+_nick_name);
				return searchFriends2b("test", _nick_name, _profile_key);
			}
			//get( "test");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
			
		return false;
		}*/
	public boolean join(String _profile_key, String _nick_name) {
		FutureGet futureGet = _dht.get(Number160.createHash("userList")).start();
		futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
			 @Override
			 public void operationComplete(FutureGet future) throws Exception {
			  if(future.isSuccess()) { // this flag indicates if the future was successful
			  // System.out.println("success");
			   
			  } else {
			  // System.out.println("failure");
			  }
			 }
			}).awaitListenersUninterruptibly();
		//futureGet.awaitUninterruptibly();
		 //System.out.println("failure");
		if(futureGet.isSuccess()&&!futureGet.isEmpty()) {
		try {
			/*ArrayList<Object[]> fList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
			ArrayList<String> friendsName =new ArrayList<String>();
			
			// System.out.println(peerId+" get friends +"+fList.size());
			for(Object[] friends:fList)
			{
				// System.out.println(peerId+" add friends "+friends[0]);
				friendsName.add((String) friends[0]);
			}*/
			//System.out.println("teeeest");
			Object [] me= {_nick_name,_profile_key ,_dht.peer().peerAddress()};
			ArrayList<Object[]> oldList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
			
			oldList.add(me);
			_dht.put(Number160.createHash("userList"))
            .data(new Data(oldList)).start().awaitListenersUninterruptibly();
			return searchFriends2c( _nick_name, _profile_key);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else {
			Object [] me= {_nick_name,_profile_key ,_dht.peer().peerAddress()};
			ArrayList<Object[]> userList=new ArrayList<Object[]>();
			//Object [] me= {_nick_name,_profile_key ,_dht.peer().peerAddress()};
			userList.add(me);
			try {
				_dht.put(Number160.createHash("userList"))
				.data(new Data(userList)).start().awaitListenersUninterruptibly();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	 System.out.println("non esiste");
		}
		return false;
			
	//return false;
		}
	@Override
	public List<String> getFriends() {
		FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
		futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
			 @Override
			 public void operationComplete(FutureGet future) throws Exception {
			  if(future.isSuccess()) { // this flag indicates if the future was successful
			  // System.out.println("success");
			   
			  } else {
			  // System.out.println("failure");
			  }
			 }
			}).awaitListenersUninterruptibly();
		//futureGet.awaitUninterruptibly();
		 //System.out.println("failure");
		if(futureGet.isSuccess()&&!futureGet.isEmpty()) {
		try {
			ArrayList<Object[]> fList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
			ArrayList<String> friendsName =new ArrayList<String>();
			// System.out.println(peerId+" get friends +"+fList.size());
			for(Object[] friends:fList)
			{
				// System.out.println(peerId+" add friends "+friends[0]);
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
		//	 System.out.println("non esiste");
		}
		return null;
	}
	public int hammingDistance(String a, String b) {
		int count=0;
		
		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA"+a.length()+" BBBBBBBBB"+ b.length());
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
	/*  public boolean getFriends5b(String name,String profile, PeerAddress adress) throws IOException {
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
				/*if (futureGet.isSuccess()) {
					
					test=new App("prova", peerId,name);//new App("prova", peerId,name,_dht.peer().peerAddress());
					test.setMytype(App.type.response);
					FutureDirect futureDirect = _dht.peer().sendDirect(adress).object(test).start();
					
					futureDirect.awaitListenersUninterruptibly();
				
					return true;
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			}*/
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
				//futureGet.awaitUninterruptibly();
				/* FuturePut future = _dht.put(Number160.createHash(profile))
                         .data(new Data(name)).start().awaitUninterruptibly();*/
				boolean c;
				//System.out.println("fget enter from "+name+" to "+profile+" and result="+(c=addFriends(profile, adress)));
				c=addFriends(profile, adress);
				if (futureGet.isSuccess()&&c) {
					//if(futureGet.isEmpty()) return false;
					test=new App("prova", peerId,name);
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
					  /*if(future.isSuccess()) { // this flag indicates if the future was successful
					  
						  System.out.println("success");
					   
					  } else {
					   System.out.println("failure");
					  }*/
					 }
					}).awaitListenersUninterruptibly();
				//futureGet.awaitUninterruptibly();
				if (futureGet.isSuccess()) {
					ArrayList<Object[]> oldList= new ArrayList<Object[]>();
					if(!futureGet.isEmpty()) {
					 oldList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
					//ArrayList<Object[]> oldList=_dht.get(Number160.createHash("friendsList")).;
					/* FuturePut future = _dht.put(Number160.createHash(profile))
	                         .data(new Data(name)).start().awaitUninterruptibly();*/
					for(int i=0;i<oldList.size();i++) {
						if(oldList.get(i)[0].equals(profile)) {
							oldList.remove(i);
							_dht.put(Number160.createHash("friendsList"+peerId))
		                    .data(new Data(oldList)).start().awaitListenersUninterruptibly();
							//System.out.print("REMOVED\n\n\n\n");
							return false;
						}
							//return true;
					}
					}
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
					/*HashSet<PeerAddress> peers_on_topic;
				peers_on_topic = (HashSet<PeerAddress>) futureGet.dataMap().values().iterator().next().object();
				peers_on_topic.add(_dht.peer().peerAddress());
				_dht.put(Number160.createHash(name)).data(new Data(peers_on_topic)).start().awaitUninterruptibly();*/
					HashSet<PeerAddress> peers_on_topic;
					peers_on_topic = (HashSet<PeerAddress>) futureGet.dataMap().values().iterator().next().object();
					peers_on_topic.add(_dht.peer().peerAddress());
					_dht.put(Number160.createHash(profile)).data(new Data(peers_on_topic)).start().awaitListenersUninterruptibly();
					for(PeerAddress peer:peers_on_topic){
						
						String message=name+"ha accettato";
						//System.out.println("send response from "+name+" to "+profile);
						FutureDirect futureDirect = _dht.peer().sendDirect(peer).object(name+" si è unito alla chat di gruppo").start();
				
						futureDirect.awaitListenersUninterruptibly();
						
					}
					addFriends(profile, null);
					return true;
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
	  }
	/*  public void store(String name, String ip) throws IOException {
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
	    } */
	  /*public String get(String name) throws ClassNotFoundException, IOException {
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
	    }*/
	 /* public boolean searchFriends2b(String name, String nickName, String profilekey) throws IOException {
	    	FutureGet futureGet = _dht.get(Number160.createHash(name)).start();
			/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
				 @Override
				 public void operationComplete(FutureGet future) throws Exception {
				  if(future.isSuccess()) { // this flag indicates if the future was successful
				   System.out.println("success");
				   
				  } else {
				   System.out.println("failure");
				  }
				 }
				}).awaitListenersUninterruptibly();*/
	    	/*futureGet.awaitUninterruptibly();
			//nickName="test";
			App test;
			try {
				if (futureGet.isSuccess()) {
					System.out.println("future searchaaaaa friends succes");
					HashSet<PeerAddress> peers_on_topic;
					peers_on_topic = (HashSet<PeerAddress>) futureGet.dataMap().values().iterator().next().object();
					test=new App(profilekey, peerId, nickName);
					//_dht.put(Number160.createHash(nickName)).data(new Data(new HashSet<PeerAddress>())).start().awaitUninterruptibly();
					//peers_on_topic.add(_dht.peer().peerAddress());
					//_dht.put(Number160.createHash(nickName)).data(new Data(peers_on_topic)).start().awaitUninterruptibly();
					System.out.println("nick name per send di test ="+test.getNickname());
					test.setMytype(App.type.friends);
					//Number160 id= new Number160(peerId);
					// creazione della lista di amici 
					 FuturePut fp = _dht.put(Number160.createHash("friendsList"+peerId)).data(new Data(friendList))
	                         .start().awaitUninterruptibly();
					 if(fp.isSuccess()) {
					for(PeerAddress peer:peers_on_topic){
						System.out.println("peer ="+peer.peerId()+" peeradress" +_dht.peer().peerAddress().peerId());
						//if(!(peer.equals(_dht.peer().peerAddress()))) {
						if(!(peer.peerId().equals(_dht.peer().peerAddress().peerId()))) {
						FutureDirect futureDirect = _dht.peer().sendDirect(peer).object(test).start();
						futureDirect.awaitUninterruptibly();
						}
						//}
					}
				}else {
					return false;
				}
					//_dht.put(Number160.createHash(nickName)).data(new Data(new HashSet<PeerAddress>())).start().awaitUninterruptibly();
					return true;
				}
				return false;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
	    	
	    }*/
	  public boolean searchFriends2c( String nickName, String profilekey) throws IOException {
	    	FutureGet futureGet = _dht.get(Number160.createHash("userList")).start();
			/*futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
				 @Override
				 public void operationComplete(FutureGet future) throws Exception {
				  if(future.isSuccess()) { // this flag indicates if the future was successful
				   System.out.println("success");
				   
				  } else {
				   System.out.println("failure");
				  }
				 }
				}).awaitListenersUninterruptibly();*/
	    	futureGet.awaitUninterruptibly();
			//nickName="test";
			App test;
			try {
				if (futureGet.isSuccess()) {
					//System.out.println("future searchaaaaa friends succes");
					ArrayList<Object []> userList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
					//HashSet<PeerAddress> peers_on_topic;
					//peers_on_topic = (HashSet<PeerAddress>) futureGet.dataMap().values().iterator().next().object();
					test=new App(profilekey, peerId, nickName);
					//_dht.put(Number160.createHash(nickName)).data(new Data(new HashSet<PeerAddress>())).start().awaitUninterruptibly();
					//peers_on_topic.add(_dht.peer().peerAddress());
					//_dht.put(Number160.createHash(nickName)).data(new Data(peers_on_topic)).start().awaitUninterruptibly();
					//System.out.println("nick name per send di test ="+test.getNickname());
					test.setMytype(App.type.friends);
					//Number160 id= new Number160(peerId);
					// creazione della lista di amici 
					ArrayList<Object[]> vuota=new ArrayList<Object[]>();
					 FuturePut fp = _dht.put(Number160.createHash("friendsList"+peerId)).data(new Data(vuota))
	                         .start().awaitUninterruptibly();
					 if(fp.isSuccess()) {
					for(int i=0;i<userList.size();i++){
						if(hammingDistance(profilekey, (String) userList.get(i)[1])<6) {
							//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA"+userList.get(i)[2]);
						PeerAddress peer=(PeerAddress) userList.get(i)[2];
						//System.out.println("peer ="+peer.peerId()+" peeradress" +_dht.peer().peerAddress().peerId());
						//if(!(peer.equals(_dht.peer().peerAddress()))) {
						if(!(peer.peerId().equals(_dht.peer().peerAddress().peerId()))) {
						FutureDirect futureDirect = _dht.peer().sendDirect(peer).object(test).start();
						futureDirect.awaitUninterruptibly();
						}
						//}
						}
					}
				}else {
					return false;
				}
					//_dht.put(Number160.createHash(nickName)).data(new Data(new HashSet<PeerAddress>())).start().awaitUninterruptibly();
					return true;
				}
				return false;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;
	    	
	    }
	  public boolean createGroupChat(String chatName, ArrayList<PeerAddress> peerfreinds){
		  App test;
			try {
				FutureGet futureGet = _dht.get(Number160.createHash(chatName)).start();
				futureGet.awaitUninterruptibly();
				if (futureGet.isSuccess() && futureGet.isEmpty()) {
					//System.out.println("future search friends succes");
				HashSet<PeerAddress> peers_on_topic=new HashSet<PeerAddress>();
				peers_on_topic.add(_dht.peer().peerAddress());
				test=new App("grup chat", peerId, chatName);
				_dht.put(Number160.createHash(chatName)).data(new Data(peers_on_topic)).start().awaitUninterruptibly();
				addFriends(chatName, null);
				//System.out.println("nick name per send di test ="+test.getNickname()+ "sixe of peer friend list"+ peerfreinds.size());
				test.setMytype(App.type.multichat);
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
	  /*
	   * public boolean createGroupChat(String chatName, ArrayList<Integer> peerfreinds){
		  App test;
			try {
				FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
				futureGet.awaitUninterruptibly();
				if (futureGet.isSuccess() && futureGet.isEmpty()) 
					System.out.println("future search friends succes");
				ArrayList<Object[]> friendsList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
				test=new App("grup chat", peerId, chatName);
				_dht.put(Number160.createHash(chatName)).data(new Data(new HashSet<PeerAddress>())).start().awaitUninterruptibly();
				System.out.println("nick name per send di test ="+test.getNickname()+ "sixe of peer friend list"+ peerfreinds.size());
				test.setMytype(App.type.multichat);
				Number160 id= new Number160(peerId);
				for (int i=0;i<peerfreinds.size();i++) {
					FutureDirect futureDirect = _dht.peer().sendDirect((PeerAddress) friendsList.get(peerfreinds.get(i))[1]).object(test).start();
					futureDirect.awaitUninterruptibly();
					
					
				
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	   * */
	  public boolean groupChat() {
			/*ArrayList<PeerAddress> peerfreinds=new ArrayList<PeerAddress>();
			System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAA friends"+friendList.size());
			for (int i=0;i<friendList.size();i++) {
				System.out.println("peer ="+friendList.get(i)[0]+" peeradress" +friendList.get(i)[1]);
				peerfreinds.add((PeerAddress) friendList.get(i)[1]);
			}
			createGroupChat("gruppo", peerfreinds);*/
			
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
		 //System.out.println("failure");
		if(futureGet.isSuccess()) {
		try {
			ArrayList<Object[]> fList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
			ArrayList<PeerAddress> friendsadress =new ArrayList<PeerAddress>();
			TextIO textIO = TextIoFactory.getTextIO();
			//textIO=new TextIO(System.in);
			Scanner keyboard = new Scanner(System.in);
			//textIO.dispose(resultData);;
			/*
			 * if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici")) {
		ArrayList<String>friends= (ArrayList<String>) connector.getFriends();
		while (i<friends.size()) {
			System.out.println("friends n "+i+" "+friends.get(i));
			i++;
		}
		while(textIO.newBooleanInputReader().withDefaultValue(false).read("\n vuoi aggiungere amici al gruppo?\n")) {
			peerfreinds.add(textIO.newIntInputReader().withDefaultValue(0).read("/n n friends"));
			}
		}
			 * *//*
			if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici")) {
			/*for(Object[] friends:fList)
			{
				friendsadress.add( (PeerAddress) friends[1]);
			}*/
				/*for (int i=0;i<fList.size();i++) {
					System.out.println("friends n "+i+" "+fList.get(i)[0]);
				}
				}
			while(textIO.newBooleanInputReader().withDefaultValue(false).read("\n vuoi aggiungere amici al gruppo?\n")) {
				friendsadress.add((PeerAddress) fList.get(textIO.newIntInputReader().withDefaultValue(0).read("/n n friends"))[1]);
				}*/
			System.out.println("vuoi conoscere la lista di amici");
			if(keyboard.nextBoolean()) {
			/*for(Object[] friends:fList)
			{
				friendsadress.add( (PeerAddress) friends[1]);
			}*/
				textIO.newBooleanInputReader().withDefaultValue(keyboard.nextBoolean());
				for (int i=0;i<fList.size();i++) {
					System.out.println("friends n "+i+" "+fList.get(i)[0]);
				}
				}
			System.out.println("vuoi conoscere la lista di amici");
			while(keyboard.nextBoolean()) {
				System.out.println("friends");
				friendsadress.add((PeerAddress) fList.get(keyboard.nextInt())[1]);
				System.out.println("vuoi conoscere la lista di amici");
				}
			return createGroupChat("gruppo", friendsadress);
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
	  public boolean groupChat2(String name, ArrayList<Integer> friends,ArrayList<String> nickFriends) {
			/*ArrayList<PeerAddress> peerfreinds=new ArrayList<PeerAddress>();
			System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAA friends"+friendList.size());
			for (int i=0;i<friendList.size();i++) {
				System.out.println("peer ="+friendList.get(i)[0]+" peeradress" +friendList.get(i)[1]);
				peerfreinds.add((PeerAddress) friendList.get(i)[1]);
			}
			createGroupChat("gruppo", peerfreinds);*/
			
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
		 //System.out.println("failure");
		if(futureGet.isSuccess()) {
		try {
			ArrayList<Object[]> fList=(ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
			ArrayList<PeerAddress> friendsadress =new ArrayList<PeerAddress>();
			TextIO textIO = TextIoFactory.getTextIO();
			//textIO=new TextIO(System.in);
			Scanner keyboard = new Scanner(System.in);
			//textIO.dispose(resultData);;
			/*
			 * if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici")) {
		ArrayList<String>friends= (ArrayList<String>) connector.getFriends();
		while (i<friends.size()) {
			System.out.println("friends n "+i+" "+friends.get(i));
			i++;
		}
		while(textIO.newBooleanInputReader().withDefaultValue(false).read("\n vuoi aggiungere amici al gruppo?\n")) {
			peerfreinds.add(textIO.newIntInputReader().withDefaultValue(0).read("/n n friends"));
			}
		}
			 * *//*
			if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici")) {
			/*for(Object[] friends:fList)
			{
				friendsadress.add( (PeerAddress) friends[1]);
			}*/
				/*for (int i=0;i<fList.size();i++) {
					System.out.println("friends n "+i+" "+fList.get(i)[0]);
				}
				}
			while(textIO.newBooleanInputReader().withDefaultValue(false).read("\n vuoi aggiungere amici al gruppo?\n")) {
				friendsadress.add((PeerAddress) fList.get(textIO.newIntInputReader().withDefaultValue(0).read("/n n friends"))[1]);
				}*/
			if(fList.size()<friends.size())
				return false;
			System.out.println("vuoi conoscere la lista di amici");
			for(int i=0;i<friends.size();i++) {
				//System.out.println(fList.get(friends.get(i))[0]+" "+(nickFriends.get(i)));
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
	  /*public boolean sendMessagebyid(String destination, String source,Object message) {

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
	    }*/
	  /*public boolean sendMessage2(int destination, String source,Object message) {

	    	FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
	        futureGet.awaitUninterruptibly();
	        App test;
	        try {
		        if (futureGet.isSuccess()) {
		        	ArrayList <Object[]> friends= (ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
		        	
		        	test=new App("prova", peerId,source);
					test.setMytype(App.type.chat);
					FutureDirect futureDirect = _dht.peer().sendDirect((PeerAddress) friends.get(destination)[1]).object(message).start();
					
					futureDirect.awaitListenersUninterruptibly();
					return true;
		        }}catch (Exception e) {
					// TODO: handle exception
				}
	    	return false;
	    }*/
	  public boolean sendMessage3(int destination, String source,Object message) {

	    	FutureGet futureGet = _dht.get(Number160.createHash("friendsList"+peerId)).start();
	        futureGet.awaitUninterruptibly();
	        App test;
	        try {
		        if (futureGet.isSuccess()) {
		        	ArrayList <Object[]> friends= (ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
		        	
		        	test=new App((String)message, peerId,source);
					test.setMytype(App.type.chat);
					if(friends.size()> destination) {
					if(friends.get(destination)[1]!=null) {
					FutureDirect futureDirect = _dht.peer().sendDirect((PeerAddress) friends.get(destination)[1]).object(test).start();
			
					futureDirect.awaitListenersUninterruptibly();
					}else {
						System.out.println("group messag pt 1");
						FutureGet futureGet2 = _dht.get(Number160.createHash((String)friends.get(destination)[0])).start();
				        futureGet2.awaitUninterruptibly();
				        if (futureGet2.isSuccess()) {
				        	test.setNickname((String) friends.get(destination)[0]);
				        	test.setText(source+ " :"+test.getText());
				        	System.out.println("group messag pt 2");
				        HashSet<PeerAddress> peers_on_topic;
						peers_on_topic = (HashSet<PeerAddress>) futureGet2.dataMap().values().iterator().next().object();
			        		for(PeerAddress peer:peers_on_topic)
						{
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
			/*List<String> question= getUserProfileQuestions();
	    	List <Integer> answer =new ArrayList<>();
	    	for (int i=0; i<question.size();i++) {
	    		answer.add(Integer.parseInt(question.get(i)));
	    	}
	    	String key= createAuserProfileKey(answer);
	    	//friendList.clear();
	    	_dht.remove(Number160.createHash("friendsList"+peerId));
	    	try {
			return	searchFriends2b("test", nickName, key);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return false;*/
		  try {
			  FutureGet futureGet = _dht.get(Number160.createHash("userList")).start();
				futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
					 @Override
					 public void operationComplete(FutureGet future) throws Exception {
					  if(future.isSuccess()) { // this flag indicates if the future was successful
					  // System.out.println("success");
					   
					  } else {
					  // System.out.println("failure");
					  }
					 }
					}).awaitListenersUninterruptibly();
				//futureGet.awaitUninterruptibly();
				 //System.out.println("failure");
				if(futureGet.isSuccess()){
					try {
						ArrayList <Object[]> userList= (ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
						for (int k=0;k<userList.size();k++) {
							if (userList.get(k)[0].equals(nickName)&&userList.get(k)[2].equals(_dht.peer().peerAddress())) {
								userList.get(k)[1]=key;
								k=userList.size();
							}
							_dht.put(Number160.createHash("userList"))
							.data(new Data(userList)).start().awaitListenersUninterruptibly();
						}
						
					} catch (ClassNotFoundException e) {
					
						e.printStackTrace();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					//Object [] me= {_nick_name,_profile_key ,_dht.peer().peerAddress()};
				}
			  FutureGet futureGet2 = _dht.get(Number160.createHash("friendsList"+peerId)).start();
		        futureGet2.awaitUninterruptibly();
		        if (futureGet2.isSuccess()&&!futureGet2.isEmpty()) {
		        	App test;
		        	ArrayList <Object[]> friends= (ArrayList<Object[]>) futureGet2.dataMap().values().iterator().next().object();
		        	
		        	test=new App("exit", peerId,nickName);
					test.setMytype(App.type.friends);
					for (int i=0;i<friends.size();i++) {
						if (friends.get(i)[1]!=null) {
					FutureDirect futureDirect = _dht.peer().sendDirect((PeerAddress) friends.get(i)[1]).object(test).start();
			
					futureDirect.awaitListenersUninterruptibly();
						}
					}
					
					}
			  _dht.remove(Number160.createHash("friendsList"+peerId));
			return searchFriends2c( nickName, key);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return false;
		}
	  public boolean leaveNetwork(String nickname) {
		  FutureGet futureGet = _dht.get(Number160.createHash("userList")).start();
			futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
				 @Override
				 public void operationComplete(FutureGet future) throws Exception {
				  if(future.isSuccess()) { // this flag indicates if the future was successful
				  // System.out.println("success");
				   
				  } else {
				  // System.out.println("failure");
				  }
				 }
				}).awaitListenersUninterruptibly();
			//futureGet.awaitUninterruptibly();
			 //System.out.println("failure");
			if(futureGet.isSuccess()){
				try {
					ArrayList <Object[]> userList= (ArrayList<Object[]>) futureGet.dataMap().values().iterator().next().object();
					for (int k=0;k<userList.size();k++) {
						if (userList.get(k)[0].equals(nickname)&&userList.get(k)[2].equals(_dht.peer().peerAddress())) {
							userList.remove(k);
							k=userList.size();
						}
						_dht.put(Number160.createHash("userList"))
						.data(new Data(userList)).start().awaitListenersUninterruptibly();
					}
					
				} catch (ClassNotFoundException e) {
				
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				//Object [] me= {_nick_name,_profile_key ,_dht.peer().peerAddress()};
			}
		  FutureGet futureGet2 = _dht.get(Number160.createHash("friendsList"+peerId)).start();
	        futureGet2.awaitUninterruptibly();
	        App test;
	        try {
		        if (futureGet2.isSuccess()) {
		        	ArrayList <Object[]> friends= (ArrayList<Object[]>) futureGet2.dataMap().values().iterator().next().object();
		        	
		        	test=new App("exit99999999", peerId,nickname);
					test.setMytype(App.type.friends);
					for (int i=0;i<friends.size();i++) {
						if (friends.get(i)[1]!=null) {
					FutureDirect futureDirect = _dht.peer().sendDirect((PeerAddress) friends.get(i)[1]).object(test).start();
			
					futureDirect.awaitListenersUninterruptibly();
						}
					}
					
					}
		        _dht.peer().announceShutdown().start().awaitUninterruptibly();
					return true;
		        }catch (Exception e) {
					// TODO: handle exception
				}
	    	return false;
			
			
			
		}
	  public List<String> createQuestion() {
		  FutureGet futureGet = _dht.get(Number160.createHash("question")).start();
	        futureGet.awaitUninterruptibly();
	        App test;
	      
		        if (futureGet.isSuccess()) {
		        	if(futureGet.isEmpty()) {
		        		ArrayList<String> questions=new ArrayList<String>();
		        		questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");
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
		        	}
		        }
		        return null;
	  }
	  public boolean addSpam(ArrayList<String> profile) throws IOException {
		  App test;
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash("spamList"+peerId)).start();
				futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
					 @Override
					 public void operationComplete(FutureGet future) throws Exception {
					  /*if(future.isSuccess()) { // this flag indicates if the future was successful
					  
						  System.out.println("success");
					   
					  } else {
					   System.out.println("failure");
					  }*/
					 }
					}).awaitListenersUninterruptibly();
				//futureGet.awaitUninterruptibly();
				if (futureGet.isSuccess()) {
					ArrayList<String> oldList= new ArrayList<String>();
					if(!futureGet.isEmpty()) {
					 oldList=(ArrayList<String>) futureGet.dataMap().values().iterator().next().object();
					//ArrayList<Object[]> oldList=_dht.get(Number160.createHash("friendsList")).;
					/* FuturePut future = _dht.put(Number160.createHash(profile))
	                         .data(new Data(name)).start().awaitUninterruptibly();*/
					
					}
					for(String nick:profile) {
					oldList.add(nick);
					}
					_dht.put(Number160.createHash("spamList"+peerId))
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
	  public boolean removeSpam(ArrayList<String> profile) throws IOException {
		  try {
				FutureGet futureGet = _dht.get(Number160.createHash("spamList"+peerId)).start();
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
					ArrayList<String> oldList=(ArrayList<String>) futureGet.dataMap().values().iterator().next().object();
					//ArrayList<Object[]> oldList=_dht.get(Number160.createHash("friendsList")).;
					/* FuturePut future = _dht.put(Number160.createHash(profile))
	                         .data(new Data(name)).start().awaitUninterruptibly();*/
					
					
					/*int i=0;
					while(!oldList.get(i)[0].equals(newfriends[0]))
						i++*/;
					for(String nick:profile) {
						oldList.remove(nick);
					}_dht.put(Number160.createHash("spamList"+peerId))
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
		public List<String> getSpamList() {
			FutureGet futureGet = _dht.get(Number160.createHash("spamList"+peerId)).start();
			futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
				 @Override
				 public void operationComplete(FutureGet future) throws Exception {
				  if(future.isSuccess()) { // this flag indicates if the future was successful
				  // System.out.println("success");
				   
				  } else {
				  // System.out.println("failure");
				  }
				 }
				}).awaitListenersUninterruptibly();
			//futureGet.awaitUninterruptibly();
			 //System.out.println("failure");
			if(futureGet.isSuccess()&&!futureGet.isEmpty()) {
			try {
				ArrayList<String> sList=(ArrayList<String>) futureGet.dataMap().values().iterator().next().object();
				ArrayList<String> friendsName =new ArrayList<String>();
				// System.out.println(peerId+" get friends +"+fList.size());
				for(String friends:sList)
				{
					// System.out.println(peerId+" add friends "+friends[0]);
					friendsName.add( friends);
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
			//	 System.out.println("non esiste");
			}
			return new ArrayList<String>();
		}
	  public List<String> getUserProfileQuestions2() {
		  FutureGet futureGet = _dht.get(Number160.createHash("question")).start();
	        futureGet.awaitUninterruptibly();
	        App test;
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
	  
}
