/*
 * InternalStateChangeEvent.java
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
 * Created on Jun 2, 2008 at 9:06:33 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.util.Collection;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent;
import org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent;

/**
 * Implementation of
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class InternalStateChangeEvent
    extends StateChangeEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -6050043500609095775L;

    /** The state change event implementation to which all behavior is delegated. */
    private final IStateChangeEvent m_delegate;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalStateChangeEvent} class.
     * 
     * @param delegate
     *        The state change event implementation to which all behavior is
     *        delegated; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    private InternalStateChangeEvent(
        /* @NonNull */
        final IStateChangeEvent delegate )
    {
        super( delegate.getEngineContext() );

        m_delegate = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code InternalStateChangeEvent} class.
     * 
     * @param context
     *        The context representing the engine that fired the event; must not
     *        be {@code null}.
     * @param attributeChanges
     *        The collection of state attribute changes; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code InternalStateChangeEvent} class.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code context} is {@code null}.
     * @throws java.lang.NullPointerException
     *         If {@code attributeChanges} is {@code null}.
     */
    static InternalStateChangeEvent createStateChangeEvent(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final Map<AttributeName, IAttributeChange> attributeChanges )
    {
        assertArgumentLegal( context != null, "context" ); //$NON-NLS-1$
        assert attributeChanges != null;

        return new InternalStateChangeEvent( new StateChangeEventDelegate( context, attributeChanges ) );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent#containsAttributeChange(org.gamegineer.engine.core.AttributeName)
     */
    public boolean containsAttributeChange(
        final AttributeName name )
    {
        return m_delegate.containsAttributeChange( name );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent#getAttributeChange(org.gamegineer.engine.core.AttributeName)
     */
    public IAttributeChange getAttributeChange(
        final AttributeName name )
    {
        return m_delegate.getAttributeChange( name );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent#getAttributeChanges()
     */
    public Collection<IAttributeChange> getAttributeChanges()
    {
        return m_delegate.getAttributeChanges();
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateEvent#getEngineContext()
     */
    public IEngineContext getEngineContext()
    {
        return m_delegate.getEngineContext();
    }
}
