/*
 * XmlGameSystemTest.java
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
 * Created on Nov 18, 2008 at 9:40:14 PM.
 */

package org.gamegineer.game.internal.core.system.bindings.xml;

import static org.gamegineer.game.core.system.Assert.assertGameSystemEquals;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.bind.UnmarshalException;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.NonValidatingGameSystemBuilder;
import org.gamegineer.game.core.system.bindings.xml.XmlGameSystems;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.bindings.xml.XmlGameSystem}
 * class.
 */
public final class XmlGameSystemTest
    extends AbstractJaxbTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system builder for use in the fixture. */
    private NonValidatingGameSystemBuilder m_builder;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystemTest} class.
     */
    public XmlGameSystemTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a reader for the specified game system.
     * 
     * @param gameSystem
     *        The game system; must not be {@code null}.
     * 
     * @return A reader for the specified game system; never {@code null}.
     */
    /* @NonNull */
    private static Reader createGameSystemReader(
        /* @NonNull */
        final IGameSystem gameSystem )
    {
        assert gameSystem != null;

        return new StringReader( XmlGameSystems.toXml( gameSystem ) );
    }

    /*
     * @see org.gamegineer.game.internal.core.system.xml.AbstractJaxbTestCase#getRootElementType()
     */
    @Override
    protected Class<?> getRootElementType()
    {
        return XmlGameSystem.class;
    }

    /*
     * @see org.gamegineer.game.internal.core.system.xml.AbstractJaxbTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        m_builder = new NonValidatingGameSystemBuilder( GameSystems.createUniqueGameSystem() );

        super.setUp();
    }

    /*
     * @see org.gamegineer.game.internal.core.system.xml.AbstractJaxbTestCase#tearDown()
     */
    @After
    @Override
    public void tearDown()
        throws Exception
    {
        super.tearDown();

        m_builder = null;
    }

    /**
     * Ensures the {@code toGameSystem} method creates the expected game system
     * when given a well-formed game system.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testToGameSystem_Success()
        throws Exception
    {
        final IGameSystem expectedGameSystem = m_builder.toGameSystem();
        final XmlGameSystem xmlGameSystem = (XmlGameSystem)getUnmarshaller().unmarshal( createGameSystemReader( expectedGameSystem ) );

        final IGameSystem actualGameSystem = xmlGameSystem.toGameSystem();

        assertGameSystemEquals( expectedGameSystem, actualGameSystem );
    }

    /**
     * Ensures a game system fails to be unmarshalled when it does not have an
     * identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoId()
        throws Exception
    {
        m_builder.setId( null );

        getUnmarshaller().unmarshal( createGameSystemReader( m_builder.toGameSystem() ) );
    }

    /**
     * Ensures a game system fails to be unmarshalled when it does not have a
     * role container.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Ignore( "XmlElementWrapper.required = true does not appear to be respected" )
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoRoleContainer()
        throws Exception
    {
        m_builder.clearRoles();

        getUnmarshaller().unmarshal( createGameSystemReader( m_builder.toGameSystem() ) );
    }

    /**
     * Ensures a game system fails to be unmarshalled when it does not have at
     * least one role.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoRoles()
        throws Exception
    {
        m_builder.clearRoles().addRole( null );

        getUnmarshaller().unmarshal( createGameSystemReader( m_builder.toGameSystem() ) );
    }

    /**
     * Ensures a game system fails to be unmarshalled when it does not have a
     * stage container.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Ignore( "XmlElementWrapper.required = true does not appear to be respected" )
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoStageContainer()
        throws Exception
    {
        m_builder.clearStages();

        getUnmarshaller().unmarshal( createGameSystemReader( m_builder.toGameSystem() ) );
    }

    /**
     * Ensures a game system fails to be unmarshalled when it does not have at
     * least one stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoStages()
        throws Exception
    {
        m_builder.clearStages().addStage( null );

        getUnmarshaller().unmarshal( createGameSystemReader( m_builder.toGameSystem() ) );
    }

    /**
     * Ensures a game system is successfully unmarshalled when it is completely
     * specified.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnmarshal_Success_Complete()
        throws Exception
    {
        final IGameSystem expectedGameSystem = m_builder.toGameSystem();

        final XmlGameSystem xmlGameSystem = (XmlGameSystem)getUnmarshaller().unmarshal( createGameSystemReader( expectedGameSystem ) );
        final IGameSystem actualGameSystem = xmlGameSystem.toGameSystem();

        assertGameSystemEquals( expectedGameSystem, actualGameSystem );
    }
}
