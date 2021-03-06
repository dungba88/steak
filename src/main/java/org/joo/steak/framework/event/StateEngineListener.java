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
 * A listener who will receive <code>StateEngineEvent</code>
 * 
 * @author griever
 *
 */
public interface StateEngineListener {

	/**
	 * Called when the state engine starts
	 * 
	 * @param event
	 *            the <code>StateEngineEvent</code>
	 */
	public void onStart(StateContext stateContext);

	/**
	 * Called before the state changed
	 * 
	 * @param event
	 * 			the event which cause the change
	 */
	public void onBeforeStateChange(StateChangeEvent event);

	/**
	 * Called after the state changed
	 * 
	 * @param event
	 * 			the event which cause the change
	 */
	public void onAfterStateChange(StateChangeEvent event);

	/**
	 * Called when the state engine finishes
	 * 
	 * @param event
	 *            the <code>StateChangedEvent</code> dispatched before the state
	 *            engine exit
	 */
	public void onFinish(StateChangeEvent event);
}
