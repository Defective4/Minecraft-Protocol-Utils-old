package net.defect.mc.event;

/**
 * An event indicating that someone just pinged this server on server list
 * @author Wojciech R. "Defective"
 *
 */
public class ServerListPingEvent {
	private String address;
	private int protocol;
	public ServerListPingEvent(String address, int protocol)
	{
		this.address = address;
		this.protocol = protocol;
	}
	/**
	 * Get user's address
	 * @return user's inet4 address
	 */
	public String getAddress()
	{
		return address;
	}
	/**
	 * Get user's client protocol
	 * @return client's protocol
	 */
	public int getProtocol()
	{
		return protocol;
	}
}
