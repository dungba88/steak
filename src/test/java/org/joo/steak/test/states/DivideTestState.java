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
package org.joo.steak.test.states;

import org.joo.steak.framework.event.StateChangeEvent;
import org.joo.steak.impl.AbstractState;

public class DivideTestState extends AbstractState {

	public DivideTestState() {
		System.out.println("creating divide test state");
	}

	@Override
	public void onEntry(StateChangeEvent event) {
		System.out.println("entering divide test state");
		TestStateContext testStateContext = (TestStateContext) getStateContext();
		testStateContext.divideData(2);
		System.out.println("current data: " + testStateContext.getData());
		changeState("done", null);
	}

	@Override
	public void onExit(StateChangeEvent event) {
		System.out.println("exiting divide test state");
		System.out.println("");
	}
}
