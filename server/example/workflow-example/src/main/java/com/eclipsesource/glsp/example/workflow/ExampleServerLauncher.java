/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.example.workflow;

import org.apache.log4j.BasicConfigurator;

import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.server.launch.DefaultGLSPServerLauncher;
import com.eclipsesource.glsp.server.launch.GLSPServerLauncher;
import com.eclipsesource.glsp.server.websocket.DefaultWebsocketGLSPServer;
import com.eclipsesource.glsp.server.websocket.WebsocketServerLauncher;

public class ExampleServerLauncher {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		GLSPServerLauncher launcher;

		if (args.length == 1 && args[0].equals("websocket")) {
			launcher = new WebsocketServerLauncher(new WorkflowGLSPModule() {

				@Override
				protected Class<? extends GLSPServer> bindGLSPServer() {
					return DefaultWebsocketGLSPServer.class;
				}

			});
			launcher.start("localhost", 8081);

		} else {
			launcher = new DefaultGLSPServerLauncher(new WorkflowGLSPModule());
			launcher.start("localhost", 5007);
		}

	}
}
