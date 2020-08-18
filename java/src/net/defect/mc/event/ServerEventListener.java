package net.defect.mc.event;

public interface ServerEventListener {
	public void onPlayerJoin(PlayerJoinEvent e);
	public void onServerListPing(ServerListPingEvent e);
}
