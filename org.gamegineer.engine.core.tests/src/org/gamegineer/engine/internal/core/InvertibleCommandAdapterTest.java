/*
 * InvertibleCommandAdapterTest.java
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
 * Created on Aug 13, 2008 at 10:35:40 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.junit.Assert.assertEquals;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.MockCommand;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.InvertibleCommandAdapter} class.
 */
public final class InvertibleCommandAdapterTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InvertibleCommandAdapterTest}
     * class.
     */
    public InvertibleCommandAdapterTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * command.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Command_Null()
    {
        new InvertibleCommandAdapter<Void>( null );
    }

    /**
     * Ensures the {@code getType} method returns the type of the underlying
     * non-invertible command.
     */
    @Test
    public void testGetType_ReturnValue()
    {
        final ICommand<Void> command = new MockCommand<Void>();
        final InvertibleCommandAdapter<Void> invertibleCommand = new InvertibleCommandAdapter<Void>( command );

        assertEquals( command.getType(), invertibleCommand.getType() );
    }
}
