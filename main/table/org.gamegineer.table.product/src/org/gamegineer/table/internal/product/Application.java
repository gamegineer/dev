/*
 * Application.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.app.BrandingUtils;
import org.gamegineer.common.core.app.IBranding;
import org.gamegineer.table.ui.ITableAdvisor;
import org.gamegineer.table.ui.ITableRunnerFactory;
import org.gamegineer.table.ui.TableAdvisor;
import org.gamegineer.table.ui.TableResult;
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
    private final AtomicReference<@Nullable ServiceRegistration<IBranding>> brandingServiceRegistrationRef_;

    /**
     * A reference to the asynchronous completion token associated with the
     * running table.
     */
    private final AtomicReference<@Nullable Future<TableResult>> futureRef_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Application} class.
     */
    public Application()
    {
        brandingServiceRegistrationRef_ = new AtomicReference<>();
        futureRef_ = new AtomicReference<>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a table advisor based on the specified application context.
     * 
     * @param context
     *        The application context.
     * 
     * @return A table advisor.
     */
    private static ITableAdvisor createTableAdvisor(
        final IApplicationContext context )
    {
        final List<String> applicationArguments = parseApplicationArguments( (@NonNull String @Nullable [])context.getArguments().get( IApplicationContext.APPLICATION_ARGS ) );
        return new TableAdvisor( applicationArguments );
    }

    /**
     * Parses the specified application arguments array.
     * 
     * @param applicationArgumentsArray
     *        The application arguments array.
     * 
     * @return The collection of application arguments.
     */
    private static List<String> parseApplicationArguments(
        final @NonNull String @Nullable [] applicationArgumentsArray )
    {
        if( (applicationArgumentsArray == null) || (applicationArgumentsArray.length == 0) )
        {
            return Collections.<@NonNull String>emptyList();
        }

        return Arrays.asList( applicationArgumentsArray );
    }

    /**
     * Publishes the branding service for the application.
     * 
     * @param context
     *        The application context.
     * 
     * @return The branding service.
     */
    private IBranding publishBranding(
        final IApplicationContext context )
    {
        final IBranding branding = new Branding( context );
        brandingServiceRegistrationRef_.set( Activator.getDefault().getBundleContext().<@NonNull IBranding>registerService( IBranding.class, branding, null ) );
        return branding;
    }

    /*
     * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
     */
    @Override
    public Object start(
        final @Nullable IApplicationContext context )
        throws Exception
    {
        assert context != null;

        final IBranding branding = publishBranding( context );
        Loggers.getDefaultLogger().info( NonNlsMessages.Application_start_starting( BrandingUtils.getVersion( branding ) ) );

        final ITableRunnerFactory tableRunnerFactory = Activator.getDefault().getTableRunnerFactory();
        if( tableRunnerFactory == null )
        {
            throw new Exception( NonNlsMessages.Application_start_tableRunnerFactoryNotAvailable );
        }

        final ITableAdvisor advisor = createTableAdvisor( context );
        final Future<TableResult> future = Activator.getDefault().getExecutorService().submit( tableRunnerFactory.createTableRunner( advisor ) );
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
     *        The table result.
     * 
     * @return The Equinox application exit object corresponding to the
     *         specified table result.
     */
    private static Object toApplicationExitObject(
        final TableResult result )
    {
        return Integer.valueOf( result.getExitCode() );
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
         *        The application context.
         */
        Branding(
            final IApplicationContext context )
        {
            context_ = context;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.common.core.app.IBranding#getApplication()
         */
        @Override
        public @Nullable String getApplication()
        {
            return context_.getBrandingApplication();
        }

        /*
         * @see org.gamegineer.common.core.app.IBranding#getBundle()
         */
        @Override
        public @Nullable Bundle getBundle()
        {
            return context_.getBrandingBundle();
        }

        /*
         * @see org.gamegineer.common.core.app.IBranding#getDescription()
         */
        @Override
        public @Nullable String getDescription()
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
        public @Nullable String getName()
        {
            return context_.getBrandingName();
        }

        /*
         * @see org.gamegineer.common.core.app.IBranding#getProperty(java.lang.String)
         */
        @Override
        public @Nullable String getProperty(
            final String name )
        {
            return context_.getBrandingProperty( name );
        }
    }
}
