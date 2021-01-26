package net.defect.mc.stat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import net.defect.mc.event.ClientChatListener;
import net.defect.mc.io.FieldInputStream;
import net.defect.mc.packets.serverbound.ChatPacket;
import net.defect.mc.packets.serverbound.KeepAlivePacket;

public class ClientSession {
	private final Socket soc;
	private final boolean compression;

	private final String name;
	private final String uuid;

	private final List<ClientChatListener> chatListeners = new ArrayList<>();

	/**
	 * Registers listener to use when client receives chat message
	 * @param l Chat listener
	 */
	public void addChatListener(ClientChatListener l) {
		chatListeners.add(l);
	}

	/**
	 * Removes a chat listener
	 * @param l Chat listener
	 */
	public void removeChatListener(ClientChatListener l) {
		chatListeners.remove(l);
	}

	/**
	 * Removes all listeners
	 */
	public void removeChatListeners() {
		chatListeners.clear();
	}

	protected ClientSession(Socket soc, boolean compression, String name, String uuid) {
		this.soc = soc;
		this.compression = compression;
		this.name = name;
		this.uuid = uuid;

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					FieldInputStream fis = new FieldInputStream(ClientSession.this.soc.getInputStream());
					OutputStream os = ClientSession.this.soc.getOutputStream();
					while (true) {
						try {
							if (ClientSession.this.compression) {
								int plen = fis.readVarInt();
								byte[] data = new byte[plen];
								fis.readFully(data);

								FieldInputStream bis = new FieldInputStream(new ByteArrayInputStream(data));
								int dlen = bis.readVarInt();
								if (dlen == 0) {
									int pid = bis.readVarInt();
									switch (pid) {
										case 0x1F: {
											long id = bis.readLong();
											os.write(new KeepAlivePacket(id).toByteArray(true));
											break;
										}
										case 0x0F: {
											String json = bis.readString();
											for (ClientChatListener cl : chatListeners)
												cl.chatReceived(json);
											break;
										}
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Send a chat message
	 * 
	 * @param msg Message to send
	 * @throws IOException thrown when failed to send chat message
	 */
	public void sendChatMessage(String msg) throws IOException {
		soc.getOutputStream().write(new ChatPacket(msg).toByteArray(compression));
		soc.getInputStream().skip(soc.getInputStream().available());
	}

	/**
	 * Close this connection
	 * 
	 * @throws IOException thrown when failed to close client session
	 */
	public void close() throws IOException {
		soc.close();
	}

	/**
	 * Get user name of session's client
	 * 
	 * @return client's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get uuid of session's client
	 * 
	 * @return client's id
	 */
	public String getUUID() {
		return uuid;
	}
}
