/*
 * CardSurfaceDesignRegistry.java
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
 * Created on Nov 17, 2009 at 9:32:25 PM.
 */

package org.gamegineer.table.internal.core.services.cardsurfacedesignregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.core.services.cardsurfacedesignregistry.ICardSurfaceDesignRegistry;
import org.gamegineer.table.internal.core.Activator;
import org.gamegineer.table.internal.core.BundleConstants;
import org.gamegineer.table.internal.core.Loggers;

/**
 * Implementation of
 * {@link org.gamegineer.table.core.services.cardsurfacedesignregistry.ICardSurfaceDesignRegistry}
 * .
 */
@ThreadSafe
public final class CardSurfaceDesignRegistry
    implements ICardSurfaceDesignRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension point attribute specifying the card surface design height. */
    private static final String ATTR_HEIGHT = "height"; //$NON-NLS-1$

    /**
     * The extension point attribute specifying the card surface design
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /** The extension point attribute specifying the card surface design width. */
    private static final String ATTR_WIDTH = "width"; //$NON-NLS-1$

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
     * @see org.gamegineer.table.core.services.cardsurfacedesignregistry.ICardSurfaceDesignRegistry#getCardSurfaceDesign(org.gamegineer.table.core.CardSurfaceDesignId)
     */
    @Override
    public ICardSurfaceDesign getCardSurfaceDesign(
        final CardSurfaceDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return getCardSurfaceDesignMap().get( id );
    }

    /**
     * Gets a map view of all card surface designs known by this object.
     * 
     * @return A map view of all card surface designs known by this object;
     *         never {@code null}.
     */
    /* @NonNull */
    private Map<CardSurfaceDesignId, ICardSurfaceDesign> getCardSurfaceDesignMap()
    {
        final Map<CardSurfaceDesignId, ICardSurfaceDesign> cardSurfaceDesigns = new HashMap<CardSurfaceDesignId, ICardSurfaceDesign>( cardSurfaceDesigns_ );
        for( final ICardSurfaceDesign cardSurfaceDesign : getForeignCardSurfaceDesigns() )
        {
            if( cardSurfaceDesigns.containsKey( cardSurfaceDesign.getId() ) )
            {
                Loggers.getDefaultLogger().warning( Messages.CardSurfaceDesignRegistry_getCardSurfaceDesignMap_duplicateId( cardSurfaceDesign.getId() ) );
            }
            else
            {
                cardSurfaceDesigns.put( cardSurfaceDesign.getId(), cardSurfaceDesign );
            }
        }
        return cardSurfaceDesigns;
    }

    /*
     * @see org.gamegineer.table.core.services.cardsurfacedesignregistry.ICardSurfaceDesignRegistry#getCardSurfaceDesigns()
     */
    @Override
    public Collection<ICardSurfaceDesign> getCardSurfaceDesigns()
    {
        return new ArrayList<ICardSurfaceDesign>( getCardSurfaceDesignMap().values() );
    }

    /**
     * Gets a collection of all foreign card surface designs not directly
     * managed by this object.
     * 
     * @return A collection of all foreign card surface designs not directly
     *         managed by this object; never {@code null}.
     */
    /* @NonNull */
    private static Collection<ICardSurfaceDesign> getForeignCardSurfaceDesigns()
    {
        final IExtensionRegistry extensionRegistry = Activator.getDefault().getExtensionRegistry();
        if( extensionRegistry == null )
        {
            Loggers.getDefaultLogger().warning( Messages.CardSurfaceDesignRegistry_getForeignCardSurfaceDesigns_noExtensionRegistry );
            return Collections.emptyList();
        }

        final Collection<ICardSurfaceDesign> cardSurfaceDesigns = new ArrayList<ICardSurfaceDesign>();
        for( final IConfigurationElement configurationElement : extensionRegistry.getConfigurationElementsFor( BundleConstants.SYMBOLIC_NAME, BundleConstants.CARD_SURFACE_DESIGNS_EXTENSION_POINT_SIMPLE_ID ) )
        {
            try
            {
                final CardSurfaceDesignId id = CardSurfaceDesignId.fromString( configurationElement.getAttribute( ATTR_ID ) );
                final int width = Integer.parseInt( configurationElement.getAttribute( ATTR_WIDTH ) );
                final int height = Integer.parseInt( configurationElement.getAttribute( ATTR_HEIGHT ) );
                cardSurfaceDesigns.add( TableFactory.createCardSurfaceDesign( id, width, height ) );
            }
            catch( final NumberFormatException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.CardSurfaceDesignRegistry_getForeignCardSurfaceDesigns_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            }
        }
        return cardSurfaceDesigns;
    }

    /*
     * @see org.gamegineer.table.core.services.cardsurfacedesignregistry.ICardSurfaceDesignRegistry#registerCardSurfaceDesign(org.gamegineer.table.core.ICardSurfaceDesign)
     */
    @Override
    public void registerCardSurfaceDesign(
        final ICardSurfaceDesign cardSurfaceDesign )
    {
        assertArgumentNotNull( cardSurfaceDesign, "cardSurfaceDesign" ); //$NON-NLS-1$

        cardSurfaceDesigns_.putIfAbsent( cardSurfaceDesign.getId(), cardSurfaceDesign );
    }

    /*
     * @see org.gamegineer.table.core.services.cardsurfacedesignregistry.ICardSurfaceDesignRegistry#unregisterCardSurfaceDesign(org.gamegineer.table.core.ICardSurfaceDesign)
     */
    @Override
    public void unregisterCardSurfaceDesign(
        final ICardSurfaceDesign cardSurfaceDesign )
    {
        assertArgumentNotNull( cardSurfaceDesign, "cardSurfaceDesign" ); //$NON-NLS-1$

        cardSurfaceDesigns_.remove( cardSurfaceDesign.getId(), cardSurfaceDesign );
    }
}
