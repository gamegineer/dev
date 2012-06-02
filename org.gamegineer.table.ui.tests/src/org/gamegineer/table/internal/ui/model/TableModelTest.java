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
import static org.junit.Assert.assertTrue;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.CardPiles;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.IContainer;
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
     * Creates a new card pile with a unique base design for the fixture table.
     * 
     * @return A new card pile; never {@code null}.
     */
    /* @NonNull */
    private ICardPile createUniqueCardPile()
    {
        return CardPiles.createUniqueCardPile( model_.getTable().getTableEnvironment() );
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
     * Ensures the {@code addTableModelListener} method adds a listener that is
     * absent from the table model listener collection.
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
     * Ensures the {@code addTableModelListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddTableModelListener_Listener_Null()
    {
        model_.addTableModelListener( null );
    }

    /**
     * Ensures the {@code addTableModelListener} method throws an exception when
     * passed a listener that is present in the table model listener collection.
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
        final ICardPile cardPile = createUniqueCardPile();
        model_.getTable().addCardPile( cardPile );
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelDirtyFlagChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        cardPile.setLocation( new Point( 1000, 1000 ) );

        niceMocksControl_.verify();
    }

    /**
     * Ensures a change to a container associated with a container model owned
     * by the table model fires a table changed event.
     */
    @Test
    public void testContainerModel_ContainerChanged_FiresTableChangedEvent()
    {
        final ICardPile cardPile = createUniqueCardPile();
        model_.getTable().addCardPile( cardPile );
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        cardPile.setLocation( new Point( 1000, 1000 ) );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code getContainerModel} throws an exception when passed a
     * container that is absent from the table.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetContainerModel_Container_Absent()
    {
        model_.getContainerModel( EasyMock.createMock( IContainer.class ) );
    }

    /**
     * Ensures the {@code getContainerModel} throws an exception when passed a
     * {@code null} container.
     */
    @Test( expected = NullPointerException.class )
    public void testGetContainerModel_Container_Null()
    {
        model_.getContainerModel( null );
    }

    /**
     * Ensures the {@code getOriginOffset} method returns a copy of the origin
     * offset.
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
     * Ensures the {@code getOriginOffset} method does not return {@code null}.
     */
    @Test
    public void testGetOriginOffset_ReturnValue_NonNull()
    {
        assertNotNull( model_.getOriginOffset() );
    }

    /**
     * Ensures the {@code getTable} method does not return {@code null}.
     */
    @Test
    public void testGetTable_ReturnValue_NonNull()
    {
        assertNotNull( model_.getTable() );
    }

    /**
     * Ensures the {@code open()} method fires a table changed event.
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
     * Ensures the {@code open()} method fires a table model dirty flag changed
     * event.
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
     * Ensures the {@code open()} method fires a table model file changed event.
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
     * Ensures the {@code open()} method fires a table model focus changed
     * event.
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
     * Ensures the {@code open()} method fires a table model origin offset
     * changed event.
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
     * Ensures the {@code open(File)} method fires a table changed event.
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
     * Ensures the {@code open(File)} method fires a table model dirty flag
     * changed event.
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
     * Ensures the {@code open(File)} method fires a table model file changed
     * event.
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
     * Ensures the {@code open(File)} method fires a table model focus changed
     * event.
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
     * Ensures the {@code open(File)} method fires a table model origin offset
     * changed event.
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
     * Ensures the {@code removeTableModelListener} method throws an exception
     * when passed a listener that is absent from the table model listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveTableModelListener_Listener_Absent()
    {
        model_.removeTableModelListener( EasyMock.createMock( ITableModelListener.class ) );
    }

    /**
     * Ensures the {@code removeTableModelListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveTableModelListener_Listener_Null()
    {
        model_.removeTableModelListener( null );
    }

    /**
     * Ensures the {@code removeTableModelListener} removes a listener that is
     * present in the table model listener collection.
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
     * Ensures the {@code save} method fires a table model dirty flag changed
     * event.
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
     * Ensures the {@code save} method fires a table model file changed event.
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
     * Ensures the {@code setFocus} method correctly changes the focus.
     */
    @Test
    public void testSetFocus()
    {
        final ICardPile cardPile1 = createUniqueCardPile();
        model_.getTable().addCardPile( cardPile1 );
        final ICardPile cardPile2 = createUniqueCardPile();
        model_.getTable().addCardPile( cardPile2 );

        model_.setFocus( cardPile1 );
        assertTrue( model_.getContainerModel( cardPile1 ).isFocused() );
        assertFalse( model_.getContainerModel( cardPile2 ).isFocused() );
        model_.setFocus( cardPile2 );
        assertFalse( model_.getContainerModel( cardPile1 ).isFocused() );
        assertTrue( model_.getContainerModel( cardPile2 ).isFocused() );
    }

    /**
     * Ensures the {@code setFocus} method does not throw an exception when
     * passed a {@code null} card pile.
     */
    @Test
    public void testSetFocus_CardPile_Null()
    {
        model_.setFocus( null );
    }

    /**
     * Ensures the {@code setFocus} method fires a table model focus changed
     * event.
     */
    @Test
    public void testSetFocus_FiresTableModelFocusChangedEvent()
    {
        final ICardPile cardPile = createUniqueCardPile();
        model_.getTable().addCardPile( cardPile );
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelFocusChanged( EasyMock.notNull( TableModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        model_.setFocus( cardPile );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code setOriginOffset} method fires a table model origin
     * offset changed event.
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
     * Ensures the {@code setOriginOffset} method throws an exception when
     * passed a {@code null} origin offset.
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

        model_.getTable().addCardPile( createUniqueCardPile() );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table changed event catches any exception thrown by the
     * {@code tableChanged} method of a table model listener.
     */
    @Test
    public void testTableChanged_CatchesListenerException()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableChanged( EasyMock.notNull( TableModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        fireTableChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table model dirty flag changed event catches any exception
     * thrown by the {@code tableModelDirtyFlagChanged} method of a table model
     * listener.
     */
    @Test
    public void testTableModelDirtyFlagChanged_CatchesListenerException()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelDirtyFlagChanged( EasyMock.notNull( TableModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        fireTableModelDirtyFlagChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table model file changed event catches any exception thrown
     * by the {@code tableModelFileChanged} method of a table model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testTableModelFileChanged_CatchesListenerException()
        throws Exception
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelFileChanged( EasyMock.notNull( TableModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        fireTableModelFileChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table model focus changed event catches any exception thrown
     * by the {@code tableModelFocusChanged} method of a table model listener.
     */
    @Test
    public void testTableModelFocusChanged_CatchesListenerException()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelFocusChanged( EasyMock.notNull( TableModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        fireTableModelFocusChangedEvent();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table model origin offset changed event catches any exception
     * thrown by the {@code tableModelOriginOffsetChanged} method of a table
     * model listener.
     */
    @Test
    public void testTableModelOriginOffsetChanged_CatchesListenerException()
    {
        final ITableModelListener listener = niceMocksControl_.createMock( ITableModelListener.class );
        listener.tableModelOriginOffsetChanged( EasyMock.notNull( TableModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addTableModelListener( listener );

        fireTableModelOriginOffsetChangedEvent();

        niceMocksControl_.verify();
    }
}
