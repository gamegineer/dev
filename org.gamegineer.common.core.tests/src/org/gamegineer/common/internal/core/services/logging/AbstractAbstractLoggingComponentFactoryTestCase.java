/*
 * AbstractAbstractLoggingComponentFactoryTestCase.java
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
 * Created on May 22, 2008 at 12:41:18 AM.
 */

package org.gamegineer.common.internal.core.services.logging;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.Map;
import org.gamegineer.common.core.services.component.ComponentCreationException;
import org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.common.internal.core.services.logging.AbstractLoggingComponentFactory}
 * class.
 * 
 * @param <F>
 *        The type of the logging component factory under test.
 * @param <T>
 *        The type of the logging component.
 */
public abstract class AbstractAbstractLoggingComponentFactoryTestCase<F extends AbstractLoggingComponentFactory<T>, T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default instance name for logging components in the fixture. */
    private static final String DEFAULT_INSTANCE_NAME = "instanceName"; //$NON-NLS-1$

    /** The default logging properties for logging components in the fixture. */
    private static final Map<String, String> DEFAULT_LOGGING_PROPERTIES = Collections.emptyMap();

    /** The logging component factory under test in the fixture. */
    private F m_factory;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAbstractLoggingComponentFactoryTestCase} class.
     */
    protected AbstractAbstractLoggingComponentFactoryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Configures an instance of the logging component associated with the
     * factory.
     * 
     * <p>
     * This method is provided simply to allow subclasses in other packages to
     * access the protected
     * {@link AbstractLoggingComponentFactory#configureLoggingComponent(Object, String, Map)}
     * method.
     * </p>
     * 
     * @param component
     *        The logging component; must not be {@code null}.
     * @param instanceName
     *        The instance name of the logging component; must not be
     *        {@code null}. This name is used to discover the component's
     *        properties in the specified logging properties.
     * @param loggingProperties
     *        The logging properties; may be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code component} or {@code instanceName} is {@code null}.
     */
    protected final void configureLoggingComponent(
        /* @NonNull */
        final T component,
        /* @NonNull */
        final String instanceName,
        /* @Nullable */
        final Map<String, String> loggingProperties )
    {
        m_factory.configureLoggingComponent( component, instanceName, loggingProperties );
    }

    /**
     * Creates a new instance of the logging component associated with the
     * factory.
     * 
     * <p>
     * This method is provided simply to allow subclasses in other packages to
     * access the protected
     * {@link AbstractLoggingComponentFactory#createLoggingComponent(String, Map)}
     * method.
     * </p>
     * 
     * @param instanceName
     *        The instance name of the logging component; must not be
     *        {@code null}. This name is used to discover the component's
     *        properties in the specified logging properties.
     * @param loggingProperties
     *        The logging properties; may be {@code null}.
     * 
     * @return A new instance of the logging component; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code instanceName} is {@code null}.
     * @throws
     *         org.gamegineer.common.core.services.component.ComponentCreationException
     *         If an error occurred during component creation.
     */
    /* @NonNull */
    protected final T createLoggingComponent(
        /* @NonNull */
        final String instanceName,
        /* @Nullable */
        final Map<String, String> loggingProperties )
        throws ComponentCreationException
    {
        return m_factory.createLoggingComponent( instanceName, loggingProperties );
    }

    /**
     * Creates the logging component factory to be tested.
     * 
     * @return The logging component factory to be tested; never {@code null}.
     */
    /* @NonNull */
    protected abstract F createLoggingComponentFactory();

    /**
     * Gets the logging component factory under test in the fixture.
     * 
     * @return The logging component factory under test in the fixture; never
     *         {@code null}.
     */
    /* @NonNull */
    protected final F getLoggingComponentFactory()
    {
        assertNotNull( m_factory );
        return m_factory;
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
        m_factory = createLoggingComponentFactory();
        assertNotNull( m_factory );
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
     * Ensures the {@code configureLoggingComponent} method throws an exception
     * when passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testConfigureLoggingComponent_Component_Null()
    {
        m_factory.configureLoggingComponent( null, DEFAULT_INSTANCE_NAME, DEFAULT_LOGGING_PROPERTIES );
    }

    /**
     * Ensures the {@code configureLoggingComponent} method throws an exception
     * when passed a {@code null} instance name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testConfigureLoggingComponent_InstanceName_Null()
        throws Exception
    {
        final T component = m_factory.createLoggingComponent( DEFAULT_INSTANCE_NAME, DEFAULT_LOGGING_PROPERTIES );
        m_factory.configureLoggingComponent( component, null, DEFAULT_LOGGING_PROPERTIES );
    }

    /**
     * Ensures the {@code createLoggingComponent} method throws an exception
     * when passed a {@code null} instance name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateLoggingComponent_InstanceName_Null()
        throws Exception
    {
        m_factory.createLoggingComponent( null, DEFAULT_LOGGING_PROPERTIES );
    }

    /**
     * Ensures the {@code createLoggingComponent} method successfully creates
     * the logging component when passed an empty collection of logging
     * properties (because they are optional).
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateLoggingComponent_LoggingProperties_Empty()
        throws Exception
    {
        assertNotNull( m_factory.createLoggingComponent( DEFAULT_INSTANCE_NAME, DEFAULT_LOGGING_PROPERTIES ) );
    }

    /**
     * Ensures the {@code createLoggingComponent} method successfully creates
     * the logging component when passed a {@code null} collection of logging
     * properties (because they are optional).
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateLoggingComponent_LoggingProperties_Null()
        throws Exception
    {
        assertNotNull( m_factory.createLoggingComponent( DEFAULT_INSTANCE_NAME, null ) );
    }

    /**
     * Ensures the {@code createLoggingComponent} method does not return
     * {@code null} when passed legal arguments.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateLoggingComponent_ReturnValue_NonNull()
        throws Exception
    {
        final T component = m_factory.createLoggingComponent( DEFAULT_INSTANCE_NAME, DEFAULT_LOGGING_PROPERTIES );
        assertNotNull( component );
    }

    /**
     * Ensures the {@code getAttribute} method returns the expected value for
     * the supported class names attribute.
     */
    @Test
    public void testGetAttribute_SupportedClassNames()
    {
        boolean foundClassName = false;
        for( final String className : SupportedClassNamesAttribute.INSTANCE.getValue( m_factory ) )
        {
            if( className.equals( m_factory.getType().getName() ) )
            {
                foundClassName = true;
                break;
            }
        }
        assertTrue( foundClassName );
    }
}
