/*
 * XmlGameSystemUis.java
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
 * Created on Feb 28, 2009 at 10:04:41 PM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.StringWriter;
import java.util.List;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.game.internal.ui.system.bindings.xml.XmlGameSystemUi;
import org.gamegineer.game.internal.ui.system.bindings.xml.XmlRoleUi;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.gamegineer.game.ui.system.IRoleUi;
import org.gamegineer.game.ui.system.NonValidatingGameSystemUi;
import org.gamegineer.game.ui.system.NonValidatingRoleUi;

/**
 * A factory for creating various XML-based game system user interface types
 * suitable for testing.
 * 
 * <p>
 * This class can properly handle any of the non-validating user interface
 * implementations.
 * </p>
 * 
 * <p>
 * This class is thread-safe
 * </p>
 */
@ThreadSafe
public final class XmlGameSystemUis
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystemUis} class.
     */
    private XmlGameSystemUis()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Converts the specified game system user interface to a valid XML
     * document.
     * 
     * @param gameSystemUi
     *        The game system user interface; must not be {@code null}.
     * 
     * @return A string containing the XML representation of the specified game
     *         system user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemUi} is {@code null}.
     */
    /* @NonNull */
    public static String toXml(
        /* @NonNull */
        final IGameSystemUi gameSystemUi )
    {
        assertArgumentNotNull( gameSystemUi, "gameSystemUi" ); //$NON-NLS-1$

        final StringWriter stringWriter = new StringWriter();
        final XMLOutputFactory factory = XMLOutputFactory.newInstance();

        try
        {
            final XMLStreamWriter writer = factory.createXMLStreamWriter( stringWriter );
            writer.writeStartDocument();

            writeGameSystemUi( writer, gameSystemUi );

            writer.writeEndDocument();
            writer.flush();
        }
        catch( final XMLStreamException e )
        {
            throw new AssertionError( e );
        }

        return stringWriter.toString();
    }

    /**
     * Converts the specified role user interface to a valid XML document.
     * 
     * @param rootElementName
     *        The name of the document root element; must not be {@code null}.
     *        This parameter is required because a role user interface may not
     *        represent the root element of a document.
     * @param roleUi
     *        The role user interface; must not be {@code null}.
     * 
     * @return A string containing the XML representation of the specified role
     *         user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code rootElementName} or {@code roleUi} is {@code null}.
     */
    /* @NonNull */
    public static String toXml(
        /* @NonNull */
        final String rootElementName,
        /* @NonNull */
        final IRoleUi roleUi )
    {
        assertArgumentNotNull( rootElementName, "rootElementName" ); //$NON-NLS-1$
        assertArgumentNotNull( roleUi, "roleUi" ); //$NON-NLS-1$

        final StringWriter stringWriter = new StringWriter();
        final XMLOutputFactory factory = XMLOutputFactory.newInstance();

        try
        {
            final XMLStreamWriter writer = factory.createXMLStreamWriter( stringWriter );
            writer.writeStartDocument();

            writer.writeStartElement( rootElementName );

            writeRoleUi( writer, roleUi );

            writer.writeEndElement();

            writer.writeEndDocument();
            writer.flush();
        }
        catch( final XMLStreamException e )
        {
            throw new AssertionError( e );
        }

        return stringWriter.toString();
    }

    /**
     * Writes the specified game system user interface to the specified XML
     * writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param gameSystemUi
     *        The game system user interface; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeGameSystemUi(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final IGameSystemUi gameSystemUi )
        throws XMLStreamException
    {
        assert writer != null;
        assert gameSystemUi != null;

        if( gameSystemUi instanceof NonValidatingGameSystemUi )
        {
            writeNonValidatingGameSystemUi( writer, (NonValidatingGameSystemUi)gameSystemUi );
        }
        else
        {
            writeValidatingGameSystemUi( writer, gameSystemUi );
        }
    }

    /**
     * Writes the specified non-validating game system user interface to the
     * specified XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param gameSystemUi
     *        The non-validating game system user interface; must not be
     *        {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeNonValidatingGameSystemUi(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final NonValidatingGameSystemUi gameSystemUi )
        throws XMLStreamException
    {
        assert writer != null;
        assert gameSystemUi != null;

        writer.writeStartElement( XmlGameSystemUi.NAME_GAME_SYSTEM_UI );
        if( gameSystemUi.getId() != null )
        {
            writer.writeAttribute( XmlGameSystemUi.NAME_ID, gameSystemUi.getId() );
        }
        if( gameSystemUi.getName() != null )
        {
            writer.writeAttribute( XmlGameSystemUi.NAME_NAME, gameSystemUi.getName() );
        }

        final List<IRoleUi> roleUis = gameSystemUi.getRoles();
        if( (roleUis != null) && !roleUis.isEmpty() )
        {
            writer.writeStartElement( XmlGameSystemUi.NAME_ROLES );
            for( final IRoleUi roleUi : roleUis )
            {
                if( roleUi != null )
                {
                    writeRoleUi( writer, roleUi );
                }
            }
            writer.writeEndElement();
        }

        writer.writeEndElement();
    }

    /**
     * Writes the specified non-validating role user interface to the specified
     * XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param roleUi
     *        The non-validating role user interface; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeNonValidatingRoleUi(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final NonValidatingRoleUi roleUi )
        throws XMLStreamException
    {
        assert writer != null;
        assert roleUi != null;

        writer.writeStartElement( XmlRoleUi.NAME_ROLE );
        if( roleUi.getId() != null )
        {
            writer.writeAttribute( XmlRoleUi.NAME_ID, roleUi.getId() );
        }
        if( roleUi.getName() != null )
        {
            writer.writeAttribute( XmlRoleUi.NAME_NAME, roleUi.getName() );
        }
        writer.writeEndElement();
    }

    /**
     * Writes the specified role user interface to the specified XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param roleUi
     *        The role user interface; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeRoleUi(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final IRoleUi roleUi )
        throws XMLStreamException
    {
        assert writer != null;
        assert roleUi != null;

        if( roleUi instanceof NonValidatingRoleUi )
        {
            writeNonValidatingRoleUi( writer, (NonValidatingRoleUi)roleUi );
        }
        else
        {
            writeValidatingRoleUi( writer, roleUi );
        }
    }

    /**
     * Writes the specified validating game system user interface to the
     * specified XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param gameSystemUi
     *        The validating game system user interface; must not be
     *        {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeValidatingGameSystemUi(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final IGameSystemUi gameSystemUi )
        throws XMLStreamException
    {
        assert writer != null;
        assert gameSystemUi != null;

        writer.writeStartElement( XmlGameSystemUi.NAME_GAME_SYSTEM_UI );
        writer.writeAttribute( XmlGameSystemUi.NAME_ID, gameSystemUi.getId() );
        writer.writeAttribute( XmlGameSystemUi.NAME_NAME, gameSystemUi.getName() );

        final List<IRoleUi> roleUis = gameSystemUi.getRoles();
        if( !roleUis.isEmpty() )
        {
            writer.writeStartElement( XmlGameSystemUi.NAME_ROLES );
            for( final IRoleUi roleUi : roleUis )
            {
                writeRoleUi( writer, roleUi );
            }
            writer.writeEndElement();
        }

        writer.writeEndElement();
    }

    /**
     * Writes the specified validating role user interface to the specified XML
     * writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param roleUi
     *        The validating role user interface; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeValidatingRoleUi(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final IRoleUi roleUi )
        throws XMLStreamException
    {
        assert writer != null;
        assert roleUi != null;

        writer.writeStartElement( XmlRoleUi.NAME_ROLE );
        writer.writeAttribute( XmlRoleUi.NAME_ID, roleUi.getId() );
        writer.writeAttribute( XmlRoleUi.NAME_NAME, roleUi.getName() );
        writer.writeEndElement();
    }
}
