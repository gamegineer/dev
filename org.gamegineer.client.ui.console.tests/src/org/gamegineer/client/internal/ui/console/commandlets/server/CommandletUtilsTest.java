/*
 * CommandletUtilsTest.java
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
 * Created on Mar 12, 2009 at 12:06:27 AM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.server;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlets.server.CommandletUtils}
 * class.
 */
public final class CommandletUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletUtilsTest} class.
     */
    public CommandletUtilsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code getGameSystemUi} method throws an exception when
     * passed a {@code null} commandlet context.
     */
    @Test( expected = AssertionError.class )
    public void testGetGameSystemUi_Context_Null()
    {
        CommandletUtils.getGameSystemUi( null, "id" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getGameSystemUi} method throws an exception when
     * passed a {@code null} game system identifier.
     */
    @Test( expected = AssertionError.class )
    public void testGetGameSystemUi_GameSystemId_Null()
    {
        CommandletUtils.getGameSystemUi( createDummy( ICommandletContext.class ), null );
    }
}
