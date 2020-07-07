package net.defect.mc.stat.data;

/**
 * Extends {@link DescriptionlessStatusData} and add support for server's description (MOTD)
 * @author Wojciech R. "Defective"
 *
 */
public class ExtraStatusData extends DescriptionlessStatusData {
	
	protected ExtraStatusData() {}
	
	Description description;
	/**
	 * Gets server description
	 */
	@Override
	public String[] getDescription()
	{
		if(description!=null)
		{
			if(description.text.length()==0)
			{
				String dsc = "";
				for(Extra extra : description.extra)
				{
					dsc += extra.text;
				}
				
				return reformat(dsc).split("\n");
			}
			else
			{
				return reformat(description.text).split("\n");
			}
		}
		else
			return null;
	}
}
class Description
{
	String text;
	Extra[] extra;
}
class Extra
{
	String text;
}