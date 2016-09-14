package org.joo.steak.test.states;

import org.joo.steak.impl.AbstractStateContext;

public class TestStateContext extends AbstractStateContext {
	
	private int data;

	public TestStateContext(String initialState, int data) {
		super(initialState);
	}

	public int getData() {
		return data;
	}

	public void multiplyData(int multiplier) {
		data *= multiplier;
	}

	public void divideData(int divider) {
		data /= divider;
	}

	public void addData(int adder) {
		data += adder;
	}

	public void substractData(int substractor) {
		data -= substractor;
	}
}
