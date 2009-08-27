/*
 * ImmutableEngineContext.java
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
 * Created on Jun 11, 2008 at 10:12:33 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Set;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IState;

/**
 * A decorator that provides an immutable view of an engine context.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class ImmutableEngineContext
    implements IEngineContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine context for which an immutable view is provided. */
    private final IEngineContext m_context;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ImmutableEngineContext} class.
     * 
     * @param context
     *        The engine context for which an immutable view is provided; must
     *        not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public ImmutableEngineContext(
        /* @NonNull */
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        m_context = context;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.IEngineContext#getContext(java.lang.Class)
     */
    public <T> T getContext(
        final Class<T> type )
    {
        return m_context.getContext( type );
    }

    /*
     * @see org.gamegineer.engine.core.IEngineContext#getExtension(java.lang.Class)
     */
    public <T> T getExtension(
        final Class<T> type )
    {
        return m_context.getExtension( type );
    }

    /*
     * @see org.gamegineer.engine.core.IEngineContext#getState()
     */
    public IState getState()
    {
        return new ImmutableState( m_context.getState() );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A decorator that provides an immutable view of an engine state.
     */
    private static final class ImmutableState
        implements IState
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The state for which an immutable view is provided. */
        private final IState m_state;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ImmutableState} class.
         * 
         * @param state
         *        The state for which an immutable view is provided; must not be
         *        {@code null}.
         */
        ImmutableState(
            /* @NonNull */
            final IState state )
        {
            assert state != null;

            m_state = state;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.engine.core.IState#addAttribute(org.gamegineer.engine.core.AttributeName, java.lang.Object)
         */
        public void addAttribute(
            @SuppressWarnings( "unused" )
            final AttributeName name,
            @SuppressWarnings( "unused" )
            final Object value )
        {
            throw new UnsupportedOperationException();
        }

        /*
         * @see org.gamegineer.engine.core.IState#containsAttribute(org.gamegineer.engine.core.AttributeName)
         */
        public boolean containsAttribute(
            final AttributeName name )
        {
            return m_state.containsAttribute( name );
        }

        /*
         * @see org.gamegineer.engine.core.IState#getAttribute(org.gamegineer.engine.core.AttributeName)
         */
        public Object getAttribute(
            final AttributeName name )
        {
            return m_state.getAttribute( name );
        }

        /*
         * @see org.gamegineer.engine.core.IState#getAttributeNames()
         */
        public Set<AttributeName> getAttributeNames()
        {
            return m_state.getAttributeNames();
        }

        /*
         * @see org.gamegineer.engine.core.IState#removeAttribute(org.gamegineer.engine.core.AttributeName)
         */
        public void removeAttribute(
            @SuppressWarnings( "unused" )
            final AttributeName name )
        {
            throw new UnsupportedOperationException();
        }

        /*
         * @see org.gamegineer.engine.core.IState#setAttribute(org.gamegineer.engine.core.AttributeName, java.lang.Object)
         */
        public void setAttribute(
            @SuppressWarnings( "unused" )
            final AttributeName name,
            @SuppressWarnings( "unused" )
            final Object value )
        {
            throw new UnsupportedOperationException();
        }
    }
}
