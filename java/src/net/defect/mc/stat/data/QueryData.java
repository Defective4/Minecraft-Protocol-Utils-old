package net.defect.mc.stat.data;

import net.defect.mc.stat.MCStatus;

/**
 * Class containing data received with {@link MCStatus}'s getQuery() method
 * @author Wojciech R. "DefektIV"
 *
 */
public class QueryData {
	final private String motd;
	final private String gametype;
	final private String map;
	final private int online;
	final private int max;
	/**
	 * Constructs object
	 * @param motd server's description
	 * @param gametype server's gametype
	 * @param map server's map
	 * @param online players online
	 * @param max maximum player count
	 */
	public QueryData(String motd, String gametype, String map, int online, int max)
	{
		this.motd = motd;
		this.gametype = gametype;
		this.map = map;
		this.online = online;
		this.max = max;
	}
	public String getMotd()
	{
		return DescriptionlessStatusData.reformat(motd);
	}
	public String getGametype()
	{
		return gametype;
	}
	public String getMap()
	{
		return map;
	}
	public int getOnlinePlayers()
	{
		return online;
	}
	public int getMaxPlayers()
	{
		return max;
	}
	
}
