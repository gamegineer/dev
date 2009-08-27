/*
 * FakeEngineContext.java
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
 * Created on Sep 2, 2008 at 10:16:42 PM.
 */

package org.gamegineer.engine.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;

/**
 * Fake implementation of {@link org.gamegineer.engine.core.IEngineContext}.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public class FakeEngineContext
    implements IEngineContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine state. */
    private final IState m_state;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeEngineContext} class.
     */
    public FakeEngineContext()
    {
        m_state = new FakeState();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * The default implementation always returns {@code null}.
     * 
     * @see org.gamegineer.engine.core.IEngineContext#getContext(java.lang.Class)
     */
    public <T> T getContext(
        final Class<T> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        return null;
    }

    /**
     * The default implementation always returns {@code null}.
     * 
     * @see org.gamegineer.engine.core.IEngineContext#getExtension(java.lang.Class)
     */
    public <T> T getExtension(
        final Class<T> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        return null;
    }

    /*
     * @see org.gamegineer.engine.core.IEngineContext#getState()
     */
    public IState getState()
    {
        return m_state;
    }
}
