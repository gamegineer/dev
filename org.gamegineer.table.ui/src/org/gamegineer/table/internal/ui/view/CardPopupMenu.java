/*
 * CardPopupMenu.java
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
 * Created on Dec 22, 2009 at 9:58:34 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPopupMenu;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.ui.action.ActionMediator;
import org.gamegineer.table.internal.ui.action.BasicAction;

/**
 * The popup menu associated with a card.
 */
@NotThreadSafe
final class CardPopupMenu
    extends JPopupMenu
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3549260668326226525L;

    /** The action mediator. */
    private final ActionMediator actionMediator_;

    /** The card associated with this menu. */
    private final ICard card_;

    /** The table associated with this menu. */
    private final ITable table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPopupMenu} class.
     * 
     * @param table
     *        The table associated with this menu; must not be {@code null}.
     * @param card
     *        The card associated with this menu; must not be {@code null}.
     */
    CardPopupMenu(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final ICard card )
    {
        assert table != null;
        assert card != null;

        actionMediator_ = new ActionMediator();
        card_ = card;
        table_ = table;

        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Binds the specified action to the specified action listener.
     * 
     * @param action
     *        The action; must not be {@code null}.
     * @param listener
     *        The action listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If an action listener is already bound to {@code action}.
     */
    private void bindAction(
        /* @NonNull */
        final BasicAction action,
        /* @NonNUll */
        final ActionListener listener )
    {
        assert action != null;
        assert listener != null;

        actionMediator_.bind( action, new UnbindActionsActionListenerDecorator( listener ) );
    }

    /**
     * Binds the action attachments for this component.
     */
    private void bindActions()
    {
        bindAction( Actions.getFlipCardAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                flipCard();
            }
        } );
        bindAction( Actions.getRemoveCardAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                removeCard();
            }
        } );
    }

    /*
     * @see javax.swing.JPopupMenu#firePopupMenuCanceled()
     */
    @Override
    protected void firePopupMenuCanceled()
    {
        super.firePopupMenuCanceled();

        unbindActions();
    }

    /*
     * @see javax.swing.JPopupMenu#firePopupMenuWillBecomeVisible()
     */
    @Override
    protected void firePopupMenuWillBecomeVisible()
    {
        super.firePopupMenuWillBecomeVisible();

        bindActions();
    }

    /**
     * Flips the card associated with this menu.
     */
    private void flipCard()
    {
        card_.flip();
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        add( Actions.getRemoveCardAction() );
        add( Actions.getFlipCardAction() );
    }

    /**
     * Removes the card associated with this menu.
     */
    private void removeCard()
    {
        table_.removeCard( card_ );
    }

    /**
     * Unbinds the action attachments for this component.
     */
    private void unbindActions()
    {
        actionMediator_.unbindAll();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * An action listener decorator that unbinds the action attachments for the
     * popup menu after the decorated action is executed.
     */
    private final class UnbindActionsActionListenerDecorator
        implements ActionListener
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The decorated action listener. */
        private final ActionListener listener_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code
         * UnbindActionsActionListenerDecorator} class.
         * 
         * @param listener
         *        The decorated action listener; must not be {@code null}.
         */
        UnbindActionsActionListenerDecorator(
            /* @NonNull */
            final ActionListener listener )
        {
            assert listener != null;

            listener_ = listener;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void actionPerformed(
            final ActionEvent e )
        {
            listener_.actionPerformed( e );

            unbindActions();
        }
    }
}
