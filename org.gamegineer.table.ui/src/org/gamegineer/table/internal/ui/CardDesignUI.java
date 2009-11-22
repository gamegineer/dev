/*
 * CardDesignUI.java
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
 * Created on Nov 20, 2009 at 11:59:42 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import javax.swing.Icon;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.CardDesignId;
import org.gamegineer.table.ui.ICardDesignUI;

/**
 * Implementation of {@link org.gamegineer.table.ui.ICardDesignUI}.
 */
@Immutable
public final class CardDesignUI
    implements ICardDesignUI
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card design icon. */
    private final Icon icon_;

    /** The card design identifier. */
    private final CardDesignId id_;

    /** The card design name. */
    private final String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardDesignUI} class.
     * 
     * @param id
     *        The card design identifier; must not be {@code null}.
     * @param name
     *        The card design name; must not be {@code null}.
     * @param icon
     *        The card design icon; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id}, {@code name}, or {@code icon} is {@code null}.
     */
    public CardDesignUI(
        /* @NonNull */
        final CardDesignId id,
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
     * @see org.gamegineer.table.ui.ICardDesignUI#getIcon()
     */
    public Icon getIcon()
    {
        return icon_;
    }

    /*
     * @see org.gamegineer.table.ui.ICardDesignUI#getId()
     */
    public CardDesignId getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.table.ui.ICardDesignUI#getName()
     */
    public String getName()
    {
        return name_;
    }
}
