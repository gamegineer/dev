/*
 * AbsoluteContainerLayout.java
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
 * Created on Jul 4, 2012 at 7:52:26 PM.
 */

package org.gamegineer.table.internal.core;

import java.awt.Dimension;
import java.awt.Point;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerLayout;

/**
 * Implementation of {@link IContainerLayout} that lays out the components of a
 * container at their absolute table coordinates.
 */
@Immutable
public final class AbsoluteContainerLayout
    extends AbstractContainerLayout
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbsoluteContainerLayout} class.
     */
    public AbsoluteContainerLayout()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.core.AbstractContainerLayout#getComponentOffsetAt(org.gamegineer.table.core.IContainer, int)
     */
    @Override
    Dimension getComponentOffsetAt(
        final IContainer container,
        final int index )
    {
        assert container != null;
        assert index >= 0;

        final Point containerOrigin = container.getOrigin();
        final Point componentLocation = container.getComponent( index ).getLocation();
        return new Dimension( containerOrigin.x - componentLocation.x, containerOrigin.y - componentLocation.y );
    }
}
