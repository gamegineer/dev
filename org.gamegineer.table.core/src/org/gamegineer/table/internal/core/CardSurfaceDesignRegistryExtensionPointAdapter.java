/*
 * CardSurfaceDesignRegistryExtensionPointAdapter.java
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
 * Created on Jul 31, 2010 at 10:10:51 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.core.ICardSurfaceDesignRegistry;

/**
 * A component that adapts card surface designs published via the {@code
 * org.gamegineer.table.core.cardSurfaceDesigns} extension point to the card
 * surface design registry.
 */
@ThreadSafe
public final class CardSurfaceDesignRegistryExtensionPointAdapter
    implements IRegistryEventListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the card surface design
     * height.
     */
    private static final String ATTR_HEIGHT = "height"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the card surface design
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the card surface design
     * width.
     */
    private static final String ATTR_WIDTH = "width"; //$NON-NLS-1$

    /** The card surface design registry service. */
    @GuardedBy( "lock_" )
    private ICardSurfaceDesignRegistry cardSurfaceDesignRegistry_;

    /**
     * The collection of card surface designs created from the extension
     * registry.
     */
    @GuardedBy( "lock_" )
    private Collection<CardSurfaceDesignExtensionProxy> cardSurfaceDesigns_;

    /** The extension registry service. */
    @GuardedBy( "lock_" )
    private IExtensionRegistry extensionRegistry_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardSurfaceDesignRegistryExtensionPointAdapter} class.
     */
    public CardSurfaceDesignRegistryExtensionPointAdapter()
    {
        lock_ = new Object();
        cardSurfaceDesignRegistry_ = null;
        cardSurfaceDesigns_ = new ArrayList<CardSurfaceDesignExtensionProxy>();
        extensionRegistry_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Activates this component.
     */
    public void activate()
    {
        registerCardSurfaceDesigns();
        extensionRegistry_.addListener( this, BundleConstants.CARD_SURFACE_DESIGNS_EXTENSION_POINT_UNIQUE_ID );
    }

    /*
     * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
     */
    @Override
    public void added(
        final IExtension[] extensions )
    {
        for( final IExtension extension : extensions )
        {
            for( final IConfigurationElement configurationElement : extension.getConfigurationElements() )
            {
                registerCardSurfaceDesign( configurationElement );
            }
        }
    }

    /*
     * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
     */
    @Override
    public void added(
        @SuppressWarnings( "unused" )
        final IExtensionPoint[] extensionPoints )
    {
        // do nothing
    }

    /**
     * Binds the card surface design registry service to this component.
     * 
     * @param cardSurfaceDesignRegistry
     *        The card surface design registry service; must not be {@code null}
     *        .
     * 
     * @throws java.lang.IllegalStateException
     *         If the card surface design registry is already bound.
     * @throws java.lang.NullPointerException
     *         If {@code cardSurfaceDesignRegistry} is {@code null}.
     */
    public void bindCardSurfaceDesignRegistry(
        /* @NonNull */
        final ICardSurfaceDesignRegistry cardSurfaceDesignRegistry )
    {
        assertArgumentNotNull( cardSurfaceDesignRegistry, "cardSurfaceDesignRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertStateLegal( cardSurfaceDesignRegistry_ == null, NonNlsMessages.CardSurfaceDesignRegistryExtensionPointAdapter_bindCardSurfaceDesignRegistry_bound );
            cardSurfaceDesignRegistry_ = cardSurfaceDesignRegistry;
        }
    }

    /**
     * Binds the extension registry service to this component.
     * 
     * @param extensionRegistry
     *        The extension registry service; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the extension registry is already bound.
     * @throws java.lang.NullPointerException
     *         If {@code extensionRegistry} is {@code null}.
     */
    public void bindExtensionRegistry(
        /* @NonNull */
        final IExtensionRegistry extensionRegistry )
    {
        assertArgumentNotNull( extensionRegistry, "extensionRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertStateLegal( extensionRegistry_ == null, NonNlsMessages.CardSurfaceDesignRegistryExtensionPointAdapter_bindExtensionRegistry_bound );
            extensionRegistry_ = extensionRegistry;
        }
    }

    /**
     * Creates a card surface design based on the specified extension
     * configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     * 
     * @return A card surface design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal card surface
     *         design.
     */
    /* @NonNull */
    private static CardSurfaceDesignExtensionProxy createCardSurfaceDesign(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final CardSurfaceDesignId id = CardSurfaceDesignId.fromString( configurationElement.getAttribute( ATTR_ID ) );

        final int width;
        try
        {
            width = Integer.parseInt( configurationElement.getAttribute( ATTR_WIDTH ) );
        }
        catch( final NumberFormatException e )
        {
            throw new IllegalArgumentException( NonNlsMessages.CardSurfaceDesignRegistryExtensionPointAdapter_createCardSurfaceDesign_parseWidthError, e );
        }

        final int height;
        try
        {
            height = Integer.parseInt( configurationElement.getAttribute( ATTR_HEIGHT ) );
        }
        catch( final NumberFormatException e )
        {
            throw new IllegalArgumentException( NonNlsMessages.CardSurfaceDesignRegistryExtensionPointAdapter_createCardSurfaceDesign_parseHeightError, e );
        }

        return new CardSurfaceDesignExtensionProxy( configurationElement.getDeclaringExtension(), id, width, height );
    }

    /**
     * Deactivates this component.
     */
    public void deactivate()
    {
        extensionRegistry_.removeListener( this );
        unregisterCardSurfaceDesigns();
    }

    /**
     * Indicates the specified card surface design was contributed by the
     * specified extension.
     * 
     * @param cardSurfaceDesign
     *        The card surface design; must not be {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return {@code true} if the specified card surface design was contributed
     *         by the specified extension; otherwise {@code false}.
     */
    private static boolean isCardSurfaceDesignContributedByExtension(
        /* @NonNull */
        final CardSurfaceDesignExtensionProxy cardSurfaceDesign,
        /* @NonNull */
        final IExtension extension )
    {
        assert cardSurfaceDesign != null;
        assert extension != null;

        if( !cardSurfaceDesign.getExtensionNamespaceId().equals( extension.getNamespaceIdentifier() ) )
        {
            return false;
        }

        final String extensionSimpleId = cardSurfaceDesign.getExtensionSimpleId();
        return (extensionSimpleId == null) || extensionSimpleId.equals( extension.getSimpleIdentifier() );
    }

    /**
     * Registers the card surface design represented by the specified extension
     * configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     */
    private void registerCardSurfaceDesign(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final CardSurfaceDesignExtensionProxy cardSurfaceDesign;
        try
        {
            cardSurfaceDesign = createCardSurfaceDesign( configurationElement );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardSurfaceDesignRegistryExtensionPointAdapter_registerCardSurfaceDesign_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            return;
        }

        synchronized( lock_ )
        {
            cardSurfaceDesignRegistry_.registerCardSurfaceDesign( cardSurfaceDesign );
            cardSurfaceDesigns_.add( cardSurfaceDesign );
        }
    }

    /**
     * Registers all card surface designs in the extension registry.
     */
    private void registerCardSurfaceDesigns()
    {
        for( final IConfigurationElement configurationElement : extensionRegistry_.getConfigurationElementsFor( BundleConstants.CARD_SURFACE_DESIGNS_EXTENSION_POINT_UNIQUE_ID ) )
        {
            registerCardSurfaceDesign( configurationElement );
        }
    }

    /*
     * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
     */
    @Override
    public void removed(
        final IExtension[] extensions )
    {
        for( final IExtension extension : extensions )
        {
            unregisterCardSurfaceDesigns( extension );
        }
    }

    /*
     * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtensionPoint[])
     */
    @Override
    public void removed(
        @SuppressWarnings( "unused" )
        final IExtensionPoint[] extensionPoints )
    {
        // do nothing
    }

    /**
     * Unbinds the card surface design registry service from this component.
     * 
     * @param cardSurfaceDesignRegistry
     *        The card surface design registry service; must not be {@code null}
     *        .
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardSurfaceDesignRegistry} is not currently bound.
     * @throws java.lang.NullPointerException
     *         If {@code cardSurfaceDesignRegistry} is {@code null}.
     */
    public void unbindCardSurfaceDesignRegistry(
        /* @NonNull */
        final ICardSurfaceDesignRegistry cardSurfaceDesignRegistry )
    {
        assertArgumentNotNull( cardSurfaceDesignRegistry, "cardSurfaceDesignRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( cardSurfaceDesignRegistry_ == cardSurfaceDesignRegistry, "cardSurfaceDesignRegistry", NonNlsMessages.CardSurfaceDesignRegistryExtensionPointAdapter_unbindCardSurfaceDesignRegistry_notBound ); //$NON-NLS-1$
            cardSurfaceDesignRegistry_ = null;
        }
    }

    /**
     * Unbinds the extension registry service from this component.
     * 
     * @param extensionRegistry
     *        The extension registry service; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code extensionRegistry} is not currently bound.
     * @throws java.lang.NullPointerException
     *         If {@code extensionRegistry} is {@code null}.
     */
    public void unbindExtensionRegistry(
        /* @NonNull */
        final IExtensionRegistry extensionRegistry )
    {
        assertArgumentNotNull( extensionRegistry, "extensionRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( extensionRegistry_ == extensionRegistry, "extensionRegistry", NonNlsMessages.CardSurfaceDesignRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound ); //$NON-NLS-1$
            extensionRegistry_ = null;
        }
    }

    /**
     * Unregisters all card surface designs.
     */
    private void unregisterCardSurfaceDesigns()
    {
        synchronized( lock_ )
        {
            for( final CardSurfaceDesignExtensionProxy cardSurfaceDesign : cardSurfaceDesigns_ )
            {
                cardSurfaceDesignRegistry_.unregisterCardSurfaceDesign( cardSurfaceDesign );
            }

            cardSurfaceDesigns_.clear();
        }
    }

    /**
     * Unregisters all card surface designs contributed by the specified
     * extension.
     * 
     * @param extension
     *        The extension; must not be {@code null}.
     */
    private void unregisterCardSurfaceDesigns(
        /* @NonNull */
        final IExtension extension )
    {
        assert extension != null;

        synchronized( lock_ )
        {
            for( final Iterator<CardSurfaceDesignExtensionProxy> iterator = cardSurfaceDesigns_.iterator(); iterator.hasNext(); )
            {
                final CardSurfaceDesignExtensionProxy cardSurfaceDesign = iterator.next();
                if( isCardSurfaceDesignContributedByExtension( cardSurfaceDesign, extension ) )
                {
                    cardSurfaceDesignRegistry_.unregisterCardSurfaceDesign( cardSurfaceDesign );
                    iterator.remove();
                }
            }
        }
    }
}
