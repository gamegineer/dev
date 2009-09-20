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
import net.jcip.annotations.ThreadSafe;
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
@ThreadSafe
public final class Services
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default debug options service to use if no service was registered. */
    private static final DebugOptions defaultDebugOptions_ = new NullDebugOptions();

    /** The singleton instance. */
    private static final Services instance_ = new Services();

    /** The component factory service tracker. */
    private ServiceTracker componentFactoryServiceTracker_;

    /** The component service registration token. */
    private ServiceRegistration componentServiceRegistration_;

    /** The component service tracker. */
    private ServiceTracker componentServiceTracker_;

    /** The debug options service tracker. */
    private ServiceTracker debugOptionsServiceTracker_;

    /** The extension registry service tracker. */
    private ServiceTracker extensionRegistryServiceTracker_;

    /** The framework log service tracker. */
    private ServiceTracker frameworkLogServiceTracker_;

    /** The logging service registration token. */
    private ServiceRegistration loggingServiceRegistration_;

    /** The logging service tracker. */
    private ServiceTracker loggingServiceTracker_;


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
        if( loggingServiceTracker_ != null )
        {
            loggingServiceTracker_.close();
            loggingServiceTracker_ = null;
        }
        if( frameworkLogServiceTracker_ != null )
        {
            frameworkLogServiceTracker_.close();
            frameworkLogServiceTracker_ = null;
        }
        if( extensionRegistryServiceTracker_ != null )
        {
            extensionRegistryServiceTracker_.close();
            extensionRegistryServiceTracker_ = null;
        }
        if( debugOptionsServiceTracker_ != null )
        {
            debugOptionsServiceTracker_.close();
            debugOptionsServiceTracker_ = null;
        }
        if( componentFactoryServiceTracker_ != null )
        {
            componentFactoryServiceTracker_.close();
            componentFactoryServiceTracker_ = null;
        }
        if( componentServiceTracker_ != null )
        {
            componentServiceTracker_.close();
            componentServiceTracker_ = null;
        }

        // Unregister package-specific services
        org.gamegineer.common.internal.core.util.logging.Services.getDefault().close();

        // Unregister bundle-specific services
        if( loggingServiceRegistration_ != null )
        {
            loggingServiceRegistration_.unregister();
            loggingServiceRegistration_ = null;
        }
        if( componentServiceRegistration_ != null )
        {
            componentServiceRegistration_.unregister();
            componentServiceRegistration_ = null;
        }
    }

    /**
     * Gets an immutable collection of all component factories registered with
     * the OSGi Framework.
     * 
     * @return An immutable collection of all component factories registered
     *         with the OSGi Framework; never {@code null}. This collection is a
     *         snapshot of the component factories available at the time of the
     *         call.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public Collection<IComponentFactory> getComponentFactories()
    {
        assertStateLegal( componentFactoryServiceTracker_ != null, Messages.Services_componentFactoryServiceTracker_notSet );

        final Object[] services = componentFactoryServiceTracker_.getServices();
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
        assertStateLegal( componentServiceTracker_ != null, Messages.Services_componentServiceTracker_notSet );

        return (IComponentService)componentServiceTracker_.getService();
    }

    /**
     * Gets the debug options service managed by this object.
     * 
     * @return The debug options service managed by this object; never {@code
     *         null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public DebugOptions getDebugOptions()
    {
        assertStateLegal( debugOptionsServiceTracker_ != null, Messages.Services_debugOptionsServiceTracker_notSet );

        final DebugOptions debugOptions = (DebugOptions)debugOptionsServiceTracker_.getService();
        if( debugOptions == null )
        {
            return defaultDebugOptions_;
        }

        return debugOptions;
    }

    /**
     * Gets the default instance of the {@code Services} class.
     * 
     * @return The default instance of the {@code Services} class; never {@code
     *         null}.
     */
    /* @NonNull */
    public static Services getDefault()
    {
        return instance_;
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
        assertStateLegal( extensionRegistryServiceTracker_ != null, Messages.Services_extensionRegistryServiceTracker_notSet );

        return (IExtensionRegistry)extensionRegistryServiceTracker_.getService();
    }

    /**
     * Gets the framework log service managed by this object.
     * 
     * @return The framework log service managed by this object; never {@code
     *         null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public FrameworkLog getFrameworkLog()
    {
        assertStateLegal( frameworkLogServiceTracker_ != null, Messages.Services_frameworkLogServiceTracker_notSet );

        return (FrameworkLog)frameworkLogServiceTracker_.getService();
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
        assertStateLegal( loggingServiceTracker_ != null, Messages.Services_loggingServiceTracker_notSet );

        return (ILoggingService)loggingServiceTracker_.getService();
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
        componentServiceRegistration_ = context.registerService( IComponentService.class.getName(), new ComponentService(), null );
        loggingServiceRegistration_ = context.registerService( ILoggingService.class.getName(), new LoggingService(), null );

        // Register package-specific services
        org.gamegineer.common.internal.core.util.logging.Services.getDefault().open( context );

        // Open bundle-specific services
        componentServiceTracker_ = new ServiceTracker( context, componentServiceRegistration_.getReference(), null );
        componentServiceTracker_.open();
        componentFactoryServiceTracker_ = new ServiceTracker( context, IComponentFactory.class.getName(), null );
        componentFactoryServiceTracker_.open();
        debugOptionsServiceTracker_ = new ServiceTracker( context, DebugOptions.class.getName(), null );
        debugOptionsServiceTracker_.open();
        extensionRegistryServiceTracker_ = new ServiceTracker( context, IExtensionRegistry.class.getName(), null );
        extensionRegistryServiceTracker_.open();
        frameworkLogServiceTracker_ = new ServiceTracker( context, FrameworkLog.class.getName(), null );
        frameworkLogServiceTracker_.open();
        loggingServiceTracker_ = new ServiceTracker( context, loggingServiceRegistration_.getReference(), null );
        loggingServiceTracker_.open();
    }
}
