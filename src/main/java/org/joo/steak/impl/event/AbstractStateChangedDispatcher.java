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
package org.joo.steak.impl.event;

import java.util.ArrayList;
import java.util.List;

import org.joo.steak.framework.event.StateChangedDispatcher;
import org.joo.steak.framework.event.StateChangeEvent;
import org.joo.steak.framework.event.StateChangedListener;

public abstract class AbstractStateChangedDispatcher implements StateChangedDispatcher {

	public List<StateChangedListener> listeners;

	public AbstractStateChangedDispatcher() {
		listeners = new ArrayList<>();
	}

	@Override
	public void addStateChangedListener(StateChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeStateChangedListener(StateChangedListener listener) {
		int idx = getIndex(listener);
		if (idx != -1)
			listeners.remove(idx);
	}

	@Override
	public void dispatchStateChangedEvent(StateChangeEvent event) {
		for (StateChangedListener listener : listeners) {
			if (listener != null) {
				listener.onStateChange(event);
			}
		}
	}

	protected final int getIndex(StateChangedListener theListener) {
		int idx = 0;
		for (StateChangedListener listener : listeners) {
			if (compare(listener, theListener)) {
				return idx;
			}
			idx++;
		}
		return -1;
	}

	private boolean compare(StateChangedListener listener,
			StateChangedListener theListener) {
		return listener != null && listener.equals(theListener);
	}
}
