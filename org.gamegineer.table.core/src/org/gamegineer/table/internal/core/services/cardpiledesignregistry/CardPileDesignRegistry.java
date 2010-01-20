/*
 * CardPileDesignRegistry.java
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

package org.gamegineer.table.internal.core.services.cardpiledesignregistry;

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
import org.gamegineer.table.core.CardPileDesignFactory;
import org.gamegineer.table.core.CardPileDesignId;
import org.gamegineer.table.core.ICardPileDesign;
import org.gamegineer.table.core.services.cardpiledesignregistry.ICardPileDesignRegistry;
import org.gamegineer.table.internal.core.Activator;
import org.gamegineer.table.internal.core.Loggers;

/**
 * Implementation of
 * {@link org.gamegineer.table.core.services.cardpiledesignregistry.ICardPileDesignRegistry}
 * .
 */
@ThreadSafe
public final class CardPileDesignRegistry
    implements ICardPileDesignRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension point attribute specifying the card pile design height. */
    private static final String ATTR_HEIGHT = "height"; //$NON-NLS-1$

    /**
     * The extension point attribute specifying the card pile design identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /** The extension point attribute specifying the card pile design width. */
    private static final String ATTR_WIDTH = "width"; //$NON-NLS-1$

    /** The collection of card pile designs directly managed by this object. */
    private final ConcurrentMap<CardPileDesignId, ICardPileDesign> cardPileDesigns_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileDesignRegistry} class.
     */
    public CardPileDesignRegistry()
    {
        cardPileDesigns_ = new ConcurrentHashMap<CardPileDesignId, ICardPileDesign>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.services.cardpiledesignregistry.ICardPileDesignRegistry#getCardPileDesign(org.gamegineer.table.core.CardPileDesignId)
     */
    public ICardPileDesign getCardPileDesign(
        final CardPileDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return getCardPileDesignMap().get( id );
    }

    /**
     * Gets a map view of all card pile designs known by this object.
     * 
     * @return A map view of all card pile designs known by this object; never
     *         {@code null}.
     */
    /* @NonNull */
    private Map<CardPileDesignId, ICardPileDesign> getCardPileDesignMap()
    {
        final Map<CardPileDesignId, ICardPileDesign> cardPileDesigns = new HashMap<CardPileDesignId, ICardPileDesign>( cardPileDesigns_ );
        for( final ICardPileDesign cardPileDesign : getForeignCardPileDesigns() )
        {
            if( cardPileDesigns.containsKey( cardPileDesign.getId() ) )
            {
                Loggers.DEFAULT.warning( Messages.CardPileDesignRegistry_getCardPileDesignMap_duplicateId( cardPileDesign.getId() ) );
            }
            else
            {
                cardPileDesigns.put( cardPileDesign.getId(), cardPileDesign );
            }
        }
        return cardPileDesigns;
    }

    /*
     * @see org.gamegineer.table.core.services.cardpiledesignregistry.ICardPileDesignRegistry#getCardPileDesigns()
     */
    public Collection<ICardPileDesign> getCardPileDesigns()
    {
        return new ArrayList<ICardPileDesign>( getCardPileDesignMap().values() );
    }

    /**
     * Gets a collection of all foreign card pile designs not directly managed
     * by this object.
     * 
     * @return A collection of all foreign card pile designs not directly
     *         managed by this object; never {@code null}.
     */
    /* @NonNull */
    private static Collection<ICardPileDesign> getForeignCardPileDesigns()
    {
        final Collection<ICardPileDesign> cardPileDesigns = new ArrayList<ICardPileDesign>();
        for( final IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor( Activator.SYMBOLIC_NAME, Activator.EXTENSION_CARD_PILE_DESIGNS ) )
        {
            try
            {
                final CardPileDesignId id = CardPileDesignId.fromString( configurationElement.getAttribute( ATTR_ID ) );
                final int width = Integer.parseInt( configurationElement.getAttribute( ATTR_WIDTH ) );
                final int height = Integer.parseInt( configurationElement.getAttribute( ATTR_HEIGHT ) );
                cardPileDesigns.add( CardPileDesignFactory.createCardPileDesign( id, width, height ) );
            }
            catch( final NumberFormatException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardPileDesignRegistry_getForeignCardPileDesigns_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            }
        }
        return cardPileDesigns;
    }

    /*
     * @see org.gamegineer.table.core.services.cardpiledesignregistry.ICardPileDesignRegistry#registerCardPileDesign(org.gamegineer.table.core.ICardPileDesign)
     */
    public void registerCardPileDesign(
        final ICardPileDesign cardPileDesign )
    {
        assertArgumentNotNull( cardPileDesign, "cardPileDesign" ); //$NON-NLS-1$

        cardPileDesigns_.putIfAbsent( cardPileDesign.getId(), cardPileDesign );
    }

    /*
     * @see org.gamegineer.table.core.services.cardpiledesignregistry.ICardPileDesignRegistry#unregisterCardPileDesign(org.gamegineer.table.core.ICardPileDesign)
     */
    public void unregisterCardPileDesign(
        final ICardPileDesign cardPileDesign )
    {
        assertArgumentNotNull( cardPileDesign, "cardPileDesign" ); //$NON-NLS-1$

        cardPileDesigns_.remove( cardPileDesign.getId(), cardPileDesign );
    }
}
