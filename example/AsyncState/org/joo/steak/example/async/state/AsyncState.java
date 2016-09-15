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
package org.joo.steak.example.async.state;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.joo.steak.example.async.thread.ExecutorManager;
import org.joo.steak.example.async.thread.HttpAsyncClientManager;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.AbstractState;

/**
 * A simple example of a asynchronous state
 * 
 * @author griever
 *
 */
public class AsyncState extends AbstractState {

	private static final String URL = "https://randomuser.me/api/";

	@Override
	public void onEntry(StateContext stateContext, StateChangedEvent event) {
		// create and execute a request
		System.out.println("starting HTTP request");
		HttpGet request = new HttpGet(URL);

		HttpAsyncClientManager.getInstance().execute(request, new FutureCallback<HttpResponse>() {

			@Override
			public void cancelled() {
				System.out.println("request cancelled");
				changeStateInNewThread("fail", null);
			}

			@Override
			public void completed(HttpResponse response) {
				System.out.println("request success");
				changeStateInNewThread("success", response);
			}

			@Override
			public void failed(Exception e) {
				System.out.println("request failed");
				changeStateInNewThread("fail", e);
			}

			private void changeStateInNewThread(final String action, final Object args) {
				// change the state in new thread, since we don't want to use
				// HttpAsynceClient I/O dispatcher thread for too long
				ExecutorManager.getInstance().execute(new Runnable() {
					public void run() {
						changeState(action, args);
					}
				});
			}
		});
	}

	@Override
	public void onExit(StateChangedEvent event) {

	}
}
