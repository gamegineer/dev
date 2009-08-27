/*
 * CommandHistoryExtensionAsExtensionTest.java
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
 * Created on Apr 26, 2008 at 12:00:22 AM.
 */

package org.gamegineer.engine.internal.core.extensions.commandhistory;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.engine.core.AbstractExtensionTestCase;
import org.gamegineer.engine.core.IExtension;
import org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandhistory.CommandHistoryExtension}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.IExtension} interface.
 */
public final class CommandHistoryExtensionAsExtensionTest
    extends AbstractExtensionTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CommandHistoryExtensionAsExtensionTest} class.
     */
    public CommandHistoryExtensionAsExtensionTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.AbstractExtensionTestCase#createExtension()
     */
    @Override
    protected IExtension createExtension()
    {
        return new CommandHistoryExtension( createDummy( ICommandHistory.class ) );
    }
}
