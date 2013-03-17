/*
 * NullDragStrategyFactory.java
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
 * Created on Mar 16, 2013 at 9:46:20 PM.
 */

package org.gamegineer.table.core.dnd;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.IComponent;

/**
 * Implementation of {@link IDragStrategyFactory} for creating instances of
 * {@link NullDragStrategy}.
 */
@Immutable
public final class NullDragStrategyFactory
    implements IDragStrategyFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final NullDragStrategyFactory INSTANCE = new NullDragStrategyFactory();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullDragStrategyFactory} class.
     */
    private NullDragStrategyFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.dnd.IDragStrategyFactory#createDragStrategy(org.gamegineer.table.core.IComponent)
     */
    @Override
    public IDragStrategy createDragStrategy(
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        return NullDragStrategy.INSTANCE;
    }
}
