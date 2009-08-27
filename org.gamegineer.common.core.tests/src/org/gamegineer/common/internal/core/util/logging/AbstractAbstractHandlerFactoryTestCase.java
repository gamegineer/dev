/*
 * AbstractAbstractHandlerFactoryTestCase.java
 * Copyright 2008 Gamegineer.org
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

package org.gamegineer.common.internal.core.util.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.common.internal.core.Activator;
import org.gamegineer.common.internal.core.services.logging.AbstractAbstractLoggingComponentFactoryTestCase;
import org.junit.Test;
import org.osgi.framework.ServiceRegistration;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.common.internal.core.util.logging.AbstractHandlerFactory}
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
        super();
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

        return String.format( "%1$s.%2$s.%3$s", getLoggingComponentFactory().getType().getName(), DEFAULT_INSTANCE_NAME, propertyName ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code configureLoggingComponent} method leaves the handler
     * encoding unchanged when the value is illegal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConfigureLoggingComponent_Encoding_Illegal()
        throws Exception
    {
        final String key = getLoggingPropertyKey( AbstractHandlerFactory.PROPERTY_ENCODING );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, "WTF?" ); //$NON-NLS-1$
        final T handler = createLoggingComponent( DEFAULT_INSTANCE_NAME, loggingProperties );
        handler.setEncoding( null );
        configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );
        assertNull( handler.getEncoding() );
    }

    /**
     * Ensures the {@code configureLoggingComponent} method correctly configures
     * the handler encoding when the value is legal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConfigureLoggingComponent_Encoding_Legal()
        throws Exception
    {
        final String value = "US-ASCII"; //$NON-NLS-1$
        final String key = getLoggingPropertyKey( AbstractHandlerFactory.PROPERTY_ENCODING );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, value );
        final T handler = createLoggingComponent( DEFAULT_INSTANCE_NAME, loggingProperties );
        configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );
        assertEquals( value, handler.getEncoding() );
    }

    /**
     * Ensures the {@code configureLoggingComponent} method leaves the handler
     * filter unchanged when the value is illegal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConfigureLoggingComponent_Filter_Illegal()
        throws Exception
    {
        final String key = getLoggingPropertyKey( AbstractHandlerFactory.PROPERTY_FILTER );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, "WTF?" ); //$NON-NLS-1$
        final T handler = createLoggingComponent( DEFAULT_INSTANCE_NAME, loggingProperties );
        final Filter value = new MockFilter();
        handler.setFilter( value );
        configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );
        assertEquals( value, handler.getFilter() );
    }

    /**
     * Ensures the {@code configureLoggingComponent} method correctly configures
     * the handler filter when the value is legal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConfigureLoggingComponent_Filter_Legal()
        throws Exception
    {
        ServiceRegistration serviceRegistration = null;
        try
        {
            serviceRegistration = Activator.getDefault().getBundleContext().registerService( IComponentFactory.class.getName(), MockFilter.FACTORY, null );

            final String value = String.format( "%1$s.filterName", MockFilter.class.getName() ); //$NON-NLS-1$
            final String key = getLoggingPropertyKey( AbstractHandlerFactory.PROPERTY_FILTER );
            final Map<String, String> loggingProperties = Collections.singletonMap( key, value );
            final T handler = createLoggingComponent( DEFAULT_INSTANCE_NAME, loggingProperties );
            configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );
            assertTrue( handler.getFilter() instanceof MockFilter );
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
     * Ensures the {@code configureLoggingComponent} method leaves the handler
     * formatter unchanged when the value is illegal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConfigureLoggingComponent_Formatter_Illegal()
        throws Exception
    {
        final String key = getLoggingPropertyKey( AbstractHandlerFactory.PROPERTY_FORMATTER );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, "WTF?" ); //$NON-NLS-1$
        final T handler = createLoggingComponent( DEFAULT_INSTANCE_NAME, loggingProperties );
        final Formatter value = new MockFormatter();
        handler.setFormatter( value );
        configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );
        assertEquals( value, handler.getFormatter() );
    }

    /**
     * Ensures the {@code configureLoggingComponent} method correctly configures
     * the handler formatter when the value is legal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConfigureLoggingComponent_Formatter_Legal()
        throws Exception
    {
        ServiceRegistration serviceRegistration = null;
        try
        {
            serviceRegistration = Activator.getDefault().getBundleContext().registerService( IComponentFactory.class.getName(), MockFormatter.FACTORY, null );

            final String value = String.format( "%1$s.formatterName", MockFormatter.class.getName() ); //$NON-NLS-1$
            final String key = getLoggingPropertyKey( AbstractHandlerFactory.PROPERTY_FORMATTER );
            final Map<String, String> loggingProperties = Collections.singletonMap( key, value );
            final T handler = createLoggingComponent( DEFAULT_INSTANCE_NAME, loggingProperties );
            configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );
            assertTrue( handler.getFormatter() instanceof MockFormatter );
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
     * Ensures the {@code configureLoggingComponent} method leaves the handler
     * level unchanged when the value is illegal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConfigureLoggingComponent_Level_Illegal()
        throws Exception
    {
        final String key = getLoggingPropertyKey( AbstractHandlerFactory.PROPERTY_LEVEL );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, "WTF?" ); //$NON-NLS-1$
        final T handler = createLoggingComponent( DEFAULT_INSTANCE_NAME, loggingProperties );
        final Level value = Level.ALL;
        handler.setLevel( value );
        configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );
        assertEquals( value, handler.getLevel() );
    }

    /**
     * Ensures the {@code configureLoggingComponent} method correctly configures
     * the handler level when the value is legal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConfigureLoggingComponent_Level_Legal()
        throws Exception
    {
        final Level value = Level.SEVERE;
        final String key = getLoggingPropertyKey( AbstractHandlerFactory.PROPERTY_LEVEL );
        final Map<String, String> loggingProperties = Collections.singletonMap( key, value.getName() );
        final T handler = createLoggingComponent( DEFAULT_INSTANCE_NAME, loggingProperties );
        configureLoggingComponent( handler, DEFAULT_INSTANCE_NAME, loggingProperties );
        assertEquals( value, handler.getLevel() );
    }
}
