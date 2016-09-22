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
package org.joo.steak.example.unitcontrol.config;

import org.joo.steak.example.unitcontrol.states.AttackState;
import org.joo.steak.example.unitcontrol.states.DefaultState;
import org.joo.steak.example.unitcontrol.states.DefendState;
import org.joo.steak.example.unitcontrol.states.IdleState;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.config.StateEngineConfigurator;
import org.joo.steak.impl.config.DefaultStateEngineConfiguration;

public abstract class AbstractConfigurator extends Object implements
		StateEngineConfigurator {

	@Override
	public StateEngineConfiguration getConfiguration() {
		DefaultStateEngineConfiguration config = new DefaultStateEngineConfiguration();
		
		configureStates(config);
		
		configureTransitions(config);
		
		return config;
	}

	protected void configureStates(DefaultStateEngineConfiguration config) {
		config.addState("default", new DefaultState());
		config.addState("attack", new AttackState());
		config.addState("defend", new DefendState());
		config.addState("idle", new IdleState());
	}

	protected abstract void configureTransitions(DefaultStateEngineConfiguration config);
}
