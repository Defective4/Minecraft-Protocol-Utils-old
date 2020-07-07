package net.defect.mc.packets.serverbound;

import net.defect.mc.packets.Packet;

/**
 * Yet unused packet<br>
 * Used for logging in to server
 * @author Wojciech R. "Defective"
 *
 */
public class LoginPacket extends Packet {
	/**
	 * Constructs login packet
	 * @param username
	 */
	public LoginPacket(String username)
	{
		super(0x00);
		putString(username);
	}
}
