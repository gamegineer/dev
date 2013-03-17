/*
 * DefaultDragStrategy.java
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
 * Created on Mar 8, 2013 at 10:04:35 PM.
 */

package org.gamegineer.table.core.dnd;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;

/**
 * Default implementation of {@link IDragStrategy} that only drags the source
 * component and allows it to be dropped on any container.
 */
@Immutable
public final class DefaultDragStrategy
    implements IDragStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The drag component. */
    private final IComponent dragComponent_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DefaultDragStrategy} class.
     * 
     * @param dragComponent
     *        The drag component; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code dragComponent} is {@code null}.
     */
    public DefaultDragStrategy(
        /* @NonNull */
        final IComponent dragComponent )
    {
        assertArgumentNotNull( dragComponent, "dragComponent" ); //$NON-NLS-1$

        dragComponent_ = dragComponent;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.dnd.IDragStrategy#canDrop(org.gamegineer.table.core.IContainer)
     */
    @Override
    public boolean canDrop(
        final IContainer dropContainer )
    {
        assertArgumentNotNull( dropContainer, "dropContainer" ); //$NON-NLS-1$

        return true;
    }

    /*
     * @see org.gamegineer.table.core.dnd.IDragStrategy#getDragComponents()
     */
    @Override
    public List<IComponent> getDragComponents()
    {
        return Collections.singletonList( dragComponent_ );
    }
}
