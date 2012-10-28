/*
 * Actions.java
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
 * Created on Oct 9, 2009 at 10:26:28 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.KeyStroke;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.ui.action.BasicAction;

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
    private static final String ADD_COMPONENT_ACTION_ID = "addComponentAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to cancel a table network control
     * request.
     */
    private static final String CANCEL_TABLE_NETWORK_CONTROL_REQUEST_ID = "cancelTableNetworkControlRequestAction"; //$NON-NLS-1$

    /** The identifier of the action used to disconnect a table network. */
    private static final String DISCONNECT_TABLE_NETWORK_ACTION_ID = "disconnectTableNetworkAction"; //$NON-NLS-1$

    /** The identifier of the action used to display the help system. */
    private static final String DISPLAY_HELP_ACTION_ID = "displayHelpAction"; //$NON-NLS-1$

    /** The identifier of the action used to exit the application. */
    private static final String EXIT_ACTION_ID = "exitAction"; //$NON-NLS-1$

    /** The identifier of the action used to flip a card in a card pile. */
    private static final String FLIP_CARD_ACTION_ID = "flipCardAction"; //$NON-NLS-1$

    /** The identifier of the action used to give table network control. */
    private static final String GIVE_TABLE_NETWORK_CONTROL_ID = "giveTableNetworkControlAction"; //$NON-NLS-1$

    /** The identifier of the action used to host a table network. */
    private static final String HOST_TABLE_NETWORK_ACTION_ID = "hostTableNetworkAction"; //$NON-NLS-1$

    /** The identifier of the action used to join a table network. */
    private static final String JOIN_TABLE_NETWORK_ACTION_ID = "joinTableNetworkAction"; //$NON-NLS-1$

    /** The identifier of the action used to open the about dialog. */
    private static final String OPEN_ABOUT_DIALOG_ACTION_ID = "openAboutDialogAction"; //$NON-NLS-1$

    /** The identifier of the action used to open a new table. */
    private static final String OPEN_NEW_TABLE_ACTION_ID = "openNewTableAction"; //$NON-NLS-1$

    /** The identifier of the action used to open an existing table. */
    private static final String OPEN_TABLE_ACTION_ID = "openTableAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to remove all card piles from the
     * table.
     */
    private static final String REMOVE_ALL_CARD_PILES_ACTION_ID = "removeAllCardPilesAction"; //$NON-NLS-1$

    /** The identifier of the action used to remove all cards from a card pile. */
    private static final String REMOVE_ALL_CARDS_ACTION_ID = "removeAllCardsAction"; //$NON-NLS-1$

    /** The identifier of the action used to remove a card from a card pile. */
    private static final String REMOVE_CARD_ACTION_ID = "removeCardAction"; //$NON-NLS-1$

    /** The identifier of the action used to remove a card pile from the table. */
    private static final String REMOVE_CARD_PILE_ACTION_ID = "removeCardPileAction"; //$NON-NLS-1$

    /** The identifier of the action used to request table network control. */
    private static final String REQUEST_TABLE_NETWORK_CONTROL_ID = "requestTableNetworkControlAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to reset the table origin to the view
     * origin.
     */
    private static final String RESET_TABLE_ORIGIN_ACTION_ID = "resetTableOriginAction"; //$NON-NLS-1$

    /** The identifier of the action used to save a table. */
    private static final String SAVE_TABLE_ACTION_ID = "saveTableAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to save a table to a different
     * location.
     */
    private static final String SAVE_TABLE_AS_ACTION_ID = "saveTableAsAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian down layout on a
     * card pile.
     */
    private static final String SET_ACCORDIAN_DOWN_CARD_PILE_LAYOUT_ACTION_ID = "setAccordianDownCardPileLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian left layout on a
     * card pile.
     */
    private static final String SET_ACCORDIAN_LEFT_CARD_PILE_LAYOUT_ACTION_ID = "setAccordianLeftCardPileLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian right layout on a
     * card pile.
     */
    private static final String SET_ACCORDIAN_RIGHT_CARD_PILE_LAYOUT_ACTION_ID = "setAccordianRightCardPileLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the accordian up layout on a
     * card pile.
     */
    private static final String SET_ACCORDIAN_UP_CARD_PILE_LAYOUT_ACTION_ID = "setAccordianUpCardPileLayoutAction"; //$NON-NLS-1$

    /**
     * The identifier of the action used to set the stacked layout on a card
     * pile.
     */
    private static final String SET_STACKED_CARD_PILE_LAYOUT_ACTION_ID = "setStackedCardPileLayoutAction"; //$NON-NLS-1$

    /** The collection of actions. */
    private static final Map<String, BasicAction> actions_ = createActions();


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
    private static Map<String, BasicAction> createActions()
    {
        final Map<String, BasicAction> actions = new HashMap<String, BasicAction>();

        actions.put( ADD_COMPONENT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "" ); //$NON-NLS-1$
            }
        } );
        actions.put( CANCEL_TABLE_NETWORK_CONTROL_REQUEST_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.CancelTableNetworkControlRequestAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.CancelTableNetworkControlRequestAction_text );
            }
        } );
        actions.put( DISCONNECT_TABLE_NETWORK_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.DisconnectTableNetworkAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.DisconnectTableNetworkAction_text );
            }
        } );
        actions.put( DISPLAY_HELP_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.DisplayHelpAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.DisplayHelpAction_text );
            }
        } );
        actions.put( EXIT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.ExitAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.ExitAction_text );
            }
        } );
        actions.put( FLIP_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.FlipCardAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.FlipCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.FlipCardAction_text );
            }
        } );
        actions.put( GIVE_TABLE_NETWORK_CONTROL_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.GiveTableNetworkControlAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.GiveTableNetworkControlAction_text );
            }
        } );
        actions.put( HOST_TABLE_NETWORK_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.HostTableNetworkAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.HostTableNetworkAction_text );
            }
        } );
        actions.put( JOIN_TABLE_NETWORK_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.JoinTableNetworkAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.JoinTableNetworkAction_text );
            }
        } );
        actions.put( OPEN_ABOUT_DIALOG_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenAboutDialogAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.OpenAboutDialogAction_text() );
            }
        } );
        actions.put( OPEN_NEW_TABLE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenNewTableAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.OpenNewTableAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.OpenNewTableAction_text );
            }
        } );
        actions.put( OPEN_TABLE_ACTION_ID, new BasicAction()
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
        actions.put( REMOVE_ALL_CARD_PILES_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveAllCardPilesAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveAllCardPilesAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RemoveAllCardPilesAction_text );
            }
        } );
        actions.put( REMOVE_ALL_CARDS_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveAllCardsAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveAllCardsAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RemoveAllCardsAction_text );
            }
        } );
        actions.put( REMOVE_CARD_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveCardAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveCardAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RemoveCardAction_text );
            }
        } );
        actions.put( REMOVE_CARD_PILE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveCardPileAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RemoveCardPileAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RemoveCardPileAction_text );
            }
        } );
        actions.put( REQUEST_TABLE_NETWORK_CONTROL_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.RequestTableNetworkControlAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.RequestTableNetworkControlAction_text );
            }
        } );
        actions.put( RESET_TABLE_ORIGIN_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.ResetTableOriginAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.ResetTableOriginAction_text );
            }
        } );
        actions.put( SAVE_TABLE_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( NlsMessages.SaveTableAction_accelerator ) );
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SaveTableAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SaveTableAction_text );
            }
        } );
        actions.put( SAVE_TABLE_AS_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SaveTableAsAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SaveTableAsAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_DOWN_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.containerLayouts.accordianDown" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianDownCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianDownCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_LEFT_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.containerLayouts.accordianLeft" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianLeftCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianLeftCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_RIGHT_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.containerLayouts.accordianRight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianRightCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianRightCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_ACCORDIAN_UP_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.containerLayouts.accordianUp" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetAccordianUpCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetAccordianUpCardPileLayoutAction_text );
            }
        } );
        actions.put( SET_STACKED_CARD_PILE_LAYOUT_ACTION_ID, new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.containerLayouts.stacked" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, KeyStroke.getKeyStroke( NlsMessages.SetStackedCardPileLayoutAction_mnemonic ).getKeyCode() );
                putValue( NAME, NlsMessages.SetStackedCardPileLayoutAction_text );
            }
        } );

        return Collections.unmodifiableMap( actions );
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
     * Gets the flip card action.
     * 
     * @return The flip card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getFlipCardAction()
    {
        return actions_.get( FLIP_CARD_ACTION_ID );
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
     * Gets the remove all card piles action.
     * 
     * @return The remove all card piles action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveAllCardPilesAction()
    {
        return actions_.get( REMOVE_ALL_CARD_PILES_ACTION_ID );
    }

    /**
     * Gets the remove all cards action.
     * 
     * @return The remove all cards action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveAllCardsAction()
    {
        return actions_.get( REMOVE_ALL_CARDS_ACTION_ID );
    }

    /**
     * Gets the remove card action.
     * 
     * @return The remove card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveCardAction()
    {
        return actions_.get( REMOVE_CARD_ACTION_ID );
    }

    /**
     * Gets the remove card pile action.
     * 
     * @return The remove card pile action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveCardPileAction()
    {
        return actions_.get( REMOVE_CARD_PILE_ACTION_ID );
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
     * Gets the set accordian down card pile layout action.
     * 
     * @return The set accordian down card pile layout action; never
     *         {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianDownCardPileLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_DOWN_CARD_PILE_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set accordian left card pile layout action.
     * 
     * @return The set accordian left card pile layout action; never
     *         {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianLeftCardPileLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_LEFT_CARD_PILE_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set accordian right card pile layout action.
     * 
     * @return The set accordian right card pile layout action; never
     *         {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianRightCardPileLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_RIGHT_CARD_PILE_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set accordian up card pile layout action.
     * 
     * @return The set accordian up card pile layout action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetAccordianUpCardPileLayoutAction()
    {
        return actions_.get( SET_ACCORDIAN_UP_CARD_PILE_LAYOUT_ACTION_ID );
    }

    /**
     * Gets the set stacked card pile layout action.
     * 
     * @return The set stacked card pile layout action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getSetStackedCardPileLayoutAction()
    {
        return actions_.get( SET_STACKED_CARD_PILE_LAYOUT_ACTION_ID );
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
