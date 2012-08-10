/*
 * ContainerLayoutExtensionFactory.java
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
 * Created on Aug 9, 2012 at 9:39:22 PM.
 */

package org.gamegineer.table.internal.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.Status;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainerLayout;

/**
 * A factory for creating container layouts located in this bundle from the
 * extension registry.
 */
@NotThreadSafe
public final class ContainerLayoutExtensionFactory
    implements IExecutableExtension, IExecutableExtensionFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * A layout in which the container is laid out with all components at their
     * absolute position in table coordinates.
     */
    static final IContainerLayout ABSOLUTE_LAYOUT = new AbsoluteLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.absolute" ) ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately below it.
     */
    static final IContainerLayout ACCORDIAN_DOWN_LAYOUT = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianDown" ), 0, 18 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately to the left of it.
     */
    static final IContainerLayout ACCORDIAN_LEFT_LAYOUT = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianLeft" ), -16, 0 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately to the right of it.
     */
    static final IContainerLayout ACCORDIAN_RIGHT_LAYOUT = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianRight" ), 16, 0 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately above it.
     */
    static final IContainerLayout ACCORDIAN_UP_LAYOUT = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianUp" ), 0, -18 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out with one component placed on
     * top of the other with no offset.
     */
    static final IContainerLayout STACKED_LAYOUT = new StackedLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.stacked" ), 10, 2, 1 ); //$NON-NLS-1$

    /** The collection of container layouts supported by this factory. */
    private static final Map<ContainerLayoutId, IContainerLayout> CONTAINER_LAYOUTS = createContainerLayouts();

    /** The identifier of the container layouts to create. */
    private ContainerLayoutId containerLayoutId_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayoutExtensionFactory}
     * class.
     */
    public ContainerLayoutExtensionFactory()
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
        final IContainerLayout containerLayout = CONTAINER_LAYOUTS.get( containerLayoutId_ );
        if( containerLayout == null )
        {
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.ContainerLayoutExtensionFactory_create_unknownId( containerLayoutId_ ) ) );
        }

        return containerLayout;
    }

    /**
     * Creates the collection of container layouts supported by this factory.
     * 
     * @return The collection of container layouts supported by this factory;
     *         never {@code null}.
     */
    /* @NonNull */
    private static Map<ContainerLayoutId, IContainerLayout> createContainerLayouts()
    {
        final Map<ContainerLayoutId, IContainerLayout> containerLayouts = new HashMap<ContainerLayoutId, IContainerLayout>();
        containerLayouts.put( ABSOLUTE_LAYOUT.getId(), ABSOLUTE_LAYOUT );
        containerLayouts.put( ACCORDIAN_DOWN_LAYOUT.getId(), ACCORDIAN_DOWN_LAYOUT );
        containerLayouts.put( ACCORDIAN_LEFT_LAYOUT.getId(), ACCORDIAN_LEFT_LAYOUT );
        containerLayouts.put( ACCORDIAN_RIGHT_LAYOUT.getId(), ACCORDIAN_RIGHT_LAYOUT );
        containerLayouts.put( ACCORDIAN_UP_LAYOUT.getId(), ACCORDIAN_UP_LAYOUT );
        containerLayouts.put( STACKED_LAYOUT.getId(), STACKED_LAYOUT );
        return Collections.unmodifiableMap( containerLayouts );
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
        if( !(data instanceof String) )
        {
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.ContainerLayoutExtensionFactory_setInitializationData_unexpectedData ) );
        }

        containerLayoutId_ = ContainerLayoutId.fromString( (String)data );
    }
}
