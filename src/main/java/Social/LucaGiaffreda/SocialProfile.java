package Social.LucaGiaffreda;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.beryx.textio.StringInputReader;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import net.tomp2p.dht.FutureGet;
import net.tomp2p.futures.FutureDirect;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;

public class SocialProfile {
	List<String>question;
	List<String>answer;
	List<String>friends;
	public SocialProfile() {
		 question=new ArrayList<String>();
		 answer=new ArrayList<String>();
		 friends=new ArrayList<String>();
		 question.add("Ho una parola gentile per tutti");
		 question.add("Sono sempre preparato");
		 question.add("Mi sento a mio agio con le persone");
		 question.add("Mi sento spesso blu");
		 question.add("Credo nell'importanza dell'arte");
		 question.add("Sento di essere migliore delle altre persone");
		 question.add("Mi interessa il significato delle cose");
		 question.add("Tratto tutti con gentilezza e simpatia");
		 question.add("Mi piacciono le serie");
		 question.add("Mi piace lo sport");
	}
	public List<String> getUserProfileQuestions() {
		
		for(int i=0;i<question.size();i++){
			TextIO textIO = TextIoFactory.getTextIO();
			TextTerminal terminal = textIO.getTextTerminal();
			//terminal.printf(question.get(i));
			answer.add( textIO.newStringInputReader().withDefaultValue("defaultValue").read(question.get(i)));
		}
		/*TextIO textIO = TextIoFactory.getTextIO();
		TextTerminal terminal = textIO.getTextTerminal();
		
		terminal.printf("Ho una parola gentile per tutti");
		StringInputReader message = textIO.newStringInputReader();
		terminal.printf("Ho una parola gentile per tutti");
		message = textIO.newStringInputReader();
		terminal.printf("Ho una parola gentile per tutti");
		message = textIO.newStringInputReader();
		terminal.printf("Ho una parola gentile per tutti");
		message = textIO.newStringInputReader();
		terminal.printf("Ho una parola gentile per tutti");
		message = textIO.newStringInputReader();
		terminal.printf("Ho una parola gentile per tutti");
		message = textIO.newStringInputReader();
		terminal.printf("Ho una parola gentile per tutti");
		message = textIO.newStringInputReader();
		terminal.printf("Ho una parola gentile per tutti");
		message = textIO.newStringInputReader();
		terminal.printf("Ho una parola gentile per tutti");
		message = textIO.newStringInputReader();
		*/
		return answer;
	}

	public String createAuserProfileKey(List<Integer> _answer) {
		String key="";
		for (int i=0;i<answer.size();i++) {
			key=key+answer.get(i);
		}
		return key;
	}

	public boolean join(String _profile_key, String _nick_name) {
		String message="Ciao sono"+ _nick_name;
		//manda sulla rete nickname e key
		/*
		 * try {
			FutureGet futureGet = _dht.get(Number160.createHash(_profile_key)).start();
			futureGet.awaitUninterruptibly();
			if (futureGet.isSuccess()) {
				HashSet<PeerAddress> peers_on_topic;
				peers_on_topic = (HashSet<PeerAddress>) futureGet.dataMap().values().iterator().next().object();
				for(PeerAddress peer:peers_on_topic)
				{
					FutureDirect futureDirect = _dht.peer().sendDirect(peer).object(_obj).start();
					futureDirect.awaitUninterruptibly();
				}
				
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		 * */
		return false;
	}

	public List<String> getFriends() {
		// event listner che quando riceve in messaggio controlla la distanza di hamming e se è sotto una certa soglia lo aggiunge agli amici
		// quando qualcuno ti aggiunge agli amici a suo volta lo aggiungi agli amici
		return null;
	}
	public int hammingDistance(String a, String b) {
		int count=0;
		for (int i=0; i<a.length();i++) {
			if(a.charAt(i)!=b.charAt(i))
				count++;
		}
		return count;
	}

}
