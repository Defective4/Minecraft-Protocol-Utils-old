package net.defect.mc.stat.data;


import net.defect.mc.stat.MCStatus;
import net.defect.mc.stat.StatusServer;

/**
 * Class used by {@link StatusServer} containing data sent to client
 * @author Wojciech R. "DefektIV"
 *
 */
public class InternalStatusData {
	IDescription description = new IDescription();
	IPlayers players = new IPlayers();
	IVersion version = new IVersion();
	String favicon;
	/**
	 * Creates data object
	 * @param description server's MOTD
	 * @param max max players
	 * @param online online players count
	 * @param players online player list
	 * @param protocol protocol used by server
	 */
	public InternalStatusData(String description, int max, int online, PlayerInfo[] players, MCStatus.Protocol protocol)
	{
		this(description, max, online, players, protocol.name, protocol.value);
	}
	/**
	 * Sets version display name
	 * @param name version name
	 */
	public void setVersionName(String name)
	{
		version.name = name;
	}
	/**
	 * Creates data object
	 * @param description server's MOTD
	 * @param max max players
	 * @param online online players count
	 * @param players online player list
	 * @param version version display name
	 * @param protocol protocol used by server
	 */
	public InternalStatusData(String description, int max, int online, PlayerInfo[] players, String version, int protocol)
	{
		this.description.text = description;
		this.players.max = max;
		this.players.online = online;
		this.players.sample = players;
		this.version.name = version;
		this.version.protocol = protocol;
	}
	/**
	 * Gets protocol
	 * @return server's protocol version
	 */
	public int getProtocol()
	{
		return version.protocol;
	}
	/**
	 * Gets description
	 * @return server's description
	 */
	public String getDescription()
	{
		return description.text;
	}
	/**
	 * Sets protocol
	 * @param protocol protocol version to set
	 */
	public void setProtocol(int protocol)
	{
		version.protocol = protocol;
	}
	
}
class IDescription
{
	String text;
}
class IPlayers
{
	int max;
	int online;
	PlayerInfo[] sample;
}
class IVersion
{
	String name;
	int protocol;
}