package Social.LucaGiaffreda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;


import net.tomp2p.peers.PeerAddress;

public class User {
private String nickname;
private String profile_key;
private List <Object[]> friendsList;
private int Id;
private SemanticHarmonySocialNetworkImpl connector;
public User(String nickname,int id, String adress) {
	this.nickname = nickname;
	
	this.Id = id;
	try {
		
		
		
		class MessageListenerImpl implements MessageListener{
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
 				
 					
 						App a = (App) obj;
 						try {
 						if(a.getMytype()==App.type.friends) {
 						if(connector.hammingDistance(a.getText(), profile_key)<2) {
							Object newFriends[]= {a.getNickname(),a.getAdress()};
 			 					//if(!friendsList.contains(newFriends))
 			 					//{
 			 					terminal.printf("\n"+peerid+" invia response amico con i dati che ha i dati"+a+"con indirizzo"+a.getAdress()+"sender ="+sender+"\n\n");
 			 					terminal.printf("\n"+peerid+" risultati getfreinds"+connector.getFriends5b(nickname, a.getNickname(),sender)+"\n\n");
						
 			 					friendsList.add(newFriends);
 			 					//}
 			 				}else {
 			 					Object newFriends[]= {a.getNickname(),a.getAdress()};
 			 					for (int i=0;i<friendsList.size();i++) {
 			 						if (friendsList.get(i)[0].equals(newFriends[0])) {
 			 							terminal.printf("\n"+peerid+" rimosso amico con i dati che ha i dati"+a+"\n\n");
 			 	 			 			
 		 			 					friendsList.remove(newFriends);
 			 						}
 			 					}
 			 				
 			 					terminal.printf("\n"+peerid+" amicizia non corrisposta"+a+"\n\n");
 			 				}
						
						}else if(a.getMytype()==App.type.chat){
							terminal.printf("\n"+peerid+"] (Direct Message Received) message"+a.getText()+"\n\n");
							}else if(a.getMytype()==App.type.multichat){
								
									terminal.printf("\n"+peerid+"] (Direct Message Received) message"+connector.getmultichat(nickname, a.getNickname())+"\n\n");
								
								} else {
								terminal.printf("\n"+peerid+" aggiunge un nuovo amico che ha i dati"+a+"\n\n");
								Object newFriends[]= {a.getNickname(),a.getAdress()};
								friendsList.add(newFriends);
								}
 			}
 			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						return "success";
 			}
		}
		connector =new SemanticHarmonySocialNetworkImpl(id, adress, new MessageListenerImpl(id));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public boolean connect() {
	List<String>question = connector.getUserProfileQuestions();
	List <Integer> answer=new ArrayList<Integer>();
	TextIO textIO = TextIoFactory.getTextIO();
	//TextTerminal terminal = textIO.getTextTerminal();
	for(int i=0;i<question.size();i++){
	answer.add( textIO.newIntInputReader().withMaxVal(1).withMinVal(0).read(question.get(i)));
	}
	setProfile_key( connector.createAuserProfileKey(answer));
	return connector.join(profile_key, nickname);
	//return false;
}
public boolean message() {
	TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal terminal = textIO.getTextTerminal();
	String destination=textIO.newStringInputReader().withDefaultValue("default").read("destination");
    //int destination=textIO.newIntInputReader().withDefaultValue(0).read("id destinazione");
	int i=0;
	/*while(!destination.equals(friendList.get(i)[0])) {
		i++;
	}*/
	String message=textIO.newStringInputReader().withDefaultValue("default").read("message");
	//con.sendMessage2((PeerAddress) friendList.get(i)[1], nick, message);
	return connector.sendMessagebyid(destination, nickname, message);
}
public void groupChat() {
	ArrayList<PeerAddress> peerfreinds=new ArrayList<PeerAddress>();
	System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAA friends"+friendsList.size());
	for (int i=0;i<friendsList.size();i++) {
		System.out.println("peer ="+friendsList.get(i)[0]+" peeradress" +friendsList.get(i)[1]);
		peerfreinds.add((PeerAddress) friendsList.get(i)[1]);
	}
	connector.createGroupChat("gruppo", peerfreinds);
}
public void changeKey() {
	List<String> question= connector.getUserProfileQuestions();
	List <Integer> answer =new ArrayList<>();
	for (int i=0; i<question.size();i++) {
		answer.add(Integer.parseInt(question.get(i)));
	}
	String key= connector.createAuserProfileKey(answer);
	friendsList.clear();
	try {
		connector.searchFriends2b("test", nickname, key);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public String getNickname() {
	return nickname;
}
public void setNickname(String nickname) {
	this.nickname = nickname;
}
public String getProfile_key() {
	return profile_key;
}
public void setProfile_key(String profile_key) {
	this.profile_key = profile_key;
}

public int getId() {
	return Id;
}
public void setId(int id) {
	this.Id = id;
}
public List <Object[]> getFriendsList() {
	return friendsList;
}
public void setFriendsList(List <Object[]> freindsList) {
	this.friendsList = freindsList;
}
}
