/*
 * CardSurfaceDesignRegistry.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Nov 17, 2009 at 9:32:25 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.gamegineer.table.core.ICardSurfaceDesignRegistry;

/**
 * Implementation of
 * {@link org.gamegineer.table.core.ICardSurfaceDesignRegistry} .
 */
@ThreadSafe
public final class CardSurfaceDesignRegistry
    implements ICardSurfaceDesignRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of card surface designs directly managed by this object. */
    private final ConcurrentMap<CardSurfaceDesignId, ICardSurfaceDesign> cardSurfaceDesigns_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardSurfaceDesignRegistry}
     * class.
     */
    public CardSurfaceDesignRegistry()
    {
        cardSurfaceDesigns_ = new ConcurrentHashMap<CardSurfaceDesignId, ICardSurfaceDesign>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.cardsurfacedesignregistry.ICardSurfaceDesignRegistry#getCardSurfaceDesign(org.gamegineer.table.core.CardSurfaceDesignId)
     */
    @Override
    public ICardSurfaceDesign getCardSurfaceDesign(
        final CardSurfaceDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return cardSurfaceDesigns_.get( id );
    }

    /*
     * @see org.gamegineer.table.core.cardsurfacedesignregistry.ICardSurfaceDesignRegistry#getCardSurfaceDesigns()
     */
    @Override
    public Collection<ICardSurfaceDesign> getCardSurfaceDesigns()
    {
        return new ArrayList<ICardSurfaceDesign>( cardSurfaceDesigns_.values() );
    }

    /*
     * @see org.gamegineer.table.core.cardsurfacedesignregistry.ICardSurfaceDesignRegistry#registerCardSurfaceDesign(org.gamegineer.table.core.ICardSurfaceDesign)
     */
    @Override
    public void registerCardSurfaceDesign(
        final ICardSurfaceDesign cardSurfaceDesign )
    {
        assertArgumentNotNull( cardSurfaceDesign, "cardSurfaceDesign" ); //$NON-NLS-1$
        assertArgumentLegal( cardSurfaceDesigns_.putIfAbsent( cardSurfaceDesign.getId(), cardSurfaceDesign ) == null, "cardSurfaceDesign", Messages.CardSurfaceDesignRegistry_registerCardSurfaceDesign_cardSurfaceDesign_registered( cardSurfaceDesign.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered card surface design '%1$s'", cardSurfaceDesign.getId() ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.cardsurfacedesignregistry.ICardSurfaceDesignRegistry#unregisterCardSurfaceDesign(org.gamegineer.table.core.ICardSurfaceDesign)
     */
    @Override
    public void unregisterCardSurfaceDesign(
        final ICardSurfaceDesign cardSurfaceDesign )
    {
        assertArgumentNotNull( cardSurfaceDesign, "cardSurfaceDesign" ); //$NON-NLS-1$
        assertArgumentLegal( cardSurfaceDesigns_.remove( cardSurfaceDesign.getId(), cardSurfaceDesign ), "cardSurfaceDesign", Messages.CardSurfaceDesignRegistry_unregisterCardSurfaceDesign_cardSurfaceDesign_unregistered( cardSurfaceDesign.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Unregistered card surface design '%1$s'", cardSurfaceDesign.getId() ) ); //$NON-NLS-1$
    }
}
