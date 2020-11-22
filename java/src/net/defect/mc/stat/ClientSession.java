package net.defect.mc.stat;

import java.io.IOException;
import java.net.Socket;

import net.defect.mc.packets.serverbound.ChatPacket;

public class ClientSession {
	private final Socket soc;
	private final boolean compression;
	
	private final String name;
	private final String uuid;
	
	protected ClientSession(Socket soc, boolean compression, String name, String uuid) {
		this.soc = soc;
		this.compression = compression;
		this.name = name;
		this.uuid = uuid;
	}
	
	/**
	 * Send a chat message
	 * @param msg Message to send
	 * @throws IOException thrown when failed to send chat message
	 */
	public void sendChatMessage(String msg) throws IOException
	{
		soc.getOutputStream().write(new ChatPacket(msg).toByteArray(compression));
		soc.getInputStream().skip(soc.getInputStream().available());
	}
	
	/**
	 * Close this connection
	 * @throws IOException thrown when failed to close client session
	 */
	public void close() throws IOException
	{
		soc.close();
	}

	/**
	 * Get user name of session's client
	 * @return client's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get uuid of session's client
	 * @return client's id
	 */
	public String getUUID() {
		return uuid;
	}
}
