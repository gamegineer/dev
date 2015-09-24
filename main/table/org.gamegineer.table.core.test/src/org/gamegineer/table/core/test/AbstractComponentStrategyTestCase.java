/*
 * AbstractComponentStrategyTestCase.java
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
 * Created on Aug 1, 2012 at 7:55:13 PM.
 */

package org.gamegineer.table.core.test;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.IComponentStrategy;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IComponentStrategy} interface.
 * 
 * @param <ComponentStrategyType>
 *        The type of the component strategy.
 */
public abstract class AbstractComponentStrategyTestCase<ComponentStrategyType extends IComponentStrategy>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component strategy under test in the fixture. */
    private Optional<ComponentStrategyType> componentStrategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentStrategyTestCase} class.
     */
    protected AbstractComponentStrategyTestCase()
    {
        componentStrategy_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component strategy to be tested.
     * 
     * @return The component strategy to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ComponentStrategyType createComponentStrategy()
        throws Exception;

    /**
     * Creates a component orientation that is guaranteed to be illegal for all
     * components.
     * 
     * @return An illegal component orientation.
     */
    private static ComponentOrientation createIllegalOrientation()
    {
        return new ComponentOrientation( "illegal", 0 ) //$NON-NLS-1$
        {
            private static final long serialVersionUID = 1L;

            @Override
            public ComponentOrientation inverse()
            {
                return this;
            }
        };
    }

    /**
     * Gets the component strategy under test in the fixture.
     * 
     * @return The component strategy under test in the fixture.
     */
    protected final ComponentStrategyType getComponentStrategy()
    {
        return componentStrategy_.get();
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
        componentStrategy_ = Optional.of( createComponentStrategy() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultLocation} method returns
     * a copy of the default location.
     */
    @Test
    public void testGetDefaultLocation_ReturnValue_Copy()
    {
        final ComponentStrategyType componentStrategy = getComponentStrategy();
        final Point location = componentStrategy.getDefaultLocation();
        final Point expectedLocation = new Point( location );

        location.setLocation( 1010, 2020 );

        assertEquals( expectedLocation, componentStrategy.getDefaultLocation() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultOrigin} method returns a
     * copy of the default origin.
     */
    @Test
    public void testGetDefaultOrigin_ReturnValue_Copy()
    {
        final ComponentStrategyType componentStrategy = getComponentStrategy();
        final Point origin = componentStrategy.getDefaultOrigin();
        final Point expectedOrigin = new Point( origin );

        origin.setLocation( 1010, 2020 );

        assertEquals( expectedOrigin, componentStrategy.getDefaultOrigin() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultSurfaceDesigns} method
     * returns a copy of the default surface designs collection.
     */
    @Test
    public void testGetDefaultSurfaceDesigns_ReturnValue_Copy()
    {
        final ComponentStrategyType componentStrategy = getComponentStrategy();
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = componentStrategy.getDefaultSurfaceDesigns();
        final Map<ComponentOrientation, ComponentSurfaceDesign> expectedSurfaceDesigns = new HashMap<>( surfaceDesigns );

        surfaceDesigns.put( createIllegalOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );

        assertEquals( expectedSurfaceDesigns, componentStrategy.getDefaultSurfaceDesigns() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultSurfaceDesigns} method
     * returns a collection whose keys equal the supported orientations
     * collection.
     */
    @Test
    public void testGetDefaultSurfaceDesigns_ReturnValue_Keys_SupportedOrientations()
    {
        final ComponentStrategyType componentStrategy = getComponentStrategy();
        final Set<ComponentOrientation> expectedValue = new HashSet<>( componentStrategy.getSupportedOrientations() );

        final Set<ComponentOrientation> actualValue = componentStrategy.getDefaultSurfaceDesigns().keySet();

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@link IComponentStrategy#getSupportedOrientations} method
     * returns an immutable collection.
     */
    @Test
    public void testGetSupportedOrientations_ReturnValue_Immutable()
    {
        final ComponentStrategyType componentStrategy = getComponentStrategy();

        assertImmutableCollection( componentStrategy.getSupportedOrientations(), componentStrategy.getDefaultOrientation() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getSupportedOrientations} method
     * does not return an empty collection.
     */
    @Test
    public void testGetSupportedOrientations_ReturnValue_NonEmpty()
    {
        assertFalse( getComponentStrategy().getSupportedOrientations().isEmpty() );
    }
}
