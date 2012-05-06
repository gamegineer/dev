/*
 * AbstractContainerLayout.java
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
 * Created on May 5, 2012 at 10:08:36 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerLayout;

/**
 * Superclass for implementations of {@link IContainerLayout}.
 */
@Immutable
abstract class AbstractContainerLayout
    implements IContainerLayout
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerLayout} class.
     */
    AbstractContainerLayout()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the offset from the container origin in table coordinates of the
     * component at the specified index.
     * 
     * @param index
     *        The component index; must be non-negative.
     * 
     * @return The offset from the container origin in table coordinates of the
     *         component at the specified index; never {@code null}.
     */
    /* @NonNull */
    abstract Dimension getComponentOffsetAt(
        int index );

    /*
     * @see org.gamegineer.table.core.IContainerLayout#layout(org.gamegineer.table.core.IContainer)
     */
    @Override
    public final void layout(
        final IContainer container )
    {
        assertArgumentNotNull( container, "container" ); //$NON-NLS-1$

        final Point containerOrigin = container.getOrigin();
        final Point componentLocation = new Point();
        for( int index = 0, size = container.getComponentCount(); index < size; ++index )
        {
            componentLocation.setLocation( containerOrigin );
            final Dimension componentOffset = getComponentOffsetAt( index );
            componentLocation.translate( componentOffset.width, componentOffset.height );
            container.getComponent( index ).setLocation( componentLocation );
        }
    }
}
