/*
 * ComponentSurfaceDesignUIRegistryExtensionPointAdapter.java
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
 * Created on Apr 23, 2012 at 8:22:36 PM.
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
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.internal.ui.util.swing.IconProxy;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;
import org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry;
import org.osgi.framework.Bundle;

/**
 * A component that adapts component surface design user interfaces published
 * via the {@code org.gamegineer.table.ui.componentSurfaceDesignUIs} extension
 * point to the component surface design user interface registry.
 */
@ThreadSafe
public final class ComponentSurfaceDesignUIRegistryExtensionPointAdapter
    implements IRegistryEventListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the component surface
     * design icon.
     */
    private static final String ATTR_ICON = "icon"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the component surface
     * design identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the component surface
     * design name.
     */
    private static final String ATTR_NAME = "name"; //$NON-NLS-1$

    /**
     * The collection of component surface design user interface registrations
     * contributed from the extension registry.
     */
    @GuardedBy( "lock_" )
    private Collection<ComponentSurfaceDesignUIRegistration> componentSurfaceDesignUIRegistrations_;

    /** The component surface design user interface registry service. */
    @GuardedBy( "lock_" )
    private IComponentSurfaceDesignUIRegistry componentSurfaceDesignUIRegistry_;

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
     * {@code ComponentSurfaceDesignUIRegistryExtensionPointAdapter} class.
     */
    public ComponentSurfaceDesignUIRegistryExtensionPointAdapter()
    {
        componentSurfaceDesignUIRegistrations_ = new ArrayList<ComponentSurfaceDesignUIRegistration>();
        componentSurfaceDesignUIRegistry_ = null;
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
            registerComponentSurfaceDesignUIs();
            extensionRegistry_.addListener( this, BundleConstants.COMPONENT_SURFACE_DESIGN_UIS_EXTENSION_POINT_UNIQUE_ID );
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
                registerComponentSurfaceDesignUI( configurationElement );
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
     * Binds the component surface design user interface registry service to
     * this component.
     * 
     * @param componentSurfaceDesignUIRegistry
     *        The component surface design user interface registry service; must
     *        not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the component surface design user interface registry is
     *         already bound.
     * @throws java.lang.NullPointerException
     *         If {@code componentSurfaceDesignUIRegistry} is {@code null}.
     */
    public void bindComponentSurfaceDesignUIRegistry(
        /* @NonNull */
        final IComponentSurfaceDesignUIRegistry componentSurfaceDesignUIRegistry )
    {
        assertArgumentNotNull( componentSurfaceDesignUIRegistry, "componentSurfaceDesignUIRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertStateLegal( componentSurfaceDesignUIRegistry_ == null, NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_bindComponentSurfaceDesignUIRegistry_bound );
            componentSurfaceDesignUIRegistry_ = componentSurfaceDesignUIRegistry;
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
            assertStateLegal( extensionRegistry_ == null, NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_bindExtensionRegistry_bound );
            extensionRegistry_ = extensionRegistry;
        }
    }

    /**
     * Creates a component surface design user interface registration based on
     * the specified extension configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     * 
     * @return A component surface design user interface registration; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal component
     *         surface design user interface.
     */
    /* @NonNull */
    private static ComponentSurfaceDesignUIRegistration createComponentSurfaceDesignUIRegistration(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_missingId );
        }
        final ComponentSurfaceDesignId id = ComponentSurfaceDesignId.fromString( idString );

        final String name = configurationElement.getAttribute( ATTR_NAME );
        if( name == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_missingName );
        }

        final String iconPath = configurationElement.getAttribute( ATTR_ICON );
        if( iconPath == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_missingIconPath );
        }
        final Bundle bundle = ContributorFactoryOSGi.resolve( configurationElement.getContributor() );
        if( bundle == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_iconBundleNotFound( configurationElement.getNamespaceIdentifier() ) );
        }
        final URL iconUrl = FileLocator.find( bundle, new Path( iconPath ), null );
        if( iconUrl == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_iconFileNotFound( bundle, iconPath ) );
        }
        final Icon icon = new IconProxy( iconUrl );

        return new ComponentSurfaceDesignUIRegistration( configurationElement.getDeclaringExtension(), id, name, icon );
    }

    /**
     * Deactivates this component.
     */
    public void deactivate()
    {
        synchronized( lock_ )
        {
            extensionRegistry_.removeListener( this );
            unregisterComponentSurfaceDesignUIs();
        }
    }

    /**
     * Indicates the specified component surface design user interface
     * registration was contributed by the specified extension.
     * 
     * @param componentSurfaceDesignUIRegistration
     *        The component surface design user interface registration; must not
     *        be {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return {@code true} if the specified component surface design user
     *         interface registration was contributed by the specified
     *         extension; otherwise {@code false}.
     */
    private static boolean isComponentSurfaceDesignUIRegistrationContributedByExtension(
        /* @NonNull */
        final ComponentSurfaceDesignUIRegistration componentSurfaceDesignUIRegistration,
        /* @NonNull */
        final IExtension extension )
    {
        assert componentSurfaceDesignUIRegistration != null;
        assert extension != null;

        if( !componentSurfaceDesignUIRegistration.getExtensionNamespaceId().equals( extension.getNamespaceIdentifier() ) )
        {
            return false;
        }

        final String extensionSimpleId = componentSurfaceDesignUIRegistration.getExtensionSimpleId();
        return (extensionSimpleId == null) || extensionSimpleId.equals( extension.getSimpleIdentifier() );
    }

    /**
     * Registers the component surface design user interface represented by the
     * specified extension configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     */
    private void registerComponentSurfaceDesignUI(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final ComponentSurfaceDesignUIRegistration componentSurfaceDesignUIRegistration;
        try
        {
            componentSurfaceDesignUIRegistration = createComponentSurfaceDesignUIRegistration( configurationElement );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_registerComponentSurfaceDesignUI_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            return;
        }

        synchronized( lock_ )
        {
            componentSurfaceDesignUIRegistry_.registerComponentSurfaceDesignUI( componentSurfaceDesignUIRegistration.getComponentSurfaceDesignUI() );
            componentSurfaceDesignUIRegistrations_.add( componentSurfaceDesignUIRegistration );
        }
    }

    /**
     * Registers all component surface design user interfaces in the extension
     * registry.
     */
    @GuardedBy( "lock_" )
    private void registerComponentSurfaceDesignUIs()
    {
        assert Thread.holdsLock( lock_ );

        for( final IConfigurationElement configurationElement : extensionRegistry_.getConfigurationElementsFor( BundleConstants.COMPONENT_SURFACE_DESIGN_UIS_EXTENSION_POINT_UNIQUE_ID ) )
        {
            registerComponentSurfaceDesignUI( configurationElement );
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
            unregisterComponentSurfaceDesignUIs( extension );
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
     * Unbinds the component surface design user interface registry service from
     * this component.
     * 
     * @param componentSurfaceDesignUIRegistry
     *        The component surface design user interface registry service; must
     *        not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code componentSurfaceDesignUIRegistry} is not currently
     *         bound.
     * @throws java.lang.NullPointerException
     *         If {@code componentSurfaceDesignUIRegistry} is {@code null}.
     */
    public void unbindComponentSurfaceDesignUIRegistry(
        /* @NonNull */
        final IComponentSurfaceDesignUIRegistry componentSurfaceDesignUIRegistry )
    {
        assertArgumentNotNull( componentSurfaceDesignUIRegistry, "componentSurfaceDesignUIRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( componentSurfaceDesignUIRegistry_ == componentSurfaceDesignUIRegistry, "componentSurfaceDesignUIRegistry", NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_unbindComponentSurfaceDesignUIRegistry_notBound ); //$NON-NLS-1$
            componentSurfaceDesignUIRegistry_ = null;
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
            assertArgumentLegal( extensionRegistry_ == extensionRegistry, "extensionRegistry", NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound ); //$NON-NLS-1$
            extensionRegistry_ = null;
        }
    }

    /**
     * Unregisters all component surface design user interfaces.
     */
    @GuardedBy( "lock_" )
    private void unregisterComponentSurfaceDesignUIs()
    {
        assert Thread.holdsLock( lock_ );

        for( final ComponentSurfaceDesignUIRegistration componentSurfaceDesignUIRegistration : componentSurfaceDesignUIRegistrations_ )
        {
            componentSurfaceDesignUIRegistry_.unregisterComponentSurfaceDesignUI( componentSurfaceDesignUIRegistration.getComponentSurfaceDesignUI() );
        }

        componentSurfaceDesignUIRegistrations_.clear();
    }

    /**
     * Unregisters all component surface design user interfaces contributed by
     * the specified extension.
     * 
     * @param extension
     *        The extension; must not be {@code null}.
     */
    private void unregisterComponentSurfaceDesignUIs(
        /* @NonNull */
        final IExtension extension )
    {
        assert extension != null;

        synchronized( lock_ )
        {
            for( final Iterator<ComponentSurfaceDesignUIRegistration> iterator = componentSurfaceDesignUIRegistrations_.iterator(); iterator.hasNext(); )
            {
                final ComponentSurfaceDesignUIRegistration componentSurfaceDesignUIRegistration = iterator.next();
                if( isComponentSurfaceDesignUIRegistrationContributedByExtension( componentSurfaceDesignUIRegistration, extension ) )
                {
                    componentSurfaceDesignUIRegistry_.unregisterComponentSurfaceDesignUI( componentSurfaceDesignUIRegistration.getComponentSurfaceDesignUI() );
                    iterator.remove();
                }
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Describes a component surface design user interface that was registered
     * from an extension.
     */
    @Immutable
    private static final class ComponentSurfaceDesignUIRegistration
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * The component surface design user interface contributed by the
         * extension.
         */
        private final ComponentSurfaceDesignUI componentSurfaceDesignUI_;

        /** The namespace identifier of the contributing extension. */
        private final String extensionNamespaceId_;

        /** The simple identifier of the contributing extension. */
        private final String extensionSimpleId_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code ComponentSurfaceDesignUIRegistration} class.
         * 
         * @param extension
         *        The extension that contributed the component surface design
         *        user interface; must not be {@code null}.
         * @param id
         *        The component surface design identifier; must not be
         *        {@code null}.
         * @param name
         *        The component surface design name; must not be {@code null}.
         * @param icon
         *        The component surface design icon; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code id}, {@code name}, or {@code icon} is {@code null}.
         */
        ComponentSurfaceDesignUIRegistration(
            /* @NonNull */
            final IExtension extension,
            /* @NonNull */
            final ComponentSurfaceDesignId id,
            /* @NonNull */
            final String name,
            /* @NonNull */
            final Icon icon )
        {
            assert extension != null;

            componentSurfaceDesignUI_ = new ComponentSurfaceDesignUI( id, name, icon );
            extensionNamespaceId_ = extension.getNamespaceIdentifier();
            extensionSimpleId_ = extension.getSimpleIdentifier();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the component surface design user interface contributed by the
         * extension.
         * 
         * @return The component surface design user interface contributed by
         *         the extension; never {@code null}.
         */
        /* @NonNull */
        ComponentSurfaceDesignUI getComponentSurfaceDesignUI()
        {
            return componentSurfaceDesignUI_;
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
