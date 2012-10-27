/*
 * CardPilePrototypeFactory.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Oct 26, 2012 at 10:20:20 PM.
 */

package org.gamegineer.table.internal.ui.prototypes;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.Status;
import org.gamegineer.table.core.CardPileOrientation;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.ComponentStrategyRegistry;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ComponentSurfaceDesignRegistry;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.NoSuchComponentStrategyException;
import org.gamegineer.table.core.NoSuchComponentSurfaceDesignException;
import org.gamegineer.table.internal.ui.BundleConstants;
import org.gamegineer.table.ui.prototype.ComponentPrototypeFactoryException;
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;

/**
 * A factory for creating card pile prototypes from the extension registry.
 */
@NotThreadSafe
public final class CardPilePrototypeFactory
    implements IComponentPrototypeFactory, IExecutableExtension
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the configuration element parameter specifying the component
     * surface design identifier for the card pile base.
     */
    private static final String PARAM_NAME_BASE_DESIGN_ID = "baseDesign"; //$NON-NLS-1$

    /** The identifier of the component surface design of the card pile base. */
    private ComponentSurfaceDesignId baseDesignId_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPilePrototypeFactory} class.
     */
    public CardPilePrototypeFactory()
    {
        baseDesignId_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.prototype.IComponentPrototypeFactory#createComponentPrototype(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    public List<IComponent> createComponentPrototype(
        final ITableEnvironment tableEnvironment )
        throws ComponentPrototypeFactoryException
    {
        assertArgumentNotNull( tableEnvironment, "tableEnvironment" ); //$NON-NLS-1$

        try
        {
            final IComponent cardPile = tableEnvironment.createContainer( ComponentStrategyRegistry.getContainerStrategy( ComponentStrategyId.fromString( "org.gamegineer.componentStrategies.cardPile" ) ) ); //$NON-NLS-1$
            cardPile.setSurfaceDesign( CardPileOrientation.BASE, ComponentSurfaceDesignRegistry.getComponentSurfaceDesign( baseDesignId_ ) );
            return Collections.singletonList( cardPile );
        }
        catch( final NoSuchComponentStrategyException e )
        {
            throw new ComponentPrototypeFactoryException( NonNlsMessages.CardPilePrototypeFactory_createComponentPrototype_error, e );
        }
        catch( final NoSuchComponentSurfaceDesignException e )
        {
            throw new ComponentPrototypeFactoryException( NonNlsMessages.CardPilePrototypeFactory_createComponentPrototype_error, e );
        }
    }

    /*
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
     */
    @Override
    public void setInitializationData(
        @SuppressWarnings( "unused" )
        final IConfigurationElement config,
        @SuppressWarnings( "unused" )
        final String propertyName,
        final Object data )
        throws CoreException
    {
        @SuppressWarnings( "unchecked" )
        final Map<String, String> parameters = (Map<String, String>)data;
        if( parameters == null )
        {
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.CardPilePrototypeFactory_setInitializationData_parametersNotSpecified ) );
        }

        final String encodedBaseDesignId = parameters.get( PARAM_NAME_BASE_DESIGN_ID );
        if( encodedBaseDesignId == null )
        {
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.CardPilePrototypeFactory_setInitializationData_parameterNotSpecified( PARAM_NAME_BASE_DESIGN_ID ) ) );
        }

        baseDesignId_ = ComponentSurfaceDesignId.fromString( encodedBaseDesignId );
    }
}
