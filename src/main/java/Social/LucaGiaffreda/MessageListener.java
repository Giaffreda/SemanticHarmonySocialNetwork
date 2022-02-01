package Social.LucaGiaffreda;

import net.tomp2p.peers.PeerAddress;

public interface MessageListener {
	
	public Object parseMessage(PeerAddress sender,  Object obj);
	
}
