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
