/*
 * StartLocalServerCommandletAsCommandletTest.java
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
 * Created on May 16, 2009 at 10:50:27 PM.
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
 * {@link org.gamegineer.client.internal.ui.console.commandlets.farm.StartLocalServerCommandlet}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandlet} interface.
 */
public final class StartLocalServerCommandletAsCommandletTest
    extends AbstractCommandletTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code StartLocalServerCommandletAsCommandletTest} class.
     */
    public StartLocalServerCommandletAsCommandletTest()
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
        return new StartLocalServerCommandlet();
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed an
     * argument list with one element.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_ArgCount_One()
        throws Exception
    {
        getCommandlet().execute( createCommandletContext( Arrays.asList( "1" ) ) ); //$NON-NLS-1$
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
     * Ensures the {@code execute} method throws an exception when the server
     * identifier is already registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_ServerId_Registered()
        throws Exception
    {
        final ICommandletContext context = createCommandletContext( Arrays.asList( "server-id", "server-name" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        getCommandlet().execute( context );

        getCommandlet().execute( context );
    }
}
