package net.defect.mc.stat.data;

import net.defect.mc.stat.MCStatus;
import net.defect.mc.stat.StatusServer;
import net.defect.mc.stat.data.FMLIStatusData.ModInfo;

/**
 * For use with {@link StatusServer}, same as {@link InternalStatusData} but additionally sends mods info to client, as if it was FML modded server 
 * @author Wojciech R. "Defective"
 *
 */
public class FMLIStatusData extends InternalStatusData {
	
	
	/**
	 * Used to represent information about Forge modification
	 * @author Wojciech R. "Defective"
	 */
	public static class ModInfo
	{
		public String modid;
		public String version;
		public ModInfo(String name, String version)
		{
			this.modid = name;
			this.version = version;
		}
	}
	
	IModInfo modinfo;
	/**
	 * Creates FML mod data object with empty mod list
	 * @param description server's MOTD
	 * @param max max players
	 * @param online online players count
	 * @param players online player list, or null if none
	 * @param protocol protocol used by server
	 * @param icon server icon to use (64x64), or null if none
	 */
	public FMLIStatusData(String description, int max, int online, PlayerInfo[] players, byte[] icon, MCStatus.Protocol protocol)
	{
		this(description,max,online,players, icon, protocol,null);
	}
	
	/**
	 * Creates FML mod data object with empty mod list
	 * @param description server's MOTD
	 * @param max max players
	 * @param online online players count
	 * @param players online player list, or null if none
	 * @param version version display name
	 * @param protocol protocol used by server
	 * @param icon server icon to use (64x64), or null if none
	 */
	public FMLIStatusData(String description, int max, int online, PlayerInfo[] players, byte[] icon, String version, int protocol)
	{
		this(description,max,online,players,icon,version,protocol,null);
	}
	
	/**
	 * Creates FML mod data object
	 * @param description server's MOTD
	 * @param max max players
	 * @param online online players count
	 * @param players online player list, or null if none
	 * @param protocol protocol used by server
	 * @param mods mod list to be sent to client
	 * @param icon server icon to use (64x64), or null if none
	 */
	public FMLIStatusData(String description, int max, int online, PlayerInfo[] players, byte[] icon, MCStatus.Protocol protocol, ModInfo[] mods)
	{
		this(description,max,online,players, icon, protocol.name,protocol.value,mods);
	}

	/**
	 * Creates FML mod data object
	 * @param description server's MOTD
	 * @param max max players
	 * @param online online players count
	 * @param players online player list, or null if none
	 * @param version version display name
	 * @param protocol protocol used by server
	 * @param mods mod list to be sent to client
	 * @param icon server icon to use (64x64), or null if none
	 */
	public FMLIStatusData(String description, int max, int online, PlayerInfo[] players, byte[] icon, String version, int protocol, ModInfo[] mods)
	{
		super(description, max, online, players, icon, version, protocol);
		if(mods==null)
			mods = new ModInfo[0];
		modinfo = new IModInfo("FML", mods);
	}
	
	
	
}
class IModInfo
{
	String type;
	ModInfo[] modList;
	protected IModInfo(String type, ModInfo[] modList)
	{
		this.type = type;
		this.modList = modList;
	}
}