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
package org.joo.steak.framework.config;

import java.util.Map;

/**
 * Interface for state engine configuration It will be used by
 * <code>StateManager</code> to load the desired <code>State</code> and
 * <code>StateTransition</code>
 * 
 * @author griever
 *
 */
public interface StateEngineConfiguration {

	/**
	 * Get the state configurations. This is a map from state name to state
	 * configuration object. The configuration object can be <code>String</code>
	 * or a <code>State</code> instance. This loaded by
	 * <code>StateEngineLoader</code>
	 * 
	 * @return the state configurations
	 */
	public Map<String, Object> getStatesConfig();

	/**
	 * Get the transition configurations. This is a 2-level map from state name
	 * to state transitions. The second level map key indicates the action that
	 * triggers the state change. And the value indicates a transition list.
	 * Usually if the transition is string, it will be converted to
	 * <code>SimpleStateTransition</code>
	 * 
	 * @return
	 */
	public Map<String, Map<String, Object[]>> getTransitionsConfig();
}
