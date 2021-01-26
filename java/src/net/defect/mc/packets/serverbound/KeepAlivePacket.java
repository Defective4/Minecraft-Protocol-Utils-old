package net.defect.mc.packets.serverbound;

import net.defect.mc.packets.Packet;

public class KeepAlivePacket extends Packet {

	public KeepAlivePacket(long id) {
		super(0x0B);
		putLong(id);
	}

}
