package org.joo.steak.example.benchmark;

import java.util.Date;
import java.util.Map;

import org.joo.steak.example.benchmark.state.BenchmarkState;
import org.joo.steak.framework.State;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.impl.DefaultStateContext;
import org.joo.steak.impl.DefaultStateManager;
import org.joo.steak.impl.config.DefaultStateEngineConfiguration;


public class Benchmark {

	public static void main(String[] args) {

		Benchmark example = new Benchmark();
		example.run();
	}

	public void run() {
		StateContext context = new DefaultStateContext("test1");
		
		StateEngineConfiguration configuration = createConfiguration();

		long start = new Date().getTime();
		
		for (int i=0; i<500000; i++) {
			DefaultStateManager stateManager = new DefaultStateManager();
			stateManager.initialize(context, configuration, null);
			stateManager.run();
			detachAllStateListeners(stateManager);
		}
		
		long now = new Date().getTime();
		System.out.println(now - start);
	}

	/**
	 * Detach the manager from all states' listeners
	 * @param stateManager
	 */
	private void detachAllStateListeners(DefaultStateManager stateManager) {
		Map<String, State> states = stateManager.getStates();
		for (String key : states.keySet()) {
			State state = states.get(key);
			state.removeStateChangedListener(stateManager);
		}
	}

	private StateEngineConfiguration createConfiguration() {
		DefaultStateEngineConfiguration configuration = new DefaultStateEngineConfiguration();

		configuration.addState("test1", new BenchmarkState());
		configuration.addState("test2", new BenchmarkState());

		configuration.addTransition("test1", "done", "test2");

		return configuration;
	}
}
