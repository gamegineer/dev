/*
 * AbstractComponentStrategyUIRegistryTestCase.java
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
 * Created on Sep 25, 2012 at 8:09:16 PM.
 */

package org.gamegineer.table.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.table.core.ComponentStrategyId;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.ui.IComponentStrategyUIRegistry} interface.
 */
public abstract class AbstractComponentStrategyUIRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The component strategy user interface registry under test in the fixture.
     */
    private IComponentStrategyUIRegistry componentStrategyUIRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentStrategyUIRegistryTestCase} class.
     */
    protected AbstractComponentStrategyUIRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component strategy user interface registry to be tested.
     * 
     * @return The component strategy user interface registry to be tested;
     *         never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IComponentStrategyUIRegistry createComponentStrategyUIRegistry()
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
        componentStrategyUIRegistry_ = createComponentStrategyUIRegistry();
        assertNotNull( componentStrategyUIRegistry_ );
    }

    /**
     * Ensures the {@link IComponentStrategyUIRegistry#getComponentStrategyUI}
     * method returns the correct value when passed an identifier that is
     * absent.
     */
    @Test
    public void testGetComponentStrategyUI_Id_Absent()
    {
        assertNull( componentStrategyUIRegistry_.getComponentStrategyUI( ComponentStrategyId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link IComponentStrategyUIRegistry#getComponentStrategyUI}
     * method throws an exception when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentStrategyUI_Id_Null()
    {
        componentStrategyUIRegistry_.getComponentStrategyUI( null );
    }

    /**
     * Ensures the {@link IComponentStrategyUIRegistry#getComponentStrategyUI}
     * method returns the correct value when passed an identifier that is
     * present.
     */
    @Test
    public void testGetComponentStrategyUI_Id_Present()
    {
        final IComponentStrategyUI expectedComponentStrategyUI = TestComponentStrategyUIs.createUniqueComponentStrategyUI();
        componentStrategyUIRegistry_.registerComponentStrategyUI( expectedComponentStrategyUI );

        final IComponentStrategyUI actualComponentStrategyUI = componentStrategyUIRegistry_.getComponentStrategyUI( expectedComponentStrategyUI.getId() );

        assertSame( expectedComponentStrategyUI, actualComponentStrategyUI );
    }

    /**
     * Ensures the {@link IComponentStrategyUIRegistry#getComponentStrategyUIs}
     * method returns a copy of the registered component strategy user interface
     * collection.
     */
    @Test
    public void testGetComponentStrategyUIs_ReturnValue_Copy()
    {
        final Collection<IComponentStrategyUI> componentStrategyUIs = componentStrategyUIRegistry_.getComponentStrategyUIs();
        final int expectedComponentStrategyUIsSize = componentStrategyUIs.size();

        componentStrategyUIs.add( TestComponentStrategyUIs.createUniqueComponentStrategyUI() );

        assertEquals( expectedComponentStrategyUIsSize, componentStrategyUIRegistry_.getComponentStrategyUIs().size() );
    }

    /**
     * Ensures the {@link IComponentStrategyUIRegistry#getComponentStrategyUIs}
     * method returns a snapshot of the registered component strategy user
     * interface collection.
     */
    @Test
    public void testGetComponentStrategyUIs_ReturnValue_Snapshot()
    {
        final Collection<IComponentStrategyUI> componentStrategyUIs = componentStrategyUIRegistry_.getComponentStrategyUIs();
        componentStrategyUIRegistry_.registerComponentStrategyUI( TestComponentStrategyUIs.createUniqueComponentStrategyUI() );

        assertTrue( componentStrategyUIs.size() != componentStrategyUIRegistry_.getComponentStrategyUIs().size() );
    }

    /**
     * Ensures the
     * {@link IComponentStrategyUIRegistry#registerComponentStrategyUI} method
     * throws an exception when passed a {@code null} component strategy user
     * interface.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterComponentStrategyUI_ComponentStrategyUI_Null()
    {
        componentStrategyUIRegistry_.registerComponentStrategyUI( null );
    }

    /**
     * Ensures the
     * {@link IComponentStrategyUIRegistry#registerComponentStrategyUI} method
     * throws an exception when a component strategy user interface with the
     * same identifier is already registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterComponentStrategyUI_ComponentStrategyUI_Registered()
    {
        final IComponentStrategyUI componentStrategyUI = TestComponentStrategyUIs.createUniqueComponentStrategyUI();
        componentStrategyUIRegistry_.registerComponentStrategyUI( componentStrategyUI );

        componentStrategyUIRegistry_.registerComponentStrategyUI( TestComponentStrategyUIs.cloneComponentStrategyUI( componentStrategyUI ) );
    }

    /**
     * Ensures the
     * {@link IComponentStrategyUIRegistry#registerComponentStrategyUI} method
     * registers an unregistered component strategy user interface.
     */
    @Test
    public void testRegisterComponentStrategyUI_ComponentStrategyUI_Unregistered()
    {
        final IComponentStrategyUI componentStrategyUI = TestComponentStrategyUIs.createUniqueComponentStrategyUI();

        componentStrategyUIRegistry_.registerComponentStrategyUI( componentStrategyUI );

        assertTrue( componentStrategyUIRegistry_.getComponentStrategyUIs().contains( componentStrategyUI ) );
    }

    /**
     * Ensures the
     * {@link IComponentStrategyUIRegistry#unregisterComponentStrategyUI} method
     * throws an exception when passed a {@code null} component strategy user
     * interface.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterComponentStrategyUI_ComponentStrategyUI_Null()
    {
        componentStrategyUIRegistry_.unregisterComponentStrategyUI( null );
    }

    /**
     * Ensures the
     * {@link IComponentStrategyUIRegistry#unregisterComponentStrategyUI} method
     * throws an exception when passed a component strategy user interface whose
     * identifier was previously registered but by a different component
     * strategy user interface instance.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterComponentStrategyUI_ComponentStrategyUI_Registered_DifferentInstance()
    {
        final IComponentStrategyUI componentStrategyUI = TestComponentStrategyUIs.createUniqueComponentStrategyUI();
        final int originalComponentStrategyUIsSize = componentStrategyUIRegistry_.getComponentStrategyUIs().size();
        componentStrategyUIRegistry_.registerComponentStrategyUI( componentStrategyUI );
        assertEquals( originalComponentStrategyUIsSize + 1, componentStrategyUIRegistry_.getComponentStrategyUIs().size() );

        componentStrategyUIRegistry_.unregisterComponentStrategyUI( TestComponentStrategyUIs.cloneComponentStrategyUI( componentStrategyUI ) );
    }

    /**
     * Ensures the
     * {@link IComponentStrategyUIRegistry#unregisterComponentStrategyUI} method
     * unregisters a previously registered component strategy user interface.
     */
    @Test
    public void testUnregisterComponentStrategyUI_ComponentStrategyUI_Registered_SameInstance()
    {
        final IComponentStrategyUI componentStrategyUI = TestComponentStrategyUIs.createUniqueComponentStrategyUI();
        final int originalComponentStrategyUIsSize = componentStrategyUIRegistry_.getComponentStrategyUIs().size();
        componentStrategyUIRegistry_.registerComponentStrategyUI( componentStrategyUI );
        assertEquals( originalComponentStrategyUIsSize + 1, componentStrategyUIRegistry_.getComponentStrategyUIs().size() );

        componentStrategyUIRegistry_.unregisterComponentStrategyUI( componentStrategyUI );

        assertEquals( originalComponentStrategyUIsSize, componentStrategyUIRegistry_.getComponentStrategyUIs().size() );
    }

    /**
     * Ensures the
     * {@link IComponentStrategyUIRegistry#unregisterComponentStrategyUI} method
     * throws an exception when passed a component strategy user interface that
     * was not previously registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterComponentStrategyUI_ComponentStrategyUI_Unregistered()
    {
        final IComponentStrategyUI componentStrategyUI = TestComponentStrategyUIs.createUniqueComponentStrategyUI();

        componentStrategyUIRegistry_.unregisterComponentStrategyUI( componentStrategyUI );
    }
}
