package net.defect.mc.rcon;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import net.defect.mc.packets.RconPacket;

/**
 * RconSession is used to communicate with RCON host
 * @author Wojciech R. "DefektIV"
 *
 */
public class RconSession {
	private int id;
	private Socket soc;
	private OutputStream os;
	private InputStream is;
	protected RconSession(int id, Socket soc) throws IOException
	{
		this.id = id;
		this.soc = soc;
		this.is = soc.getInputStream();
		this.os = soc.getOutputStream();
	}
	/**
	 * Send command to host
	 * @param command command to send
	 * @return command result
	 * @throws IOException when there was an error sending command
	 */
	public String sendCommand(String command) throws IOException
	{
		RconPacket packet = new RconPacket(id, 2, command.getBytes());
		os.write(packet.toByteArray());
		RconPacket response = RconReader.readPacket(is);
		return new String(response.getPayload());
	}
	/**
	 * Closes this session
	 * @throws IOException when there was error closing session
	 */
	public void closeSession() throws IOException
	{
		soc.close();
	}
	
}
