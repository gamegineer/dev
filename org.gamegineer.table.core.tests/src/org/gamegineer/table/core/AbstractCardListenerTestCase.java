/*
 * AbstractCardListenerTestCase.java
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
 * Created on Oct 24, 2009 at 9:18:42 PM.
 */

package org.gamegineer.table.core;

import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ICardListener} interface.
 */
public abstract class AbstractCardListenerTestCase
    extends AbstractComponentListenerTestCase<ICardListener>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCardListenerTestCase}
     * class.
     */
    protected AbstractCardListenerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card listener under test in the fixture.
     * 
     * @return The card listener under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final ICardListener getCardListener()
    {
        return getComponentListener();
    }

    /**
     * Ensures the {@code cardOrientationChanged} method throws an exception
     * when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardOrientationChanged_Event_Null()
    {
        getCardListener().cardOrientationChanged( null );
    }

    /**
     * Ensures the {@code cardSurfaceDesignsChanged} method throws an exception
     * when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardSurfaceDesignsChanged_Event_Null()
    {
        getCardListener().cardSurfaceDesignsChanged( null );
    }
}
