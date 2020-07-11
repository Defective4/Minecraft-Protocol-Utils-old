package net.defect.mc.packets.serverbound;

import net.defect.mc.packets.Packet;

/**
 * Packet used for logging in to server
 * @author Wojciech R. "DefektIV"
 *
 */
public class LoginPacket extends Packet {
	/**
	 * Constructs login packet
	 * @param username Client's username
	 */
	public LoginPacket(String username)
	{
		super(0x00);
		putString(username);
	}
}
