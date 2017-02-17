/*
 * LoggingService.java
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
 * Created on May 14, 2008 at 10:30:07 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Logger;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.logging.ILoggingService;
import org.gamegineer.common.internal.core.impl.Debug;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * Implementation of {@link ILoggingService}.
 */
@ThreadSafe
public final class LoggingService
    implements ILoggingService
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The bundle-relative path of the logging properties file. */
    private static final String LOGGING_PROPERTIES_PATH = "logging.properties"; //$NON-NLS-1$

    /** The instance lock. */
    private final Object lock_;

    /**
     * The collection of managed loggers. The key is the logger name. The value
     * is the logger.
     */
    @GuardedBy( "lock_" )
    private final Map<String, Logger> loggers_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggingService} class.
     */
    public LoggingService()
    {
        lock_ = new Object();
        loggers_ = new HashMap<>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Activates the logging service component.
     * 
     * @param bundleContext
     *        The bundle context.
     */
    public void activate(
        final BundleContext bundleContext )
    {
        // NB: This seemingly meaningless log message is actually required in
        // order to ensure the "org.gamegineer" logger is created so that all
        // other Gamegineer bundle loggers will inherit its attributes.
        // DO NOT REMOVE THIS STATEMENT!
        getLogger( bundleContext.getBundle() ).info( "Gamegineer logging service activated" ); //$NON-NLS-1$
    }

    /**
     * Configures all ancestors of the named logger (creating them if necessary)
     * which have a configuration defined in the specified logging properties.
     * 
     * <p>
     * This method must be called while {@code lock_} is held.
     * </p>
     * 
     * @param loggerName
     *        The logger name.
     * @param properties
     *        The logging properties.
     */
    @GuardedBy( "lock_" )
    private void configureAncestorLoggers(
        final String loggerName,
        final Map<String, String> properties )
    {
        assert Thread.holdsLock( lock_ );

        for( final String ancestorLoggerName : LoggingProperties.getAncestorLoggerNames( properties, loggerName ) )
        {
            if( !loggers_.containsKey( ancestorLoggerName ) )
            {
                final Logger logger = Logger.getLogger( ancestorLoggerName );
                loggers_.put( ancestorLoggerName, logger );
                configureLogger( logger, new LoggerConfiguration( ancestorLoggerName, properties ) );
            }
        }
    }

    /**
     * Configures the specified logger.
     * 
     * @param logger
     *        The logger.
     * @param config
     *        The logger configuration.
     */
    private static void configureLogger(
        final Logger logger,
        final LoggerConfiguration config )
    {
        Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Configuring logger '%1$s'", logger.getName() ) ); //$NON-NLS-1$

        logger.setFilter( config.getFilter( logger.getFilter() ) );
        logger.setLevel( config.getLevel( logger.getLevel() ) );
        logger.setUseParentHandlers( config.getUseParentHandlers( logger.getUseParentHandlers() ) );

        for( final Handler handler : config.getHandlers() )
        {
            logger.addHandler( handler );
        }
    }

    /*
     * @see org.gamegineer.common.core.logging.ILoggingService#getLogger(org.osgi.framework.Bundle)
     */
    @Override
    public Logger getLogger(
        final Bundle bundle )
    {
        return getLogger( bundle, null );
    }

    /*
     * @see org.gamegineer.common.core.logging.ILoggingService#getLogger(org.osgi.framework.Bundle, java.lang.String)
     */
    @Override
    public Logger getLogger(
        final Bundle bundle,
        final @Nullable String name )
    {
        Logger logger = null;
        final String loggerName = getLoggerName( bundle, name );

        synchronized( lock_ )
        {
            logger = loggers_.get( loggerName );
            if( logger != null )
            {
                return logger;
            }

            logger = Logger.getLogger( loggerName );
            loggers_.put( loggerName, logger );

            final Map<String, String> properties = getLoggingProperties( bundle );
            if( properties != null )
            {
                configureLogger( logger, new LoggerConfiguration( loggerName, properties ) );
                configureAncestorLoggers( loggerName, properties );
            }
        }

        return logger;
    }

    /**
     * Gets the fully-qualified name of the logger for the specified bundle.
     * 
     * @param bundle
     *        The bundle.
     * @param name
     *        The logger name. If {@code null} or an empty string, the
     *        fully-qualified name of the default logger for the bundle will be
     *        returned.
     * 
     * @return The fully-qualified name of the logger for the specified bundle.
     */
    private static String getLoggerName(
        final Bundle bundle,
        final @Nullable String name )
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( bundle.getSymbolicName() );
        if( (name != null) && !name.isEmpty() )
        {
            sb.append( '.' );
            sb.append( name );
        }
        return sb.toString();
    }

    /**
     * Gets the logging properties for the specified bundle.
     * 
     * @param bundle
     *        The bundle.
     * 
     * @return The logging properties for the specified bundle or {@code null}
     *         if no properties exist or they could not be loaded.
     */
    private static @Nullable Map<String, String> getLoggingProperties(
        final Bundle bundle )
    {
        final URL propertiesUrl = bundle.getEntry( LOGGING_PROPERTIES_PATH );
        if( propertiesUrl == null )
        {
            return null;
        }

        final Properties properties = new Properties();
        try
        {
            properties.load( propertiesUrl.openStream() );
        }
        catch( final IOException e )
        {
            Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Failed to load logging properties from '%1$s'", propertiesUrl ), e ); //$NON-NLS-1$
            return null;
        }

        return LoggingProperties.toMap( properties );
    }
}
