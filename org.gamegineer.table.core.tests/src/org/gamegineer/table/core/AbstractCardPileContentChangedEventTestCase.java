/*
 * AbstractCardPileContentChangedEventTestCase.java
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
 * Created on Jan 10, 2010 at 10:10:37 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ICardPileContentChangedEvent} interface.
 * 
 * @param <T>
 *        The type of the card pile content changed event.
 */
public abstract class AbstractCardPileContentChangedEventTestCase<T extends ICardPileContentChangedEvent>
    extends AbstractCardPileEventTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardPileContentChangedEventTestCase} class.
     */
    protected AbstractCardPileContentChangedEventTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code getCard} method does not return {@code null}.
     */
    @Test
    public void testGetCard_ReturnValue_NonNull()
    {
        assertNotNull( getCardPileEvent().getCard() );
    }
}
