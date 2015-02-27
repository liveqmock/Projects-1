package com.newsoft.foundation.cache;

/**
 * Define the generic exception when implement the cache function.
 * 
 * @author George Guo
 * 
 */
public class CacheException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7733111136782014643L;

	/**
	 * Constructor for the CacheException object.
	 */
	public CacheException() {
		super();
	}

	/**
	 * Constructor for the CacheException object.
	 * 
	 * @param message
	 *            the exception detail message
	 */
	public CacheException(String message) {
		super(message);
	}

	/**
	 * Constructs a new CacheException with the specified detail message and
	 * cause.
	 * <p>
	 * Note that the detail message associated with <code>cause</code> is
	 * <i>not</i> automatically incorporated in this runtime exception's detail
	 * message.
	 * 
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            {@link #getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 * @since 1.2.4
	 */
	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new CacheException with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which
	 * typically contains the class and detail message of <tt>cause</tt>). This
	 * constructor is useful for runtime exceptions that are little more than
	 * wrappers for other throwables.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 * @since 1.2.4
	 */
	public CacheException(Throwable cause) {
		super(cause);
	}
}
