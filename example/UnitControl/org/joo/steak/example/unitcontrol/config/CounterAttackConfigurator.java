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

import org.joo.steak.example.unitcontrol.states.CounterAttackState;
import org.joo.steak.example.unitcontrol.transitions.CriticalHealthTransition;
import org.joo.steak.impl.config.DefaultStateEngineConfiguration;

public class CounterAttackConfigurator extends AbstractConfigurator {
	
	protected void configureStates(DefaultStateEngineConfiguration config) {
		super.configureStates(config);
		config.addState("counterattack", new CounterAttackState());
	}

	@Override
	protected void configureTransitions(DefaultStateEngineConfiguration config) {
		config.addTransition("default", "done", "defend");
		config.addTransition("*", "attack", "attack");
		config.addTransitions("*", "attacked", new Object[] { new CriticalHealthTransition("idle"), "counterattack" });
		config.addTransitions("attack", "done", new Object[] { new CriticalHealthTransition("idle"), "attack" });
		config.addTransitions("defend", "done", new Object[] { new CriticalHealthTransition("idle"), "attack" });
		config.addTransitions("idle", "done", new Object[] { new CriticalHealthTransition("idle"), "attack" });
	}
}
