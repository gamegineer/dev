/*
 * AbstractAbstractLoggingComponentFactoryTestCase.java
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
 * Created on May 22, 2008 at 12:41:18 AM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import static org.junit.Assert.assertNotNull;
import java.util.Optional;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link AbstractLoggingComponentFactory} class.
 * 
 * @param <F>
 *        The type of the logging component factory under test.
 * @param <T>
 *        The type of the logging component; may be an abstract type.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public abstract class AbstractAbstractLoggingComponentFactoryTestCase<F extends AbstractLoggingComponentFactory<T>, @NonNull T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A logging component for use in the fixture. */
    private Optional<T> component_;

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
        component_ = Optional.empty();
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
    protected abstract F createLoggingComponentFactory()
        throws Exception;

    /**
     * Gets the logging component for use in the fixture.
     * 
     * @return The logging component for use in the fixture; never {@code null}.
     */
    protected final T getLoggingComponent()
    {
        return component_.get();
    }

    /**
     * Gets the logging component factory under test in the fixture.
     * 
     * @return The logging component factory under test in the fixture; never
     *         {@code null}.
     */
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
        component_ = Optional.of( factory_.createLoggingComponent( getLoggingComponentType().getName() ) );
    }

    /**
     * Placeholder for future interface tests.
     */
    @Test
    public void testDummy()
    {
        // do nothing
    }
}
