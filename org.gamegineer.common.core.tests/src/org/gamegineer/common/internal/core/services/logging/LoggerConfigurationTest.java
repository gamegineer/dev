/*
 * LoggerConfigurationTest.java
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
 * Created on May 15, 2008 at 11:22:40 PM.
 */

package org.gamegineer.common.internal.core.services.logging;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.gamegineer.common.core.services.component.IComponentCreationContext;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.common.core.services.component.MockComponentFactory;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute;
import org.gamegineer.common.core.services.component.util.attribute.ComponentCreationContextAttributeAccessor;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor;
import org.gamegineer.common.internal.core.Activator;
import org.gamegineer.common.internal.core.services.logging.attributes.InstanceNameAttribute;
import org.gamegineer.common.internal.core.services.logging.attributes.LoggingPropertiesAttribute;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.ServiceRegistration;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.services.logging.LoggerConfiguration}
 * class.
 */
public final class LoggerConfigurationTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The class name of the filter used in the fixture. */
    private static final String FILTER_CLASS_NAME = MockFilter.class.getName();

    /** The instance name of the filter used in the fixture. */
    private static final String FILTER_INSTANCE_NAME = "filterName"; //$NON-NLS-1$

    /** The class name of the handler used in the fixture. */
    private static final String HANDLER_CLASS_NAME = MockHandler.class.getName();

    /** The instance name of the first handler used in the fixture. */
    private static final String HANDLER_INSTANCE_NAME_1 = "handlerName1"; //$NON-NLS-1$

    /** The instance name of the second handler used in the fixture. */
    private static final String HANDLER_INSTANCE_NAME_2 = "handlerName2"; //$NON-NLS-1$

    /** The default logger configuration under test in the fixture. */
    private LoggerConfiguration m_config;

    /** The logging properties which contains the logger configuration. */
    private LoggingProperties m_props;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggerConfigurationTest} class.
     */
    public LoggerConfigurationTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        final Properties props = new Properties();

        // Logger with all properties configured
        props.put( "logger.default.filter", String.format( "%1$s.%2$s", FILTER_CLASS_NAME, FILTER_INSTANCE_NAME ) ); //$NON-NLS-1$ //$NON-NLS-2$
        props.put( "logger.default.handlers", String.format( "%1$s.%2$s, %1$s.%3$s", HANDLER_CLASS_NAME, HANDLER_INSTANCE_NAME_1, HANDLER_INSTANCE_NAME_2 ) ); //$NON-NLS-1$ //$NON-NLS-2$
        props.put( "logger.default.level", Level.SEVERE.getName() ); //$NON-NLS-1$
        props.put( "logger.default.useParentHandlers", "true" ); //$NON-NLS-1$ //$NON-NLS-2$

        // Logger with an illegal filter
        props.put( "logger.illegalFilter.filter", "A_NAME_WITHOUT_A_DOT" ); //$NON-NLS-1$ //$NON-NLS-2$

        // Logger with an illegal handler
        props.put( "logger.illegalHandler.handlers", String.format( "%1$s.%2$s, %3$s", HANDLER_CLASS_NAME, HANDLER_INSTANCE_NAME_1, "A_NAME_WITHOUT_A_DOT" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        // Logger with no filter
        props.put( "logger.noFilter.level", Level.INFO.getName() ); //$NON-NLS-1$

        // Logger with no handlers
        props.put( "logger.noHandlers.level", Level.INFO.getName() ); //$NON-NLS-1$

        // Filter
        props.put( String.format( "%1$s.%2$s.%3$s", FILTER_CLASS_NAME, FILTER_INSTANCE_NAME, MockFilterFactory.PROPERTY_MOCK_BOOLEAN_PROPERTY ), Boolean.toString( false ) ); //$NON-NLS-1$

        // Handler 1
        props.put( String.format( "%1$s.%2$s.%3$s", HANDLER_CLASS_NAME, HANDLER_INSTANCE_NAME_1, MockHandlerFactory.PROPERTY_MOCK_LEVEL_PROPERTY ), Level.SEVERE.getName() ); //$NON-NLS-1$

        // Handler 2
        props.put( String.format( "%1$s.%2$s.%3$s", HANDLER_CLASS_NAME, HANDLER_INSTANCE_NAME_2, MockHandlerFactory.PROPERTY_MOCK_LEVEL_PROPERTY ), Level.WARNING.getName() ); //$NON-NLS-1$

        m_props = new LoggingProperties( props );
        m_config = new LoggerConfiguration( m_props, "logger.default" ); //$NON-NLS-1$
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        m_config = null;
        m_props = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * logging properties object.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_LoggingProperties_Null()
    {
        new LoggerConfiguration( null, "z" ); //$NON-NLS-1$
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * name.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Name_Null()
    {
        new LoggerConfiguration( m_props, null );
    }

    /**
     * Ensures the {@code getFilter} method returns the expected value when the
     * logger is configured and the filter configuration is legal.
     */
    @Test
    public void testGetFilter_Configured()
    {
        ServiceRegistration serviceRegistration = null;
        try
        {
            serviceRegistration = Activator.getDefault().getBundleContext().registerService( IComponentFactory.class.getName(), new MockFilterFactory(), null );

            final MockFilter filter = (MockFilter)m_config.getFilter( null );
            assertNotNull( filter );
            assertEquals( FILTER_INSTANCE_NAME, filter.getName() );
            assertFalse( filter.getMockBooleanProperty() );
        }
        finally
        {
            if( serviceRegistration != null )
            {
                serviceRegistration.unregister();
            }
        }
    }

    /**
     * Ensures the {@code getFilter} method returns the default value when the
     * logger is configured but the filter could not be created because no
     * component factory is available.
     */
    @Test
    public void testGetFilter_Configured_FilterCreationFailed()
    {
        assertNull( m_config.getFilter( null ) );
    }

    /**
     * Ensures the {@code getFilter} method returns the default value when the
     * logger is configured but the filter property is illegal.
     */
    @Test
    public void testGetFilter_Configured_IllegalFilter()
    {
        final LoggerConfiguration config = new LoggerConfiguration( m_props, "logger.illegalFilter" ); //$NON-NLS-1$
        assertNull( config.getFilter( null ) );
    }

    /**
     * Ensures the {@code getFilter} method returns the default value when the
     * logger is configured but no filter is specified.
     */
    @Test
    public void testGetFilter_Configured_NoFilter()
    {
        final LoggerConfiguration config = new LoggerConfiguration( m_props, "logger.noFilter" ); //$NON-NLS-1$
        assertNull( config.getFilter( null ) );
    }

    /**
     * Ensures the {@code getFilter} method returns the default value when the
     * logger is not configured.
     */
    @Test
    public void testGetFilter_NotConfigured()
    {
        final LoggerConfiguration config = new LoggerConfiguration( m_props, "logger.unknown" ); //$NON-NLS-1$
        assertNull( config.getFilter( null ) );
    }

    /**
     * Ensures the {@code getHandlers} method returns the expected value when
     * the logger is configured.
     */
    @Test
    public void testGetHandlers_Configured()
    {
        ServiceRegistration serviceRegistration = null;
        try
        {
            serviceRegistration = Activator.getDefault().getBundleContext().registerService( IComponentFactory.class.getName(), new MockHandlerFactory(), null );

            final List<Handler> handlerList = m_config.getHandlers();
            assertEquals( 2, handlerList.size() );
            assertEquals( HANDLER_INSTANCE_NAME_1, ((MockHandler)handlerList.get( 0 )).getName() );
            assertEquals( Level.SEVERE, handlerList.get( 0 ).getLevel() );
            assertEquals( HANDLER_INSTANCE_NAME_2, ((MockHandler)handlerList.get( 1 )).getName() );
            assertEquals( Level.WARNING, handlerList.get( 1 ).getLevel() );
        }
        finally
        {
            if( serviceRegistration != null )
            {
                serviceRegistration.unregister();
            }
        }
    }

    /**
     * Ensures the {@code getHandlers} method returns an empty collection when
     * the logger is configured but the handlers could not be created because no
     * component factory is available.
     */
    @Test
    public void testGetHandlers_Configured_HandlerCreationFailed()
    {
        final List<Handler> handlerList = m_config.getHandlers();
        assertNotNull( handlerList );
        assertTrue( handlerList.isEmpty() );
    }

    /**
     * Ensures the {@code getHandlers} method does not include handlers that
     * have an illegal name when the logger is configured.
     */
    @Test
    public void testGetHandlers_Configured_IllegalHandler()
    {
        ServiceRegistration serviceRegistration = null;
        try
        {
            serviceRegistration = Activator.getDefault().getBundleContext().registerService( IComponentFactory.class.getName(), new MockHandlerFactory(), null );

            final LoggerConfiguration config = new LoggerConfiguration( m_props, "logger.illegalHandler" ); //$NON-NLS-1$
            final List<Handler> handlerList = config.getHandlers();
            assertEquals( 1, handlerList.size() );
            assertEquals( HANDLER_INSTANCE_NAME_1, ((MockHandler)handlerList.get( 0 )).getName() );
        }
        finally
        {
            if( serviceRegistration != null )
            {
                serviceRegistration.unregister();
            }
        }
    }

    /**
     * Ensures the {@code getHandlers} method returns an empty collection when
     * the logger is configured but no handlers are specified.
     */
    @Test
    public void testGetHandlers_Configured_NoHandlers()
    {
        final LoggerConfiguration config = new LoggerConfiguration( m_props, "logger.noHandlers" ); //$NON-NLS-1$
        final List<Handler> handlerList = config.getHandlers();
        assertNotNull( handlerList );
        assertTrue( handlerList.isEmpty() );
    }

    /**
     * Ensures the {@code getHandlers} method returns an empty collection when
     * the logger is not configured.
     */
    @Test
    public void testGetHandlers_NotConfigured()
    {
        final LoggerConfiguration config = new LoggerConfiguration( m_props, "logger.unknown" ); //$NON-NLS-1$
        final List<Handler> handlerList = config.getHandlers();
        assertNotNull( handlerList );
        assertTrue( handlerList.isEmpty() );
    }

    /**
     * Ensures the {@code getLevel} method returns the expected value when the
     * logger is configured.
     */
    @Test
    public void testGetLevel_Configured()
    {
        assertEquals( Level.SEVERE, m_config.getLevel( null ) );
    }

    /**
     * Ensures the {@code getLevel} method returns the default value when the
     * logger is not configured.
     */
    @Test
    public void testGetLevel_NotConfigured()
    {
        final LoggerConfiguration config = new LoggerConfiguration( m_props, "logger.unknown" ); //$NON-NLS-1$
        assertEquals( null, config.getLevel( null ) );
    }

    /**
     * Ensures the {@code getUseParentHandlers} method returns the expected
     * value when the logger is configured.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetUseParentHandlers_Configured()
    {
        assertEquals( true, m_config.getUseParentHandlers( false ) );
    }

    /**
     * Ensures the {@code getUseParentHandlers} method returns the default value
     * when the logger is not configured.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetUseParentHandlers_NotConfigured()
    {
        final LoggerConfiguration config = new LoggerConfiguration( m_props, "logger.unknown" ); //$NON-NLS-1$
        assertEquals( false, config.getUseParentHandlers( false ) );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Mock implementation of {@link java.util.logging.Filter}.
     * 
     * <p>
     * This filter supports the following mock properties:
     * </p>
     * 
     * <ul>
     * <li>{@code mockBooleanProperty} specifies a {@code Boolean} value
     * (defaults to {@code true}).</li>
     * </ul>
     */
    private static final class MockFilter
        implements Filter
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The mock Boolean property. */
        private boolean m_mockBooleanProperty;

        /** The filter name. */
        private final String m_name;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockFilter} class.
         * 
         * @param name
         *        The filter name; must not be {@code null}.
         */
        MockFilter(
            /* @NonNull */
            final String name )
        {
            assert name != null;

            m_name = name;
            m_mockBooleanProperty = true;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the value of the mock Boolean property.
         * 
         * @return The value of the mock Boolean property.
         */
        boolean getMockBooleanProperty()
        {
            return m_mockBooleanProperty;
        }

        /**
         * Gets the name of the filter.
         * 
         * @return The name of the filter; never {@code null}.
         */
        /* @NonNull */
        String getName()
        {
            return m_name;
        }

        /*
         * @see java.util.logging.Filter#isLoggable(java.util.logging.LogRecord)
         */
        public boolean isLoggable(
            @SuppressWarnings( "unused" )
            final LogRecord record )
        {
            return true;
        }

        /**
         * Sets the value of the mock Boolean property.
         * 
         * @param mockBooleanProperty
         *        The value of the mock Boolean property.
         */
        void setMockBooleanProperty(
            final boolean mockBooleanProperty )
        {
            m_mockBooleanProperty = mockBooleanProperty;
        }
    }

    /**
     * Component factory for creating instances of {@code MockFilter}.
     */
    private static final class MockFilterFactory
        extends MockComponentFactory
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The name of the mock Boolean property. */
        static final String PROPERTY_MOCK_BOOLEAN_PROPERTY = "mockBooleanProperty"; //$NON-NLS-1$


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockFilterFactory} class.
         */
        MockFilterFactory()
        {
            SupportedClassNamesAttribute.INSTANCE.setValue( this, MockFilter.class.getName() );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.common.core.services.component.IComponentFactory#createComponent(org.gamegineer.common.core.services.component.IComponentCreationContext)
         */
        @Override
        public Object createComponent(
            final IComponentCreationContext context )
        {
            assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

            final IAttributeAccessor accessor = new ComponentCreationContextAttributeAccessor( context );
            final String className = ClassNameAttribute.INSTANCE.getValue( accessor );
            final String instanceName = InstanceNameAttribute.INSTANCE.getValue( accessor );
            final Map<String, String> loggingProperties = LoggingPropertiesAttribute.INSTANCE.getValue( accessor );//(Map<String, String>)context.getAttribute( "loggingProperties" );

            if( !MockFilter.class.getName().equals( className ) )
            {
                throw new IllegalArgumentException( "the requested class name is not supported" ); //$NON-NLS-1$
            }

            final MockFilter filter = new MockFilter( instanceName );

            if( loggingProperties != null )
            {
                final String mockBooleanPropertyName = String.format( "%1$s.%2$s.%3$s", className, instanceName, PROPERTY_MOCK_BOOLEAN_PROPERTY ); //$NON-NLS-1$
                if( loggingProperties.containsKey( mockBooleanPropertyName ) )
                {
                    filter.setMockBooleanProperty( Boolean.parseBoolean( loggingProperties.get( mockBooleanPropertyName ) ) );
                }
            }

            return filter;
        }
    }

    /**
     * Mock implementation of {@link java.util.logging.Handler}.
     * 
     * <p>
     * This handler supports the following mock properties:
     * </p>
     * 
     * <ul>
     * <li>{@code mockBooleanProperty} specifies a {@code Boolean} value
     * (defaults to {@code true}).</li>
     * </ul>
     */
    private static final class MockHandler
        extends Handler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The handler name. */
        private final String m_name;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockHandler} class.
         * 
         * @param name
         *        The handler name; must not be {@code null}.
         */
        MockHandler(
            /* @NonNull*/
            final String name )
        {
            assert name != null;

            m_name = name;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.util.logging.Handler#close()
         */
        @Override
        public void close()
            throws SecurityException
        {
            // Do nothing
        }

        /*
         * @see java.util.logging.Handler#flush()
         */
        @Override
        public void flush()
        {
            // Do nothing
        }

        /**
         * Gets the name of the handler.
         * 
         * @return The name of the handler; never {@code null}.
         */
        /* @NonNull */
        String getName()
        {
            return m_name;
        }

        /*
         * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
         */
        @Override
        public void publish(
            @SuppressWarnings( "unused" )
            final LogRecord record )
        {
            // Do nothing
        }
    }

    /**
     * Component factory for creating instances of {@code MockHandler}.
     */
    private static final class MockHandlerFactory
        extends MockComponentFactory
    {
        // ======================================================================
        // Fields
        // ======================================================================

        /** The name of the mock Level property. */
        static final String PROPERTY_MOCK_LEVEL_PROPERTY = "mockLevelProperty"; //$NON-NLS-1$


        // ======================================================================
        // Constructors
        // ======================================================================

        /**
         * Initializes a new instance of the {@code MockHandlerFactory} class.
         */
        MockHandlerFactory()
        {
            SupportedClassNamesAttribute.INSTANCE.setValue( this, MockHandler.class.getName() );
        }


        // ======================================================================
        // Methods
        // ======================================================================

        /*
         * @see org.gamegineer.common.core.services.component.IComponentFactory#createComponent(org.gamegineer.common.core.services.component.IComponentCreationContext)
         */
        @Override
        public Object createComponent(
            final IComponentCreationContext context )
        {
            assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

            final IAttributeAccessor accessor = new ComponentCreationContextAttributeAccessor( context );
            final String className = ClassNameAttribute.INSTANCE.getValue( accessor );
            final String instanceName = InstanceNameAttribute.INSTANCE.getValue( accessor );
            final Map<String, String> loggingProperties = LoggingPropertiesAttribute.INSTANCE.getValue( accessor );

            if( !MockHandler.class.getName().equals( className ) )
            {
                throw new IllegalArgumentException( "the requested class name is not supported" ); //$NON-NLS-1$
            }

            final MockHandler handler = new MockHandler( instanceName );

            if( loggingProperties != null )
            {
                final String levelName = String.format( "%1$s.%2$s.%3$s", className, instanceName, PROPERTY_MOCK_LEVEL_PROPERTY ); //$NON-NLS-1$
                if( loggingProperties.containsKey( levelName ) )
                {
                    handler.setLevel( Level.parse( loggingProperties.get( levelName ) ) );
                }
            }

            return handler;
        }
    }
}
