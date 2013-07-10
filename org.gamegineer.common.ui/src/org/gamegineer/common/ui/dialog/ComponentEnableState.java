/*
 * ComponentEnableState.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Nov 20, 2010 at 10:07:50 PM.
 */

package org.gamegineer.common.ui.dialog;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;
import net.jcip.annotations.Immutable;

/**
 * A class to save and restore the enable/disable state of a component and its
 * descendants.
 */
@Immutable
public final class ComponentEnableState
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of components that have been disabled. */
    private final Collection<Component> components_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentEnableState} class.
     * 
     * @param components
     *        The collection of components that have been disabled; must not be
     *        {@code null}.
     */
    private ComponentEnableState(
        /* @NonNull */
        final Collection<Component> components )
    {
        assert components != null;

        components_ = components;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Disables the specified component and its descendants.
     * 
     * @param component
     *        The component to disable; must not be {@code null}.
     * 
     * @return A memento representing the previous enable/disable state of the
     *         component and its descendants; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    /* @NonNull */
    public static ComponentEnableState disable(
        /* @NonNull */
        final Component component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        final Collection<Component> components = new ArrayList<>();
        disable( component, components );

        return new ComponentEnableState( components );
    }

    /**
     * Disables the specified component and its descendants.
     * 
     * @param component
     *        The component to disable; must not be {@code null}.
     * @param components
     *        The collection of components that have been disabled; must not be
     *        {@code null}.
     */
    private static void disable(
        /* @NonNull */
        final Component component,
        /* @NonNull */
        final Collection<Component> components )
    {
        assert component != null;
        assert components != null;

        if( component.isEnabled() )
        {
            components.add( component );
            component.setEnabled( false );
        }

        if( component instanceof Container )
        {
            final Container container = (Container)component;
            for( int index = 0, count = container.getComponentCount(); index < count; ++index )
            {
                disable( container.getComponent( index ), components );
            }
        }
    }

    /**
     * Restores the previous component enable/disable state saved in this
     * object.
     */
    public void restore()
    {
        for( final Component component : components_ )
        {
            component.setEnabled( true );
        }
    }
}
