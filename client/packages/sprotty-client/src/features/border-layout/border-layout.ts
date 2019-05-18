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
import { injectable } from "inversify";
import { VNode } from "snabbdom/vnode";
import {
    add,
    center,
    EMPTY_BOUNDS,
    findParent,
    IVNodeDecorator,
    Point,
    setAttr,
    SModelElement,
    SNode,
    SShapeElement,
    subtract,
    translatePoint
} from "sprotty";

import { bottomLeftPoint, topLeftPoint, topRightPoint } from "../../utils/geometry";
import { BorderPlacement, getBorderPlacement, isBorderLayoutable } from "./model";

@injectable()
export class BorderLayoutDecorator implements IVNodeDecorator {

    decorate(vnode: VNode, element: SModelElement): VNode {
        if (isBorderLayoutable(element)) {
            if (element.bounds !== EMPTY_BOUNDS) {
                const placement = getBorderPlacement(element);
                let newCenterPoint = this.calculateLineStartPoint(element, placement);
                newCenterPoint = add(newCenterPoint, this.calculateLinePosition(element, placement));
                newCenterPoint = add(newCenterPoint, this.getLineOffset(element, placement));
                const transform = `translate(${newCenterPoint.x}, ${newCenterPoint.y})`;

                setAttr(vnode, 'transform', transform);
            }
        }
        return vnode;
    }
    postUpdate(): void {


    }

    /**
     * Calculate the distance to the begin of the borderside of the parent node;
     */
    protected calculateLineStartPoint(element: SShapeElement, placement: BorderPlacement): Point {
        const parentNode = findParent(element, p => p instanceof SNode)! as SNode;
        const centerPoint = translatePoint(center(element.bounds), element, parentNode.parent);
        let startPoint = { x: 0, y: 0 };
        switch (placement.side) {
            case "left":
                startPoint = {
                    x: topLeftPoint(parentNode.bounds).x,
                    y: centerPoint.y
                };
                break;
            case "right":
                startPoint = {
                    x: topRightPoint(parentNode.bounds).x,
                    y: centerPoint.y
                };
                break;
            case "top":
                startPoint = {
                    x: centerPoint.x,
                    y: topLeftPoint(parentNode.bounds).y
                };
                break;
            case "bottom":
                startPoint = {
                    x: centerPoint.x,
                    y: bottomLeftPoint(parentNode.bounds).y
                };
                break;
        }

        return subtract(startPoint, centerPoint);
    }

    /**
     * Calculate the position of the port on the boder line.
     */
    protected calculateLinePosition(element: SShapeElement, placement: BorderPlacement): Point {
        const position = Math.min(1, Math.max(0, placement.position));
        const parent = element.parent as SShapeElement;
        switch (placement.side) {
            case "left":
            case "right":
                return { x: 0, y: parent.bounds.height >= 0 ? position * parent.bounds.height - position * element.bounds.height : 0 };
            case "top":
            case "bottom":
                return { x: parent.bounds.width >= 0 ? position * parent.bounds.width - position * element.bounds.width : 0, y: 0 };
        }
    }

    /**
     *  Calculate the offest of the port from the line.
     */
    protected getLineOffset(element: SShapeElement, placement: BorderPlacement): Point {
        const offset = Math.min(1, Math.max(0, placement.lineOffset));
        switch (placement.side) {
            case "left":
            case "right":
                return { x: element.bounds.width >= 0 ? (offset - 0.5) * element.bounds.width : 0, y: 0 };
            case "top":
            case "bottom":
                return { x: 0, y: element.bounds.height >= 0 ? (offset - 0.5) * element.bounds.height : 0 };
        }
    }
}


