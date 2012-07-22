/*
 * ContainerTest.java
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
 * Created on Jun 12, 2012 at 8:08:48 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesigns;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.Container} class.
 */
public final class ContainerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container under test in the fixture. */
    private Container container_;

    /** The table environment for use in the fixture. */
    private TableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerTest} class.
     */
    public ContainerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment.
     * 
     * @return A new component; never {@code null}.
     */
    /* @NonNull */
    private Component createUniqueComponent()
    {
        final Component component = new Card( tableEnvironment_ ); // FIXME: change to Component once it is no longer abstract
        for( final ComponentOrientation orientation : component.getSupportedOrientations() )
        {
            component.setSurfaceDesign( orientation, ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        }

        return component;
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
        tableEnvironment_ = new TableEnvironment();
        container_ = new CardPile( tableEnvironment_ ); // FIXME: change to Container once it is no longer abstract
    }

    /**
     * Ensures the {@code getComponentIndex(Component)} method throws an
     * exception when passed a component that is absent from the component
     * collection.
     */
    @Test( expected = AssertionError.class )
    public void testGetComponentIndexFromComponent_Component_Absent()
    {
        container_.getTableEnvironment().getLock().lock();
        try
        {
            container_.getComponentIndex( createUniqueComponent() );
        }
        finally
        {
            container_.getTableEnvironment().getLock().unlock();
        }
    }

    /**
     * Ensures the {@code getComponentIndex(Component)} method returns the
     * correct value when passed a component present in the component
     * collection.
     */
    @Test
    public void testGetComponentIndexFromComponent_Component_Present()
    {
        final Component component = createUniqueComponent();
        container_.addComponent( createUniqueComponent() );
        container_.addComponent( component );
        container_.addComponent( createUniqueComponent() );

        final int actualValue;
        container_.getTableEnvironment().getLock().lock();
        try
        {
            actualValue = container_.getComponentIndex( component );
        }
        finally
        {
            container_.getTableEnvironment().getLock().unlock();
        }

        assertEquals( 1, actualValue );
    }
}
