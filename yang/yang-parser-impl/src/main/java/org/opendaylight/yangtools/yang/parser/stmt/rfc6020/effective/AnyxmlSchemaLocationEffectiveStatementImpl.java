/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.yangtools.yang.parser.stmt.rfc6020.effective;

import com.google.common.annotations.Beta;
import java.util.Objects;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.model.api.SchemaPath;
import org.opendaylight.yangtools.yang.model.api.stmt.SchemaNodeIdentifier;
import org.opendaylight.yangtools.yang.model.api.stmt.UnknownStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext;

@Beta
public final class AnyxmlSchemaLocationEffectiveStatementImpl extends
        UnknownEffectiveStatementBase<SchemaNodeIdentifier> {

    private final SchemaPath path;

    public AnyxmlSchemaLocationEffectiveStatementImpl(
            final StmtContext<SchemaNodeIdentifier, UnknownStatement<SchemaNodeIdentifier>, ?> ctx) {
        super(ctx);

        path = ctx.getParentContext().getSchemaPath().get().createChild(getNodeType());
    }

    @Override
    public QName getQName() {
        return getNodeType();
    }

    @Override
    public SchemaPath getPath() {
        return path;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(path);
        result = prime * result + Objects.hashCode(getNodeType());
        result = prime * result + Objects.hashCode(getNodeParameter());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AnyxmlSchemaLocationEffectiveStatementImpl other = (AnyxmlSchemaLocationEffectiveStatementImpl) obj;
        if (!Objects.equals(path, other.path)) {
            return false;
        }
        if (!Objects.equals(getNodeType(), other.getNodeType())) {
            return false;
        }
        if (!Objects.equals(getNodeParameter(), other.getNodeParameter())) {
            return false;
        }
        return true;
    }
}
