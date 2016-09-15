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

import java.util.Map;

/**
 * A state context is an object to be passed amongst <code>State</code>. It
 * should be unique for each run of <code>StateManager</code>.
 * 
 * @author griever
 *
 */
public interface StateContext {
	
	/**
	 * The UUID for this StateContext instance
	 * @return the UUID
	 */
	public String getUUID();

	/**
	 * Get the initial state
	 * 
	 * @return the initial state
	 */
	public String getInitialState();

	/**
	 * Get context map which may contains additional data
	 * 
	 * @return the context map
	 */
	public Map<String, Object> getContextMap();
}
