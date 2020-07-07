package net.defect.mc.rcon;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import net.defect.mc.packets.RconPacket;

/**
 * RconReader is used for reading {@link RconPacket}s from InputStream
 * @author Wojciech R. "Defective"
 *
 */
public class RconReader {
	private RconReader() {}
	/**
	 * Read {@link RconPacket} from stream
	 * @param is Stream to read from
	 * @return Rcon packet
	 * @throws IOException
	 */
	public static RconPacket readPacket(InputStream is) throws IOException
	{
		RconPacket rp = null;
		
		byte[] intBuf = new byte[3*4];
		is.read(intBuf);
		ByteBuffer buf = ByteBuffer.wrap(intBuf).order(ByteOrder.LITTLE_ENDIAN);
		int len = buf.getInt();
		int id = buf.getInt();
		int type = buf.getInt();
		
		byte[] payload = new byte[len-8];
		is.read(payload);
		
		
		rp = new RconPacket(id, type, payload);
		
		return rp;
	}
}
