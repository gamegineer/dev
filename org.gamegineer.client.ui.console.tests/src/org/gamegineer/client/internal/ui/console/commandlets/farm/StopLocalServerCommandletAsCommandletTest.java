/*
 * StopLocalServerCommandletAsCommandletTest.java
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
 * Created on May 22, 2009 at 10:22:42 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.farm;

import java.util.Arrays;
import java.util.Collections;
import org.gamegineer.client.ui.console.commandlet.AbstractCommandletTestCase;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlets.farm.StopLocalServerCommandlet}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandlet} interface.
 */
public final class StopLocalServerCommandletAsCommandletTest
    extends AbstractCommandletTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code StopLocalServerCommandletAsCommandletTest} class.
     */
    public StopLocalServerCommandletAsCommandletTest()
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
        return new StopLocalServerCommandlet();
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed an
     * argument list with two elements.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_ArgCount_Two()
        throws Exception
    {
        getCommandlet().execute( createCommandletContext( Arrays.asList( "1", "2" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
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
     * Ensures the {@code execute} method throws an exception when the server
     * identifier is not registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_ServerId_Unregistered()
        throws Exception
    {
        final ICommandletContext context = createCommandletContext( Arrays.asList( "server-id" ) ); //$NON-NLS-1$

        getCommandlet().execute( context );
    }
}
