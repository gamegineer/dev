/*
 * CommandHistoryExtensionAsCommandHistoryTest.java
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
 * Created on Apr 25, 2008 at 11:59:53 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandhistory;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.List;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.extensions.commandhistory.AbstractCommandHistoryTestCase;
import org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandhistory.CommandHistoryExtension}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory}
 * interface.
 */
public final class CommandHistoryExtensionAsCommandHistoryTest
    extends AbstractCommandHistoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CommandHistoryExtensionAsCommandHistoryTest} class.
     */
    public CommandHistoryExtensionAsCommandHistoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.commandhistory.AbstractCommandHistoryTestCase#createCommandHistory(org.gamegineer.engine.core.IEngineContext, java.util.List)
     */
    @Override
    protected ICommandHistory createCommandHistory(
        final IEngineContext context,
        final List<IInvertibleCommand<?>> commands )
        throws Exception
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final CommandHistoryExtension extension = new CommandHistoryExtension( new FakeCommandHistory( commands ) );
        extension.start( context );
        return extension;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A fake implementation of {@code ICommandHistory} to test the
     * implementation of {@code CommandHistoryExtension}.
     */
    private static final class FakeCommandHistory
        implements ICommandHistory
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The command history commands. */
        private final List<IInvertibleCommand<?>> m_commands;

        /**
         * The index in the command history representing the location where the
         * next command will be inserted.
         */
        private int m_nextCommandIndex;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code FakeCommandHistory} class.
         * 
         * @param commands
         *        The initial command history; must not be {@code null}.
         */
        FakeCommandHistory(
            /* @NonNull */
            final List<IInvertibleCommand<?>> commands )
        {
            assert commands != null;

            m_commands = new ArrayList<IInvertibleCommand<?>>( commands );
            m_nextCommandIndex = commands.size();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#canRedo(org.gamegineer.engine.core.IEngineContext)
         */
        public boolean canRedo(
            final IEngineContext context )
        {
            assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

            return m_nextCommandIndex < m_commands.size();
        }

        /*
         * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#canUndo(org.gamegineer.engine.core.IEngineContext)
         */
        public boolean canUndo(
            final IEngineContext context )
        {
            assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

            return m_nextCommandIndex > 0;
        }

        /*
         * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#getCommands(org.gamegineer.engine.core.IEngineContext)
         */
        public List<IInvertibleCommand<?>> getCommands(
            final IEngineContext context )
        {
            assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

            return new ArrayList<IInvertibleCommand<?>>( m_commands.subList( 0, m_nextCommandIndex ) );
        }

        /*
         * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#redo(org.gamegineer.engine.core.IEngineContext)
         */
        public void redo(
            final IEngineContext context )
        {
            assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
            assertStateLegal( canRedo( context ) );

            ++m_nextCommandIndex;
        }

        /*
         * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#undo(org.gamegineer.engine.core.IEngineContext)
         */
        public void undo(
            final IEngineContext context )
        {
            assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
            assertStateLegal( canUndo( context ) );

            --m_nextCommandIndex;
        }
    }
}
