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
private List <App> spamMessages;
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
 				//System.out.println("\n"+peerid+"] (Direct Message Received) "+obj+"\n\n");
 				
 					
 						App a = (App) obj;
 						try {
 						if(a.getMytype()==App.type.friends) {
 						//if(hammingDistance(a.getText(), profile_key)<2) {
 							
							//Object newFriends[]= {a.getNickname(),sender};
							//ArrayList<String>Friend=(ArrayList<String>) connector.getFriends();
 			 					//if(!friendsList.contains(newFriends))
 			 					//{
								//if(!Friend.contains(a.getNickname())) {
 			 					//terminal.printf("\n"+peerid+" invia response amico con i dati che ha i dati"+a+"con indirizzo"+a.getAdress()+"sender ="+sender+"\n\n");
								
							//System.out.println("\n"+peerid+" risultato getfreinds"+connector.getFriends5c(nickname, a.getNickname(),sender)+"\n\n");
							connector.getFriends5c(nickname, a.getNickname(),sender);
 			 					//friendsList.add(newFriends);
 			 					/*}else {
 			 						terminal.printf("\n gia' amici"+"\n\n");
 			 						connector.reFriends(sender, nickname);
 			 						
 			 					}*/
 			 				/*}else {
 			 					
 			 					//Object newFriends[]= {a.getNickname(),sender};
 			 					//for (int i=0;i<friendsList.size();i++) {
 			 					ArrayList<String>Friend=(ArrayList<String>) connector.getFriends();
 			 						if (Friend.contains(a.getNickname())) {
 			 							terminal.printf("\n"+peerid+" rimosso amico con i dati che ha i dati"+a+"\n\n");
 			 	 			 			
 		 			 					connector.removeFriends(a.getNickname(), sender, Friend.indexOf(a.getNickname()));
 			 						}
 			 					//}
 			 				
 			 						System.out.println("\n"+peerid+" amicizia non corrisposta"+a+"\n\n");
 			 				}*/
						
						}else if(a.getMytype()==App.type.chat){
							ArrayList<String> spam=(ArrayList<String>) connector.getSpamList();
							for (String s:spam )
								System.out.println(" in spam list ="+s);
							if(spam.contains(a.getNickname())) {
								addSpamMessages(a);
								
							}else {
								terminal.printf("\n"+peerid+"] (Direct Message Received by "+a.getNickname()+" ) message =="+a.getText()+"\n\n");
								
							}
							}else if(a.getMytype()==App.type.multichat){
								
								//System.out.println("\n"+peerid+"] (Direct Message Received) message"+connector.getmultichat(nickname, a.getNickname())+"\n\n");
								connector.getmultichat(nickname, a.getNickname());
								} else {
									//System.out.println("\n"+peerid+" aggiunge un nuovo amico che ha i dati"+a+connector.addFriends(a.getNickname(), sender)+" con risultato"+"\n\n");
									connector.addFriends(a.getNickname(), sender);
									//	Object newFriends[]= {a.getNickname(),sender};
								//connector.addFriends(a.getNickname(), sender);
								//friendsList.add(newFriends);
								}
 						
 			}
 			 catch (IOException e) {
			
				e.printStackTrace();
			}
						return "success";
 			}
		}
		connector =new SemanticHarmonySocialNetworkImpl(id, adress, new MessageListenerImpl(id));
		spamMessages=new ArrayList<App>();
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
	return connector.join2(profile_key, nickname);
	//return false;
}
public boolean message() {
	TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal terminal = textIO.getTextTerminal();
	//String destination=textIO.newStringInputReader().withDefaultValue("default").read("destination");
    //int destination=textIO.newIntInputReader().withDefaultValue(0).read("id destinazione");
	int i=0;
	/*while(!destination.equals(friendList.get(i)[0])) {
		i++;
	}*/
	if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici")) {
	ArrayList<String>friends= (ArrayList<String>) connector.getFriends();
	while (i<friends.size()) {
		System.out.println("friends n "+i+" "+friends.get(i));
		i++;
	}
	/*int destination=textIO.newIntInputReader().withDefaultValue(0).read("/n n friends");
	String message=textIO.newStringInputReader().withDefaultValue("default").read("message");
	return connector.sendMessage3(destination, nickname, message);*/
	}
	int destination=textIO.newIntInputReader().withDefaultValue(0).read("/n n friends");
	String message=textIO.newStringInputReader().withDefaultValue("default").read("message");
	//String nick=textIO.newStringInputReader().withDefaultValue("default").read("nick");
	return connector.sendMessage3(destination, nickname, message);
	//return connector.sendMessagebyid(destination, nickname, message);
}
public boolean groupChat() {
	ArrayList<Integer> peerfreinds=new ArrayList<Integer>();
	//System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAA friends"+friendsList.size());
	/*for (int i=0;i<friendsList.size();i++) {
		System.out.println("peer ="+friendsList.get(i)[0]+" peeradress" +friendsList.get(i)[1]);
		peerfreinds.add((PeerAddress) friendsList.get(i)[1]);
	}*/	
	TextIO textIO = TextIoFactory.getTextIO();
	/*int i=0;
	if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici")) {
		ArrayList<String>friends= (ArrayList<String>) connector.getFriends();
		while (i<friends.size()) {
			System.out.println("friends n "+i+" "+friends.get(i));
			i++;
		}
		while(textIO.newBooleanInputReader().withDefaultValue(false).read("\n vuoi aggiungere amici al gruppo?\n")) {
			peerfreinds.add(textIO.newIntInputReader().withDefaultValue(0).read("/n n friends"));
			}
		}*/
	return connector.groupChat();
}
public boolean groupChat2() {
	ArrayList<Integer> peerfreinds=new ArrayList<Integer>();
	ArrayList<String> nickfreinds=new ArrayList<String>();
	//System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAA friends"+friendsList.size());
	/*for (int i=0;i<friendsList.size();i++) {
		System.out.println("peer ="+friendsList.get(i)[0]+" peeradress" +friendsList.get(i)[1]);
		peerfreinds.add((PeerAddress) friendsList.get(i)[1]);
	}*/	
	TextIO textIO = TextIoFactory.getTextIO();
	int i=0;
	if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici")) {
		ArrayList<String>friends= (ArrayList<String>) connector.getFriends();
		while (i<friends.size()) {
			System.out.println("friends n "+i+" "+friends.get(i));
			i++;
		}
		while(textIO.newBooleanInputReader().withDefaultValue(false).read("\n vuoi aggiungere amici al gruppo?\n")) {
			int choise=textIO.newIntInputReader().withDefaultValue(0).read("/n n friends");
			peerfreinds.add(choise);
			nickfreinds.add(friends.get(choise));
			}
		}
	String name=textIO.newStringInputReader().withDefaultValue("gruppo").read("Inserisci nome gruppo");
	return connector.groupChat2(name,peerfreinds,nickfreinds);
}
public boolean changeKey() {
	List<String> question= connector.getUserProfileQuestions();
		TextIO textIO = TextIoFactory.getTextIO();

	List <Integer> answer =new ArrayList<>();
	for (int i=0; i<question.size();i++) {
		answer.add(textIO.newIntInputReader().withMaxVal(1).withMinVal(0).read(question.get(i)));
	}
	String key= connector.createAuserProfileKey(answer);
	
	//connector.searchFriends2b("test", nickname, key);
	return connector.changeKey(nickname, key);
}
public boolean addSpam() throws IOException {
	ArrayList<String> nickfreinds=new ArrayList<String>();
	TextIO textIO = TextIoFactory.getTextIO();
	int i=0;
	if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici")) {
		ArrayList<String>friends= (ArrayList<String>) connector.getFriends();
		while (i<friends.size()) {
			System.out.println("friends n "+i+" "+friends.get(i));
			i++;
		}
		while(textIO.newBooleanInputReader().withDefaultValue(false).read("\n vuoi aggiungere amici agli spam?\n")) {
			int choise=textIO.newIntInputReader().withDefaultValue(0).read("/n n friends");
			nickfreinds.add(friends.get(choise));
			}
		
		}
	return connector.addSpam(nickfreinds);
}
public boolean removeSpam() throws IOException {
	ArrayList<String> nickfreinds=new ArrayList<String>();
	TextIO textIO = TextIoFactory.getTextIO();
	int i=0;
	if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici in spam")) {
		ArrayList<String>friends= (ArrayList<String>) connector.getSpamList();
		if(friends!=null)
		while (i<friends.size()) {
			System.out.println("friends n "+i+" "+friends.get(i));
			i++;
		}
		while(textIO.newBooleanInputReader().withDefaultValue(false).read("\n vuoi rimuovere amici agli spam?\n")) {
			int choise=textIO.newIntInputReader().withDefaultValue(0).read("/n n friends");
			nickfreinds.add(friends.get(choise));
			}
		
		}
	return connector.removeSpam(nickfreinds);
}
public boolean mostraSpam() throws IOException {
	ArrayList<String> nickfreinds=new ArrayList<String>();
	TextIO textIO = TextIoFactory.getTextIO();
	int i=0;
		ArrayList<String>friends= (ArrayList<String>) connector.getSpamList();
		while (i<friends.size()) {
			System.out.println("friends n "+i+" "+friends.get(i));
			i++;
		}
	
	return true;
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
public List getFriendsList() {
	TextIO textIO = TextIoFactory.getTextIO();
	int i=0;
	//if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici")) {
		ArrayList<String>friends= (ArrayList<String>) connector.getFriends();
		while (i<friends.size()) {
			System.out.println("friends n "+i+" "+friends.get(i));
			i++;
		}
		
	return connector.getFriends();
}
public void setFriendsList(List <Object[]> freindsList) {
	this.friendsList = freindsList;
}
public SemanticHarmonySocialNetworkImpl getConnector() {
	return connector;
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
	//System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBB"+count);
	return count;
}
public boolean exit() {
	return connector.leaveNetwork(nickname);
}
public void getSpamMessages() {
	/*List <App> appoggio=spamMessages;
	spamMessages.clear();
	return appoggio;*/
	while (spamMessages.size()>0) {
		System.out.println("Messaggio spam =" +spamMessages.get(0));
		spamMessages.remove(0);
		
	}
}
public void addSpamMessages(App spamMessages) {
	if(this.spamMessages.size()>=10)
		this.spamMessages.remove(0);
	this.spamMessages.add( spamMessages);
}
}
