package net.defect.mc.packets.serverbound;

import net.defect.mc.packets.Packet;
import net.defect.mc.stat.MCStatus.Protocol;

/**
 * Packet used to switch server to target state
 * @author Wojciech R. "DefektIV"
 *
 */
public class HandshakePacket extends Packet {
	/**
	 * Constructs handshake packet
	 * @param protocol protocol used in connection. See {@link Protocol}
	 * @param host server's hostname
	 * @param port server's port
	 * @param state next state (1 = status, 2 = login)
	 */
	public HandshakePacket(int protocol, String host, int port, int state)
	{
		super(0x00);
		putVarInt(protocol);
		putString(host);
		putShort(port);
		putVarInt(state);
	}
}
