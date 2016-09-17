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
package org.joo.steak.framework.event;

import org.joo.steak.framework.StateContext;

/**
 * This is mainly used by <code>StateManager</code> to dispatch an event when it
 * starts/finishes
 * 
 * @author griever
 *
 */
public interface StateEngineDispatcher {

	/**
	 * Add a listener for <code>StateEngineEvent</code>
	 * 
	 * @param listener
	 *            the listener to be added
	 */
	public void addStateEngineListener(StateEngineListener listener);

	/**
	 * Remove a listener so that it will no longer receive state engine event
	 * 
	 * @param listener
	 *            the listener to be removed
	 */
	public void removeStateEngineListener(StateEngineListener listener);

	/**
	 * Dispatch an event to all listeners when the state engine starts
	 * 
	 * @param stateContext
	 *            the context
	 */
	public void dispatchStateEngineStartEvent(StateContext stateContext);

	/**
	 * Dispatch a <code>StateChangedEvent</code> to all listeners when the state
	 * about to be changed
	 * 
	 * @param event
	 *            the event
	 */
	public void dispatchStateChangeEvent(StateChangeEvent event);

	/**
	 * Dispatch a <code>StateChangedEvent</code> to all listeners when the state
	 * engine finishes
	 * 
	 * @param event
	 *            the event
	 */
	public void dispatchStateEngineFinishEvent(StateChangeEvent event);
}
