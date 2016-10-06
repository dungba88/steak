package org.joo.steak.example.benchmark.state;

import org.joo.steak.framework.event.StateChangeEvent;
import org.joo.steak.framework.exception.StateExecutionException;
import org.joo.steak.impl.DefaultState;

public class BenchmarkState extends DefaultState {

	@Override
	public void onEntry(StateChangeEvent event) throws StateExecutionException {
		changeState("done");
	}
}
