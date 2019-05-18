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
import { BoundsAware, SModelElement, SModelExtension, SShapeElement } from "sprotty";

export const borderLayoutFeature = Symbol('borderLayout');


export interface BorderLayoutable extends SModelExtension {
    borderPlacement: BorderPlacement;
}

export function isBorderLayoutable<T extends SModelElement>(element: T): element is T & SShapeElement & BoundsAware & BorderLayoutable {
    return element instanceof SShapeElement
        && element.parent instanceof SShapeElement
        && 'borderPlacement' in element
        && element.hasFeature(borderLayoutFeature);
}
export type BorderSide = 'left' | 'right' | 'top' | 'bottom';

export class BorderPlacement extends Object {
    /**
    * where is the label relative to the node center
    */
    side: BorderSide;
    /**
     * between 0 (begin of the boder line) and 1 (end of the border line)
     */
    position: number;

    /**
     * position of the port relative to the border line.
     * between 0 (entire port shape is left/top of border line) and 1 (entire border shape is right/bottom of border line)
     */
    lineOffset: number;

}

export const DEFAULT_BORDER_PLACEMENT: BorderPlacement = {
    side: 'left',
    position: 0.5,
    lineOffset: 0.5
};


export function getBorderPlacement(element: SModelElement & BorderLayoutable): BorderPlacement {
    const placement = (element as any).borderPlacement;
    if (placement) {
        return placement;
    } else {
        return DEFAULT_BORDER_PLACEMENT;
    }
}
