package net.defect.mc.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Special packet used in receiving/sending data from/to RCON
 * @author Wojciech R. "Defective"
 *
 */
public class RconPacket {
	public int id;
	public int type;
	public byte[] payload;
	private ByteBuffer buffer;
	/**
	 * Constructs RCON packet
	 * @param id generated Session ID
	 * @param type type of packet, 3 = login, 2 = command, 0 = command output
	 * @param payload data to send (RCON password or command)
	 */
	public RconPacket(int id, int type, byte[] payload)
	{
		this.id = id;
		this.type = type;
		this.payload = payload;
		buffer = ByteBuffer.allocate(14+payload.length).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(10+payload.length);
		buffer.putInt(id);
		buffer.putInt(type);
		buffer.put(payload);
		buffer.put(new byte[] { 0x00, 0x00 });
		
	}
	/**
	 * Gets this packet's contents as byte array<br>
	 * Useful when sending data
	 * @return byte array
	 */
	public byte[] toByteArray()
	{
		return buffer.array();
	}
	/**
	 * Useful when receiving data
	 * @return packet ID
	 */
	public int getID()
	{
		return id;
	}
	/**
	 * Useful when receiving data
	 * @return packet type
	 */
	public int getType()
	{
		return type;
	}
	/**
	 * Useful when receiving data
	 * @return packet data
	 */
	public byte[] getPayload()
	{
		return payload;
	}
}
