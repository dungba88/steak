package org.joo.state.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.joo.state.framework.StateManager;
import org.joo.state.framework.config.StateEngineConfiguration;
import org.joo.state.impl.DefaultStateManager;
import org.joo.state.impl.config.DefaultStateEngineConfiguration;
import org.joo.state.impl.config.JSONStateEngineConfigurator;
import org.joo.state.impl.loader.ImmediateStateEngineLoader;
import org.joo.state.impl.loader.PrototypeStateEngineLoader;
import org.joo.state.test.states.AddTestState;
import org.joo.state.test.states.DefaultTestState;
import org.joo.state.test.states.DivideTestState;
import org.joo.state.test.states.MultiplyTestState;
import org.joo.state.test.states.SubstractTestState;
import org.joo.state.test.states.TestStateContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit Test for standalone setups
 * 
 * @author griever
 *
 */
public class StandaloneStateTest {
	
	/**
	 * Test with JSON configuration
	 */
	@Test
	public void testJSON() {
		TestStateContext stateContext = new TestStateContext("default", 0);

		StateEngineConfiguration configuration = setupJSONConfiguration();

		System.out.println("testing with JSON configuration and immediate state loading");

		StateManager manager = new DefaultStateManager();
		manager.initialize(stateContext, configuration, new ImmediateStateEngineLoader());
		manager.run();

		Assert.assertEquals(1, stateContext.getData());
	}

	/**
	 * Test with immediate loading
	 */
	@Test
	public void testImmediate() {
		TestStateContext stateContext = new TestStateContext("default", 0);

		StateEngineConfiguration configuration = setupPrototypeConfiguration();

		System.out.println("testing with immediate state loading");

		StateManager manager = new DefaultStateManager();
		manager.initialize(stateContext, configuration, new ImmediateStateEngineLoader());
		manager.run();

		Assert.assertEquals(1, stateContext.getData());
	}

	/**
	 * Test with prototype loading
	 */
	@Test
	public void testPrototype() {
		TestStateContext stateContext = new TestStateContext("default", 0);

		StateEngineConfiguration configuration = setupPrototypeConfiguration();

		System.out.println("testing with prototype state loading");

		StateManager manager = new DefaultStateManager();
		manager.initialize(stateContext, configuration, new PrototypeStateEngineLoader());
		manager.run();

		Assert.assertEquals(1, stateContext.getData());
	}

	/**
	 * Test with simple state
	 */
	@Test
	public void testSimple() {
		TestStateContext stateContext = new TestStateContext("default", 0);

		System.out.println("testing with simple state");

		StateEngineConfiguration configuration = setupSimpleConfiguration();

		StateManager manager = new DefaultStateManager();
		manager.initialize(stateContext, configuration, null);
		manager.run();

		Assert.assertEquals(1, stateContext.getData());
	}

	private StateEngineConfiguration setupPrototypeConfiguration() {
		DefaultStateEngineConfiguration configuration = new DefaultStateEngineConfiguration();

		configuration.addState("default", "org.joo.state.test.states.DefaultTestState");
		configuration.addState("add", "org.joo.state.test.states.AddTestState");
		configuration.addState("multiply", "org.joo.state.test.states.MultiplyTestState");
		configuration.addState("substract", "org.joo.state.test.states.SubstractTestState");
		configuration.addState("divide", "org.joo.state.test.states.DivideTestState");

		configuration.addTransition("default", "*", "add");
		configuration.addTransition("add", "*", "multiply");
		configuration.addTransition("multiply", "*", "substract");
		configuration.addTransition("substract", "*", "divide");

		return configuration;
	}

	private StateEngineConfiguration setupSimpleConfiguration() {
		DefaultStateEngineConfiguration configuration = new DefaultStateEngineConfiguration();

		configuration.addState("default", new DefaultTestState());
		configuration.addState("add", new AddTestState());
		configuration.addState("multiply", new MultiplyTestState());
		configuration.addState("substract", new SubstractTestState());
		configuration.addState("divide", new DivideTestState());

		configuration.addTransition("default", "*", "add");
		configuration.addTransition("add", "*", "multiply");
		configuration.addTransition("multiply", "*", "substract");
		configuration.addTransition("substract", "*", "divide");

		return configuration;
	}
	
	private StateEngineConfiguration setupJSONConfiguration() {
		String config = readFile("src/test/resources/config.json");
		JSONStateEngineConfigurator configurator = new JSONStateEngineConfigurator(config);
		return configurator.getConfiguration();
	}
	
	private String readFile(String file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			String content = IOUtils.toString(fis);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
