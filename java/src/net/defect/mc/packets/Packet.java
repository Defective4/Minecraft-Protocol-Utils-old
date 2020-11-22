package net.defect.mc.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.defect.mc.io.FieldOutputStream;

/**
 * Base class for all Minecraft packets
 * @author Wojciech R. "DefektIV"
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
	 * Returns this packet's data as byte array with standard packet format
	 * @return packet data
	 */
	public byte[] toByteArray()
	{
		return toByteArray(false);
	}
	/**
	 * Returns this packet's data as byte array
	 * @return packet data
	 * @param postCompression determines packet's post compression format status. By default is't false
	 */
	public byte[] toByteArray(boolean postCompression)
	{
		byte[] data = buffer.toByteArray();
		int len = data.length;
		if(postCompression) len++;
		ByteArrayOutputStream buf2 = new ByteArrayOutputStream();
		FieldOutputStream fos2 = new FieldOutputStream(buf2);
		try
		{
			fos2.writeVarInt(len);
			if(postCompression)
				fos2.writeVarInt(0);
			fos2.write(data);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return buf2.toByteArray();
	}
}
