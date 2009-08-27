/*
 * XmlGameSystemFactory.java
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
 * Created on Oct 29, 2008 at 11:18:57 PM.
 */

package org.gamegineer.game.core.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
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
import org.gamegineer.game.core.system.GameSystemException;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.internal.core.system.bindings.xml.XmlGameSystem;
import org.xml.sax.SAXException;

/**
 * A factory for creating game systems from XML sources.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class XmlGameSystemFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystemFactory} class.
     */
    private XmlGameSystemFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a game system from an XML document obtained from the specified
     * file.
     * 
     * @param file
     *        The file from which the XML document will be read; must not be
     *        {@code null}.
     * 
     * @return A game system; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code file} is {@code null}.
     * @throws org.gamegineer.game.core.system.GameSystemException
     *         If an error occurs while creating the game system.
     */
    /* @NonNull */
    public static IGameSystem createGameSystem(
        /* @NonNull */
        final File file )
        throws GameSystemException
    {
        assertArgumentNotNull( file, "file" ); //$NON-NLS-1$

        return createGameSystem( createReader( file ) );
    }

    /**
     * Creates a game system from an XML document obtained from the specified
     * reader.
     * 
     * @param reader
     *        The reader from which the XML document will be read; must not be
     *        {@code null}.
     * 
     * @return A game system; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code reader} is {@code null}.
     * @throws org.gamegineer.game.core.system.GameSystemException
     *         If an error occurs while creating the game system.
     */
    /* @NonNull */
    public static IGameSystem createGameSystem(
        /* @NonNull */
        final Reader reader )
        throws GameSystemException
    {
        assertArgumentNotNull( reader, "reader" ); //$NON-NLS-1$

        try
        {
            final Unmarshaller unmarshaller = createUnmarshaller();
            final XmlGameSystem xmlGameSystem = (XmlGameSystem)unmarshaller.unmarshal( reader );
            return xmlGameSystem.toGameSystem();
        }
        catch( final JAXBException e )
        {
            throw new GameSystemException( Messages.XmlGameSystemFactory_createGameSystem_creationError, e );
        }
    }

    /**
     * Creates a reader for the specified game system file.
     * 
     * @param gameSystemFile
     *        The game system file; must not be {@code null}.
     * 
     * @return A reader for the specified game system file; never {@code null}.
     * 
     * @throws org.gamegineer.game.core.system.GameSystemException
     *         If an error occurs while creating the reader including if the
     *         game system file does not exist.
     */
    /* @NonNull */
    private static Reader createReader(
        /* @NonNull */
        final File gameSystemFile )
        throws GameSystemException
    {
        assert gameSystemFile != null;

        try
        {
            return new FileReader( gameSystemFile );
        }
        catch( final FileNotFoundException e )
        {
            throw new GameSystemException( Messages.XmlGameSystemFactory_createReader_fileNotFound, e );
        }
    }

    /**
     * Creates a game system unmarshaller.
     * 
     * @return A game system unmarshaller; never {@code null}.
     * 
     * @throws javax.xml.bind.JAXBException
     *         If an error occurs.
     */
    /* @NonNull */
    private static Unmarshaller createUnmarshaller()
        throws JAXBException
    {
        final JAXBContext context = JAXBContext.newInstance( XmlGameSystem.class );

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
                    result.setSystemId( "gameSystem.xsd" ); //$NON-NLS-1$
                    return result;
                }
            };
            context.generateSchema( outputResolver );
        }
        catch( final IOException e )
        {
            throw new JAXBException( Messages.XmlGameSystemFactory_createUnmarshaller_generateSchemaError, e );
        }

        final Schema schema;
        try
        {
            final SchemaFactory schemaFactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
            schema = schemaFactory.newSchema( new StreamSource( new StringReader( schemaWriter.toString() ) ) );
        }
        catch( final SAXException e )
        {
            throw new JAXBException( Messages.XmlGameSystemFactory_createUnmarshaller_parseSchemaError, e );
        }

        final Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema( schema );
        return unmarshaller;
    }
}
