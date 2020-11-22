package net.defect.mc.stat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

import com.google.gson.Gson;

import net.defect.mc.io.FieldInputStream;
import net.defect.mc.packets.Packet;
import net.defect.mc.packets.serverbound.HandshakePacket;
import net.defect.mc.packets.serverbound.LoginPacket;
import net.defect.mc.packets.serverbound.RequestPacket;
import net.defect.mc.stat.data.ExtraStatusData;
import net.defect.mc.stat.data.InternalStatusData;
import net.defect.mc.stat.data.NormalStatusData;
import net.defect.mc.stat.data.QueryData;
import net.defect.mc.stat.data.ResponseData;
import net.defect.mc.stat.data.SimpleStatusData;
import net.defect.mc.stat.data.StatusData;

/**
 * Core class containing most of the library's functionality
 * @author Wojciech R. "DefektIV"
 * @version 1.3
 */
public class MCStatus {
	
	private static Proxy p = null;
	
	/**
	 * Protocol constants for main versions
	 * @author Wojciech R. "DefektIV"
	 *
	 */
	public enum Protocol
	{
		/**
		 * Client Out of Date (Protocol number 999)<br>
		 * Used by {@link StatusServer} to indicate that the client is out of date<br>
		 * This forces version name to be displayed on client's server list<br>
		 * You should not use this with {@link MCStatus} as server may refuse to send further data
		 */
		COD(999, ""),
		/**
		 * Server Out of Date (Protocol number 1)<br>
		 * Used by {@link StatusServer} to indicate that the server is out of date<br>
		 * This forces version name to be displayed on client's server list<br>
		 * You should not use this with {@link MCStatus} as server may refuse to send further data
		 */
		SOD(1,""),
		/**
		 * Indicates that {@link StatusServer} should automatically detect client's protocol<br>
		 * Since 1.1 it can also be used with {@link MCStatus}'s login() method for client to try to automatically obtain server's protocol<br>
		 * Do NOT use this with {@link MCStatus}'s getStatus() method!
		 */
		UNIVERSAL(-1,"All"),
		/**
		 * Protocol for version 1.16.1
		 */
		V1_16_1(736,"1.16.1"),
		/**
		 * Protocol for version 1.16
		 */
		V1_16(735,"1.16"),
		/**
		 * Protocol for version 1.15.2
		 */
		V1_15_2(578,"1.15.2"),
		/**
		 * Protocol for version 1.15.1
		 */
		V1_15_1(575,"1.15.1"),
		/**
		 * Protocol for version 1.15
		 */
		V1_15(573,"1.15"),
		/**
		 * Protocol for version 1.14.4
		 */
		V1_14_4(498,"1.14.4"),
		/**
		 * Protocol for version 1.14.3
		 */
		V1_14_3(490,"1.14.3"),
		/**
		 * Protocol for version 1.14.2
		 */
		V1_14_2(485,"1.14.2"),
		/**
		 * Protocol for version 1.14.1
		 */
		V1_14_1(480,"1.14.1"),
		/**
		 * Protocol for version 1.14
		 */
		V1_14(477,"1.14"),
		/**
		 * Protocol for version 1.13.2
		 */
		V1_13_2(404,"1.13.2"),
		/**
		 * Protocol for version 1.13.1
		 */
		V1_13_1(401,"1.13.1"),
		/**
		 * Protocol for version 1.13
		 */
		V1_13(393,"1.13"),
		/**
		 * Protocol for version 1.12.2
		 */
		V1_12_2(340,"1.12.2"),
		/**
		 * Protocol for version 1.12.1
		 */
		V1_12_1(338,"1.12.1"),
		/**
		 * Protocol for version 1.12
		 */
		V1_12(335,"1.12"),
		/**
		 * Protocol for version 1.11.X
		 */
		V1_11_X(316,"1.11.X"),
		/**
		 * Protocol for version 1.11
		 */
		V1_11(315,"1.11"),
		/**
		 * Protocol for version 1.10
		 */
		V1_10(210,"1.10"),
		/**
		 * Protocol for version 1.9.4
		 */
		V1_9_4(110,"1.9.4"),
		/**
		 * Protocol for version 1.9.2
		 */
		V1_9_2(108,"1.9.2"),
		/**
		 * Protocol for version 1.9
		 */
		V1_9(107,"1.9"),
		/**
		 * Protocol for version 1.8
		 */
		V1_8(47,"1.8"),
		/**
		 * Protocol for version 1.7.10
		 */
		V1_7_10(5,"1.7.10"),
		/**
		 * Protocol for version 1.7.2
		 */
		V1_7_2(4,"1.7.2");
		
		
		/**
		 * Protocol version value
		 */
		public int value;
		/**
		 * Version's name
		 */
		public String name;
		
