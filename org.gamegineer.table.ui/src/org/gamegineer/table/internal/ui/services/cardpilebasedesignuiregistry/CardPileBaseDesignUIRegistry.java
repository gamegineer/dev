/*
 * CardPileBaseDesignUIRegistry.java
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
 * Created on Jan 23, 2010 at 9:37:47 PM.
 */

package org.gamegineer.table.internal.ui.services.cardpilebasedesignuiregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import javax.swing.Icon;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.Services;
import org.gamegineer.table.internal.ui.util.swing.IconProxy;
import org.gamegineer.table.ui.CardPileBaseDesignUIFactory;
import org.gamegineer.table.ui.ICardPileBaseDesignUI;
import org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry;
import org.osgi.framework.Bundle;

/**
 * Implementation of
 * {@link org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry}
 * .
 */
@ThreadSafe
public final class CardPileBaseDesignUIRegistry
    implements ICardPileBaseDesignUIRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension point attribute specifying the card pile base design icon. */
    private static final String ATTR_ICON = "icon"; //$NON-NLS-1$

    /**
     * The extension point attribute specifying the card pile base design
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /** The extension point attribute specifying the card pile base design name. */
    private static final String ATTR_NAME = "name"; //$NON-NLS-1$

    /**
     * The collection of card pile base design user interfaces directly managed
     * by this object.
     */
    private final ConcurrentMap<CardPileBaseDesignId, ICardPileBaseDesignUI> cardPileBaseDesignUIs_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileBaseDesignUIRegistry}
     * class.
     */
    public CardPileBaseDesignUIRegistry()
    {
        cardPileBaseDesignUIs_ = new ConcurrentHashMap<CardPileBaseDesignId, ICardPileBaseDesignUI>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card pile base design user interface from the specified
     * configuration element.
     * 
     * @param configurationElement
     *        The configuration element; must not be {@code null}.
     * 
     * @return A new card pile base design user interface; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal card pile base
     *         design user interface.
     */
    /* @NonNull */
    private static ICardPileBaseDesignUI createCardPileBaseDesignUI(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( Messages.CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_missingId );
        }
        final CardPileBaseDesignId id = CardPileBaseDesignId.fromString( idString );

        final String name = configurationElement.getAttribute( ATTR_NAME );
        if( name == null )
        {
            throw new IllegalArgumentException( Messages.CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_missingName );
        }

        final String iconPath = configurationElement.getAttribute( ATTR_ICON );
        if( iconPath == null )
        {
            throw new IllegalArgumentException( Messages.CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_missingIconPath );
        }
        final Bundle[] bundles = Services.getDefault().getPackageAdministrationService().getBundles( configurationElement.getNamespaceIdentifier(), null );
        if( (bundles == null) || (bundles.length == 0) )
        {
            throw new IllegalArgumentException( Messages.CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_iconBundleNotFound( configurationElement.getNamespaceIdentifier() ) );
        }
        final URL iconUrl = FileLocator.find( bundles[ 0 ], new Path( iconPath ), null );
        if( iconUrl == null )
        {
            throw new IllegalArgumentException( Messages.CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_iconFileNotFound( bundles[ 0 ], iconPath ) );
        }
        final Icon icon = new IconProxy( iconUrl );

        return CardPileBaseDesignUIFactory.createCardPileBaseDesignUI( id, name, icon );
    }

    /*
     * @see org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry#getCardPileBaseDesignUI(org.gamegineer.table.core.CardPileBaseDesignId)
     */
    @Override
    public ICardPileBaseDesignUI getCardPileBaseDesignUI(
        final CardPileBaseDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return getCardPileBaseDesignUIMap().get( id );
    }

    /**
     * Gets a map view of all card pile base design user interfaces known by
     * this object.
     * 
     * @return A map view of all card pile base design user interfaces known by
     *         this object; never {@code null}.
     */
    /* @NonNull */
    private Map<CardPileBaseDesignId, ICardPileBaseDesignUI> getCardPileBaseDesignUIMap()
    {
        final Map<CardPileBaseDesignId, ICardPileBaseDesignUI> cardPileBaseDesignUIs = new HashMap<CardPileBaseDesignId, ICardPileBaseDesignUI>( cardPileBaseDesignUIs_ );
        for( final ICardPileBaseDesignUI cardPileBaseDesignUI : getForeignCardPileBaseDesignUIs() )
        {
            if( cardPileBaseDesignUIs.containsKey( cardPileBaseDesignUI.getId() ) )
            {
                Loggers.DEFAULT.warning( Messages.CardPileBaseDesignUIRegistry_getCardPileBaseDesignUIMap_duplicateId( cardPileBaseDesignUI.getId() ) );
            }
            else
            {
                cardPileBaseDesignUIs.put( cardPileBaseDesignUI.getId(), cardPileBaseDesignUI );
            }
        }
        return cardPileBaseDesignUIs;
    }

    /*
     * @see org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry#getCardPileBaseDesignUIs()
     */
    @Override
    public Collection<ICardPileBaseDesignUI> getCardPileBaseDesignUIs()
    {
        return new ArrayList<ICardPileBaseDesignUI>( getCardPileBaseDesignUIMap().values() );
    }

    /**
     * Gets a collection of all foreign card pile base design user interfaces
     * not directly managed by this object.
     * 
     * @return A collection of all foreign card pile base design interfaces not
     *         directly managed by this object; never {@code null}.
     */
    /* @NonNull */
    private static Collection<ICardPileBaseDesignUI> getForeignCardPileBaseDesignUIs()
    {
        final Collection<ICardPileBaseDesignUI> cardPileBaseDesignUIs = new ArrayList<ICardPileBaseDesignUI>();
        for( final IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor( Activator.SYMBOLIC_NAME, Activator.EXTENSION_CARD_PILE_BASE_DESIGN_UIS ) )
        {
            try
            {
                cardPileBaseDesignUIs.add( createCardPileBaseDesignUI( configurationElement ) );
            }
            catch( final IllegalArgumentException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardPileBaseDesignUIRegistry_getForeignCardPileBaseDesignUIs_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            }
        }
        return cardPileBaseDesignUIs;
    }

    /*
     * @see org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry#registerCardPileBaseDesignUI(org.gamegineer.table.ui.ICardPileBaseDesignUI)
     */
    @Override
    public void registerCardPileBaseDesignUI(
        final ICardPileBaseDesignUI cardPileBaseDesignUI )
    {
        assertArgumentNotNull( cardPileBaseDesignUI, "cardPileBaseDesignUI" ); //$NON-NLS-1$

        cardPileBaseDesignUIs_.putIfAbsent( cardPileBaseDesignUI.getId(), cardPileBaseDesignUI );
    }

    /*
     * @see org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry#unregisterCardPileBaseDesignUI(org.gamegineer.table.ui.ICardPileBaseDesignUI)
     */
    @Override
    public void unregisterCardPileBaseDesignUI(
        final ICardPileBaseDesignUI cardPileBaseDesignUI )
    {
        assertArgumentNotNull( cardPileBaseDesignUI, "cardPileBaseDesignUI" ); //$NON-NLS-1$

        cardPileBaseDesignUIs_.remove( cardPileBaseDesignUI.getId(), cardPileBaseDesignUI );
    }
}
