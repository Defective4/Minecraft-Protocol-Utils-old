package net.defect.mc.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.defect.mc.io.FieldOutputStream;

/**
 * Base class for all Minecraft packets
 * @author Wojciech R. "Defective"
 *
 */
public class Packet {
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	FieldOutputStream fos = new FieldOutputStream(buffer);
	
	
	protected Packet(int id)
	{
		try {
			fos.writeVarInt(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void putVarInt(int i)
	{
		try {
			fos.writeVarInt(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void putShort(int s)
	{
		try {
			fos.writeShort(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void putString(String s)
	{
		try {
			fos.writeString(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void putLong(long l)
	{
		try {
			fos.writeLong(l);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns this packet's data as byte array
	 * @return packet data
	 */
	public byte[] toByteArray()
	{
		byte[] data = buffer.toByteArray();
		ByteArrayOutputStream buf2 = new ByteArrayOutputStream();
		FieldOutputStream fos2 = new FieldOutputStream(buf2);
		try
		{
			fos2.writeVarInt(data.length);
			fos2.write(data);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return buf2.toByteArray();
	}
}
