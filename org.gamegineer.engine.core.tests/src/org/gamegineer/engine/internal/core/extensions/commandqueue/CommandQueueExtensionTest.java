/*
 * CommandQueueExtensionTest.java
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
 * Created on Jun 9, 2008 at 9:20:54 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandqueue;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.MockCommand;
import org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandqueue.CommandQueueExtension}
 * class.
 */
public final class CommandQueueExtensionTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandQueueExtensionTest}
     * class.
     */
    public CommandQueueExtensionTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * delegate.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Delegate_Null()
    {
        new CommandQueueExtension( null );
    }

    /**
     * Ensures the {@code submitCommand} method throws an exception when called
     * before the extension has been started.
     */
    @Test( expected = IllegalStateException.class )
    public void testSubmitCommand_ExtensionNotStarted()
    {
        final CommandQueueExtension extension = new CommandQueueExtension( createDummy( ICommandQueue.class ) );

        extension.submitCommand( createDummy( IEngineContext.class ), new MockCommand<Void>() );
    }
}
