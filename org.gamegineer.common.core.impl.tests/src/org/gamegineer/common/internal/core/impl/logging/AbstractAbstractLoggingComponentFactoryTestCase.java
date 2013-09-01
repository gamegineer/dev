/*
 * AbstractAbstractLoggingComponentFactoryTestCase.java
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
 * Created on May 22, 2008 at 12:41:18 AM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import static org.junit.Assert.assertNotNull;
import java.util.Collections;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.common.internal.core.impl.logging.AbstractLoggingComponentFactory}
 * class.
 * 
 * @param <F>
 *        The type of the logging component factory under test.
 * @param <T>
 *        The type of the logging component; may be an abstract type.
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

    /** A logging component for use in the fixture. */
    private T component_;

    /** The logging component factory under test in the fixture. */
    private F factory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAbstractLoggingComponentFactoryTestCase} class.
     */
    protected AbstractAbstractLoggingComponentFactoryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the logging component factory to be tested.
     * 
     * @return The logging component factory to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract F createLoggingComponentFactory()
        throws Exception;

    /**
     * Gets the logging component for use in the fixture.
     * 
     * @return The logging component for use in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final T getLoggingComponent()
    {
        assertNotNull( component_ );
        return component_;
    }

    /**
     * Gets the logging component factory under test in the fixture.
     * 
     * @return The logging component factory under test in the fixture; never
     *         {@code null}.
     */
    /* @NonNull */
    protected final F getLoggingComponentFactory()
    {
        assertNotNull( factory_ );
        return factory_;
    }

    /**
     * Gets the logging component type created by the factory.
     * 
     * @return The logging component type created by the factory; never
     *         {@code null}.
     */
    /* @NonNull */
    protected abstract Class<? extends T> getLoggingComponentType();

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
        factory_ = createLoggingComponentFactory();
        assertNotNull( factory_ );
        component_ = factory_.createLoggingComponent( getLoggingComponentType().getName() );
        assertNotNull( component_ );
    }

    /**
     * Ensures the
     * {@link AbstractLoggingComponentFactory#configureLoggingComponent} method
     * throws an exception when passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testConfigureLoggingComponent_Component_Null()
    {
        factory_.configureLoggingComponent( null, DEFAULT_INSTANCE_NAME, DEFAULT_LOGGING_PROPERTIES );
    }

    /**
     * Ensures the
     * {@link AbstractLoggingComponentFactory#configureLoggingComponent} method
     * throws an exception when passed a {@code null} instance name.
     */
    @Test( expected = NullPointerException.class )
    public void testConfigureLoggingComponent_InstanceName_Null()
    {
        factory_.configureLoggingComponent( component_, null, DEFAULT_LOGGING_PROPERTIES );
    }

    /**
     * Ensures the
     * {@link AbstractLoggingComponentFactory#createLoggingComponent} method
     * throws an exception when passed a {@code null} type name.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateLoggingComponent_TypeName_Null()
    {
        factory_.createLoggingComponent( null );
    }
}
