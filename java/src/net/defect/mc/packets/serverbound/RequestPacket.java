package net.defect.mc.packets.serverbound;

import net.defect.mc.packets.Packet;

/**
 * Used to request status data from server
 * @author Wojciech R. "DefektIV"
 *
 */
public class RequestPacket extends Packet {
	/**
	 * Constructs request packet
	 */
	public RequestPacket()
	{
		super(0x00);
	}
}
