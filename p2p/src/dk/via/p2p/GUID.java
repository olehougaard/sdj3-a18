package dk.via.p2p;

public class GUID {
	private short guid;
	
	public GUID(short guid) {
		this.guid = guid;
	}
	
	public GUID(String hex) {
		this.guid = Short.parseShort(hex, 16);
	}
	
	public short asNumber() {
		return guid;
	}
	
	public String asHex() {
		return Integer.toHexString(guid);
	}
}
