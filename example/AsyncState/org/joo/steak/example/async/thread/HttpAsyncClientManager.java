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

import java.io.IOException;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

/**
 * A manager for HttpAsyncClient.
 * 
 * @author griever
 *
 */
public class HttpAsyncClientManager {

	private CloseableHttpAsyncClient httpClient;

	private static HttpAsyncClientManager instance;

	private static final Object lock = new Object();

	public static HttpAsyncClientManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new HttpAsyncClientManager();
				}
			}
		}
		return instance;
	}

	private HttpAsyncClientManager() {
		httpClient = HttpAsyncClients.createDefault();
		httpClient.start();
	}

	public Future<HttpResponse> execute(HttpUriRequest request, FutureCallback<HttpResponse> callback) {
		return httpClient.execute(request, callback);
	}

	public void shutdown() {
		ExecutorManager.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
