/*
 * XmlGameSystemUiTest.java
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
 * Created on Feb 28, 2009 at 10:17:13 PM.
 */

package org.gamegineer.game.internal.ui.system.bindings.xml;

import static org.gamegineer.game.ui.system.Assert.assertGameSystemUiEquals;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.bind.UnmarshalException;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.ui.system.GameSystemUis;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.gamegineer.game.ui.system.NonValidatingGameSystemUiBuilder;
import org.gamegineer.game.ui.system.bindings.xml.XmlGameSystemUis;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.ui.system.bindings.xml.XmlGameSystemUi}
 * class.
 */
public final class XmlGameSystemUiTest
    extends AbstractJaxbTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system user interface builder for use in the fixture. */
    private NonValidatingGameSystemUiBuilder builder_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystemUiTest} class.
     */
    public XmlGameSystemUiTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a reader for the specified game system user interface.
     * 
     * @param gameSystemUi
     *        The game system user interface; must not be {@code null}.
     * 
     * @return A reader for the specified game system user interface; never
     *         {@code null}.
     */
    /* @NonNull */
    private static Reader createGameSystemUiReader(
        /* @NonNull */
        final IGameSystemUi gameSystemUi )
    {
        assert gameSystemUi != null;

        return new StringReader( XmlGameSystemUis.toXml( gameSystemUi ) );
    }

    /*
     * @see org.gamegineer.game.internal.ui.system.xml.AbstractJaxbTestCase#getRootElementType()
     */
    @Override
    protected Class<?> getRootElementType()
    {
        return XmlGameSystemUi.class;
    }

    /*
     * @see org.gamegineer.game.internal.ui.system.xml.AbstractJaxbTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        builder_ = new NonValidatingGameSystemUiBuilder( GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() ) );

        super.setUp();
    }

    /*
     * @see org.gamegineer.game.internal.ui.system.xml.AbstractJaxbTestCase#tearDown()
     */
    @After
    @Override
    public void tearDown()
        throws Exception
    {
        super.tearDown();

        builder_ = null;
    }

    /**
     * Ensures the {@code toGameSystemUi} method creates the expected game
     * system user interface when given a well-formed game system user
     * interface.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testToGameSystemUi_Success()
        throws Exception
    {
        final IGameSystemUi expectedGameSystemUi = builder_.toGameSystemUi();
        final XmlGameSystemUi xmlGameSystemUi = (XmlGameSystemUi)getUnmarshaller().unmarshal( createGameSystemUiReader( expectedGameSystemUi ) );

        final IGameSystemUi actualGameSystemUi = xmlGameSystemUi.toGameSystemUi();

        assertGameSystemUiEquals( expectedGameSystemUi, actualGameSystemUi );
    }

    /**
     * Ensures a game system user interface fails to be unmarshalled when it
     * does not have an identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoId()
        throws Exception
    {
        builder_.setId( null );

        getUnmarshaller().unmarshal( createGameSystemUiReader( builder_.toGameSystemUi() ) );
    }

    /**
     * Ensures a game system user interface fails to be unmarshalled when it
     * does not have a name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoName()
        throws Exception
    {
        builder_.setName( null );

        getUnmarshaller().unmarshal( createGameSystemUiReader( builder_.toGameSystemUi() ) );
    }

    /**
     * Ensures a game system user interface fails to be unmarshalled when it
     * does not have a role container.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Ignore( "XmlElementWrapper.required = true does not appear to be respected" )
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoRoleContainer()
        throws Exception
    {
        builder_.clearRoles();

        getUnmarshaller().unmarshal( createGameSystemUiReader( builder_.toGameSystemUi() ) );
    }

    /**
     * Ensures a game system user interface fails to be unmarshalled when it
     * does not have at least one role.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoRoles()
        throws Exception
    {
        builder_.clearRoles().addRole( null );

        getUnmarshaller().unmarshal( createGameSystemUiReader( builder_.toGameSystemUi() ) );
    }

    /**
     * Ensures a game system user interface is successfully unmarshalled when it
     * is completely specified.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnmarshal_Success_Complete()
        throws Exception
    {
        final IGameSystemUi expectedGameSystemUi = builder_.toGameSystemUi();

        final XmlGameSystemUi xmlGameSystemUi = (XmlGameSystemUi)getUnmarshaller().unmarshal( createGameSystemUiReader( expectedGameSystemUi ) );
        final IGameSystemUi actualGameSystemUi = xmlGameSystemUi.toGameSystemUi();

        assertGameSystemUiEquals( expectedGameSystemUi, actualGameSystemUi );
    }
}
