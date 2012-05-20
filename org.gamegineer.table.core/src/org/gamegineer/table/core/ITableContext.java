/*
 * ITableContext.java
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
 * Created on May 19, 2012 at 9:21:05 PM.
 */

package org.gamegineer.table.core;

import java.awt.Dimension;
import java.util.concurrent.locks.Lock;

// TODO: rename to ITableRuntime or something equivalent

/**
 * The execution context for one or more virtual game tables.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ITableContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card.
     * 
     * @return A new card; never {@code null}.
     */
    /* @NonNull */
    public ICard createCard();

    /**
     * Creates a new card pile.
     * 
     * @return A new card pile; never {@code null}.
     */
    /* @NonNull */
    public ICardPile createCardPile();

    /**
     * Creates a new component surface design.
     * 
     * @param id
     *        The component surface design identifier; must not be {@code null}.
     * @param size
     *        The component surface design size in table coordinates; no
     *        component may be negative.
     * 
     * @return A new component surface design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any component of {@code size} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} or {@code size} is {@code null}.
     */
    /* @NonNull */
    public IComponentSurfaceDesign createComponentSurfaceDesign(
        /* @NonNull */
        ComponentSurfaceDesignId id,
        /* @NonNull */
        Dimension size );

    /**
     * Creates a new component surface design.
     * 
     * @param id
     *        The component surface design identifier; must not be {@code null}.
     * @param width
     *        The component surface design width in table coordinates; must not
     *        be negative.
     * @param height
     *        The component surface design height in table coordinates; must not
     *        be negative.
     * 
     * @return A new component surface design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public IComponentSurfaceDesign createComponentSurfaceDesign(
        /* @NonNull */
        ComponentSurfaceDesignId id,
        int width,
        int height );

    /**
     * Creates a new table.
     * 
     * @return A new table; never {@code null}.
     */
    /* @NonNull */
    public ITable createTable();

    /**
     * Gets the table context lock.
     * 
     * <p>
     * Any modification to a table or table component must be executed while the
     * table context lock is held. All public methods of all public types in
     * this package will acquire the table context lock as needed. Clients must
     * manually acquire the table context lock when invoking multiple methods
     * that should be treated as an atomic operation.
     * </p>
     * 
     * @return The table context lock; never {@code null}.
     */
    /* @NonNull */
    public Lock getLock();
}
