/*
 * CommandQueueExtension.java
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
 * Created on Jun 9, 2008 at 9:13:25 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandqueue;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.Future;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue;
import org.gamegineer.engine.internal.core.extensions.AbstractExtension;

/**
 * Implementation of the
 * {@link org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue}
 * extension that delegates its implementation to another command queue
 * implementation.
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class CommandQueueExtension
    extends AbstractExtension
    implements ICommandQueue
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command queue to which all operations are delegated. */
    private final ICommandQueue delegate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandQueueExtension} class.
     * 
     * @param delegate
     *        The command queue to which all operations are delegated; must not
     *        be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    public CommandQueueExtension(
        /* @NonNull */
        final ICommandQueue delegate )
    {
        super( ICommandQueue.class );

        assertArgumentNotNull( delegate, "delegate" ); //$NON-NLS-1$

        delegate_ = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the command queue to which all operations are delegated.
     * 
     * @return The command queue to which all operations are delegated; never
     *         {@code null}.
     */
    /* @NonNull */
    ICommandQueue getDelegate()
    {
        return delegate_;
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue#submitCommand(org.gamegineer.engine.core.IEngineContext,
     *      org.gamegineer.engine.core.ICommand)
     */
    public <T> Future<T> submitCommand(
        final IEngineContext context,
        final ICommand<T> command )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$
        assertExtensionStarted();

        return delegate_.submitCommand( context, command );
    }
}
