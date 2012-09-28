/*
 * ContainerLayoutRegistryExtensionPointAdapter.java
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
 * Created on Aug 9, 2012 at 8:30:54 PM.
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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerLayoutRegistry;

/**
 * A component that adapts container layouts published via the
 * {@code org.gamegineer.table.core.containerLayouts} extension point to the
 * container layout registry.
 */
@ThreadSafe
public final class ContainerLayoutRegistryExtensionPointAdapter
    implements IRegistryEventListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the container layout class
     * name.
     */
    private static final String ATTR_CLASS_NAME = "className"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the container layout
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The collection of container layout registrations contributed from the
     * extension registry.
     */
    @GuardedBy( "lock_" )
    private Collection<ContainerLayoutRegistration> containerLayoutRegistrations_;

    /** The container layout registry service. */
    @GuardedBy( "lock_" )
    private IContainerLayoutRegistry containerLayoutRegistry_;

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
     * {@code ContainerLayoutRegistryExtensionPointAdapter} class.
     */
    public ContainerLayoutRegistryExtensionPointAdapter()
    {
        containerLayoutRegistrations_ = new ArrayList<ContainerLayoutRegistration>();
        containerLayoutRegistry_ = null;
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
            registerContainerLayouts();
            extensionRegistry_.addListener( this, BundleConstants.CONTAINER_LAYOUTS_EXTENSION_POINT_UNIQUE_ID );
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
                registerContainerLayout( configurationElement );
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
     * Binds the container layout registry service to this component.
     * 
     * @param containerLayoutRegistry
     *        The container layout registry service; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the container layout registry is already bound.
     * @throws java.lang.NullPointerException
     *         If {@code containerLayoutRegistry} is {@code null}.
     */
    public void bindContainerLayoutRegistry(
        /* @NonNull */
        final IContainerLayoutRegistry containerLayoutRegistry )
    {
        assertArgumentNotNull( containerLayoutRegistry, "containerLayoutRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertStateLegal( containerLayoutRegistry_ == null, NonNlsMessages.ContainerLayoutRegistryExtensionPointAdapter_bindContainerLayoutRegistry_bound );
            containerLayoutRegistry_ = containerLayoutRegistry;
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
            assertStateLegal( extensionRegistry_ == null, NonNlsMessages.ContainerLayoutRegistryExtensionPointAdapter_bindExtensionRegistry_bound );
            extensionRegistry_ = extensionRegistry;
        }
    }

    /**
     * Creates a container layout registration based on the specified extension
     * configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     * 
     * @return A container layout registration; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal container
     *         layout.
     */
    /* @NonNull */
    private static ContainerLayoutRegistration createContainerLayoutRegistration(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ContainerLayoutRegistryExtensionPointAdapter_createContainerLayoutRegistration_missingId );
        }
        @SuppressWarnings( "unused" )
        final ContainerLayoutId id = ContainerLayoutId.fromString( idString );

        // TODO: should be using a proxy here so we don't eagerly load the associated plug-in

        final IContainerLayout containerLayout;
        try
        {
            containerLayout = (IContainerLayout)configurationElement.createExecutableExtension( ATTR_CLASS_NAME );
        }
        catch( final CoreException e )
        {
            throw new IllegalArgumentException( NonNlsMessages.ContainerLayoutRegistryExtensionPointAdapter_createContainerLayoutRegistration_createContainerLayoutError, e );
        }

        return new ContainerLayoutRegistration( configurationElement.getDeclaringExtension(), containerLayout );
    }

    /**
     * Deactivates this component.
     */
    public void deactivate()
    {
        synchronized( lock_ )
        {
            extensionRegistry_.removeListener( this );
            unregisterContainerLayouts();
        }
    }

    /**
     * Indicates the specified container layout registration was contributed by
     * the specified extension.
     * 
     * @param containerLayoutRegistration
     *        The container layout registration; must not be {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return {@code true} if the specified container layout registration was
     *         contributed by the specified extension; otherwise {@code false}.
     */
    private static boolean isContainerLayoutRegistrationContributedByExtension(
        /* @NonNull */
        final ContainerLayoutRegistration containerLayoutRegistration,
        /* @NonNull */
        final IExtension extension )
    {
        assert containerLayoutRegistration != null;
        assert extension != null;

        if( !containerLayoutRegistration.getExtensionNamespaceId().equals( extension.getNamespaceIdentifier() ) )
        {
            return false;
        }

        final String extensionSimpleId = containerLayoutRegistration.getExtensionSimpleId();
        return (extensionSimpleId == null) || extensionSimpleId.equals( extension.getSimpleIdentifier() );
    }

    /**
     * Registers the container layout represented by the specified extension
     * configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     */
    private void registerContainerLayout(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final ContainerLayoutRegistration containerLayoutRegistration;
        try
        {
            containerLayoutRegistration = createContainerLayoutRegistration( configurationElement );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ContainerLayoutRegistryExtensionPointAdapter_registerContainerLayout_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            return;
        }

        synchronized( lock_ )
        {
            containerLayoutRegistry_.registerContainerLayout( containerLayoutRegistration.getContainerLayout() );
            containerLayoutRegistrations_.add( containerLayoutRegistration );
        }
    }

    /**
     * Registers all container layouts in the extension registry.
     */
    @GuardedBy( "lock_" )
    private void registerContainerLayouts()
    {
        assert Thread.holdsLock( lock_ );

        for( final IConfigurationElement configurationElement : extensionRegistry_.getConfigurationElementsFor( BundleConstants.CONTAINER_LAYOUTS_EXTENSION_POINT_UNIQUE_ID ) )
        {
            registerContainerLayout( configurationElement );
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
            unregisterContainerLayouts( extension );
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
     * Unbinds the container layout registry service from this component.
     * 
     * @param containerLayoutRegistry
     *        The container layout registry service; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code containerLayoutRegistry} is not currently bound.
     * @throws java.lang.NullPointerException
     *         If {@code containerLayoutRegistry} is {@code null}.
     */
    public void unbindContainerLayoutRegistry(
        /* @NonNull */
        final IContainerLayoutRegistry containerLayoutRegistry )
    {
        assertArgumentNotNull( containerLayoutRegistry, "containerLayoutRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( containerLayoutRegistry_ == containerLayoutRegistry, "containerLayoutRegistry", NonNlsMessages.ContainerLayoutRegistryExtensionPointAdapter_unbindContainerLayoutRegistry_notBound ); //$NON-NLS-1$
            containerLayoutRegistry_ = null;
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
            assertArgumentLegal( extensionRegistry_ == extensionRegistry, "extensionRegistry", NonNlsMessages.ContainerLayoutRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound ); //$NON-NLS-1$
            extensionRegistry_ = null;
        }
    }

    /**
     * Unregisters all container layouts.
     */
    @GuardedBy( "lock_" )
    private void unregisterContainerLayouts()
    {
        assert Thread.holdsLock( lock_ );

        for( final ContainerLayoutRegistration containerLayoutRegistration : containerLayoutRegistrations_ )
        {
            containerLayoutRegistry_.unregisterContainerLayout( containerLayoutRegistration.getContainerLayout() );
        }

        containerLayoutRegistrations_.clear();
    }

    /**
     * Unregisters all container layouts contributed by the specified extension.
     * 
     * @param extension
     *        The extension; must not be {@code null}.
     */
    private void unregisterContainerLayouts(
        /* @NonNull */
        final IExtension extension )
    {
        assert extension != null;

        synchronized( lock_ )
        {
            for( final Iterator<ContainerLayoutRegistration> iterator = containerLayoutRegistrations_.iterator(); iterator.hasNext(); )
            {
                final ContainerLayoutRegistration containerLayoutRegistration = iterator.next();
                if( isContainerLayoutRegistrationContributedByExtension( containerLayoutRegistration, extension ) )
                {
                    containerLayoutRegistry_.unregisterContainerLayout( containerLayoutRegistration.getContainerLayout() );
                    iterator.remove();
                }
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Describes a container layout that was registered from an extension.
     */
    @Immutable
    private static final class ContainerLayoutRegistration
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The container layout contributed by the extension. */
        private final IContainerLayout containerLayout_;

        /** The namespace identifier of the contributing extension. */
        private final String extensionNamespaceId_;

        /** The simple identifier of the contributing extension. */
        private final String extensionSimpleId_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ContainerLayoutRegistration}
         * class.
         * 
         * @param extension
         *        The extension that contributed the container layout; must not
         *        be {@code null}.
         * @param containerLayout
         *        The container layout contributed by the extension; must not be
         *        {@code null}.
         */
        ContainerLayoutRegistration(
            /* @NonNull */
            final IExtension extension,
            /* @NonNull */
            final IContainerLayout containerLayout )
        {
            assert extension != null;
            assert containerLayout != null;

            containerLayout_ = containerLayout;
            extensionNamespaceId_ = extension.getNamespaceIdentifier();
            extensionSimpleId_ = extension.getSimpleIdentifier();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the container layout contributed by the extension.
         * 
         * @return The container layout contributed by the extension; never
         *         {@code null}.
         */
        /* @NonNull */
        IContainerLayout getContainerLayout()
        {
            return containerLayout_;
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
