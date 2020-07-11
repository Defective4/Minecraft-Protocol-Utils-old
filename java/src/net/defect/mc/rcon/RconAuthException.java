package net.defect.mc.rcon;

public class RconAuthException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2930892453928691533L;

	/**
	 * Exception thrown when authentication failed (password is invalid)
	 * @param reason Exception's description
	 */
	public RconAuthException(String reason)
	{
		super(reason);
	}
}
