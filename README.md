# steak
Simple state engine. Support standalone and Spring-based applications.

## What is steak?
Steak is a simple State Pattern implementation in Java which let you easily code independent, decoupled states and use configuration to specify state transition flows.
It allows you to use JSON, XML or an explicit configuration.

## Requirements
It requires Java 7 to run. If you want to use JSON configuration then you also need to import [org.json](http://json.org/). Also if you want to run the test, then also to add JUnit and Apache Commons IO to your classpath. Or you can simply configure your Eclipse project as a maven project and let it update the dependencies.

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
        public void onEntry(StateChangedEvent event) {
            System.out.println("entering default test state");
            changeState("done", null);
        }

        @Override
        public void onExit(StateChangedEvent event) {
            System.out.println("exiting default test state");
        }
    }


## How does it work

Steak use a loosely coupled implementation of State Pattern. That means each state will be independent from others. Each
state handles its own logic, then dispatch an event to notify the StateManager to move to next state. The transition flow
can be defined via the configuration.

For example in the configuration above, we defined 4 states: `default`, `add`, `multiply`, `substract` and `divide`.
Then we defined the transition flows between those states. This line of code:

    configuration.addTransition("default", "*", "add");
    
means when we are in `default` state, if there any event dispatched, then we'll move to `add` state. You can also specify a `StateTransition` object, so the following code will be equivalent to the above:

    configuration.addTransition("default", "*", new SimpleStateTransition("add"));

Of course you can use your own `StateTransition`!

## JSON and XML configuration

JSON and XML configuration are supported. Please check [src/test/resources/config.json](https://github.com/dungba88/steak/blob/master/src/test/resources/config.json) and [src/test/resources/config.xml](https://github.com/dungba88/steak/blob/master/src/test/resources/config.xml) for more details.
