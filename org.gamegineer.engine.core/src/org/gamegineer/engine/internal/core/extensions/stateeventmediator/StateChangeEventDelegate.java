/*
 * StateChangeEventDelegate.java
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
 * Created on Jun 2, 2008 at 9:05:18 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent;

/**
 * An implementation of
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent}
 * to which implementations of
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.StateChangeEvent}
 * can delegate their behavior.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class StateChangeEventDelegate
    extends StateEventDelegate
    implements IStateChangeEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of state attribute changes. */
    private final Map<AttributeName, IAttributeChange> m_attributeChanges;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateChangeEventDelegate} class.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * @param attributeChanges
     *        The collection of state attribute changes; must not be
     *        {@code null}.
     */
    StateChangeEventDelegate(
        /* @NonNull */
        final IEngineContext context,
        /* @NonNull */
        final Map<AttributeName, IAttributeChange> attributeChanges )
    {
        super( context );

        assert attributeChanges != null;

        m_attributeChanges = Collections.unmodifiableMap( attributeChanges );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent#containsAttributeChange(org.gamegineer.engine.core.AttributeName)
     */
    public boolean containsAttributeChange(
        final AttributeName name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return m_attributeChanges.containsKey( name );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent#getAttributeChange(org.gamegineer.engine.core.AttributeName)
     */
    public IAttributeChange getAttributeChange(
        final AttributeName name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( m_attributeChanges.containsKey( name ), "name", Messages.StateChangeEventDelegate_attributeChange_absent( name ) ); //$NON-NLS-1$

        return m_attributeChanges.get( name );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent#getAttributeChanges()
     */
    public Collection<IAttributeChange> getAttributeChanges()
    {
        return m_attributeChanges.values();
    }
}
