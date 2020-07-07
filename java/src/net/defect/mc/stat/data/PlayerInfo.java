package net.defect.mc.stat.data;

/**
 * Object containing info about certain player
 * @author Wojciech R. "Defective"
 *
 */
public class PlayerInfo {
	protected PlayerInfo() {}
	String name;
	String id;
	/**
	 * Constructs object with given parameters
	 * @param name Player's username
	 * @param id Player ID
	 */
	public PlayerInfo(String name, String id)
	{
		this.name = name;
		this.id = id;
	}
	/**
	 * Get player's name
	 * @return Player's username
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Get player's ID
	 * @return Player's ID
	 */
	public String getID()
	{
		return id;
	}
}
