/*
 * StateManagerExtension.java
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
 * Created on Jul 2, 2008 at 11:21:33 PM.
 */

package org.gamegineer.engine.internal.core.extensions.statemanager;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.statemanager.IStateManager;
import org.gamegineer.engine.internal.core.extensions.AbstractExtension;

/**
 * Implementation of the
 * {@link org.gamegineer.engine.core.extensions.statemanager.IStateManager}
 * extension that delegates its implementation to another command queue
 * implementation.
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class StateManagerExtension
    extends AbstractExtension
    implements IStateManager
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The state manager to which all operations are delegated. */
    private final IStateManager delegate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateManagerExtension} class.
     * 
     * @param delegate
     *        The state manager to which all operations are delegated; must not
     *        be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    public StateManagerExtension(
        /* @NonNull */
        final IStateManager delegate )
    {
        super( IStateManager.class );

        assertArgumentNotNull( delegate, "delegate" ); //$NON-NLS-1$

        delegate_ = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the state manager to which all operations are delegated.
     * 
     * @return The state manager to which all operations are delegated; never
     *         {@code null}.
     */
    /* @NonNull */
    IStateManager getDelegate()
    {
        return delegate_;
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.statemanager.IStateManager#getMemento(org.gamegineer.engine.core.IEngineContext)
     */
    public IMemento getMemento(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertExtensionStarted();

        return delegate_.getMemento( context );
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.statemanager.IStateManager#setMemento(org.gamegineer.engine.core.IEngineContext,
     *      org.gamegineer.common.persistence.memento.IMemento)
     */
    public void setMemento(
        final IEngineContext context,
        final IMemento memento )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$
        assertExtensionStarted();

        delegate_.setMemento( context, memento );
    }
}
