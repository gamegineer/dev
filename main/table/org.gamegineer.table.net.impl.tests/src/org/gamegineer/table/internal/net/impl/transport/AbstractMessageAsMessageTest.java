/*
 * AbstractMessageAsMessageTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Apr 8, 2011 at 10:11:52 PM.
 */

package org.gamegineer.table.internal.net.impl.transport;

/**
 * A fixture for testing the {@link AbstractMessage} class to ensure it does not
 * violate the contract of the {@link IMessage} interface.
 */
public final class AbstractMessageAsMessageTest
    extends AbstractMessageTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractMessageAsMessageTest}
     * class.
     */
    public AbstractMessageAsMessageTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.AbstractMessageTestCase#createMessage()
     */
    @Override
    protected IMessage createMessage()
    {
        return new AbstractMessage()
        {
            private static final long serialVersionUID = 1L;
        };
    }
}
