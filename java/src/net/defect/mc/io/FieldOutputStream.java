package net.defect.mc.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * {@link DataOutputStream} with ability to write VarInts
 * @author Wojciech R. "DefektIV"
 *
 */
public class FieldOutputStream extends DataOutputStream {

	/**
	 * 
	 * @param os parent {@link OutputStream}
	 */
	public FieldOutputStream(OutputStream os) {
		super(os);
	}
	/**
	 * Writes VarInt to stream
	 * Snippet from wiki.vg
	 * @param value VarInt to write
	 * @throws IOException when writing failed
	 */
	public void writeVarInt(int value) throws IOException {
	    do {
	        byte temp = (byte)(value & 0b01111111);
	        value >>>= 7;
	        if (value != 0) {
	            temp |= 0b10000000;
	        }
	        writeByte(temp);
	    } while (value != 0);
	}
	/**
	 * Writes string to stream
	 * @param s string to write
	 * @throws IOException when writing failed
	 */
	public void writeString(String s) throws IOException
	{
		writeVarInt(s.length());
		write(s.getBytes());
	}

}
