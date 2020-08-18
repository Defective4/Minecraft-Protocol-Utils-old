package net.defect.mc.stat.data;

import net.defect.mc.stat.MCStatus;

/**
 * Used only as base class for {@link NormalStatusData} and {@link ExtraStatusData}<br>
 * Contains most of server's status data excluding description (MOTD)
 * @author Wojciech R. "DefektIV"
 *
 */
public class DescriptionlessStatusData implements StatusData {

	protected DescriptionlessStatusData() {}
	
	Players players;
	Version version;
	long ping = -1;
	String favicon = null;
	
	/**
	 * Used internally
	 */
	public void setPing(long ping)
	{
		this.ping = ping;
	}
	
	/**
	 * Gets max players count
	 */
	@Override
	public int getMaxPlayers() {

		return players!=null ? players.max : -1;
	}

	/**
	 * Gets current online players count
	 */
	@Override
	public int getOnlinePlayers() {

		return players!=null ? players.online : -1;
	}

	/**
	 * Gets list of online players
	 */
	@Override
	public PlayerInfo[] getPlayers() {

		return players!=null ? players.sample : null;
	}

	/**
	 * Gets server's protocol
	 */
	@Override
	public int getProtocol() {

		int protocol = version!=null ? version.protocol : -1;
		protocol = protocol>MCStatus.maxProtocol ? MCStatus.maxProtocol : protocol;
		return protocol;
	}

	/**
	 * Gets server's version name
	 */
	@Override
	public String getVersionName() {

		return version!=null ? version.name : null;
	}

	/**
	 * Gets ping set by setPing() method
	 */
	@Override
	public long getPing() {

		return ping;
	}


	/**
	 * Returns null
	 */
	@Override
	public String[] getDescription() {

		return null;
	}
	
	
	protected static String reformat(String s)
	{
		String[] chars = s.split("");
		String st = "";
		for(int x=0;x<chars.length;x++)
		{
			if(chars[x].equals("\u00A7"))
			{
				chars[x] = "";
				if(chars.length>x+1)
				{
					chars[x+1] = "";
				}
			}
			st += chars[x];
		}
		st = st.replaceAll("[^a-zA-Z0-9\\,\\.\\/\\;\\:\\|\\<\\>\\?\\{\\}\\\"\\[\\]\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\-\\=\\_\\+\\`\\s]", "");
		return st;
		
	}
	
}
class Players
{
	int max;
	int online;
	PlayerInfo[] sample;
}
class Version
{
	int protocol;
	String name;
}
