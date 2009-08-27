/*
 * ExtensionRegistryExtension.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Apr 19, 2008 at 10:12:14 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IExtension;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry;
import org.gamegineer.engine.core.extensions.extensionregistry.TooManyExtensionsException;
import org.gamegineer.engine.core.util.attribute.Attribute;
import org.gamegineer.engine.internal.core.Debug;
import org.gamegineer.engine.internal.core.Loggers;
import org.gamegineer.engine.internal.core.extensions.AbstractExtension;

/**
 * Implementation of the
 * {@link org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry}
 * extension.
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class ExtensionRegistryExtension
    extends AbstractExtension
    implements IExtensionRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension collection attribute. */
    private static final Attribute<Map<Class<?>, IExtension>> EXTENSIONS_ATTRIBUTE = new Attribute<Map<Class<?>, IExtension>>( Scope.ENGINE_CONTROL, "org.gamegineer.engine.internal.core.extensions.extensionregistry.extensions" ) //$NON-NLS-1$
    {
        @Override
        protected Map<Class<?>, IExtension> decorateValue(
            final Map<Class<?>, IExtension> value )
        {
            return Collections.unmodifiableMap( value );
        }
    };


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ExtensionRegistryExtension}
     * class.
     */
    public ExtensionRegistryExtension()
    {
        super( IExtensionRegistry.class );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry#getExtension(org.gamegineer.engine.core.IEngineContext,
     *      java.lang.Class)
     */
    public IExtension getExtension(
        final IEngineContext context,
        final Class<?> type )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$
        assertExtensionStarted();

        return EXTENSIONS_ATTRIBUTE.getValue( context.getState() ).get( type );
    }

    /**
     * Gets the extension registry from the specified engine state.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * 
     * @return The extension registry; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code state} does not contain an extension registry.
     * @throws java.lang.NullPointerException
     *         If {@code state} is {@code null}.
     */
    /* @NonNull */
    public static IExtensionRegistry getExtensionRegistry(
        /* @NonNull */
        final IState state )
    {
        assertArgumentNotNull( state, "state" ); //$NON-NLS-1$

        final IExtensionRegistry extensionRegistry = (IExtensionRegistry)EXTENSIONS_ATTRIBUTE.getValue( state ).get( IExtensionRegistry.class );
        assertArgumentLegal( extensionRegistry != null, "state", Messages.ExtensionRegistryExtension_getExtensionRegistry_noExtensionRegistry ); //$NON-NLS-1$
        return extensionRegistry;
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry#registerExtension(org.gamegineer.engine.core.IEngineContext,
     *      org.gamegineer.engine.core.IExtension)
     */
    public void registerExtension(
        final IEngineContext context,
        final IExtension extension )
        throws EngineException, TooManyExtensionsException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( extension, "extension" ); //$NON-NLS-1$
        assertExtensionStarted();

        final Map<Class<?>, IExtension> extensions = EXTENSIONS_ATTRIBUTE.getValue( context.getState() );
        if( extensions.containsKey( extension.getExtensionType() ) )
        {
            throw new TooManyExtensionsException();
        }

        final Map<Class<?>, IExtension> newExtensions = new HashMap<Class<?>, IExtension>( extensions );
        newExtensions.put( extension.getExtensionType(), extension );
        EXTENSIONS_ATTRIBUTE.setValue( context.getState(), newExtensions );

        try
        {
            extension.start( context );
        }
        catch( final EngineException e )
        {
            throw e;
        }
        catch( final Exception e )
        {
            throw new EngineException( Messages.ExtensionRegistryExtension_registerExtension_unexpectedException( extension.getExtensionType().getName() ), e );
        }

        if( Debug.DEFAULT )
        {
            Debug.trace( String.format( "Registered extension for type '%1$s'.", extension.getExtensionType().getName() ) ); //$NON-NLS-1$
        }
    }

    /*
     * @see org.gamegineer.engine.internal.core.extensions.AbstractExtension#start(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public void start(
        final IEngineContext context )
        throws EngineException
    {
        super.start( context );

        EXTENSIONS_ATTRIBUTE.add( context.getState(), Collections.<Class<?>, IExtension>singletonMap( IExtensionRegistry.class, this ) );
    }

    /*
     * @see org.gamegineer.engine.internal.core.extensions.AbstractExtension#stop(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public void stop(
        final IEngineContext context )
        throws EngineException
    {
        super.stop( context );

        EXTENSIONS_ATTRIBUTE.remove( context.getState() );
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry#unregisterExtension(org.gamegineer.engine.core.IEngineContext,
     *      org.gamegineer.engine.core.IExtension)
     */
    public void unregisterExtension(
        final IEngineContext context,
        final IExtension extension )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( extension, "extension" ); //$NON-NLS-1$
        assertExtensionStarted();

        final Map<Class<?>, IExtension> extensions = EXTENSIONS_ATTRIBUTE.getValue( context.getState() );
        final IExtension registeredExtension = extensions.get( extension.getExtensionType() );
        assertArgumentLegal( registeredExtension == extension, "extension", Messages.ExtensionRegistryExtension_unregisterExtension_extensionUnregistered ); //$NON-NLS-1$

        try
        {
            extension.stop( context );
        }
        catch( final EngineException e )
        {
            Loggers.DEFAULT.log( Level.SEVERE, Messages.ExtensionRegistryExtension_unregisterExtension_fail( extension.getExtensionType().getName() ), e );
        }
        catch( final Exception e )
        {
            Loggers.DEFAULT.log( Level.SEVERE, Messages.ExtensionRegistryExtension_unregisterExtension_unexpectedException( extension.getExtensionType().getName() ), e );
        }

        final Map<Class<?>, IExtension> newExtensions = new HashMap<Class<?>, IExtension>( extensions );
        newExtensions.remove( extension.getExtensionType() );
        EXTENSIONS_ATTRIBUTE.setValue( context.getState(), newExtensions );

        if( Debug.DEFAULT )
        {
            Debug.trace( String.format( "Unregistered extension for type '%1$s'.", extension.getExtensionType().getName() ) ); //$NON-NLS-1$
        }
    }
}
