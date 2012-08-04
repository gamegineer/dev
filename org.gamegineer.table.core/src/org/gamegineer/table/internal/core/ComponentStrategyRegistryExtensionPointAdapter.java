/*
 * ComponentStrategyRegistryExtensionPointAdapter.java
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
 * Created on Aug 3, 2012 at 9:30:23 PM.
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
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IComponentStrategyRegistry;

/**
 * A component that adapts component strategies published via the
 * {@code org.gamegineer.table.core.componentStrategies} extension point to the
 * component strategy registry.
 */
@ThreadSafe
public final class ComponentStrategyRegistryExtensionPointAdapter
    implements IRegistryEventListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the component strategy
     * class name.
     */
    private static final String ATTR_CLASS_NAME = "className"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the component strategy
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The collection of component strategy registrations contributed from the
     * extension registry.
     */
    @GuardedBy( "lock_" )
    private Collection<ComponentStrategyRegistration> componentStrategyRegistrations_;

    /** The component strategy registry service. */
    @GuardedBy( "lock_" )
    private IComponentStrategyRegistry componentStrategyRegistry_;

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
     * {@code ComponentStrategyRegistryExtensionPointAdapter} class.
     */
    public ComponentStrategyRegistryExtensionPointAdapter()
    {
        componentStrategyRegistrations_ = new ArrayList<ComponentStrategyRegistration>();
        componentStrategyRegistry_ = null;
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
            registerComponentStrategies();
            extensionRegistry_.addListener( this, BundleConstants.COMPONENT_STRATEGIES_EXTENSION_POINT_UNIQUE_ID );
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
                registerComponentStrategy( configurationElement );
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
     * Binds the component strategy registry service to this component.
     * 
     * @param componentStrategyRegistry
     *        The component strategy registry service; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the component strategy registry is already bound.
     * @throws java.lang.NullPointerException
     *         If {@code componentStrategyRegistry} is {@code null}.
     */
    public void bindComponentStrategyRegistry(
        /* @NonNull */
        final IComponentStrategyRegistry componentStrategyRegistry )
    {
        assertArgumentNotNull( componentStrategyRegistry, "componentStrategyRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertStateLegal( componentStrategyRegistry_ == null, NonNlsMessages.ComponentStrategyRegistryExtensionPointAdapter_bindComponentStrategyRegistry_bound );
            componentStrategyRegistry_ = componentStrategyRegistry;
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
            assertStateLegal( extensionRegistry_ == null, NonNlsMessages.ComponentStrategyRegistryExtensionPointAdapter_bindExtensionRegistry_bound );
            extensionRegistry_ = extensionRegistry;
        }
    }

    /**
     * Creates a component strategy registration based on the specified
     * extension configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     * 
     * @return A component strategy registration; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal component
     *         strategy.
     */
    /* @NonNull */
    private static ComponentStrategyRegistration createComponentStrategyRegistration(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        @SuppressWarnings( "unused" )
        final ComponentStrategyId id = ComponentStrategyId.fromString( configurationElement.getAttribute( ATTR_ID ) );

        // TODO: should be using a proxy here so we don't eagerly load the associated plug-in

        final IComponentStrategy componentStrategy;
        try
        {
            componentStrategy = (IComponentStrategy)configurationElement.createExecutableExtension( ATTR_CLASS_NAME );
        }
        catch( final CoreException e )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentStrategyRegistryExtensionPointAdapter_createComponentStrategyRegistration_createComponentStrategyError, e );
        }

        return new ComponentStrategyRegistration( configurationElement.getDeclaringExtension(), componentStrategy );
    }

    /**
     * Deactivates this component.
     */
    public void deactivate()
    {
        synchronized( lock_ )
        {
            extensionRegistry_.removeListener( this );
            unregisterComponentStrategies();
        }
    }

    /**
     * Indicates the specified component strategy registration was contributed
     * by the specified extension.
     * 
     * @param componentStrategyRegistration
     *        The component strategy registration; must not be {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return {@code true} if the specified component strategy registration was
     *         contributed by the specified extension; otherwise {@code false}.
     */
    private static boolean isComponentStrategyRegistrationContributedByExtension(
        /* @NonNull */
        final ComponentStrategyRegistration componentStrategyRegistration,
        /* @NonNull */
        final IExtension extension )
    {
        assert componentStrategyRegistration != null;
        assert extension != null;

        if( !componentStrategyRegistration.getExtensionNamespaceId().equals( extension.getNamespaceIdentifier() ) )
        {
            return false;
        }

        final String extensionSimpleId = componentStrategyRegistration.getExtensionSimpleId();
        return (extensionSimpleId == null) || extensionSimpleId.equals( extension.getSimpleIdentifier() );
    }

    /**
     * Registers the component strategy represented by the specified extension
     * configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     */
    private void registerComponentStrategy(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final ComponentStrategyRegistration componentStrategyRegistration;
        try
        {
            componentStrategyRegistration = createComponentStrategyRegistration( configurationElement );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentStrategyRegistryExtensionPointAdapter_registerComponentStrategy_parseError( configurationElement.getAttribute( ATTR_ID ) ), e );
            return;
        }

        synchronized( lock_ )
        {
            componentStrategyRegistry_.registerComponentStrategy( componentStrategyRegistration.getComponentStrategy() );
            componentStrategyRegistrations_.add( componentStrategyRegistration );
        }
    }

    /**
     * Registers all component strategies in the extension registry.
     */
    @GuardedBy( "lock_" )
    private void registerComponentStrategies()
    {
        assert Thread.holdsLock( lock_ );

        for( final IConfigurationElement configurationElement : extensionRegistry_.getConfigurationElementsFor( BundleConstants.COMPONENT_STRATEGIES_EXTENSION_POINT_UNIQUE_ID ) )
        {
            registerComponentStrategy( configurationElement );
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
            unregisterComponentStrategies( extension );
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
     * Unbinds the component strategy registry service from this component.
     * 
     * @param componentStrategyRegistry
     *        The component strategy registry service; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code componentStrategyRegistry} is not currently bound.
     * @throws java.lang.NullPointerException
     *         If {@code componentStrategyRegistry} is {@code null}.
     */
    public void unbindComponentStrategyRegistry(
        /* @NonNull */
        final IComponentStrategyRegistry componentStrategyRegistry )
    {
        assertArgumentNotNull( componentStrategyRegistry, "componentStrategyRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( componentStrategyRegistry_ == componentStrategyRegistry, "componentStrategyRegistry", NonNlsMessages.ComponentStrategyRegistryExtensionPointAdapter_unbindComponentStrategyRegistry_notBound ); //$NON-NLS-1$
            componentStrategyRegistry_ = null;
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
            assertArgumentLegal( extensionRegistry_ == extensionRegistry, "extensionRegistry", NonNlsMessages.ComponentStrategyRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound ); //$NON-NLS-1$
            extensionRegistry_ = null;
        }
    }

    /**
     * Unregisters all component strategies.
     */
    @GuardedBy( "lock_" )
    private void unregisterComponentStrategies()
    {
        assert Thread.holdsLock( lock_ );

        for( final ComponentStrategyRegistration componentStrategyRegistration : componentStrategyRegistrations_ )
        {
            componentStrategyRegistry_.unregisterComponentStrategy( componentStrategyRegistration.getComponentStrategy() );
        }

        componentStrategyRegistrations_.clear();
    }

    /**
     * Unregisters all component strategies contributed by the specified
     * extension.
     * 
     * @param extension
     *        The extension; must not be {@code null}.
     */
    private void unregisterComponentStrategies(
        /* @NonNull */
        final IExtension extension )
    {
        assert extension != null;

        synchronized( lock_ )
        {
            for( final Iterator<ComponentStrategyRegistration> iterator = componentStrategyRegistrations_.iterator(); iterator.hasNext(); )
            {
                final ComponentStrategyRegistration componentStrategyRegistration = iterator.next();
                if( isComponentStrategyRegistrationContributedByExtension( componentStrategyRegistration, extension ) )
                {
                    componentStrategyRegistry_.unregisterComponentStrategy( componentStrategyRegistration.getComponentStrategy() );
                    iterator.remove();
                }
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Describes a component strategy that was registered from an extension.
     */
    @Immutable
    private static final class ComponentStrategyRegistration
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The component strategy contributed by the extension. */
        private final IComponentStrategy componentStrategy_;

        /** The namespace identifier of the contributing extension. */
        private final String extensionNamespaceId_;

        /** The simple identifier of the contributing extension. */
        private final String extensionSimpleId_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code ComponentStrategyRegistration} class.
         * 
         * @param extension
         *        The extension that contributed the component strategy; must
         *        not be {@code null}.
         * @param componentStrategy
         *        The component strategy contributed by the extension; must not
         *        be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code componentStrategy} is {@code null}.
         */
        ComponentStrategyRegistration(
            /* @NonNull */
            final IExtension extension,
            /* @NonNull */
            final IComponentStrategy componentStrategy )
        {
            assert extension != null;
            assert componentStrategy != null;

            componentStrategy_ = componentStrategy;
            extensionNamespaceId_ = extension.getNamespaceIdentifier();
            extensionSimpleId_ = extension.getSimpleIdentifier();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the component strategy contributed by the extension.
         * 
         * @return The component strategy contributed by the extension; never
         *         {@code null}.
         */
        /* @NonNull */
        IComponentStrategy getComponentStrategy()
        {
            return componentStrategy_;
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
