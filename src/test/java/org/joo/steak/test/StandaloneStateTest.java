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
package org.joo.steak.test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.joo.steak.framework.StateManager;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.event.StateChangeEvent;
import org.joo.steak.impl.AsyncStateManager;
import org.joo.steak.impl.DefaultStateManager;
import org.joo.steak.impl.config.DefaultStateEngineConfiguration;
import org.joo.steak.impl.config.JSONStateEngineConfigurator;
import org.joo.steak.impl.config.XMLStateEngineConfigurator;
import org.joo.steak.impl.event.DefaultStateEngineListener;
import org.joo.steak.impl.loader.PrototypeStateEngineLoader;
import org.joo.steak.test.states.AddTestState;
import org.joo.steak.test.states.DefaultTestState;
import org.joo.steak.test.states.DivideTestState;
import org.joo.steak.test.states.MultiplyTestState;
import org.joo.steak.test.states.SubtractTestState;
import org.joo.steak.test.states.TestStateContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit Test for standalone setups
 * 
 * @author griever
 *
 */
public class StandaloneStateTest {

	@Test
	public void testAsync() {
		System.out.println("testing with asynchronous state manager");

		TestStateContext stateContext = new TestStateContext("default", 0);

		StateEngineConfiguration configuration = setupSimpleConfiguration();

		// create new fixed thread pool with 8 threads
		final ExecutorService service = Executors.newFixedThreadPool(8);

		StateManager manager = new AsyncStateManager(service);
		manager.initialize(stateContext, configuration, null);

		// listen for finish event
		manager.addStateEngineListener(new DefaultStateEngineListener() {

			@Override
			public void onFinish(StateChangeEvent event) {
				System.out.println("state engine finished. shutting down executor service");
				service.shutdown();
			}
		});

		manager.run();

		System.out.println("after running. awaiting ExecutorService shutdown");

		try {
			// we don't use CountDownLatch here for sake of simplicity, just 
			// wait for 2 seconds or until executor service is shut down
			service.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Assert.assertEquals(1, stateContext.getData());

		System.out.println("\n----------\n");
	}

	/**
	 * Test with XML configuration
	 */
	@Test
	public void testXML() {
		System.out.println("testing with XML configuration and immediate state loading");

		TestStateContext stateContext = new TestStateContext("default", 0);

		StateEngineConfiguration configuration = setupXMLConfiguration();

		StateManager manager = new DefaultStateManager();
		manager.initialize(stateContext, configuration, null);
		manager.run();

		Assert.assertEquals(1, stateContext.getData());

		System.out.println("\n----------\n");
	}

	/**
	 * Test with JSON configuration
	 */
	@Test
	public void testJSON() {
		System.out.println("testing with JSON configuration and immediate state loading");

		TestStateContext stateContext = new TestStateContext("default", 0);

		StateEngineConfiguration configuration = setupJSONConfiguration();

		StateManager manager = new DefaultStateManager();
		manager.initialize(stateContext, configuration, null);
		manager.run();

		Assert.assertEquals(1, stateContext.getData());

		System.out.println("\n----------\n");
	}

	/**
	 * Test with immediate loading
	 */
	@Test
	public void testImmediate() {
		System.out.println("testing with immediate state loading");

		TestStateContext stateContext = new TestStateContext("default", 0);

		StateEngineConfiguration configuration = setupPrototypeConfiguration();

		StateManager manager = new DefaultStateManager();
		manager.initialize(stateContext, configuration, null);
		manager.run();

		Assert.assertEquals(1, stateContext.getData());

		System.out.println("\n----------\n");
	}

	/**
	 * Test with prototype loading
	 */
	@Test
	public void testPrototype() {
		System.out.println("testing with prototype state loading");

		TestStateContext stateContext = new TestStateContext("default", 0);

		StateEngineConfiguration configuration = setupPrototypeConfiguration();

		StateManager manager = new DefaultStateManager();
		manager.initialize(stateContext, configuration, new PrototypeStateEngineLoader());
		manager.run();

		Assert.assertEquals(1, stateContext.getData());

		System.out.println("\n----------\n");
	}

	/**
	 * Test with simple state
	 */
	@Test
	public void testSimple() {
		System.out.println("testing with simple state");

		TestStateContext stateContext = new TestStateContext("default", 0);

		StateEngineConfiguration configuration = setupSimpleConfiguration();

		StateManager manager = new DefaultStateManager();
		manager.initialize(stateContext, configuration, null);
		manager.run();

		Assert.assertEquals(1, stateContext.getData());

		System.out.println("\n----------\n");
	}

	private StateEngineConfiguration setupPrototypeConfiguration() {
		DefaultStateEngineConfiguration configuration = new DefaultStateEngineConfiguration();

		configuration.addState("default", "org.joo.steak.test.states.DefaultTestState");
		configuration.addState("add", "org.joo.steak.test.states.AddTestState");
		configuration.addState("multiply", "org.joo.steak.test.states.MultiplyTestState");
		configuration.addState("subtract", "org.joo.steak.test.states.SubtractTestState");
		configuration.addState("divide", "org.joo.steak.test.states.DivideTestState");

		configuration.addTransition("default", "*", "add");
		configuration.addTransition("add", "*", "multiply");
		configuration.addTransition("multiply", "*", "subtract");
		configuration.addTransition("subtract", "*", "divide");

		return configuration;
	}

	private StateEngineConfiguration setupSimpleConfiguration() {
		DefaultStateEngineConfiguration configuration = new DefaultStateEngineConfiguration();

		configuration.addState("default", new DefaultTestState());
		configuration.addState("add", new AddTestState());
		configuration.addState("multiply", new MultiplyTestState());
		configuration.addState("subtract", new SubtractTestState());
		configuration.addState("divide", new DivideTestState());

		configuration.addTransition("default", "*", "add");
		configuration.addTransition("add", "*", "multiply");
		configuration.addTransition("multiply", "*", "subtract");
		configuration.addTransition("subtract", "*", "divide");

		return configuration;
	}

	private StateEngineConfiguration setupJSONConfiguration() {
		String config = readFile("src/test/resources/config.json");
		JSONStateEngineConfigurator configurator = new JSONStateEngineConfigurator(config);
		return configurator.getConfiguration();
	}

	private StateEngineConfiguration setupXMLConfiguration() {
		String config = readFile("src/test/resources/config.xml");
		XMLStateEngineConfigurator configurator = new XMLStateEngineConfigurator(config);
		return configurator.getConfiguration();
	}

	private String readFile(String file) {
		try {
			return FileUtils.readFileToString(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
