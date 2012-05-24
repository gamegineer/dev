/*
 * ComponentSurfaceDesignUI.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Apr 23, 2012 at 8:15:04 PM.
 */

package org.gamegineer.table.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import javax.swing.Icon;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

/**
 * A component surface design user interface.
 */
@Immutable
public final class ComponentSurfaceDesignUI
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component surface design icon. */
    private final Icon icon_;

    /** The component surface design identifier. */
    private final ComponentSurfaceDesignId id_;

    /** The component surface design name. */
    private final String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesignUI} class.
     * 
     * @param id
     *        The component surface design identifier; must not be {@code null}.
     * @param name
     *        The component surface design name; must not be {@code null}.
     * @param icon
     *        The component surface design icon; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id}, {@code name}, or {@code icon} is {@code null}.
     */
    public ComponentSurfaceDesignUI(
        /* @NonNull */
        final ComponentSurfaceDesignId id,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final Icon icon )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentNotNull( icon, "icon" ); //$NON-NLS-1$

        id_ = id;
        name_ = name;
        icon_ = icon;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component surface design icon.
     * 
     * @return The component surface design icon; never {@code null}.
     */
    /* @NonNull */
    public Icon getIcon()
    {
        return icon_;
    }

    /**
     * Gets the component surface design identifier.
     * 
     * @return The component surface design identifier; never {@code null}.
     */
    /* @NonNull */
    public ComponentSurfaceDesignId getId()
    {
        return id_;
    }

    /**
     * Gets the component surface design name.
     * 
     * @return The component surface design name; never {@code null}.
     */
    /* @NonNull */
    public String getName()
    {
        return name_;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ComponentSurfaceDesignUI[" ); //$NON-NLS-1$
        sb.append( "icon_=" ); //$NON-NLS-1$
        sb.append( icon_ );
        sb.append( ", id_=" ); //$NON-NLS-1$
        sb.append( id_ );
        sb.append( ", name_=" ); //$NON-NLS-1$
        sb.append( name_ );
        sb.append( "]" ); //$NON-NLS-1$
        return sb.toString();
    }
}
