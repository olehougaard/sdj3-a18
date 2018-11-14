package dk.via.p2p;

public class RoutingTable {
	private GUID guid;
	
	private Peer[][] peers;

	public RoutingTable(Peer peer) {
		this.guid = peer.getGuid();
		peers = new Peer[4][16];
		for(int i = 0; i < peers.length; i++) {
			peers[i][getHexDigit(guid, i)] = peer;
		}
	}

	private static int getHexDigit(GUID guid, int i) {
		return Integer.parseInt(guid.asHex().substring(i, i+1), 16);
	}
	
	private String longestCommonPrefix(GUID targetGuid) {
		String guid = this.guid.asHex();
		String peerGuid = targetGuid.asHex();
		int i = 0;
		while(peerGuid.charAt(i) == guid.charAt(i))
			i++;
		return peerGuid.substring(0, i);
	}
	
	public Peer bestRoute(GUID targetGuid) {
		String prefix = longestCommonPrefix(targetGuid);
		int i = prefix.length();
		return peers[i][getHexDigit(targetGuid, i)];
	}
	
	public boolean knownGuid(GUID targetGuid) {
		return bestRoute(targetGuid) != null;
	}
	
	public void addPeer(Peer peer) {
		String prefix = longestCommonPrefix(peer.getGuid());
		int i = prefix.length();
		peers[i][getHexDigit(peer.getGuid(), i)] = peer;
	}
}
