package net.defect.mc.stat.data;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		if(FormattingCodes.codes==null)
			FormattingCodes.initCodes();
		
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
					}
				}
				
				
				
				
				
				IExtra ex = new IExtra(fPart, color, obfuscated, bold, strikethrough, italic);
				
				ext.add(ex);
			}
		}
		
		IExtra[] extA = new IExtra[ext.size()];
		extA = ext.toArray(extA);
		
		this.description.extra = extA;
		
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