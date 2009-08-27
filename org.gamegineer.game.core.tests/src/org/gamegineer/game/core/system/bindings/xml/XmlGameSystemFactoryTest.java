/*
 * XmlGameSystemFactoryTest.java
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
 * Created on Nov 9, 2008 at 9:43:38 PM.
 */

package org.gamegineer.game.core.system.bindings.xml;

import static org.gamegineer.game.core.system.Assert.assertGameSystemEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import org.gamegineer.game.core.system.GameSystemException;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.NonValidatingGameSystemBuilder;
import org.gamegineer.game.internal.core.Activator;
import org.gamegineer.test.core.TestCases;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.core.system.bindings.xml.XmlGameSystemFactory}
 * class.
 */
public final class XmlGameSystemFactoryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The test case temporary directory. */
    private static final File TEMPORARY_DIRECTORY = TestCases.getTemporaryDirectory( Activator.getDefault().getBundleContext().getBundle(), XmlGameSystemFactoryTest.class );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystemFactoryTest} class.
     */
    public XmlGameSystemFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an instance of {@code IGameSystemFactoryMethod} for the
     * {@code createGameSystem(File)} factory method.
     * 
     * @return An instance of {@code IGameSystemFactoryMethod} for the
     *         {@code createGameSystem(File)} factory method; never {@code null}.
     */
    /* @NonNull */
    private static IGameSystemFactoryMethod createGameSystemFromFileFactoryMethod()
    {
        return new IGameSystemFactoryMethod()
        {
            @SuppressWarnings( "synthetic-access" )
            public IGameSystem createGameSystem(
                final IGameSystem gameSystem )
                throws Exception
            {
                final File gameSystemFile = File.createTempFile( "test", ".xml", TEMPORARY_DIRECTORY ); //$NON-NLS-1$ //$NON-NLS-2$
                final Writer gameSystemWriter = new FileWriter( gameSystemFile );
                try
                {
                    gameSystemWriter.write( XmlGameSystems.toXml( gameSystem ) );
                }
                finally
                {
                    gameSystemWriter.close();
                }

                return XmlGameSystemFactory.createGameSystem( gameSystemFile );
            }
        };
    }

    /**
     * Creates an instance of {@code IGameSystemFactoryMethod} for the
     * {@code createGameSystem(Reader)} factory method.
     * 
     * @return An instance of {@code IGameSystemFactoryMethod} for the
     *         {@code createGameSystem(Reader)} factory method; never
     *         {@code null}.
     */
    /* @NonNull */
    private static IGameSystemFactoryMethod createGameSystemFromReaderFactoryMethod()
    {
        return new IGameSystemFactoryMethod()
        {
            public IGameSystem createGameSystem(
                final IGameSystem gameSystem )
                throws Exception
            {
                final Reader reader = new StringReader( XmlGameSystems.toXml( gameSystem ) );
                return XmlGameSystemFactory.createGameSystem( reader );
            }
        };
    }

    /**
     * Ensures the {@code createGameSystem(File)} method throws an exception
     * when passed a {@code null} file.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystemFromFile_File_Null()
        throws Exception
    {
        final File file = null;

        XmlGameSystemFactory.createGameSystem( file );
    }

    /**
     * Ensures the {@code createGameSystem(File)} method throws an exception
     * when it unmarshals a malformed game system.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = GameSystemException.class )
    public void testCreateGameSystemFromFile_GameSystem_Malformed()
        throws Exception
    {
        testGameSystemFactoryMethod_GameSystem_Malformed( createGameSystemFromFileFactoryMethod() );
    }

    /**
     * Ensures the {@code createGameSystem(File)} method correctly unmarshals a
     * well-formed game system.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameSystemFromFile_GameSystem_WellFormed()
        throws Exception
    {
        testGameSystemFactoryMethod_GameSystem_WellFormed( createGameSystemFromFileFactoryMethod() );
    }

    /**
     * Ensures the {@code createGameSystem(File)} method throws an exception
     * when passed a game system file that does not exist.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameSystemFromFile_GameSystemFile_NotExists()
        throws Exception
    {
        final File file = File.createTempFile( "test", ".xml", TEMPORARY_DIRECTORY ); //$NON-NLS-1$ //$NON-NLS-2$
        assertTrue( file.delete() );

        try
        {
            XmlGameSystemFactory.createGameSystem( file );
            fail( "Expected exception to be thrown." ); //$NON-NLS-1$
        }
        catch( final GameSystemException e )
        {
            if( !(e.getCause() instanceof FileNotFoundException) )
            {
                fail( "Expected exception cause to be FileNotFoundException." ); //$NON-NLS-1$
            }
        }
    }

    /**
     * Ensures the {@code createGameSystem(File)} method does not throw an
     * exception when the associated string bundle file does not exist.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameSystemFromFile_StringBundleFile_NotExists()
        throws Exception
    {
        final File gameSystemFile = File.createTempFile( "test", ".xml", TEMPORARY_DIRECTORY ); //$NON-NLS-1$ //$NON-NLS-2$
        final Writer writer = new FileWriter( gameSystemFile );
        try
        {
            writer.write( XmlGameSystems.toXml( GameSystems.createUniqueGameSystem() ) );
        }
        finally
        {
            writer.close();
        }

        XmlGameSystemFactory.createGameSystem( gameSystemFile );
    }

    /**
     * Ensures the {@code createGameSystem(Reader)} method throws an exception
     * when it unmarshals a malformed game system.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = GameSystemException.class )
    public void testCreateGameSystemFromReader_GameSystem_Malformed()
        throws Exception
    {
        testGameSystemFactoryMethod_GameSystem_Malformed( createGameSystemFromReaderFactoryMethod() );
    }

    /**
     * Ensures the {@code createGameSystem(Reader)} method correctly unmarshals
     * a well-formed game system.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameSystemFromReader_GameSystem_WellFormed()
        throws Exception
    {
        testGameSystemFactoryMethod_GameSystem_WellFormed( createGameSystemFromReaderFactoryMethod() );
    }

    /**
     * Ensures the {@code createGameSystem(Reader)} method throws an exception
     * when passed a {@code null} reader.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystemFromReader_Reader_Null()
        throws Exception
    {
        final Reader reader = null;

        XmlGameSystemFactory.createGameSystem( reader );
    }

    /**
     * A parameterized test method to ensure a game system factory method throws
     * an exception when it unmarshals a malformed game system.
     * 
     * @param method
     *        The game system factory method; must not be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    private void testGameSystemFactoryMethod_GameSystem_Malformed(
        /* @NonNull */
        final IGameSystemFactoryMethod method )
        throws Exception
    {
        assertNotNull( method );

        final NonValidatingGameSystemBuilder builder = new NonValidatingGameSystemBuilder();
        final IGameSystem gameSystem = builder.toGameSystem();

        method.createGameSystem( gameSystem );
    }

    /**
     * A parameterized test method to ensure a game system factory method
     * correctly unmarshals a well-formed game system.
     * 
     * @param method
     *        The game system factory method; must not be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    private void testGameSystemFactoryMethod_GameSystem_WellFormed(
        /* @NonNull */
        final IGameSystemFactoryMethod method )
        throws Exception
    {
        assertNotNull( method );

        final IGameSystem expectedGameSystem = GameSystems.createUniqueGameSystem();

        final IGameSystem actualGameSystem = method.createGameSystem( expectedGameSystem );

        assertGameSystemEquals( expectedGameSystem, actualGameSystem );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * An abstraction of a factory method of the {@code XmlGameSystemFactory}
     * class.
     */
    private interface IGameSystemFactoryMethod
    {
        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Creates a game system from the specified game system using the
         * strategy implemented by the factory method.
         * 
         * @param gameSystem
         *        The game system; must not be {@code null}.
         * 
         * @return A game system; never {@code null}.
         * 
         * @throws java.lang.Exception
         *         If the game system cannot be created.
         * @throws java.lang.NullPointerException
         *         If {@code gameSystem} is {@code null}.
         */
        /* @NonNull */
        public IGameSystem createGameSystem(
            /* @NonNull */
            final IGameSystem gameSystem )
            throws Exception;
    }
}
