/*
 * ConnectServerCommandletAsCommandletTest.java
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
 * Created on Dec 29, 2008 at 9:07:55 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.connection;

import java.util.Arrays;
import java.util.Collections;
import org.gamegineer.client.ui.console.commandlet.AbstractCommandletTestCase;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlets.connection.ConnectServerCommandlet}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandlet} interface.
 */
public final class ConnectServerCommandletAsCommandletTest
    extends AbstractCommandletTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ConnectServerCommandletAsCommandletTest} class.
     */
    public ConnectServerCommandletAsCommandletTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.commandlet.AbstractCommandletTestCase#createCommandlet()
     */
    @Override
    protected ICommandlet createCommandlet()
    {
        return new ConnectServerCommandlet();
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed an
     * argument list with three elements.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_ArgCount_Three()
        throws Exception
    {
        getCommandlet().execute( createCommandletContext( Arrays.asList( "1", "2", "3" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed an
     * empty argument list.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_ArgCount_Zero()
        throws Exception
    {
        getCommandlet().execute( createCommandletContext( Collections.<String>emptyList() ) );
    }

    /**
     * Ensures the {@code execute} method throws an exception when the local
     * connection descriptor does not specify the game server identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_ConnectionDescriptor_Local_NoServerId()
        throws Exception
    {
        final String connectionString = String.format( "%1$s", ConnectServerCommandletFacade.CONNECTION_TYPE_LOCAL() ); //$NON-NLS-1$

        getCommandlet().execute( createCommandletContext( Collections.singletonList( connectionString ) ) );
    }

    /**
     * Ensures the {@code execute} method throws an exception when the local
     * connection descriptor specifies a game server that has not been
     * registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_ConnectionDescriptor_Local_ServerUnregistered()
        throws Exception
    {
        final String connectionString = String.format( "%1$s:%2$s=unknown-id", ConnectServerCommandletFacade.CONNECTION_TYPE_LOCAL(), ConnectServerCommandletFacade.CONNECTION_PROPERTY_LOCAL_ID() ); //$NON-NLS-1$

        getCommandlet().execute( createCommandletContext( Collections.singletonList( connectionString ) ) );
    }
}
