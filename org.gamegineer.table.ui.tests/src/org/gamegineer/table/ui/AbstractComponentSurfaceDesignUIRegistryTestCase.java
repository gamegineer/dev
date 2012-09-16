/*
 * AbstractComponentSurfaceDesignUIRegistryTestCase.java
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
 * Created on Apr 23, 2012 at 8:06:29 PM.
 */

package org.gamegineer.table.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry} interface.
 */
public abstract class AbstractComponentSurfaceDesignUIRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The component surface design user interface registry under test in the
     * fixture.
     */
    private IComponentSurfaceDesignUIRegistry componentSurfaceDesignUIRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentSurfaceDesignUIRegistryTestCase} class.
     */
    protected AbstractComponentSurfaceDesignUIRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component surface design user interface registry to be
     * tested.
     * 
     * @return The component surface design user interface registry to be
     *         tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IComponentSurfaceDesignUIRegistry createComponentSurfaceDesignUIRegistry()
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
        componentSurfaceDesignUIRegistry_ = createComponentSurfaceDesignUIRegistry();
        assertNotNull( componentSurfaceDesignUIRegistry_ );
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#getComponentSurfaceDesignUI}
     * method returns the correct value when passed an identifier that is
     * absent.
     */
    @Test
    public void testGetComponentSurfaceDesignUI_Id_Absent()
    {
        assertNull( componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUI( ComponentSurfaceDesignId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#getComponentSurfaceDesignUI}
     * method throws an exception when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentSurfaceDesignUI_Id_Null()
    {
        componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUI( null );
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#getComponentSurfaceDesignUI}
     * method returns the correct value when passed an identifier that is
     * present.
     */
    @Test
    public void testGetComponentSurfaceDesignUI_Id_Present()
    {
        final ComponentSurfaceDesignUI expectedComponentSurfaceDesignUI = TestComponentSurfaceDesignUIs.createUniqueComponentSurfaceDesignUI();
        componentSurfaceDesignUIRegistry_.registerComponentSurfaceDesignUI( expectedComponentSurfaceDesignUI );

        final ComponentSurfaceDesignUI actualComponentSurfaceDesignUI = componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUI( expectedComponentSurfaceDesignUI.getId() );

        assertSame( expectedComponentSurfaceDesignUI, actualComponentSurfaceDesignUI );
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#getComponentSurfaceDesignUIs}
     * method returns a copy of the registered component surface design user
     * interface collection.
     */
    @Test
    public void testGetComponentSurfaceDesignUIs_ReturnValue_Copy()
    {
        final Collection<ComponentSurfaceDesignUI> componentSurfaceDesignUIs = componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUIs();
        final int expectedComponentSurfaceDesignUIsSize = componentSurfaceDesignUIs.size();

        componentSurfaceDesignUIs.add( TestComponentSurfaceDesignUIs.createUniqueComponentSurfaceDesignUI() );

        assertEquals( expectedComponentSurfaceDesignUIsSize, componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUIs().size() );
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#getComponentSurfaceDesignUIs}
     * method returns a snapshot of the registered component surface design user
     * interface collection.
     */
    @Test
    public void testGetComponentSurfaceDesignUIs_ReturnValue_Snapshot()
    {
        final Collection<ComponentSurfaceDesignUI> componentSurfaceDesignUIs = componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUIs();
        componentSurfaceDesignUIRegistry_.registerComponentSurfaceDesignUI( TestComponentSurfaceDesignUIs.createUniqueComponentSurfaceDesignUI() );

        assertTrue( componentSurfaceDesignUIs.size() != componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUIs().size() );
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#registerComponentSurfaceDesignUI}
     * method throws an exception when passed a {@code null} component surface
     * design user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterComponentSurfaceDesignUI_ComponentSurfaceDesignUI_Null()
    {
        componentSurfaceDesignUIRegistry_.registerComponentSurfaceDesignUI( null );
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#registerComponentSurfaceDesignUI}
     * method throws an exception when a component surface design user interface
     * with the same identifier is already registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterComponentSurfaceDesignUI_ComponentSurfaceDesignUI_Registered()
    {
        final ComponentSurfaceDesignUI componentSurfaceDesignUI = TestComponentSurfaceDesignUIs.createUniqueComponentSurfaceDesignUI();
        componentSurfaceDesignUIRegistry_.registerComponentSurfaceDesignUI( componentSurfaceDesignUI );

        componentSurfaceDesignUIRegistry_.registerComponentSurfaceDesignUI( TestComponentSurfaceDesignUIs.cloneComponentSurfaceDesignUI( componentSurfaceDesignUI ) );
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#registerComponentSurfaceDesignUI}
     * method registers an unregistered component surface design user interface.
     */
    @Test
    public void testRegisterComponentSurfaceDesignUI_ComponentSurfaceDesignUI_Unregistered()
    {
        final ComponentSurfaceDesignUI componentSurfaceDesignUI = TestComponentSurfaceDesignUIs.createUniqueComponentSurfaceDesignUI();

        componentSurfaceDesignUIRegistry_.registerComponentSurfaceDesignUI( componentSurfaceDesignUI );

        assertTrue( componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUIs().contains( componentSurfaceDesignUI ) );
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#unregisterComponentSurfaceDesignUI}
     * method throws an exception when passed a {@code null} component surface
     * design user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterComponentSurfaceDesignUI_ComponentSurfaceDesignUI_Null()
    {
        componentSurfaceDesignUIRegistry_.unregisterComponentSurfaceDesignUI( null );
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#unregisterComponentSurfaceDesignUI}
     * method throws an exception when passed a component surface design user
     * interface whose identifier was previously registered but by a different
     * component surface design user interface instance.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterComponentSurfaceDesignUI_ComponentSurfaceDesignUI_Registered_DifferentInstance()
    {
        final ComponentSurfaceDesignUI componentSurfaceDesignUI = TestComponentSurfaceDesignUIs.createUniqueComponentSurfaceDesignUI();
        final int originalComponentSurfaceDesignUIsSize = componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUIs().size();
        componentSurfaceDesignUIRegistry_.registerComponentSurfaceDesignUI( componentSurfaceDesignUI );
        assertEquals( originalComponentSurfaceDesignUIsSize + 1, componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUIs().size() );

        componentSurfaceDesignUIRegistry_.unregisterComponentSurfaceDesignUI( TestComponentSurfaceDesignUIs.cloneComponentSurfaceDesignUI( componentSurfaceDesignUI ) );
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#unregisterComponentSurfaceDesignUI}
     * method unregisters a previously registered component surface design user
     * interface.
     */
    @Test
    public void testUnregisterComponentSurfaceDesignUI_ComponentSurfaceDesignUI_Registered_SameInstance()
    {
        final ComponentSurfaceDesignUI componentSurfaceDesignUI = TestComponentSurfaceDesignUIs.createUniqueComponentSurfaceDesignUI();
        final int originalComponentSurfaceDesignUIsSize = componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUIs().size();
        componentSurfaceDesignUIRegistry_.registerComponentSurfaceDesignUI( componentSurfaceDesignUI );
        assertEquals( originalComponentSurfaceDesignUIsSize + 1, componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUIs().size() );

        componentSurfaceDesignUIRegistry_.unregisterComponentSurfaceDesignUI( componentSurfaceDesignUI );

        assertEquals( originalComponentSurfaceDesignUIsSize, componentSurfaceDesignUIRegistry_.getComponentSurfaceDesignUIs().size() );
    }

    /**
     * Ensures the
     * {@link IComponentSurfaceDesignUIRegistry#unregisterComponentSurfaceDesignUI}
     * method throws an exception when passed a component surface design user
     * interface that was not previously registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterComponentSurfaceDesignUI_ComponentSurfaceDesignUI_Unregistered()
    {
        final ComponentSurfaceDesignUI componentSurfaceDesignUI = TestComponentSurfaceDesignUIs.createUniqueComponentSurfaceDesignUI();

        componentSurfaceDesignUIRegistry_.unregisterComponentSurfaceDesignUI( componentSurfaceDesignUI );
    }
}
