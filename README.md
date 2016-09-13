# steak
Simple state engine. Support standalone and Spring-based applications.

## What is steak?
Steak is a simple State Pattern implementation in Java which let you easily code independent, decoupled states and use configuration to specify state transition flows.
It allows you to use JSON, XML or an explicit configuration.

## Requirements
It requires Java 7 to run. If you want to use JSON configuration then you also need to import [org.json](http://json.org/)

## How to use
At the simplest setup, it allows require a few line of codes to run

    // create new StateContext with initial state "default"
    TestStateContext stateContext = new TestStateContext("default", 0);
    
    // create instance of StateManager and run
    StateManager manager = new DefaultStateManager();
    manager.initialize(stateContext, configuration, null);
    manager.run();
    
To create the configuration, you can use either JSON, XML or explicit one. Let say you want to create an explicit configuration:

    DefaultStateEngineConfiguration configuration = new DefaultStateEngineConfiguration();

    // add some states
    configuration.addState("default", new DefaultTestState());
    configuration.addState("add", new AddTestState());
    configuration.addState("multiply", new MultiplyTestState());
    configuration.addState("substract", new SubstractTestState());
    configuration.addState("divide", new DivideTestState());
    
    // add some transitions. asterisk means any action will match
    configuration.addTransition("default", "*", "add");
    configuration.addTransition("add", "*", "multiply");
    configuration.addTransition("multiply", "*", "substract");
    configuration.addTransition("substract", "*", "divide");
    
And for the state itself, here is a simple implementation of a state

    public class DefaultTestState extends AbstractState {
	
		@Override
		public void handle(StateContext stateContext) {
			TestStateContext testStateContext = (TestStateContext) stateContext;
			System.out.println("current data: " + testStateContext.getData());
			// move to next state
			changeState("done", null);
		}
	}


## How does it work

Steak use a loosely coupled implementation of State Pattern. That means each state will be independent from each other. Each
state handles its own logic, then dispatch an event to notify the StateManager to move to next state. The transition flow
can be defined via the configuration.

For example in the configuration above, we defined 4 states: `default`, `add`, `multiply`, `substract` and `divide`.
Then we defined the transition flows between those states. This line of code:

    configuration.addTransition("default", "*", "add");
    
means when we are in `default` state, if there any event dispatched, then we'll move to `add` state.
