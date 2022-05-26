package Social.LucaGiaffreda;



import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.junit.jupiter.api.*;
import Social.LucaGiaffreda.User;
import Social.LucaGiaffreda.App;
import Social.LucaGiaffreda.SemanticHarmonySocialNetworkImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest 
  
{
	/*public static class IntegerAsker {
	    private final Scanner scanner;
	    private final PrintStream out;

	    public IntegerAsker(InputStream in, PrintStream out) {
	        scanner = new Scanner(in);
	        this.out = out;
	    }

	    public int ask(String message) {
	        out.println(message);
	        return scanner.nextInt();
	    }
	}*/
	/* private final InputStream systemIn = System.in;
	    private final PrintStream systemOut = System.out;

	    private ByteArrayInputStream testIn;
	    private ByteArrayOutputStream testOut;

	    @Before
	    public void setUpOutput() {
	        testOut = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(testOut));
	    }

	    private void provideInput(String data) {
	        testIn = new ByteArrayInputStream(data.getBytes());
	        System.setIn(testIn);
	    }

	    private String getOutput() {
	        return testOut.toString();
	    }

	    @After
	    public void restoreSystemInputOutput() {
	        System.setIn(systemIn);
	        System.setOut(systemOut);
	    }*/
	
	 private static User peer0, peer1, peer2, peer3;
	 @BeforeAll
	 static void testCaseGeneratePeers(){
	        assertDoesNotThrow(() ->peer0 = new User("name0",0, "127.0.0.1"));
	        assertDoesNotThrow(() ->peer1 = new User("name1",1, "127.0.0.1"));
	        assertDoesNotThrow(() ->peer2 = new User("name2",2, "127.0.0.1"));
	        assertDoesNotThrow(() ->peer3 = new User("name3",3, "127.0.0.1"));

	        
	    }
	 	@Test
	    @Order (1)
	    //@Disabled
	    void testCaseConnect() throws InterruptedException {
	 		//InputOutput inputOutput= new InputOutput();
/*
	 	    String input = "add 5";
	 	    InputStream in = new ByteArrayInputStream(input.getBytes());
	 	    System.setIn(in);*/

	 		// do your thing
	 		//TextIO textIO = TextIoFactory.getTextIO();
	 		ArrayList<Integer> answer0= new ArrayList<Integer>();
	 		
	 		answer0.addAll(Arrays.asList(0,0,0,0,0));
	 		ArrayList<Integer> answer1= new ArrayList<Integer>();
	 		answer1.addAll(Arrays.asList(1,0,0,1,2));
	 		ArrayList<Integer> answer2= new ArrayList<Integer>();
	 		answer2.addAll(Arrays.asList(0,0,2,1,3));
	 		ArrayList<Integer> answer3= new ArrayList<Integer>();
	 		answer3.addAll(Arrays.asList(1,0,3,3,3));
	 		String key0,key1,key2,key3;
	 		key0=peer0.getConnector().createAuserProfileKey(answer0);
	 		key1=peer1.getConnector().createAuserProfileKey(answer1);
	 		key2=peer2.getConnector().createAuserProfileKey(answer2);
	 		key3=peer3.getConnector().createAuserProfileKey(answer3);
	 		System.out.println("aaaaaaaaaaaaa"+key0);
	 		System.out.println("aaaaaaaaaaaaa"+key1);
	 		System.out.println("aaaaaaaaaaaaa"+key2);
	 		System.out.println("aaaaaaaaaaaaa"+key3);
	 		System.out.println("hamming distance"+peer0.getConnector().hammingDistance(key0, key1));
	 		System.out.println("hamming distance"+peer0.getConnector().hammingDistance(key0, key2));
	 		System.out.println("hamming distance"+peer0.getConnector().hammingDistance(key0, key3));
	 		System.out.println("hamming distance"+peer0.getConnector().hammingDistance(key1, key2));
	 		
	 		peer0.setProfile_key(key0);
	 		assertTrue(peer0.getConnector().join(key0, peer0.getNickname()));
	 		//TimeUnit.SECONDS.sleep(1);
	 		peer1.setProfile_key(key1);
	 		//assertEquals(1, peer1.hammingDistance(peer1.getNickname(), peer0.getNickname()));
	 		assertTrue(peer1.getConnector().join(key1, peer1.getNickname()));
	 		peer2.setProfile_key(key2);
	 		//assertTrue(peer1.connect());TimeUnit.SECONDS.sleep(1);
	 		//TimeUnit.SECONDS.sleep(1);
	 		assertTrue(peer2.getConnector().join(key2,peer2.getNickname()));
	 		
	 		peer3.setProfile_key(key3);
	 		//TimeUnit.SECONDS.sleep(1);
	 		assertTrue(peer3.getConnector().join(key3, peer3.getNickname()));
	 		
	 		TimeUnit.SECONDS.sleep(2);
	 		ArrayList<String> test= new ArrayList<String>();
	 		test=
	 				(ArrayList<String>) peer0.getFriendsList();
	 		System.out.println(peer3.getNickname()+peer3.getId());
	 		for (int i=0;i<test.size();i++) {
	 		assertArrayEquals(test.toArray(), peer0.getFriendsList().toArray());
	 		
	 		//System.out.println("AAAAAAAAAAAAAAAA"+test.get(i));
	 		//assertTrue("0",peer3.connect());
	 		// optionally, reset System.in to its original
	 		//System.setIn(sysInBackup);
	 		}
	 		
	 		//assertTrue(peer1.getConnector().getFriends5c("peer1", "peer0", peer0.getConnector().));
	 	}
	 	@Test
	    @Order (2)
	    //@Disabled
	    void testCaseSend() throws InterruptedException {
	 		assertTrue(peer0.getConnector().sendMessage3(0, peer0.getNickname(), "test message"));
	 		assertTrue(peer1.getConnector().sendMessage3(1, peer1.getNickname(), "test message2"));
	 		assertFalse(peer2.getConnector().sendMessage3(3, peer2.getNickname(), "test message2"));
	 	}
	 	@Test
	    @Order (3)
	    //@Disabled
	    void testCaseGroupChat() throws InterruptedException {
	 		
	 		ArrayList<String> nickFriends=(ArrayList<String>) peer1.getFriendsList();
	 		nickFriends.add("not friends");
	 		ArrayList<Integer> numberfriends=new ArrayList<Integer>();
	 		numberfriends.add(0);
	 		numberfriends.add(1);
	 		numberfriends.add(2);
	 		assertFalse(peer1.getConnector().groupChat2("gruppo",numberfriends, nickFriends));
	 		
	 		numberfriends.remove(2);
	 		nickFriends.remove("not friends");
	 		assertTrue(peer1.getConnector().groupChat2("gruppo",numberfriends, nickFriends));
	 		
	 		assertTrue(peer1.getConnector().sendMessage3(2, peer0.getNickname(), "test group message"));
	 		//textIO.newBooleanInputReader().withTrueInput("N");
	 		//assertTrue(peer1.getConnector().sendMessage3(0, peer1.getNickname(), "test message2"));
	 		//assertFalse(peer2.getConnector().sendMessage3(3, peer2.getNickname(), "test message2"));
	 		//System.setIn(sysInBackup);
	 	}
	 	@Test
	    @Order (4)
	    //@Disabled
	    void testCaseChangekey() throws InterruptedException {
	 		
	 		ArrayList<Integer>answer0=new ArrayList<Integer>();
	 		answer0.addAll(Arrays.asList(0,3,3,3,3));
	 		
	 		System.out.println("profilekey 1: "+peer1.getProfile_key());
	 		peer0.setProfile_key(peer0.getConnector().createAuserProfileKey(answer0));
	 		assertTrue(peer0.getConnector().changeKey(peer0.getNickname(), peer0.getProfile_key()));
	 		ArrayList<String> expectedfriends= new ArrayList<String>();
	 		expectedfriends.add(peer3.getNickname());
	 		//expectedfriends.add("gruppo");
	 		TimeUnit.SECONDS.sleep(1);
	 		ArrayList<String> c= (ArrayList<String>) peer0.getFriendsList();
	 		for (String d:c)
	 			System.out.print("aaaa"+d);
	 		assertArrayEquals(expectedfriends.toArray(), peer0.getFriendsList().toArray());
	 		expectedfriends.remove(peer3.getNickname());
	 		expectedfriends.add(peer2.getNickname());
	 		expectedfriends.add("gruppo");
	 		ArrayList<String> a= (ArrayList<String>) peer1.getFriendsList();
	 		for (String b:a)
	 			System.out.print("aaaa"+b);
	 		assertArrayEquals(expectedfriends.toArray(), peer1.getFriendsList().toArray());
	 	}
 

	@Test
    @Order (5)
    //@Disabled
    void testCaseChangeExit() throws InterruptedException {
 		
		assertTrue(peer2.exit());
 		TimeUnit.SECONDS.sleep(1);
 		ArrayList<String> expectedfriends= new ArrayList<String>();
 		expectedfriends.add("gruppo");
 		ArrayList<String> a= (ArrayList<String>) peer1.getFriendsList();
 		for (String b:a)
 			System.out.print("aaaa"+b);
 		assertArrayEquals(expectedfriends.toArray(), peer1.getFriendsList().toArray());
 	}
	@Test
    @Order (6)
    //@Disabled
    void testCaseGetQuestion() throws InterruptedException {
 	
 		ArrayList<String> questions=new ArrayList<String>();
		        		questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");
		assertEquals(questions, peer0.getConnector().getUserProfileQuestions2());
 		/*TimeUnit.SECONDS.sleep(1);
 		ArrayList<String> expectedfriends= new ArrayList<String>();
 		expectedfriends.add("gruppo");
 		ArrayList<String> a= (ArrayList<String>) peer1.getFriendsList();
 		for (String b:a)
 			System.out.print("aaaa"+b);
 		assertArrayEquals(expectedfriends.toArray(), peer1.getFriendsList().toArray());*/
		assertArrayEquals(questions.toArray(), peer1.getConnector().getUserProfileQuestions2().toArray());
 	}
	@Test
    @Order (7)
    //@Disabled
    void testCaseAddSpam() throws InterruptedException, IOException {
 		/*InputStream sysInBackup = System.in; 
 		 String input = "False\n True\n 0\n True\n 1\n False\n";
	 	    InputStream in = new ByteArrayInputStream(input.getBytes());
	 	    System.setIn(in);
	 	    Scanner inp=new Scanner(System.in);
 		TextIO textIO = TextIoFactory.getTextIO();
 		TextTerminal terminal = textIO.getTextTerminal();
 		textIO.newBooleanInputReader().withDefaultValue(false);
 		textIO.newBooleanInputReader().withTrueInput("N");
 		assertTrue(peer1.groupChat());
 		assertTrue(peer1.getConnector().sendMessage3(2, peer0.getNickname(), "test group message"));
 		textIO.newBooleanInputReader().withTrueInput("N");
 		//assertTrue(peer1.getConnector().sendMessage3(0, peer1.getNickname(), "test message2"));
 		//assertFalse(peer2.getConnector().sendMessage3(3, peer2.getNickname(), "test message2"));
 		System.setIn(sysInBackup);
 		ArrayList<String> questions=new ArrayList<String>();
		        		questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");*/
		//assertTrue(peer2.exit());
 		//TimeUnit.SECONDS.sleep(1);
		ArrayList<String> spamlist= new ArrayList<String>();
		spamlist.add(peer1.getNickname());
		assertTrue(peer0.getConnector().addSpam(spamlist));
		//assertTrue(peer0.getConnector().sendMessage3(0, peer0.getNickname(), "test message"));
 		
 		ArrayList<String> expectedfriendsSpam= new ArrayList<String>();
 		expectedfriendsSpam.add(peer1.getNickname());
 		ArrayList<String> a= (ArrayList<String>) peer0.getConnector().getSpamList();
 		for (String b:a)
 			System.out.print("aaaa"+b);
 		assertArrayEquals(expectedfriendsSpam.toArray(), peer0.getConnector().getSpamList().toArray());
 	}
	@Test
    @Order (9)
    @Disabled
    void testCaseSeeSpamMessages() throws InterruptedException, IOException {
 	
		assertTrue(peer1.getConnector().sendMessage3(0, peer1.getNickname(), "test message"));
 		
 		ArrayList<String> expectedfriendsSpam= new ArrayList<String>();
 		expectedfriendsSpam.add(peer1.getNickname());
 		ArrayList<String> a= (ArrayList<String>) peer0.getConnector().getSpamList();
 		for (String b:a)
 			System.out.print("aaaa"+b);
 		assertArrayEquals(expectedfriendsSpam.toArray(), peer0.getConnector().getSpamList().toArray());
 	}

	@Test
    @Order (8)
    
    void testCaseRemoveSpam() throws InterruptedException, IOException {
 		/*InputStream sysInBackup = System.in; 
 		 String input = "False\n True\n 0\n True\n 1\n False\n";
	 	    InputStream in = new ByteArrayInputStream(input.getBytes());
	 	    System.setIn(in);
	 	    Scanner inp=new Scanner(System.in);
 		TextIO textIO = TextIoFactory.getTextIO();
 		TextTerminal terminal = textIO.getTextTerminal();
 		textIO.newBooleanInputReader().withDefaultValue(false);
 		textIO.newBooleanInputReader().withTrueInput("N");
 		assertTrue(peer1.groupChat());
 		assertTrue(peer1.getConnector().sendMessage3(2, peer0.getNickname(), "test group message"));
 		textIO.newBooleanInputReader().withTrueInput("N");
 		//assertTrue(peer1.getConnector().sendMessage3(0, peer1.getNickname(), "test message2"));
 		//assertFalse(peer2.getConnector().sendMessage3(3, peer2.getNickname(), "test message2"));
 		System.setIn(sysInBackup);
 		ArrayList<String> questions=new ArrayList<String>();
		        		questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Ho una parola gentile per tutti");*/
		//assertTrue(peer2.exit());
 		//TimeUnit.SECONDS.sleep(1);
		ArrayList<String> spamlist= new ArrayList<String>();
		spamlist.add(peer2.getNickname());
		
		assertTrue(peer0.getConnector().addSpam(spamlist));
		spamlist.add(peer1.getNickname());
		spamlist.remove(peer2.getNickname());
		assertTrue(peer0.getConnector().removeSpam(spamlist));
		//assertTrue(peer0.getConnector().sendMessage3(0, peer0.getNickname(), "test message"));
 		
 		ArrayList<String> expectedfriendsSpam= new ArrayList<String>();
 		expectedfriendsSpam.add(peer2.getNickname());
 		ArrayList<String> a= (ArrayList<String>) peer0.getConnector().getSpamList();
 		for (String b:a)
 			System.out.print("aaaa"+b);
 		assertArrayEquals(expectedfriendsSpam.toArray(), peer0.getConnector().getSpamList().toArray());
 	}

}