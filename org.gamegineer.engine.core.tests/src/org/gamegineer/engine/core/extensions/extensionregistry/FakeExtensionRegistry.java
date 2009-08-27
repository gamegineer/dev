/*
 * FakeExtensionRegistry.java
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
 * Created on Jun 8, 2008 at 10:22:50 PM.
 */

package org.gamegineer.engine.core.extensions.extensionregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashMap;
import java.util.Map;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IExtension;

/**
 * Fake implementation of
 * {@link org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry}.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public class FakeExtensionRegistry
    implements IExtensionRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of registered extensions. */
    private final Map<Class<?>, IExtension> m_extensions;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeExtensionRegistry} class.
     */
    public FakeExtensionRegistry()
    {
        m_extensions = new HashMap<Class<?>, IExtension>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry#getExtension(org.gamegineer.engine.core.IEngineContext, java.lang.Class)
     */
    public IExtension getExtension(
        final IEngineContext context,
        final Class<?> type )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        return m_extensions.get( type );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry#registerExtension(org.gamegineer.engine.core.IEngineContext, org.gamegineer.engine.core.IExtension)
     */
    public void registerExtension(
        final IEngineContext context,
        final IExtension extension )
        throws EngineException, TooManyExtensionsException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( extension, "extension" ); //$NON-NLS-1$

        if( m_extensions.containsKey( extension.getExtensionType() ) )
        {
            throw new TooManyExtensionsException();
        }

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
            throw new EngineException( e );
        }

        m_extensions.put( extension.getExtensionType(), extension );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry#unregisterExtension(org.gamegineer.engine.core.IEngineContext, org.gamegineer.engine.core.IExtension)
     */
    public void unregisterExtension(
        final IEngineContext context,
        final IExtension extension )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( extension, "extension" ); //$NON-NLS-1$
        assertArgumentLegal( m_extensions.get( extension.getExtensionType() ) == extension, "extension" ); //$NON-NLS-1$

        m_extensions.remove( extension.getExtensionType() );

        try
        {
            extension.stop( context );
        }
        catch( final Exception e )
        {
            // Ignored
        }
    }
}
