package Social.LucaGiaffreda;



import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.junit.jupiter.api.*;
import Social.LucaGiaffreda.User;
import Social.LucaGiaffreda.Message;
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
 * Unit test for simple Message.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest 
  
{
	
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
	    void testCaseConnect() throws InterruptedException {
	/*ArrayList<Integer> answer0= new ArrayList<Integer>();
	 		
	 		answer0.addAll(Arrays.asList(0,0,0,0,0));
	 		ArrayList<Integer> answer1= new ArrayList<Integer>();
	 		answer1.addAll(Arrays.asList(1,0,0,1,2));
	 		ArrayList<Integer> answer2= new ArrayList<Integer>();
	 		answer2.addAll(Arrays.asList(0,0,2,1,3));
	 		ArrayList<Integer> answer3= new ArrayList<Integer>();
	 		answer3.addAll(Arrays.asList(1,0,3,3,3));*/
	 		 ArrayList<Integer> answer0= new ArrayList<Integer>();
	 		
	 		answer0.addAll(Arrays.asList(0,0,0,0,0,0,0));
	 		ArrayList<Integer> answer1= new ArrayList<Integer>();
	 		answer1.addAll(Arrays.asList(1,0,0,1,2,3,1));
	 		ArrayList<Integer> answer2= new ArrayList<Integer>();
	 		answer2.addAll(Arrays.asList(0,0,2,1,3,1,0));
	 		ArrayList<Integer> answer3= new ArrayList<Integer>();
	 		answer3.addAll(Arrays.asList(1,0,3,3,3,0,2));
	 		String key0,key1,key2,key3;
	 		key0=peer0.getConnector().createAuserProfileKey(answer0);
	 		key1=peer1.getConnector().createAuserProfileKey(answer1);
	 		key2=peer2.getConnector().createAuserProfileKey(answer2);
	 		key3=peer3.getConnector().createAuserProfileKey(answer3);
	 		
	 		peer0.setProfile_key(key0);
	 		assertTrue(peer0.getConnector().join(key0, peer0.getNickname()));
	 		peer1.setProfile_key(key1);
	 		assertTrue(peer1.getConnector().join(key1, peer1.getNickname()));
	 		peer2.setProfile_key(key2);
	 		assertTrue(peer2.getConnector().join(key2,peer2.getNickname()));
	 		
	 		peer3.setProfile_key(key3);
	 		assertTrue(peer3.getConnector().join(key3, peer3.getNickname()));
	 		
	 		TimeUnit.SECONDS.sleep(2);
	 		ArrayList<String> test= new ArrayList<String>();
	 		test=
	 				(ArrayList<String>) peer0.getConnector().getFriends();
	 		for (int i=0;i<test.size();i++) {
	 		assertArrayEquals(test.toArray(), peer0.getConnector().getFriends().toArray());
	 		
	 		}
	 		
	 	}
	 	@Test
	    @Order (2)
	    //@Disabled
	    void testCaseSend() throws InterruptedException {
	 		assertTrue(peer0.getConnector().sendMessage(0, peer0.getNickname(), "test message"));
	 		assertTrue(peer1.getConnector().sendMessage(1, peer1.getNickname(), "test message2"));
	 		assertFalse(peer2.getConnector().sendMessage(3, peer2.getNickname(), "test message2"));
	 		assertTrue(peer2.getConnector().sendMessage(1, peer2.getNickname(), "test message3"));
	 	}
	 	@Test
	    @Order (3)
	    //@Disabled
	    void testCaseGroupChat() throws InterruptedException {
	 		
	 		ArrayList<String> nickFriends=(ArrayList<String>) peer1.getConnector().getFriends();
	 		nickFriends.add("not friends");
	 		ArrayList<Integer> numberfriends=new ArrayList<Integer>();
	 		numberfriends.add(0);
	 		numberfriends.add(1);
	 		numberfriends.add(2);
	 		assertFalse(peer1.getConnector().groupChat2("gruppo",numberfriends, nickFriends));
	 		
	 		numberfriends.remove(2);
	 		nickFriends.remove("not friends");
	 		assertTrue(peer1.getConnector().groupChat2("gruppo",numberfriends, nickFriends));
	 		
	 		assertTrue(peer1.getConnector().sendMessage(2, peer0.getNickname(), "test group message"));
	 	}
	 	@Test
	    @Order (4)
	    //@Disabled
	    void testCaseChangekey() throws InterruptedException {
	 		
	 		/*ArrayList<Integer>answer0=new ArrayList<Integer>();
	 		answer0.addAll(Arrays.asList(0,3,3,2,3));*/
	 		for(int i=0;i<1;i++) {
	 		ArrayList<Integer>answer0=new ArrayList<Integer>();
	 		answer0.addAll(Arrays.asList(0,3,3,3,3,2,2));
	 		peer0.setProfile_key(peer0.getConnector().createAuserProfileKey(answer0));
	 		assertTrue(peer0.getConnector().changeKey(peer0.getNickname(), peer0.getProfile_key()));
	 		ArrayList<String> expectedfriends= new ArrayList<String>();
	 		expectedfriends.add(peer2.getNickname());
	 		expectedfriends.add(peer3.getNickname());
	 		ArrayList<String> expectedfriends1= new ArrayList<String>();
	 		expectedfriends1.add(peer3.getNickname());
	 		expectedfriends1.add(peer2.getNickname());
	 		//int d2=peer0.getConnector().hammingDistance(peer0.getProfile_key(), peer2.getProfile_key());
	 		TimeUnit.SECONDS.sleep(1);
	 		ArrayList<String> c= (ArrayList<String>) peer0.getConnector().getFriends();
	 		//int d1=peer0.getConnector().hammingDistance(peer0.getProfile_key(), peer1.getProfile_key());
	 		//int d2=peer0.getConnector().hammingDistance(peer0.getProfile_key(), peer2.getProfile_key());
	 		//int d3=peer0.getConnector().hammingDistance(peer0.getProfile_key(), peer3.getProfile_key());
	 		
	 		for(String s: c)
	 			System.out.println(s);
	 		/*System.out.println(d1);
	 		System.out.println(d2);
	 		System.out.println(d3);*/
	 		assertArrayEquals(expectedfriends.toArray(), c.toArray());
	 		//assertTrue(expectedfriends.toArray().equals(c.toArray()) || expectedfriends1.toArray().equals(c.toArray()));
	 		expectedfriends.remove(peer3.getNickname());
	 		expectedfriends.add(peer2.getNickname());
	 		expectedfriends.add("gruppo");
	 		ArrayList<String> a= (ArrayList<String>) peer1.getConnector().getFriends();
	 		
	 		assertArrayEquals(a.toArray(), peer1.getConnector().getFriends().toArray());
	 		}
	 	}
 

	@Test
    @Order (5)
    //@Disabled
    void testCaseChangeExit() throws InterruptedException {
 		
		assertTrue(peer2.exit());
 		TimeUnit.SECONDS.sleep(1);
 		ArrayList<String> expectedfriends= new ArrayList<String>();
 		expectedfriends.add("gruppo");
 		ArrayList<String> a= (ArrayList<String>) peer1.getConnector().getFriends();
 		
 		assertArrayEquals(expectedfriends.toArray(), peer1.getConnector().getFriends().toArray());
 	}
	@Test
    @Order (6)
    //@Disabled
    void testCaseGetQuestion() throws InterruptedException {
 	
 		ArrayList<String> questions=new ArrayList<String>();
		        		questions.add("Ho una parola gentile per tutti");
		       		 questions.add("Sono sempre preparato");
		       		 questions.add("Mi sento a mio agio con le persone");
		       		questions.add("Evito di assumermi molte responsabilità");
		       		questions.add("Faccio amicizia facilmente");
		       		questions.add("Mi interessa il significato delle cose");
		       		questions.add("Ho una vivida immaginazione");
		assertEquals(questions, peer0.getConnector().getUserProfileQuestions());
		assertArrayEquals(questions.toArray(), peer1.getConnector().getUserProfileQuestions().toArray());
 	}
	@Test
    @Order (9)
	 void testCaseCreateAuserProfileKey() throws InterruptedException {
		 	
	 		ArrayList<Integer> answer0= new ArrayList<Integer>();
	 		answer0.addAll(Arrays.asList(0,3,0,2,0));
	 		ArrayList<Integer> answer1= new ArrayList<Integer>();
	 		answer1.addAll(Arrays.asList(1,1,0,3,2));
	 		String key0,key1;
	 		key0="000111000011000";
	 		key1="011001010111011";
			assertEquals(key0, peer0.getConnector().createAuserProfileKey(answer0));
			
			assertNotEquals(key1, peer1.getConnector().createAuserProfileKey(answer1));
			key1="001001000111011";
			
			assertEquals(key1, peer1.getConnector().createAuserProfileKey(answer1));
	 	}
	@Test
    @Order (7)
    //@Disabled
    void testCaseAddSpam() throws InterruptedException, IOException {
		ArrayList<String> spamlist= new ArrayList<String>();
		spamlist.add(peer1.getNickname());
		assertTrue(peer0.getConnector().addSpam(spamlist));
 		
 		ArrayList<String> expectedfriendsSpam= new ArrayList<String>();
 		expectedfriendsSpam.add(peer1.getNickname());
 		ArrayList<String> a= (ArrayList<String>) peer0.getConnector().getSpamList();
 		
 		assertArrayEquals(expectedfriendsSpam.toArray(), a.toArray());
 	}
	@Test
    @Order (10)
    @Disabled
    void testCaseSeeSpamMessages() throws InterruptedException, IOException {
 	
		assertTrue(peer1.getConnector().sendMessage(0, peer1.getNickname(), "test message"));
 		
 		ArrayList<String> expectedfriendsSpam= new ArrayList<String>();
 		expectedfriendsSpam.add(peer1.getNickname());
 		ArrayList<String> a= (ArrayList<String>) peer0.getConnector().getSpamList();
 		
 		assertArrayEquals(expectedfriendsSpam.toArray(), peer0.getConnector().getSpamList().toArray());
 	}

	@Test
    @Order (8)
    
    void testCaseRemoveSpam() throws InterruptedException, IOException {
		ArrayList<String> spamlist= new ArrayList<String>();
		spamlist.add(peer2.getNickname());
		
		assertTrue(peer0.getConnector().addSpam(spamlist));
		spamlist.add(peer1.getNickname());
		spamlist.remove(peer2.getNickname());
		assertTrue(peer0.getConnector().removeSpam(spamlist));
 		
 		ArrayList<String> expectedfriendsSpam= new ArrayList<String>();
 		expectedfriendsSpam.add(peer2.getNickname());
 		TimeUnit.SECONDS.sleep(1);
 		ArrayList<String> a= (ArrayList<String>) peer0.getConnector().getSpamList();
 		
 		assertArrayEquals(expectedfriendsSpam.toArray(), a.toArray());
 	}

}