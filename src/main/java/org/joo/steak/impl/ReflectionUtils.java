package org.joo.steak.impl;

import org.joo.steak.framework.StateInitializationException;

public class ReflectionUtils {

	public static Object loadClass(String className) {
		try {
			Class<?> stateClass = Class.forName(className);
			Object loadedState = stateClass.newInstance();
			return loadedState;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new StateInitializationException("Cannot initialize class " + className, e);
		}
	}
}
