/*
 * NullGameSystemUiAsGameSystemUiTest.java
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
 * Created on Mar 11, 2009 at 11:02:56 PM.
 */

package org.gamegineer.game.internal.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.ui.system.AbstractGameSystemUiTestCase;
import org.gamegineer.game.ui.system.IGameSystemUi;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.ui.system.NullGameSystemUi} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.game.ui.system.IGameSystemUi} interface.
 */
public final class NullGameSystemUiAsGameSystemUiTest
    extends AbstractGameSystemUiTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code NullGameSystemUiAsGameSystemUiTest} class.
     */
    public NullGameSystemUiAsGameSystemUiTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.ui.system.AbstractGameSystemUiTestCase#createGameSystemUi(org.gamegineer.game.core.system.IGameSystem)
     */
    @Override
    protected IGameSystemUi createGameSystemUi(
        final IGameSystem gameSystem )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$

        return new NullGameSystemUi( gameSystem.getId() );
    }
}