package net.defect.mc.packets.clientbound;

import net.defect.mc.packets.Packet;

/**
 * Packet containing response to client's Ping packet
 * @author Wojciech R. "DefektIV"
 *
 */
public class PongPacket extends Packet {
	/**
	 * Constructs Pong packet
	 * @param payload payload received from client
	 */
	public PongPacket(long payload)
	{
		super(0x01);
		putLong(payload);
	}
}
