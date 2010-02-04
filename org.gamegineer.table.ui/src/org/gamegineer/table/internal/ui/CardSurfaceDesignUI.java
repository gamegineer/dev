/*
 * CardSurfaceDesignUI.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Nov 20, 2009 at 11:59:42 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import javax.swing.Icon;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.ui.ICardSurfaceDesignUI;

/**
 * Implementation of {@link org.gamegineer.table.ui.ICardSurfaceDesignUI}.
 */
@Immutable
public final class CardSurfaceDesignUI
    implements ICardSurfaceDesignUI
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card surface design icon. */
    private final Icon icon_;

    /** The card surface design identifier. */
    private final CardSurfaceDesignId id_;

    /** The card surface design name. */
    private final String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardSurfaceDesignUI} class.
     * 
     * @param id
     *        The card surface design identifier; must not be {@code null}.
     * @param name
     *        The card surface design name; must not be {@code null}.
     * @param icon
     *        The card surface design icon; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id}, {@code name}, or {@code icon} is {@code null}.
     */
    public CardSurfaceDesignUI(
        /* @NonNull */
        final CardSurfaceDesignId id,
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

    /*
     * @see org.gamegineer.table.ui.ICardSurfaceDesignUI#getIcon()
     */
    public Icon getIcon()
    {
        return icon_;
    }

    /*
     * @see org.gamegineer.table.ui.ICardSurfaceDesignUI#getId()
     */
    public CardSurfaceDesignId getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.table.ui.ICardSurfaceDesignUI#getName()
     */
    public String getName()
    {
        return name_;
    }
}
