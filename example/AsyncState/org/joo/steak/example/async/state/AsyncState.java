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

			private void changeStateInNewThread(String action, Object args) {
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
