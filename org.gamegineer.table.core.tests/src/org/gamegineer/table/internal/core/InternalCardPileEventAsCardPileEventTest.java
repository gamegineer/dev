/*
 * InternalCardPileEventAsCardPileEventTest.java
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
 * Created on Jan 18, 2010 at 11:32:26 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.table.core.AbstractCardPileEventTestCase;
import org.gamegineer.table.core.ICardPile;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.InternalCardPileEvent} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardPileEvent} interface.
 */
public final class InternalCardPileEventAsCardPileEventTest
    extends AbstractCardPileEventTestCase<InternalCardPileEvent>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * InternalCardPileEventAsCardPileEventTest} class.
     */
    public InternalCardPileEventAsCardPileEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractCardPileEventTestCase#createCardPileEvent()
     */
    @Override
    protected InternalCardPileEvent createCardPileEvent()
    {
        return InternalCardPileEvent.createCardPileEvent( createDummy( ICardPile.class ) );
    }
}
