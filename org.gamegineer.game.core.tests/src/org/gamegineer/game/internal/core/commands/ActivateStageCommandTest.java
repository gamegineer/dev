/*
 * ActivateStageCommandTest.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Aug 22, 2008 at 9:51:24 PM.
 */

package org.gamegineer.game.internal.core.commands;

import org.gamegineer.game.internal.core.StageVersion;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.commands.ActivateStageCommand}
 * class.
 */
public final class ActivateStageCommandTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActivateStageCommandTest} class.
     */
    public ActivateStageCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * source stage identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_SourceStageId_Null()
    {
        new ActivateStageCommand( null, new StageVersion() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * source stage version.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_SourceStageVersion_Null()
    {
        new ActivateStageCommand( "id", null ); //$NON-NLS-1$
    }
}