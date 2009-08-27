/*
 * XmlGameSystems.java
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
 * Created on Nov 14, 2008 at 11:20:43 PM.
 */

package org.gamegineer.game.core.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.StringWriter;
import java.util.List;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.NonValidatingGameSystem;
import org.gamegineer.game.core.system.NonValidatingRole;
import org.gamegineer.game.core.system.NonValidatingStage;
import org.gamegineer.game.internal.core.system.bindings.xml.XmlGameSystem;
import org.gamegineer.game.internal.core.system.bindings.xml.XmlRole;
import org.gamegineer.game.internal.core.system.bindings.xml.XmlStage;

/**
 * A factory for creating various XML-based game system types suitable for
 * testing.
 * 
 * <p>
 * This class can properly handle any of the non-validating implementations.
 * </p>
 * 
 * <p>
 * This class is thread-safe
 * </p>
 */
@ThreadSafe
public final class XmlGameSystems
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystems} class.
     */
    private XmlGameSystems()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Converts the specified game system to a valid XML document.
     * 
     * @param gameSystem
     *        The game system; must not be {@code null}.
     * 
     * @return A string containing the XML representation of the specified game
     *         system; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystem} is {@code null}.
     */
    /* @NonNull */
    public static String toXml(
        /* @NonNull */
        final IGameSystem gameSystem )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$

        final StringWriter stringWriter = new StringWriter();
        final XMLOutputFactory factory = XMLOutputFactory.newInstance();

        try
        {
            final XMLStreamWriter writer = factory.createXMLStreamWriter( stringWriter );
            writer.writeStartDocument();

            writeGameSystem( writer, gameSystem );

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
     * Converts the specified role to a valid XML document.
     * 
     * @param rootElementName
     *        The name of the document root element; must not be {@code null}.
     *        This parameter is required because a role may not represent the
     *        root element of a document.
     * @param role
     *        The role; must not be {@code null}.
     * 
     * @return A string containing the XML representation of the specified role;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code rootElementName} or {@code role} is {@code null}.
     */
    /* @NonNull */
    public static String toXml(
        /* @NonNull */
        final String rootElementName,
        /* @NonNull */
        final IRole role )
    {
        assertArgumentNotNull( rootElementName, "rootElementName" ); //$NON-NLS-1$
        assertArgumentNotNull( role, "role" ); //$NON-NLS-1$

        final StringWriter stringWriter = new StringWriter();
        final XMLOutputFactory factory = XMLOutputFactory.newInstance();

        try
        {
            final XMLStreamWriter writer = factory.createXMLStreamWriter( stringWriter );
            writer.writeStartDocument();

            writer.writeStartElement( rootElementName );

            writeRole( writer, role );

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
     * Converts the specified stage to a valid XML document.
     * 
     * @param rootElementName
     *        The name of the document root element; must not be {@code null}.
     *        This parameter is required because a stage may not represent the
     *        root element of a document.
     * @param stage
     *        The stage; must not be {@code null}.
     * 
     * @return A string containing the XML representation of the specified
     *         stage; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code rootElementName} or {@code stage} is {@code null}.
     */
    /* @NonNull */
    public static String toXml(
        /* @NonNull */
        final String rootElementName,
        /* @NonNull */
        final IStage stage )
    {
        assertArgumentNotNull( rootElementName, "rootElementName" ); //$NON-NLS-1$
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$

        final StringWriter stringWriter = new StringWriter();
        final XMLOutputFactory factory = XMLOutputFactory.newInstance();

        try
        {
            final XMLStreamWriter writer = factory.createXMLStreamWriter( stringWriter );
            writer.writeStartDocument();

            writer.writeStartElement( rootElementName );

            writeStage( writer, stage );

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
     * Writes the specified game system to the specified XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param gameSystem
     *        The game system; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeGameSystem(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final IGameSystem gameSystem )
        throws XMLStreamException
    {
        assert writer != null;
        assert gameSystem != null;

        if( gameSystem instanceof NonValidatingGameSystem )
        {
            writeNonValidatingGameSystem( writer, (NonValidatingGameSystem)gameSystem );
        }
        else
        {
            writeValidatingGameSystem( writer, gameSystem );
        }
    }

    /**
     * Writes the specified non-validating game system to the specified XML
     * writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param gameSystem
     *        The non-validating game system; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeNonValidatingGameSystem(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final NonValidatingGameSystem gameSystem )
        throws XMLStreamException
    {
        assert writer != null;
        assert gameSystem != null;

        writer.writeStartElement( XmlGameSystem.NAME_GAME_SYSTEM );
        if( gameSystem.getId() != null )
        {
            writer.writeAttribute( XmlGameSystem.NAME_ID, gameSystem.getId() );
        }

        final List<IRole> roles = gameSystem.getRoles();
        if( (roles != null) && !roles.isEmpty() )
        {
            writer.writeStartElement( XmlGameSystem.NAME_ROLES );
            for( final IRole role : roles )
            {
                if( role != null )
                {
                    writeRole( writer, role );
                }
            }
            writer.writeEndElement();
        }

        final List<IStage> stages = gameSystem.getStages();
        if( (stages != null) && !stages.isEmpty() )
        {
            writer.writeStartElement( XmlGameSystem.NAME_STAGES );
            for( final IStage stage : stages )
            {
                if( stage != null )
                {
                    writeStage( writer, stage );
                }
            }
            writer.writeEndElement();
        }

        writer.writeEndElement();
    }

    /**
     * Writes the specified non-validating role to the specified XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param role
     *        The non-validating role; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeNonValidatingRole(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final NonValidatingRole role )
        throws XMLStreamException
    {
        assert writer != null;
        assert role != null;

        writer.writeStartElement( XmlRole.NAME_ROLE );
        if( role.getId() != null )
        {
            writer.writeAttribute( XmlRole.NAME_ID, role.getId() );
        }
        writer.writeEndElement();
    }

    /**
     * Writes the specified non-validating stage to the specified XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param stage
     *        The non-validating stage; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeNonValidatingStage(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final NonValidatingStage stage )
        throws XMLStreamException
    {
        assert writer != null;
        assert stage != null;

        writer.writeStartElement( XmlStage.NAME_STAGE );
        if( stage.hasCardinality() )
        {
            writer.writeAttribute( XmlStage.NAME_CARDINALITY, String.valueOf( stage.getCardinality() ) );
        }
        if( stage.getId() != null )
        {
            writer.writeAttribute( XmlStage.NAME_ID, stage.getId() );
        }
        if( stage.getStrategy() != null )
        {
            writer.writeAttribute( XmlStage.NAME_STRATEGY, stage.getStrategy().getClass().getName() );
        }

        final List<IStage> childStages = stage.getStages();
        if( (childStages != null) && !childStages.isEmpty() )
        {
            writer.writeStartElement( XmlStage.NAME_STAGES );
            for( final IStage childStage : childStages )
            {
                if( childStage != null )
                {
                    writeStage( writer, childStage );
                }
            }
            writer.writeEndElement();
        }

        writer.writeEndElement();
    }

    /**
     * Writes the specified role to the specified XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param role
     *        The role; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeRole(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final IRole role )
        throws XMLStreamException
    {
        assert writer != null;
        assert role != null;

        if( role instanceof NonValidatingRole )
        {
            writeNonValidatingRole( writer, (NonValidatingRole)role );
        }
        else
        {
            writeValidatingRole( writer, role );
        }
    }

    /**
     * Writes the specified stage to the specified XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param stage
     *        The stage; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeStage(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final IStage stage )
        throws XMLStreamException
    {
        assert writer != null;
        assert stage != null;

        if( stage instanceof NonValidatingStage )
        {
            writeNonValidatingStage( writer, (NonValidatingStage)stage );
        }
        else
        {
            writeValidatingStage( writer, stage );
        }
    }

    /**
     * Writes the specified validating game system to the specified XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param gameSystem
     *        The validating game system; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeValidatingGameSystem(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final IGameSystem gameSystem )
        throws XMLStreamException
    {
        assert writer != null;
        assert gameSystem != null;

        writer.writeStartElement( XmlGameSystem.NAME_GAME_SYSTEM );
        writer.writeAttribute( XmlGameSystem.NAME_ID, gameSystem.getId() );

        final List<IRole> roles = gameSystem.getRoles();
        if( !roles.isEmpty() )
        {
            writer.writeStartElement( XmlGameSystem.NAME_ROLES );
            for( final IRole role : roles )
            {
                writeRole( writer, role );
            }
            writer.writeEndElement();
        }

        final List<IStage> stages = gameSystem.getStages();
        if( !stages.isEmpty() )
        {
            writer.writeStartElement( XmlGameSystem.NAME_STAGES );
            for( final IStage stage : stages )
            {
                writeStage( writer, stage );
            }
            writer.writeEndElement();
        }

        writer.writeEndElement();
    }

    /**
     * Writes the specified validating role to the specified XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param role
     *        The validating role; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeValidatingRole(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final IRole role )
        throws XMLStreamException
    {
        assert writer != null;
        assert role != null;

        writer.writeStartElement( XmlRole.NAME_ROLE );
        writer.writeAttribute( XmlRole.NAME_ID, role.getId() );
        writer.writeEndElement();
    }

    /**
     * Writes the specified validating stage to the specified XML writer.
     * 
     * @param writer
     *        The XML writer; must not be {@code null}.
     * @param stage
     *        The validating stage; must not be {@code null}.
     * 
     * @throws javax.xml.stream.XMLStreamException
     *         If an error occurs.
     */
    private static void writeValidatingStage(
        /* @NonNull */
        final XMLStreamWriter writer,
        /* @NonNull */
        final IStage stage )
        throws XMLStreamException
    {
        assert writer != null;
        assert stage != null;

        writer.writeStartElement( XmlStage.NAME_STAGE );
        writer.writeAttribute( XmlStage.NAME_CARDINALITY, String.valueOf( stage.getCardinality() ) );
        writer.writeAttribute( XmlStage.NAME_ID, stage.getId() );
        writer.writeAttribute( XmlStage.NAME_STRATEGY, stage.getStrategy().getClass().getName() );

        final List<IStage> childStages = stage.getStages();
        if( !childStages.isEmpty() )
        {
            writer.writeStartElement( XmlStage.NAME_STAGES );
            for( final IStage childStage : childStages )
            {
                writeStage( writer, childStage );
            }
            writer.writeEndElement();
        }

        writer.writeEndElement();
    }
}
