/*
 * CardPileStrategy.java
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
 * Created on Aug 1, 2012 at 8:14:11 PM.
 */

package org.gamegineer.cards.internal.core.strategies;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.jcip.annotations.Immutable;
import org.gamegineer.cards.core.CardPileOrientation;
import org.gamegineer.cards.core.CardsComponentStrategyIds;
import org.gamegineer.table.core.AbstractContainerStrategy;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.ContainerLayoutIds;

/**
 * A component strategy that represents a card pile.
 */
@Immutable
final class CardPileStrategy
    extends AbstractContainerStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of supported card pile orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( CardPileOrientation.values( CardPileOrientation.class ) ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileStrategy} class.
     */
    CardPileStrategy()
    {
        super( CardsComponentStrategyIds.CARD_PILE );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractContainerStrategy#getDefaultLayoutId()
     */
    @Override
    protected ContainerLayoutId getDefaultLayoutId()
    {
        return ContainerLayoutIds.STACKED;
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultOrientation()
     */
    @Override
    public ComponentOrientation getDefaultOrientation()
    {
        return CardPileOrientation.BASE;
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentStrategy#getDefaultSurfaceDesignId()
     */
    @Override
    protected ComponentSurfaceDesignId getDefaultSurfaceDesignId()
    {
        return ComponentSurfaceDesignId.fromString( "org.gamegineer.cards.cardPileSurfaceDesigns.default" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getSupportedOrientations()
     */
    @Override
    public Collection<ComponentOrientation> getSupportedOrientations()
    {
        return SUPPORTED_ORIENTATIONS;
    }
}
