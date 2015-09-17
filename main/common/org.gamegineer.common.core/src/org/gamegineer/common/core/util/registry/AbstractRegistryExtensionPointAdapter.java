/*
 * AbstractRegistryExtensionPointAdapter.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Nov 17, 2012 at 9:08:10 PM.
 */

package org.gamegineer.common.core.util.registry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
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
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.internal.core.Loggers;

/**
 * A component that adapts objects published via an extension point to a service
 * that implements {@link IRegistry}.
 * 
 * @param <ObjectIdType>
 *        The type of object used to identify an object managed by the registry.
 * @param <ObjectType>
 *        The type of object managed by the registry.
 */
@ThreadSafe
public abstract class AbstractRegistryExtensionPointAdapter<ObjectIdType, ObjectType>
    implements IRegistryEventListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension registry service. */
    @GuardedBy( "lock_" )
    @Nullable
    private IExtensionRegistry extensionRegistry_;

    /** The instance lock. */
    private final Object lock_;

    /**
     * The collection of object registrations contributed from the extension
     * registry.
     */
    @GuardedBy( "lock_" )
    @Nullable
    private Collection<ObjectRegistration<ObjectType>> objectRegistrations_;

    /** The object registry service. */
    @GuardedBy( "lock_" )
    @Nullable
    private IRegistry<ObjectIdType, ObjectType> objectRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractRegistryExtensionPointAdapter} class.
     */
    protected AbstractRegistryExtensionPointAdapter()
    {
        extensionRegistry_ = null;
        lock_ = new Object();
        objectRegistrations_ = new ArrayList<>();
        objectRegistry_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Activates this component.
     */
    public final void activate()
    {
        synchronized( lock_ )
        {
            final IExtensionRegistry extensionRegistry = extensionRegistry_;
            assert extensionRegistry != null;

            registerObjects();
            extensionRegistry.addListener( this, getExtensionPointId() );
        }
    }

    /*
     * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
     */
    @Override
    public final void added(
        final IExtension @Nullable[] extensions )
    {
        assert extensions != null;

        for( final IExtension extension : extensions )
        {
            for( final IConfigurationElement configurationElement : extension.getConfigurationElements() )
            {
                registerObject( configurationElement );
            }
        }
    }

    /*
     * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
     */
    @Override
    public final void added(
        @SuppressWarnings( "unused" )
        final IExtensionPoint @Nullable[] extensionPoints )
    {
        // do nothing
    }

    /**
     * Binds the extension registry service to this component.
     * 
     * @param extensionRegistry
     *        The extension registry service; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the extension registry is already bound.
     */
    public final void bindExtensionRegistry(
        final IExtensionRegistry extensionRegistry )
    {
        synchronized( lock_ )
        {
            assertStateLegal( extensionRegistry_ == null, NonNlsMessages.AbstractRegistryExtensionPointAdapter_bindExtensionRegistry_bound );
            extensionRegistry_ = extensionRegistry;
        }
    }

    /**
     * Binds the object registry service to this component.
     * 
     * @param objectRegistry
     *        The object registry service; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the object registry is already bound.
     */
    public final void bindObjectRegistry(
        final IRegistry<ObjectIdType, ObjectType> objectRegistry )
    {
        synchronized( lock_ )
        {
            assertStateLegal( objectRegistry_ == null, NonNlsMessages.AbstractRegistryExtensionPointAdapter_bindObjectRegistry_bound );
            objectRegistry_ = objectRegistry;
        }
    }

    /**
     * Creates a new object from the specified configuration element.
     * 
     * @param configurationElement
     *        The configuration element; must not be {@code null}.
     * 
     * @return A new object; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal object.
     */
    protected abstract ObjectType createObject(
        IConfigurationElement configurationElement );

    /**
     * Creates an object registration based on the specified extension
     * configuration element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     * 
     * @return An object registration; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal object.
     */
    private ObjectRegistration<ObjectType> createObjectRegistration(
        final IConfigurationElement configurationElement )
    {
        return new ObjectRegistration<>( configurationElement.getDeclaringExtension(), createObject( configurationElement ) );
    }

    /**
     * Deactivates this component.
     */
    public final void deactivate()
    {
        synchronized( lock_ )
        {
            final IExtensionRegistry extensionRegistry = extensionRegistry_;
            assert extensionRegistry != null;

            extensionRegistry.removeListener( this );
            unregisterObjects();
        }
    }

    /**
     * Gets the identifier of the extension point associated with this adapter.
     * 
     * @return The identifier of the extension point associated with this
     *         adapter; never {@code null}.
     */
    protected abstract String getExtensionPointId();

    /**
     * Indicates the specified object registration was contributed by the
     * specified extension.
     * 
     * @param objectRegistration
     *        The object registration; must not be {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return {@code true} if the specified object registration was contributed
     *         by the specified extension; otherwise {@code false}.
     */
    private boolean isObjectRegistrationContributedByExtension(
        final ObjectRegistration<ObjectType> objectRegistration,
        final IExtension extension )
    {
        if( !objectRegistration.getExtensionNamespaceId().equals( extension.getNamespaceIdentifier() ) )
        {
            return false;
        }

        final String extensionSimpleId = objectRegistration.getExtensionSimpleId();
        return (extensionSimpleId == null) || extensionSimpleId.equals( extension.getSimpleIdentifier() );
    }

    /**
     * Registers all objects in the extension registry.
     */
    @GuardedBy( "lock_" )
    private void registerObjects()
    {
        assert Thread.holdsLock( lock_ );

        final IExtensionRegistry extensionRegistry = extensionRegistry_;
        assert extensionRegistry != null;

        for( final IConfigurationElement configurationElement : extensionRegistry.getConfigurationElementsFor( getExtensionPointId() ) )
        {
            registerObject( configurationElement );
        }
    }

    /**
     * Registers the object represented by the specified extension configuration
     * element.
     * 
     * @param configurationElement
     *        The extension configuration element; must not be {@code null}.
     */
    private void registerObject(
        final IConfigurationElement configurationElement )
    {
        final ObjectRegistration<ObjectType> objectRegistration;
        try
        {
            objectRegistration = createObjectRegistration( configurationElement );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractRegistryExtensionPointAdapter_registerObject_parseError( configurationElement ), e );
            return;
        }

        synchronized( lock_ )
        {
            final Collection<ObjectRegistration<ObjectType>> objectRegistrations = objectRegistrations_;
            assert objectRegistrations != null;
            final IRegistry<ObjectIdType, ObjectType> objectRegistry = objectRegistry_;
            assert objectRegistry != null;

            objectRegistry.registerObject( objectRegistration.getObject() );
            objectRegistrations.add( objectRegistration );
        }
    }

    /*
     * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
     */
    @Override
    public final void removed(
        final IExtension @Nullable[] extensions )
    {
        assert extensions != null;

        for( final IExtension extension : extensions )
        {
            assert extension != null;
            unregisterObjects( extension );
        }
    }

    /*
     * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtensionPoint[])
     */
    @Override
    public final void removed(
        @SuppressWarnings( "unused" )
        final IExtensionPoint @Nullable[] extensionPoints )
    {
        // do nothing
    }

    /**
     * Unbinds the extension registry service from this component.
     * 
     * @param extensionRegistry
     *        The extension registry service; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code extensionRegistry} is not currently bound.
     */
    public final void unbindExtensionRegistry(
        final IExtensionRegistry extensionRegistry )
    {
        synchronized( lock_ )
        {
            assertArgumentLegal( extensionRegistry_ == extensionRegistry, "extensionRegistry", NonNlsMessages.AbstractRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound ); //$NON-NLS-1$
            extensionRegistry_ = null;
        }
    }

    /**
     * Unbinds the object registry service from this component.
     * 
     * @param objectRegistry
     *        The object registry service; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code objectRegistry} is not currently bound.
     */
    public final void unbindObjectRegistry(
        final IRegistry<ObjectIdType, ObjectType> objectRegistry )
    {
        synchronized( lock_ )
        {
            assertArgumentLegal( objectRegistry_ == objectRegistry, "objectRegistry", NonNlsMessages.AbstractRegistryExtensionPointAdapter_unbindObjectRegistry_notBound ); //$NON-NLS-1$
            objectRegistry_ = null;
        }
    }

    /**
     * Unregisters all objects.
     */
    @GuardedBy( "lock_" )
    private void unregisterObjects()
    {
        assert Thread.holdsLock( lock_ );

        final Collection<ObjectRegistration<ObjectType>> objectRegistrations = objectRegistrations_;
        assert objectRegistrations != null;
        final IRegistry<ObjectIdType, ObjectType> objectRegistry = objectRegistry_;
        assert objectRegistry != null;

        for( final ObjectRegistration<ObjectType> objectRegistration : objectRegistrations )
        {
            objectRegistry.unregisterObject( objectRegistration.getObject() );
        }

        objectRegistrations.clear();
    }

    /**
     * Unregisters all objects contributed by the specified extension.
     * 
     * @param extension
     *        The extension; must not be {@code null}.
     */
    private void unregisterObjects(
        final IExtension extension )
    {
        synchronized( lock_ )
        {
            final Collection<ObjectRegistration<ObjectType>> objectRegistrations = objectRegistrations_;
            assert objectRegistrations != null;
            final IRegistry<ObjectIdType, ObjectType> objectRegistry = objectRegistry_;
            assert objectRegistry != null;

            for( final Iterator<ObjectRegistration<ObjectType>> iterator = objectRegistrations.iterator(); iterator.hasNext(); )
            {
                final ObjectRegistration<ObjectType> objectRegistration = iterator.next();
                if( isObjectRegistrationContributedByExtension( objectRegistration, extension ) )
                {
                    objectRegistry.unregisterObject( objectRegistration.getObject() );
                    iterator.remove();
                }
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Describes an object that was registered from an extension.
     * 
     * @param <ObjectType>
     *        The type of object managed by the registry.
     */
    @Immutable
    private static final class ObjectRegistration<ObjectType>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The namespace identifier of the contributing extension. */
        private final String extensionNamespaceId_;

        /** The simple identifier of the contributing extension. */
        @Nullable
        private final String extensionSimpleId_;

        /** The object contributed by the extension. */
        private final ObjectType object_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ObjectRegistration} class.
         * 
         * @param extension
         *        The extension that contributed the component strategy; must
         *        not be {@code null}.
         * @param object
         *        The object contributed by the extension; must not be
         *        {@code null}.
         */
        ObjectRegistration(
            final IExtension extension,
            final ObjectType object )
        {
            extensionNamespaceId_ = extension.getNamespaceIdentifier();
            extensionSimpleId_ = extension.getSimpleIdentifier();
            object_ = object;
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
        @Nullable
        String getExtensionSimpleId()
        {
            return extensionSimpleId_;
        }

        /**
         * Gets the object contributed by the extension.
         * 
         * @return The object contributed by the extension; never {@code null}.
         */
        ObjectType getObject()
        {
            return object_;
        }
    }
}
