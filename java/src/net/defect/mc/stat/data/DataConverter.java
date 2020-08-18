package net.defect.mc.stat.data;



public class DataConverter {
	public static InternalStatusData toIStatusData(StatusData data) throws DecodingException
	{
		
		String[] descriptionS = data.getDescription();
		String description = "";
		for(String desc : descriptionS)
			description += desc+"\n";
		int max = data.getMaxPlayers();
		int online = data.getOnlinePlayers();
		PlayerInfo[] players = data.getPlayers();
		int protocol = data.getProtocol();
		String vname = data.getVersionName();
		InternalStatusData sd = new InternalStatusData(description, max, online, players, vname,  protocol);
		return sd;
	}
}
