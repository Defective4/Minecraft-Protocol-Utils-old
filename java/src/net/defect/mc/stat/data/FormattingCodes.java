package net.defect.mc.stat.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Internal use only
 * @author Wojciech R. "Defective"
 */
public class FormattingCodes {
	
	private FormattingCodes() {}
	
	protected static Map<String, String> codes = null;
	protected static void initCodes()
	{
		codes = new HashMap<String, String>();
		codes.put("0", "black");
		codes.put("1", "dark_blue");
		codes.put("2", "dark_green");
		codes.put("3", "dark_aqua");
		codes.put("4", "dark_red");
		codes.put("5", "dark_purple");
		codes.put("6", "gold");
		codes.put("7", "gray");
		codes.put("8", "dark_gray");
		codes.put("9", "blue");
		codes.put("a", "green");
		codes.put("b", "aqua");
		codes.put("c", "red");
		codes.put("d", "light_purple");
		codes.put("e", "yellow");
		codes.put("f", "white");
		
	}
}
