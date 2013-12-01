/*
 * Actions.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Oct 9, 2009 at 10:26:28 PM.
 */

package org.gamegineer.table.internal.ui.impl.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.KeyStroke;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.impl.ContainerLayoutIds;
import org.gamegineer.table.internal.ui.impl.action.BasicAction;

/**
 * The collection of actions available to all views.
 */
@ThreadSafe
final class Actions
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The identifier of the action used to add a component to the table. */
    private static final Object ADD_COMPONENT_ACTION_ID = "addComponentAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to cancel a table network control
     * request.
     */
    private static final Object CANCEL_TABLE_NETWORK_CONTROL_REQUEST_ID = "cancelTableNetworkControlRequestAction"; //$NON-NLS-1$

    /** The identifier of the action used to debug trace the table. */
    private static final Object DEBUG_TRACE_TABLE_ACTION_ID = "debugTraceTableAction"; //$NON-NLS-1$

    /** The identifier of the action used to debug trace the table model. */
    private static final Object DEBUG_TRACE_TABLE_MODEL_ACTION_ID = "debugTraceTableModelAction"; //$NON-NLS-1$

    /** The identifier of the action used to disconnect a table network. */
    private static final Object DISCONNECT_TABLE_NETWORK_ACTION_ID = "disconnectTableNetworkAction"; //$NON-NLS-1$

    /** The identifier of the action used to display the help system. */
    private static final Object DISPLAY_HELP_ACTION_ID = "displayHelpAction"; //$NON-NLS-1$

    /** The identifier of the action used to exit the application. */
    private static final Object EXIT_ACTION_ID = "exitAction"; //$NON-NLS-1$

    /** The identifier of the action used to flip a component. */
    private static final Object FLIP_COMPONENT_ACTION_ID = "flipComponentAction"; //$NON-NLS-1$

    /** The identifier of the action used to give table network control. */
    private static final Object GIVE_TABLE_NETWORK_CONTROL_ID = "giveTableNetworkControlAction"; //$NON-NLS-1$

    /** The identifier of the action used to host a table network. */
    private static final Object HOST_TABLE_NETWORK_ACTION_ID = "hostTableNetworkAction"; //$NON-NLS-1$

    /** The identifier of the action used to join a table network. */
    private static final Object JOIN_TABLE_NETWORK_ACTION_ID = "joinTableNetworkAction"; //$NON-NLS-1$

    /** The identifier of the action used to open the about dialog. */
    private static final Object OPEN_ABOUT_DIALOG_ACTION_ID = "openAboutDialogAction"; //$NON-NLS-1$

    /** The identifier of the action used to open a new table. */
    private static final Object OPEN_NEW_TABLE_ACTION_ID = "openNewTableAction"; //$NON-NLS-1$

    /** The identifier of the action used to open an existing table. */
    private static final Object OPEN_TABLE_ACTION_ID = "openTableAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to remove all components from a
     * container.
     */
    private static final Object REMOVE_ALL_COMPONENTS_ACTION_ID = "removeAllComponentsAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to remove a component from a container.
     */
    private static final Object REMOVE_COMPONENT_ACTION_ID = "removeComponentAction"; //$NON-NLS-1$

    /** The identifier of the action used to request table network control. */
    private static final Object REQUEST_TABLE_NETWORK_CONTROL_ID = "requestTableNetworkControlAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to reset the table origin to the view
     * origin.
     */
    private static final Object RESET_TABLE_ORIGIN_ACTION_ID = "resetTableOriginAction"; //$NON-NLS-1$

    /** The identifier of the action used to save a table. */
    private static final Object SAVE_TABLE_ACTION_ID = "saveTableAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to save a table to a different
     * location.
     */
    private static final Object SAVE_TABLE_AS_ACTION_ID = "saveTableAsAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian down layout on a
     * container.
     */
    private static final Object SET_ACCORDIAN_DOWN_CONTAINER_LAYOUT_ACTION_ID = "setAccordianDownContainerLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian left layout on a
     * container.
     */
    private static final Object SET_ACCORDIAN_LEFT_CONTAINER_LAYOUT_ACTION_ID = "setAccordianLeftContainerLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian right layout on a
     * container.
     */
    private static final Object SET_ACCORDIAN_RIGHT_CONTAINER_LAYOUT_ACTION_ID = "setAccordianRightContainerLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian up layout on a
     * container.
     */
    private static final Object SET_ACCORDIAN_UP_CONTAINER_LAYOUT_ACTION_ID = "setAccordianUpContainerLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the stacked layout on a
     * container.
     */
    private static final Object SET_STACKED_CONTAINER_LAYOUT_ACTION_ID = "setStackedContainerLayoutAction"; //$NON-NLS-1$

    /**
     * The collection of actions. The key is the action identifier. The value is
     * the action.
     */
    private static final Map<Object, BasicAction> actions_ = createActions();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Actions} class.
     */
    private Actions()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the collection of actions.
     * 
     * @return The collection of actions; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static Map<Object, BasicAction> createActions()
    {
        final Collection<BasicAction> actions = new ArrayList<>();

        actions.add( new BasicAction( ADD_COMPONENT_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "" ); //$NON-NLS-1$
            }
        } );
        actions.add( new BasicAction( CANCEL_TABLE_NETWORK_CONTROL_REQUEST_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.CancelTableNetworkControlRequestAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.CancelTableNetworkControlRequestAction_text );
            }
        } );
        actions.add( new BasicAction( DEBUG_TRACE_TABLE_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.DebugTraceTableAction_accelerator ) );
            }
        } );
        actions.add( new BasicAction( DEBUG_TRACE_TABLE_MODEL_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.DebugTraceTableModelAction_accelerator ) );
            }
        } );
        actions.add( new BasicAction( DISCONNECT_TABLE_NETWORK_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.DisconnectTableNetworkAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.DisconnectTableNetworkAction_text );
            }
        } );
        actions.add( new BasicAction( DISPLAY_HELP_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.DisplayHelpAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.DisplayHelpAction_text );
            }
        } );
        actions.add( new BasicAction( EXIT_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.ExitAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.ExitAction_text );
            }
        } );
        actions.add( new BasicAction( FLIP_COMPONENT_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.FlipComponentAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.FlipComponentAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.FlipComponentAction_text );
            }
        } );
        actions.add( new BasicAction( GIVE_TABLE_NETWORK_CONTROL_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.GiveTableNetworkControlAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.GiveTableNetworkControlAction_text );
            }
        } );
        actions.add( new BasicAction( HOST_TABLE_NETWORK_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.HostTableNetworkAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.HostTableNetworkAction_text );
            }
        } );
        actions.add( new BasicAction( JOIN_TABLE_NETWORK_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.JoinTableNetworkAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.JoinTableNetworkAction_text );
            }
        } );
        actions.add( new BasicAction( OPEN_ABOUT_DIALOG_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenAboutDialogAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.OpenAboutDialogAction_text() );
            }
        } );
        actions.add( new BasicAction( OPEN_NEW_TABLE_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenNewTableAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenNewTableAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.OpenNewTableAction_text );
            }
        } );
        actions.add( new BasicAction( OPEN_TABLE_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                // NB: Using an empty string for the command key to force the user to be
                // prompted for a file name.  Cannot use null because AbstractButton
                // substitutes the button text for the command key when the command key
                // is null.
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenTableAction_accelerator ) );
                putValue( ACTION_COMMAND_KEY, "" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenTableAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.OpenTableAction_text );
            }
        } );
        actions.add( new BasicAction( REMOVE_ALL_COMPONENTS_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveAllComponentsAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveAllComponentsAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RemoveAllComponentsAction_text );
            }
        } );
        actions.add( new BasicAction( REMOVE_COMPONENT_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveComponentAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveComponentAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RemoveComponentAction_text );
            }
        } );
        actions.add( new BasicAction( REQUEST_TABLE_NETWORK_CONTROL_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RequestTableNetworkControlAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RequestTableNetworkControlAction_text );
            }
        } );
        actions.add( new BasicAction( RESET_TABLE_ORIGIN_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.ResetTableOriginAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.ResetTableOriginAction_text );
            }
        } );
        actions.add( new BasicAction( SAVE_TABLE_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.SaveTableAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SaveTableAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SaveTableAction_text );
            }
        } );
        actions.add( new BasicAction( SAVE_TABLE_AS_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SaveTableAsAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SaveTableAsAction_text );
            }
        } );
        actions.add( new BasicAction( SET_ACCORDIAN_DOWN_CONTAINER_LAYOUT_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, ContainerLayoutIds.ACCORDIAN_DOWN.toString() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianDownContainerLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianDownContainerLayoutAction_text );
            }
        } );
        actions.add( new BasicAction( SET_ACCORDIAN_LEFT_CONTAINER_LAYOUT_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, ContainerLayoutIds.ACCORDIAN_LEFT.toString() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianLeftContainerLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianLeftContainerLayoutAction_text );
            }
        } );
        actions.add( new BasicAction( SET_ACCORDIAN_RIGHT_CONTAINER_LAYOUT_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, ContainerLayoutIds.ACCORDIAN_RIGHT.toString() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianRightContainerLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianRightContainerLayoutAction_text );
            }
        } );
        actions.add( new BasicAction( SET_ACCORDIAN_UP_CONTAINER_LAYOUT_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, ContainerLayoutIds.ACCORDIAN_UP.toString() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianUpContainerLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianUpContainerLayoutAction_text );
            }
        } );
        actions.add( new BasicAction( SET_STACKED_CONTAINER_LAYOUT_ACTION_ID )
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, ContainerLayoutIds.STACKED.toString() );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetStackedContainerLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetStackedContainerLayoutAction_text );
            }
        } );

        final Map<Object, BasicAction> actionMap = new HashMap<>( actions.size() );
        for( final BasicAction action : actions )
        {
            actionMap.put( action.getId(), action );
        }

        return Collections.unmodifiableMap( actionMap );
    }

    /**
     * Gets the add component action.
     * 
     * @return The add component action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddComponentAction()
    {
        return actions_.get( ADD_COMPONENT_ACTION_ID );
    }

    /**
     * Gets the cancel table network control request action.
     * 
     * @return The cancel table network control request action; never
     *         {@code null}.
     */
    /* @NonNull */
    static BasicAction getCancelTableNetworkControlRequestAction()
    {
        return actions_.get( CANCEL_TABLE_NETWORK_CONTROL_REQUEST_ID );
    }

    /**
     * Gets the debug trace table action.
     * 
     * @return The debug trace table action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getDebugTraceTableAction()
    {
        return actions_.get( DEBUG_TRACE_TABLE_ACTION_ID );
    }

    /**
     * Gets the debug trace table model action.
     * 
     * @return The debug trace table model action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getDebugTraceTableModelAction()
    {
        return actions_.get( DEBUG_TRACE_TABLE_MODEL_ACTION_ID );
    }

    /**
     * Gets the disconnect table network action.
     * 
     * @return The disconnect table network action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getDisconnectTableNetworkAction()
    {
        return actions_.get( DISCONNECT_TABLE_NETWORK_ACTION_ID );
    }

    /**
     * Gets the display help action.
     * 
     * @return The display help action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getDisplayHelpAction()
    {
        return actions_.get( DISPLAY_HELP_ACTION_ID );
    }

    /**
     * Gets the exit action.
     * 
     * @return The exit action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getExitAction()
    {
        return actions_.get( EXIT_ACTION_ID );
    }

    /**
     * Gets the flip component action.
     * 
     * @return The flip component action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getFlipComponentAction()
    {
        return actions_.get( FLIP_COMPONENT_ACTION_ID );
    }

    /**
     * Gets the give table network control action.
     * 
     * @return The give table network control action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getGiveTableNetworkControlAction()
    {
        return actions_.get( GIVE_TABLE_NETWORK_CONTROL_ID );
    }

    /**
     * Gets the host table network action.
     * 
     * @return The host table network action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getHostTableNetworkAction()
    {
        return actions_.get( HOST_TABLE_NETWORK_ACTION_ID );
    }

    /**
     * Gets the join table network action.
     * 
     * @return The join table network action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getJoinTableNetworkAction()
    {
        return actions_.get( JOIN_TABLE_NETWORK_ACTION_ID );
    }

    /**
     * Gets the open about dialog action.
     * 
     * @return The open about dialog action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getOpenAboutDialogAction()
    {
        return actions_.get( OPEN_ABOUT_DIALOG_ACTION_ID );
    }

    /**
     * Gets the open new table action.
     * 
     * @return The open new table action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getOpenNewTableAction()
    {
        return actions_.get( OPEN_NEW_TABLE_ACTION_ID );
    }

    /**
     * Gets the open table action.
     * 
     * @return The open table action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getOpenTableAction()
    {
        return actions_.get( OPEN_TABLE_ACTION_ID );
    }

    /**
     * Gets the remove all components action.
     * 
     * @return The remove all components action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveAllComponentsAction()
    {
        return actions_.get( REMOVE_ALL_COMPONENTS_ACTION_ID );
    }

    /**
     * Gets the remove component action.
     * 
     * @return The remove component action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveComponentAction()
    {
        return actions_.get( REMOVE_COMPONENT_ACTION_ID );
    }

    /**
     * Gets the request table network control action.
     * 
     * @return The request table network control action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRequestTableNetworkControlAction()
    {
        return actions_.get( REQUEST_TABLE_NETWORK_CONTROL_ID );
    }

    /**
     * Gets the reset table origin action.
     * 
     * @return The reset table origin action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getResetTableOriginAction()
    {
        return actions_.get( RESET_TABLE_ORIGIN_ACTION_ID );
    }

    /**
     * Gets the save table action.
     * 
     * @return The save table action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getSaveTableAction()
    {
        return actions_.get( SAVE_TABLE_ACTION_ID );
    }

    /**
     * Gets the save table as action.
     * 
     * @return The save table as action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getSaveTableAsAction()
    {
        return actions_.get( SAVE_TABLE_AS_ACTION_ID );
    }

    /**
     * Gets the set accordian down container layout action.
     * 
     * @return The set accordian down container layout action; never
     *         {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianDownContainerLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_DOWN_CONTAINER_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set accordian left container layout action.
     * 
     * @return The set accordian left container layout action; never
     *         {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianLeftContainerLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_LEFT_CONTAINER_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set accordian right container layout action.
     * 
     * @return The set accordian right container layout action; never
     *         {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianRightContainerLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_RIGHT_CONTAINER_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set accordian up container layout action.
     * 
     * @return The set accordian up container layout action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianUpContainerLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_UP_CONTAINER_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set stacked container layout action.
     * 
     * @return The set stacked container layout action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetStackedContainerLayoutAction()
    {
        return actions_.get( SET_STACKED_CONTAINER_LAYOUT_ACTION_ID );
    }

    /**
     * Updates the state of all actions.
     */
    static void updateAll()
    {
        for( final BasicAction action : actions_.values() )
        {
            action.update();
        }
    }
}
