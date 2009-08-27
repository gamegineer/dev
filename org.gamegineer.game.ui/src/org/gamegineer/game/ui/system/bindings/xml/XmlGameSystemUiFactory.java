/*
 * XmlGameSystemUiFactory.java
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
 * Created on Feb 26, 2009 at 11:10:48 PM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.game.internal.ui.Loggers;
import org.gamegineer.game.internal.ui.system.bindings.xml.LocalizedStringReplacer;
import org.gamegineer.game.internal.ui.system.bindings.xml.StringBundleFactory;
import org.gamegineer.game.internal.ui.system.bindings.xml.XmlGameSystemUi;
import org.gamegineer.game.ui.system.GameSystemUiException;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.xml.sax.SAXException;

/**
 * A factory for creating game system user interfaces from XML sources.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class XmlGameSystemUiFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystemUiFactory} class.
     */
    private XmlGameSystemUiFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a game system user interface from an XML document obtained from
     * the specified file.
     * 
     * <p>
     * This method supports associating a string bundle with the game system
     * user interface using one or more standard Java properties files in the
     * same directory as the XML file. Each properties file encapsulates the
     * localized string entries for a single locale. This method will use the
     * following files to build the string bundle:
     * </p>
     * 
     * <ul>
     * <li>&lt;<i>file-name</i>&gt;_&lt;<i>language</i>&gt;_&lt;<i>country</i>&gt;_&lt;<i>variant</i>&gt;.properties</li>
     * <li>&lt;<i>file-name</i>&gt;_&lt;<i>language</i>&gt;_&lt;<i>country</i>&gt;.properties</li>
     * <li>&lt;<i>file-name</i>&gt;_&lt;<i>language</i>&gt;.properties</li>
     * <li>&lt;<i>file-name</i>&gt;.properties</li>
     * </ul>
     * 
     * <p>
     * where <i>file-name</i> represents the absolute path of the XML file
     * without the extension. If none of these files exist, no exception will be
     * thrown and no locale-neutral keys in the XML file will be replaced.
     * </p>
     * 
     * @param file
     *        The file from which the XML document will be read; must not be
     *        {@code null}.
     * 
     * @return A game system user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code file} is {@code null}.
     * @throws org.gamegineer.game.ui.system.GameSystemUiException
     *         If an error occurs while creating the game system user interface.
     */
    /* @NonNull */
    public static IGameSystemUi createGameSystemUi(
        /* @NonNull */
        final File file )
        throws GameSystemUiException
    {
        assertArgumentNotNull( file, "file" ); //$NON-NLS-1$

        return createGameSystemUi( createReader( file ), createStringBundle( file ) );
    }

    /**
     * Creates a game system user interface from an XML document obtained from
     * the specified reader.
     * 
     * @param reader
     *        The reader from which the XML document will be read; must not be
     *        {@code null}.
     * @param bundle
     *        The string bundle used to replace locale-neutral keys in the XML
     *        document with their corresponding localized strings; must not be
     *        {@code null}.
     * 
     * @return A game system user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code reader} or {@code bundle} is {@code null}.
     * @throws org.gamegineer.game.ui.system.GameSystemUiException
     *         If an error occurs while creating the game system user interface.
     */
    /* @NonNull */
    public static IGameSystemUi createGameSystemUi(
        /* @NonNull */
        final Reader reader,
        /* @NonNull */
        final IStringBundle bundle )
        throws GameSystemUiException
    {
        assertArgumentNotNull( reader, "reader" ); //$NON-NLS-1$
        assertArgumentNotNull( bundle, "bundle" ); //$NON-NLS-1$

        try
        {
            final Unmarshaller unmarshaller = createUnmarshaller( bundle );
            final XmlGameSystemUi xmlGameSystemUI = (XmlGameSystemUi)unmarshaller.unmarshal( reader );
            return xmlGameSystemUI.toGameSystemUi();
        }
        catch( final JAXBException e )
        {
            throw new GameSystemUiException( Messages.XmlGameSystemUiFactory_createGameSystemUiFromReader_creationError, e );
        }
    }

    /**
     * Creates a game system user interface from an XML document obtained from
     * the specified URL.
     * 
     * <p>
     * This method supports associating a string bundle with the game system
     * user interface using one or more standard Java properties files in the
     * same location as the XML file. Each properties file encapsulates the
     * localized string entries for a single locale. This method will use the
     * following URLs to build the string bundle:
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
     * where <i>url</i> represents the URL of the XML file without the file
     * name extension. If none of these files exist, no exception will be thrown
     * and no locale-neutral keys in the XML file will be replaced.
     * </p>
     * 
     * @param url
     *        The URL from which the XML document will be read; must not be
     *        {@code null}.
     * 
     * @return A game system user interface; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code url} includes a reference or a query string.
     * @throws java.lang.NullPointerException
     *         If {@code url} is {@code null}.
     * @throws org.gamegineer.game.ui.system.GameSystemUiException
     *         If an error occurs while creating the game system user interface.
     */
    /* @NonNull */
    public static IGameSystemUi createGameSystemUi(
        /* @NonNull */
        final URL url )
        throws GameSystemUiException
    {
        assertArgumentNotNull( url, "url" ); //$NON-NLS-1$
        assertArgumentLegal( url.getRef() == null, "url", Messages.XmlGameSystemUiFactory_createGameSystemUiFromUrl_url_hasReference ); //$NON-NLS-1$
        assertArgumentLegal( url.getQuery() == null, "url", Messages.XmlGameSystemUiFactory_createGameSystemUiFromUrl_url_hasQueryString ); //$NON-NLS-1$

        return createGameSystemUi( createReader( url ), createStringBundle( url ) );
    }

    /**
     * Creates a reader for the specified game system user interface file.
     * 
     * @param gameSystemUiFile
     *        The game system user interface file; must not be {@code null}.
     * 
     * @return A reader for the specified game system user interface file; never
     *         {@code null}.
     * 
     * @throws org.gamegineer.game.ui.system.GameSystemUiException
     *         If an error occurs while creating the reader including if the
     *         game system user interface file does not exist.
     */
    /* @NonNull */
    private static Reader createReader(
        /* @NonNull */
        final File gameSystemUiFile )
        throws GameSystemUiException
    {
        assert gameSystemUiFile != null;

        try
        {
            return new FileReader( gameSystemUiFile );
        }
        catch( final FileNotFoundException e )
        {
            throw new GameSystemUiException( Messages.XmlGameSystemUiFactory_createReaderFromFile_fileNotFound, e );
        }
    }

    /**
     * Creates a reader for the specified game system user interface URL.
     * 
     * @param gameSystemUiUrl
     *        The game system user interface URL; must not be {@code null}.
     * 
     * @return A reader for the specified game system user interface URL; never
     *         {@code null}.
     * 
     * @throws org.gamegineer.game.ui.system.GameSystemUiException
     *         If an error occurs while creating the reader including if the
     *         game system user interface URL does not exist.
     */
    /* @NonNull */
    private static Reader createReader(
        /* @NonNull */
        final URL gameSystemUiUrl )
        throws GameSystemUiException
    {
        assert gameSystemUiUrl != null;

        try
        {
            return new InputStreamReader( gameSystemUiUrl.openStream() );
        }
        catch( final IOException e )
        {
            throw new GameSystemUiException( Messages.XmlGameSystemUiFactory_createReaderFromUrl_urlNotFound, e );
        }
    }

    /**
     * Creates a string bundle associated with the specified game system user
     * interface file.
     * 
     * @param gameSystemUiFile
     *        The game system user interface file; must not be {@code null}.
     * 
     * @return A string bundle; never {@code null}.
     */
    /* @NonNull */
    private static IStringBundle createStringBundle(
        /* @NonNull */
        final File gameSystemUiFile )
    {
        assert gameSystemUiFile != null;

        final StringBundleFactory.IStringBundleResolver resolver = new StringBundleFactory.IStringBundleResolver()
        {
            public Map<String, String> getStringBundleEntries(
                final Locale locale )
            {
                assertArgumentNotNull( locale, "locale" ); //$NON-NLS-1$

                final Map<String, String> entries = new HashMap<String, String>();

                final File stringBundleFile = getStringBundleFile( gameSystemUiFile, locale );
                final Reader reader;
                try
                {
                    reader = new FileReader( stringBundleFile );
                    try
                    {
                        final Properties props = new Properties();
                        props.load( reader );
                        for( final String key : props.stringPropertyNames() )
                        {
                            entries.put( key, props.getProperty( key ) );
                        }
                    }
                    catch( final IOException e )
                    {
                        Loggers.SYSTEM.log( Level.SEVERE, Messages.XmlGameSystemUiFactory_createStringBundleFromFile_readFileError, e );
                    }
                    finally
                    {
                        try
                        {
                            reader.close();
                        }
                        catch( final IOException e )
                        {
                            Loggers.SYSTEM.log( Level.SEVERE, Messages.XmlGameSystemUiFactory_createStringBundleFromFile_closeFileError, e );
                        }
                    }
                }
                catch( final FileNotFoundException e )
                {
                    Loggers.SYSTEM.fine( Messages.XmlGameSystemUiFactory_createStringBundleFromFile_fileNotFound( stringBundleFile ) );
                }

                return entries;
            }
        };
        return StringBundleFactory.createStringBundle( resolver, Locale.getDefault() );
    }

    /**
     * Creates a string bundle associated with the specified game system user
     * interface URL.
     * 
     * @param gameSystemUiUrl
     *        The game system user interface URL; must not be {@code null}.
     * 
     * @return A string bundle; never {@code null}.
     */
    /* @NonNull */
    private static IStringBundle createStringBundle(
        /* @NonNull */
        final URL gameSystemUiUrl )
    {
        assert gameSystemUiUrl != null;

        final StringBundleFactory.IStringBundleResolver resolver = new StringBundleFactory.IStringBundleResolver()
        {
            public Map<String, String> getStringBundleEntries(
                final Locale locale )
            {
                assertArgumentNotNull( locale, "locale" ); //$NON-NLS-1$

                final Map<String, String> entries = new HashMap<String, String>();

                try
                {
                    final URL stringBundleUrl = getStringBundleUrl( gameSystemUiUrl, locale );
                    final Reader reader;
                    try
                    {
                        reader = new InputStreamReader( stringBundleUrl.openStream() );
                        try
                        {
                            final Properties props = new Properties();
                            props.load( reader );
                            for( final String key : props.stringPropertyNames() )
                            {
                                entries.put( key, props.getProperty( key ) );
                            }
                        }
                        catch( final IOException e )
                        {
                            Loggers.SYSTEM.log( Level.SEVERE, Messages.XmlGameSystemUiFactory_createStringBundleFromUrl_readStreamError, e );
                        }
                        finally
                        {
                            try
                            {
                                reader.close();
                            }
                            catch( final IOException e )
                            {
                                Loggers.SYSTEM.log( Level.SEVERE, Messages.XmlGameSystemUiFactory_createStringBundleFromUrl_closeStreamError, e );
                            }
                        }
                    }
                    catch( final IOException e )
                    {
                        Loggers.SYSTEM.fine( Messages.XmlGameSystemUiFactory_createStringBundleFromUrl_urlNotFound( stringBundleUrl ) );
                    }
                }
                catch( final MalformedURLException e )
                {
                    Loggers.SYSTEM.log( Level.SEVERE, Messages.XmlGameSystemUiFactory_createStringBundleFromUrl_malformedUrl, e );
                }

                return entries;
            }
        };
        return StringBundleFactory.createStringBundle( resolver, Locale.getDefault() );
    }

    /**
     * Creates a game system user interface unmarshaller.
     * 
     * @param bundle
     *        The string bundle used to replace locale-neutral keys in the
     *        unmarshalled object; must not be {@code null}.
     * 
     * @return A game system user interface unmarshaller; never {@code null}.
     * 
     * @throws javax.xml.bind.JAXBException
     *         If an error occurs.
     */
    /* @NonNull */
    private static Unmarshaller createUnmarshaller(
        /* @NonNull */
        final IStringBundle bundle )
        throws JAXBException
    {
        assert bundle != null;

        final JAXBContext context = JAXBContext.newInstance( XmlGameSystemUi.class );

        final StringWriter schemaWriter = new StringWriter();
        try
        {
            final SchemaOutputResolver outputResolver = new SchemaOutputResolver()
            {
                @Override
                public Result createOutput(
                    @SuppressWarnings( "unused" )
                    final String namespaceUri,
                    @SuppressWarnings( "unused" )
                    final String suggestedFileName )
                {
                    final StreamResult result = new StreamResult( schemaWriter );
                    result.setSystemId( "gameSystemUi.xsd" ); //$NON-NLS-1$
                    return result;
                }
            };
            context.generateSchema( outputResolver );
        }
        catch( final IOException e )
        {
            throw new JAXBException( Messages.XmlGameSystemUiFactory_createUnmarshaller_generateSchemaError, e );
        }

        final Schema schema;
        try
        {
            final SchemaFactory schemaFactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
            schema = schemaFactory.newSchema( new StreamSource( new StringReader( schemaWriter.toString() ) ) );
        }
        catch( final SAXException e )
        {
            throw new JAXBException( Messages.XmlGameSystemUiFactory_createUnmarshaller_parseSchemaError, e );
        }

        final Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema( schema );
        unmarshaller.setListener( new LocalizedStringReplacer( bundle ) );
        return unmarshaller;
    }

    /**
     * Gets the file representing the string bundle for the specified locale
     * associated with the specified game system user interface file.
     * 
     * @param gameSystemUiFile
     *        The game system user interface file; must not be {@code null}.
     * @param locale
     *        The locale; must not be {@code null}.
     * 
     * @return The file representing the string bundle for the specified locale
     *         associated with the specified game system user interface file;
     *         must not be {@code null}.
     */
    /* @NonNull */
    static File getStringBundleFile(
        /* @NonNull */
        final File gameSystemUiFile,
        /* @NonNull */
        final Locale locale )
    {
        assert gameSystemUiFile != null;
        assert locale != null;

        // Strip existing extension
        final String stringBundleFilePath;
        if( gameSystemUiFile.getName().lastIndexOf( '.' ) != -1 )
        {
            final int lastDotIndex = gameSystemUiFile.getAbsolutePath().lastIndexOf( '.' );
            assert lastDotIndex != -1;
            stringBundleFilePath = gameSystemUiFile.getAbsolutePath().substring( 0, lastDotIndex );
        }
        else
        {
            stringBundleFilePath = gameSystemUiFile.getAbsolutePath();
        }

        // Append locale name and correct extension
        final StringBuilder sb = new StringBuilder( stringBundleFilePath );
        if( !locale.getLanguage().isEmpty() )
        {
            sb.append( '_' );
            sb.append( locale.getLanguage() );

            if( !locale.getCountry().isEmpty() )
            {
                sb.append( '_' );
                sb.append( locale.getCountry() );

                if( !locale.getVariant().isEmpty() )
                {
                    sb.append( '_' );
                    sb.append( locale.getVariant() );
                }
            }
        }
        sb.append( ".properties" ); //$NON-NLS-1$

        return new File( sb.toString() );
    }

    /**
     * Gets the URL representing the string bundle for the specified locale
     * associated with the specified game system user interface URL.
     * 
     * @param gameSystemUiUrl
     *        The game system user interface URL; must not be {@code null}.
     * @param locale
     *        The locale; must not be {@code null}.
     * 
     * @return The URL representing the string bundle for the specified locale
     *         associated with the specified game system user interface URL;
     *         must not be {@code null}.
     * 
     * @throws java.net.MalformedURLException
     *         If an error occurs creating the string bundle URL.
     */
    /* @NonNull */
    static URL getStringBundleUrl(
        /* @NonNull */
        final URL gameSystemUiUrl,
        /* @NonNull */
        final Locale locale )
        throws MalformedURLException
    {
        assert gameSystemUiUrl != null;
        assert locale != null;

        // Strip existing extension
        final String stringBundleUrlPath;
        final String gameSystemUiUrlPath = gameSystemUiUrl.getPath();
        final int lastDotIndex = gameSystemUiUrlPath.lastIndexOf( '.' );
        final int lastSlashIndex = gameSystemUiUrlPath.lastIndexOf( '/' );
        if( (lastDotIndex != -1) && (lastDotIndex > lastSlashIndex) )
        {
            stringBundleUrlPath = gameSystemUiUrlPath.substring( 0, lastDotIndex );
        }
        else
        {
            stringBundleUrlPath = gameSystemUiUrlPath;
        }

        // Append locale name and correct extension
        final StringBuilder sb = new StringBuilder( stringBundleUrlPath );
        if( !locale.getLanguage().isEmpty() )
        {
            sb.append( '_' );
            sb.append( locale.getLanguage() );

            if( !locale.getCountry().isEmpty() )
            {
                sb.append( '_' );
                sb.append( locale.getCountry() );

                if( !locale.getVariant().isEmpty() )
                {
                    sb.append( '_' );
                    sb.append( locale.getVariant() );
                }
            }
        }
        sb.append( ".properties" ); //$NON-NLS-1$

        return new URL( gameSystemUiUrl.getProtocol(), gameSystemUiUrl.getHost(), sb.toString() );
    }
}
