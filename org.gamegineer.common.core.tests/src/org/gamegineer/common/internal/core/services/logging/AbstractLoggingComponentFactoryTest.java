/*
 * AbstractLoggingComponentFactoryTest.java
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
 * Created on May 21, 2008 at 11:15:31 PM.
 */

package org.gamegineer.common.internal.core.services.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.Map;
import org.gamegineer.common.core.services.component.ComponentCreationContextBuilder;
import org.gamegineer.common.core.services.component.IComponentCreationContext;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.internal.core.Activator;
import org.gamegineer.common.internal.core.services.logging.attributes.InstanceNameAttribute;
import org.gamegineer.common.internal.core.services.logging.attributes.LoggingPropertiesAttribute;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.ServiceRegistration;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.services.logging.AbstractLoggingComponentFactory}
 * class.
 */
public final class AbstractLoggingComponentFactoryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default component class name to use in the creation context. */
    private static final String DEFAULT_CLASS_NAME = MockLoggingComponent.class.getName();

    /** The default component instance name to use in the creation context. */
    private static final String DEFAULT_INSTANCE_NAME = "instanceName"; //$NON-NLS-1$

    /** The default logging properties to use in the creation context. */
    private static final Map<String, String> DEFAULT_LOGGING_PROPERTIES = Collections.emptyMap();

    /** The logging component factory under test in the fixture. */
    private AbstractLoggingComponentFactory<MockLoggingComponent> m_factory;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractLoggingComponentFactoryTest} class.
     */
    public AbstractLoggingComponentFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a component creation context suitable for a logging component
     * factory.
     * 
     * @param addClassName
     *        {@code true} to add the {@code className} attribute to the
     *        context; otherwise {@code false}.
     * @param className
     *        The component class name; must not be {@code null} if
     *        {@code addClassName} is {@code true}.
     * @param addInstanceName
     *        {@code true} to add the {@code instanceName} attribute to the
     *        context; otherwise {@code false}.
     * @param instanceName
     *        The component instance name; must not be {@code null} if
     *        {@code addInstanceName} is {@code true}.
     * @param addLoggingProperties
     *        {@code true} to add the {@code loggingProperties} attribute to the
     *        context; otherwise {@code false}.
     * @param loggingProperties
     *        The logging properties; must not be {@code null} if
     *        {@code addLoggingProperties} is {@code true}.
     * 
     * @return A component creation context; never {@code null}.
     */
    /* @NonNull */
    private static IComponentCreationContext createComponentCreationContext(
        final boolean addClassName,
        /* @Nullable */
        final String className,
        final boolean addInstanceName,
        /* @Nullable */
        final String instanceName,
        final boolean addLoggingProperties,
        /* @Nullable */
        final Map<String, String> loggingProperties )
    {
        assert !addClassName || (className != null);
        assert !addInstanceName || (instanceName != null);
        assert !addLoggingProperties || (loggingProperties != null);

        final ComponentCreationContextBuilder builder = new ComponentCreationContextBuilder();
        if( addClassName )
        {
            builder.setAttribute( ClassNameAttribute.INSTANCE.getName(), className );
        }
        if( addInstanceName )
        {
            builder.setAttribute( InstanceNameAttribute.INSTANCE.getName(), instanceName );
        }
        if( addLoggingProperties )
        {
            builder.setAttribute( LoggingPropertiesAttribute.INSTANCE.getName(), loggingProperties );
        }
        return builder.toComponentCreationContext();
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
        m_factory = new AbstractLoggingComponentFactory<MockLoggingComponent>( MockLoggingComponent.class )
        {
            // no overrides
        };
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
        m_factory = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * component type.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Type_Null()
    {
        new AbstractLoggingComponentFactory<MockLoggingComponent>( null )
        {
            // no overrides
        };
    }

    /**
     * Ensures the {@code createComponent} method throws an exception if the
     * class name attribute in the creation context is absent.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponent_Context_ClassNameAttribute_Absent()
        throws Exception
    {
        m_factory.createComponent( createComponentCreationContext( false, null, true, DEFAULT_INSTANCE_NAME, true, DEFAULT_LOGGING_PROPERTIES ) );
    }

    /**
     * Ensures the {@code createComponent} method throws an exception if the
     * class name attribute in the creation context specifies the wrong
     * component type supported by the factory.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponent_Context_ClassNameAttribute_Present_WrongType()
        throws Exception
    {
        m_factory.createComponent( createComponentCreationContext( true, Integer.class.getName(), true, DEFAULT_INSTANCE_NAME, true, DEFAULT_LOGGING_PROPERTIES ) );
    }

    /**
     * Ensures the {@code createComponent} method throws an exception if the
     * instance name attribute in the creation context is absent.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponent_Context_InstanceNameAttribute_Absent()
        throws Exception
    {
        m_factory.createComponent( createComponentCreationContext( true, DEFAULT_CLASS_NAME, false, null, true, DEFAULT_LOGGING_PROPERTIES ) );
    }

    /**
     * Ensures the {@code createComponent} method does not throw an exception if
     * the logging properties attribute in the creation context is absent.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateComponent_Context_LoggingPropertiesAttribute_Absent()
        throws Exception
    {
        m_factory.createComponent( createComponentCreationContext( true, DEFAULT_CLASS_NAME, true, DEFAULT_INSTANCE_NAME, false, null ) );
    }

    /**
     * Ensures the {@code createNamedLoggingComponent} method creates the
     * expected object.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateNamedLoggingComponent()
        throws Exception
    {
        ServiceRegistration serviceRegistration = null;
        try
        {
            serviceRegistration = Activator.getDefault().getBundleContext().registerService( IComponentFactory.class.getName(), MockLoggingComponent.FACTORY, null );

            final String name = String.format( "%1$s.name", MockLoggingComponent.class.getName() ); //$NON-NLS-1$
            final Object component = AbstractLoggingComponentFactory.createNamedLoggingComponent( name, null );
            assertTrue( component instanceof MockLoggingComponent );
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
     * Ensures the {@code createNamedLoggingComponent} method does not throw an
     * exception when passed a {@code null} logging properties object.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateNamedLoggingComponent_LoggingProperties_Null()
        throws Exception
    {
        ServiceRegistration serviceRegistration = null;
        try
        {
            serviceRegistration = Activator.getDefault().getBundleContext().registerService( IComponentFactory.class.getName(), MockLoggingComponent.FACTORY, null );

            final String name = String.format( "%1$s.name", MockLoggingComponent.class.getName() ); //$NON-NLS-1$
            AbstractLoggingComponentFactory.createNamedLoggingComponent( name, null );
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
     * Ensures the {@code createNamedLoggingComponent} method throws an
     * exception when passed a name that does not contain at least one dot.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateNamedLoggingComponent_Name_Illegal()
        throws Exception
    {
        AbstractLoggingComponentFactory.createNamedLoggingComponent( "name", Collections.<String, String>emptyMap() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createNamedLoggingComponent} method throws an
     * exception when passed a {@code null} name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateNamedLoggingComponent_Name_Null()
        throws Exception
    {
        AbstractLoggingComponentFactory.createNamedLoggingComponent( null, Collections.<String, String>emptyMap() );
    }

    /**
     * Ensures the {@code getLoggingProperty} method throws an exception when
     * passed a {@code null} instance name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetLoggingProperty_InstanceName_Null()
    {
        m_factory.getLoggingProperty( null, "name", DEFAULT_LOGGING_PROPERTIES ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getLoggingProperty} method throws an exception when
     * passed a {@code null} logging properties object.
     */
    @Test( expected = NullPointerException.class )
    public void testGetLoggingProperty_LoggingProperties_Null()
    {
        m_factory.getLoggingProperty( DEFAULT_INSTANCE_NAME, "name", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getLoggingProperty} method returns {@code null} when
     * the logging property is absent.
     */
    @Test
    public void testGetLoggingProperty_Property_Absent()
    {
        assertNull( m_factory.getLoggingProperty( DEFAULT_INSTANCE_NAME, "name", DEFAULT_LOGGING_PROPERTIES ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getLoggingProperty} method returns the expected value
     * when the logging property is present.
     */
    @Test
    public void testGetLoggingProperty_Property_Present()
    {
        final String name = "name"; //$NON-NLS-1$
        final String value = "value"; //$NON-NLS-1$
        final String key = String.format( "%1$s.%2$s.%3$s", DEFAULT_CLASS_NAME, DEFAULT_INSTANCE_NAME, name ); //$NON-NLS-1$
        final Map<String, String> loggingProperties = Collections.singletonMap( key, value );
        assertEquals( value, m_factory.getLoggingProperty( DEFAULT_INSTANCE_NAME, name, loggingProperties ) );
    }

    /**
     * Ensures the {@code getLoggingProperty} method throws an exception when
     * passed a {@code null} property name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetLoggingProperty_PropertyName_Null()
    {
        m_factory.getLoggingProperty( DEFAULT_INSTANCE_NAME, null, DEFAULT_LOGGING_PROPERTIES );
    }
}
