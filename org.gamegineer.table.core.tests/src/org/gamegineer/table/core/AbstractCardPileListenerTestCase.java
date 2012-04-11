/*
 * AbstractCardPileListenerTestCase.java
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
 * Created on Jan 10, 2010 at 10:22:59 PM.
 */

package org.gamegineer.table.core;

import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ICardPileListener} interface.
 */
public abstract class AbstractCardPileListenerTestCase
    extends AbstractComponentListenerTestCase<ICardPileListener>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractCardPileListenerTestCase} class.
     */
    protected AbstractCardPileListenerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card pile listener under test in the fixture.
     * 
     * @return The card pile listener under test in the fixture; never
     *         {@code null}.
     */
    /* @NonNull */
    protected final ICardPileListener getCardPileListener()
    {
        return getComponentListener();
    }

    /**
     * Ensures the {@code cardPileLayoutChanged} method throws an exception when
     * passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardPileLayoutChanged_Event_Null()
    {
        getCardPileListener().cardPileLayoutChanged( null );
    }
}
