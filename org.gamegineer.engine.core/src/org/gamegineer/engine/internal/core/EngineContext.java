/*
 * EngineContext.java
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
 * Created on Jun 11, 2008 at 7:46:40 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.contexts.command.ICommandContext;
import org.gamegineer.engine.core.contexts.extension.IExtensionContext;

/**
 * Implementation of {@link org.gamegineer.engine.core.IEngineContext}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class EngineContext
    implements IEngineContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command context. */
    private final ICommandContext commandContext_;

    /** The engine associated with this context. */
    private final Engine engine_;

    /** The extension context. */
    private final IExtensionContext extensionContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EngineContext} class.
     * 
     * @param engine
     *        The engine associated with this context; must not be {@code null}.
     * @param commandContext
     *        The command context; must not be {@code null}.
     */
    EngineContext(
        /* @NonNull */
        final Engine engine,
        /* @NonNull */
        final ICommandContext commandContext )
    {
        assert engine != null;
        assert commandContext != null;

        engine_ = engine;
        commandContext_ = commandContext;
        extensionContext_ = new ExtensionContext();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the engine associated with this context.
     * 
     * @return The engine associated with this context; never {@code null}.
     */
    /* @NonNull */
    Engine getEngine()
    {
        return engine_;
    }

    /*
     * @see org.gamegineer.engine.core.IEngineContext#getContext(java.lang.Class)
     */
    public <T> T getContext(
        final Class<T> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        if( type == ICommandContext.class )
        {
            return type.cast( commandContext_ );
        }
        else if( type == IExtensionContext.class )
        {
            return type.cast( extensionContext_ );
        }

        return null;
    }

    /*
     * @see org.gamegineer.engine.core.IEngineContext#getExtension(java.lang.Class)
     */
    public <T> T getExtension(
        final Class<T> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        return type.cast( engine_.getExtensionRegistry().getExtension( this, type ) );
    }

    /*
     * @see org.gamegineer.engine.core.IEngineContext#getState()
     */
    public IState getState()
    {
        return engine_.getState();
    }
}
