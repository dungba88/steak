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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

import org.joo.steak.framework.event.StateChangeEvent;
import org.joo.steak.framework.exception.StateExecutionException;

/**
 * Asynchronous implementation of <code>StateManager</code>. It relies on
 * <code>ExecutorService</code> to do the job.
 * 
 * @author griever
 *
 */
public class AsyncStateManager extends DefaultStateManager {

	private ExecutorService service;

	public AsyncStateManager(ExecutorService service) {
		this.service = service;
	}

	@Override
	public void doRun() {
		service.execute(new Runnable() {

			@Override
			public void run() {
				enterState(getInitialState(), null);
			}
		});
	}

	@Override
	public void onStateChange(StateChangeEvent event) {
		if (event == null)
			return;

		checkStateIntegrity(event);

		try {
			service.execute(new Runnable() {
	
				@Override
				public void run() {
					moveNextState(event);
				}
			});
		} catch (RejectedExecutionException ex) {
			if (service.isShutdown()) {
				delegateException(new StateExecutionException("Thread pool has been shut down.", ex));
			} else {
				delegateException(new StateExecutionException("Exception occurred in onStateChange", ex));
			}
		}
	}

	public ExecutorService getExecutorService() {
		return service;
	}
}