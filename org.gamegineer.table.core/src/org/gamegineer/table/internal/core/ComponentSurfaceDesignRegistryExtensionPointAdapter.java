/*
 * ComponentSurfaceDesignRegistryExtensionPointAdapter.java
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
 * Created on Apr 7, 2012 at 9:37:50 PM.
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
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IComponentSurfaceDesignRegistry;

/**
 * A component that adapts component surface designs published via the
 * {@code org.gamegineer.table.core.componentSurfaceDesigns} extension point to
 * the component surface design registry.
 */
@ThreadSafe
public final class ComponentSurfaceDesignRegistryExtensionPointAdapter
    implements IRegistryEventListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the component surface
     * design height.
     */
    private static final String ATTR_HEIGHT = "height"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the component surface
     * design identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the component surface
     * design width.
     */
    private static final String ATTR_WIDTH = "width"; //$NON-NLS-1$

    /**
     * The collection of component surface design registrations contributed from
     * the extension registry.
     */
    @GuardedBy( "lock_" )
    private Collection<ComponentSurfaceDesignRegistration> componentSurfaceDesignRegistrations_;

    /** The component surface design registry service. */
    @GuardedBy( "lock_" )
    private IComponentSurfaceDesignRegistry componentSurfaceDesignRegistry_;

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
     * {@code ComponentSurfaceDesignRegistryExtensionPointAdapter} class.
     */
    public ComponentSurfaceDesignRegistryExtensionPointAdapter()
    {
        componentSurfaceDesignRegistrations_ = new ArrayList<ComponentSurfaceDesignRegistration>();
        componentSurfaceDesignRegistry_ = null;
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
            registerComponentSurfaceDesigns();
            extensionRegistry_.addListener( this, BundleConstants.COMPONENT_SURFACE_DESIGNS_EXTENSION_POINT_UNIQUE_ID );
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
                registerComponentSurfaceDesign( configurationElement );
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
     * Binds the component surface design registry service to this component.
     * 
     * @param componentSurfaceDesignRegistry
     *        The component surface design registry service; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the component surface design registry is already bound.
     * @throws java.lang.NullPointerException
     *         If {@code componentSurfaceDesignRegistry} is {@code null}.
     */
    public void bindComponentSurfaceDesignRegistry(
        /* @NonNull */
        final IComponentSurfaceDesignRegistry componentSurfaceDesignRegistry )
    {
        assertArgumentNotNull( componentSurfaceDesignRegistry, "componentSurfaceDesignRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertStateLegal( componentSurfaceDesignRegistry_ == null, NonNlsMessages.ComponentSurfaceDesignRegistryExtensionPointAdapter_bindComponentSurfaceDesignRegistry_bound );
            componentSurfaceDesignRegistry_ = componentSurfaceDesignRegistry;
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
            assertStateLegal( extensionRegistry_ == null, NonNlsMessages.ComponentSurfaceDesignRegistryExtensionPointAdapter_bindExtensionRegistry_bound );
            extensionRegistry_ = extensionRegistry;
        }
    }

    /**
     * Creates a component surface design registration based on the specified
     * extension configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     * 
     * @return A component surface design registration; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal component
     *         surface design.
     */
    /* @NonNull */
    private static ComponentSurfaceDesignRegistration createComponentSurfaceDesignRegistration(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignRegistryExtensionPointAdapter_createComponentSurfaceDesignRegistration_missingId );
        }
        final ComponentSurfaceDesignId id = ComponentSurfaceDesignId.fromString( idString );

        final int width;
        try
        {
            width = Integer.parseInt( configurationElement.getAttribute( ATTR_WIDTH ) );
        }
        catch( final NumberFormatException e )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignRegistryExtensionPointAdapter_createComponentSurfaceDesignRegistration_parseWidthError, e );
        }

        final int height;
        try
        {
            height = Integer.parseInt( configurationElement.getAttribute( ATTR_HEIGHT ) );
        }
        catch( final NumberFormatException e )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignRegistryExtensionPointAdapter_createComponentSurfaceDesignRegistration_parseHeightError, e );
        }

        return new ComponentSurfaceDesignRegistration( configurationElement.getDeclaringExtension(), new ComponentSurfaceDesign( id, width, height ) );
    }

    /**
     * Deactivates this component.
     */
    public void deactivate()
    {
        synchronized( lock_ )
        {
            extensionRegistry_.removeListener( this );
            unregisterComponentSurfaceDesigns();
        }
    }

    /**
     * Indicates the specified component surface design registration was
     * contributed by the specified extension.
     * 
     * @param componentSurfaceDesignRegistration
     *        The component surface design registration; must not be
     *        {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return {@code true} if the specified component surface design
     *         registration was contributed by the specified extension;
     *         otherwise {@code false}.
     */
    private static boolean isComponentSurfaceDesignRegistrationContributedByExtension(
        /* @NonNull */
        final ComponentSurfaceDesignRegistration componentSurfaceDesignRegistration,
        /* @NonNull */
        final IExtension extension )
    {
        assert componentSurfaceDesignRegistration != null;
        assert extension != null;

        if( !componentSurfaceDesignRegistration.getExtensionNamespaceId().equals( extension.getNamespaceIdentifier() ) )
        {
            return false;
        }

        final String extensionSimpleId = componentSurfaceDesignRegistration.getExtensionSimpleId();
        return (extensionSimpleId == null) || extensionSimpleId.equals( extension.getSimpleIdentifier() );
    }

    /**
     * Registers the component surface design represented by the specified
     * extension configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     */
    private void registerComponentSurfaceDesign(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final ComponentSurfaceDesignRegistration componentSurfaceDesignRegistration;
        try
        {
            componentSurfaceDesignRegistration = createComponentSurfaceDesignRegistration( configurationElement );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentSurfaceDesignRegistryExtensionPointAdapter_registerComponentSurfaceDesign_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            return;
        }

        synchronized( lock_ )
        {
            componentSurfaceDesignRegistry_.register( componentSurfaceDesignRegistration.getComponentSurfaceDesign() );
            componentSurfaceDesignRegistrations_.add( componentSurfaceDesignRegistration );
        }
    }

    /**
     * Registers all component surface designs in the extension registry.
     */
    @GuardedBy( "lock_" )
    private void registerComponentSurfaceDesigns()
    {
        assert Thread.holdsLock( lock_ );

        for( final IConfigurationElement configurationElement : extensionRegistry_.getConfigurationElementsFor( BundleConstants.COMPONENT_SURFACE_DESIGNS_EXTENSION_POINT_UNIQUE_ID ) )
        {
            registerComponentSurfaceDesign( configurationElement );
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
            unregisterComponentSurfaceDesigns( extension );
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
     * Unbinds the component surface design registry service from this
     * component.
     * 
     * @param componentSurfaceDesignRegistry
     *        The component surface design registry service; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code componentSurfaceDesignRegistry} is not currently bound.
     * @throws java.lang.NullPointerException
     *         If {@code componentSurfaceDesignRegistry} is {@code null}.
     */
    public void unbindComponentSurfaceDesignRegistry(
        /* @NonNull */
        final IComponentSurfaceDesignRegistry componentSurfaceDesignRegistry )
    {
        assertArgumentNotNull( componentSurfaceDesignRegistry, "componentSurfaceDesignRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( componentSurfaceDesignRegistry_ == componentSurfaceDesignRegistry, "componentSurfaceDesignRegistry", NonNlsMessages.ComponentSurfaceDesignRegistryExtensionPointAdapter_unbindComponentSurfaceDesignRegistry_notBound ); //$NON-NLS-1$
            componentSurfaceDesignRegistry_ = null;
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
            assertArgumentLegal( extensionRegistry_ == extensionRegistry, "extensionRegistry", NonNlsMessages.ComponentSurfaceDesignRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound ); //$NON-NLS-1$
            extensionRegistry_ = null;
        }
    }

    /**
     * Unregisters all component surface designs.
     */
    @GuardedBy( "lock_" )
    private void unregisterComponentSurfaceDesigns()
    {
        assert Thread.holdsLock( lock_ );

        for( final ComponentSurfaceDesignRegistration componentSurfaceDesignRegistration : componentSurfaceDesignRegistrations_ )
        {
            componentSurfaceDesignRegistry_.unregister( componentSurfaceDesignRegistration.getComponentSurfaceDesign() );
        }

        componentSurfaceDesignRegistrations_.clear();
    }

    /**
     * Unregisters all component surface designs contributed by the specified
     * extension.
     * 
     * @param extension
     *        The extension; must not be {@code null}.
     */
    private void unregisterComponentSurfaceDesigns(
        /* @NonNull */
        final IExtension extension )
    {
        assert extension != null;

        synchronized( lock_ )
        {
            for( final Iterator<ComponentSurfaceDesignRegistration> iterator = componentSurfaceDesignRegistrations_.iterator(); iterator.hasNext(); )
            {
                final ComponentSurfaceDesignRegistration componentSurfaceDesignRegistration = iterator.next();
                if( isComponentSurfaceDesignRegistrationContributedByExtension( componentSurfaceDesignRegistration, extension ) )
                {
                    componentSurfaceDesignRegistry_.unregister( componentSurfaceDesignRegistration.getComponentSurfaceDesign() );
                    iterator.remove();
                }
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Describes a component surface design that was registered from an
     * extension.
     */
    @Immutable
    private static final class ComponentSurfaceDesignRegistration
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The component surface design contributed by the extension. */
        private final ComponentSurfaceDesign componentSurfaceDesign_;

        /** The namespace identifier of the contributing extension. */
        private final String extensionNamespaceId_;

        /** The simple identifier of the contributing extension. */
        private final String extensionSimpleId_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code ComponentSurfaceDesignRegistration} class.
         * 
         * @param extension
         *        The extension that contributed the component surface design;
         *        must not be {@code null}.
         * @param componentSurfaceDesign
         *        The component surface design contributed by the extension;
         *        must not be {@code null}.
         */
        ComponentSurfaceDesignRegistration(
            /* @NonNull */
            final IExtension extension,
            /* @NonNull */
            final ComponentSurfaceDesign componentSurfaceDesign )
        {
            assert extension != null;
            assert componentSurfaceDesign != null;

            componentSurfaceDesign_ = componentSurfaceDesign;
            extensionNamespaceId_ = extension.getNamespaceIdentifier();
            extensionSimpleId_ = extension.getSimpleIdentifier();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the component surface design contributed by the extension.
         * 
         * @return The component surface design contributed by the extension;
         *         never {@code null}.
         */
        /* @NonNull */
        ComponentSurfaceDesign getComponentSurfaceDesign()
        {
            return componentSurfaceDesign_;
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
