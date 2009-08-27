/*
 * ComponentService.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Apr 11, 2008 at 11:49:41 PM.
 */

package org.gamegineer.common.internal.core.services.component;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.core.services.component.ComponentCreationException;
import org.gamegineer.common.core.services.component.IComponentCreationContext;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.common.core.services.component.IComponentService;
import org.gamegineer.common.core.services.component.IComponentSpecification;
import org.gamegineer.common.core.services.component.UnsupportedComponentSpecificationException;
import org.gamegineer.common.internal.core.Activator;
import org.gamegineer.common.internal.core.Debug;
import org.gamegineer.common.internal.core.Services;

/**
 * Implementation of
 * {@link org.gamegineer.common.core.services.component.IComponentService}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class ComponentService
    implements IComponentService
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension point attribute specifying the component factory class. */
    private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

    /** The collection of component factories directly managed by this object. */
    private final CopyOnWriteArrayList<IComponentFactory> m_factories;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentService} class.
     */
    public ComponentService()
    {
        m_factories = new CopyOnWriteArrayList<IComponentFactory>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.IComponentService#createComponent(org.gamegineer.common.core.services.component.IComponentSpecification, org.gamegineer.common.core.services.component.IComponentCreationContext)
     */
    public Object createComponent(
        final IComponentSpecification specification,
        final IComponentCreationContext context )
        throws ComponentCreationException, UnsupportedComponentSpecificationException
    {
        assertArgumentNotNull( specification, "specification" ); //$NON-NLS-1$
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        for( final IComponentFactory factory : getComponentFactories() )
        {
            if( specification.matches( factory ) )
            {
                return factory.createComponent( context );
            }
        }

        throw new UnsupportedComponentSpecificationException();
    }

    /*
     * @see org.gamegineer.common.core.services.component.IComponentService#getComponentFactories()
     */
    public Collection<IComponentFactory> getComponentFactories()
    {
        final List<IComponentFactory> factories = new ArrayList<IComponentFactory>( m_factories );
        factories.addAll( getForeignComponentFactories() );
        return Collections.unmodifiableList( factories );
    }

    /*
     * @see org.gamegineer.common.core.services.component.IComponentService#getComponentFactories(org.gamegineer.common.core.services.component.IComponentSpecification)
     */
    public Collection<IComponentFactory> getComponentFactories(
        final IComponentSpecification specification )
    {
        assertArgumentNotNull( specification, "specification" ); //$NON-NLS-1$

        List<IComponentFactory> factories = null;
        for( final IComponentFactory factory : getComponentFactories() )
        {
            if( specification.matches( factory ) )
            {
                if( factories == null )
                {
                    factories = new ArrayList<IComponentFactory>();
                }
                factories.add( factory );
            }
        }

        if( factories == null )
        {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList( factories );
    }

    /**
     * Gets a collection of all foreign component factories not directly managed
     * by this object.
     * 
     * @return A collection of all foreign component factories not directly
     *         managed by this object; never {@code null}.
     */
    /* @NonNull */
    private static Collection<IComponentFactory> getForeignComponentFactories()
    {
        final Collection<IComponentFactory> factories = new ArrayList<IComponentFactory>();
        factories.addAll( getForeignComponentFactoriesFromServiceRegistry() );
        factories.addAll( getForeignComponentFactoriesFromExtensionRegistry() );
        return factories;
    }

    /**
     * Gets a collection of all foreign component factories declared in the
     * extension registry.
     * 
     * @return A collection of all foreign component factories declared in the
     *         extension registry; never {@code null}.
     */
    /* @NonNull */
    private static Collection<IComponentFactory> getForeignComponentFactoriesFromExtensionRegistry()
    {
        // TODO: Create proxies instead so we can lazy load the hosting
        // component factory plug-ins.  (See section 17.3.2 of Clayberg-06.)

        final Collection<IComponentFactory> factories = new ArrayList<IComponentFactory>();
        for( final IConfigurationElement element : Platform.getExtensionRegistry().getConfigurationElementsFor( Activator.SYMBOLIC_NAME, Activator.EXTENSION_COMPONENT_FACTORIES ) )
        {
            try
            {
                factories.add( (IComponentFactory)element.createExecutableExtension( ATTR_CLASS ) );
            }
            catch( final CoreException e )
            {
                // NB: Cannot use the logging service to log exceptions because
                // the logging service itself depends on the component service,
                // and it will not yet be initialized within this method.
                if( Debug.SERVICES_COMPONENT )
                {
                    Debug.trace( String.format( "An error occurred while creating the component factory with class name '%1$s'.", element.getAttribute( ATTR_CLASS ) ), e ); //$NON-NLS-1$
                }
            }
        }
        return factories;
    }

    /**
     * Gets a collection of all foreign component factories declared in the
     * service registry.
     * 
     * @return A collection of all foreign component factories declared in the
     *         service registry; never {@code null}.
     */
    /* @NonNull */
    private static Collection<IComponentFactory> getForeignComponentFactoriesFromServiceRegistry()
    {
        return Services.getDefault().getComponentFactories();
    }

    /*
     * @see org.gamegineer.common.core.services.component.IComponentService#registerComponentFactory(org.gamegineer.common.core.services.component.IComponentFactory)
     */
    public void registerComponentFactory(
        final IComponentFactory factory )
    {
        assertArgumentNotNull( factory, "factory" ); //$NON-NLS-1$

        m_factories.addIfAbsent( factory );
    }

    /*
     * @see org.gamegineer.common.core.services.component.IComponentService#unregisterComponentFactory(org.gamegineer.common.core.services.component.IComponentFactory)
     */
    public void unregisterComponentFactory(
        final IComponentFactory factory )
    {
        assertArgumentNotNull( factory, "factory" ); //$NON-NLS-1$

        m_factories.remove( factory );
    }
}
