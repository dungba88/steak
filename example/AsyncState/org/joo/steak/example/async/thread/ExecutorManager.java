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
package org.joo.steak.example.async.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A manager for executor service. Can be used to run some tasks asynchronously
 * 
 * @author griever
 *
 */
public class ExecutorManager {

	private ExecutorService executor;

	private static ExecutorManager instance;
	
	private static final Object lock = new Object();

	public static ExecutorManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new ExecutorManager();
				}
			}
		}
		return instance;
	}
	
	private ExecutorManager() {
		executor = Executors.newFixedThreadPool(8);
	}

	public void execute(Runnable runnable) {
		executor.execute(runnable);
	}

	public void shutdown() {
		executor.shutdown();
	}
}
