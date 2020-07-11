package net.defect.mc.stat.data;

import java.awt.image.BufferedImage;

/**
 * Object containing data returned by Legacy Server List Ping
 * @author Wojciech R. "DefektIV"
 *
 */
public class SimpleStatusData implements StatusData {

	final int max;
	final int online;
	final long ping;
	final String desc;
	/**
	 * Constructs object
	 * @param online players online
	 * @param max max players
	 * @param ping server's ping
	 * @param desc server's description
	 */
	public SimpleStatusData(int online, int max, long ping, String desc)
	{
		this.max = max;
		this.online = online;
		this.ping = ping;
		this.desc = desc;
	}
	
	@Override
	public void setPing(long p) {}
	
	@Override
	public int getMaxPlayers() {

		return max;
	}

	@Override
	public int getOnlinePlayers() {

		return online;
	}
	/**
	 * Returns null, because Legacy SLP does not send this field
	 */
	@Override
	public PlayerInfo[] getPlayers() {

		return null;
	}
	/**
	 * Returns -1, because Legacy SLP does not send this field
	 */
	@Override
	public int getProtocol() {

		return -1;
	}
	/**
	 * Returns null, because Legacy SLP does not send this field
	 */
	@Override
	public String getVersionName() {

		return null;
	}

	@Override
	public long getPing() {

		return ping;
	}

	/**
	 * Returns null, because Legacy SLP does not send this field
	 */
	@Override
	public BufferedImage getServerIcon() {

		return null;
	}


	@Override
	public String[] getDescription() {
		
		return desc.split("\n");
	}

}
