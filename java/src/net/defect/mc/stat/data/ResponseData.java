package net.defect.mc.stat.data;

/**
 * Container for misc. text data received from server
 * @author Wojciech R. "Defective"
 *
 */
public class ResponseData {
	String text = "";
	String translate = "";
	protected ResponseData() {}
	public String getData()
	{
		return text.length()>0 ? text : translate;
	}
}
