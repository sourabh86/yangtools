/*
 * Copyright (c) 2019 PANTHEON.tech, s.r.o. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.rfc6241.parser;

import com.google.common.annotations.Beta;
import org.opendaylight.yangtools.rfc6241.model.api.GetFilterElementAttributesEffectiveStatement;
import org.opendaylight.yangtools.rfc6241.model.api.GetFilterElementAttributesSchemaNode;
import org.opendaylight.yangtools.rfc6241.model.api.GetFilterElementAttributesStatement;
import org.opendaylight.yangtools.rfc6241.model.api.NetconfStatements;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.model.api.SchemaPath;
import org.opendaylight.yangtools.yang.model.api.YangStmtMapping;
import org.opendaylight.yangtools.yang.model.api.meta.StatementDefinition;
import org.opendaylight.yangtools.yang.parser.rfc7950.stmt.UnknownEffectiveStatementBase;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractVoidStatementSupport;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext.Mutable;
import org.opendaylight.yangtools.yang.parser.spi.meta.SubstatementValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Beta
public final class GetFilterElementAttributesStatementSupport
    extends AbstractVoidStatementSupport<GetFilterElementAttributesStatement,
        GetFilterElementAttributesEffectiveStatement> {

    private static final class Declared extends AbstractDeclaredStatement<Void>
            implements GetFilterElementAttributesStatement {
        Declared(final StmtContext<Void, ?, ?> context) {
            super(context);
        }

        @Override
        public Void getArgument() {
            return null;
        }
    }

    private static final class Effective
            extends UnknownEffectiveStatementBase<Void, GetFilterElementAttributesStatement>
            implements GetFilterElementAttributesEffectiveStatement, GetFilterElementAttributesSchemaNode {

        private final SchemaPath path;

        Effective(final StmtContext<Void, GetFilterElementAttributesStatement, ?> ctx) {
            super(ctx);
            path = ctx.coerceParentContext().getSchemaPath().get().createChild(
                ctx.getPublicDefinition().getStatementName());
        }

        @Override
        public QName getQName() {
            return path.getLastComponent();
        }

        @Override
        public SchemaPath getPath() {
            return path;
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(GetFilterElementAttributesStatementSupport.class);
    private static final GetFilterElementAttributesStatementSupport INSTANCE =
            new GetFilterElementAttributesStatementSupport(NetconfStatements.GET_FILTER_ELEMENT_ATTRIBUTES);

    private final SubstatementValidator validator;

    GetFilterElementAttributesStatementSupport(final StatementDefinition definition) {
        super(definition);
        this.validator = SubstatementValidator.builder(definition).build();
    }

    public static GetFilterElementAttributesStatementSupport getInstance() {
        return INSTANCE;
    }

    @Override
    public GetFilterElementAttributesStatement createDeclared(
            final StmtContext<Void, GetFilterElementAttributesStatement, ?> ctx) {
        return new Declared(ctx);
    }

    @Override
    public GetFilterElementAttributesEffectiveStatement createEffective(final StmtContext<Void,
            GetFilterElementAttributesStatement, GetFilterElementAttributesEffectiveStatement> ctx) {
        return new Effective(ctx);
    }

    @Override
    protected SubstatementValidator getSubstatementValidator() {
        return validator;
    }

    @Override
    public void onFullDefinitionDeclared(final Mutable<Void, GetFilterElementAttributesStatement,
            GetFilterElementAttributesEffectiveStatement> stmt) {
        super.onFullDefinitionDeclared(stmt);
        stmt.setIsSupportedToBuildEffective(computeSupported(stmt));
    }

    private static boolean computeSupported(final StmtContext<?, ?, ?> stmt) {
        final StmtContext<?, ?, ?> parent = stmt.getParentContext();
        if (parent == null) {
            LOG.debug("No parent, ignoring get-filter-element-attributes statement");
            return false;
        }
        if (parent.getPublicDefinition() != YangStmtMapping.ANYXML) {
            LOG.debug("Parent is not an anyxml node, ignoring get-filter-element-attributes statement");
            return false;
        }
        if (!"filter".equals(parent.rawStatementArgument())) {
            LOG.debug("Parent is not named 'filter', ignoring get-filter-element-attributes statement");
            return false;
        }

        final StmtContext<?, ?, ?> grandParent = parent.getParentContext();
        if (grandParent == null) {
            LOG.debug("No grandparent, ignoring get-filter-element-attributes statement");
            return false;
        }
        if (grandParent.getPublicDefinition() != YangStmtMapping.INPUT) {
            LOG.debug("Grandparent is not an input node, ignoring get-filter-element-attributes statement");
            return false;
        }

        final StmtContext<?, ?, ?> greatGrandParent = grandParent.getParentContext();
        if (greatGrandParent == null) {
            LOG.debug("No grandparent, ignoring get-filter-element-attributes statement");
            return false;
        }
        if (greatGrandParent.getPublicDefinition() != YangStmtMapping.RPC) {
            LOG.debug("Grandparent is not an RPC node, ignoring get-filter-element-attributes statement");
            return false;
        }

        switch (greatGrandParent.rawStatementArgument()) {
            case "get":
            case "get-config":
                return true;
            default:
                LOG.debug("Great-grandparent is not named 'get' nor 'get-config, ignoring get-filter-element-attributes"
                    + " statement");
                return false;
        }
    }
}
