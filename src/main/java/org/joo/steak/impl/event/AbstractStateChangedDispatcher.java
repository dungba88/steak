package org.joo.steak.impl.event;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.joo.steak.framework.event.StateChangedDispatcher;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.framework.event.StateChangedListener;

public abstract class AbstractStateChangedDispatcher implements StateChangedDispatcher {

	public List<WeakReference<StateChangedListener>> listeners;
	
	public AbstractStateChangedDispatcher() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addStateChangedListener(StateChangedListener listener) {
		listeners.add(new WeakReference<StateChangedListener>(listener));
	}
	
	@Override
	public void removeStateChangedListener(StateChangedListener listener) {
		int idx = getIndex(listener);
		if (idx != -1)
			listeners.remove(idx);
	}

	@Override
	public void dispatchStateChangedEvent(StateChangedEvent event) {
		for (WeakReference<StateChangedListener> listenerRef : listeners) {
			StateChangedListener listener = listenerRef.get();
			if (listener != null) {
				listener.onStateChanged(event);
			}
		}
	}
	
	protected final int getIndex(StateChangedListener theListener) {
		int idx = 0;
		for (WeakReference<StateChangedListener> listenerRef : listeners) {
			StateChangedListener listener = listenerRef.get();
			if (listener != null && listener.equals(theListener)) {
				return idx;
			}
			idx++;
		}
		return -1;
	}
}
