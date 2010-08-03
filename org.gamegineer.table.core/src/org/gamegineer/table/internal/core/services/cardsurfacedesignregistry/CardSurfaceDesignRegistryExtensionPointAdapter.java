/*
 * CardSurfaceDesignRegistryExtensionPointAdapter.java
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
 * Created on Jul 31, 2010 at 10:10:51 PM.
 */

package org.gamegineer.table.internal.core.services.cardsurfacedesignregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.awt.Dimension;
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
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.core.services.cardsurfacedesignregistry.ICardSurfaceDesignRegistry;
import org.gamegineer.table.internal.core.BundleConstants;
import org.gamegineer.table.internal.core.Loggers;

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
    private Collection<CardSurfaceDesign> cardSurfaceDesigns_;

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
        cardSurfaceDesigns_ = new ArrayList<CardSurfaceDesign>();
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
            assertStateLegal( cardSurfaceDesignRegistry_ == null, Messages.CardSurfaceDesignRegistryExtensionPointAdapter_bindCardSurfaceDesignRegistry_bound );
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
            assertStateLegal( extensionRegistry_ == null, Messages.CardSurfaceDesignRegistryExtensionPointAdapter_bindExtensionRegistry_bound );
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
     * @return A card surface design or {@code null} if a card surface design
     *         could not be created from the specified extension configuration
     *         element.
     */
    /* @Nullable */
    private static CardSurfaceDesign createCardSurfaceDesign(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        try
        {
            final CardSurfaceDesignId id = CardSurfaceDesignId.fromString( configurationElement.getAttribute( ATTR_ID ) );
            final int width = Integer.parseInt( configurationElement.getAttribute( ATTR_WIDTH ) );
            final int height = Integer.parseInt( configurationElement.getAttribute( ATTR_HEIGHT ) );
            return new CardSurfaceDesign( configurationElement.getDeclaringExtension(), id, width, height );
        }
        catch( final NumberFormatException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.CardSurfaceDesignRegistryExtensionPointAdapter_createCardSurfaceDesign_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            return null;
        }
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
        final CardSurfaceDesign cardSurfaceDesign,
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

        final CardSurfaceDesign cardSurfaceDesign = createCardSurfaceDesign( configurationElement );
        if( cardSurfaceDesign != null )
        {
            synchronized( lock_ )
            {
                cardSurfaceDesignRegistry_.registerCardSurfaceDesign( cardSurfaceDesign );
                cardSurfaceDesigns_.add( cardSurfaceDesign );
            }
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
     * Unbinds the card surface design registry service to this component.
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
            assertArgumentLegal( cardSurfaceDesignRegistry_ == cardSurfaceDesignRegistry, "cardSurfaceDesignRegistry", Messages.CardSurfaceDesignRegistryExtensionPointAdapter_unbindCardSurfaceDesignRegistry_notBound ); //$NON-NLS-1$
            cardSurfaceDesignRegistry_ = null;
        }
    }

    /**
     * Unbinds the extension registry service to this component.
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
            assertArgumentLegal( extensionRegistry_ == extensionRegistry, "extensionRegistry", Messages.CardSurfaceDesignRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound ); //$NON-NLS-1$
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
            for( final CardSurfaceDesign cardSurfaceDesign : cardSurfaceDesigns_ )
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
            for( final Iterator<CardSurfaceDesign> iterator = cardSurfaceDesigns_.iterator(); iterator.hasNext(); )
            {
                final CardSurfaceDesign cardSurfaceDesign = iterator.next();
                if( isCardSurfaceDesignContributedByExtension( cardSurfaceDesign, extension ) )
                {
                    cardSurfaceDesignRegistry_.unregisterCardSurfaceDesign( cardSurfaceDesign );
                    iterator.remove();
                }
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Implementation of {@link org.gamegineer.table.core.ICardSurfaceDesign}
     * created from an extension.
     */
    @Immutable
    private static final class CardSurfaceDesign
        implements ICardSurfaceDesign
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The card surface design to which all behavior is delegated. */
        private final ICardSurfaceDesign delegate_;

        /** The namespace identifier of the contributing extension. */
        private final String extensionNamespaceId_;

        /** The simple identifier of the contributing extension. */
        private final String extensionSimpleId_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardSurfaceDesign} class.
         * 
         * @param extension
         *        The extension that contributed this card surface design; must
         *        not be {@code null}.
         * @param id
         *        The card surface design identifier; must not be {@code null}.
         * @param width
         *        The card surface design width in table coordinates; must not
         *        be negative.
         * @param height
         *        The card surface design height in table coordinates; must not
         *        be negative.
         * 
         * @throws java.lang.IllegalArgumentException
         *         If {@code width} or {@code height} is negative.
         * @throws java.lang.NullPointerException
         *         If {@code id} is {@code null}.
         */
        CardSurfaceDesign(
            /* @NonNull */
            final IExtension extension,
            /* @NonNull */
            final CardSurfaceDesignId id,
            final int width,
            final int height )
        {
            assert extension != null;

            extensionNamespaceId_ = extension.getNamespaceIdentifier();
            extensionSimpleId_ = extension.getSimpleIdentifier();
            delegate_ = TableFactory.createCardSurfaceDesign( id, width, height );
        }


        // ==================================================================
        // Methods
        // ==================================================================

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

        /*
         * @see org.gamegineer.table.core.ICardSurfaceDesign#getId()
         */
        @Override
        public CardSurfaceDesignId getId()
        {
            return delegate_.getId();
        }

        /*
         * @see org.gamegineer.table.core.ICardSurfaceDesign#getSize()
         */
        @Override
        public Dimension getSize()
        {
            return delegate_.getSize();
        }
    }
}
