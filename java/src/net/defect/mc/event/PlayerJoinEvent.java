package net.defect.mc.event;

/**
 * Event indicating that a player tried to join this server
 * @author Wojciech R. "Defective"
 *
 */
public class PlayerJoinEvent {
	private String player;
	private String address;
	private int protocol;
	public PlayerJoinEvent(String player, String address, int protocol)
	{
		this.player = player;
		this.address = address;
		this.protocol = protocol;
	}
	
	/**
	 * Get player's nickname
	 * @return
	 */
	public String getPlayer()
	{
		return player;
	}
	/**
	 * Get player's address
	 * @return player's inet4 address
	 */
	public String getAddress()
	{
		return address;
	}
	/**
	 * Get protocol used by player
	 * @return protocol number
	 */
	public int getProtocol()
	{
		return protocol;
	}
}
