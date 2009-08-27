/*
 * XmlGameSystemUiExtensionFactory.java
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
 * Created on Mar 4, 2009 at 12:02:42 AM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
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
import org.gamegineer.game.internal.ui.Activator;
import org.gamegineer.game.internal.ui.Services;
import org.gamegineer.game.ui.system.GameSystemUiException;
import org.osgi.framework.Bundle;

/**
 * A factory for creating game system user interfaces from XML sources via the
 * extension registry.
 * 
 * <p>
 * This class may be specified as the value of {@code class} attribute of the
 * {@code org.gamegineer.game.ui.gameSystemUis} extension point. It accepts a
 * single parameter specifying the bundle path of the XML file that defines the
 * game system user interface. This parameter can be set using the ":" separator
 * approach or as a named XML parameter. For example,
 * </p>
 * 
 * <p>
 * {@code class="org.gamegineer.game.ui.system.xml.XmlGameSystemUiExtensionFactory:/dir/gameSystemUi.xml"}
 * </p>
 * 
 * <p>
 * or
 * </p>
 * 
 * <p>
 * {@code <parameter name="path" value="/dir/gameSystemUi.xml" />}
 * </p>
 * 
 * <p>
 * This class supports associating a string bundle with the game system user
 * interface using one or more standard Java properties files in the same
 * location as the XML file. Each properties file encapsulates the localized
 * string entries for a single locale. This method will use the following URLs
 * to build the string bundle:
 * </p>
 * 
 * <ul>
 * <li>&lt;<i>url</i>&gt;_&lt;<i>language</i>&gt;_&lt;<i>country</i>&gt;_&lt;<i>variant</i>&gt;.properties</li>
 * <li>&lt;<i>url</i>&gt;_&lt;<i>language</i>&gt;_&lt;<i>country</i>&gt;.properties</li>
 * <li>&lt;<i>url</i>&gt;_&lt;<i>language</i>&gt;.properties</li>
 * <li>&lt;<i>url</i>&gt;.properties</li>
 * </ul>
 * 
 * <p>
 * where <i>url</i> represents the URL of the XML file without the file name
 * extension. If none of these files exist, no exception will be thrown and no
 * locale-neutral keys in the XML file will be replaced.
 * </p>
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class XmlGameSystemUiExtensionFactory
    implements IExecutableExtension, IExecutableExtensionFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute specifying the bundle path of the game system file. */
    private static final String ATTR_PATH = "path"; //$NON-NLS-1$

    /** The bundle URL of the game system user interface file. */
    private URL m_gameSystemUiUrl;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystemUiExtensionFactory}
     * class.
     */
    public XmlGameSystemUiExtensionFactory()
    {
        m_gameSystemUiUrl = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.core.runtime.IExecutableExtensionFactory#create()
     */
    public Object create()
        throws CoreException
    {
        if( m_gameSystemUiUrl == null )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.XmlGameSystemUiExtensionFactory_create_urlNotSet ) );
        }

        try
        {
            return XmlGameSystemUiFactory.createGameSystemUi( m_gameSystemUiUrl );
        }
        catch( final GameSystemUiException e )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.XmlGameSystemUiExtensionFactory_create_creationError, e ) );
        }
    }

    /**
     * Parses the specified extension point data to extract the bundle path of
     * the game system user interface file.
     * 
     * @param data
     *        The extension point data; may be {@code null}.
     * 
     * @return The bundle path of the game system user interface file or
     *         {@code null} if the data could not be parsed.
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
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.XmlGameSystemUiExtensionFactory_setInitializationData_pathNotSet ) );
        }

        final Bundle[] bundles = Services.getDefault().getPackageAdministrationService().getBundles( config.getNamespaceIdentifier(), null );
        if( (bundles == null) || (bundles.length == 0) )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.XmlGameSystemUiExtensionFactory_setInitializationData_bundleNotFound( config.getNamespaceIdentifier() ) ) );
        }

        m_gameSystemUiUrl = FileLocator.find( bundles[ 0 ], new Path( path ), null );
        if( m_gameSystemUiUrl == null )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.XmlGameSystemUiExtensionFactory_setInitializationData_fileNotFound( bundles[ 0 ], path ) ) );
        }
    }
}
