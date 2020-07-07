package net.defect.mc.stat.data;

public class DecodingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1476631548631309499L;
	private Exception cause;
	public DecodingException(Exception cause, String text)
	{
		super(text);
		this.cause = cause;
	}
	public Exception getCause()
	{
		return cause;
	}
}
