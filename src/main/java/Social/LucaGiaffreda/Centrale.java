package Social.LucaGiaffreda;

import java.util.ArrayList;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;


public class Centrale {
	@Option(name="-a", aliases="--adress", required=true)
	private static String adress;

	@Option(name="-id", aliases="--identifierpeer", required=true)
	private static int id;
	/*@Option(name="-m", aliases="--masterip", usage="the master peer ip address", required=true)
	private static String master;

	@Option(name="-id", aliases="--identifierpeer", usage="the unique identifier for this peer", required=true)
	private static int id;*/
public static void main (String[] args)throws NumberFormatException, Exception {
	 System.out.println("twst");
	/* class MessageListenerImpl implements MessageListener{
		int peerid;
	
		public MessageListenerImpl(int peerid)
		{
			this.peerid=peerid;

		}
		public Object parseMessage(Object obj) {
			
			TextIO textIO = TextIoFactory.getTextIO();
			TextTerminal terminal = textIO.getTextTerminal();
			terminal.printf("\n"+peerid+"] (Direct Message Received) "+obj+"\n\n");
			return "success";
		}

	}*/
	 
	 //aggiunte
	 Centrale example=new Centrale();
	 final CmdLineParser parser = new CmdLineParser(example);  
	 parser.parseArgument(args);
	 TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal terminal = textIO.getTextTerminal();
	// SemanticHarmonySocialNetworkImpl ex= new SemanticHarmonySocialNetworkImpl(Integer.parseInt(args[0]), args[1]);
	//List<String> question= ex.getUserProfileQuestions();
	//List <Integer> answer =new ArrayList<>();
	
	/*for (int i=0; i<question.size();i++) {
		answer.add(Integer.parseInt(question.get(i)));
	}*/
	//String key= ex.createAuserProfileKey(answer);
    System.out.println(id+" "+adress);
    String nick=textIO.newStringInputReader().withDefaultValue("default").read("inserisci nick");
	
	 User user=new User(nick, id,adress);
	//ex.join(key,nick );
	//System.out.println(ex.peerId);
	 user.connect();
	while(true) {
		System.out.println("1 = exit");
		System.out.println("2 = message");
		System.out.println("3 = group chat");
		System.out.println("4 = change key");
		int option = textIO.newIntInputReader()
				.withMaxVal(4)
				.withMinVal(1)
				.read("Option");
		switch (option) {
		case 1:
			ArrayList<String> friendsList=(ArrayList<String>) user.getFriendsList();
			for(int i=0; i<friendsList.size();i++)
				 System.out.println("twst"+friendsList.get(i));
				System.exit(0);
				break;
		case 2:
			user.message();
			break;
		case 3:
			user.groupChat2();
			break;
		case 4:
			user.changeKey( );
			break;
		default:
			break;
		}
	}
	/*while(true) {
		if(textIO.newBooleanInputReader().withDefaultValue(false).read("exit?")) {
			for(int i=0; i<ex.getFriends().size();i++)
			 System.out.println("twst"+ex.getFriends().get(i));
			System.exit(0);
		}else if(textIO.newBooleanInputReader().withDefaultValue(false).read("send?")){
			ex.message(nick);
		}else if(textIO.newBooleanInputReader().withDefaultValue(false).read("create group chat?")){
			ex.groupChat();
		}
		}*/
	 // BasicConfigurator.configure();
   /*ExampleSimple dns = new ExampleSimple(Integer.parseInt(args[0]),args[2],new MessageListenerImpl(Integer.parseInt(args[0])));
   TextIO textIO = TextIoFactory.getTextIO();
   TextTerminal terminal = textIO.getTextTerminal();;
   dns.setAnswer(textIO.newStringInputReader().withDefaultValue("default").read("risposte"));
   if (args.length == 4) {
       dns.store(args[1], args[2]);
   }
   if (args.length == 3) {
       System.out.println("Name:" + args[1] + " IP:" + dns.get(args[1]));
   }
   while(true) {
		terminal.printf("wait");
		if(textIO.newBooleanInputReader().withDefaultValue(false).read("exit?")) {
			System.exit(0);
		}else {
		
			if(textIO.newBooleanInputReader().withDefaultValue(false).read("cerca?")) {
				dns.searchFriends(args[1], args[0]);
			}
			else{
				if(textIO.newBooleanInputReader().withDefaultValue(false).read("eccetta?")) {
					//dns.getFriends(args[0], textIO.newStringInputReader().withDefaultValue("default").read("Messaggio da inviare"));
					dns.FriendsbyId(args[0], textIO.newStringInputReader().withDefaultValue("default").read("profilo"), textIO.newStringInputReader().withDefaultValue("default").read("risposte"));
				}
				else {
					if(textIO.newBooleanInputReader().withDefaultValue(false).read("vuoi inviare un messaggio?")) {
						String message=textIO.newStringInputReader().withDefaultValue("default").read("Messaggio da inviare");
						String name=textIO.newStringInputReader().withDefaultValue("default").read("a chi?");
						dns.sendMessage(name, message);
			}
			}
			}
		}
		}*/
}
}
