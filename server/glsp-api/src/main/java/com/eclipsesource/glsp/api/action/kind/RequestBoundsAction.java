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
package com.eclipsesource.glsp.api.action.kind;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.graph.GModelRoot;

public class RequestBoundsAction extends Action {
	
	private GModelRoot newRoot;

	public RequestBoundsAction() {
		super(Action.Kind.REQUEST_BOUNDS);
	}

	public RequestBoundsAction(GModelRoot newRoot) {
		this();
		this.newRoot = newRoot;
	}

	public GModelRoot getNewRoot() {
		return newRoot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((newRoot == null) ? 0 : newRoot.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestBoundsAction other = (RequestBoundsAction) obj;
		if (newRoot == null) {
			if (other.newRoot != null)
				return false;
		} else if (!newRoot.equals(other.newRoot))
			return false;
		return true;
	}

}
