package org.joo.state.framework;

/**
 * This exception is thrown in initialization method of 
 * <code>StateManager</code> or some other classes.
 * It indicates that something wrong when loading configuration.
 * @author griever
 *
 */
public class StateInitializationException extends RuntimeException {

	private static final long serialVersionUID = -4488369581488070303L;
	
	public StateInitializationException(String msg) {
		super(msg);
	}

	public StateInitializationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