		private Protocol(int value, String name)
		{
			this.name = name;
			this.value = value;
		}
	}
	
	private static int defaultProtocol = 340;
	/**
	 * Highest protocol value supported.
	 */
	public static int maxProtocol = 736;
	
	protected MCStatus() {}
	
	
	/**
	 * Creates {@link StatusServer} bound to specified host and port
	 * @param host server' hostname
	 * @param port server's port
	 * @param data data for server to send
	 * @return Created {@link StatusServer}
	 */
	public static StatusServer createServer(String host, int port, InternalStatusData data)
	{
		return new StatusServer(host, port, data);
	}
	/**
	 * Creates {@link StatusServer} bound to specified port
	 * @param port server's port
	 * @param data data for server to send
	 * @return Created {@link StatusServer}
	 */
	public static StatusServer createServer(int port, InternalStatusData data)
	{
		return createServer(null, port, data);
	}
	/**
	 * Creates {@link StatusServer} bound to specified host and port
	 * @param host server's hostname
	 * @param port server's port
	 * @return Created {@link StatusServer}
	 */
	public static StatusServer createServer(String host, int port)
	{
		return createServer(host, port, null);
	}
	/**
	 * Creates {@link StatusServer} bound to specified port
	 * @param port server's port
	 * @return Created {@link StatusServer}
	 */
	public static StatusServer createServer(int port)
	{
		return createServer(null, port, null);
	}
	
	
	/**
	 * Sends Query request to target server<br>
	 * Server must have enabled "enable-query" in server.properties
	 * @param host target hostname
	 * @param port server's host
	 * @return {@link QueryData} object containing data received from server
	 * @throws IOException when there was error querying server
	 */
	public static QueryData query(String host, int port) throws IOException
	{
		DatagramSocket soc = new DatagramSocket();
		soc.connect(new InetSocketAddress(host, port));
		byte[] response = new byte[10240];
		
		byte[] request = new byte[] {
				(byte)0xFE, (byte)0xFD,
				0x09,
				0x00, 0x00, 0x00, 0x01
		};
		DatagramPacket requestPacket = new DatagramPacket(request, request.length);
		soc.send(requestPacket);
		
		DatagramPacket responsePacket = new DatagramPacket(response, response.length);
		soc.receive(responsePacket);
		
		byte[] tokenBytes = new byte[11];
		for(int x=5;response[x]!=0;x++)
		{
			tokenBytes[x-5] = response[x];
		}
		
		int token = Integer.parseInt(new String(tokenBytes).trim());
		
		request = new byte[]
		{
				(byte)0xFE, (byte)0xFD,
				0x00,
				0x00, 0x00, 0x00, 0x01,
				(byte) (token >> 24), (byte) (token >> 16), (byte) (token >> 8), (byte) token
		};
		
		requestPacket = new DatagramPacket(request, request.length);
		soc.send(requestPacket);
		
		responsePacket = new DatagramPacket(response, response.length);
		soc.receive(responsePacket);
		
		soc.close();
		
		String[] data = new String(response).trim().split("\0");
		QueryData dt = new QueryData(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
		return dt;
	}
	
	/**
	 * Set proxy to use in connections to servers
	 * @param p Proxy to use
	 */
	public static void setProxy(Proxy p)
	{
		MCStatus.p = p;
	}
	
	/**
	 * Joins the target server
	 * @param host server's hostname
	 * @param port server's port
	 * @param username client's username
	 * @param protocol protocol used to connect to server
	 * @return socket connected to server
	 * @throws IOException thrown when there was any error during logging in
	 */
	public static ClientSession login(String host, int port, String username, Protocol protocol) throws IOException
	{
		return login(host, port, username, protocol.value);
	}
	/**
	 * Joins the target server
	 * @param host server's hostname
	 * @param port server's port
	 * @param username client's username
	 * @param protocol protocol used to connect. When MCStatus.Protocol.UNIVERSAL is used, client will attempt to detect server's protocol.
	 * @return socket connected to server
	 * @throws IOException thrown when there was any error during logging in
	 */
	public static ClientSession login(String host, int port, String username, int protocol) throws IOException
	{
		if(protocol==-1)
			protocol = getStatus(host, port, MCStatus.defaultProtocol).getProtocol();
		if(protocol==-1)
			throw new IOException("Server didn't send valid protocol version! Can't start login process.");
		
		Socket soc = p==null ? new Socket() : new Socket(p);
		soc.connect(new InetSocketAddress(host, port));
		
		OutputStream os = soc.getOutputStream();
		FieldInputStream is = new FieldInputStream(soc.getInputStream());
		
		Packet handshake = new HandshakePacket(protocol, host, port, 2);
		Packet login = new LoginPacket(username);
		
		os.write(handshake.toByteArray());
		os.write(login.toByteArray());
		
		is.readVarInt();
		int id = is.readVarInt();
		
		boolean compression = false;
		
		if(id==0x00)
		{
			byte[] data = new byte[is.readVarInt()];
			is.readFully(data);
			soc.close();
			String json = new String(data);
			ResponseData rcv = new Gson().fromJson(json, ResponseData.class);
			String response = rcv.getData();
			throw new IOException("Could not join the server: "+response);
		}
		else if(id==0x03)
		{
			compression = is.readVarInt()!=-1;
		}
		
		is.readVarInt();
		if(compression)
			is.readVarInt();
		id = is.readVarInt();
		if(id==0x02) {
			byte[] uuidB = new byte[is.readVarInt()];
			is.readFully(uuidB);
			byte[] nameB = new byte[is.readVarInt()];
			is.readFully(nameB);
			
			String uuid = new String(uuidB);
			String name = new String(nameB);
			
			return new ClientSession(soc, compression, name, uuid);
		}
		else
		{
			soc.close();
			throw new IOException("Login Success not received!");
		}
		
	}
	
	/**
	 * Gets target server's status data
	 * @param host server's hostname
	 * @param port server's port
	 * @param protocol {@link Protocol} used to connect to this server
	 * @return {@link StatusData} object containing received data
	 * @throws IOException when there was error retrieving server status
	 */
	public static StatusData getStatus(String host, int port, Protocol protocol) throws IOException
	{
		if(protocol==Protocol.UNIVERSAL)
			throw new IOException("You can't use UNIVERSAL protocol in status query!");
		return getStatus(host, port, protocol.value);
	}
	/**
	 * Gets target server's status data
	 * @param host server's hostname
	 * @param port server's port
	 * @param protocol value of protocol used to connect to this server
	 * @return {@link StatusData} object containing received data
	 * @throws IOException when there was error retrieving server status
	 */
	public static StatusData getStatus(String host, int port, int protocol) throws IOException
	{
		Socket soc = p==null ? new Socket() : new Socket(p);
		long start = System.currentTimeMillis();
		soc.connect(new InetSocketAddress(host, port));
		long ping = System.currentTimeMillis() - start;
		
		FieldInputStream is = new FieldInputStream(soc.getInputStream());
		OutputStream os = soc.getOutputStream();
		Packet handshake = new HandshakePacket(protocol, host, port, 1);
		os.write(handshake.toByteArray());
		os.write(new RequestPacket().toByteArray());
		
		is.readVarInt();
		int packetID = is.readVarInt();
		if(packetID!=0x00) {
			soc.close();
			System.out.println(packetID);
			throw new IOException("Bad packet ID received!");
		}
		int size = is.readVarInt();
		byte[] data = new byte[size];
		is.readFully(data);
		soc.close();
		String json = new String(data);
		StatusData status;
		try
		{
			status = new Gson().fromJson(json, NormalStatusData.class);
		}
		catch(Exception e)
		{
			status = new Gson().fromJson(json, ExtraStatusData.class);
		}
		status.setPing(ping);
		return status;
	}
	/**
	 * @deprecated
	 * @param host target host
	 * @param port server's port
	 * @return {@link StatusData} object containing received data
	 * @throws IOException  when there was error retrieving server status
	 */
	public static StatusData getStatus(String host, int port) throws IOException
	{
		return getStatus(host, port, defaultProtocol);
	}
	
	/**
	 * Performs legacy server list ping<br>
	 * Use this to get status of servers before Netty rewrite (version older than 1.7)
	 * @param host target host
	 * @param port server's port
	 * @return {@link SimpleStatusData} object containing received data
	 * @throws IOException  when there was error retrieving server status
	 */
	public static SimpleStatusData getSimpleStatus(String host, int port) throws IOException
	{
		Socket soc = p==null ? new Socket() : new Socket(p);
		long start = System.currentTimeMillis();
		soc.connect(new InetSocketAddress(host, port));
		long ping = System.currentTimeMillis() - start;
		
		DataInputStream dis = new DataInputStream(soc.getInputStream());
		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
		
		dos.writeByte(0xFE);
		
		int packetID = dis.readUnsignedByte();
		if(packetID!=0xFF) {
			soc.close();
			throw new IOException("Bad packet ID received!");
		}
		int len = dis.readShort();
		byte[] dataBytes = new byte[len*2];
		dis.readFully(dataBytes);
		soc.close();
		String[] data = new String(dataBytes,"utf16").split("\u00A7");
		String motd = "";
		int max = Integer.parseInt(data[data.length-1]);
		int online = Integer.parseInt(data[data.length-2]);
		if(data.length>2)
		{
			for(int x=0;x<data.length-2;x++)
			{
				motd+= data[x].length()>0 ? data[x].substring(1) : "";
			}
		}
		return new SimpleStatusData(online, max, ping, motd);
	}
}
