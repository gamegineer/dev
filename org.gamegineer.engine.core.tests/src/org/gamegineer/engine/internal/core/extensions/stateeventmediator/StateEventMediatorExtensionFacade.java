/*
 * StateEventMediatorExtensionFacade.java
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
 * Created on May 2, 2009 at 11:27:54 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import java.lang.reflect.Field;
import java.util.List;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener;
import org.gamegineer.engine.core.util.attribute.Attribute;

/**
 * A class for transparently accessing inaccessible members of the
 * {@code StateEventMediatorExtension} class for testing purposes.
 */
final class StateEventMediatorExtensionFacade
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code StateEventMediatorExtensionFacade} class.
     */
    private StateEventMediatorExtensionFacade()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Accessible facade for the {@code ATTR_ACTIVE_STATE_LISTENERS} class
     * field.
     * 
     * @return The value of the {@code ATTR_ACTIVE_STATE_LISTENERS} class field;
     *         never {@code null}.
     */
    /* @NonNull */
    static String ATTR_ACTIVE_STATE_LISTENERS()
    {
        try
        {
            final Field field = StateEventMediatorExtension.class.getDeclaredField( "ATTR_ACTIVE_STATE_LISTENERS" ); //$NON-NLS-1$
            field.setAccessible( true );
            return (String)field.get( null );
        }
        catch( final Exception e )
        {
            throw new AssertionError( "failed to read 'ATTR_ACTIVE_STATE_LISTENERS'" ); //$NON-NLS-1$
        }
    }

    /**
     * Accessible facade for the {@code STATE_LISTENERS_ATTRIBUTE} class field.
     * 
     * @return The value of the {@code STATE_LISTENERS_ATTRIBUTE} class field;
     *         never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "unchecked" )
    static Attribute<List<IStateListener>> STATE_LISTENERS_ATTRIBUTE()
    {
        try
        {
            final Field field = StateEventMediatorExtension.class.getDeclaredField( "STATE_LISTENERS_ATTRIBUTE" ); //$NON-NLS-1$
            field.setAccessible( true );
            return (Attribute<List<IStateListener>>)field.get( null );
        }
        catch( final Exception e )
        {
            throw new AssertionError( "failed to read 'STATE_LISTENERS_ATTRIBUTE'" ); //$NON-NLS-1$
        }
    }
}
