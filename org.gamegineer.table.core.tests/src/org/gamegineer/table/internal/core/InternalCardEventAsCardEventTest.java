/*
 * InternalCardEventAsCardEventTest.java
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
 * Created on Oct 24, 2009 at 10:18:17 PM.
 */

package org.gamegineer.table.internal.core;

import org.easymock.EasyMock;
import org.gamegineer.table.core.AbstractCardEventTestCase;
import org.gamegineer.table.core.ICard;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.InternalCardEvent} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardEvent} interface.
 */
public final class InternalCardEventAsCardEventTest
    extends AbstractCardEventTestCase<InternalCardEvent>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * InternalCardEventAsCardEventTest} class.
     */
    public InternalCardEventAsCardEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractCardEventTestCase#createCardEvent()
     */
    @Override
    protected InternalCardEvent createCardEvent()
    {
        return InternalCardEvent.createCardEvent( EasyMock.createMock( ICard.class ) );
    }
}
