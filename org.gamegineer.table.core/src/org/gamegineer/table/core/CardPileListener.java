/*
 * CardPileListener.java
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
 * Created on Aug 3, 2011 at 8:22:10 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;

/**
 * Default implementation of {@link ICardPileListener}.
 * 
 * <p>
 * All methods of this class do nothing.
 * </p>
 */
@Immutable
public class CardPileListener
    extends ComponentListener
    implements ICardPileListener
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileListener} class.
     */
    public CardPileListener()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.core.ICardPileListener#cardAdded(org.gamegineer.table.core.CardPileContentChangedEvent)
     */
    @Override
    public void cardAdded(
        final CardPileContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.core.ICardPileListener#cardPileBaseDesignChanged(org.gamegineer.table.core.CardPileEvent)
     */
    @Override
    public void cardPileBaseDesignChanged(
        final CardPileEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.core.ICardPileListener#cardPileLayoutChanged(org.gamegineer.table.core.CardPileEvent)
     */
    @Override
    public void cardPileLayoutChanged(
        final CardPileEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.core.ICardPileListener#cardRemoved(org.gamegineer.table.core.CardPileContentChangedEvent)
     */
    @Override
    public void cardRemoved(
        final CardPileContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }
}
