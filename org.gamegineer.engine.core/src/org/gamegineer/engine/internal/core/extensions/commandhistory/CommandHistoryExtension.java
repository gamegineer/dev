/*
 * CommandHistoryExtension.java
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
 * Created on Apr 25, 2008 at 11:55:23 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandhistory;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.List;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory;
import org.gamegineer.engine.internal.core.extensions.AbstractExtension;

/**
 * Implementation of the
 * {@link org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory}
 * extension that delegates its implementation to another command history
 * implementation.
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class CommandHistoryExtension
    extends AbstractExtension
    implements ICommandHistory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command history to which all operations are delegated. */
    private final ICommandHistory m_delegate;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandHistoryExtension} class.
     * 
     * @param delegate
     *        The command history to which all operations are delegated; must
     *        not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    public CommandHistoryExtension(
        /* @NonNull */
        final ICommandHistory delegate )
    {
        super( ICommandHistory.class );

        assertArgumentNotNull( delegate, "delegate" ); //$NON-NLS-1$

        m_delegate = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#canRedo(org.gamegineer.engine.core.IEngineContext)
     */
    public boolean canRedo(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertExtensionStarted();

        return m_delegate.canRedo( context );
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#canUndo(org.gamegineer.engine.core.IEngineContext)
     */
    public boolean canUndo(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertExtensionStarted();

        return m_delegate.canUndo( context );
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#getCommands(org.gamegineer.engine.core.IEngineContext)
     */
    public List<IInvertibleCommand<?>> getCommands(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertExtensionStarted();

        return m_delegate.getCommands( context );
    }

    /**
     * Gets the command history to which all operations are delegated.
     * 
     * @return The command history to which all operations are delegated; never
     *         {@code null}.
     */
    /* @NonNull */
    ICommandHistory getDelegate()
    {
        return m_delegate;
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#redo(org.gamegineer.engine.core.IEngineContext)
     */
    public void redo(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertExtensionStarted();

        m_delegate.redo( context );
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory#undo(org.gamegineer.engine.core.IEngineContext)
     */
    public void undo(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertExtensionStarted();

        m_delegate.undo( context );
    }
}
