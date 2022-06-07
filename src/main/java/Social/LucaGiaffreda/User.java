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
private List <Message> spamMessages;
public User(String nickname,int id, String adress) {
	this.nickname = nickname;
	
	this.Id = id;
	try {
		
		
		
		class MessageListenerImpl implements MessageListener{
 			int peerid;
 			
 			public MessageListenerImpl(int peerid)
 			{
 				this.peerid=peerid;
 				
 			}
 			public Object parseMessage(PeerAddress sender, Object obj) {
 				
 				TextIO textIO = TextIoFactory.getTextIO();
 				TextTerminal terminal = textIO.getTextTerminal();
 				;
 					
 					
 						Message a = (Message) obj;
 						try {
 						if(a.getMytype()==Message.type.friends) {
 							if(a.getText().equals("exit"))
 								connector.removeFriends(a.getNickname(), sender);
 							else
 								connector.setFriends(nickname, a.getNickname(),sender);
 			 				
						
						}else if(a.getMytype()==Message.type.chat){
							ArrayList<String> spam=(ArrayList<String>) connector.getSpamList();
							
							if(spam.contains(a.getNickname())) {
								addSpamMessages(a);
								
							}else {
								terminal.printf("\n"+peerid+"] (Message Received by "+a.getNickname()+" ) message = "+a.getText()+"\n\n");
								
							}
							}else if(a.getMytype()==Message.type.multichat){
								
								connector.getmultichat(nickname, a.getNickname());
								} else {
									System.out.println(nickname+ "pre add "+ a.getNickname());
									connector.addFriends(a.getNickname(), sender);
									System.out.println(nickname+ "add "+ a.getNickname());
									}
 						
 			}
 			 catch (IOException e) {
			
				e.printStackTrace();
			}
						return "success";
 			}
		}
		connector =new SemanticHarmonySocialNetworkImpl(id, adress, new MessageListenerImpl(id));
		spamMessages=new ArrayList<Message>();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
public boolean connect() {
	List<String>question = connector.getUserProfileQuestions();
	List <Integer> answer=new ArrayList<Integer>();
	TextIO textIO = TextIoFactory.getTextIO();
	for(int i=0;i<question.size();i++){
	answer.add( textIO.newIntInputReader().withMaxVal(3).withMinVal(0).read("Valuta da 0 a 3 "+question.get(i)));
	}
	setProfile_key( connector.createAuserProfileKey(answer));
	return connector.join(profile_key, nickname);

}
public boolean message() {
	TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal terminal = textIO.getTextTerminal();
	int i=0;
	
	if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici")) {
	ArrayList<String>friends= (ArrayList<String>) connector.getFriends();
	if(friends!=null)
	while (i<friends.size()) {
		System.out.println("friends n "+i+" "+friends.get(i));
		i++;
	}
	}
	int destination=textIO.newIntInputReader().withDefaultValue(0).read("/n n friends");
	String message=textIO.newStringInputReader().withDefaultValue("default").read("message");
	return connector.sendMessage(destination, nickname, message);
	}

public boolean groupChat2() {
	ArrayList<Integer> peerfreinds=new ArrayList<Integer>();
	ArrayList<String> nickfreinds=new ArrayList<String>();
		
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
	List<String> question= new ArrayList<String>();
	question=connector.getUserProfileQuestions();
		TextIO textIO = TextIoFactory.getTextIO();

	List <Integer> answer =new ArrayList<>();
	
	for (int i=0; i<question.size();i++) {
		answer.add(textIO.newIntInputReader().withMaxVal(3).withMinVal(0).read("Valuta da 0 a 3 "+question.get(i)));
	}
	String key= connector.createAuserProfileKey(answer);
	
	return connector.changeKey(nickname, key);
}
public boolean addSpam() throws IOException {
	ArrayList<String> nickfreinds=new ArrayList<String>();
	TextIO textIO = TextIoFactory.getTextIO();
	int i=0;
	ArrayList<String>friends= (ArrayList<String>) connector.getFriends();
	if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici")) {
		
		if(friends!=null)
		while (i<friends.size()) {
			System.out.println("friends n "+i+" "+friends.get(i));
			i++;
		}
	}
	if(friends!=null)
		while(textIO.newBooleanInputReader().withDefaultValue(false).read("\n vuoi aggiungere amici agli spam?\n")) {
			int choise=textIO.newIntInputReader().withDefaultValue(0).read("/n n friends");
			nickfreinds.add(friends.get(choise));
			}
		
		
	return connector.addSpam(nickfreinds);
}
public boolean removeSpam() throws IOException {
	ArrayList<String> nickfreinds=new ArrayList<String>();
	TextIO textIO = TextIoFactory.getTextIO();
	int i=0;
	ArrayList<String>friends= (ArrayList<String>) connector.getSpamList();
	if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi conoscere la lista di amici in spam")) {
		if(friends!=null) {
		while (i<friends.size()) {
			System.out.println("friends n "+i+" "+friends.get(i));
			i++;
		}
		}
		if(friends!=null)
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
public void getFriendsList() {
	TextIO textIO = TextIoFactory.getTextIO();
	int i=0;
		ArrayList<String>friends= (ArrayList<String>) connector.getFriends();
		while (i<friends.size()) {
			System.out.println("friends n "+i+" "+friends.get(i));
			i++;
		}
		
	//return connector.getFriends();
}
public void setFriendsList(List <Object[]> freindsList) {
	this.friendsList = freindsList;
}
public SemanticHarmonySocialNetworkImpl getConnector() {
	return connector;
}

public boolean exit() {
	return connector.leaveNetwork(nickname);
}
public void getSpamMessages() {
	while (spamMessages.size()>0) {
		System.out.println("Messaggio spam =" +spamMessages.get(0));
		spamMessages.remove(0);
		
	}
}
public void addSpamMessages(Message spamMessages) {
	if(this.spamMessages.size()>=10)
		this.spamMessages.remove(0);
	this.spamMessages.add( spamMessages);
}
}
