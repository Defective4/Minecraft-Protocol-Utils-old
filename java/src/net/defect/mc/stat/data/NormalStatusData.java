package net.defect.mc.stat.data;

/**
 * Extends {@link DescriptionlessStatusData} and adds support for server's description (MOTD)
 * @author Wojciech R. "DefektIV"
 *
 */
public class NormalStatusData extends DescriptionlessStatusData {
	protected NormalStatusData() {}
	String description;
	
	/**
	 * Gets description
	 */
	@Override
	public String[] getDescription()
	{
		return reformat(description).split("\n");
	}
}
