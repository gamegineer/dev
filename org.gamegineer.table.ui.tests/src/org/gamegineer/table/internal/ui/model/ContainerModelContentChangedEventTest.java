/*
 * ContainerModelContentChangedEventTest.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Sep 14, 2012 at 9:12:15 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.gamegineer.table.ui.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ContainerModelContentChangedEvent}
 * class.
 */
public final class ContainerModelContentChangedEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container model content changed event under test in the fixture. */
    private ContainerModelContentChangedEvent event_;

    /** The table environment model for use in the fixture. */
    private TableEnvironmentModel tableEnvironmentModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerModelContentChangedEventTest} class.
     */
    public ContainerModelContentChangedEventTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new unique component model.
     * 
     * @return A new unique component model; never {@code null}.
     */
    /* @NonNull */
    private ComponentModel createUniqueComponentModel()
    {
        return new ComponentModel( tableEnvironmentModel_, TestComponents.createUniqueComponent( tableEnvironmentModel_.getTableEnvironment() ) );
    }

    /**
     * Creates a new unique container model.
     * 
     * @return A new unique container model; never {@code null}.
     */
    /* @NonNull */
    private ContainerModel createUniqueContainerModel()
    {
        return new ContainerModel( tableEnvironmentModel_, TestComponents.createUniqueContainer( tableEnvironmentModel_.getTableEnvironment() ) );
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
        tableEnvironmentModel_ = new TableEnvironmentModel( TableEnvironmentFactory.createTableEnvironment( new SingleThreadedTableEnvironmentContext() ) );
        event_ = new ContainerModelContentChangedEvent( createUniqueContainerModel(), createUniqueComponentModel(), 0 );
    }

    /**
     * Ensures the
     * {@link ContainerModelContentChangedEvent#ContainerModelContentChangedEvent}
     * constructor throws an exception when passed a {@code null} component
     * model.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_ComponentModel_Null()
    {
        new ContainerModelContentChangedEvent( createUniqueContainerModel(), null, 0 );
    }

    /**
     * Ensures the
     * {@link ContainerModelContentChangedEvent#ContainerModelContentChangedEvent}
     * constructor throws an exception when passed an illegal component model
     * index that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_ComponentModelIndex_Illegal_Negative()
    {
        new ContainerModelContentChangedEvent( createUniqueContainerModel(), createUniqueComponentModel(), -1 );
    }

    /**
     * Ensures the
     * {@link ContainerModelContentChangedEvent#ContainerModelContentChangedEvent}
     * constructor throws an exception when passed a {@code null} source.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Source_Null()
    {
        new ContainerModelContentChangedEvent( null, createUniqueComponentModel(), 0 );
    }

    /**
     * Ensures the {@link ContainerModelContentChangedEvent#getComponentModel}
     * method does not return {@code null}.
     */
    @Test
    public void testGetComponentModel_ReturnValue_NonNull()
    {
        assertNotNull( event_.getComponentModel() );
    }
}
