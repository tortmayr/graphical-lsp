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
package com.eclipsesource.glsp.api.handler;

import java.util.Optional;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.graph.GModelRoot;

public interface OperationHandler extends Handler<AbstractOperationAction> {

	Optional<GModelRoot> execute(AbstractOperationAction action, GraphicalModelState modelState);

	@Override
	default boolean handles(AbstractOperationAction action) {
		return Optional.ofNullable(handlesActionType()) //
				.map(cl -> cl.isInstance(action)) //
				.orElse(false);
	}

	default Class<?> handlesActionType() {
		return null;
	}

}
