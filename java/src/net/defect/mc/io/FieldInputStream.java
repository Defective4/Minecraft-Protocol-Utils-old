package net.defect.mc.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@link DataInputStream} with ability to read VarInts
 * @author Wojciech R. "Defective"
 *
 */

public class FieldInputStream extends DataInputStream {

	/**
	 * 
	 * @param is parent {@link InputStream}
	 */
	public FieldInputStream(InputStream is) {
		super(is);
	}
	/**
	 * Reads VarInt from stream<br>
	 * Snippet from wiki.vg
	 * @return read VarInt
	 * @throws IOException
	 */
	public int readVarInt() throws IOException {
	    int numRead = 0;
	    int result = 0;
	    byte read;
	    do {
	        read = readByte();
	        int value = (read & 0b01111111);
	        result |= (value << (7 * numRead));

	        numRead++;
	        if (numRead > 5) {
	            throw new RuntimeException("VarInt is too big");
	        }
	    } while ((read & 0b10000000) != 0);

	    return result;
	}
	/**
	 * Reads string from stream<br>
	 * @return read string
	 * @throws IOException
	 */
	public String readString() throws IOException
	{
		int sz = readVarInt();
		byte[] data = new byte[sz];
		readFully(data);
		return new String(data);
	}

}
