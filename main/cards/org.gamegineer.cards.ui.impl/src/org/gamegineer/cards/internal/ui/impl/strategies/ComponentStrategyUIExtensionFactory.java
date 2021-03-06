/*
 * ComponentStrategyUIExtensionFactory.java
 * Copyright 2008-2015 Gamegineer contributors and others.
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on Mar 29, 2013 at 10:11:42 PM.
 */

package org.gamegineer.cards.internal.ui.impl.strategies;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.cards.internal.ui.impl.BundleConstants;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.ui.IComponentStrategyUI;

/**
 * A factory for creating component strategy user interfaces located in this
 * bundle from the extension registry.
 */
@NotThreadSafe
public final class ComponentStrategyUIExtensionFactory
    implements IExecutableExtension, IExecutableExtensionFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the component strategy
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The collection of component strategy user interfaces supported by this
     * factory.
     */
    private static final Map<ComponentStrategyId, IComponentStrategyUI> COMPONENT_STRATEGY_UIS;

    /** The identifier of the component strategy user interface to create. */
    private @Nullable ComponentStrategyId componentStrategyId_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code ComponentStrategyExtensionFactory} class.
     */
    static
    {
        final Map<ComponentStrategyId, IComponentStrategyUI> componentStrategyUIs = new HashMap<>();
        componentStrategyUIs.put( InternalComponentStrategyUIs.CARD.getId(), InternalComponentStrategyUIs.CARD );
        componentStrategyUIs.put( InternalComponentStrategyUIs.CARD_PILE.getId(), InternalComponentStrategyUIs.CARD_PILE );
        COMPONENT_STRATEGY_UIS = Collections.unmodifiableMap( componentStrategyUIs );
    }

    /**
     * Initializes a new instance of the
     * {@code ComponentStrategyUIExtensionFactory} class.
     */
    public ComponentStrategyUIExtensionFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.core.runtime.IExecutableExtensionFactory#create()
     */
    @Override
    public Object create()
        throws CoreException
    {
        final IComponentStrategyUI componentStrategyUI = COMPONENT_STRATEGY_UIS.get( componentStrategyId_ );
        if( componentStrategyUI == null )
        {
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.ComponentStrategyUIExtensionFactory_create_unknownId( componentStrategyId_ ) ) );
        }

        return componentStrategyUI;
    }

    /*
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
     */
    @Override
    public void setInitializationData(
        final @Nullable IConfigurationElement config,
        final @Nullable String propertyName,
        final @Nullable Object data )
        throws CoreException
    {
        assert config != null;

        final String idString = config.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.ComponentStrategyUIExtensionFactory_setInitializationData_missingId ) );
        }

        componentStrategyId_ = ComponentStrategyId.fromString( idString );
    }
}
