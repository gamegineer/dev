/*
 * CardSurfaceDesignUIRegistryExtensionPointAdapter.java
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
 * Created on Aug 4, 2010 at 10:00:14 PM.
 */

package org.gamegineer.table.internal.ui.services.cardsurfacedesignuiregistry;

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
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
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
 * A component that adapts card surface design user interfaces published via the
 * {@code org.gamegineer.table.ui.cardSurfaceDesignUIs} extension point to the
 * card surface design user interface registry.
 */
@ThreadSafe
public final class CardSurfaceDesignUIRegistryExtensionPointAdapter
    implements IRegistryEventListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the card surface design
     * icon.
     */
    private static final String ATTR_ICON = "icon"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the card surface design
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the card surface design
     * name.
     */
    private static final String ATTR_NAME = "name"; //$NON-NLS-1$

    /** The card surface design user interface registry service. */
    @GuardedBy( "lock_" )
    private ICardSurfaceDesignUIRegistry cardSurfaceDesignUIRegistry_;

    /**
     * The collection of card surface design user interfaces created from the
     * extension registry.
     */
    @GuardedBy( "lock_" )
    private Collection<CardSurfaceDesignUI> cardSurfaceDesignUIs_;

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
     * CardSurfaceDesignUIRegistryExtensionPointAdapter} class.
     */
    public CardSurfaceDesignUIRegistryExtensionPointAdapter()
    {
        lock_ = new Object();
        cardSurfaceDesignUIRegistry_ = null;
        cardSurfaceDesignUIs_ = new ArrayList<CardSurfaceDesignUI>();
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
        registerCardSurfaceDesignUIs();
        extensionRegistry_.addListener( this, BundleConstants.CARD_SURFACE_DESIGN_UIS_EXTENSION_POINT_UNIQUE_ID );
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
                registerCardSurfaceDesignUI( configurationElement );
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
     * Binds the card surface design user interface registry service to this
     * component.
     * 
     * @param cardSurfaceDesignUIRegistry
     *        The card surface design user interface registry service; must not
     *        be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the card surface design user interface registry is already
     *         bound.
     * @throws java.lang.NullPointerException
     *         If {@code cardSurfaceDesignUIRegistry} is {@code null}.
     */
    public void bindCardSurfaceDesignUIRegistry(
        /* @NonNull */
        final ICardSurfaceDesignUIRegistry cardSurfaceDesignUIRegistry )
    {
        assertArgumentNotNull( cardSurfaceDesignUIRegistry, "cardSurfaceDesignUIRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertStateLegal( cardSurfaceDesignUIRegistry_ == null, Messages.CardSurfaceDesignUIRegistryExtensionPointAdapter_bindCardSurfaceDesignUIRegistry_bound );
            cardSurfaceDesignUIRegistry_ = cardSurfaceDesignUIRegistry;
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
            assertStateLegal( extensionRegistry_ == null, Messages.CardSurfaceDesignUIRegistryExtensionPointAdapter_bindExtensionRegistry_bound );
            extensionRegistry_ = extensionRegistry;
        }
    }

    /**
     * Creates a card surface design user interface based on the specified
     * extension configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     * 
     * @return A card surface design user interface; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal card surface
     *         design user interface.
     */
    /* @NonNull */
    private static CardSurfaceDesignUI createCardSurfaceDesignUI(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_missingId );
        }
        final CardSurfaceDesignId id = CardSurfaceDesignId.fromString( idString );

        final String name = configurationElement.getAttribute( ATTR_NAME );
        if( name == null )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_missingName );
        }

        final String iconPath = configurationElement.getAttribute( ATTR_ICON );
        if( iconPath == null )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_missingIconPath );
        }
        final PackageAdmin packageAdmin = Activator.getDefault().getPackageAdmin();
        if( packageAdmin == null )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_noPackageAdminService );
        }
        final Bundle[] bundles = packageAdmin.getBundles( configurationElement.getNamespaceIdentifier(), null );
        if( (bundles == null) || (bundles.length == 0) )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_iconBundleNotFound( configurationElement.getNamespaceIdentifier() ) );
        }
        final URL iconUrl = FileLocator.find( bundles[ 0 ], new Path( iconPath ), null );
        if( iconUrl == null )
        {
            throw new IllegalArgumentException( Messages.CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_iconFileNotFound( bundles[ 0 ], iconPath ) );
        }
        final Icon icon = new IconProxy( iconUrl );

        return new CardSurfaceDesignUI( configurationElement.getDeclaringExtension(), id, name, icon );
    }

    /**
     * Deactivates this component.
     */
    public void deactivate()
    {
        extensionRegistry_.removeListener( this );
        unregisterCardSurfaceDesignUIs();
    }

    /**
     * Indicates the specified card surface design user interface was
     * contributed by the specified extension.
     * 
     * @param cardSurfaceDesignUI
     *        The card surface design user interface; must not be {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return {@code true} if the specified card surface design user interface
     *         was contributed by the specified extension; otherwise {@code
     *         false}.
     */
    private static boolean isCardSurfaceDesignUIContributedByExtension(
        /* @NonNull */
        final CardSurfaceDesignUI cardSurfaceDesignUI,
        /* @NonNull */
        final IExtension extension )
    {
        assert cardSurfaceDesignUI != null;
        assert extension != null;

        if( !cardSurfaceDesignUI.getExtensionNamespaceId().equals( extension.getNamespaceIdentifier() ) )
        {
            return false;
        }

        final String extensionSimpleId = cardSurfaceDesignUI.getExtensionSimpleId();
        return (extensionSimpleId == null) || extensionSimpleId.equals( extension.getSimpleIdentifier() );
    }

    /**
     * Registers the card surface design user interface represented by the
     * specified extension configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     */
    private void registerCardSurfaceDesignUI(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final CardSurfaceDesignUI cardSurfaceDesignUI;
        try
        {
            cardSurfaceDesignUI = createCardSurfaceDesignUI( configurationElement );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.CardSurfaceDesignUIRegistryExtensionPointAdapter_registerCardSurfaceDesignUI_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            return;
        }

        synchronized( lock_ )
        {
            cardSurfaceDesignUIRegistry_.registerCardSurfaceDesignUI( cardSurfaceDesignUI );
            cardSurfaceDesignUIs_.add( cardSurfaceDesignUI );
        }
    }

    /**
     * Registers all card surface design user interfaces in the extension
     * registry.
     */
    private void registerCardSurfaceDesignUIs()
    {
        for( final IConfigurationElement configurationElement : extensionRegistry_.getConfigurationElementsFor( BundleConstants.CARD_SURFACE_DESIGN_UIS_EXTENSION_POINT_UNIQUE_ID ) )
        {
            registerCardSurfaceDesignUI( configurationElement );
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
            unregisterCardSurfaceDesignUIs( extension );
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
     * Unbinds the card surface design user interface registry service from this
     * component.
     * 
     * @param cardSurfaceDesignUIRegistry
     *        The card surface design user interface registry service; must not
     *        be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardSurfaceDesignUIRegistry} is not currently bound.
     * @throws java.lang.NullPointerException
     *         If {@code cardSurfaceDesignUIRegistry} is {@code null}.
     */
    public void unbindCardSurfaceDesignUIRegistry(
        /* @NonNull */
        final ICardSurfaceDesignUIRegistry cardSurfaceDesignUIRegistry )
    {
        assertArgumentNotNull( cardSurfaceDesignUIRegistry, "cardSurfaceDesignUIRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( cardSurfaceDesignUIRegistry_ == cardSurfaceDesignUIRegistry, "cardSurfaceDesignUIRegistry", Messages.CardSurfaceDesignUIRegistryExtensionPointAdapter_unbindCardSurfaceDesignUIRegistry_notBound ); //$NON-NLS-1$
            cardSurfaceDesignUIRegistry_ = null;
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
            assertArgumentLegal( extensionRegistry_ == extensionRegistry, "extensionRegistry", Messages.CardSurfaceDesignUIRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound ); //$NON-NLS-1$
            extensionRegistry_ = null;
        }
    }

    /**
     * Unregisters all card surface design user interfaces.
     */
    private void unregisterCardSurfaceDesignUIs()
    {
        synchronized( lock_ )
        {
            for( final CardSurfaceDesignUI cardSurfaceDesignUI : cardSurfaceDesignUIs_ )
            {
                cardSurfaceDesignUIRegistry_.unregisterCardSurfaceDesignUI( cardSurfaceDesignUI );
            }

            cardSurfaceDesignUIs_.clear();
        }
    }

    /**
     * Unregisters all card surface design user interfaces contributed by the
     * specified extension.
     * 
     * @param extension
     *        The extension; must not be {@code null}.
     */
    private void unregisterCardSurfaceDesignUIs(
        /* @NonNull */
        final IExtension extension )
    {
        assert extension != null;

        synchronized( lock_ )
        {
            for( final Iterator<CardSurfaceDesignUI> iterator = cardSurfaceDesignUIs_.iterator(); iterator.hasNext(); )
            {
                final CardSurfaceDesignUI cardSurfaceDesignUI = iterator.next();
                if( isCardSurfaceDesignUIContributedByExtension( cardSurfaceDesignUI, extension ) )
                {
                    cardSurfaceDesignUIRegistry_.unregisterCardSurfaceDesignUI( cardSurfaceDesignUI );
                    iterator.remove();
                }
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Implementation of {@link org.gamegineer.table.ui.ICardSurfaceDesignUI}
     * created from an extension.
     */
    @Immutable
    private static final class CardSurfaceDesignUI
        implements ICardSurfaceDesignUI
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * The card surface design user interface to which all behavior is
         * delegated.
         */
        private final ICardSurfaceDesignUI delegate_;

        /** The namespace identifier of the contributing extension. */
        private final String extensionNamespaceId_;

        /** The simple identifier of the contributing extension. */
        private final String extensionSimpleId_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardSurfaceDesignUI} class.
         * 
         * @param extension
         *        The extension that contributed this card surface design user
         *        interface; must not be {@code null}.
         * @param id
         *        The card surface design identifier; must not be {@code null}.
         * @param name
         *        The card surface design name; must not be {@code null}.
         * @param icon
         *        The card surface design icon; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code id}, {@code name}, or {@code icon} is {@code null}.
         */
        CardSurfaceDesignUI(
            /* @NonNull */
            final IExtension extension,
            /* @NonNull */
            final CardSurfaceDesignId id,
            /* @NonNull */
            final String name,
            /* @NonNull */
            final Icon icon )
        {
            assert extension != null;

            extensionNamespaceId_ = extension.getNamespaceIdentifier();
            extensionSimpleId_ = extension.getSimpleIdentifier();
            delegate_ = TableUIFactory.createCardSurfaceDesignUI( id, name, icon );
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
         * @see org.gamegineer.table.ui.ICardSurfaceDesignUI#getIcon()
         */
        @Override
        public Icon getIcon()
        {
            return delegate_.getIcon();
        }

        /*
         * @see org.gamegineer.table.ui.ICardSurfaceDesignUI#getId()
         */
        @Override
        public CardSurfaceDesignId getId()
        {
            return delegate_.getId();
        }

        /*
         * @see org.gamegineer.table.ui.ICardSurfaceDesignUI#getName()
         */
        @Override
        public String getName()
        {
            return delegate_.getName();
        }
    }
}
