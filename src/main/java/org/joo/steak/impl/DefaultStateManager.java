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

import java.util.Map;
import java.util.WeakHashMap;

import org.joo.steak.framework.State;
import org.joo.steak.framework.StateTransition;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.event.StateChangeEvent;

public class DefaultStateManager extends AbstractStateManager {

	@Override
	public void doRun() {
		super.enterState(getInitialState(), null);
	}

	@Override
	public void onStateChange(StateChangeEvent event) {
		if (event == null)
			return;
		
		checkStateIntegrity(event);

		moveNextState(event);
	}

	@Override
	protected void doInitialization(StateEngineConfiguration configuration) {

	}

	@Override
	protected Map<String, State> doInitializeStatesMap() {
		return new WeakHashMap<>();
	}

	@Override
	protected Map<String, StateTransition[]> doInitializeTransitionMap() {
		return new WeakHashMap<>();
	}

	@Override
	protected Map<String, Map<String, StateTransition[]>> doInitializeTransitionsMap() {
		return new WeakHashMap<>();
	}
}
