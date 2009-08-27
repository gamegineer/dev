/*
 * Services.java
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
 * Created on Feb 28, 2008 at 11:43:06 PM.
 */

package org.gamegineer.common.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.osgi.framework.log.FrameworkLog;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.common.core.services.component.IComponentService;
import org.gamegineer.common.core.services.logging.ILoggingService;
import org.gamegineer.common.internal.core.services.component.ComponentService;
import org.gamegineer.common.internal.core.services.logging.LoggingService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Manages the OSGi services used by the bundle.
 * 
 * <p>
 * The {@code close} method should be called before the bundle is stopped.
 * </p>
 */
public final class Services
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default debug options service to use if no service was registered. */
    private static final DebugOptions c_defaultDebugOptions = new NullDebugOptions();

    /** The singleton instance. */
    private static final Services c_instance = new Services();

    /** The component factory service tracker. */
    private ServiceTracker m_componentFactoryServiceTracker;

    /** The component service registration token. */
    private ServiceRegistration m_componentServiceRegistration;

    /** The component service tracker. */
    private ServiceTracker m_componentServiceTracker;

    /** The debug options service tracker. */
    private ServiceTracker m_debugOptionsServiceTracker;

    /** The extension registry service tracker. */
    private ServiceTracker m_extensionRegistryServiceTracker;

    /** The framework log service tracker. */
    private ServiceTracker m_frameworkLogServiceTracker;

    /** The logging service registration token. */
    private ServiceRegistration m_loggingServiceRegistration;

    /** The logging service tracker. */
    private ServiceTracker m_loggingServiceTracker;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Services} class.
     */
    private Services()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the services managed by this object.
     */
    void close()
    {
        // Close bundle-specific services
        if( m_loggingServiceTracker != null )
        {
            m_loggingServiceTracker.close();
            m_loggingServiceTracker = null;
        }
        if( m_frameworkLogServiceTracker != null )
        {
            m_frameworkLogServiceTracker.close();
            m_frameworkLogServiceTracker = null;
        }
        if( m_extensionRegistryServiceTracker != null )
        {
            m_extensionRegistryServiceTracker.close();
            m_extensionRegistryServiceTracker = null;
        }
        if( m_debugOptionsServiceTracker != null )
        {
            m_debugOptionsServiceTracker.close();
            m_debugOptionsServiceTracker = null;
        }
        if( m_componentFactoryServiceTracker != null )
        {
            m_componentFactoryServiceTracker.close();
            m_componentFactoryServiceTracker = null;
        }
        if( m_componentServiceTracker != null )
        {
            m_componentServiceTracker.close();
            m_componentServiceTracker = null;
        }

        // Unregister package-specific services
        org.gamegineer.common.internal.core.util.logging.Services.getDefault().close();

        // Unregister bundle-specific services
        if( m_loggingServiceRegistration != null )
        {
            m_loggingServiceRegistration.unregister();
            m_loggingServiceRegistration = null;
        }
        if( m_componentServiceRegistration != null )
        {
            m_componentServiceRegistration.unregister();
            m_componentServiceRegistration = null;
        }
    }

    /**
     * Gets an immutable collection of all component factories registered with
     * the OSGi Framework.
     * 
     * @return An immutable collection of all component factories registered
     *         with the OSGi Framework; never {@code null}. This collection is
     *         a snapshot of the component factories available at the time of
     *         the call.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public Collection<IComponentFactory> getComponentFactories()
    {
        assertStateLegal( m_componentFactoryServiceTracker != null, Messages.Services_componentFactoryServiceTracker_notSet );

        final Object[] services = m_componentFactoryServiceTracker.getServices();
        if( (services == null) || (services.length == 0) )
        {
            return Collections.emptyList();
        }

        final List<IComponentFactory> factories = new ArrayList<IComponentFactory>( services.length );
        for( final Object service : services )
        {
            factories.add( (IComponentFactory)service );
        }
        return Collections.unmodifiableList( factories );
    }

    /**
     * Gets the component service managed by this object.
     * 
     * @return The component service managed by this object; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public IComponentService getComponentService()
    {
        assertStateLegal( m_componentServiceTracker != null, Messages.Services_componentServiceTracker_notSet );

        return (IComponentService)m_componentServiceTracker.getService();
    }

    /**
     * Gets the debug options service managed by this object.
     * 
     * @return The debug options service managed by this object; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public DebugOptions getDebugOptions()
    {
        assertStateLegal( m_debugOptionsServiceTracker != null, Messages.Services_debugOptionsServiceTracker_notSet );

        final DebugOptions debugOptions = (DebugOptions)m_debugOptionsServiceTracker.getService();
        if( debugOptions == null )
        {
            return c_defaultDebugOptions;
        }

        return debugOptions;
    }

    /**
     * Gets the default instance of the {@code Services} class.
     * 
     * @return The default instance of the {@code Services} class; never
     *         {@code null}.
     */
    /* @NonNull */
    public static Services getDefault()
    {
        return c_instance;
    }

    /**
     * Gets the extension registry service managed by this object.
     * 
     * @return The extension registry service managed by this object; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public IExtensionRegistry getExtensionRegistry()
    {
        assertStateLegal( m_extensionRegistryServiceTracker != null, Messages.Services_extensionRegistryServiceTracker_notSet );

        return (IExtensionRegistry)m_extensionRegistryServiceTracker.getService();
    }

    /**
     * Gets the framework log service managed by this object.
     * 
     * @return The framework log service managed by this object; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public FrameworkLog getFrameworkLog()
    {
        assertStateLegal( m_frameworkLogServiceTracker != null, Messages.Services_frameworkLogServiceTracker_notSet );

        return (FrameworkLog)m_frameworkLogServiceTracker.getService();
    }

    /**
     * Gets the logging service managed by this object.
     * 
     * @return The logging service managed by this object; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public ILoggingService getLoggingService()
    {
        assertStateLegal( m_loggingServiceTracker != null, Messages.Services_loggingServiceTracker_notSet );

        return (ILoggingService)m_loggingServiceTracker.getService();
    }

    /**
     * Opens the services managed by this object.
     * 
     * @param context
     *        The execution context of the bundle; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    void open(
        /* @NonNull */
        final BundleContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        // Register bundle-specific services
        m_componentServiceRegistration = context.registerService( IComponentService.class.getName(), new ComponentService(), null );
        m_loggingServiceRegistration = context.registerService( ILoggingService.class.getName(), new LoggingService(), null );

        // Register package-specific services
        org.gamegineer.common.internal.core.util.logging.Services.getDefault().open( context );

        // Open bundle-specific services
        m_componentServiceTracker = new ServiceTracker( context, m_componentServiceRegistration.getReference(), null );
        m_componentServiceTracker.open();
        m_componentFactoryServiceTracker = new ServiceTracker( context, IComponentFactory.class.getName(), null );
        m_componentFactoryServiceTracker.open();
        m_debugOptionsServiceTracker = new ServiceTracker( context, DebugOptions.class.getName(), null );
        m_debugOptionsServiceTracker.open();
        m_extensionRegistryServiceTracker = new ServiceTracker( context, IExtensionRegistry.class.getName(), null );
        m_extensionRegistryServiceTracker.open();
        m_frameworkLogServiceTracker = new ServiceTracker( context, FrameworkLog.class.getName(), null );
        m_frameworkLogServiceTracker.open();
        m_loggingServiceTracker = new ServiceTracker( context, m_loggingServiceRegistration.getReference(), null );
        m_loggingServiceTracker.open();
    }
}
