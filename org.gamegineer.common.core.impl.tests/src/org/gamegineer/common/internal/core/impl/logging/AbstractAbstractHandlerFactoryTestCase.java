/*
 * AbstractAbstractHandlerFactoryTestCase.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on May 23, 2008 at 10:05:22 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import org.gamegineer.common.core.logging.LoggingServiceConstants;
import org.junit.Test;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentFactory;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.common.internal.core.impl.logging.AbstractHandlerFactory}
 * class.
 * 
 * @param <F>
 *        The type of the handler factory under test.
 * @param <T>
 *        The type of the handler.
 */
public abstract class AbstractAbstractHandlerFactoryTestCase<F extends AbstractHandlerFactory<T>, T extends Handler>
    extends AbstractAbstractLoggingComponentFactoryTestCase<F, T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default instance name for the fixture. */
    private static final String DEFAULT_INSTANCE_NAME = "instanceName"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes an instance of the
     * {@code AbstractAbstractHandlerFactoryTestCase} class.
     */
    protected AbstractAbstractHandlerFactoryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the map key for the specified logging property.
     * 
     * @param propertyName
     *        The name of the logging property; must not be {@code null}.
     * 
     * @return The map key for the specified logging property; never
     *         {@code null}.
     */
    /* @NonNull */
    private String getLoggingPropertyKey(
        /* @NonNull */
        final String propertyName )
    {
        assert propertyName != null;

        return String.format( "%1$s.%2$s.%3$s", getLoggingComponentType().getName(), DEFAULT_INSTANCE_NAME, propertyName ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link AbstractHandlerFactory#configureLoggingComponent}
     * method leaves the handler encoding unchanged when the value is illegal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConfigureLoggingComponent_Encoding_Illegal()
        throws Exception
    {
        final String key = getLoggingPropertyKey( LoggingServiceConstants.PROPERTY_HANDLER_ENCODING );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, "WTF?" ); //$NON-NLS-1$
        final T handler = getLoggingComponent();
        handler.setEncoding( null );

        getLoggingComponentFactory().configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );

        assertNull( handler.getEncoding() );
    }

    /**
     * Ensures the {@link AbstractHandlerFactory#configureLoggingComponent}
     * method correctly configures the handler encoding when the value is legal.
     */
    @Test
    public void testConfigureLoggingComponent_Encoding_Legal()
    {
        final String value = "US-ASCII"; //$NON-NLS-1$
        final String key = getLoggingPropertyKey( LoggingServiceConstants.PROPERTY_HANDLER_ENCODING );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, value );
        final T handler = getLoggingComponent();

        getLoggingComponentFactory().configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );

        assertEquals( value, handler.getEncoding() );
    }

    /**
     * Ensures the {@link AbstractHandlerFactory#configureLoggingComponent}
     * method leaves the handler filter unchanged when the value is illegal.
     */
    @Test
    public void testConfigureLoggingComponent_Filter_Illegal()
    {
        final String key = getLoggingPropertyKey( LoggingServiceConstants.PROPERTY_HANDLER_FILTER );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, "WTF?" ); //$NON-NLS-1$
        final T handler = getLoggingComponent();
        final Filter value = new FakeFilter();
        handler.setFilter( value );

        getLoggingComponentFactory().configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );

        assertEquals( value, handler.getFilter() );
    }

    /**
     * Ensures the {@link AbstractHandlerFactory#configureLoggingComponent}
     * method correctly configures the handler filter when the value is legal.
     */
    @Test
    public void testConfigureLoggingComponent_Filter_Legal()
    {
        final ServiceRegistration<ComponentFactory> serviceRegistration = FakeFilter.registerComponentFactory();
        try
        {
            final String value = String.format( "%1$s.filterName", FakeFilter.class.getName() ); //$NON-NLS-1$
            final String key = getLoggingPropertyKey( LoggingServiceConstants.PROPERTY_HANDLER_FILTER );
            final Map<String, String> loggingProperties = Collections.singletonMap( key, value );
            final T handler = getLoggingComponent();

            getLoggingComponentFactory().configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );

            assertTrue( handler.getFilter() instanceof FakeFilter );
        }
        finally
        {
            serviceRegistration.unregister();
        }
    }

    /**
     * Ensures the {@link AbstractHandlerFactory#configureLoggingComponent}
     * method leaves the handler formatter unchanged when the value is illegal.
     */
    @Test
    public void testConfigureLoggingComponent_Formatter_Illegal()
    {
        final String key = getLoggingPropertyKey( LoggingServiceConstants.PROPERTY_HANDLER_FORMATTER );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, "WTF?" ); //$NON-NLS-1$
        final T handler = getLoggingComponent();
        final Formatter value = new FakeFormatter();
        handler.setFormatter( value );

        getLoggingComponentFactory().configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );

        assertEquals( value, handler.getFormatter() );
    }

    /**
     * Ensures the {@link AbstractHandlerFactory#configureLoggingComponent}
     * method correctly configures the handler formatter when the value is
     * legal.
     */
    @Test
    public void testConfigureLoggingComponent_Formatter_Legal()
    {
        final String value = String.format( "%1$s.formatterName", FakeFormatter.class.getName() ); //$NON-NLS-1$
        final String key = getLoggingPropertyKey( LoggingServiceConstants.PROPERTY_HANDLER_FORMATTER );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, value );
        final T handler = getLoggingComponent();

        getLoggingComponentFactory().configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );

        assertTrue( handler.getFormatter() instanceof FakeFormatter );
    }

    /**
     * Ensures the {@link AbstractHandlerFactory#configureLoggingComponent}
     * method leaves the handler level unchanged when the value is illegal.
     */
    @Test
    public void testConfigureLoggingComponent_Level_Illegal()
    {
        final String key = getLoggingPropertyKey( LoggingServiceConstants.PROPERTY_HANDLER_LEVEL );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, "WTF?" ); //$NON-NLS-1$
        final T handler = getLoggingComponent();
        final Level value = Level.ALL;
        handler.setLevel( value );

        getLoggingComponentFactory().configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );

        assertEquals( value, handler.getLevel() );
    }

    /**
     * Ensures the {@link AbstractHandlerFactory#configureLoggingComponent}
     * method correctly configures the handler level when the value is legal.
     */
    @Test
    public void testConfigureLoggingComponent_Level_Legal()
    {
        final Level value = Level.SEVERE;
        final String key = getLoggingPropertyKey( LoggingServiceConstants.PROPERTY_HANDLER_LEVEL );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, value.getName() );
        final T handler = getLoggingComponent();

        getLoggingComponentFactory().configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );

        assertEquals( value, handler.getLevel() );
    }
}
