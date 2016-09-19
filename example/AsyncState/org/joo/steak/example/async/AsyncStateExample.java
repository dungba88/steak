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
package org.joo.steak.example.async;

import org.joo.steak.example.async.state.AsyncState;
import org.joo.steak.example.async.state.FailState;
import org.joo.steak.example.async.state.RetryState;
import org.joo.steak.example.async.state.SuccessState;
import org.joo.steak.example.async.thread.ExecutorManager;
import org.joo.steak.example.async.thread.HttpAsyncClientManager;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.StateManager;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.event.StateChangeEvent;
import org.joo.steak.impl.DefaultStateContext;
import org.joo.steak.impl.DefaultStateManager;
import org.joo.steak.impl.config.DefaultStateEngineConfiguration;
import org.joo.steak.impl.event.DefaultStateEngineListener;

/**
 * This an example of asynchronous state. It will call a request to
 * https://randomuser.me/api/ to retrieve a random user name, and notify the
 * <code>StateManager</code> to change state when it finishes.
 * 
 * If the request is executed successfully, it will move to SuccessState and
 * exit, otherwise it will move to RetryState. The RetryState will again move to
 * AsyncState to retry the request. It maintains a retry counter which is
 * initially 3. After 3 unsuccessful retries, it will move to FailState.
 * 
 * Many thanks to @bachden for guiding me when coding this example
 * 
 * @author griever
 *
 */
public class AsyncStateExample {

	public static void main(String[] args) {

		AsyncStateExample example = new AsyncStateExample();
		example.run();
	}

	public void run() {
		StateContext context = new DefaultStateContext("async");
		StateEngineConfiguration configuration = createConfiguration();

		StateManager stateManager = new DefaultStateManager();
		stateManager.initialize(context, configuration, null);

		// hook some state engine events
		stateManager.addStateEngineListener(new DefaultStateEngineListener() {

			@Override
			public void onStart(StateContext stateContext) {
				System.out.println("State engine starts");
			}

			@Override
			public void onFinish(StateChangeEvent event) {
				System.out.println("State engine finishes");

				// cleaning up resources
				HttpAsyncClientManager.getInstance().shutdown();
				ExecutorManager.getInstance().shutdown();
			}
		});

		stateManager.run();

		System.out.println("After running");
	}

	private StateEngineConfiguration createConfiguration() {
		DefaultStateEngineConfiguration configuration = new DefaultStateEngineConfiguration();

		configuration.addState("async", new AsyncState());
		configuration.addState("success", new SuccessState());
		configuration.addState("retry", new RetryState(3));
		configuration.addState("fail", new FailState());

		configuration.addTransition("async", "success", "success");
		configuration.addTransition("async", "fail", "retry");
		configuration.addTransition("retry", "ok", "async");
		configuration.addTransition("retry", "expired", "fail");

		return configuration;
	}
}
