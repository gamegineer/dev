/*
 * TableModelTest.java
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
 * Created on Dec 28, 2009 at 8:25:04 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.TableModel} class.
 */
public final class TableModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table model under test in the fixture. */
    private TableModel model_;

    /** The nice mocks control for use in the fixture. */
    private IMocksControl niceMocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableModelTest} class.
     */
    public TableModelTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a temporary file.
     * 
     * @return A temporary file; never {@code null}.
     */
    /* @NonNull */
    private static File createTemporaryFile()
    {
        try
        {
            final File temporaryFile = File.createTempFile( TableModelTest.class.getName(), null );
            temporaryFile.deleteOnExit();
            return temporaryFile;
        }
        catch( final IOException e )
        {
            throw new AssertionError( "Failed to create temporary file." ); //$NON-NLS-1$
        }
    }

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment.
     * 
     * @return A new component; never {@code null}.
     */
    /* @NonNull */
    private IComponent createUniqueComponent()
    {
        return TestComponents.createUniqueComponent( model_.getTable().getTableEnvironment() );
    }

    /**
     * Creates a new container with unique attributes using the fixture table
     * environment.
     * 
     * @return A new container; never {@code null}.
     */
    /* @NonNull */
    private IContainer createUniqueContainer()
    {
        return TestComponents.createUniqueContainer( model_.getTable().getTableEnvironment() );
    }

    /**
     * Fires a table changed event for the table model under test in the
     * fixture.
     */
    private void fireTableChangedEvent()
    {
        fireTableModelEvent( "fireTableChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires a table model dirty flag changed event for the table model under
     * test in the fixture.
     */
    private void fireTableModelDirtyFlagChangedEvent()
    {
        fireTableModelEvent( "fireTableModelDirtyFlagChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires the specified table model event for the table model under test in
     * the fixture.
     * 
     * @param methodName
     *        The name of the method that fires the table model event; must not
     *        be {@code null}.
     */
    private void fireTableModelEvent(
        /* @NonNull */
        final String methodName )
    {
        assert methodName != null;

        try
        {
            final Method method = TableModel.class.getDeclaredMethod( methodName );
            method.setAccessible( true );
            method.invoke( model_ );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Fires a table model file changed event for the table model under test in
     * the fixture.
     */
    private void fireTableModelFileChangedEvent()
    {
        fireTableModelEvent( "fireTableModelFileChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires a table model focus changed event for the table model under test in
     * the fixture.
     */
    private void fireTableModelFocusChangedEvent()
    {
        fireTableModelEvent( "fireTableModelFocusChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires a table model origin offset changed event for the table model under
     * test in the fixture.
     */
    private void fireTableModelOriginOffsetChangedEvent()
    {
        fireTableModelEvent( "fireTableModelOriginOffsetChanged" ); //$NON-NLS-1$
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
        niceMocksControl_ = EasyMock.createNiceControl();
        model_ = new TableModel();
    }

    /**
     * Ensures the {@link TableModel#addTableModelListener} method adds a
     * listener that is absent from the table model listener collection.
     */
    @Test
    public void testAddTableModelListener_Listener_Absent()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();

        fireTableChangedEvent();
        model_.addTableModelListener( listener );
        fireTableChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#addTableModelListener} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddTableModelListener_Listener_Null()
    {
        model_.addTableModelListener( null );
    }

    /**
     * Ensures the {@link TableModel#addTableModelListener} method throws an
     * exception when passed a listener that is present in the table model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddTableModelListener_Listener_Present()
    {
        final ITableModelListener listener = EasyMock.createMock( ITableModelListener.class );
        model_.addTableModelListener( listener );

        model_.addTableModelListener( listener );
    }

    /**
     * Ensures a change to a container associated with a container model owned
     * by the table model fires a table model dirty flag changed event.
     */
    @Test
    public void testContainerModel_ContainerChanged_FiresTableModelDirtyFlagChangedEvent()
    {
        final IComponent component = createUniqueContainer();
        model_.getTable().getTabletop().addComponent( component );
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelDirtyFlagChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        component.setLocation( new Point( 1000, 1000 ) );

        niceMocksControl_.verify();
    }

    /**
     * Ensures a change to a container associated with a container model owned
     * by the table model fires a table changed event.
     */
    @Test
    public void testContainerModel_ContainerChanged_FiresTableChangedEvent()
    {
        final IComponent component = createUniqueContainer();
        model_.getTable().getTabletop().addComponent( component );
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        component.setLocation( new Point( 1000, 1000 ) );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#getComponentModel} method throws an
     * exception when passed a path that is absent.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetComponentModel_Path_Absent()
    {
        model_.getComponentModel( new ComponentPath( null, 1 ) );
    }

    /**
     * Ensures the {@link TableModel#getComponentModel} method throws an
     * exception when passed a {@code null} path.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentModel_Path_Null()
    {
        model_.getComponentModel( null );
    }

    /**
     * Ensures the {@link TableModel#getComponentModel} method returns the
     * correct component model when passed a path that is present.
     */
    @Test
    public void testGetComponentModel_Path_Present()
    {
        final IContainer expectedContainer = createUniqueContainer();
        model_.getTable().getTabletop().addComponent( expectedContainer );
        expectedContainer.addComponent( createUniqueComponent() );
        expectedContainer.addComponent( createUniqueComponent() );
        final IComponent expectedComponent = createUniqueComponent();
        expectedContainer.addComponent( expectedComponent );

        final ComponentModel actualContainerModel = model_.getComponentModel( expectedContainer.getPath() );
        final ComponentModel actualComponentModel = model_.getComponentModel( expectedComponent.getPath() );

        assertSame( expectedContainer, actualContainerModel.getComponent() );
        assertSame( expectedComponent, actualComponentModel.getComponent() );
    }

    /**
     * Ensures the {@link TableModel#getFocusableComponent} method returns the
     * expected component when a focusable component exists at the specified
     * location.
     */
    @Test
    public void testGetFocusableComponent_Location_FocusableComponent()
    {
        final IComponent expectedComponent = createUniqueContainer();
        model_.getTable().getTabletop().addComponent( expectedComponent );

        final IComponent actualComponent = model_.getFocusableComponent( new Point( 0, 0 ) );

        assertSame( expectedComponent, actualComponent );
    }

    /**
     * Ensures the {@link TableModel#getFocusableComponent} method returns
     * {@code null} when no component exists at the specified location.
     */
    @Test
    public void testGetFocusableComponent_Location_NoComponent()
    {
        assertNull( model_.getFocusableComponent( new Point( Integer.MIN_VALUE, Integer.MIN_VALUE ) ) );
    }

    /**
     * Ensures the {@link TableModel#getFocusableComponent} method throws an
     * exception when passed a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testGetFocusableComponent_Location_Null()
    {
        model_.getFocusableComponent( null );
    }

    /**
     * Ensures the {@link TableModel#getOriginOffset} method returns a copy of
     * the origin offset.
     */
    @Test
    public void testGetOriginOffset_ReturnValue_Copy()
    {
        final Dimension originOffset = model_.getOriginOffset();
        final Dimension expectedOriginOffset = new Dimension( originOffset );
        originOffset.setSize( expectedOriginOffset.width + 100, expectedOriginOffset.height + 200 );

        final Dimension actualOriginOffset = model_.getOriginOffset();

        assertEquals( expectedOriginOffset, actualOriginOffset );
    }

    /**
     * Ensures the {@link TableModel#getOriginOffset} method does not return
     * {@code null}.
     */
    @Test
    public void testGetOriginOffset_ReturnValue_NonNull()
    {
        assertNotNull( model_.getOriginOffset() );
    }

    /**
     * Ensures the {@link TableModel#getTable} method does not return
     * {@code null}.
     */
    @Test
    public void testGetTable_ReturnValue_NonNull()
    {
        assertNotNull( model_.getTable() );
    }

    /**
     * Ensures the {@link TableModel#open()} method fires a table changed event.
     */
    @Test
    public void testOpen_FiresTableChangedEvent()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.open();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#open()} method fires a table model dirty
     * flag changed event.
     */
    @Test
    public void testOpen_FiresTableModelDirtyFlagChangedEvent()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelDirtyFlagChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.open();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#open()} method fires a table model file
     * changed event.
     */
    @Test
    public void testOpen_FiresTableModelFileChangedEvent()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelFileChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.open();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#open()} method fires a table model focus
     * changed event.
     */
    @Test
    public void testOpen_FiresTableModelFocusChangedEvent()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelFocusChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.open();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#open()} method fires a table model origin
     * offset changed event.
     */
    @Test
    public void testOpen_FiresTableModelOriginOffsetChangedEvent()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelOriginOffsetChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.open();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#open(File)} method fires a table changed
     * event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenFromFile_FiresTableChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.save( file );
        model_.addTableModelListener( listener );

        model_.open( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#open(File)} method fires a table model
     * dirty flag changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenFromFile_FiresTableModelDirtyFlagChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelDirtyFlagChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.save( file );
        model_.addTableModelListener( listener );

        model_.open( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#open(File)} method fires a table model file
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenFromFile_FiresTableModelFileChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelFileChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.save( file );
        model_.addTableModelListener( listener );

        model_.open( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#open(File)} method fires a table model
     * focus changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenFromFile_FiresTableModelFocusChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelFocusChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.save( file );
        model_.addTableModelListener( listener );

        model_.open( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#open(File)} method fires a table model
     * origin offset changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenFromFile_FiresTableModelOriginOffsetChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelOriginOffsetChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.save( file );
        model_.addTableModelListener( listener );

        model_.open( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures a table model focus changed event is fired if the component with
     * the focus is removed from the table.
     */
    @Test
    public void testRemoveFocusedComponent_FiresTableModelFocusChangedEvent()
    {
        final IComponent component = createUniqueContainer();
        model_.getTable().getTabletop().addComponent( component );
        model_.setFocus( component );
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelFocusChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.getTable().getTabletop().removeComponent( component );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#removeTableModelListener} method throws an
     * exception when passed a listener that is absent from the table model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveTableModelListener_Listener_Absent()
    {
        model_.removeTableModelListener( EasyMock.createMock( ITableModelListener.class ) );
    }

    /**
     * Ensures the {@link TableModel#removeTableModelListener} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveTableModelListener_Listener_Null()
    {
        model_.removeTableModelListener( null );
    }

    /**
     * Ensures the {@link TableModel#removeTableModelListener} removes a
     * listener that is present in the table model listener collection.
     */
    @Test
    public void testRemoveTableModelListener_Listener_Present()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        fireTableChangedEvent();
        model_.removeTableModelListener( listener );
        fireTableChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#save} method fires a table model dirty flag
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSave_FiresTableModelDirtyFlagChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelDirtyFlagChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.save( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#save} method fires a table model file
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSave_FiresTableModelFileChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelFileChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.save( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#setFocus} method correctly changes the
     * focus.
     */
    @Test
    public void testSetFocus()
    {
        final IContainer container1 = createUniqueContainer();
        model_.getTable().getTabletop().addComponent( container1 );
        final IContainer container2 = createUniqueContainer();
        model_.getTable().getTabletop().addComponent( container2 );

        model_.setFocus( container1 );
        assertTrue( model_.getComponentModel( container1.getPath() ).isFocused() );
        assertFalse( model_.getComponentModel( container2.getPath() ).isFocused() );
        model_.setFocus( container2 );
        assertFalse( model_.getComponentModel( container1.getPath() ).isFocused() );
        assertTrue( model_.getComponentModel( container2.getPath() ).isFocused() );
    }

    /**
     * Ensures the {@link TableModel#setFocus} method does not throw an
     * exception when passed a {@code null} container.
     */
    @Test
    public void testSetFocus_Container_Null()
    {
        model_.setFocus( null );
    }

    /**
     * Ensures the {@link TableModel#setFocus} method fires a table model focus
     * changed event.
     */
    @Test
    public void testSetFocus_FiresTableModelFocusChangedEvent()
    {
        final IContainer container = createUniqueContainer();
        model_.getTable().getTabletop().addComponent( container );
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelFocusChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.setFocus( container );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#setOriginOffset} method fires a table model
     * origin offset changed event.
     */
    @Test
    public void testSetOriginOffset_FiresTableModelOriginOffsetChangedEvent()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelOriginOffsetChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.setOriginOffset( new Dimension( 100, 200 ) );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@link TableModel#setOriginOffset} method throws an exception
     * when passed a {@code null} origin offset.
     */
    @Test( expected = NullPointerException.class )
    public void testSetOriginOffset_OriginOffset_Null()
    {
        model_.setOriginOffset( null );
    }

    /**
     * Ensures a change to the underlying table state fires a table changed
     * event.
     */
    @Test
    public void testTable_StateChanged_FiresTableChangedEvent()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.getTable().getTabletop().addComponent( createUniqueContainer() );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table changed event catches any exception thrown by the
     * {@link ITableModelListener#tableChanged} method of a table model
     * listener.
     */
    @Test
    public void testTableChanged_CatchesListenerException()
    {
        final ITableModelListener listener1 = niceMocksControl_.createMock( ITableModelListener.class );
        listener1.tableChanged( EasyMock.notNull( TableModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableModelListener listener2 = niceMocksControl_.createMock( ITableModelListener.class );
        listener2.tableChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener1 );
        model_.addTableModelListener( listener2 );

        fireTableChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table model dirty flag changed event catches any exception
     * thrown by the {@link ITableModelListener#tableModelDirtyFlagChanged}
     * method of a table model listener.
     */
    @Test
    public void testTableModelDirtyFlagChanged_CatchesListenerException()
    {
        final ITableModelListener listener1 = niceMocksControl_.createMock( ITableModelListener.class );
        listener1.tableModelDirtyFlagChanged( EasyMock.notNull( TableModelEvent.class ) );
        final ITableModelListener listener2 = niceMocksControl_.createMock( ITableModelListener.class );
        listener2.tableModelDirtyFlagChanged( EasyMock.notNull( TableModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener1 );
        model_.addTableModelListener( listener2 );

        fireTableModelDirtyFlagChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table model file changed event catches any exception thrown
     * by the {@link ITableModelListener#tableModelFileChanged} method of a
     * table model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testTableModelFileChanged_CatchesListenerException()
        throws Exception
    {
        final ITableModelListener listener1 = niceMocksControl_.createMock( ITableModelListener.class );
        listener1.tableModelFileChanged( EasyMock.notNull( TableModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableModelListener listener2 = niceMocksControl_.createMock( ITableModelListener.class );
        listener2.tableModelFileChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener1 );
        model_.addTableModelListener( listener2 );

        fireTableModelFileChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table model focus changed event catches any exception thrown
     * by the {@link ITableModelListener#tableModelFocusChanged} method of a
     * table model listener.
     */
    @Test
    public void testTableModelFocusChanged_CatchesListenerException()
    {
        final ITableModelListener listener1 = niceMocksControl_.createMock( ITableModelListener.class );
        listener1.tableModelFocusChanged( EasyMock.notNull( TableModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableModelListener listener2 = niceMocksControl_.createMock( ITableModelListener.class );
        listener2.tableModelFocusChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener1 );
        model_.addTableModelListener( listener2 );

        fireTableModelFocusChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table model origin offset changed event catches any exception
     * thrown by the {@link ITableModelListener#tableModelOriginOffsetChanged}
     * method of a table model listener.
     */
    @Test
    public void testTableModelOriginOffsetChanged_CatchesListenerException()
    {
        final ITableModelListener listener1 = niceMocksControl_.createMock( ITableModelListener.class );
        listener1.tableModelOriginOffsetChanged( EasyMock.notNull( TableModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final ITableModelListener listener2 = niceMocksControl_.createMock( ITableModelListener.class );
        listener2.tableModelOriginOffsetChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener1 );
        model_.addTableModelListener( listener2 );

        fireTableModelOriginOffsetChangedEvent();

        niceMocksControl_.verify();
    }
}
