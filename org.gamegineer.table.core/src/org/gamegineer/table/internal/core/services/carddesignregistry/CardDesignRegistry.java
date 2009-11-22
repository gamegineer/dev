/*
 * CardDesignRegistry.java
 * Copyright 2008-2009 Gamegineer.org
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

package org.gamegineer.table.internal.core.services.carddesignregistry;

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
import org.gamegineer.table.core.CardDesignFactory;
import org.gamegineer.table.core.CardDesignId;
import org.gamegineer.table.core.ICardDesign;
import org.gamegineer.table.core.services.carddesignregistry.ICardDesignRegistry;
import org.gamegineer.table.internal.core.Activator;
import org.gamegineer.table.internal.core.Loggers;

/**
 * Implementation of
 * {@link org.gamegineer.table.core.services.carddesignregistry.ICardDesignRegistry}
 * .
 */
@ThreadSafe
public final class CardDesignRegistry
    implements ICardDesignRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension point attribute specifying the card design height. */
    private static final String ATTR_HEIGHT = "height"; //$NON-NLS-1$

    /** The extension point attribute specifying the card design identifier. */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /** The extension point attribute specifying the card design width. */
    private static final String ATTR_WIDTH = "width"; //$NON-NLS-1$

    /** The collection of card designs directly managed by this object. */
    private final ConcurrentMap<CardDesignId, ICardDesign> cardDesigns_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardDesignRegistry} class.
     */
    public CardDesignRegistry()
    {
        cardDesigns_ = new ConcurrentHashMap<CardDesignId, ICardDesign>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.services.carddesignregistry.ICardDesignRegistry#getCardDesign(org.gamegineer.table.core.CardDesignId)
     */
    public ICardDesign getCardDesign(
        final CardDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return getCardDesignMap().get( id );
    }

    /**
     * Gets a map view of all card designs known by this object.
     * 
     * @return A map view of all card designs known by this object; never
     *         {@code null}.
     */
    /* @NonNull */
    private Map<CardDesignId, ICardDesign> getCardDesignMap()
    {
        final Map<CardDesignId, ICardDesign> cardDesigns = new HashMap<CardDesignId, ICardDesign>( cardDesigns_ );
        for( final ICardDesign cardDesign : getForeignCardDesigns() )
        {
            if( cardDesigns.containsKey( cardDesign.getId() ) )
            {
                Loggers.DEFAULT.warning( Messages.CardDesignRegistry_getCardDesignMap_duplicateId( cardDesign.getId() ) );
            }
            else
            {
                cardDesigns.put( cardDesign.getId(), cardDesign );
            }
        }
        return cardDesigns;
    }

    /*
     * @see org.gamegineer.table.core.services.carddesignregistry.ICardDesignRegistry#getCardDesigns()
     */
    public Collection<ICardDesign> getCardDesigns()
    {
        return new ArrayList<ICardDesign>( getCardDesignMap().values() );
    }

    /**
     * Gets a collection of all foreign card designs not directly managed by
     * this object.
     * 
     * @return A collection of all foreign card designs not directly managed by
     *         this object; never {@code null}.
     */
    /* @NonNull */
    private static Collection<ICardDesign> getForeignCardDesigns()
    {
        final Collection<ICardDesign> cardDesigns = new ArrayList<ICardDesign>();
        for( final IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor( Activator.SYMBOLIC_NAME, Activator.EXTENSION_CARD_DESIGNS ) )
        {
            try
            {
                final CardDesignId id = CardDesignId.fromString( configurationElement.getAttribute( ATTR_ID ) );
                final int width = Integer.parseInt( configurationElement.getAttribute( ATTR_WIDTH ) );
                final int height = Integer.parseInt( configurationElement.getAttribute( ATTR_HEIGHT ) );
                cardDesigns.add( CardDesignFactory.createCardDesign( id, width, height ) );
            }
            catch( final NumberFormatException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardDesignRegistry_getForeignCardDesigns_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            }
        }
        return cardDesigns;
    }

    /*
     * @see org.gamegineer.table.core.services.carddesignregistry.ICardDesignRegistry#registerCardDesign(org.gamegineer.table.core.ICardDesign)
     */
    public void registerCardDesign(
        final ICardDesign cardDesign )
    {
        assertArgumentNotNull( cardDesign, "cardDesign" ); //$NON-NLS-1$

        cardDesigns_.putIfAbsent( cardDesign.getId(), cardDesign );
    }

    /*
     * @see org.gamegineer.table.core.services.carddesignregistry.ICardDesignRegistry#unregisterCardDesign(org.gamegineer.table.core.ICardDesign)
     */
    public void unregisterCardDesign(
        final ICardDesign cardDesign )
    {
        assertArgumentNotNull( cardDesign, "cardDesign" ); //$NON-NLS-1$

        cardDesigns_.remove( cardDesign.getId(), cardDesign );
    }
}
