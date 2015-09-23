/*
 * AbstractVersionControlTestCase.java
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
 * Created on Jun 21, 2013 at 10:38:30 PM.
 */

package org.gamegineer.table.core.version.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.awt.Point;
import java.util.Optional;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.test.TestComponentSurfaceDesigns;
import org.gamegineer.table.core.test.TestComponents;
import org.gamegineer.table.core.test.TestContainerLayouts;
import org.gamegineer.table.core.version.IVersionControl;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IVersionControl} interface.
 */
public abstract class AbstractVersionControlTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The version control under test in the fixture. */
    private Optional<IVersionControl> versionControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractVersionControlTestCase}
     * class.
     */
    protected AbstractVersionControlTestCase()
    {
        versionControl_ = Optional.empty();
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
    private IComponent createUniqueComponent()
    {
        return TestComponents.createUniqueComponent( getTable().getTableEnvironment() );
    }

    /**
     * Creates a new container with unique attributes using the fixture table
     * environment.
     * 
     * @return A new container; never {@code null}.
     */
    private IContainer createUniqueContainer()
    {
        return TestComponents.createUniqueContainer( getTable().getTableEnvironment() );
    }

    /**
     * Gets the table associated with the fixture.
     * 
     * @return The table associated with the fixture; never {@code null}.
     */
    protected abstract ITable getTable();

    /**
     * Gets the tabletop associated with the fixture.
     * 
     * @return The tabletop associated with the fixture; never {@code null}.
     */
    private IContainer getTabletop()
    {
        return getTable().getTabletop();
    }

    /**
     * Gets the version control under test in the fixture.
     * 
     * @return The version control under test in the fixture; never {@code null}
     *         .
     */
    protected final IVersionControl getVersionControl()
    {
        return versionControl_.get();
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
        final IVersionControl versionControl = getTable().getExtension( IVersionControl.class );
        assertNotNull( versionControl );
        versionControl_ = Optional.of( versionControl );
    }

    /**
     * Ensures adding a component to a container that is associated with the
     * table increments the table revision number.
     */
    @Test
    public void testAddComponent_AssociatedWithTable_IncrementsRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        getTabletop().addComponent( createUniqueComponent() );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertTrue( "new revision number not greater than old revision number", newRevisionNumber > oldRevisionNumber ); //$NON-NLS-1$
    }

    /**
     * Ensures adding a component to a container that is not associated with the
     * table does not increment the table revision number.
     */
    @Test
    public void testAddComponent_NotAssociatedWithTable_DoesNotIncrementRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IContainer container = createUniqueContainer();
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        container.addComponent( createUniqueComponent() );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertEquals( oldRevisionNumber, newRevisionNumber );
    }

    /**
     * Ensures removing a component from a container that is associated with the
     * table increments the table revision number.
     */
    @Test
    public void testRemoveComponent_AssociatedWithTable_IncrementsRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IContainer tabletop = getTabletop();
        final IComponent component = createUniqueComponent();
        tabletop.addComponent( component );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        tabletop.removeComponent( component );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertTrue( "new revision number not greater than old revision number", newRevisionNumber > oldRevisionNumber ); //$NON-NLS-1$
    }

    /**
     * Ensures removing a component from a container that is not associated with
     * the table does not increment the table revision number.
     */
    @Test
    public void testRemoveComponent_NotAssociatedWithTable_DoesNotIncrementRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IContainer container = createUniqueContainer();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        container.removeComponent( component );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertEquals( oldRevisionNumber, newRevisionNumber );
    }

    /**
     * Ensures setting the location of a component that is associated with the
     * table increments the table revision number.
     */
    @Test
    public void testSetComponentLocation_AssociatedWithTable_IncrementsRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IComponent component = createUniqueComponent();
        getTabletop().addComponent( component );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        component.setLocation( new Point( 1000, 1000 ) );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertTrue( "new revision number not greater than old revision number", newRevisionNumber > oldRevisionNumber ); //$NON-NLS-1$
    }

    /**
     * Ensures setting the location of a component that is not associated with
     * the table does not increment the table revision number.
     */
    @Test
    public void testSetComponentLocation_NotAssociatedWithTable_DoesNotIncrementRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IContainer container = createUniqueContainer();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        component.setLocation( new Point( 1000, 1000 ) );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertEquals( oldRevisionNumber, newRevisionNumber );
    }

    /**
     * Ensures setting the orientation of a component that is associated with
     * the table increments the table revision number.
     */
    @Test
    public void testSetComponentOrientation_AssociatedWithTable_IncrementsRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IComponent component = createUniqueComponent();
        getTabletop().addComponent( component );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        component.setOrientation( component.getOrientation().inverse() );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertTrue( "new revision number not greater than old revision number", newRevisionNumber > oldRevisionNumber ); //$NON-NLS-1$
    }

    /**
     * Ensures setting the orientation of a component that is not associated
     * with the table does not increment the table revision number.
     */
    @Test
    public void testSetComponentOrientation_NotAssociatedWithTable_DoesNotIncrementRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IContainer container = createUniqueContainer();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        component.setOrientation( component.getOrientation().inverse() );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertEquals( oldRevisionNumber, newRevisionNumber );
    }

    /**
     * Ensures setting the origin of a component that is associated with the
     * table increments the table revision number.
     */
    @Test
    public void testSetComponentOrigin_AssociatedWithTable_IncrementsRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IComponent component = createUniqueComponent();
        getTabletop().addComponent( component );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        component.setOrigin( new Point( 1000, 1000 ) );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertTrue( "new revision number not greater than old revision number", newRevisionNumber > oldRevisionNumber ); //$NON-NLS-1$
    }

    /**
     * Ensures setting the origin of a component that is not associated with the
     * table does not increment the table revision number.
     */
    @Test
    public void testSetComponentOrigin_NotAssociatedWithTable_DoesNotIncrementRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IContainer container = createUniqueContainer();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        component.setOrigin( new Point( 1000, 1000 ) );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertEquals( oldRevisionNumber, newRevisionNumber );
    }

    /**
     * Ensures setting the surface design of a component that is associated with
     * the table increments the table revision number.
     */
    @Test
    public void testSetComponentSurfaceDesign_AssociatedWithTable_IncrementsRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IComponent component = createUniqueComponent();
        getTabletop().addComponent( component );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        component.setSurfaceDesign( component.getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertTrue( "new revision number not greater than old revision number", newRevisionNumber > oldRevisionNumber ); //$NON-NLS-1$
    }

    /**
     * Ensures setting the surface design of a component that is not associated
     * with the table does not increment the table revision number.
     */
    @Test
    public void testSetComponentSurfaceDesign_NotAssociatedWithTable_DoesNotIncrementRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IContainer container = createUniqueContainer();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        component.setSurfaceDesign( component.getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertEquals( oldRevisionNumber, newRevisionNumber );
    }

    /**
     * Ensures setting the layout of a container that is associated with the
     * table increments the table revision number.
     */
    @Test
    public void testSetContainerLayout_AssociatedWithTable_IncrementsRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IContainer container = createUniqueContainer();
        getTabletop().addComponent( container );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        container.setLayout( TestContainerLayouts.createUniqueContainerLayout() );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertTrue( "new revision number not greater than old revision number", newRevisionNumber > oldRevisionNumber ); //$NON-NLS-1$
    }

    /**
     * Ensures setting the layout of a container that is not associated with the
     * table does not increment the table revision number.
     */
    @Test
    public void testSetContainerLayout_NotAssociatedWithTable_DoesNotIncrementRevisionNumber()
    {
        final IVersionControl versionControl = getVersionControl();
        final IContainer parentContainer = createUniqueContainer();
        final IContainer container = createUniqueContainer();
        parentContainer.addComponent( container );
        final long oldRevisionNumber = versionControl.getRevisionNumber();

        container.setLayout( TestContainerLayouts.createUniqueContainerLayout() );
        final long newRevisionNumber = versionControl.getRevisionNumber();

        assertEquals( oldRevisionNumber, newRevisionNumber );
    }
}
