/*
 * AbstractJaxbTestCase.java
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
 * Created on Nov 8, 2008 at 11:01:45 PM.
 */

package org.gamegineer.game.internal.core.system.bindings.xml;

import static org.junit.Assert.assertNotNull;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.junit.After;
import org.junit.Before;

/**
 * A fixture for testing JAXB-enabled classes.
 */
public abstract class AbstractJaxbTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The unmarshaller for use in the fixture. */
    private Unmarshaller unmarshaller_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractJaxbTestCase} class.
     */
    protected AbstractJaxbTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the unmarshaller for use in the fixture.
     * 
     * @param rootElementType
     *        The type of the root element; must not be {@code null}.
     * 
     * @return An unmarshaller; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    private static Unmarshaller createUnmarshaller(
        /* @NonNull */
        final Class<?> rootElementType )
        throws Exception
    {
        assert rootElementType != null;

        final StringWriter schemaWriter = new StringWriter();
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
                result.setSystemId( "definition.xsd" ); //$NON-NLS-1$
                return result;
            }
        };
        final JAXBContext context = JAXBContext.newInstance( rootElementType );
        context.generateSchema( outputResolver );

        final SchemaFactory schemaFactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
        final Schema schema = schemaFactory.newSchema( new StreamSource( new StringReader( schemaWriter.toString() ) ) );
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema( schema );

        return unmarshaller;
    }

    /**
     * Gets the root element type of the document to be unmarshalled.
     * 
     * @return The root element type of the document to be unmarshalled; never
     *         {@code null}.
     */
    /* @NonNull */
    protected abstract Class<?> getRootElementType();

    /**
     * Gets the unmarshaller for use in the fixture.
     * 
     * @return The unmarshaller for use in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final Unmarshaller getUnmarshaller()
    {
        assertNotNull( unmarshaller_ );
        return unmarshaller_;
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        final Class<?> rootElementType = getRootElementType();
        assertNotNull( rootElementType );
        unmarshaller_ = createUnmarshaller( rootElementType );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        unmarshaller_ = null;
    }
}
