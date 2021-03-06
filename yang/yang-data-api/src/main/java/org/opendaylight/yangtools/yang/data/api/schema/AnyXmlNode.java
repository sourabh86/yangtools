/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.data.api.schema;

import javax.xml.transform.dom.DOMSource;
import org.opendaylight.yangtools.yang.data.api.AttributesContainer;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier.NodeIdentifier;

/**
 * AN normalizedNode.
 */
public interface AnyXmlNode extends AttributesContainer, DataContainerChild<NodeIdentifier, DOMSource> {
    /**
     * Return value represented as a DOMSource. Returned source contains top level element
     * that duplicates the anyxml node.
     *
     * @return anyxml node value represented as DOMSource.
     */
    @Override
    DOMSource getValue();
}
