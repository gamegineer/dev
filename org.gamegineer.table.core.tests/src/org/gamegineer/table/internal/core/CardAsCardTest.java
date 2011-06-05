/*
 * CardAsCardTest.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Oct 11, 2009 at 9:53:43 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.common.core.util.memento.IMemento;
import org.gamegineer.table.core.AbstractCardTestCase;
import org.gamegineer.table.core.CardSurfaceDesigns;
import org.gamegineer.table.core.ICard;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.Card}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICard} interface.
 */
public final class CardAsCardTest
    extends AbstractCardTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardAsCardTest} class.
     */
    public CardAsCardTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractCardTestCase#createCard()
     */
    @Override
    protected ICard createCard()
    {
        return new Card( CardSurfaceDesigns.createUniqueCardSurfaceDesign(), CardSurfaceDesigns.createUniqueCardSurfaceDesign() );
    }

    /*
     * @see org.gamegineer.table.core.AbstractCardTestCase#createCard(org.gamegineer.common.core.util.memento.IMemento)
     */
    @Override
    protected ICard createCard(
        final IMemento memento )
        throws Exception
    {
        return Card.fromMemento( memento );
    }
}
