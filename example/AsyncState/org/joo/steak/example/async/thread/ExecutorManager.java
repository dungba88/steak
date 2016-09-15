package org.joo.steak.example.async.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A manager for executor service. Can be used to run some tasks asynchronously
 * 
 * @author griever
 *
 */
public class ExecutorManager {

	private ExecutorService executor;

	private static ExecutorManager instance;
	
	private static final Object lock = new Object();

	public static ExecutorManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new ExecutorManager();
				}
			}
		}
		return instance;
	}
	
	private ExecutorManager() {
		executor = Executors.newFixedThreadPool(8);
	}

	public void execute(Runnable runnable) {
		executor.execute(runnable);
	}

	public void shutdown() {
		executor.shutdown();
	}
}
