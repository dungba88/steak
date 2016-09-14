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
import java.util.concurrent.ConcurrentHashMap;

import org.joo.steak.framework.StateContext;

public abstract class ConcurrentStateContext implements StateContext {
	
	private String initialState;
	
	private ConcurrentHashMap<String, Object> contextMap;
	
	public ConcurrentStateContext(String initialState) {
		this.initialState = initialState;
		this.contextMap = new ConcurrentHashMap<>();
	}
	
	@Override
	public String getInitialState() {
		return initialState;
	}

	@Override
	public Map<String, Object> getContextMap() {
		return contextMap;
	}
}
