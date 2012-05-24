/*
 * AbstractComponentSurfaceDesignRegistryTestCase.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Apr 7, 2012 at 9:15:35 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IComponentSurfaceDesignRegistry} interface.
 */
public abstract class AbstractComponentSurfaceDesignRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component surface design registry under test in the fixture. */
    private IComponentSurfaceDesignRegistry componentSurfaceDesignRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentSurfaceDesignRegistryTestCase} class.
     */
    protected AbstractComponentSurfaceDesignRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component surface design registry to be tested.
     * 
     * @return The component surface design registry to be tested; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IComponentSurfaceDesignRegistry createComponentSurfaceDesignRegistry()
        throws Exception;

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
        componentSurfaceDesignRegistry_ = createComponentSurfaceDesignRegistry();
        assertNotNull( componentSurfaceDesignRegistry_ );
    }

    /**
     * Ensures the {@code getComponentSurfaceDesign} method returns the correct
     * value when passed an identifier that is absent.
     */
    @Test
    public void testGetComponentSurfaceDesign_Id_Absent()
    {
        assertNull( componentSurfaceDesignRegistry_.getComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getComponentSurfaceDesign} method throws an exception
     * when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentSurfaceDesign_Id_Null()
    {
        componentSurfaceDesignRegistry_.getComponentSurfaceDesign( null );
    }

    /**
     * Ensures the {@code getComponentSurfaceDesign} method returns the correct
     * value when passed an identifier that is present.
     */
    @Test
    public void testGetComponentSurfaceDesign_Id_Present()
    {
        final ComponentSurfaceDesign expectedComponentSurfaceDesign = ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign();
        componentSurfaceDesignRegistry_.registerComponentSurfaceDesign( expectedComponentSurfaceDesign );

        final ComponentSurfaceDesign actualComponentSurfaceDesign = componentSurfaceDesignRegistry_.getComponentSurfaceDesign( expectedComponentSurfaceDesign.getId() );

        assertSame( expectedComponentSurfaceDesign, actualComponentSurfaceDesign );
    }

    /**
     * Ensures the {@code getComponentSurfaceDesigns} method returns a copy of
     * the registered component surface design collection.
     */
    @Test
    public void testGetComponentSurfaceDesigns_ReturnValue_Copy()
    {
        final Collection<ComponentSurfaceDesign> componentSurfaceDesigns = componentSurfaceDesignRegistry_.getComponentSurfaceDesigns();
        final int expectedComponentSurfaceDesignsSize = componentSurfaceDesigns.size();

        componentSurfaceDesigns.add( ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );

        assertEquals( expectedComponentSurfaceDesignsSize, componentSurfaceDesignRegistry_.getComponentSurfaceDesigns().size() );
    }

    /**
     * Ensures the {@code getComponentSurfaceDesigns} method returns a snapshot
     * of the registered component surface design collection.
     */
    @Test
    public void testGetComponentSurfaceDesigns_ReturnValue_Snapshot()
    {
        final Collection<ComponentSurfaceDesign> componentSurfaceDesigns = componentSurfaceDesignRegistry_.getComponentSurfaceDesigns();
        componentSurfaceDesignRegistry_.registerComponentSurfaceDesign( ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );

        assertTrue( componentSurfaceDesigns.size() != componentSurfaceDesignRegistry_.getComponentSurfaceDesigns().size() );
    }

    /**
     * Ensures the {@code registerComponentSurfaceDesign} method throws an
     * exception when passed a {@code null} component surface design.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterComponentSurfaceDesign_ComponentSurfaceDesign_Null()
    {
        componentSurfaceDesignRegistry_.registerComponentSurfaceDesign( null );
    }

    /**
     * Ensures the {@code registerComponentSurfaceDesign} method throws an
     * exception when a component surface design with the same identifier is
     * already registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterComponentSurfaceDesign_ComponentSurfaceDesign_Registered()
    {
        final ComponentSurfaceDesign componentSurfaceDesign = ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign();
        componentSurfaceDesignRegistry_.registerComponentSurfaceDesign( componentSurfaceDesign );

        componentSurfaceDesignRegistry_.registerComponentSurfaceDesign( ComponentSurfaceDesigns.cloneComponentSurfaceDesign( componentSurfaceDesign ) );
    }

    /**
     * Ensures the {@code registerComponentSurfaceDesign} method registers an
     * unregistered component surface design.
     */
    @Test
    public void testRegisterComponentSurfaceDesign_ComponentSurfaceDesign_Unregistered()
    {
        final ComponentSurfaceDesign componentSurfaceDesign = ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign();

        componentSurfaceDesignRegistry_.registerComponentSurfaceDesign( componentSurfaceDesign );

        assertTrue( componentSurfaceDesignRegistry_.getComponentSurfaceDesigns().contains( componentSurfaceDesign ) );
    }

    /**
     * Ensures the {@code unregisterComponentSurfaceDesign} method throws an
     * exception when passed a {@code null} component surface design.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterComponentSurfaceDesign_ComponentSurfaceDesign_Null()
    {
        componentSurfaceDesignRegistry_.unregisterComponentSurfaceDesign( null );
    }

    /**
     * Ensures the {@code unregisterComponentSurfaceDesign} method throws an
     * exception when passed a component surface design whose identifier was
     * previously registered but by a different component surface design
     * instance.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterComponentSurfaceDesign_ComponentSurfaceDesign_Registered_DifferentInstance()
    {
        final ComponentSurfaceDesign componentSurfaceDesign = ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign();
        final int originalComponentSurfaceDesignsSize = componentSurfaceDesignRegistry_.getComponentSurfaceDesigns().size();
        componentSurfaceDesignRegistry_.registerComponentSurfaceDesign( componentSurfaceDesign );
        assertEquals( originalComponentSurfaceDesignsSize + 1, componentSurfaceDesignRegistry_.getComponentSurfaceDesigns().size() );

        componentSurfaceDesignRegistry_.unregisterComponentSurfaceDesign( ComponentSurfaceDesigns.cloneComponentSurfaceDesign( componentSurfaceDesign ) );
    }

    /**
     * Ensures the {@code unregisterComponentSurfaceDesign} method unregisters a
     * previously registered component surface design.
     */
    @Test
    public void testUnregisterComponentSurfaceDesign_ComponentSurfaceDesign_Registered_SameInstance()
    {
        final ComponentSurfaceDesign componentSurfaceDesign = ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign();
        final int originalComponentSurfaceDesignsSize = componentSurfaceDesignRegistry_.getComponentSurfaceDesigns().size();
        componentSurfaceDesignRegistry_.registerComponentSurfaceDesign( componentSurfaceDesign );
        assertEquals( originalComponentSurfaceDesignsSize + 1, componentSurfaceDesignRegistry_.getComponentSurfaceDesigns().size() );

        componentSurfaceDesignRegistry_.unregisterComponentSurfaceDesign( componentSurfaceDesign );

        assertEquals( originalComponentSurfaceDesignsSize, componentSurfaceDesignRegistry_.getComponentSurfaceDesigns().size() );
    }

    /**
     * Ensures the {@code unregisterComponentSurfaceDesign} method throws an
     * exception when passed a component surface design that was not previously
     * registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterComponentSurfaceDesign_ComponentSurfaceDesign_Unregistered()
    {
        final ComponentSurfaceDesign componentSurfaceDesign = ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign();

        componentSurfaceDesignRegistry_.unregisterComponentSurfaceDesign( componentSurfaceDesign );
    }
}
