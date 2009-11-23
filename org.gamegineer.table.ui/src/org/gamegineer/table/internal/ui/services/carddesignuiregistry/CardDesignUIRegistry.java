/*
 * CardDesignUIRegistry.java
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
 * Created on Nov 21, 2009 at 12:27:06 AM.
 */

package org.gamegineer.table.internal.ui.services.carddesignuiregistry;

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
import org.gamegineer.table.core.CardDesignId;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.Services;
import org.gamegineer.table.internal.ui.util.swing.IconProxy;
import org.gamegineer.table.ui.CardDesignUIFactory;
import org.gamegineer.table.ui.ICardDesignUI;
import org.gamegineer.table.ui.services.carddesignuiregistry.ICardDesignUIRegistry;
import org.osgi.framework.Bundle;

/**
 * Implementation of
 * {@link org.gamegineer.table.ui.services.carddesignuiregistry.ICardDesignUIRegistry}
 * .
 */
@ThreadSafe
public final class CardDesignUIRegistry
    implements ICardDesignUIRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension point attribute specifying the card design icon. */
    private static final String ATTR_ICON = "icon"; //$NON-NLS-1$

    /** The extension point attribute specifying the card design identifier. */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /** The extension point attribute specifying the card design name. */
    private static final String ATTR_NAME = "name"; //$NON-NLS-1$

    /**
     * The collection of card design user interfaces directly managed by this
     * object.
     */
    private final ConcurrentMap<CardDesignId, ICardDesignUI> cardDesignUIs_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardDesignUIRegistry} class.
     */
    public CardDesignUIRegistry()
    {
        cardDesignUIs_ = new ConcurrentHashMap<CardDesignId, ICardDesignUI>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card design user interface from the specified configuration
     * element.
     * 
     * @param configurationElement
     *        The configuration element; must not be {@code null}.
     * 
     * @return A new card design user interface; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal card design
     *         user interface.
     */
    /* @NonNull */
    private static ICardDesignUI createCardDesignUI(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( Messages.CardDesignUIRegistry_createCardDesignUI_missingId );
        }
        final CardDesignId id = CardDesignId.fromString( idString );

        final String name = configurationElement.getAttribute( ATTR_NAME );
        if( name == null )
        {
            throw new IllegalArgumentException( Messages.CardDesignUIRegistry_createCardDesignUI_missingName );
        }

        final String iconPath = configurationElement.getAttribute( ATTR_ICON );
        if( iconPath == null )
        {
            throw new IllegalArgumentException( Messages.CardDesignUIRegistry_createCardDesignUI_missingIconPath );
        }
        final Bundle[] bundles = Services.getDefault().getPackageAdministrationService().getBundles( configurationElement.getNamespaceIdentifier(), null );
        if( (bundles == null) || (bundles.length == 0) )
        {
            throw new IllegalArgumentException( Messages.CardDesignUIRegistry_createCardDesignUI_iconBundleNotFound( configurationElement.getNamespaceIdentifier() ) );
        }
        final URL iconUrl = FileLocator.find( bundles[ 0 ], new Path( iconPath ), null );
        if( iconUrl == null )
        {
            throw new IllegalArgumentException( Messages.CardDesignUIRegistry_createCardDesignUI_iconFileNotFound( bundles[ 0 ], iconPath ) );
        }
        final Icon icon = new IconProxy( iconUrl );

        return CardDesignUIFactory.createCardDesignUI( id, name, icon );
    }

    /*
     * @see org.gamegineer.table.ui.services.carddesignuiregistry.ICardDesignUIRegistry#getCardDesignUI(org.gamegineer.table.core.CardDesignId)
     */
    public ICardDesignUI getCardDesignUI(
        final CardDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return getCardDesignUIMap().get( id );
    }

    /**
     * Gets a map view of all card design user interfaces known by this object.
     * 
     * @return A map view of all card design user interfaces known by this
     *         object; never {@code null}.
     */
    /* @NonNull */
    private Map<CardDesignId, ICardDesignUI> getCardDesignUIMap()
    {
        final Map<CardDesignId, ICardDesignUI> cardDesignUIs = new HashMap<CardDesignId, ICardDesignUI>( cardDesignUIs_ );
        for( final ICardDesignUI cardDesignUI : getForeignCardDesignUIs() )
        {
            if( cardDesignUIs.containsKey( cardDesignUI.getId() ) )
            {
                Loggers.DEFAULT.warning( Messages.CardDesignUIRegistry_getCardDesignUIMap_duplicateId( cardDesignUI.getId() ) );
            }
            else
            {
                cardDesignUIs.put( cardDesignUI.getId(), cardDesignUI );
            }
        }
        return cardDesignUIs;
    }

    /*
     * @see org.gamegineer.table.ui.services.carddesignuiregistry.ICardDesignUIRegistry#getCardDesignUIs()
     */
    public Collection<ICardDesignUI> getCardDesignUIs()
    {
        return new ArrayList<ICardDesignUI>( getCardDesignUIMap().values() );
    }

    /**
     * Gets a collection of all foreign card design user interfaces not directly
     * managed by this object.
     * 
     * @return A collection of all foreign card design interfaces not directly
     *         managed by this object; never {@code null}.
     */
    /* @NonNull */
    private static Collection<ICardDesignUI> getForeignCardDesignUIs()
    {
        final Collection<ICardDesignUI> cardDesignUIs = new ArrayList<ICardDesignUI>();
        for( final IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor( Activator.SYMBOLIC_NAME, Activator.EXTENSION_CARD_DESIGN_UIS ) )
        {
            try
            {
                cardDesignUIs.add( createCardDesignUI( configurationElement ) );
            }
            catch( final IllegalArgumentException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.CardDesignUIRegistry_getForeignCardDesignUIs_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            }
        }
        return cardDesignUIs;
    }

    /*
     * @see org.gamegineer.table.ui.services.carddesignuiregistry.ICardDesignUIRegistry#registerCardDesignUI(org.gamegineer.table.ui.ICardDesignUI)
     */
    public void registerCardDesignUI(
        final ICardDesignUI cardDesignUI )
    {
        assertArgumentNotNull( cardDesignUI, "cardDesignUI" ); //$NON-NLS-1$

        cardDesignUIs_.putIfAbsent( cardDesignUI.getId(), cardDesignUI );
    }

    /*
     * @see org.gamegineer.table.ui.services.carddesignuiregistry.ICardDesignUIRegistry#unregisterCardDesignUI(org.gamegineer.table.ui.ICardDesignUI)
     */
    public void unregisterCardDesignUI(
        final ICardDesignUI cardDesignUI )
    {
        assertArgumentNotNull( cardDesignUI, "cardDesignUI" ); //$NON-NLS-1$

        cardDesignUIs_.remove( cardDesignUI.getId(), cardDesignUI );
    }
}
