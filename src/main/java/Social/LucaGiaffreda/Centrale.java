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

public static void main (String[] args)throws NumberFormatException, Exception {
	// System.out.println("twst");
		 
	 //aggiunte
	 Centrale example=new Centrale();
	 final CmdLineParser parser = new CmdLineParser(example);  
	 parser.parseArgument(args);
	 TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal terminal = textIO.getTextTerminal();
	
    System.out.println(id+" "+adress);
    String nick=textIO.newStringInputReader().withDefaultValue("default").read("inserisci nick");
	
	 User user=new User(nick, id,adress);
	 user.connect();
	while(true) {
		System.out.println("1 = esci");
		System.out.println("2 = invia messaggio");
		System.out.println("3 = chat di gruppo");
		System.out.println("4 = cambia chiave");
		System.out.println("5 = vedi spam friends");
		System.out.println("6 = aggiungi amici in spam");
		System.out.println("7 = rimuovi amici in spam");
		System.out.println("8 = vedi spam message");
		System.out.println("9 = vedi lista amici");
		int option = textIO.newIntInputReader()
				.withMaxVal(9)
				.withMinVal(1)
				.read("Option");
		switch (option) {
		case 1:
			//ArrayList<String> friendsList=(ArrayList<String>)
			user.getFriendsList();
			/*for(int i=0; i<friendsList.size();i++)
				 System.out.println(friendsList.get(i));*/
			user.exit();
				System.exit(0);
				break;
		case 2:
			user.message();
			break;
		case 3:
			user.groupChat();
			break;
		case 4:
			user.changeKey( );
			break;
		case 5:
			user.mostraSpam();
			break;
		case 6:
			user.addSpam();
			break;
		case 7:
			user.removeSpam();
			break;
		case 8:
			user.getSpamMessages();
			break;
		case 9:
			user.getFriendsList();
			break;
		
		default:
			break;
		}
	}

}
}
