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

import org.joo.steak.example.unitcontrol.transitions.CriticalHealthTransition;
import org.joo.steak.impl.config.DefaultStateEngineConfiguration;

public class DefensiveConfigurator extends AbstractConfigurator {

	@Override
	protected void configureTransitions(DefaultStateEngineConfiguration config) {
		config.addTransition("default", "done", "defend");
		config.addTransition("*", "attack", "attack");
		config.addTransitions("default", "attacked", new Object[] { new CriticalHealthTransition("idle"), "defend" });
		config.addTransition("attack", "done", "defend");
		config.addTransition("attack", "attacked", "defend");
		config.addTransition("defend", "done", "idle");
		config.addTransition("defend", "attacked", "idle");
		config.addTransitions("idle", "done", new Object[] { new CriticalHealthTransition("idle"), "attack" });
		config.addTransitions("idle", "attacked", new Object[] { new CriticalHealthTransition("idle"), "attack" });
	}
}
