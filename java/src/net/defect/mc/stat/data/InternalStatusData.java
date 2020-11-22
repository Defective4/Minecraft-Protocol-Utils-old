package net.defect.mc.stat.data;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

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
	 * @param icon server icon to use (64x64), or null if none
	 */
	public InternalStatusData(String description, int max, int online, BufferedImage icon, PlayerInfo[] players, MCStatus.Protocol protocol)
	{
		this(description, max, online, players, icon, protocol.name, protocol.value);
	}
	/**
	 * Creates data object
	 * @param description server's MOTD
	 * @param max max players
	 * @param online online players count
	 * @param players online player list
	 * @param version version display name
	 * @param protocol protocol used by server
	 * @param icon server icon to use (64x64), or null if none
	 */
	public InternalStatusData(String description, int max, int online, PlayerInfo[] players, BufferedImage icon, String version, int protocol)
	{
		if(FormattingCodes.codes==null)
			FormattingCodes.initCodes();
		
		setDescription(description);
		
		this.players.max = max;
		this.players.online = online;
		this.players.sample = players;
		this.version.name = version;
		this.version.protocol = protocol;
		
		if(icon!=null)
		{
			setServerIcon(icon);
		}
	}
	
	/**
	 * Sets description sent to the client
	 * @param description description to set
	 */
	public void setDescription(String description) {
		String[] parts = description.split("&");
		
		List<IExtra> ext = new ArrayList<>();
		
		for(String part : parts)
		{
			if(part.length()>0)
			{
				Map<String, String> codes = FormattingCodes.codes;
				
				String code = part.substring(0,1);
				String fPart = part.substring(1);
				String color = null;
				boolean obfuscated = false;
				boolean bold = false;
				boolean strikethrough = false;
				boolean italic = false;
				
				
				if(codes.containsKey(code))
				{
					color = codes.get(code);
				}
				else
				{
					switch(code)
					{
					case "k":
					{
						obfuscated = true;
						break;
					}
					case "l":
					{
						bold = true;
						break;
					}
					case "m":
					{
						strikethrough = true;
						break;
					}
					case "o":
					{
						italic = true;
						break;
					}
					default:
					{
						fPart = part;
						break;
					}
					}
				}
				
				
				
				IExtra ex = new IExtra(fPart, color, obfuscated, bold, strikethrough, italic);
				
				ext.add(ex);
			}
		}
		
		IExtra[] extA = new IExtra[ext.size()];
		extA = ext.toArray(extA);
		
		this.description.extra = extA;
	}
	
	/**
	 * Set player list
	 * @param players player list to set
	 */
	public void setPlayers(PlayerInfo[] players) {
		this.players.sample = players;
	}
	
	/**
	 * Get player list currently displayed by this object
	 * @return array of player infos
	 */
	public PlayerInfo[] getPlayers() {
		return this.players.sample;
	}
	
	/**
	 * Set online players
	 * @param online online players count
	 */
	public void setOnlinePlayers(int online) {
		this.players.online = online;
	}
	
	/**
	 * Get online players displayed by this object
	 * @return online players count
	 */
	public int getOnlinePlayers() {
		return this.players.online;
	}
	
	/**
	 * Set maximum players
	 * @param max maximum players count
	 */
	public void setMaxPlayers(int max) {
		this.players.max = max;
	}
	
	/**
	 * Get maximum players displayed by this object
	 * @return maximum players count
	 */
	public int getMaxPlayers() {
		return this.players.max;
	}
	

	/**
	 * Get version name displayed by this object
	 * @return version name
	 */
	public String getVersionName() {
		return version.name;
	}
	
	/**
	 * Set version name displayed when client tries to ping server with incompatible version
	 * @param version version name
	 */
	public void setVersionName(String version) {
		this.version.name = version;
	}
	
	/**
	 * Get protocol used by this object
	 * @return protocol number
	 */
	public int getProtocol() {
		return version.protocol;
	}
	
	/**
	 * Set protocol used by this object
	 * @param protocol protocol number
	 */
	public void setProtocol(int protocol) {
		this.version.protocol = protocol;
	}
	
	/**
	 * Set server icon sent to client
	 * @param icon server icon image
	 */
	public void setServerIcon(BufferedImage icon) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(icon, "png", bos);
			this.favicon = "data:image/png;base64,"+Base64.encode(bos.toByteArray());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
class IDescription
{
	String text = "";
	IExtra[] extra;
	
}
class IExtra
{
	protected IExtra(String text, String color, boolean obfuscated, boolean bold, boolean strikethrough, boolean italic)
	{
		this.text = text;
		if(color!=null)
			this.color = color;
		
		this.obfuscated = obfuscated;
		this.bold = bold;
		this.strikethrough = strikethrough;
		this.italic = italic;
	}
	
	boolean obfuscated;
	boolean bold;
	boolean strikethrough;
	boolean italic;
	
	String text;
	String color;
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