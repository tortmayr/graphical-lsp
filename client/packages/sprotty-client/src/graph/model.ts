/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
import { RectangularPort, SGraph } from "sprotty/lib";

import {
    BorderLayoutable,
    borderLayoutFeature,
    BorderPlacement,
    DEFAULT_BORDER_PLACEMENT
} from "../features/border-layout/model";
import { Saveable, saveFeature } from "../features/save/model";


export class GLSPGraph extends SGraph implements Saveable {
    dirty: boolean;

    hasFeature(feature: symbol) {
        return feature === saveFeature || super.hasFeature(feature);
    }
}

export class BorderPort extends RectangularPort implements BorderLayoutable {
    borderPlacement: BorderPlacement = DEFAULT_BORDER_PLACEMENT;

    hasFeature(feature: symbol) {
        return feature === borderLayoutFeature || super.hasFeature(feature);
    }

}
