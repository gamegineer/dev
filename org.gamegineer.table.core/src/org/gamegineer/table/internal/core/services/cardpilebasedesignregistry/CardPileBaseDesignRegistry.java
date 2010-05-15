/*
 * CardPileBaseDesignRegistry.java
 * Copyright 2008-2010 Gamegineer.org
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

package org.gamegineer.table.internal.core.services.cardpilebasedesignregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.table.core.CardPileBaseDesignFactory;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.ICardPileBaseDesign;
import org.gamegineer.table.core.services.cardpilebasedesignregistry.ICardPileBaseDesignRegistry;
import org.gamegineer.table.internal.core.Activator;
import org.gamegineer.table.internal.core.Loggers;

/**
 * Implementation of
 * {@link org.gamegineer.table.core.services.cardpilebasedesignregistry.ICardPileBaseDesignRegistry}
 * .
 */
@ThreadSafe
public final class CardPileBaseDesignRegistry
    implements ICardPileBaseDesignRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The extension point attribute specifying the card pile base design
     * height.
     */
    private static final String ATTR_HEIGHT = "height"; //$NON-NLS-1$

    /**
     * The extension point attribute specifying the card pile base design
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The extension point attribute specifying the card pile base design width.
     */
    private static final String ATTR_WIDTH = "width"; //$NON-NLS-1$

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
     * @see org.gamegineer.table.core.services.cardpilebasedesignregistry.ICardPileBaseDesignRegistry#getCardPileBaseDesign(org.gamegineer.table.core.CardPileBaseDesignId)
     */
    @Override
    public ICardPileBaseDesign getCardPileBaseDesign(
        final CardPileBaseDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return getCardPileBaseDesignMap().get( id );
    }

    /**
     * Gets a map view of all card pile base designs known by this object.
     * 
     * @return A map view of all card pile base designs known by this object;
     *         never {@code null}.
     */
    /* @NonNull */
    private Map<CardPileBaseDesignId, ICardPileBaseDesign> getCardPileBaseDesignMap()
    {
        final Map<CardPileBaseDesignId, ICardPileBaseDesign> cardPileBaseDesigns = new HashMap<CardPileBaseDesignId, ICardPileBaseDesign>( cardPileBaseDesigns_ );
        for( final ICardPileBaseDesign cardPileBaseDesign : getForeignCardPileBaseDesigns() )
        {
            if( cardPileBaseDesigns.containsKey( cardPileBaseDesign.getId() ) )
            {
                Loggers.DEFAULT.warning( Messages.CardPileBaseDesignRegistry_getCardPileBaseDesignMap_duplicateId( cardPileBaseDesign.getId() ) );
            }
            else
            {
                cardPileBaseDesigns.put( cardPileBaseDesign.getId(), cardPileBaseDesign );
            }
        }
        return cardPileBaseDesigns;
    }

    /*
     * @see org.gamegineer.table.core.services.cardpilebasedesignregistry.ICardPileBaseDesignRegistry#getCardPileBaseDesigns()
     */
    @Override
    public Collection<ICardPileBaseDesign> getCardPileBaseDesigns()
    {
        return new ArrayList<ICardPileBaseDesign>( getCardPileBaseDesignMap().values() );
    }

    /**
     * Gets a collection of all foreign card pile base designs not directly
     * managed by this object.
     * 
     * @return A collection of all foreign card pile base designs not directly
     *         managed by this object; never {@code null}.
     */
    /* @NonNull */
    private static Collection<ICardPileBaseDesign> getForeignCardPileBaseDesigns()
    {
        final Collection<ICardPileBaseDesign> cardPileBaseDesigns = new ArrayList<ICardPileBaseDesign>();
        for( final IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor( Activator.SYMBOLIC_NAME, Activator.EXTENSION_CARD_PILE_BASE_DESIGNS ) )
        {
            try
            {
                final CardPileBaseDesignId id = CardPileBaseDesignId.fromString( configurationElement.getAttribute( ATTR_ID ) );
                final int width = Integer.parseInt( configurationElement.getAttribute( ATTR_WIDTH ) );
                final int height = Integer.parseInt( configurationElement.getAttribute( ATTR_HEIGHT ) );
                cardPileBaseDesigns.add( CardPileBaseDesignFactory.createCardPileBaseDesign( id, width, height ) );
            }
            catch( final NumberFormatException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardPileBaseDesignRegistry_getForeignCardPileBaseDesigns_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            }
        }
        return cardPileBaseDesigns;
    }

    /*
     * @see org.gamegineer.table.core.services.cardpilebasedesignregistry.ICardPileBaseDesignRegistry#registerCardPileBaseDesign(org.gamegineer.table.core.ICardPileBaseDesign)
     */
    @Override
    public void registerCardPileBaseDesign(
        final ICardPileBaseDesign cardPileBaseDesign )
    {
        assertArgumentNotNull( cardPileBaseDesign, "cardPileBaseDesign" ); //$NON-NLS-1$

        cardPileBaseDesigns_.putIfAbsent( cardPileBaseDesign.getId(), cardPileBaseDesign );
    }

    /*
     * @see org.gamegineer.table.core.services.cardpilebasedesignregistry.ICardPileBaseDesignRegistry#unregisterCardPileBaseDesign(org.gamegineer.table.core.ICardPileBaseDesign)
     */
    @Override
    public void unregisterCardPileBaseDesign(
        final ICardPileBaseDesign cardPileBaseDesign )
    {
        assertArgumentNotNull( cardPileBaseDesign, "cardPileBaseDesign" ); //$NON-NLS-1$

        cardPileBaseDesigns_.remove( cardPileBaseDesign.getId(), cardPileBaseDesign );
    }
}
