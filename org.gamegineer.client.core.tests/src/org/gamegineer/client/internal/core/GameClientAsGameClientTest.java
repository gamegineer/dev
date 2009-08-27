/*
 * GameClientAsGameClientTest.java
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
 * Created on Dec 27, 2008 at 9:31:03 PM.
 */

package org.gamegineer.client.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.client.core.AbstractGameClientTestCase;
import org.gamegineer.client.core.IGameClient;
import org.gamegineer.client.core.config.IGameClientConfiguration;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.core.GameClient} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.client.core.IGameClient} interface.
 */
public final class GameClientAsGameClientTest
    extends AbstractGameClientTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameClientAsGameClientTest}
     * class.
     */
    public GameClientAsGameClientTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.core.AbstractGameClientTestCase#createGameClient(org.gamegineer.client.core.config.IGameClientConfiguration)
     */
    @Override
    protected IGameClient createGameClient(
        final IGameClientConfiguration gameClientConfig )
        throws Exception
    {
        assertArgumentNotNull( gameClientConfig, "gameClientConfig" ); //$NON-NLS-1$

        return GameClient.createGameClient( gameClientConfig );
    }
}
