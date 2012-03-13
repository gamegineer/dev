/*
 * CardPileModelListener.java
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
 * Created on Aug 3, 2011 at 8:46:12 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;

/**
 * Default implementation of {@link ICardPileModelListener}.
 * 
 * <p>
 * All methods of this class do nothing.
 * </p>
 */
public class CardPileModelListener
    implements ICardPileModelListener
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileModelListener} class.
     */
    public CardPileModelListener()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.model.ICardPileModelListener#cardPileChanged(org.gamegineer.table.internal.ui.model.CardPileModelEvent)
     */
    @Override
    public void cardPileChanged(
        final CardPileModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.model.ICardPileModelListener#cardPileModelFocusChanged(org.gamegineer.table.internal.ui.model.CardPileModelEvent)
     */
    @Override
    public void cardPileModelFocusChanged(
        final CardPileModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }
}
