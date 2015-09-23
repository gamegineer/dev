/*
 * LoggerConfigurationTest.java
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
 * Created on May 15, 2008 at 11:22:40 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Handler;
import java.util.logging.Level;
import org.gamegineer.common.core.logging.LoggingServiceConstants;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentFactory;

/**
 * A fixture for testing the {@link LoggerConfiguration} class.
 */
public final class LoggerConfigurationTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The class name of the filter used in the fixture. */
    private static final String FILTER_CLASS_NAME = FakeFilter.class.getName();

    /** The instance name of the filter used in the fixture. */
    private static final String FILTER_INSTANCE_NAME = "filterName"; //$NON-NLS-1$

    /** The class name of the handler used in the fixture. */
    private static final String HANDLER_CLASS_NAME = FakeHandler.class.getName();

    /** The instance name of the first handler used in the fixture. */
    private static final String HANDLER_INSTANCE_NAME_1 = "handlerName1"; //$NON-NLS-1$

    /** The instance name of the second handler used in the fixture. */
    private static final String HANDLER_INSTANCE_NAME_2 = "handlerName2"; //$NON-NLS-1$

    /** The default logger configuration under test in the fixture. */
    private Optional<LoggerConfiguration> loggerConfiguration_;

    /** The logger properties for use in the fixture. */
    private Optional<Map<String, String>> properties_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggerConfigurationTest} class.
     */
    public LoggerConfigurationTest()
    {
        loggerConfiguration_ = Optional.empty();
        properties_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the logger configuration under test in the fixture.
     * 
     * @return The logger configuration under test in the fixture; never
     *         {@code null}.
     */
    private LoggerConfiguration getLoggerConfiguration()
    {
        return loggerConfiguration_.get();
    }

    /**
     * Gets the fixture logger properties.
     * 
     * @return The fixture logger properties; never {@code null}.
     */
    private Map<String, String> getProperties()
    {
        return properties_.get();
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        final Map<String, String> properties = new HashMap<>();

        // Logger with all properties configured
        properties.put( "logger.default.filter", String.format( "%1$s.%2$s", FILTER_CLASS_NAME, FILTER_INSTANCE_NAME ) ); //$NON-NLS-1$ //$NON-NLS-2$
        properties.put( "logger.default.handlers", String.format( "%1$s.%2$s, %1$s.%3$s", HANDLER_CLASS_NAME, HANDLER_INSTANCE_NAME_1, HANDLER_INSTANCE_NAME_2 ) ); //$NON-NLS-1$ //$NON-NLS-2$
        properties.put( "logger.default.level", Level.SEVERE.getName() ); //$NON-NLS-1$
        properties.put( "logger.default.useParentHandlers", "true" ); //$NON-NLS-1$ //$NON-NLS-2$

        // Logger with an illegal filter
        properties.put( "logger.illegalFilter.filter", "A_NAME_WITHOUT_A_DOT" ); //$NON-NLS-1$ //$NON-NLS-2$

        // Logger with an illegal handler
        properties.put( "logger.illegalHandler.handlers", String.format( "%1$s.%2$s, %3$s", HANDLER_CLASS_NAME, HANDLER_INSTANCE_NAME_1, "A_NAME_WITHOUT_A_DOT" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        // Logger with no filter
        properties.put( "logger.noFilter.level", Level.INFO.getName() ); //$NON-NLS-1$

        // Logger with no handlers
        properties.put( "logger.noHandlers.level", Level.INFO.getName() ); //$NON-NLS-1$

        // Filter
        properties.put( String.format( "%1$s.%2$s.%3$s", FILTER_CLASS_NAME, FILTER_INSTANCE_NAME, FakeFilter.PROPERTY_FAKE_BOOLEAN_PROPERTY ), Boolean.toString( false ) ); //$NON-NLS-1$

        // Handler 1
        properties.put( String.format( "%1$s.%2$s.%3$s", HANDLER_CLASS_NAME, HANDLER_INSTANCE_NAME_1, LoggingServiceConstants.PROPERTY_HANDLER_LEVEL ), Level.SEVERE.getName() ); //$NON-NLS-1$

        // Handler 2
        properties.put( String.format( "%1$s.%2$s.%3$s", HANDLER_CLASS_NAME, HANDLER_INSTANCE_NAME_2, LoggingServiceConstants.PROPERTY_HANDLER_LEVEL ), Level.WARNING.getName() ); //$NON-NLS-1$

        properties_ = Optional.of( properties );
        loggerConfiguration_ = Optional.of( new LoggerConfiguration( "logger.default", properties ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link LoggerConfiguration#LoggerConfiguration} constructor
     * makes a copy of the logging properties collection.
     */
    @Test
    public void testConstructor_Properties_Copy()
    {
        final String loggerName = "logger.default"; //$NON-NLS-1$
        final String propertyName = String.format( "%1$s.%2$s", loggerName, LoggingServiceConstants.PROPERTY_LOGGER_LEVEL ); //$NON-NLS-1$
        final Map<String, String> properties = new HashMap<>();
        properties.put( propertyName, Level.SEVERE.getName() );

        final LoggerConfiguration config = new LoggerConfiguration( loggerName, properties );
        properties.remove( propertyName );

        assertEquals( Level.SEVERE, config.getLevel( Level.OFF ) );
    }

    /**
     * Ensures the {@link LoggerConfiguration#getFilter} method returns the
     * expected value when the logger is configured and the filter configuration
     * is legal.
     */
    @Test
    public void testGetFilter_Configured()
    {
        final ServiceRegistration<ComponentFactory> serviceRegistration = FakeFilter.registerComponentFactory();
        try
        {
            final FakeFilter filter = (FakeFilter)getLoggerConfiguration().getFilter( null );

            assertNotNull( filter );
            assertFalse( filter.getFakeBooleanProperty() );
        }
        finally
        {
            serviceRegistration.unregister();
        }
    }

    /**
     * Ensures the {@link LoggerConfiguration#getFilter} method returns the
     * default value when the logger is configured but the filter could not be
     * created because no component factory is available.
     */
    @Test
    public void testGetFilter_Configured_FilterCreationFailed()
    {
        assertNull( getLoggerConfiguration().getFilter( null ) );
    }

    /**
     * Ensures the {@link LoggerConfiguration#getFilter} method returns the
     * default value when the logger is configured but the filter property is
     * illegal.
     */
    @Test
    public void testGetFilter_Configured_IllegalFilter()
    {
        final LoggerConfiguration config = new LoggerConfiguration( "logger.illegalFilter", getProperties() ); //$NON-NLS-1$

        assertNull( config.getFilter( null ) );
    }

    /**
     * Ensures the {@link LoggerConfiguration#getFilter} method returns the
     * default value when the logger is configured but no filter is specified.
     */
    @Test
    public void testGetFilter_Configured_NoFilter()
    {
        final LoggerConfiguration config = new LoggerConfiguration( "logger.noFilter", getProperties() ); //$NON-NLS-1$

        assertNull( config.getFilter( null ) );
    }

    /**
     * Ensures the {@link LoggerConfiguration#getFilter} method returns the
     * default value when the logger is not configured.
     */
    @Test
    public void testGetFilter_NotConfigured()
    {
        final LoggerConfiguration config = new LoggerConfiguration( "logger.unknown", getProperties() ); //$NON-NLS-1$

        assertNull( config.getFilter( null ) );
    }

    /**
     * Ensures the {@link LoggerConfiguration#getHandlers} method returns the
     * expected value when the logger is configured.
     */
    @Test
    public void testGetHandlers_Configured()
    {
        final ServiceRegistration<ComponentFactory> serviceRegistration = FakeHandler.registerComponentFactory();
        try
        {
            final List<Handler> handlers = getLoggerConfiguration().getHandlers();

            assertEquals( 2, handlers.size() );
            assertEquals( Level.SEVERE, handlers.get( 0 ).getLevel() );
            assertEquals( Level.WARNING, handlers.get( 1 ).getLevel() );
        }
        finally
        {
            serviceRegistration.unregister();
        }
    }

    /**
     * Ensures the {@link LoggerConfiguration#getHandlers} method returns an
     * empty collection when the logger is configured but the handlers could not
     * be created because no component factory is available.
     */
    @Test
    public void testGetHandlers_Configured_HandlerCreationFailed()
    {
        final ServiceRegistration<ComponentFactory> serviceRegistration = FakeHandler.registerFailingComponentFactory();
        try
        {
            final List<Handler> handlers = getLoggerConfiguration().getHandlers();

            assertNotNull( handlers );
            assertTrue( handlers.isEmpty() );
        }
        finally
        {
            serviceRegistration.unregister();
        }
    }

    /**
     * Ensures the {@link LoggerConfiguration#getHandlers} method does not
     * include handlers that have an illegal name when the logger is configured.
     */
    @Test
    public void testGetHandlers_Configured_IllegalHandler()
    {
        final ServiceRegistration<ComponentFactory> serviceRegistration = FakeHandler.registerComponentFactory();
        try
        {
            final LoggerConfiguration config = new LoggerConfiguration( "logger.illegalHandler", getProperties() ); //$NON-NLS-1$

            final List<Handler> handlers = config.getHandlers();

            assertEquals( 1, handlers.size() );
            assertEquals( Level.SEVERE, handlers.get( 0 ).getLevel() );
        }
        finally
        {
            serviceRegistration.unregister();
        }
    }

    /**
     * Ensures the {@link LoggerConfiguration#getHandlers} method returns an
     * empty collection when the logger is configured but no handlers are
     * specified.
     */
    @Test
    public void testGetHandlers_Configured_NoHandlers()
    {
        final LoggerConfiguration config = new LoggerConfiguration( "logger.noHandlers", getProperties() ); //$NON-NLS-1$

        final List<Handler> handlers = config.getHandlers();

        assertNotNull( handlers );
        assertTrue( handlers.isEmpty() );
    }

    /**
     * Ensures the {@link LoggerConfiguration#getHandlers} method returns an
     * empty collection when the logger is not configured.
     */
    @Test
    public void testGetHandlers_NotConfigured()
    {
        final LoggerConfiguration config = new LoggerConfiguration( "logger.unknown", getProperties() ); //$NON-NLS-1$

        final List<Handler> handlers = config.getHandlers();

        assertNotNull( handlers );
        assertTrue( handlers.isEmpty() );
    }

    /**
     * Ensures the {@link LoggerConfiguration#getLevel} method returns the
     * expected value when the logger is configured.
     */
    @Test
    public void testGetLevel_Configured()
    {
        assertEquals( Level.SEVERE, getLoggerConfiguration().getLevel( null ) );
    }

    /**
     * Ensures the {@link LoggerConfiguration#getLevel} method returns the
     * default value when the logger is not configured.
     */
    @Test
    public void testGetLevel_NotConfigured()
    {
        final LoggerConfiguration config = new LoggerConfiguration( "logger.unknown", getProperties() ); //$NON-NLS-1$

        assertEquals( null, config.getLevel( null ) );
    }

    /**
     * Ensures the {@link LoggerConfiguration#getUseParentHandlers} method
     * returns the expected value when the logger is configured.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetUseParentHandlers_Configured()
    {
        assertEquals( true, getLoggerConfiguration().getUseParentHandlers( false ) );
    }

    /**
     * Ensures the {@link LoggerConfiguration#getUseParentHandlers} method
     * returns the default value when the logger is not configured.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetUseParentHandlers_NotConfigured()
    {
        final LoggerConfiguration config = new LoggerConfiguration( "logger.unknown", getProperties() ); //$NON-NLS-1$

        assertEquals( false, config.getUseParentHandlers( false ) );
    }
}
