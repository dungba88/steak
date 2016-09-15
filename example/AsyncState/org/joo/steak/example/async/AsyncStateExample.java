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
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.framework.event.StateEngineListener;
import org.joo.steak.impl.DefaultStateContext;
import org.joo.steak.impl.DefaultStateManager;
import org.joo.steak.impl.config.DefaultStateEngineConfiguration;

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
		stateManager.addStateEngineListener(new StateEngineListener() {

			@Override
			public void onStateChanged(StateChangedEvent event) {

			}

			@Override
			public void onStart(StateContext stateContext) {
				System.out.println("State engine starts");
			}

			@Override
			public void onFinish(StateChangedEvent event) {
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
