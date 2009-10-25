/*
 * Actions.java
 * Copyright 2008-2009 Gamegineer.org
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

import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.action.BasicAction;

/**
 * The collection of actions available to all views.
 */
@NotThreadSafe
final class Actions
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The action used to add a card to the table. */
    private static final BasicAction addCardAction_;

    /** The action used to exit the application. */
    private static final BasicAction exitAction_;

    /** The action used to flip a card on the table. */
    private static final BasicAction flipCardAction_;

    /** The action used to open the about dialog. */
    private static final BasicAction openAboutDialogAction_;

    /** The action used to remove a card from the table. */
    private static final BasicAction removeCardAction_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Actions} class.
     */
    static
    {
        addCardAction_ = createAddCardAction();
        exitAction_ = createExitAction();
        flipCardAction_ = createFlipCardAction();
        openAboutDialogAction_ = createOpenAboutDialogAction();
        removeCardAction_ = createRemoveCardAction();
    }

    /**
     * Initializes a new instance of the {@code Actions} class.
     */
    private Actions()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the add card action.
     * 
     * @return The add card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddCardAction_mnemonic ) );
                putValue( NAME, Messages.AddCardAction_text );
            }
        };
    }

    /**
     * Creates the exit action.
     * 
     * @return The exit action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createExitAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.ExitAction_mnemonic ) );
                putValue( NAME, Messages.ExitAction_text );
            }
        };
    }

    /**
     * Creates the flip card action.
     * 
     * @return The flip card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createFlipCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.FlipCardAction_mnemonic ) );
                putValue( NAME, Messages.FlipCardAction_text );
            }
        };
    }

    /**
     * Creates the open about dialog action.
     * 
     * @return The open about dialog action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createOpenAboutDialogAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.OpenAboutDialogAction_mnemonic ) );
                putValue( NAME, Messages.OpenAboutDialogAction_text );
            }
        };
    }

    /**
     * Creates the remove card action.
     * 
     * @return The remove card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createRemoveCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.RemoveCardAction_mnemonic ) );
                putValue( NAME, Messages.RemoveCardAction_text );
            }
        };
    }

    /**
     * Gets the add card action.
     * 
     * @return The add card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddCardAction()
    {
        return addCardAction_;
    }

    /**
     * Gets the exit action.
     * 
     * @return The exit action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getExitAction()
    {
        return exitAction_;
    }

    /**
     * Gets the flip card action.
     * 
     * @return The flip card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getFlipCardAction()
    {
        return flipCardAction_;
    }

    /**
     * Gets the open about dialog action.
     * 
     * @return The open about dialog action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getOpenAboutDialogAction()
    {
        return openAboutDialogAction_;
    }

    /**
     * Gets the remove card action.
     * 
     * @return The remove card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveCardAction()
    {
        return removeCardAction_;
    }
}
