package org.joo.steak.example.async.state;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.AbstractState;

/**
 * A state used when we successfully execute HTTP request
 * 
 * @author griever
 *
 */
public class SuccessState extends AbstractState {

	@Override
	public void onEntry(StateContext stateContext, StateChangedEvent event) {
		if (event.getArgs() instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) event.getArgs();
			try {
				InputStream is = response.getEntity().getContent();
				String content = IOUtils.toString(is);
				System.out.println(content);
				changeState("exit");
			} catch (UnsupportedOperationException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onExit(StateChangedEvent event) {

	}
}
