/*
 * MainFrame.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Sep 18, 2009 at 10:09:51 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.IPredicate;
import org.gamegineer.common.ui.databinding.swing.SwingRealm;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.action.ActionMediator;
import org.gamegineer.table.internal.ui.model.FramePreferences;
import org.gamegineer.table.internal.ui.model.IMainModelListener;
import org.gamegineer.table.internal.ui.model.ITableModelListener;
import org.gamegineer.table.internal.ui.model.MainModel;
import org.gamegineer.table.internal.ui.model.MainModelContentChangedEvent;
import org.gamegineer.table.internal.ui.model.MainModelEvent;
import org.gamegineer.table.internal.ui.model.ModelException;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.internal.ui.model.TableModelEvent;
import org.gamegineer.table.internal.ui.util.OptionDialogs;
import org.gamegineer.table.internal.ui.util.swing.JFileChooser;
import org.gamegineer.table.ui.ITableAdvisor;
import org.osgi.framework.Bundle;

/**
 * The top-level frame.
 */
@NotThreadSafe
public final class MainFrame
    extends JFrame
    implements IMainModelListener, ITableModelListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 1087139002992381995L;

    /** The action mediator. */
    private final ActionMediator actionMediator_;

    /** The model. */
    private final MainModel model_;

    /** The main view. */
    private final MainView mainView_;

    /** The menu bar view. */
    private final MenuBarView menuBarView_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainFrame} class.
     * 
     * @param advisor
     *        The table advisor; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code advisor} is {@code null}.
     */
    public MainFrame(
        /* @NonNull */
        final ITableAdvisor advisor )
    {
        assertArgumentNotNull( advisor, "advisor" ); //$NON-NLS-1$

        actionMediator_ = new ActionMediator();
        model_ = new MainModel( advisor );
        mainView_ = new MainView( model_ );
        menuBarView_ = new MenuBarView( model_ );

        initializeComponent();

        loadApplicationState();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.awt.Frame#addNotify()
     */
    @Override
    public void addNotify()
    {
        super.addNotify();

        SwingRealm.installSystemRealm();

        bindActions();
        model_.addMainModelListener( this );
        model_.openTable();
    }

    /**
     * Binds the action attachments for this component.
     */
    private void bindActions()
    {
        actionMediator_.bindActionListener( Actions.getExitAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                if( confirmSaveDirtyTable() )
                {
                    dispose();
                }
            }
        } );
        actionMediator_.bindActionListener( Actions.getOpenAboutDialogAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                JOptionPane.showMessageDialog( MainFrame.this, NlsMessages.AboutDialog_message( model_.getVersion() ), NlsMessages.AboutDialog_title, JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE );
            }
        } );
        actionMediator_.bindActionListener( Actions.getOpenNewTableAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                openNewTable();
            }
        } );
        actionMediator_.bindActionListener( Actions.getOpenTableAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                final ActionEvent event )
            {
                openTable( event.getActionCommand() );
            }
        } );
        actionMediator_.bindActionListener( Actions.getSaveTableAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                saveTable( false );
            }
        } );
        actionMediator_.bindActionListener( Actions.getSaveTableAsAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                saveTable( true );
            }
        } );

        final IPredicate<Action> isTableDirtyPredicate = new IPredicate<Action>()
        {
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return isTableDirty();
            }
        };
        final IPredicate<Action> isTableEditablePredicate = new IPredicate<Action>()
        {
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return isTableEditable();
            }
        };
        actionMediator_.bindShouldEnablePredicate( Actions.getOpenNewTableAction(), isTableEditablePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getOpenTableAction(), isTableEditablePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSaveTableAction(), isTableDirtyPredicate );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#cardPileFocusChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void cardPileFocusChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /**
     * Prompts the user to save the active table if it is dirty.
     * 
     * <p>
     * If this method returns {@code false}, the caller should not proceed to
     * discard the current table.
     * </p>
     * 
     * @return {@code true} if the table is not dirty, the user chooses not to
     *         save the dirty table, or the dirty table is saved successfully;
     *         {@code false} if the user wishes to abort the current operation
     *         or the dirty table was not saved successfully.
     */
    private boolean confirmSaveDirtyTable()
    {
        if( !isTableDirty() )
        {
            return true;
        }

        final int result = OptionDialogs.showConfirmDialog( this, NlsMessages.MainFrame_confirmSaveDirtyTable_message( getTableName() ) );
        if( result == JOptionPane.YES_OPTION )
        {
            return saveTable( false );
        }
        else if( result == JOptionPane.NO_OPTION )
        {
            return true;
        }

        return false; // JOptionPane.CANCEL_OPTION
    }

    /*
     * @see java.awt.Window#dispose()
     */
    @Override
    public void dispose()
    {
        model_.getTableModel().getTableNetwork().disconnect();

        super.dispose();
    }

    /**
     * Gets the collection of application icon images.
     * 
     * @return The collection of application icon images; never {@code null}.
     */
    /* @NonNull */
    private static List<Image> getApplicationIconImages()
    {
        final String[] imagePaths = new String[] { //
            "/icons/application-16.png", //$NON-NLS-1$
            "/icons/application-32.png", //$NON-NLS-1$
            "/icons/application-48.png", //$NON-NLS-1$
            "/icons/application-64.png" //$NON-NLS-1$
        };

        final Bundle bundle = Activator.getDefault().getBundleContext().getBundle();
        final List<Image> iconImages = new ArrayList<Image>();
        for( final String imagePath : imagePaths )
        {
            try
            {
                final URL url = bundle.getEntry( imagePath );
                assert url != null;
                iconImages.add( ImageIO.read( url ) );
            }
            catch( final IOException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.MainFrame_getApplicationIconImages_readImageError( imagePath ), e );
            }
        }
        return iconImages;
    }

    /**
     * Gets the file associated with the table.
     * 
     * @return The file associated with the table; never {@code null}.
     */
    /* @NonNull */
    private File getTableFile()
    {
        final TableModel tableModel = model_.getTableModel();
        if( tableModel == null )
        {
            return null;
        }

        return tableModel.getFile();
    }

    /**
     * Gets the name of the table.
     * 
     * @return The name of the table; never {@code null}.
     */
    /* @NonNull */
    private String getTableName()
    {
        final File file = getTableFile();
        if( file == null )
        {
            return NlsMessages.MainFrame_untitledTable;
        }

        return file.getName();
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        setJMenuBar( menuBarView_.getMenuBar() );

        setContentPane( mainView_ );

        setLocationByPlatform( true );
        setSize( 640, 480 );
        setIconImages( getApplicationIconImages() );
        updateTitle();
    }

    /**
     * Indicates the table is dirty.
     * 
     * @return {@code true} if the table is dirty; otherwise {@code false}.
     */
    private boolean isTableDirty()
    {
        final TableModel tableModel = model_.getTableModel();
        if( tableModel == null )
        {
            return false;
        }

        return tableModel.isDirty();
    }

    /**
     * Indicates the table is editable.
     * 
     * @return {@code true} if the table is editable; otherwise {@code false}.
     */
    private boolean isTableEditable()
    {
        final TableModel tableModel = model_.getTableModel();
        if( tableModel == null )
        {
            return false;
        }

        return !tableModel.getTableNetwork().isConnected();
    }

    /**
     * Loads the global application state.
     */
    private void loadApplicationState()
    {
        model_.load();

        loadPreferences();
    }

    /**
     * Loads the preferences for this class.
     */
    @SuppressWarnings( "boxing" )
    private void loadPreferences()
    {
        final FramePreferences framePreferences = model_.getPreferencesModel().getFramePreferences();

        final Point location = framePreferences.getLocation();
        if( location != null )
        {
            setLocation( location );
        }

        final Dimension size = framePreferences.getSize();
        if( size != null )
        {
            setSize( size );
        }

        final Integer state = framePreferences.getState();
        if( state != null )
        {
            setExtendedState( state );
        }
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IMainModelListener#mainModelStateChanged(org.gamegineer.table.internal.ui.model.MainModelEvent)
     */
    @Override
    public void mainModelStateChanged(
        final MainModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        Actions.updateAll();
    }

    /**
     * Opens a new empty table.
     */
    private void openNewTable()
    {
        if( confirmSaveDirtyTable() )
        {
            model_.openTable();
        }
    }

    /**
     * Opens the an existing table from the specified file.
     * 
     * @param fileName
     *        The name of the file from which the table will be opened; must not
     *        be {@code null}. Pass an empty string to prompt the user for a
     *        file name.
     */
    private void openTable(
        /* @NonNull */
        final String fileName )
    {
        assert fileName != null;

        if( !confirmSaveDirtyTable() )
        {
            return;
        }

        final File file;
        if( fileName.isEmpty() )
        {
            final JFileChooser fileChooser = FileChoosers.getTableFileChooser( getTableFile() );
            if( fileChooser.showOpenDialog( this ) == JFileChooser.CANCEL_OPTION )
            {
                return;
            }

            file = fileChooser.getSelectedFile();
        }
        else
        {
            file = new File( fileName );
        }

        try
        {
            model_.openTable( file );
        }
        catch( final ModelException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.MainFrame_openTable_error( file ), e );
            OptionDialogs.showErrorMessageDialog( this, NlsMessages.MainFrame_openTable_error );
        }
    }

    /*
     * @see javax.swing.JFrame#processWindowEvent(java.awt.event.WindowEvent)
     */
    @Override
    protected void processWindowEvent(
        final WindowEvent event )
    {
        switch( event.getID() )
        {
            case WindowEvent.WINDOW_CLOSED:
                saveApplicationState();
                break;

            case WindowEvent.WINDOW_CLOSING:
                if( !confirmSaveDirtyTable() )
                {
                    return;
                }
                break;
        }

        super.processWindowEvent( event );
    }

    /*
     * @see java.awt.Frame#removeNotify()
     */
    @Override
    public void removeNotify()
    {
        model_.removeMainModelListener( this );
        actionMediator_.unbindAll();

        SwingRealm.uninstallSystemRealm();

        super.removeNotify();
    }

    /**
     * Saves the global application state.
     */
    private void saveApplicationState()
    {
        savePreferences();

        model_.save();
    }

    /**
     * Saves the preferences for this class.
     */
    @SuppressWarnings( "boxing" )
    private void savePreferences()
    {
        final FramePreferences framePreferences = model_.getPreferencesModel().getFramePreferences();
        framePreferences.setLocation( getLocation() );
        framePreferences.setSize( getSize() );
        framePreferences.setState( getExtendedState() );
    }

    /**
     * Saves the table state to a file.
     * 
     * @param forcePromptForFile
     *        Indicates the user should be forced to choose a file regardless of
     *        whether or not the table state is already associated with an
     *        existing file.
     * 
     * @return {@code true} if the table was saved; otherwise {@code false}.
     */
    private boolean saveTable(
        final boolean forcePromptForFile )
    {
        final File file;
        if( forcePromptForFile || (getTableFile() == null) )
        {
            final JFileChooser fileChooser = FileChoosers.getTableFileChooser( getTableFile() );
            if( fileChooser.showSaveDialog( this ) == JFileChooser.CANCEL_OPTION )
            {
                return false;
            }

            file = fileChooser.getSelectedFile();
        }
        else
        {
            file = getTableFile();
        }

        try
        {
            model_.saveTable( file );
        }
        catch( final ModelException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.MainFrame_saveTable_error( file ), e );
            OptionDialogs.showErrorMessageDialog( this, NlsMessages.MainFrame_saveTable_error );
            return false;
        }

        return true;
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IMainModelListener#tableClosed(org.gamegineer.table.internal.ui.model.MainModelContentChangedEvent)
     */
    @Override
    public void tableClosed(
        final MainModelContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                event.getTableModel().removeTableModelListener( MainFrame.this );
                updateTitle();
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableModelDirtyFlagChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableModelDirtyFlagChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableModelFileChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableModelFileChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                updateTitle();
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableModelStateChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableModelStateChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableOriginOffsetChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableOriginOffsetChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IMainModelListener#tableOpened(org.gamegineer.table.internal.ui.model.MainModelContentChangedEvent)
     */
    @Override
    public void tableOpened(
        final MainModelContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                event.getTableModel().addTableModelListener( MainFrame.this );
                updateTitle();
            }
        } );
    }

    /**
     * Updates the frame title based on the state of the model.
     */
    private void updateTitle()
    {
        setTitle( NlsMessages.MainFrame_title( getTableName() ) );
    }
}
