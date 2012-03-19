/*
 * CardPileBaseDesignRegistryExtensionPointAdapter.java
 * Copyright 2008-2012 Gamegineer.org
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
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.ICardPileBaseDesign;
import org.gamegineer.table.core.ICardPileBaseDesignRegistry;
import org.gamegineer.table.core.TableFactory;

/**
 * A component that adapts card pile base designs published via the
 * {@code org.gamegineer.table.core.cardPileBaseDesigns} extension point to the
 * card pile base design registry.
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

    /**
     * The collection of card pile base design registrations contributed from
     * the extension registry.
     */
    @GuardedBy( "lock_" )
    private Collection<CardPileBaseDesignRegistration> cardPileBaseDesignRegistrations_;

    /** The card pile base design registry service. */
    @GuardedBy( "lock_" )
    private ICardPileBaseDesignRegistry cardPileBaseDesignRegistry_;

    /** The extension registry service. */
    @GuardedBy( "lock_" )
    private IExtensionRegistry extensionRegistry_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardPileBaseDesignRegistryExtensionPointAdapter} class.
     */
    public CardPileBaseDesignRegistryExtensionPointAdapter()
    {
        cardPileBaseDesignRegistrations_ = new ArrayList<CardPileBaseDesignRegistration>();
        cardPileBaseDesignRegistry_ = null;
        extensionRegistry_ = null;
        lock_ = new Object();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Activates this component.
     */
    public void activate()
    {
        synchronized( lock_ )
        {
            registerCardPileBaseDesigns();
            extensionRegistry_.addListener( this, BundleConstants.CARD_PILE_BASE_DESIGNS_EXTENSION_POINT_UNIQUE_ID );
        }
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
     *        The card pile base design registry service; must not be
     *        {@code null}.
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
            assertStateLegal( cardPileBaseDesignRegistry_ == null, NonNlsMessages.CardPileBaseDesignRegistryExtensionPointAdapter_bindCardPileBaseDesignRegistry_bound );
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
            assertStateLegal( extensionRegistry_ == null, NonNlsMessages.CardPileBaseDesignRegistryExtensionPointAdapter_bindExtensionRegistry_bound );
            extensionRegistry_ = extensionRegistry;
        }
    }

    /**
     * Creates a card pile base design registration based on the specified
     * extension configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     * 
     * @return A card pile base design registration; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal card pile base
     *         design.
     */
    /* @NonNull */
    private static CardPileBaseDesignRegistration createCardPileBaseDesignRegistration(
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
            throw new IllegalArgumentException( NonNlsMessages.CardPileBaseDesignRegistryExtensionPointAdapter_createCardPileBaseDesignRegistration_parseWidthError, e );
        }

        final int height;
        try
        {
            height = Integer.parseInt( configurationElement.getAttribute( ATTR_HEIGHT ) );
        }
        catch( final NumberFormatException e )
        {
            throw new IllegalArgumentException( NonNlsMessages.CardPileBaseDesignRegistryExtensionPointAdapter_createCardPileBaseDesignRegistration_parseHeightError, e );
        }

        return new CardPileBaseDesignRegistration( configurationElement.getDeclaringExtension(), id, width, height );
    }

    /**
     * Deactivates this component.
     */
    public void deactivate()
    {
        synchronized( lock_ )
        {
            extensionRegistry_.removeListener( this );
            unregisterCardPileBaseDesigns();
        }
    }

    /**
     * Indicates the specified card pile base design registration was
     * contributed by the specified extension.
     * 
     * @param cardPileBaseDesignRegistration
     *        The card pile base design registration; must not be {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return {@code true} if the specified card pile base design registration
     *         was contributed by the specified extension; otherwise
     *         {@code false}.
     */
    private static boolean isCardPileBaseDesignRegistrationContributedByExtension(
        /* @NonNull */
        final CardPileBaseDesignRegistration cardPileBaseDesignRegistration,
        /* @NonNull */
        final IExtension extension )
    {
        assert cardPileBaseDesignRegistration != null;
        assert extension != null;

        if( !cardPileBaseDesignRegistration.getExtensionNamespaceId().equals( extension.getNamespaceIdentifier() ) )
        {
            return false;
        }

        final String extensionSimpleId = cardPileBaseDesignRegistration.getExtensionSimpleId();
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

        final CardPileBaseDesignRegistration cardPileBaseDesignRegistration;
        try
        {
            cardPileBaseDesignRegistration = createCardPileBaseDesignRegistration( configurationElement );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPileBaseDesignRegistryExtensionPointAdapter_registerCardPileBaseDesign_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            return;
        }

        synchronized( lock_ )
        {
            cardPileBaseDesignRegistry_.registerCardPileBaseDesign( cardPileBaseDesignRegistration.getCardPileBaseDesign() );
            cardPileBaseDesignRegistrations_.add( cardPileBaseDesignRegistration );
        }
    }

    /**
     * Registers all card pile base designs in the extension registry.
     */
    @GuardedBy( "lock_" )
    private void registerCardPileBaseDesigns()
    {
        assert Thread.holdsLock( lock_ );

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
     *        The card pile base design registry service; must not be
     *        {@code null}.
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
            assertArgumentLegal( cardPileBaseDesignRegistry_ == cardPileBaseDesignRegistry, "cardPileBaseDesignRegistry", NonNlsMessages.CardPileBaseDesignRegistryExtensionPointAdapter_unbindCardPileBaseDesignRegistry_notBound ); //$NON-NLS-1$
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
            assertArgumentLegal( extensionRegistry_ == extensionRegistry, "extensionRegistry", NonNlsMessages.CardPileBaseDesignRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound ); //$NON-NLS-1$
            extensionRegistry_ = null;
        }
    }

    /**
     * Unregisters all card pile base designs.
     */
    @GuardedBy( "lock_" )
    private void unregisterCardPileBaseDesigns()
    {
        assert Thread.holdsLock( lock_ );

        for( final CardPileBaseDesignRegistration cardPileBaseDesignRegistration : cardPileBaseDesignRegistrations_ )
        {
            cardPileBaseDesignRegistry_.unregisterCardPileBaseDesign( cardPileBaseDesignRegistration.getCardPileBaseDesign() );
        }

        cardPileBaseDesignRegistrations_.clear();
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
            for( final Iterator<CardPileBaseDesignRegistration> iterator = cardPileBaseDesignRegistrations_.iterator(); iterator.hasNext(); )
            {
                final CardPileBaseDesignRegistration cardPileBaseDesignRegistration = iterator.next();
                if( isCardPileBaseDesignRegistrationContributedByExtension( cardPileBaseDesignRegistration, extension ) )
                {
                    cardPileBaseDesignRegistry_.unregisterCardPileBaseDesign( cardPileBaseDesignRegistration.getCardPileBaseDesign() );
                    iterator.remove();
                }
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Describes a card pile base design that was registered from an extension.
     */
    @Immutable
    private static final class CardPileBaseDesignRegistration
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The card pile base design contributed by the extension. */
        private final ICardPileBaseDesign cardPileBaseDesign_;

        /** The namespace identifier of the contributing extension. */
        private final String extensionNamespaceId_;

        /** The simple identifier of the contributing extension. */
        private final String extensionSimpleId_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code CardPileBaseDesignRegistration} class.
         * 
         * @param extension
         *        The extension that contributed the card pile base design; must
         *        not be {@code null}.
         * @param id
         *        The card pile base design identifier; must not be {@code null}
         *        .
         * @param width
         *        The card pile base design width in table coordinates; must not
         *        be negative.
         * @param height
         *        The card pile base design height in table coordinates; must
         *        not be negative.
         * 
         * @throws java.lang.IllegalArgumentException
         *         If {@code width} or {@code height} is negative.
         * @throws java.lang.NullPointerException
         *         If {@code id} is {@code null}.
         */
        CardPileBaseDesignRegistration(
            /* @NonNull */
            final IExtension extension,
            /* @NonNull */
            final CardPileBaseDesignId id,
            final int width,
            final int height )
        {
            assert extension != null;

            cardPileBaseDesign_ = TableFactory.createCardPileBaseDesign( id, width, height );
            extensionNamespaceId_ = extension.getNamespaceIdentifier();
            extensionSimpleId_ = extension.getSimpleIdentifier();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the card pile base design contributed by the extension.
         * 
         * @return The card pile base design contributed by the extension; never
         *         {@code null}.
         */
        /* @NonNull */
        ICardPileBaseDesign getCardPileBaseDesign()
        {
            return cardPileBaseDesign_;
        }

        /**
         * Gets the namespace identifier of the contributing extension.
         * 
         * @return The namespace identifier of the contributing extension; never
         *         {@code null}.
         */
        /* @NonNull */
        String getExtensionNamespaceId()
        {
            return extensionNamespaceId_;
        }

        /**
         * Gets the simple identifier of the contributing extension.
         * 
         * @return The simple identifier of the contributing extension; may be
         *         {@code null}.
         */
        /* @Nullable */
        String getExtensionSimpleId()
        {
            return extensionSimpleId_;
        }
    }
}
