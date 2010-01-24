/*
 * CardPileDesignUIRegistry.java
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

package org.gamegineer.table.internal.ui.services.cardpiledesignuiregistry;

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
import org.gamegineer.table.core.CardPileDesignId;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.Services;
import org.gamegineer.table.internal.ui.util.swing.IconProxy;
import org.gamegineer.table.ui.CardPileDesignUIFactory;
import org.gamegineer.table.ui.ICardPileDesignUI;
import org.gamegineer.table.ui.services.cardpiledesignuiregistry.ICardPileDesignUIRegistry;
import org.osgi.framework.Bundle;

/**
 * Implementation of
 * {@link org.gamegineer.table.ui.services.cardpiledesignuiregistry.ICardPileDesignUIRegistry}
 * .
 */
@ThreadSafe
public final class CardPileDesignUIRegistry
    implements ICardPileDesignUIRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension point attribute specifying the card pile design icon. */
    private static final String ATTR_ICON = "icon"; //$NON-NLS-1$

    /**
     * The extension point attribute specifying the card pile design identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /** The extension point attribute specifying the card pile design name. */
    private static final String ATTR_NAME = "name"; //$NON-NLS-1$

    /**
     * The collection of card pile design user interfaces directly managed by
     * this object.
     */
    private final ConcurrentMap<CardPileDesignId, ICardPileDesignUI> cardPileDesignUIs_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileDesignUIRegistry} class.
     */
    public CardPileDesignUIRegistry()
    {
        cardPileDesignUIs_ = new ConcurrentHashMap<CardPileDesignId, ICardPileDesignUI>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card pile design user interface from the specified
     * configuration element.
     * 
     * @param configurationElement
     *        The configuration element; must not be {@code null}.
     * 
     * @return A new card pile design user interface; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal card pile
     *         design user interface.
     */
    /* @NonNull */
    private static ICardPileDesignUI createCardPileDesignUI(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( Messages.CardPileDesignUIRegistry_createCardPileDesignUI_missingId );
        }
        final CardPileDesignId id = CardPileDesignId.fromString( idString );

        final String name = configurationElement.getAttribute( ATTR_NAME );
        if( name == null )
        {
            throw new IllegalArgumentException( Messages.CardPileDesignUIRegistry_createCardPileDesignUI_missingName );
        }

        final String iconPath = configurationElement.getAttribute( ATTR_ICON );
        if( iconPath == null )
        {
            throw new IllegalArgumentException( Messages.CardPileDesignUIRegistry_createCardPileDesignUI_missingIconPath );
        }
        final Bundle[] bundles = Services.getDefault().getPackageAdministrationService().getBundles( configurationElement.getNamespaceIdentifier(), null );
        if( (bundles == null) || (bundles.length == 0) )
        {
            throw new IllegalArgumentException( Messages.CardPileDesignUIRegistry_createCardPileDesignUI_iconBundleNotFound( configurationElement.getNamespaceIdentifier() ) );
        }
        final URL iconUrl = FileLocator.find( bundles[ 0 ], new Path( iconPath ), null );
        if( iconUrl == null )
        {
            throw new IllegalArgumentException( Messages.CardPileDesignUIRegistry_createCardPileDesignUI_iconFileNotFound( bundles[ 0 ], iconPath ) );
        }
        final Icon icon = new IconProxy( iconUrl );

        return CardPileDesignUIFactory.createCardPileDesignUI( id, name, icon );
    }

    /*
     * @see org.gamegineer.table.ui.services.cardpiledesignuiregistry.ICardPileDesignUIRegistry#getCardPileDesignUI(org.gamegineer.table.core.CardPileDesignId)
     */
    public ICardPileDesignUI getCardPileDesignUI(
        final CardPileDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return getCardPileDesignUIMap().get( id );
    }

    /**
     * Gets a map view of all card pile design user interfaces known by this
     * object.
     * 
     * @return A map view of all card pile design user interfaces known by this
     *         object; never {@code null}.
     */
    /* @NonNull */
    private Map<CardPileDesignId, ICardPileDesignUI> getCardPileDesignUIMap()
    {
        final Map<CardPileDesignId, ICardPileDesignUI> cardPileDesignUIs = new HashMap<CardPileDesignId, ICardPileDesignUI>( cardPileDesignUIs_ );
        for( final ICardPileDesignUI cardPileDesignUI : getForeignCardPileDesignUIs() )
        {
            if( cardPileDesignUIs.containsKey( cardPileDesignUI.getId() ) )
            {
                Loggers.DEFAULT.warning( Messages.CardPileDesignUIRegistry_getCardPileDesignUIMap_duplicateId( cardPileDesignUI.getId() ) );
            }
            else
            {
                cardPileDesignUIs.put( cardPileDesignUI.getId(), cardPileDesignUI );
            }
        }
        return cardPileDesignUIs;
    }

    /*
     * @see org.gamegineer.table.ui.services.cardpiledesignuiregistry.ICardPileDesignUIRegistry#getCardPileDesignUIs()
     */
    public Collection<ICardPileDesignUI> getCardPileDesignUIs()
    {
        return new ArrayList<ICardPileDesignUI>( getCardPileDesignUIMap().values() );
    }

    /**
     * Gets a collection of all foreign card pile design user interfaces not
     * directly managed by this object.
     * 
     * @return A collection of all foreign card pile design interfaces not
     *         directly managed by this object; never {@code null}.
     */
    /* @NonNull */
    private static Collection<ICardPileDesignUI> getForeignCardPileDesignUIs()
    {
        final Collection<ICardPileDesignUI> cardPileDesignUIs = new ArrayList<ICardPileDesignUI>();
        for( final IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor( Activator.SYMBOLIC_NAME, Activator.EXTENSION_CARD_PILE_DESIGN_UIS ) )
        {
            try
            {
                cardPileDesignUIs.add( createCardPileDesignUI( configurationElement ) );
            }
            catch( final IllegalArgumentException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardPileDesignUIRegistry_getForeignCardPileDesignUIs_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            }
        }
        return cardPileDesignUIs;
    }

    /*
     * @see org.gamegineer.table.ui.services.cardpiledesignuiregistry.ICardPileDesignUIRegistry#registerCardPileDesignUI(org.gamegineer.table.ui.ICardPileDesignUI)
     */
    public void registerCardPileDesignUI(
        final ICardPileDesignUI cardPileDesignUI )
    {
        assertArgumentNotNull( cardPileDesignUI, "cardPileDesignUI" ); //$NON-NLS-1$

        cardPileDesignUIs_.putIfAbsent( cardPileDesignUI.getId(), cardPileDesignUI );
    }

    /*
     * @see org.gamegineer.table.ui.services.cardpiledesignuiregistry.ICardPileDesignUIRegistry#unregisterCardPileDesignUI(org.gamegineer.table.ui.ICardPileDesignUI)
     */
    public void unregisterCardPileDesignUI(
        final ICardPileDesignUI cardPileDesignUI )
    {
        assertArgumentNotNull( cardPileDesignUI, "cardPileDesignUI" ); //$NON-NLS-1$

        cardPileDesignUIs_.remove( cardPileDesignUI.getId(), cardPileDesignUI );
    }
}
