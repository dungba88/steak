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
package org.joo.steak.framework;

import org.joo.steak.framework.event.StateChangedDispatcher;
import org.joo.steak.framework.event.StateChangeEvent;

/**
 * A state is an independent object which can perform business logic upon some
 * data. The data will be passed as <code>stateContext</code>. It can notify the
 * <code>StateManager</code> when it finishes its logic to move to next state by
 * calling <code>fireStateChangedEvent</code> A state should not concern about
 * how other states actually work. A state can be assigned an unique ID in
 * <code>StateManager</code>
 * 
 * @author griever
 *
 */
public interface State extends StateChangedDispatcher {
	
	/**
	 * Called by <code>StateManager</code> when reaching the current state
	 * 
	 * @param stateContext
	 *            the state context which may contains data to be processed
	 * @param event
	 *            the event dispatched by last state when it finishes
	 */
	public void onEntry(StateContext stateContext, StateChangeEvent event);

	/**
	 * Called by <code>StateManager</code> when exiting the current state
	 * 
	 * @param event
	 *            the original event dispatched by this state when changing
	 *            state
	 */
	public void onExit(StateChangeEvent event);
}
