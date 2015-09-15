/*
 * PassiveTransportLayer.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jan 18, 2011 at 8:31:02 PM.
 */

package org.gamegineer.table.internal.net.impl.transport.fake;

import java.util.concurrent.Future;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayer;

/**
 * Fake implementation of {@link ITransportLayer} for a passive connection.
 */
@Immutable
final class PassiveTransportLayer
    implements ITransportLayer
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PassiveTransportLayer} class.
     */
    PassiveTransportLayer()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#beginClose()
     */
    @Override
    public Future<@Nullable Void> beginClose()
    {
        return new SynchronousFuture<>();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#beginOpen(java.lang.String, int)
     */
    @Override
    public Future<@Nullable Void> beginOpen(
        @SuppressWarnings( "unused" )
        final String hostName,
        @SuppressWarnings( "unused" )
        final int port )
    {
        return new SynchronousFuture<>();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#endClose(java.util.concurrent.Future)
     */
    @Override
    public void endClose(
        @SuppressWarnings( "unused" )
        final Future<@Nullable Void> future )
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#endOpen(java.util.concurrent.Future)
     */
    @Override
    public void endOpen(
        @SuppressWarnings( "unused" )
        final Future<@Nullable Void> future )
    {
        // do nothing
    }
}
