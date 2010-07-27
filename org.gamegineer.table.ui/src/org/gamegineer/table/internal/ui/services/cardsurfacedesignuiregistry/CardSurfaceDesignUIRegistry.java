/*
 * CardSurfaceDesignUIRegistry.java
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
 * Created on Nov 21, 2009 at 12:27:06 AM.
 */

package org.gamegineer.table.internal.ui.services.cardsurfacedesignuiregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import javax.swing.Icon;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Path;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.BundleConstants;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.util.swing.IconProxy;
import org.gamegineer.table.ui.ICardSurfaceDesignUI;
import org.gamegineer.table.ui.TableUIFactory;
import org.gamegineer.table.ui.services.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry;
import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.PackageAdmin;

/**
 * Implementation of
 * {@link org.gamegineer.table.ui.services.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry}
 * .
 */
@ThreadSafe
public final class CardSurfaceDesignUIRegistry
    implements ICardSurfaceDesignUIRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension point attribute specifying the card surface design icon. */
    private static final String ATTR_ICON = "icon"; //$NON-NLS-1$

    /**
     * The extension point attribute specifying the card surface design
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /** The extension point attribute specifying the card surface design name. */
    private static final String ATTR_NAME = "name"; //$NON-NLS-1$

    /**
     * The collection of card surface design user interfaces directly managed by
     * this object.
     */
    private final ConcurrentMap<CardSurfaceDesignId, ICardSurfaceDesignUI> cardSurfaceDesignUIs_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardSurfaceDesignUIRegistry}
     * class.
     */
    public CardSurfaceDesignUIRegistry()
    {
        cardSurfaceDesignUIs_ = new ConcurrentHashMap<CardSurfaceDesignId, ICardSurfaceDesignUI>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card surface design user interface from the specified
     * configuration element.
     * 
     * @param configurationElement
     *        The configuration element; must not be {@code null}.
     * 
     * @return A new card surface design user interface; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal card surface
     *         design user interface.
     */
    /* @NonNull */
    private static ICardSurfaceDesignUI createCardSurfaceDesignUI(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_missingId );
        }
        final CardSurfaceDesignId id = CardSurfaceDesignId.fromString( idString );

        final String name = configurationElement.getAttribute( ATTR_NAME );
        if( name == null )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_missingName );
        }

        final String iconPath = configurationElement.getAttribute( ATTR_ICON );
        if( iconPath == null )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_missingIconPath );
        }
        final PackageAdmin packageAdmin = Activator.getDefault().getPackageAdmin();
        if( packageAdmin == null )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_noPackageAdminService );
        }
        final Bundle[] bundles = packageAdmin.getBundles( configurationElement.getNamespaceIdentifier(), null );
        if( (bundles == null) || (bundles.length == 0) )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_iconBundleNotFound( configurationElement.getNamespaceIdentifier() ) );
        }
        final URL iconUrl = FileLocator.find( bundles[ 0 ], new Path( iconPath ), null );
        if( iconUrl == null )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_iconFileNotFound( bundles[ 0 ], iconPath ) );
        }
        final Icon icon = new IconProxy( iconUrl );

        return TableUIFactory.createCardSurfaceDesignUI( id, name, icon );
    }

    /*
     * @see org.gamegineer.table.ui.services.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry#getCardSurfaceDesignUI(org.gamegineer.table.core.CardSurfaceDesignId)
     */
    @Override
    public ICardSurfaceDesignUI getCardSurfaceDesignUI(
        final CardSurfaceDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return getCardSurfaceDesignUIMap().get( id );
    }

    /**
     * Gets a map view of all card surface design user interfaces known by this
     * object.
     * 
     * @return A map view of all card surface design user interfaces known by
     *         this object; never {@code null}.
     */
    /* @NonNull */
    private Map<CardSurfaceDesignId, ICardSurfaceDesignUI> getCardSurfaceDesignUIMap()
    {
        final Map<CardSurfaceDesignId, ICardSurfaceDesignUI> cardSurfaceDesignUIs = new HashMap<CardSurfaceDesignId, ICardSurfaceDesignUI>( cardSurfaceDesignUIs_ );
        for( final ICardSurfaceDesignUI cardSurfaceDesignUI : getForeignCardSurfaceDesignUIs() )
        {
            if( cardSurfaceDesignUIs.containsKey( cardSurfaceDesignUI.getId() ) )
            {
                Loggers.getDefaultLogger().warning( Messages.CardSurfaceDesignUIRegistry_getCardSurfaceDesignUIMap_duplicateId( cardSurfaceDesignUI.getId() ) );
            }
            else
            {
                cardSurfaceDesignUIs.put( cardSurfaceDesignUI.getId(), cardSurfaceDesignUI );
            }
        }
        return cardSurfaceDesignUIs;
    }

    /*
     * @see org.gamegineer.table.ui.services.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry#getCardSurfaceDesignUIs()
     */
    @Override
    public Collection<ICardSurfaceDesignUI> getCardSurfaceDesignUIs()
    {
        return new ArrayList<ICardSurfaceDesignUI>( getCardSurfaceDesignUIMap().values() );
    }

    /**
     * Gets a collection of all foreign card surface design user interfaces not
     * directly managed by this object.
     * 
     * @return A collection of all foreign card surface design interfaces not
     *         directly managed by this object; never {@code null}.
     */
    /* @NonNull */
    private static Collection<ICardSurfaceDesignUI> getForeignCardSurfaceDesignUIs()
    {
        final IExtensionRegistry extensionRegistry = Activator.getDefault().getExtensionRegistry();
        if( extensionRegistry == null )
        {
            Loggers.getDefaultLogger().warning( Messages.CardSurfaceDesignUIRegistry_getForeignCardSurfaceDesignUIs_noExtensionRegistry );
            return Collections.emptyList();
        }

        final Collection<ICardSurfaceDesignUI> cardSurfaceDesignUIs = new ArrayList<ICardSurfaceDesignUI>();
        for( final IConfigurationElement configurationElement : extensionRegistry.getConfigurationElementsFor( BundleConstants.SYMBOLIC_NAME, BundleConstants.EXTENSION_CARD_SURFACE_DESIGN_UIS ) )
        {
            try
            {
                cardSurfaceDesignUIs.add( createCardSurfaceDesignUI( configurationElement ) );
            }
            catch( final IllegalArgumentException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.CardSurfaceDesignUIRegistry_getForeignCardSurfaceDesignUIs_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            }
        }
        return cardSurfaceDesignUIs;
    }

    /*
     * @see org.gamegineer.table.ui.services.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry#registerCardSurfaceDesignUI(org.gamegineer.table.ui.ICardSurfaceDesignUI)
     */
    @Override
    public void registerCardSurfaceDesignUI(
        final ICardSurfaceDesignUI cardSurfaceDesignUI )
    {
        assertArgumentNotNull( cardSurfaceDesignUI, "cardSurfaceDesignUI" ); //$NON-NLS-1$

        cardSurfaceDesignUIs_.putIfAbsent( cardSurfaceDesignUI.getId(), cardSurfaceDesignUI );
    }

    /*
     * @see org.gamegineer.table.ui.services.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry#unregisterCardSurfaceDesignUI(org.gamegineer.table.ui.ICardSurfaceDesignUI)
     */
    @Override
    public void unregisterCardSurfaceDesignUI(
        final ICardSurfaceDesignUI cardSurfaceDesignUI )
    {
        assertArgumentNotNull( cardSurfaceDesignUI, "cardSurfaceDesignUI" ); //$NON-NLS-1$

        cardSurfaceDesignUIs_.remove( cardSurfaceDesignUI.getId(), cardSurfaceDesignUI );
    }
}
