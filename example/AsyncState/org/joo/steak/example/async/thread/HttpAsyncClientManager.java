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

	public Future<HttpResponse> execute(HttpUriRequest request,
			FutureCallback<HttpResponse> callback) {
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
