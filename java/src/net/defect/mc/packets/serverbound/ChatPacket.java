package net.defect.mc.packets.serverbound;

import net.defect.mc.packets.Packet;

public class ChatPacket extends Packet {
	public ChatPacket(String msg)
	{
		super(0x02);
		putString(msg);
	}
}
