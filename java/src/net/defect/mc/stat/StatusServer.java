package net.defect.mc.stat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import net.defect.mc.event.PlayerJoinEvent;
import net.defect.mc.event.ServerEventListener;
import net.defect.mc.event.ServerListPingEvent;
import net.defect.mc.io.FieldInputStream;
import net.defect.mc.io.FieldOutputStream;
import net.defect.mc.packets.Packet;
import net.defect.mc.packets.clientbound.PongPacket;
import net.defect.mc.packets.clientbound.ResponsePacket;
import net.defect.mc.stat.data.InternalStatusData;

/**
 * Status Server provides status data to Minecraft client<br>
 * You can use this to create server that will display data on Minecraft server list
 * @author Wojciech R. "DefektIV"
 *
 */
public class StatusServer {
	private String host;
	private int port;
	private InternalStatusData data;
	private String joinResponse = "Disconnected";
	private long fp = 0;
	
	private List<ServerEventListener> listeners = new ArrayList<ServerEventListener>();
	
	protected StatusServer(String host, int port, InternalStatusData data)
	{
		this.port = port;
		this.host = host;
		this.data = data;
	}
	/**
	 * Sets amount of time to wait before sending response (default is 0)
	 * @param val ping in ms
	 */
	public void setFakePing(long val)
	{
		this.fp = val;
	}
	/**
	 * Sets hostname
	 * @param host server's hostname to use
	 */
	public void setHost(String host)
	{
		this.host = host;
	}
	/**
	 * Sets server's port
	 * @param port port to use
	 */
	public void setPort(int port)
	{
		this.port = port;
	}
	/**
	 * Sets message displayed when user tries to connect to this server
	 * @param msg message
	 */
	public void setJoinMessage(String msg)
	{
		this.joinResponse = msg;
	}
	/**
	 * Sets data sent by this server
	 * @param data {@link InternalStatusData} object containing data
	 */
	public void setData(InternalStatusData data)
	{
		this.data = data;
	}
	private boolean running = false;
	
	/**
	 * Adds an event listener to this server
	 * @param e
	 */
	public void addEventListener(ServerEventListener e)
	{
		listeners.add(e);
	}
	/**
	 * Removes event listener from this server
	 * @param e
	 */
	public void removeEventListener(ServerEventListener e)
	{
		listeners.remove(e);
	}
	
	/**
	 * Stops the server
	 */
	public void stop()
	{
		try
		{
			Socket soc = new Socket();
			soc.connect(new InetSocketAddress(host, port));
			soc.close();
		}
		catch(Exception e) {}
		running = false;
	}
	/**
	 * Binds server to specified address and starts accepting connections
	 * @throws IOException when there was error creating server
	 */
	public void start() throws IOException
	{
		running = true;
		final ServerSocket srv = new ServerSocket();
		if(host==null)
			srv.bind(new InetSocketAddress(25565));
		else
			srv.bind(new InetSocketAddress(host, port));
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(running)
				{
					try
					{
						int pid;
						Socket soc = srv.accept();
						FieldInputStream is = new FieldInputStream(soc.getInputStream());
						FieldOutputStream os = new FieldOutputStream(soc.getOutputStream());
						is.readVarInt();
						pid = is.readVarInt();
						if(pid==0x00)
						{
							int protocol = is.readVarInt();
							is.readString();
							is.readShort();
							int state = is.readVarInt();
							
							
							if(state==1)
							{
								ServerListPingEvent pe = new ServerListPingEvent(soc.getInetAddress().getHostAddress(), protocol);
								for(ServerEventListener sel : listeners)
								{
									sel.onServerListPing(pe);
								}
								is.readVarInt();
								pid = is.readVarInt();
								if(pid==0x00)
								{
									if(data.getProtocol()==-1)
										data.setProtocol(protocol);
									String json = new Gson().toJson(data, data.getClass());
									Packet response = new ResponsePacket(json);
									Thread.sleep(fp);
									os.write(response.toByteArray());
									
									is.readVarInt();
									pid = is.readVarInt();
									if(pid==0x01)
									{
										long payload = is.readLong();
										Thread.sleep(fp);
										os.write(new PongPacket(payload).toByteArray());
									}
								}
							}
							else
							{
								is.readVarInt();
								int ppid = is.readVarInt();
								if(ppid==0x00)
								{
									String username = is.readString();
									PlayerJoinEvent pje = new PlayerJoinEvent(username, soc.getInetAddress().getHostAddress(), protocol);
									for(ServerEventListener se : listeners)
									{
										se.onPlayerJoin(pje);
									}
									os.write(new ResponsePacket("{\"text\":\""+joinResponse+"\"}").toByteArray());
								}
							}
						}
						
						
						soc.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				try {
					srv.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
