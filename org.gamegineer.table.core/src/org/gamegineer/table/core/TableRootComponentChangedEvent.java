/*
 * TableRootComponentChangedEvent.java
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
 * Created on Jun 30, 2012 at 9:49:24 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;

/**
 * An event used to notify listeners that the root component of a table has
 * changed.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public class TableRootComponentChangedEvent
    extends TableEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 5836639439009358623L;

    /** The new root component associated with the event. */
    private final IComponent newRootComponent_;

    /** The old root component associated with the event. */
    private final IComponent oldRootComponent_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableRootComponentChangedEvent}
     * class.
     * 
     * @param source
     *        The table that fired the event; must not be {@code null}.
     * @param oldRootComponent
     *        The old root component associated with the event; must not be
     *        {@code null}.
     * @param newRootComponent
     *        The new root component associated with the event; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null}.
     * @throws java.lang.NullPointerException
     *         If {@code oldRootComponent} or {@code newRootComponent} is
     *         {@code null}.
     */
    public TableRootComponentChangedEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final ITable source,
        /* @NonNull */
        final IComponent oldRootComponent,
        /* @NonNull */
        final IComponent newRootComponent )
    {
        super( source );

        assertArgumentNotNull( oldRootComponent, "oldRootComponent" ); //$NON-NLS-1$
        assertArgumentNotNull( newRootComponent, "newRootComponent" ); //$NON-NLS-1$

        newRootComponent_ = newRootComponent;
        oldRootComponent_ = oldRootComponent;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the new root component associated with the event.
     * 
     * @return The new root component associated with the event; never
     *         {@code null}.
     */
    /* @NonNull */
    public final IComponent getNewRootComponent()
    {
        return newRootComponent_;
    }

    /**
     * Gets the old root component associated with the event.
     * 
     * @return The old root component associated with the event; never
     *         {@code null}.
     */
    /* @NonNull */
    public final IComponent getOldRootComponent()
    {
        return oldRootComponent_;
    }
}
