/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.joo.steak.impl;

import org.joo.steak.framework.StateTransition;
import org.joo.steak.framework.TransitionContext;
import org.joo.steak.framework.exception.StateInitializationException;

public abstract class AbstractTransitionProxy implements StateTransition {

	private String className;

	public AbstractTransitionProxy(String className) {
		this.className = className;
	}

	@Override
	public boolean isSatisfiedBy(TransitionContext transitionContext) {
		StateTransition loadedTransition = getTransition();
		return loadedTransition.isSatisfiedBy(transitionContext);
	}

	@Override
	public String getNextState() {
		StateTransition loadedTransition = getTransition();
		return loadedTransition.getNextState();
	}

	protected abstract StateTransition getTransition();

	protected final StateTransition loadTransition() {
		try {
			return (StateTransition) ReflectionUtils.loadClass(className);
		} catch (ClassCastException e) {
			throw new StateInitializationException("Class " + className + " is not subclass of State");
		}
	}
}