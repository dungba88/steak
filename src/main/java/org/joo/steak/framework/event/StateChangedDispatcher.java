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

/**
 * This is mainly used by <code>State</code> to dispatch
 * <code>StateChangedEvent</code> to <code>StateManager</code> when it finishes
 * its task and want to move on.
 * 
 * @author griever
 *
 */
public interface StateChangedDispatcher {

	/**
	 * Add a listener for <code>StateChangedEvent</code>
	 * 
	 * @param listener
	 *            the listener to be added
	 */
	public void addStateChangedListener(StateChangedListener listener);

	/**
	 * Remove a listener so that it will no longer receive
	 * <code>StateChangedEvent</code>
	 * 
	 * @param listener
	 *            the listener to be removed
	 */
	public void removeStateChangedListener(StateChangedListener listener);

	/**
	 * Dispatch a <code>StateChangedEvent</code> to all listeners
	 * 
	 * @param event
	 *            the event
	 */
	public void dispatchStateChangedEvent(StateChangeEvent event);
}
