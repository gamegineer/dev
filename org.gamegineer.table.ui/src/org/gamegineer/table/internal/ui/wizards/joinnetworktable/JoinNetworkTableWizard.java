/*
 * JoinNetworkTableWizard.java
 * Copyright 2008-2010 Gamegineer.org
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

package org.gamegineer.table.internal.ui.wizards.joinnetworktable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.operation.RunnableTask;
import org.gamegineer.common.ui.wizard.AbstractWizard;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.internal.ui.util.OptionDialogs;
import org.gamegineer.table.net.INetworkTable;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableConfigurationBuilder;

/**
 * The join network table wizard.
 */
@NotThreadSafe
public final class JoinNetworkTableWizard
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
     * Initializes a new instance of the {@code JoinNetworkTableWizard} class.
     * 
     * @param tableModel
     *        The table model associated with the wizard; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableModel} is {@code null}.
     */
    public JoinNetworkTableWizard(
        /* @NonNull */
        final TableModel tableModel )
    {
        assertArgumentNotNull( tableModel, "tableModel" ); //$NON-NLS-1$

        connectionState_ = ConnectionState.DISCONNECTED;
        model_ = new Model();
        tableModel_ = tableModel;

        setTitle( Messages.JoinNetworkTableWizard_title );
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
    /* @NonNull */
    Model getModel()
    {
        return model_;
    }

    /**
     * Gets the network table configuration.
     * 
     * @return The network table configuration; never {@code null}.
     */
    /* @NonNull */
    private INetworkTableConfiguration getNetworkTableConfiguration()
    {
        final NetworkTableConfigurationBuilder configurationBuilder = new NetworkTableConfigurationBuilder();
        configurationBuilder.setHostName( model_.getHostName() );
        configurationBuilder.setPort( model_.getPort() );
        configurationBuilder.setLocalPlayerName( model_.getPlayerName() );
        configurationBuilder.setPassword( model_.getPassword() );
        return configurationBuilder.toNetworkTableConfiguration();
    }

    /*
     * @see org.gamegineer.common.ui.wizard.AbstractWizard#performFinish()
     */
    @Override
    public boolean performFinish()
    {
        if( connectionState_ == ConnectionState.DISCONNECTED )
        {
            final INetworkTableConfiguration configuration = getNetworkTableConfiguration();
            connectionState_ = ConnectionState.CONNECTING;
            getContainer().executeTask( new RunnableTask<ConnectionState, Void>()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                protected ConnectionState doInBackground()
                    throws Exception
                {
                    setDescription( "Opening port..." ); //$NON-NLS-1$

                    for( int index = 0; index <= 100; ++index )
                    {
                        if( index == 33 )
                        {
                            setDescription( "Connecting..." ); //$NON-NLS-1$
                        }
                        else if( index == 67 )
                        {
                            setDescription( "Authenticating..." ); //$NON-NLS-1$
                        }

                        setProgress( index );
                        Thread.sleep( 50 );
                    }

                    final INetworkTable networkTable = tableModel_.getNetworkTable();
                    networkTable.endJoin( networkTable.beginJoin( configuration ) );
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
                            getContainer().finish();
                        }
                    }
                    catch( final CancellationException e )
                    {
                        // do nothing
                    }
                    catch( final ExecutionException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, Messages.JoinNetworkTableWizard_finish_error_nonNls, e );
                        OptionDialogs.showErrorMessageDialog( getContainer().getShell(), Messages.JoinNetworkTableWizard_finish_error );
                    }
                    catch( final InterruptedException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, Messages.JoinNetworkTableWizard_finish_interrupted_nonNls, e );
                        Thread.currentThread().interrupt();
                    }
                    finally
                    {
                        connectionState_ = newState;
                    }
                }
            } );
        }

        return connectionState_ == ConnectionState.CONNECTED;
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
