# Minecraft Utils
![License](https://img.shields.io/github/license/Defective4/Minecraft-utils)

A Java library containing some utilities to communicate with Minecraft server (RCON, status, query)

# Functions
- Reading Minecraft server status (MOTD, players, protocol, version name, etc.)
- Reading Minecraft query (Gametype, Map name)
- Connecting to RCON
- Creating server which is not playable but can show some information on Minecraft server list
- Joining Minecraft server (since v1.1)

# Examples

### Get server status:
```java
StatusData data = MCStatus.getStatus("localhost", 25565, MCStatus.Protocol.V1_12_2);
String[] motdArray = data.getDescription();
String motd = "";
for(String m : motdArray)
	motd += m;
String version = data.getVersionName();
int protocol = data.getProtocol();
int online = data.getOnlinePlayers();
int max = data.getMaxPlayers();
long ping = data.getPing();
			
System.out.println("MOTD: "+motd);
System.out.println("Version name: "+version);
System.out.println("Protocol: "+protocol);
System.out.println("Players: "+online+"/"+max);
System.out.println("Ping: "+ping);
```
Example output:
```
MOTD: A Minecraft Server
Version name: 1.12.2
Protocol: 340
Players: 0/100
Ping: 25
```


### Legacy Server List Ping (For servers below version 1.7):
```java
SimpleStatusData data = MCStatus.getSimpleStatus("localhost", 25565);
String motd = data.getDescription()[0];
int max = data.getMaxPlayers();
int online = data.getOnlinePlayers();
long ping = data.getPing();
			
System.out.println("MOTD: "+motd);
System.out.println("Players: "+online+"/"+max);
System.out.println("Ping: "+ping);
```
Example output:
```
MOTD:  Minecraft Server
Players: 0/100
Ping: 96
```


### Query:
```java
QueryData data = MCStatus.query("localhost", 25565);
String gametype = data.getGametype();
String map = data.getMap();
String motd = data.getMotd();
int online = data.getOnlinePlayers();
int max = data.getMaxPlayers();
			
System.out.println("Gametype: "+gametype);
System.out.println("Map: "+map);
System.out.println("MOTD: "+motd);
System.out.println("Players: "+online+"/"+max);
```
Example output:
```
Gametype: SMP
Map: world
MOTD: A Minecraft Server
Players: 0/100
```


### RCON:
```java
String password = "password";
String cmd = "list";
			
Rcon rcon = new Rcon();
try
{
	RconSession ses = rcon.connect("localhost", 25575, password);
	String result = ses.sendCommand(cmd);
	ses.closeSession();
	
	System.out.println(result);
}
catch(RconAuthException e)
{
	System.out.println("Authentication failed, bad password!");
}
```
Example output:
```
There are 0/100 players online: 
```


# List to do
- [x] Minecraft status
  - [x] Client
  - [x] Server
    - [ ] Legacy Server List Ping support
- [x] Minecraft Query
  - [x] Simple stat
  - [ ] Full stat
- [x] Minecraft RCON
  - [x] Client
  - [ ] \(Optional) Server
- [x] Joining Minecraft server
  - [ ] Chat support
- [ ] Partial port to other languages (PHP)
