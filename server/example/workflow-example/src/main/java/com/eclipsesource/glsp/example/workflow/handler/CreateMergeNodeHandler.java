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
package com.eclipsesource.glsp.example.workflow.handler;

import java.util.Optional;

import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.workflow.utils.ModelTypes;
import com.eclipsesource.glsp.example.workflow.wfgraph.ActivityNode;
import com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphFactory;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.server.operationhandler.CreateNodeOperationHandler;
import com.eclipsesource.glsp.server.util.GModelUtil;

public class CreateMergeNodeHandler extends CreateNodeOperationHandler {

	public CreateMergeNodeHandler() {
		super(ModelTypes.MERGE_NODE);
	}

	@Override
	protected GNode createNode(Optional<GPoint> point, GraphicalModelState modelState) {
		ActivityNode result = WfgraphFactory.eINSTANCE.createActivityNode();
		result.setType(elementTypeId);
		result.setNodeType("mergeNode");
		point.ifPresent(result::setPosition);

		GModelUtil.generateId(result, "activityNode", modelState);

		return result;
	}

}
