/*
 * ContainerLayoutExtensionFactory.java
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
 * Created on Aug 9, 2012 at 9:39:22 PM.
 */

package org.gamegineer.table.internal.core.impl.layouts;

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
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.internal.core.impl.BundleConstants;

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
     * The configuration element attribute specifying the container layout
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /** The collection of container layouts supported by this factory. */
    private static final Map<ContainerLayoutId, IContainerLayout> CONTAINER_LAYOUTS;

    /** The identifier of the container layouts to create. */
    private @Nullable ContainerLayoutId containerLayoutId_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code ContainerLayoutExtensionFactory} class.
     */
    static
    {
        final Map<ContainerLayoutId, IContainerLayout> containerLayouts = new HashMap<>();
        containerLayouts.put( InternalContainerLayouts.ACCORDIAN_DOWN.getId(), InternalContainerLayouts.ACCORDIAN_DOWN );
        containerLayouts.put( InternalContainerLayouts.ACCORDIAN_LEFT.getId(), InternalContainerLayouts.ACCORDIAN_LEFT );
        containerLayouts.put( InternalContainerLayouts.ACCORDIAN_RIGHT.getId(), InternalContainerLayouts.ACCORDIAN_RIGHT );
        containerLayouts.put( InternalContainerLayouts.ACCORDIAN_UP.getId(), InternalContainerLayouts.ACCORDIAN_UP );
        containerLayouts.put( InternalContainerLayouts.STACKED.getId(), InternalContainerLayouts.STACKED );
        CONTAINER_LAYOUTS = Collections.unmodifiableMap( containerLayouts );
    }

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
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.ContainerLayoutExtensionFactory_setInitializationData_missingId ) );
        }

        containerLayoutId_ = ContainerLayoutId.fromString( idString );
    }
}
