package net.defect.mc.rcon;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

import net.defect.mc.packets.RconPacket;

/**
 * Class for creating RCON connections
 * @author Wojciech R. "Defective"
 *
 */
public class Rcon {
	private int requestID;
	public Rcon()
	{
		requestID = new Random().nextInt(Integer.MAX_VALUE);
	}
	/**
	 * Connect to target RCON host
	 * @param host server's hostnae
	 * @param port RCON port
	 * @param password RCON password
	 * @return rcon session used to communicate with RCON
	 * @throws IOException
	 * @throws RconAuthException thrown when password is invalid
	 */
	public RconSession connect(String host, int port, String password) throws IOException, RconAuthException
	{
		Socket soc = new Socket();
		soc.connect(new InetSocketAddress(host, port));
		OutputStream os = soc.getOutputStream();
		InputStream is = soc.getInputStream();
		
		os.write(new RconPacket(requestID, 3, password.getBytes()).toByteArray());
		
		RconPacket response = RconReader.readPacket(is);
		
		int responseID = response.getID();
		if(responseID==-1) {
			soc.close();
			throw new RconAuthException("Rcon Login failed!");
		}
		return new RconSession(response.getID(), soc);
	}
}
