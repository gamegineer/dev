/*
 * CardPileBaseDesignRegistry.java
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
 * Created on Jan 19, 2010 at 11:26:51 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.ICardPileBaseDesign;
import org.gamegineer.table.core.ICardPileBaseDesignRegistry;

/**
 * Implementation of
 * {@link org.gamegineer.table.core.ICardPileBaseDesignRegistry} .
 */
@ThreadSafe
public final class CardPileBaseDesignRegistry
    implements ICardPileBaseDesignRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of card pile base designs directly managed by this object.
     */
    private final ConcurrentMap<CardPileBaseDesignId, ICardPileBaseDesign> cardPileBaseDesigns_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileBaseDesignRegistry}
     * class.
     */
    public CardPileBaseDesignRegistry()
    {
        cardPileBaseDesigns_ = new ConcurrentHashMap<CardPileBaseDesignId, ICardPileBaseDesign>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.cardpilebasedesignregistry.ICardPileBaseDesignRegistry#getCardPileBaseDesign(org.gamegineer.table.core.CardPileBaseDesignId)
     */
    @Override
    public ICardPileBaseDesign getCardPileBaseDesign(
        final CardPileBaseDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return cardPileBaseDesigns_.get( id );
    }

    /*
     * @see org.gamegineer.table.core.cardpilebasedesignregistry.ICardPileBaseDesignRegistry#getCardPileBaseDesigns()
     */
    @Override
    public Collection<ICardPileBaseDesign> getCardPileBaseDesigns()
    {
        return new ArrayList<ICardPileBaseDesign>( cardPileBaseDesigns_.values() );
    }

    /*
     * @see org.gamegineer.table.core.cardpilebasedesignregistry.ICardPileBaseDesignRegistry#registerCardPileBaseDesign(org.gamegineer.table.core.ICardPileBaseDesign)
     */
    @Override
    public void registerCardPileBaseDesign(
        final ICardPileBaseDesign cardPileBaseDesign )
    {
        assertArgumentNotNull( cardPileBaseDesign, "cardPileBaseDesign" ); //$NON-NLS-1$
        assertArgumentLegal( cardPileBaseDesigns_.putIfAbsent( cardPileBaseDesign.getId(), cardPileBaseDesign ) == null, "cardPileBaseDesign", Messages.CardPileBaseDesignRegistry_registerCardPileBaseDesign_cardPileBaseDesign_registered( cardPileBaseDesign.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_SERVICES_CARD_PILE_BASE_DESIGN_REGISTRY, String.format( "Registered card pile base design '%1$s'", cardPileBaseDesign.getId() ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.cardpilebasedesignregistry.ICardPileBaseDesignRegistry#unregisterCardPileBaseDesign(org.gamegineer.table.core.ICardPileBaseDesign)
     */
    @Override
    public void unregisterCardPileBaseDesign(
        final ICardPileBaseDesign cardPileBaseDesign )
    {
        assertArgumentNotNull( cardPileBaseDesign, "cardPileBaseDesign" ); //$NON-NLS-1$
        assertArgumentLegal( cardPileBaseDesigns_.remove( cardPileBaseDesign.getId(), cardPileBaseDesign ), "cardPileBaseDesign", Messages.CardPileBaseDesignRegistry_unregisterCardPileBaseDesign_cardPileBaseDesign_unregistered( cardPileBaseDesign.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_SERVICES_CARD_PILE_BASE_DESIGN_REGISTRY, String.format( "Unregistered card pile base design '%1$s'", cardPileBaseDesign.getId() ) ); //$NON-NLS-1$
    }
}
