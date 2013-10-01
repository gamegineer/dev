/*
 * AbsoluteLayout.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.core.impl.layouts;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.AbstractContainerLayout;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerLayout;

/**
 * Implementation of {@link IContainerLayout} that lays out the components of a
 * container at their absolute table coordinates.
 */
@Immutable
final class AbsoluteLayout
    extends AbstractContainerLayout
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbsoluteLayout} class.
     * 
     * @param id
     *        The container layout identifier; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    AbsoluteLayout(
        /* @NonNull */
        final ContainerLayoutId id )
    {
        super( id );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractContainerLayout#getComponentOffsetAt(org.gamegineer.table.core.IContainer, int)
     */
    @Override
    protected Dimension getComponentOffsetAt(
        final IContainer container,
        final int index )
    {
        assertArgumentNotNull( container, "container" ); //$NON-NLS-1$
        assertArgumentLegal( index >= 0, "index", NonNlsMessages.AbstractContainerLayout_getComponentOffsetAt_index_negative ); //$NON-NLS-1$

        final Point containerOrigin = container.getOrigin();
        final Point componentLocation = container.getComponent( index ).getLocation();
        return new Dimension( componentLocation.x - containerOrigin.x, componentLocation.y - containerOrigin.y );
    }
}
