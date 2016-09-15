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
package org.joo.steak.impl.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.config.StateEngineConfigurator;
import org.joo.steak.framework.exception.StateInitializationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLStateEngineConfigurator implements StateEngineConfigurator {

	private static final String DEFAULT_CHARSET = "UTF-8";

	private String config;

	private String charset;

	XPath xPath;

	public XMLStateEngineConfigurator(String config) {
		this(config, null);
	}

	public XMLStateEngineConfigurator(String config, String charset) {
		this.config = config;
		this.charset = charset != null ? charset : DEFAULT_CHARSET;
	}

	@Override
	public StateEngineConfiguration getConfiguration() {
		try {
			Document doc = parse();

			xPath = XPathFactory.newInstance().newXPath();

			NodeList stateNodeList = fetchNodeList(doc, "/config/states/state");
			Map<String, Object> states = parseStatesXML(stateNodeList);

			NodeList transitionsNodeList = fetchNodeList(doc,
					"/config/flows/flow");

			Map<String, Map<String, Object[]>> transitions = parseTransitionsXML(transitionsNodeList);

			return new DefaultStateEngineConfiguration(states, transitions);
		} catch (Exception ex) {
			throw new StateInitializationException("Invalid XML configuration",
					ex);
		}
	}

	private Map<String, Map<String, Object[]>> parseTransitionsXML(
			NodeList transitionsNodeList) throws XPathExpressionException {
		Map<String, Map<String, Object[]>> map = new HashMap<>();
		if (transitionsNodeList != null) {
			for (int i = 0; i < transitionsNodeList.getLength(); i++) {
				Node node = transitionsNodeList.item(i);
				String from = fetchNode(node, "from").getTextContent();
				String action = fetchNode(node, "action").getTextContent();
				NodeList toList = fetchNodeList(node, "transitions/transition");
				Object[] transitions = parseTransitionsArr(toList);

				if (!map.containsKey(from)) {
					map.put(from, new HashMap<String, Object[]>());
				}
				map.get(from).put(action, transitions);
			}
		}
		return map;
	}

	private Object[] parseTransitionsArr(NodeList toList) {
		if (toList == null)
			return new Object[0];

		Object[] objects = new Object[toList.getLength()];
		for (int i = 0; i < toList.getLength(); i++) {
			Node node = toList.item(i);
			objects[i] = node.getTextContent();
		}
		return objects;
	}

	private Map<String, Object> parseStatesXML(NodeList stateNodeList)
			throws XPathExpressionException {
		Map<String, Object> map = new HashMap<>();
		if (stateNodeList != null) {
			for (int i = 0; i < stateNodeList.getLength(); i++) {
				Node node = stateNodeList.item(i);
				Node keyNode = fetchNode(node, "name");
				Node valueNode = fetchNode(node, "value");
				map.put(keyNode.getTextContent(), valueNode.getTextContent());
			}
		}
		return map;
	}

	private NodeList fetchNodeList(Node doc, String expression)
			throws XPathExpressionException {
		return (NodeList) xPath.compile(expression).evaluate(doc,
				XPathConstants.NODESET);
	}

	private Node fetchNode(Node doc, String expression)
			throws XPathExpressionException {
		return (Node) xPath.compile(expression).evaluate(doc,
				XPathConstants.NODE);
	}

	private Document parse() throws UnsupportedEncodingException, SAXException,
			IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder
				.parse(new ByteArrayInputStream(config.getBytes(charset)));
	}
}
