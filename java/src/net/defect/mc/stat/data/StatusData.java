package net.defect.mc.stat.data;

import java.awt.image.BufferedImage;

/**
 * Interface for most of classes containing SLP (Server List Ping) data
 * @author Wojciech R. "Defective"
 *
 */
public interface StatusData {
	/**
	 * Gets max player count 
	 * @return max player count
	 */
	public int getMaxPlayers();
	/**
	 * Gets online player count 
	 * @return online player count
	 */
	public int getOnlinePlayers();
	/**
	 * Gets player list
	 * @return player list
	 */
	public PlayerInfo[] getPlayers();
	/**
	 * Gets server's protocol version
	 * @return protocol version
	 */
	public int getProtocol();
	/**
	 * Gets server's version name
	 * @return version name
	 */
	public String getVersionName();
	/**
	 * Gets server's ping
	 * @return ping
	 */
	public long getPing();
	/**
	 * Gets server's decoded icon
	 * @return server's icon
	 */
	public BufferedImage getServerIcon() throws DecodingException;
	/**
	 * Gets server's description (MOTD)
	 * @return server's description
	 */
	public String[] getDescription();
	/**
	 * Sets ping
	 * @param ping
	 */
	public void setPing(long ping);
	
}
