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
package com.eclipsesource.glsp.api.jsonrpc;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.sprotty.ServerStatus;

import com.eclipsesource.glsp.api.action.ActionMessage;

public interface GLSPServer extends GLSPClientAware {

	public interface Provider {
		GLSPServer getGraphicalLanguageServer(String clientId);
	}

	void initialize();

	@JsonNotification("process")
	void process(ActionMessage message);

	@JsonRequest("shutdown")
	CompletableFuture<Object> shutdown();

	@JsonNotification("exit")
	void exit(String clientId);

	ServerStatus getStatus();
}
