/*
 * TicTacToeGameSystemExtensionFactory.java
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
 * Created on Aug 19, 2009 at 9:43:09 PM.
 */

package org.gamegineer.tictactoe.core.system;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.Status;
import org.gamegineer.game.core.system.GameSystemException;
import org.gamegineer.game.core.system.bindings.xml.XmlGameSystemFactory;
import org.gamegineer.tictactoe.internal.core.Activator;

// XXX: This class is a temporary workaround for a recently encountered class
// loading issue.
//
// Originally, I envisioned using the XmlGameSystemExtensionFactory class in
// the "org.gamegineer.game.core.gameSystems" extension point to create all
// game systems defined using the XML bindings.  However, Equinox does not
// move a bundle from the STARTING state to the ACTIVE state until a class
// from the bundle is loaded.  Even though the game system factory loads the
// XML file from the bundle, that action does not force the bundle to become
// active.  During processing of the XML file, an attempt is made to load
// classes from the game system bundle.  Because the bundle has not become
// active, the classes cannot be resolved and game system creation fails.
//
// My first solution to this problem was to have the XmlGameSystemExtensionFactory
// class force the bundle from which it loaded the XML file to become active.
// This worked nicely, but didn't smell right, as I felt I shouldn't be
// invoking Bundle.start() directly.
//
// Instead, I followed the rule of "do as the ints do" and conformed to the
// expectations of the Equinox bundle loading model.  I introduced a custom
// game system factory class located in the game system bundle and specified
// that factory class in the extension point.  This causes the bundle to
// become active which ensures all classes specified in the game system XML
// file are resolvable.  I don't like this solution because it basically
// renders XmlGameSystemExtensionFactory useless and forces each game system
// author to write a custom game system extension factory.  Also, the current
// implementation of the custom factory hard-codes the game system binding
// (XML) and the bundle-relative path of the XML file.  This will prevent an
// advanced end-user from possibly specifying a different binding and/or file
// in a declarative manner, thus forcing them to modify code.

/**
 * A factory for creating the Tic-Tac-Toe game system via the extension
 * registry.
 * 
 * <p>
 * This class may be specified as the value of the {@code class} attribute of
 * the {@code org.gamegineer.game.core.gameSystems} extension point.
 * </p>
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class TicTacToeGameSystemExtensionFactory
    implements IExecutableExtensionFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code TicTacToeGameSystemExtensionFactory} class.
     */
    public TicTacToeGameSystemExtensionFactory()
    {
        super();
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
        final String gameSystemPath = "/gameSystem.xml"; //$NON-NLS-1$
        final URL gameSystemUrl = Activator.getDefault().getBundleContext().getBundle().getEntry( gameSystemPath );
        if( gameSystemUrl == null )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.TicTacToeGameSystemExtensionFactory_create_fileNotFound( gameSystemPath ) ) );
        }

        try
        {
            return XmlGameSystemFactory.createGameSystem( new InputStreamReader( gameSystemUrl.openStream() ) );
        }
        catch( final IOException e )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.TicTacToeGameSystemExtensionFactory_create_openFileError, e ) );
        }
        catch( final GameSystemException e )
        {
            throw new CoreException( new Status( Status.ERROR, Activator.SYMBOLIC_NAME, Messages.TicTacToeGameSystemExtensionFactory_create_creationError, e ) );
        }
    }
}
