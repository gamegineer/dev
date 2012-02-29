/*
 * Application.java
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
 * Created on Sep 16, 2009 at 11:38:32 PM.
 */

package org.gamegineer.table.internal.product;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.gamegineer.common.core.app.BrandingUtils;
import org.gamegineer.common.core.app.IBranding;
import org.gamegineer.table.ui.ITableAdvisor;
import org.gamegineer.table.ui.TableAdvisor;
import org.gamegineer.table.ui.TableResult;
import org.gamegineer.table.ui.TableUIFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceRegistration;

/**
 * The entry point of the Gamegineer table application.
 */
@ThreadSafe
public final class Application
    implements IApplication
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The registration token for the application branding service. */
    private final AtomicReference<ServiceRegistration<IBranding>> brandingServiceRegistrationRef_;

    /**
     * A reference to the asynchronous completion token associated with the
     * running table.
     */
    private final AtomicReference<Future<TableResult>> futureRef_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Application} class.
     */
    public Application()
    {
        brandingServiceRegistrationRef_ = new AtomicReference<ServiceRegistration<IBranding>>();
        futureRef_ = new AtomicReference<Future<TableResult>>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a table advisor based on the specified application context.
     * 
     * @param context
     *        The application context; must not be {@code null}.
     * 
     * @return A table advisor; never {@code null}.
     */
    /* @NonNull */
    private static ITableAdvisor createTableAdvisor(
        /* @NonNull */
        final IApplicationContext context )
    {
        assert context != null;

        final List<String> applicationArguments = parseApplicationArguments( (String[])context.getArguments().get( IApplicationContext.APPLICATION_ARGS ) );
        return new TableAdvisor( applicationArguments );
    }

    /**
     * Parses the specified application arguments array.
     * 
     * @param applicationArgumentsArray
     *        The application arguments array; may be {@code null}.
     * 
     * @return The collection of application arguments; never {@code null}.
     */
    /* @NonNull */
    private static List<String> parseApplicationArguments(
        /* @Nullable */
        final String[] applicationArgumentsArray )
    {
        if( (applicationArgumentsArray == null) || (applicationArgumentsArray.length == 0) )
        {
            return Collections.emptyList();
        }

        return Arrays.asList( applicationArgumentsArray );
    }

    /**
     * Publishes the branding service for the application.
     * 
     * @param context
     *        The application context; must not be {@code null}.
     * 
     * @return The branding service; never {@code null}.
     */
    /* @NonNull */
    private IBranding publishBranding(
        /* @NonNull */
        final IApplicationContext context )
    {
        assert context != null;

        final IBranding branding = new Branding( context );
        brandingServiceRegistrationRef_.set( Activator.getDefault().getBundleContext().registerService( IBranding.class, branding, null ) );
        return branding;
    }

    /*
     * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
     */
    @Override
    public Object start(
        final IApplicationContext context )
        throws Exception
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final IBranding branding = publishBranding( context );
        Loggers.getDefaultLogger().info( NonNlsMessages.Application_start_starting( BrandingUtils.getVersion( branding ) ) );

        final ITableAdvisor advisor = createTableAdvisor( context );
        final Future<TableResult> future = Activator.getDefault().getExecutorService().submit( TableUIFactory.createTableRunner( advisor ) );
        futureRef_.set( future );

        context.applicationRunning();

        try
        {
            TableResult result;
            try
            {
                result = future.get();
            }
            catch( final CancellationException e )
            {
                Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.Application_start_cancelled, e );
                result = TableResult.OK;
            }

            Loggers.getDefaultLogger().info( NonNlsMessages.Application_start_stopped( result ) );
            return toApplicationExitObject( result );
        }
        finally
        {
            futureRef_.set( null );
            unpublishBranding();
        }
    }

    /*
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    @Override
    public void stop()
    {
        final Future<?> future = futureRef_.get();
        if( future != null )
        {
            Loggers.getDefaultLogger().info( NonNlsMessages.Application_stop_stopping );
            if( !future.cancel( true ) )
            {
                Loggers.getDefaultLogger().severe( NonNlsMessages.Application_stop_cancelFailed );
            }
        }
    }

    /**
     * Converts the specified table result to an appropriate Equinox application
     * exit object.
     * 
     * @param result
     *        The table result; must not be {@code null}.
     * 
     * @return The Equinox application exit object corresponding to the
     *         specified table result; never {@code null}.
     */
    /* @NonNull */
    private static Object toApplicationExitObject(
        /* @NonNull */
        final TableResult result )
    {
        assert result != null;

        @SuppressWarnings( "boxing" )
        final Integer exitCode = result.getExitCode();
        return exitCode;
    }

    /**
     * Unpublishes the branding service for the application.
     */
    private void unpublishBranding()
    {
        final ServiceRegistration<IBranding> brandingServiceRegistration = brandingServiceRegistrationRef_.getAndSet( null );
        if( brandingServiceRegistration != null )
        {
            brandingServiceRegistration.unregister();
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Adapts an instance of {@link IApplicationContext} to {@link IBranding}.
     */
    @Immutable
    private static final class Branding
        implements IBranding
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The application context. */
        private final IApplicationContext context_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Branding} class.
         * 
         * @param context
         *        The application context; must not be {@code null}.
         */
        Branding(
            /* @NonNull */
            final IApplicationContext context )
        {
            assert context != null;

            context_ = context;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.common.core.app.IBranding#getApplication()
         */
        @Override
        public String getApplication()
        {
            return context_.getBrandingApplication();
        }

        /*
         * @see org.gamegineer.common.core.app.IBranding#getBundle()
         */
        @Override
        public Bundle getBundle()
        {
            return context_.getBrandingBundle();
        }

        /*
         * @see org.gamegineer.common.core.app.IBranding#getDescription()
         */
        @Override
        public String getDescription()
        {
            return context_.getBrandingDescription();
        }

        /*
         * @see org.gamegineer.common.core.app.IBranding#getId()
         */
        @Override
        public String getId()
        {
            return context_.getBrandingId();
        }

        /*
         * @see org.gamegineer.common.core.app.IBranding#getName()
         */
        @Override
        public String getName()
        {
            return context_.getBrandingName();
        }

        /*
         * @see org.gamegineer.common.core.app.IBranding#getProperty(java.lang.String)
         */
        @Override
        public String getProperty(
            final String name )
        {
            assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

            return context_.getBrandingProperty( name );
        }
    }
}
