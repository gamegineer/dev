/*
 * CardPileBaseDesignRegistryExtensionPointAdapter.java
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
 * Created on Jul 27, 2010 at 10:03:44 PM.
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
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.ICardPileBaseDesignRegistry;

/**
 * A component that adapts card pile base designs published via the {@code
 * org.gamegineer.table.core.cardPileBaseDesigns} extension point to the card
 * pile base design registry.
 */
@ThreadSafe
public final class CardPileBaseDesignRegistryExtensionPointAdapter
    implements IRegistryEventListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the card pile base design
     * height.
     */
    private static final String ATTR_HEIGHT = "height"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the card pile base design
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the card pile base design
     * width.
     */
    private static final String ATTR_WIDTH = "width"; //$NON-NLS-1$

    /** The card pile base design registry service. */
    @GuardedBy( "lock_" )
    private ICardPileBaseDesignRegistry cardPileBaseDesignRegistry_;

    /**
     * The collection of card pile base designs created from the extension
     * registry.
     */
    @GuardedBy( "lock_" )
    private Collection<CardPileBaseDesignExtensionProxy> cardPileBaseDesigns_;

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
     * CardPileBaseDesignRegistryExtensionPointAdapter} class.
     */
    public CardPileBaseDesignRegistryExtensionPointAdapter()
    {
        lock_ = new Object();
        cardPileBaseDesignRegistry_ = null;
        cardPileBaseDesigns_ = new ArrayList<CardPileBaseDesignExtensionProxy>();
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
        registerCardPileBaseDesigns();
        extensionRegistry_.addListener( this, BundleConstants.CARD_PILE_BASE_DESIGNS_EXTENSION_POINT_UNIQUE_ID );
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
                registerCardPileBaseDesign( configurationElement );
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
     * Binds the card pile base design registry service to this component.
     * 
     * @param cardPileBaseDesignRegistry
     *        The card pile base design registry service; must not be {@code
     *        null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the card pile base design registry is already bound.
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesignRegistry} is {@code null}.
     */
    public void bindCardPileBaseDesignRegistry(
        /* @NonNull */
        final ICardPileBaseDesignRegistry cardPileBaseDesignRegistry )
    {
        assertArgumentNotNull( cardPileBaseDesignRegistry, "cardPileBaseDesignRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertStateLegal( cardPileBaseDesignRegistry_ == null, Messages.CardPileBaseDesignRegistryExtensionPointAdapter_bindCardPileBaseDesignRegistry_bound );
            cardPileBaseDesignRegistry_ = cardPileBaseDesignRegistry;
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
            assertStateLegal( extensionRegistry_ == null, Messages.CardPileBaseDesignRegistryExtensionPointAdapter_bindExtensionRegistry_bound );
            extensionRegistry_ = extensionRegistry;
        }
    }

    /**
     * Creates a card pile base design based on the specified extension
     * configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     * 
     * @return A card pile base design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal card pile base
     *         design.
     */
    /* @NonNull */
    private static CardPileBaseDesignExtensionProxy createCardPileBaseDesign(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final CardPileBaseDesignId id = CardPileBaseDesignId.fromString( configurationElement.getAttribute( ATTR_ID ) );

        final int width;
        try
        {
            width = Integer.parseInt( configurationElement.getAttribute( ATTR_WIDTH ) );
        }
        catch( final NumberFormatException e )
        {
            throw new IllegalArgumentException( Messages.CardPileBaseDesignRegistryExtensionPointAdapter_createCardPileBaseDesign_parseWidthError, e );
        }

        final int height;
        try
        {
            height = Integer.parseInt( configurationElement.getAttribute( ATTR_HEIGHT ) );
        }
        catch( final NumberFormatException e )
        {
            throw new IllegalArgumentException( Messages.CardPileBaseDesignRegistryExtensionPointAdapter_createCardPileBaseDesign_parseHeightError, e );
        }

        return new CardPileBaseDesignExtensionProxy( configurationElement.getDeclaringExtension(), id, width, height );
    }

    /**
     * Deactivates this component.
     */
    public void deactivate()
    {
        extensionRegistry_.removeListener( this );
        unregisterCardPileBaseDesigns();
    }

    /**
     * Indicates the specified card pile base design was contributed by the
     * specified extension.
     * 
     * @param cardPileBaseDesign
     *        The card pile base design; must not be {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return {@code true} if the specified card pile base design was
     *         contributed by the specified extension; otherwise {@code false}.
     */
    private static boolean isCardPileBaseDesignContributedByExtension(
        /* @NonNull */
        final CardPileBaseDesignExtensionProxy cardPileBaseDesign,
        /* @NonNull */
        final IExtension extension )
    {
        assert cardPileBaseDesign != null;
        assert extension != null;

        if( !cardPileBaseDesign.getExtensionNamespaceId().equals( extension.getNamespaceIdentifier() ) )
        {
            return false;
        }

        final String extensionSimpleId = cardPileBaseDesign.getExtensionSimpleId();
        return (extensionSimpleId == null) || extensionSimpleId.equals( extension.getSimpleIdentifier() );
    }

    /**
     * Registers the card pile base design represented by the specified
     * extension configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     */
    private void registerCardPileBaseDesign(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final CardPileBaseDesignExtensionProxy cardPileBaseDesign;
        try
        {
            cardPileBaseDesign = createCardPileBaseDesign( configurationElement );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.CardPileBaseDesignRegistryExtensionPointAdapter_registerCardPileBaseDesign_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            return;
        }

        synchronized( lock_ )
        {
            cardPileBaseDesignRegistry_.registerCardPileBaseDesign( cardPileBaseDesign );
            cardPileBaseDesigns_.add( cardPileBaseDesign );
        }
    }

    /**
     * Registers all card pile base designs in the extension registry.
     */
    private void registerCardPileBaseDesigns()
    {
        for( final IConfigurationElement configurationElement : extensionRegistry_.getConfigurationElementsFor( BundleConstants.CARD_PILE_BASE_DESIGNS_EXTENSION_POINT_UNIQUE_ID ) )
        {
            registerCardPileBaseDesign( configurationElement );
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
            unregisterCardPileBaseDesigns( extension );
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
     * Unbinds the card pile base design registry service from this component.
     * 
     * @param cardPileBaseDesignRegistry
     *        The card pile base design registry service; must not be {@code
     *        null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPileBaseDesignRegistry} is not currently bound.
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesignRegistry} is {@code null}.
     */
    public void unbindCardPileBaseDesignRegistry(
        /* @NonNull */
        final ICardPileBaseDesignRegistry cardPileBaseDesignRegistry )
    {
        assertArgumentNotNull( cardPileBaseDesignRegistry, "cardPileBaseDesignRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( cardPileBaseDesignRegistry_ == cardPileBaseDesignRegistry, "cardPileBaseDesignRegistry", Messages.CardPileBaseDesignRegistryExtensionPointAdapter_unbindCardPileBaseDesignRegistry_notBound ); //$NON-NLS-1$
            cardPileBaseDesignRegistry_ = null;
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
            assertArgumentLegal( extensionRegistry_ == extensionRegistry, "extensionRegistry", Messages.CardPileBaseDesignRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound ); //$NON-NLS-1$
            extensionRegistry_ = null;
        }
    }

    /**
     * Unregisters all card pile base designs.
     */
    private void unregisterCardPileBaseDesigns()
    {
        synchronized( lock_ )
        {
            for( final CardPileBaseDesignExtensionProxy cardPileBaseDesign : cardPileBaseDesigns_ )
            {
                cardPileBaseDesignRegistry_.unregisterCardPileBaseDesign( cardPileBaseDesign );
            }

            cardPileBaseDesigns_.clear();
        }
    }

    /**
     * Unregisters all card pile base designs contributed by the specified
     * extension.
     * 
     * @param extension
     *        The extension; must not be {@code null}.
     */
    private void unregisterCardPileBaseDesigns(
        /* @NonNull */
        final IExtension extension )
    {
        assert extension != null;

        synchronized( lock_ )
        {
            for( final Iterator<CardPileBaseDesignExtensionProxy> iterator = cardPileBaseDesigns_.iterator(); iterator.hasNext(); )
            {
                final CardPileBaseDesignExtensionProxy cardPileBaseDesign = iterator.next();
                if( isCardPileBaseDesignContributedByExtension( cardPileBaseDesign, extension ) )
                {
                    cardPileBaseDesignRegistry_.unregisterCardPileBaseDesign( cardPileBaseDesign );
                    iterator.remove();
                }
            }
        }
    }
}
