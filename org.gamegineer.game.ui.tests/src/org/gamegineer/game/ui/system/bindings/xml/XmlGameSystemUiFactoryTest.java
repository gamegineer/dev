/*
 * XmlGameSystemUiFactoryTest.java
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
 * Created on Feb 28, 2009 at 9:30:57 PM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import static org.gamegineer.game.ui.system.Assert.assertGameSystemUiEquals;
import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.util.Collections;
import java.util.Locale;
import java.util.Properties;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.internal.ui.Activator;
import org.gamegineer.game.ui.system.GameSystemUiBuilder;
import org.gamegineer.game.ui.system.GameSystemUiException;
import org.gamegineer.game.ui.system.GameSystemUis;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.gamegineer.game.ui.system.NonValidatingGameSystemUiBuilder;
import org.gamegineer.test.core.TestCases;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.ui.system.bindings.xml.XmlGameSystemUiFactory}
 * class.
 */
public final class XmlGameSystemUiFactoryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The test case temporary directory. */
    private static final File TEMPORARY_DIRECTORY = TestCases.getTemporaryDirectory( Activator.getDefault().getBundleContext().getBundle(), XmlGameSystemUiFactoryTest.class );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystemUiFactoryTest}
     * class.
     */
    public XmlGameSystemUiFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an instance of {@code IGameSystemUiFactoryMethod} for the
     * {@code createGameSystemUi(File)} factory method.
     * 
     * @return An instance of {@code IGameSystemUiFactoryMethod} for the
     *         {@code createGameSystemUi(File)} factory method; never
     *         {@code null}.
     */
    /* @NonNull */
    private static IGameSystemUiFactoryMethod createGameSystemUiFromFileFactoryMethod()
    {
        return new IGameSystemUiFactoryMethod()
        {
            @SuppressWarnings( "synthetic-access" )
            public IGameSystemUi createLocalizedGameSystemUi(
                final IGameSystemUi localeNeutralGameSystemUi,
                final IStringBundle bundle )
                throws Exception
            {
                // Write the game system user interface file
                final File gameSystemUiFile = File.createTempFile( "test", ".xml", TEMPORARY_DIRECTORY ); //$NON-NLS-1$ //$NON-NLS-2$
                final Writer gameSystemUiWriter = new FileWriter( gameSystemUiFile );
                try
                {
                    gameSystemUiWriter.write( XmlGameSystemUis.toXml( localeNeutralGameSystemUi ) );
                }
                finally
                {
                    gameSystemUiWriter.close();
                }

                // Write the associated string bundle file
                final File stringBundleFile = XmlGameSystemUiFactory.getStringBundleFile( gameSystemUiFile, Locale.ROOT );
                final Writer stringBundleWriter = new FileWriter( stringBundleFile );
                try
                {
                    final Properties props = new Properties();
                    for( final String key : bundle.getKeys() )
                    {
                        props.put( key, bundle.getString( key ) );
                    }
                    props.store( stringBundleWriter, null );
                }
                finally
                {
                    stringBundleWriter.close();
                }

                return XmlGameSystemUiFactory.createGameSystemUi( gameSystemUiFile );
            }
        };
    }

    /**
     * Creates an instance of {@code IGameSystemUiFactoryMethod} for the
     * {@code createGameSystemUi(Reader, IStringBundle)} factory method.
     * 
     * @return An instance of {@code IGameSystemUiFactoryMethod} for the
     *         {@code createGameSystemUi(Reader, IStringBundle)} factory method;
     *         never {@code null}.
     */
    /* @NonNull */
    private static IGameSystemUiFactoryMethod createGameSystemUiFromReaderFactoryMethod()
    {
        return new IGameSystemUiFactoryMethod()
        {
            public IGameSystemUi createLocalizedGameSystemUi(
                final IGameSystemUi localeNeutralGameSystemUi,
                final IStringBundle bundle )
                throws Exception
            {
                final Reader reader = new StringReader( XmlGameSystemUis.toXml( localeNeutralGameSystemUi ) );
                return XmlGameSystemUiFactory.createGameSystemUi( reader, bundle );
            }
        };
    }

    /**
     * Creates an instance of {@code IGameSystemUiFactoryMethod} for the
     * {@code createGameSystemUi(URL)} factory method.
     * 
     * @return An instance of {@code IGameSystemUiFactoryMethod} for the
     *         {@code createGameSystemUi(URL)} factory method; never
     *         {@code null}.
     */
    /* @NonNull */
    private static IGameSystemUiFactoryMethod createGameSystemUiFromUrlFactoryMethod()
    {
        return new IGameSystemUiFactoryMethod()
        {
            @SuppressWarnings( "synthetic-access" )
            public IGameSystemUi createLocalizedGameSystemUi(
                final IGameSystemUi localeNeutralGameSystemUi,
                final IStringBundle bundle )
                throws Exception
            {
                // Write the game system user interface file
                final File gameSystemUiFile = File.createTempFile( "test", ".xml", TEMPORARY_DIRECTORY ); //$NON-NLS-1$ //$NON-NLS-2$
                final Writer gameSystemUiWriter = new FileWriter( gameSystemUiFile );
                try
                {
                    gameSystemUiWriter.write( XmlGameSystemUis.toXml( localeNeutralGameSystemUi ) );
                }
                finally
                {
                    gameSystemUiWriter.close();
                }

                // Write the associated string bundle file
                final File stringBundleFile = XmlGameSystemUiFactory.getStringBundleFile( gameSystemUiFile, Locale.ROOT );
                final Writer stringBundleWriter = new FileWriter( stringBundleFile );
                try
                {
                    final Properties props = new Properties();
                    for( final String key : bundle.getKeys() )
                    {
                        props.put( key, bundle.getString( key ) );
                    }
                    props.store( stringBundleWriter, null );
                }
                finally
                {
                    stringBundleWriter.close();
                }

                return XmlGameSystemUiFactory.createGameSystemUi( gameSystemUiFile.toURI().toURL() );
            }
        };
    }

    /**
     * Ensures the {@code createGameSystemUi(File)} method throws an exception
     * when passed a {@code null} file.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystemUiFromFile_File_Null()
        throws Exception
    {
        final File file = null;

        XmlGameSystemUiFactory.createGameSystemUi( file );
    }

    /**
     * Ensures the {@code createGameSystemUi(File)} method throws an exception
     * when it unmarshals a malformed game system user interface.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = GameSystemUiException.class )
    public void testCreateGameSystemUiFromFile_GameSystemUi_Malformed()
        throws Exception
    {
        testGameSystemUiFactoryMethod_GameSystemUi_Malformed( createGameSystemUiFromFileFactoryMethod() );
    }

    /**
     * Ensures the {@code createGameSystemUi(File)} method correctly unmarshals
     * a well-formed game system user interface.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameSystemUiFromFile_GameSystemUi_WellFormed()
        throws Exception
    {
        testGameSystemUiFactoryMethod_GameSystemUi_WellFormed( createGameSystemUiFromFileFactoryMethod() );
    }

    /**
     * Ensures the {@code createGameSystemUi(File)} method throws an exception
     * when passed a game system user interface file that does not exist.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameSystemUiFromFile_GameSystemUiFile_NotExists()
        throws Exception
    {
        final File file = File.createTempFile( "test", ".xml", TEMPORARY_DIRECTORY ); //$NON-NLS-1$ //$NON-NLS-2$
        assertTrue( file.delete() );

        try
        {
            XmlGameSystemUiFactory.createGameSystemUi( file );
            fail( "Expected exception to be thrown." ); //$NON-NLS-1$
        }
        catch( final GameSystemUiException e )
        {
            if( !(e.getCause() instanceof FileNotFoundException) )
            {
                fail( "Expected exception cause to be FileNotFoundException." ); //$NON-NLS-1$
            }
        }
    }

    /**
     * Ensures the {@code createGameSystemUi(File)} method does not throw an
     * exception when the associated string bundle file does not exist.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameSystemUiFromFile_StringBundleFile_NotExists()
        throws Exception
    {
        final File file = File.createTempFile( "test", ".xml", TEMPORARY_DIRECTORY ); //$NON-NLS-1$ //$NON-NLS-2$
        final Writer writer = new FileWriter( file );
        try
        {
            writer.write( XmlGameSystemUis.toXml( GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() ) ) );
        }
        finally
        {
            writer.close();
        }

        XmlGameSystemUiFactory.createGameSystemUi( file );
    }

    /**
     * Ensures the {@code createGameSystemUi(Reader, IStringBundle)} method
     * throws an exception when passed a {@code null} string bundle.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystemUiFromReader_Bundle_Null()
        throws Exception
    {
        XmlGameSystemUiFactory.createGameSystemUi( new StringReader( "" ), null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createGameSystemUi(Reader, IStringBundle)} method
     * throws an exception when it unmarshals a malformed game system user
     * interface.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = GameSystemUiException.class )
    public void testCreateGameSystemUiFromReader_GameSystemUi_Malformed()
        throws Exception
    {
        testGameSystemUiFactoryMethod_GameSystemUi_Malformed( createGameSystemUiFromReaderFactoryMethod() );
    }

    /**
     * Ensures the {@code createGameSystemUi(Reader, IStringBundle)} method
     * correctly unmarshals a well-formed game system user interface.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameSystemUiFromReader_GameSystemUi_WellFormed()
        throws Exception
    {
        testGameSystemUiFactoryMethod_GameSystemUi_WellFormed( createGameSystemUiFromReaderFactoryMethod() );
    }

    /**
     * Ensures the {@code createGameSystemUi(Reader, IStringBundle)} method
     * throws an exception when passed a {@code null} reader.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystemUiFromReader_Reader_Null()
        throws Exception
    {
        XmlGameSystemUiFactory.createGameSystemUi( null, createDummy( IStringBundle.class ) );
    }

    /**
     * Ensures the {@code createGameSystemUi(URL)} method throws an exception
     * when it unmarshals a malformed game system user interface.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = GameSystemUiException.class )
    public void testCreateGameSystemUiFromUrl_GameSystemUi_Malformed()
        throws Exception
    {
        testGameSystemUiFactoryMethod_GameSystemUi_Malformed( createGameSystemUiFromUrlFactoryMethod() );
    }

    /**
     * Ensures the {@code createGameSystemUi(URL)} method correctly unmarshals a
     * well-formed game system user interface.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameSystemUiFromUrl_GameSystemUi_WellFormed()
        throws Exception
    {
        testGameSystemUiFactoryMethod_GameSystemUi_WellFormed( createGameSystemUiFromUrlFactoryMethod() );
    }

    /**
     * Ensures the {@code createGameSystemUi(URL)} method throws an exception
     * when passed a game system user interface URL that does not exist.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameSystemUiFromUrl_GameSystemUiUrl_NotExists()
        throws Exception
    {
        final File file = File.createTempFile( "test", ".xml", TEMPORARY_DIRECTORY ); //$NON-NLS-1$ //$NON-NLS-2$
        final URL url = file.toURI().toURL();
        assertTrue( file.delete() );

        try
        {
            XmlGameSystemUiFactory.createGameSystemUi( url );
            fail( "Expected exception to be thrown." ); //$NON-NLS-1$
        }
        catch( final GameSystemUiException e )
        {
            if( !(e.getCause() instanceof IOException) )
            {
                fail( "Expected exception cause to be IOException." ); //$NON-NLS-1$
            }
        }
    }

    /**
     * Ensures the {@code createGameSystemUi(URL)} method does not throw an
     * exception when the associated string bundle URL does not exist.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGameSystemUiFromUrl_StringBundleUrl_NotExists()
        throws Exception
    {
        final File file = File.createTempFile( "test", ".xml", TEMPORARY_DIRECTORY ); //$NON-NLS-1$ //$NON-NLS-2$
        final URL url = file.toURI().toURL();
        final Writer writer = new FileWriter( file );
        try
        {
            writer.write( XmlGameSystemUis.toXml( GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() ) ) );
        }
        finally
        {
            writer.close();
        }

        XmlGameSystemUiFactory.createGameSystemUi( url );
    }

    /**
     * Ensures the {@code createGameSystemUi(URL)} method throws an exception
     * when passed a URL that contains a query string.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateGameSystemUiFromUrl_Url_HasQueryString()
        throws Exception
    {
        final URL url = new URL( "http://host.domain.com/dir/file.ext?query=string" ); //$NON-NLS-1$

        XmlGameSystemUiFactory.createGameSystemUi( url );
    }

    /**
     * Ensures the {@code createGameSystemUi(URL)} method throws an exception
     * when passed a URL that contains a reference.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateGameSystemUiFromUrl_Url_HasReference()
        throws Exception
    {
        final URL url = new URL( "http://host.domain.com/dir/file.ext#reference" ); //$NON-NLS-1$

        XmlGameSystemUiFactory.createGameSystemUi( url );
    }

    /**
     * Ensures the {@code createGameSystemUi(URL)} method throws an exception
     * when passed a {@code null} URL.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystemUiFromUrl_Url_Null()
        throws Exception
    {
        final URL url = null;

        XmlGameSystemUiFactory.createGameSystemUi( url );
    }

    /**
     * A parameterized test method to ensure a game system user interface
     * factory method throws an exception when it unmarshals a malformed game
     * system user interface.
     * 
     * @param method
     *        The game system user interface factory method; must not be
     *        {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    private void testGameSystemUiFactoryMethod_GameSystemUi_Malformed(
        /* @NonNull */
        final IGameSystemUiFactoryMethod method )
        throws Exception
    {
        assertNotNull( method );

        final NonValidatingGameSystemUiBuilder builder = new NonValidatingGameSystemUiBuilder();
        final IGameSystemUi localeNeutralGameSystemUi = builder.toGameSystemUi();
        final IStringBundle bundle = new FakeStringBundle();

        method.createLocalizedGameSystemUi( localeNeutralGameSystemUi, bundle );
    }

    /**
     * A parameterized test method to ensure a game system user interface
     * factory method correctly unmarshals a well-formed game system user
     * interface.
     * 
     * @param method
     *        The game system user interface factory method; must not be
     *        {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    private void testGameSystemUiFactoryMethod_GameSystemUi_WellFormed(
        /* @NonNull */
        final IGameSystemUiFactoryMethod method )
        throws Exception
    {
        assertNotNull( method );

        final String GAME_SYSTEM_NAME_KEY = "gameSystemName"; //$NON-NLS-1$
        final String GAME_SYSTEM_NAME_VALUE = "the game system name"; //$NON-NLS-1$
        final GameSystemUiBuilder localeNeutralGameSystemUiBuilder = GameSystemUis.createGameSystemUiBuilder( GameSystems.createUniqueGameSystem() );
        localeNeutralGameSystemUiBuilder.setName( LocaleNeutralKey.decorateKey( GAME_SYSTEM_NAME_KEY ) );
        final IGameSystemUi localeNeutralGameSystemUi = localeNeutralGameSystemUiBuilder.toGameSystemUi();
        final IStringBundle bundle = new FakeStringBundle( Collections.singletonMap( GAME_SYSTEM_NAME_KEY, GAME_SYSTEM_NAME_VALUE ) );
        final IGameSystemUi expectedGameSystemUi = new LocalizedGameSystemUi( localeNeutralGameSystemUi, bundle );

        final IGameSystemUi actualGameSystemUi = method.createLocalizedGameSystemUi( localeNeutralGameSystemUi, bundle );

        assertTrue( LocaleNeutralKey.isDecoratedKey( localeNeutralGameSystemUi.getName() ) );
        assertFalse( LocaleNeutralKey.isDecoratedKey( expectedGameSystemUi.getName() ) );
        assertFalse( LocaleNeutralKey.isDecoratedKey( actualGameSystemUi.getName() ) );
        assertGameSystemUiEquals( expectedGameSystemUi, actualGameSystemUi );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * An abstraction of a factory method of the {@code XmlGameSystemUiFactory}
     * class.
     */
    private interface IGameSystemUiFactoryMethod
    {
        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Creates a localized game system user interface from the specified
         * locale-neutral game system user interface and string bundle using the
         * strategy implemented by the factory method.
         * 
         * @param localeNeutralGameSystemUi
         *        The locale-neutral game system user interface; must not be
         *        {@code null}.
         * @param bundle
         *        The string bundle; must not be {@code null}.
         * 
         * @return A localized game system user interface; never {@code null}.
         * 
         * @throws java.lang.Exception
         *         If the localized game system user interface cannot be
         *         created.
         * @throws java.lang.NullPointerException
         *         If {@code localeNeutralGameSystemUi} or {@code bundle} is
         *         {@code null}.
         */
        /* @NonNull */
        public IGameSystemUi createLocalizedGameSystemUi(
            /* @NonNull */
            final IGameSystemUi localeNeutralGameSystemUi,
            /* @NonNull */
            final IStringBundle bundle )
            throws Exception;
    }
}
