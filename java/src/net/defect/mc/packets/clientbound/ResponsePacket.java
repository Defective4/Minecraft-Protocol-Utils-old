package net.defect.mc.packets.clientbound;



import net.defect.mc.packets.Packet;

/**
 * Packet containing JSON response for client's request
 * @author Wojciech R. "DefektIV"
 *
 */
public class ResponsePacket extends Packet {
	/**
	 * Constructs response packet
	 * @param message JSON message to send to client
	 */
	public ResponsePacket(String message)
	{
		super(0x00);
			putString(message);
	}
}
