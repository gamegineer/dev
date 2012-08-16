/*
 * AbstractContainerLayoutRegistryTestCase.java
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
 * Created on Aug 9, 2012 at 8:13:46 PM.
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
 * {@link org.gamegineer.table.core.IContainerLayoutRegistry} interface.
 */
public abstract class AbstractContainerLayoutRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container layout registry under test in the fixture. */
    private IContainerLayoutRegistry containerLayoutRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractContainerLayoutRegistryTestCase} class.
     */
    protected AbstractContainerLayoutRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the container layout registry to be tested.
     * 
     * @return The container layout registry to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IContainerLayoutRegistry createContainerLayoutRegistry()
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
        containerLayoutRegistry_ = createContainerLayoutRegistry();
        assertNotNull( containerLayoutRegistry_ );
    }

    /**
     * Ensures the {@code getContainerLayout} method returns the correct value
     * when passed an identifier that is absent.
     */
    @Test
    public void testGetContainerLayout_Id_Absent()
    {
        assertNull( containerLayoutRegistry_.getContainerLayout( ContainerLayoutId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getContainerLayout} method throws an exception when
     * passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetContainerLayout_Id_Null()
    {
        containerLayoutRegistry_.getContainerLayout( null );
    }

    /**
     * Ensures the {@code getContainerLayout} method returns the correct value
     * when passed an identifier that is present.
     */
    @Test
    public void testGetContainerLayout_Id_Present()
    {
        final IContainerLayout expectedContainerLayout = TestContainerLayouts.createUniqueContainerLayout();
        containerLayoutRegistry_.registerContainerLayout( expectedContainerLayout );

        final IContainerLayout actualContainerLayout = containerLayoutRegistry_.getContainerLayout( expectedContainerLayout.getId() );

        assertSame( expectedContainerLayout, actualContainerLayout );
    }

    /**
     * Ensures the {@code getContainerLayouts} method returns a copy of the
     * registered container layout collection.
     */
    @Test
    public void testGetContainerLayouts_ReturnValue_Copy()
    {
        final Collection<IContainerLayout> containerLayouts = containerLayoutRegistry_.getContainerLayouts();
        final int expectedContainerLayoutsSize = containerLayouts.size();

        containerLayouts.add( TestContainerLayouts.createUniqueContainerLayout() );

        assertEquals( expectedContainerLayoutsSize, containerLayoutRegistry_.getContainerLayouts().size() );
    }

    /**
     * Ensures the {@code getContainerLayouts} method returns a snapshot of the
     * registered container layout collection.
     */
    @Test
    public void testGetContainerLayouts_ReturnValue_Snapshot()
    {
        final Collection<IContainerLayout> containerLayouts = containerLayoutRegistry_.getContainerLayouts();
        containerLayoutRegistry_.registerContainerLayout( TestContainerLayouts.createUniqueContainerLayout() );

        assertTrue( containerLayouts.size() != containerLayoutRegistry_.getContainerLayouts().size() );
    }

    /**
     * Ensures the {@code registerContainerLayout} method throws an exception
     * when passed a {@code null} container layout.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterContainerLayout_ContainerLayout_Null()
    {
        containerLayoutRegistry_.registerContainerLayout( null );
    }

    /**
     * Ensures the {@code registerContainerLayout} method throws an exception
     * when a container layout with the same identifier is already registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterContainerLayout_ContainerLayout_Registered()
    {
        final IContainerLayout containerLayout = TestContainerLayouts.createUniqueContainerLayout();
        containerLayoutRegistry_.registerContainerLayout( containerLayout );

        containerLayoutRegistry_.registerContainerLayout( TestContainerLayouts.cloneContainerLayout( containerLayout ) );
    }

    /**
     * Ensures the {@code registerContainerLayout} method registers an
     * unregistered container layout.
     */
    @Test
    public void testRegisterContainerLayout_ContainerLayout_Unregistered()
    {
        final IContainerLayout containerLayout = TestContainerLayouts.createUniqueContainerLayout();

        containerLayoutRegistry_.registerContainerLayout( containerLayout );

        assertTrue( containerLayoutRegistry_.getContainerLayouts().contains( containerLayout ) );
    }

    /**
     * Ensures the {@code unregisterContainerLayout} method throws an exception
     * when passed a {@code null} container layout.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterContainerLayout_ContainerLayout_Null()
    {
        containerLayoutRegistry_.unregisterContainerLayout( null );
    }

    /**
     * Ensures the {@code unregisterContainerLayout} method throws an exception
     * when passed a container layout whose identifier was previously registered
     * but by a different container layout instance.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterContainerLayout_ContainerLayout_Registered_DifferentInstance()
    {
        final IContainerLayout containerLayout = TestContainerLayouts.createUniqueContainerLayout();
        final int originalContainerLayoutsSize = containerLayoutRegistry_.getContainerLayouts().size();
        containerLayoutRegistry_.registerContainerLayout( containerLayout );
        assertEquals( originalContainerLayoutsSize + 1, containerLayoutRegistry_.getContainerLayouts().size() );

        containerLayoutRegistry_.unregisterContainerLayout( TestContainerLayouts.cloneContainerLayout( containerLayout ) );
    }

    /**
     * Ensures the {@code unregisterContainerLayout} method unregisters a
     * previously registered container layout.
     */
    @Test
    public void testUnregisterContainerLayout_ContainerLayout_Registered_SameInstance()
    {
        final IContainerLayout containerLayout = TestContainerLayouts.createUniqueContainerLayout();
        final int originalContainerLayoutsSize = containerLayoutRegistry_.getContainerLayouts().size();
        containerLayoutRegistry_.registerContainerLayout( containerLayout );
        assertEquals( originalContainerLayoutsSize + 1, containerLayoutRegistry_.getContainerLayouts().size() );

        containerLayoutRegistry_.unregisterContainerLayout( containerLayout );

        assertEquals( originalContainerLayoutsSize, containerLayoutRegistry_.getContainerLayouts().size() );
    }

    /**
     * Ensures the {@code unregisterContainerLayout} method throws an exception
     * when passed a container layout that was not previously registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterContainerLayout_ContainerLayout_Unregistered()
    {
        final IContainerLayout containerLayout = TestContainerLayouts.createUniqueContainerLayout();

        containerLayoutRegistry_.unregisterContainerLayout( containerLayout );
    }
}
