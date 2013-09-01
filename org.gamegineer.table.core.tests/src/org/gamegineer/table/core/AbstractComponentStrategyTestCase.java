/*
 * AbstractComponentStrategyTestCase.java
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
 * Created on Aug 1, 2012 at 7:55:13 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IComponentStrategy} interface.
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
    private ComponentStrategyType componentStrategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentStrategyTestCase} class.
     */
    protected AbstractComponentStrategyTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component strategy to be tested.
     * 
     * @return The component strategy to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ComponentStrategyType createComponentStrategy()
        throws Exception;

    /**
     * Creates a component orientation that is guaranteed to be illegal for all
     * components.
     * 
     * @return An illegal component orientation; never {@code null}.
     */
    /* @NonNull */
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
     * @return The component strategy under test in the fixture; never
     *         {@code null}.
     */
    /* @NonNull */
    protected final ComponentStrategyType getComponentStrategy()
    {
        assertNotNull( componentStrategy_ );
        return componentStrategy_;
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
        componentStrategy_ = createComponentStrategy();
        assertNotNull( componentStrategy_ );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultLocation} method returns
     * a copy of the default location.
     */
    @Test
    public void testGetDefaultLocation_ReturnValue_Copy()
    {
        final Point location = componentStrategy_.getDefaultLocation();
        final Point expectedLocation = new Point( location );

        location.setLocation( 1010, 2020 );

        assertEquals( expectedLocation, componentStrategy_.getDefaultLocation() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultLocation} method does not
     * return {@code null}.
     */
    @Test
    public void testGetDefaultLocation_ReturnValue_NonNull()
    {
        assertNotNull( componentStrategy_.getDefaultLocation() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultOrientation} method does
     * not return {@code null}.
     */
    @Test
    public void testGetDefaultOrientation_ReturnValue_NonNull()
    {
        assertNotNull( componentStrategy_.getDefaultOrientation() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultOrigin} method returns a
     * copy of the default origin.
     */
    @Test
    public void testGetDefaultOrigin_ReturnValue_Copy()
    {
        final Point origin = componentStrategy_.getDefaultOrigin();
        final Point expectedOrigin = new Point( origin );

        origin.setLocation( 1010, 2020 );

        assertEquals( expectedOrigin, componentStrategy_.getDefaultOrigin() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultOrigin} method does not
     * return {@code null}.
     */
    @Test
    public void testGetDefaultOrigin_ReturnValue_NonNull()
    {
        assertNotNull( componentStrategy_.getDefaultOrigin() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultSurfaceDesigns} method
     * returns a copy of the default surface designs collection.
     */
    @Test
    public void testGetDefaultSurfaceDesigns_ReturnValue_Copy()
    {
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = componentStrategy_.getDefaultSurfaceDesigns();
        final Map<ComponentOrientation, ComponentSurfaceDesign> expectedSurfaceDesigns = new HashMap<>( surfaceDesigns );

        surfaceDesigns.put( createIllegalOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );

        assertEquals( expectedSurfaceDesigns, componentStrategy_.getDefaultSurfaceDesigns() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultSurfaceDesigns} method
     * returns a collection whose keys equal the supported orientations
     * collection.
     */
    @Test
    public void testGetDefaultSurfaceDesigns_ReturnValue_Keys_SupportedOrientations()
    {
        final Set<ComponentOrientation> expectedValue = new HashSet<>( componentStrategy_.getSupportedOrientations() );

        final Set<ComponentOrientation> actualValue = componentStrategy_.getDefaultSurfaceDesigns().keySet();

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultSurfaceDesigns} method
     * does not return {@code null}.
     */
    @Test
    public void testGetDefaultSurfaceDesigns_ReturnValue_NonNull()
    {
        assertNotNull( componentStrategy_.getDefaultSurfaceDesigns() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getDefaultSurfaceDesigns} method
     * returns a collection whose values are not {@code null}.
     */
    @Test
    public void testGetDefaultSurfaceDesigns_ReturnValue_Values_NonNull()
    {
        for( final ComponentSurfaceDesign surfaceDesign : componentStrategy_.getDefaultSurfaceDesigns().values() )
        {
            assertNotNull( surfaceDesign );
        }
    }

    /**
     * Ensures the {@link IComponentStrategy#getExtension} method throws an
     * exception when passed a {@code null} type.
     */
    @Test( expected = NullPointerException.class )
    public void testGetExtension_Type_Null()
    {
        componentStrategy_.getExtension( null );
    }

    /**
     * Ensures the {@link IComponentStrategy#getId} method does not return
     * {@code null}.
     */
    @Test
    public void testGetId_ReturnValue_NonNull()
    {
        assertNotNull( componentStrategy_.getId() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getSupportedOrientations} method
     * returns an immutable collection.
     */
    @Test
    public void testGetSupportedOrientations_ReturnValue_Immutable()
    {
        assertImmutableCollection( componentStrategy_.getSupportedOrientations() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getSupportedOrientations} method
     * does not return an empty collection.
     */
    @Test
    public void testGetSupportedOrientations_ReturnValue_NonEmpty()
    {
        assertFalse( componentStrategy_.getSupportedOrientations().isEmpty() );
    }

    /**
     * Ensures the {@link IComponentStrategy#getSupportedOrientations} method
     * does not return {@code null}.
     */
    @Test
    public void testGetSupportedOrientations_ReturnValue_NonNull()
    {
        assertNotNull( componentStrategy_.getSupportedOrientations() );
    }
}
