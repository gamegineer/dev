/*
 * CardPileBaseDesignUIRegistryExtensionPointAdapter.java
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
 * Created on Aug 2, 2010 at 9:46:32 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import javax.swing.Icon;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Path;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.internal.ui.util.swing.IconProxy;
import org.gamegineer.table.ui.ICardPileBaseDesignUI;
import org.gamegineer.table.ui.ICardPileBaseDesignUIRegistry;
import org.gamegineer.table.ui.TableUIFactory;
import org.osgi.framework.Bundle;

/**
 * A component that adapts card pile base design user interfaces published via
 * the {@code org.gamegineer.table.ui.cardPileBaseDesignUIs} extension point to
 * the card pile base design user interface registry.
 */
@ThreadSafe
public final class CardPileBaseDesignUIRegistryExtensionPointAdapter
    implements IRegistryEventListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the card pile base design
     * icon.
     */
    private static final String ATTR_ICON = "icon"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the card pile base design
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the card pile base design
     * name.
     */
    private static final String ATTR_NAME = "name"; //$NON-NLS-1$

    /**
     * The collection of card pile base design user interface registrations
     * contributed from the extension registry.
     */
    @GuardedBy( "lock_" )
    private Collection<CardPileBaseDesignUIRegistration> cardPileBaseDesignUIRegistrations_;

    /** The card pile base design user interface registry service. */
    @GuardedBy( "lock_" )
    private ICardPileBaseDesignUIRegistry cardPileBaseDesignUIRegistry_;

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
     * {@code CardPileBaseDesignUIRegistryExtensionPointAdapter} class.
     */
    public CardPileBaseDesignUIRegistryExtensionPointAdapter()
    {
        cardPileBaseDesignUIRegistrations_ = new ArrayList<CardPileBaseDesignUIRegistration>();
        cardPileBaseDesignUIRegistry_ = null;
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
            registerCardPileBaseDesignUIs();
            extensionRegistry_.addListener( this, BundleConstants.CARD_PILE_BASE_DESIGN_UIS_EXTENSION_POINT_UNIQUE_ID );
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
                registerCardPileBaseDesignUI( configurationElement );
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
     * Binds the card pile base design user interface registry service to this
     * component.
     * 
     * @param cardPileBaseDesignUIRegistry
     *        The card pile base design user interface registry service; must
     *        not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the card pile base design user interface registry is already
     *         bound.
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesignUIRegistry} is {@code null}.
     */
    public void bindCardPileBaseDesignUIRegistry(
        /* @NonNull */
        final ICardPileBaseDesignUIRegistry cardPileBaseDesignUIRegistry )
    {
        assertArgumentNotNull( cardPileBaseDesignUIRegistry, "cardPileBaseDesignUIRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertStateLegal( cardPileBaseDesignUIRegistry_ == null, NonNlsMessages.CardPileBaseDesignUIRegistryExtensionPointAdapter_bindCardPileBaseDesignUIRegistry_bound );
            cardPileBaseDesignUIRegistry_ = cardPileBaseDesignUIRegistry;
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
            assertStateLegal( extensionRegistry_ == null, NonNlsMessages.CardPileBaseDesignUIRegistryExtensionPointAdapter_bindExtensionRegistry_bound );
            extensionRegistry_ = extensionRegistry;
        }
    }

    /**
     * Creates a card pile base design user interface registration based on the
     * specified extension configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     * 
     * @return A card pile base design user interface registration; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal card pile base
     *         design user interface.
     */
    /* @NonNull */
    private static CardPileBaseDesignUIRegistration createCardPileBaseDesignUIRegistration(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUIRegistration_missingId );
        }
        final CardPileBaseDesignId id = CardPileBaseDesignId.fromString( idString );

        final String name = configurationElement.getAttribute( ATTR_NAME );
        if( name == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUIRegistration_missingName );
        }

        final String iconPath = configurationElement.getAttribute( ATTR_ICON );
        if( iconPath == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUIRegistration_missingIconPath );
        }
        final Bundle bundle = ContributorFactoryOSGi.resolve( configurationElement.getContributor() );
        if( bundle == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUIRegistration_iconBundleNotFound( configurationElement.getNamespaceIdentifier() ) );
        }
        final URL iconUrl = FileLocator.find( bundle, new Path( iconPath ), null );
        if( iconUrl == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUIRegistration_iconFileNotFound( bundle, iconPath ) );
        }
        final Icon icon = new IconProxy( iconUrl );

        return new CardPileBaseDesignUIRegistration( configurationElement.getDeclaringExtension(), id, name, icon );
    }

    /**
     * Deactivates this component.
     */
    public void deactivate()
    {
        synchronized( lock_ )
        {
            extensionRegistry_.removeListener( this );
            unregisterCardPileBaseDesignUIs();
        }
    }

    /**
     * Indicates the specified card pile base design user interface registration
     * was contributed by the specified extension.
     * 
     * @param cardPileBaseDesignUIRegistration
     *        The card pile base design user interface registration; must not be
     *        {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return {@code true} if the specified card pile base design user
     *         interface registration was contributed by the specified
     *         extension; otherwise {@code false}.
     */
    private static boolean isCardPileBaseDesignUIRegistrationContributedByExtension(
        /* @NonNull */
        final CardPileBaseDesignUIRegistration cardPileBaseDesignUIRegistration,
        /* @NonNull */
        final IExtension extension )
    {
        assert cardPileBaseDesignUIRegistration != null;
        assert extension != null;

        if( !cardPileBaseDesignUIRegistration.getExtensionNamespaceId().equals( extension.getNamespaceIdentifier() ) )
        {
            return false;
        }

        final String extensionSimpleId = cardPileBaseDesignUIRegistration.getExtensionSimpleId();
        return (extensionSimpleId == null) || extensionSimpleId.equals( extension.getSimpleIdentifier() );
    }

    /**
     * Registers the card pile base design user interface represented by the
     * specified extension configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     */
    private void registerCardPileBaseDesignUI(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final CardPileBaseDesignUIRegistration cardPileBaseDesignUIRegistration;
        try
        {
            cardPileBaseDesignUIRegistration = createCardPileBaseDesignUIRegistration( configurationElement );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPileBaseDesignUIRegistryExtensionPointAdapter_registerCardPileBaseDesignUI_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            return;
        }

        synchronized( lock_ )
        {
            cardPileBaseDesignUIRegistry_.registerCardPileBaseDesignUI( cardPileBaseDesignUIRegistration.getCardPileBaseDesignUI() );
            cardPileBaseDesignUIRegistrations_.add( cardPileBaseDesignUIRegistration );
        }
    }

    /**
     * Registers all card pile base design user interfaces in the extension
     * registry.
     */
    @GuardedBy( "lock_" )
    private void registerCardPileBaseDesignUIs()
    {
        assert Thread.holdsLock( lock_ );

        for( final IConfigurationElement configurationElement : extensionRegistry_.getConfigurationElementsFor( BundleConstants.CARD_PILE_BASE_DESIGN_UIS_EXTENSION_POINT_UNIQUE_ID ) )
        {
            registerCardPileBaseDesignUI( configurationElement );
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
            unregisterCardPileBaseDesignUIs( extension );
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
     * Unbinds the card pile base design user interface registry service from
     * this component.
     * 
     * @param cardPileBaseDesignUIRegistry
     *        The card pile base design user interface registry service; must
     *        not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPileBaseDesignUIRegistry} is not currently bound.
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesignUIRegistry} is {@code null}.
     */
    public void unbindCardPileBaseDesignUIRegistry(
        /* @NonNull */
        final ICardPileBaseDesignUIRegistry cardPileBaseDesignUIRegistry )
    {
        assertArgumentNotNull( cardPileBaseDesignUIRegistry, "cardPileBaseDesignUIRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( cardPileBaseDesignUIRegistry_ == cardPileBaseDesignUIRegistry, "cardPileBaseDesignUIRegistry", NonNlsMessages.CardPileBaseDesignUIRegistryExtensionPointAdapter_unbindCardPileBaseDesignUIRegistry_notBound ); //$NON-NLS-1$
            cardPileBaseDesignUIRegistry_ = null;
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
            assertArgumentLegal( extensionRegistry_ == extensionRegistry, "extensionRegistry", NonNlsMessages.CardPileBaseDesignUIRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound ); //$NON-NLS-1$
            extensionRegistry_ = null;
        }
    }

    /**
     * Unregisters all card pile base design user interfaces.
     */
    @GuardedBy( "lock_" )
    private void unregisterCardPileBaseDesignUIs()
    {
        assert Thread.holdsLock( lock_ );

        for( final CardPileBaseDesignUIRegistration cardPileBaseDesignUIRegistration : cardPileBaseDesignUIRegistrations_ )
        {
            cardPileBaseDesignUIRegistry_.unregisterCardPileBaseDesignUI( cardPileBaseDesignUIRegistration.getCardPileBaseDesignUI() );
        }

        cardPileBaseDesignUIRegistrations_.clear();
    }

    /**
     * Unregisters all card pile base design user interfaces contributed by the
     * specified extension.
     * 
     * @param extension
     *        The extension; must not be {@code null}.
     */
    private void unregisterCardPileBaseDesignUIs(
        /* @NonNull */
        final IExtension extension )
    {
        assert extension != null;

        synchronized( lock_ )
        {
            for( final Iterator<CardPileBaseDesignUIRegistration> iterator = cardPileBaseDesignUIRegistrations_.iterator(); iterator.hasNext(); )
            {
                final CardPileBaseDesignUIRegistration cardPileBaseDesignUIRegistration = iterator.next();
                if( isCardPileBaseDesignUIRegistrationContributedByExtension( cardPileBaseDesignUIRegistration, extension ) )
                {
                    cardPileBaseDesignUIRegistry_.unregisterCardPileBaseDesignUI( cardPileBaseDesignUIRegistration.getCardPileBaseDesignUI() );
                    iterator.remove();
                }
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Describes a card pile base design user interface that was registered from
     * an extension.
     */
    @Immutable
    private static final class CardPileBaseDesignUIRegistration
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * The card pile base design user interface contributed by the
         * extension.
         */
        private final ICardPileBaseDesignUI cardPileBaseDesignUI_;

        /** The namespace identifier of the contributing extension. */
        private final String extensionNamespaceId_;

        /** The simple identifier of the contributing extension. */
        private final String extensionSimpleId_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code CardPileBaseDesignUIRegistration} class.
         * 
         * @param extension
         *        The extension that contributed the card pile base design user
         *        interface; must not be {@code null}.
         * @param id
         *        The card pile base design identifier; must not be {@code null}
         *        .
         * @param name
         *        The card pile base design name; must not be {@code null}.
         * @param icon
         *        The card pile base design icon; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code id}, {@code name}, or {@code icon} is {@code null}.
         */
        CardPileBaseDesignUIRegistration(
            /* @NonNull */
            final IExtension extension,
            /* @NonNull */
            final CardPileBaseDesignId id,
            /* @NonNull */
            final String name,
            /* @NonNull */
            final Icon icon )
        {
            assert extension != null;

            cardPileBaseDesignUI_ = TableUIFactory.createCardPileBaseDesignUI( id, name, icon );
            extensionNamespaceId_ = extension.getNamespaceIdentifier();
            extensionSimpleId_ = extension.getSimpleIdentifier();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the card pile base design user interface contributed by the
         * extension.
         * 
         * @return The card pile base design user interface contributed by the
         *         extension; never {@code null}.
         */
        /* @NonNull */
        ICardPileBaseDesignUI getCardPileBaseDesignUI()
        {
            return cardPileBaseDesignUI_;
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
