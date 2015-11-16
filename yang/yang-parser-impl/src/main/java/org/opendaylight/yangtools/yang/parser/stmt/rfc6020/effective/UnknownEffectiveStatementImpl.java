/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.yangtools.yang.parser.stmt.rfc6020.effective;

import java.util.Objects;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.model.api.SchemaPath;
import org.opendaylight.yangtools.yang.model.api.UnknownSchemaNode;
import org.opendaylight.yangtools.yang.model.api.stmt.UnknownStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext;
import org.opendaylight.yangtools.yang.parser.stmt.rfc6020.Utils;

public final class UnknownEffectiveStatementImpl extends UnknownEffectiveStatementBase<String> {

    private final QName maybeQNameArgument;
    private final SchemaPath path;

    public UnknownEffectiveStatementImpl(final StmtContext<String, UnknownStatement<String>, ?> ctx) {
        super(ctx);

        // FIXME: Remove following section after fixing 4380
        final UnknownSchemaNode original = ctx.getOriginalCtx() == null ? null : (UnknownSchemaNode) ctx
                .getOriginalCtx().buildEffective();
        if (original != null) {
            this.maybeQNameArgument = original.getQName();
        } else {
            QName maybeQNameArgumentInit = null;
            try {
                maybeQNameArgumentInit = Utils.qNameFromArgument(ctx, argument());
            } catch (IllegalArgumentException e) {
                maybeQNameArgumentInit = getNodeType();
            }
            this.maybeQNameArgument = maybeQNameArgumentInit;
        }
        path = ctx.getParentContext().getSchemaPath().get().createChild(maybeQNameArgument);
    }

    @Override
    public QName getQName() {
        return maybeQNameArgument;
    }

    @Override
    public SchemaPath getPath() {
        return path;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(maybeQNameArgument);
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
        UnknownEffectiveStatementImpl other = (UnknownEffectiveStatementImpl) obj;
        if (!Objects.equals(maybeQNameArgument, other.maybeQNameArgument)) {
            return false;
        }
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
