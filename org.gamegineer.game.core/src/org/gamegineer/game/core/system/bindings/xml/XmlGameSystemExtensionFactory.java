/*
 * XmlGameSystemExtensionFactory.java
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
 * Created on Feb 18, 2009 at 10:57:15 PM.
 */

package org.gamegineer.game.core.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.gamegineer.game.core.system.GameSystemException;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.internal.core.Activator;
import org.gamegineer.game.internal.core.Services;
import org.osgi.framework.Bundle;

/**
 * A factory for creating game systems from XML sources via the extension
 * registry.
 * 
 * <p>
 * This class may be specified as the value of the {@code class} attribute of
 * the {@code org.gamegineer.game.core.gameSystems} extension point. It accepts
 * a single parameter specifying the bundle path of the XML file that defines
 * the game system. This parameter can be set using the ":" separator approach
 * or as a named XML parameter. For example,
 * </p>
 * 
 * <p>
 * {@code class="org.gamegineer.game.core.system.xml.XmlGameSystemExtensionFactory:/dir/gameSystem.xml"}
 * </p>
 * 
 * <p>
 * or
 * </p>
 * 
 * <p>
 * {@code <parameter name="path" value="/dir/gameSystem.xml" />}
 * </p>
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class XmlGameSystemExtensionFactory
    implements IExecutableExtension, IExecutableExtensionFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute specifying the bundle path of the game system file. */
    private static final String ATTR_PATH = "path"; //$NON-NLS-1$

    /** The bundle URL of the game system file. */
    private URL m_gameSystemUrl;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystemExtensionFactory}
     * class.
     */
    public XmlGameSystemExtensionFactory()
    {
        m_gameSystemUrl = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.core.runtime.IExecutableExtensionFactory#create()
     */
    public IGameSystem create()
        throws CoreException
    {
        if( m_gameSystemUrl == null )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.XmlGameSystemExtensionFactory_create_urlNotSet ) );
        }

        try
        {
            return XmlGameSystemFactory.createGameSystem( new InputStreamReader( m_gameSystemUrl.openStream() ) );
        }
        catch( final IOException e )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.XmlGameSystemExtensionFactory_create_openFileError, e ) );
        }
        catch( final GameSystemException e )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.XmlGameSystemExtensionFactory_create_creationError, e ) );
        }
    }

    /**
     * Parses the specified extension point data to extract the bundle path of
     * the game system file.
     * 
     * @param data
     *        The extension point data; may be {@code null}.
     * 
     * @return The bundle path of the game system file or {@code null} if the
     *         data could not be parsed.
     */
    /* @Nullable */
    private static String parsePath(
        /* @Nullable */
        final Object data )
    {
        if( data instanceof String )
        {
            return (String)data;
        }
        else if( data instanceof Map )
        {
            @SuppressWarnings( "unchecked" )
            final Map<String, String> map = (Map<String, String>)data;
            return map.get( ATTR_PATH );
        }

        return null;
    }

    /*
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
     */
    public void setInitializationData(
        final IConfigurationElement config,
        final String propertyName,
        final Object data )
        throws CoreException
    {
        assertArgumentNotNull( config, "config" ); //$NON-NLS-1$
        assertArgumentNotNull( propertyName, "propertyName" ); //$NON-NLS-1$

        final String path = parsePath( data );
        if( path == null )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.XmlGameSystemExtensionFactory_setInitializationData_pathNotSet ) );
        }

        final Bundle[] bundles = Services.getDefault().getPackageAdministrationService().getBundles( config.getNamespaceIdentifier(), null );
        if( (bundles == null) || (bundles.length == 0) )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.XmlGameSystemExtensionFactory_setInitializationData_bundleNotFound( config.getNamespaceIdentifier() ) ) );
        }

        m_gameSystemUrl = FileLocator.find( bundles[ 0 ], new Path( path ), null );
        if( m_gameSystemUrl == null )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.XmlGameSystemExtensionFactory_setInitializationData_fileNotFound( bundles[ 0 ], path ) ) );
        }
    }
}
