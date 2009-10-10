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

    /** The action used to exit the application. */
    private static final BasicAction exitAction_;

    /** The action used to open the about dialog. */
    private static final BasicAction openAboutDialogAction_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Actions} class.
     */
    static
    {
        exitAction_ = createExitAction();
        openAboutDialogAction_ = createOpenAboutDialogAction();
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
            private static final long serialVersionUID = 6316141299713487260L;

            {
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.ExitAction_mnemonic ) );
                putValue( NAME, Messages.ExitAction_text );
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
            private static final long serialVersionUID = 5964488434872973833L;

            {
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.OpenAboutDialogAction_mnemonic ) );
                putValue( NAME, Messages.OpenAboutDialogAction_text );
            }
        };
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
     * Gets the open about dialog action.
     * 
     * @return The open about dialog action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getOpenAboutDialogAction()
    {
        return openAboutDialogAction_;
    }
}
