/*
 * AbstractContainerStrategy.java
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
 * Created on Aug 21, 2012 at 8:42:12 PM.
 */

package org.gamegineer.cards.internal.core.strategies;

import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.cards.internal.core.Loggers;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.ContainerLayoutRegistry;
import org.gamegineer.table.core.ContainerLayouts;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerStrategy;
import org.gamegineer.table.core.NoSuchContainerLayoutException;

/**
 * Superclass for all container strategies.
 */
@Immutable
abstract class AbstractContainerStrategy
    extends AbstractComponentStrategy
    implements IContainerStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerStrategy}
     * class.
     * 
     * @param id
     *        The component strategy identifier; must not be {@code null}.
     */
    AbstractContainerStrategy(
        /* @NonNull */
        final ComponentStrategyId id )
    {
        super( id );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IContainerStrategy#getDefaultLayout()
     */
    @Override
    public final IContainerLayout getDefaultLayout()
    {
        try
        {
            return ContainerLayoutRegistry.getContainerLayout( getDefaultLayoutId() );
        }
        catch( final NoSuchContainerLayoutException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractContainerStrategy_getDefaultLayout_notAvailable, e );
        }

        return ContainerLayouts.ABSOLUTE;
    }

    /**
     * Gets the identifier of the default container layout.
     * 
     * @return The identifier of the default container layout; never
     *         {@code null}.
     */
    /* @NonNull */
    abstract ContainerLayoutId getDefaultLayoutId();
}
