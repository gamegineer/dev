/*
 * GameClientFactoryTest.java
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
 * Created on Dec 27, 2008 at 9:43:39 PM.
 */

package org.gamegineer.client.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.gamegineer.client.core.config.Configurations;
import org.gamegineer.client.core.config.IGameClientConfiguration;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.core.GameClientFactory} class.
 */
public final class GameClientFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameClientFactoryTest} class.
     */
    public GameClientFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a legal game client configuration.
     * 
     * @return A legal game client configuration; never {@code null}.
     */
    /* @NonNull */
    private static IGameClientConfiguration createLegalGameClientConfiguration()
    {
        return Configurations.createGameClientConfiguration();
    }

    /**
     * Ensures the {@code createGameClient} method throws an exception when
     * passed an illegal game client configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Ignore( "Currently, there is no way to create an illegal game client configuration." )
    @Test( expected = GameClientConfigurationException.class )
    public void testCreateGameClient_GameClientConfig_Illegal()
        throws Exception
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createGameClient} method throws an exception if passed
     * a {@code null} game client configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameClient_GameClientConfig_Null()
        throws Exception
    {
        GameClientFactory.createGameClient( null );
    }

    /**
     * Ensures the {@code createGameClient} method does not return {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameClient_ReturnValue_NonNull()
        throws Exception
    {
        assertNotNull( GameClientFactory.createGameClient( createLegalGameClientConfiguration() ) );
    }
}
