/*
 * JoinTableNetworkWizard.java
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
 * Created on Oct 8, 2010 at 9:52:00 PM.
 */

package org.gamegineer.table.internal.ui.impl.wizards.jointablenetwork;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import javax.swing.SwingUtilities;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.common.ui.operation.RunnableTask;
import org.gamegineer.common.ui.wizard.AbstractWizard;
import org.gamegineer.common.ui.wizard.IWizardContainer;
import org.gamegineer.table.internal.ui.impl.Loggers;
import org.gamegineer.table.internal.ui.impl.model.TableModel;
import org.gamegineer.table.internal.ui.impl.util.OptionDialogs;
import org.gamegineer.table.net.ITableNetwork;
import org.gamegineer.table.net.TableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkConfigurationBuilder;
import org.gamegineer.table.net.TableNetworkException;

/**
 * The join table network wizard.
 */
@NotThreadSafe
public final class JoinTableNetworkWizard
    extends AbstractWizard
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The connection state. */
    private ConnectionState connectionState_;

    /** The wizard model. */
    private final Model model_;

    /** The table model associated with the wizard. */
    private final TableModel tableModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code JoinTableNetworkWizard} class.
     * 
     * @param tableModel
     *        The table model associated with the wizard; must not be
     *        {@code null}.
     */
    public JoinTableNetworkWizard(
        final TableModel tableModel )
    {
        connectionState_ = ConnectionState.DISCONNECTED;
        model_ = new Model();
        tableModel_ = tableModel;

        setTitle( NlsMessages.JoinTableNetworkWizard_title );
        setNeedsProgressMonitor( true );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.wizard.AbstractWizard#addPages()
     */
    @Override
    public void addPages()
    {
        addPage( new MainPage() );
    }

    /*
     * @see org.gamegineer.common.ui.wizard.AbstractWizard#dispose()
     */
    @Override
    public void dispose()
    {
        model_.dispose();

        super.dispose();
    }

    /**
     * Gets the wizard model.
     * 
     * @return The wizard model; never {@code null}.
     */
    Model getModel()
    {
        return model_;
    }

    /**
     * Gets the table network configuration.
     * 
     * @return The table network configuration; never {@code null}.
     */
    private TableNetworkConfiguration getTableNetworkConfiguration()
    {
        final TableNetworkConfigurationBuilder configurationBuilder = new TableNetworkConfigurationBuilder( tableModel_.getTable() );
        configurationBuilder.setHostName( model_.getHostName() );
        configurationBuilder.setLocalPlayerName( model_.getPlayerName() );
        configurationBuilder.setPassword( model_.getPassword() );
        configurationBuilder.setPort( model_.getPort() );
        return configurationBuilder.toTableNetworkConfiguration();
    }

    /*
     * @see org.gamegineer.common.ui.wizard.AbstractWizard#performFinish()
     */
    @Override
    public boolean performFinish()
    {
        if( connectionState_ == ConnectionState.DISCONNECTED )
        {
            final ITableNetwork tableNetwork = tableModel_.getTableNetwork();
            final TableNetworkConfiguration configuration = getTableNetworkConfiguration();
            connectionState_ = ConnectionState.CONNECTING;
            final IWizardContainer container = getContainer();
            assert container != null;
            container.executeTask( new RunnableTask<ConnectionState, Void>()
            {
                @Override
                protected ConnectionState doInBackground()
                    throws Exception
                {
                    setDescription( NlsMessages.JoinTableNetworkWizard_description_connecting );
                    setProgressIndeterminate( true );
                    tableNetwork.join( configuration );
                    return ConnectionState.CONNECTED;
                }

                @Override
                @SuppressWarnings( "synthetic-access" )
                protected void done()
                {
                    ConnectionState newState = ConnectionState.DISCONNECTED;
                    try
                    {
                        newState = get();
                        if( newState == ConnectionState.CONNECTED )
                        {
                            container.finish();
                        }
                    }
                    catch( @SuppressWarnings( "unused" ) final CancellationException e )
                    {
                        // do nothing
                    }
                    catch( final ExecutionException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.JoinTableNetworkWizard_finish_error, e );
                        final Throwable cause = e.getCause();
                        if( cause instanceof TableNetworkException )
                        {
                            showErrorMessageDialogLater( NlsMessages.JoinTableNetworkWizard_finish_error( ((TableNetworkException)cause).getError() ) );
                        }
                        else
                        {
                            throw TaskUtils.launderThrowable( cause );
                        }
                    }
                    catch( final InterruptedException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.JoinTableNetworkWizard_finish_interrupted, e );
                        showErrorMessageDialogLater( NlsMessages.JoinTableNetworkWizard_finish_interrupted );
                    }
                    finally
                    {
                        assert newState != null;
                        connectionState_ = newState;
                    }
                }
            } );
        }

        return connectionState_ == ConnectionState.CONNECTED;
    }

    /**
     * Shows an error message dialog at the next available opportunity.
     * 
     * @param message
     *        The error message; must not be {@code null}.
     */
    private void showErrorMessageDialogLater(
        final String message )
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            public void run()
            {
                final IWizardContainer container = getContainer();
                assert container != null;
                OptionDialogs.showErrorMessageDialog( container.getShell(), message );
            }
        } );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The connection state.
     */
    private enum ConnectionState
    {
        /** The wizard is disconnected. */
        DISCONNECTED,

        /** The wizard is connecting. */
        CONNECTING,

        /** The wizard is connected. */
        CONNECTED;
    }
}
