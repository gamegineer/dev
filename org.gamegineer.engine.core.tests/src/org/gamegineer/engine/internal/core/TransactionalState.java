/*
 * TransactionalState.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Apr 22, 2008 at 11:02:11 PM.
 */

package org.gamegineer.engine.internal.core;

import java.util.Set;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IState;

/**
 * A decorator for {@link org.gamegineer.engine.internal.core.State} to ensure a
 * transaction is active for those operations which require it.
 */
final class TransactionalState
    implements IState
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The state being decorated. */
    private final State m_state;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TransactionalState} class.
     * 
     * @param state
     *        The state to be decorated; must not be {@code null}.
     */
    TransactionalState(
        /* @NonNull */
        final State state )
    {
        assert state != null;

        m_state = state;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.IState#addAttribute(org.gamegineer.engine.core.AttributeName, java.lang.Object)
     */
    public void addAttribute(
        final AttributeName name,
        final Object value )
    {
        try
        {
            m_state.beginTransaction();
            m_state.addAttribute( name, value );
            m_state.commitTransaction();
        }
        finally
        {
            if( m_state.isTransactionActive() )
            {
                m_state.rollbackTransaction();
            }
        }
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
        final AttributeName name )
    {
        try
        {
            m_state.beginTransaction();
            m_state.removeAttribute( name );
            m_state.commitTransaction();
        }
        finally
        {
            if( m_state.isTransactionActive() )
            {
                m_state.rollbackTransaction();
            }
        }
    }

    /*
     * @see org.gamegineer.engine.core.IState#setAttribute(org.gamegineer.engine.core.AttributeName, java.lang.Object)
     */
    public void setAttribute(
        final AttributeName name,
        final Object value )
    {
        try
        {
            m_state.beginTransaction();
            m_state.setAttribute( name, value );
            m_state.commitTransaction();
        }
        finally
        {
            if( m_state.isTransactionActive() )
            {
                m_state.rollbackTransaction();
            }
        }
    }
}
