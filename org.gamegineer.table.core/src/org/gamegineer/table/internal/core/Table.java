/*
 * Table.java
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
 * Created on Oct 6, 2009 at 11:09:51 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;

/**
 * Implementation of {@link org.gamegineer.table.core.ITable}.
 */
@ThreadSafe
public final class Table
    implements ITable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of cards on this table. */
    @GuardedBy( "lock_ " )
    private final Collection<ICard> cards_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Table} class.
     */
    public Table()
    {
        lock_ = new Object();
        cards_ = new ArrayList<ICard>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ITable#addCard(org.gamegineer.table.core.ICard)
     */
    public void addCard(
        final ICard card )
    {
        assertArgumentNotNull( card, "card" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            if( !cards_.contains( card ) )
            {
                cards_.add( card );
            }
        }
    }

    /*
     * @see org.gamegineer.table.core.ITable#getCards()
     */
    public Collection<ICard> getCards()
    {
        synchronized( lock_ )
        {
            return new ArrayList<ICard>( cards_ );
        }
    }

    /*
     * @see org.gamegineer.table.core.ITable#removeCard(org.gamegineer.table.core.ICard)
     */
    public void removeCard(
        final ICard card )
    {
        assertArgumentNotNull( card, "card" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            cards_.remove( card );
        }
    }
}
