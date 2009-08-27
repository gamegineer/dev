/*
 * StateEventDelegate.java
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
 * Created on Jun 2, 2008 at 9:03:23 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateEvent;
import org.gamegineer.engine.internal.core.ImmutableEngineContext;

/**
 * An implementation of
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IStateEvent}
 * to which implementations of
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.StateEvent}
 * can delegate their behavior.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
class StateEventDelegate
    implements IStateEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine context. */
    private final IEngineContext m_context;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateEventDelegate} class.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     */
    StateEventDelegate(
        /* @NonNull */
        final IEngineContext context )
    {
        assert context != null;

        m_context = new ImmutableEngineContext( context );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateEvent#getEngineContext()
     */
    public final IEngineContext getEngineContext()
    {
        return m_context;
    }
}
