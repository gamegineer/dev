/*
 * AbstractLoggingComponentFactoryTest.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on May 21, 2008 at 11:15:31 PM.
 */

package org.gamegineer.common.internal.core.logging;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Filter;
import org.gamegineer.common.core.logging.LoggingServiceConstants;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentException;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.logging.AbstractLoggingComponentFactory}
 * class.
 */
public final class AbstractLoggingComponentFactoryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Component properties for use in the fixture. */
    private Dictionary<String, Object> componentProperties_;

    /** The logging component factory under test in the fixture. */
    private AbstractLoggingComponentFactory<Object> factory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractLoggingComponentFactoryTest} class.
     */
    public AbstractLoggingComponentFactoryTest()
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
        componentProperties_ = new Hashtable<String, Object>();
        componentProperties_.put( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_TYPE_NAME, Object.class.getName() );
        componentProperties_.put( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_INSTANCE_NAME, "instanceName" ); //$NON-NLS-1$
        componentProperties_.put( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_LOGGING_PROPERTIES, Collections.<String, String>emptyMap() );

        factory_ = new AbstractLoggingComponentFactory<Object>( Object.class )
        {
            // no overrides
        };
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * type.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Type_Null()
    {
        new AbstractLoggingComponentFactory<Object>( null )
        {
            // no overrides
        };
    }

    /**
     * Ensures the {@code createNamedLoggingComponent} method creates the
     * expected object.
     */
    @Test
    public void testCreateNamedLoggingComponent_HappyPath()
    {
        final ServiceRegistration serviceRegistration = FakeFilter.registerComponentFactory();
        try
        {
            final String name = String.format( "%1$s.name", FakeFilter.class.getName() ); //$NON-NLS-1$

            final Filter component = AbstractLoggingComponentFactory.createNamedLoggingComponent( Filter.class, name, null );

            assertNotNull( component );
            assertTrue( component instanceof FakeFilter );
        }
        finally
        {
            serviceRegistration.unregister();
        }
    }

    /**
     * Ensures the {@code createNamedLoggingComponent} method throws an
     * exception when passed a name that does not contain at least one dot.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateNamedLoggingComponent_Name_Illegal()
    {
        AbstractLoggingComponentFactory.createNamedLoggingComponent( Object.class, "name", Collections.<String, String>emptyMap() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code newInstance} method throws an exception when passed a
     * {@code null} component properties collection.
     */
    @Test( expected = ComponentException.class )
    public void testNewInstance_ComponentProperties_Null()
    {
        factory_.newInstance( null );
    }

    /**
     * Ensures the {@code newInstance} method throws an exception when the
     * instance name property is absent.
     */
    @Test( expected = ComponentException.class )
    public void testNewInstance_InstanceName_Absent()
    {
        componentProperties_.remove( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_INSTANCE_NAME );

        factory_.newInstance( componentProperties_ );
    }

    /**
     * Ensures the {@code newInstance} method throws an exception when the
     * instance name property is an illegal type.
     */
    @Test( expected = ComponentException.class )
    public void testNewInstance_InstanceName_IllegalType()
    {
        componentProperties_.put( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_INSTANCE_NAME, new Object() );

        factory_.newInstance( componentProperties_ );
    }

    /**
     * Ensures the {@code newInstance} method does not throw an exception when
     * the logging properties property is absent.
     */
    @Test
    public void testNewInstance_LoggingProperties_Absent()
    {
        componentProperties_.remove( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_LOGGING_PROPERTIES );

        assertNotNull( factory_.newInstance( componentProperties_ ) );
    }

    /**
     * Ensures the {@code newInstance} method throws an exception when the
     * logging properties property is an illegal type.
     */
    @Test( expected = ComponentException.class )
    public void testNewInstance_LoggingProperties_IllegalType()
    {
        componentProperties_.put( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_LOGGING_PROPERTIES, new Object() );

        factory_.newInstance( componentProperties_ );
    }

    /**
     * Ensures the {@code newInstance} method throws an exception when the type
     * name property is absent.
     */
    @Test( expected = ComponentException.class )
    public void testNewInstance_TypeName_Absent()
    {
        componentProperties_.remove( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_TYPE_NAME );

        factory_.newInstance( componentProperties_ );
    }

    /**
     * Ensures the {@code newInstance} method throws an exception when the type
     * name property is an illegal type.
     */
    @Test( expected = ComponentException.class )
    public void testNewInstance_TypeName_IllegalType()
    {
        componentProperties_.put( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_TYPE_NAME, new Object() );

        factory_.newInstance( componentProperties_ );
    }
}
