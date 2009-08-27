/*
 * RegisterExtensionCommandAsInvertibleCommandTest.java
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
 * Created on Apr 21, 2008 at 9:43:42 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

import org.gamegineer.engine.core.AbstractInvertibleCommandTestCase;
import org.gamegineer.engine.core.MockExtension;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.extensionregistry.RegisterExtensionCommand}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.IInvertibleCommand} interface.
 */
public final class RegisterExtensionCommandAsInvertibleCommandTest
    extends AbstractInvertibleCommandTestCase<RegisterExtensionCommand, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code RegisterExtensionCommandAsInvertibleCommandTest} class.
     */
    public RegisterExtensionCommandAsInvertibleCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.AbstractCommandTestCase#createCommand()
     */
    @Override
    protected RegisterExtensionCommand createCommand()
    {
        return new RegisterExtensionCommand( new MockExtension( Object.class ) );
    }
}
