/*
 * XmlRoleUiTest.java
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
 * Created on Feb 28, 2009 at 10:17:19 PM.
 */

package org.gamegineer.game.internal.ui.system.bindings.xml;

import static org.gamegineer.game.ui.system.Assert.assertRoleUiEquals;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.ui.system.GameSystemUis;
import org.gamegineer.game.ui.system.IRoleUi;
import org.gamegineer.game.ui.system.NonValidatingRoleUiBuilder;
import org.gamegineer.game.ui.system.bindings.xml.XmlGameSystemUis;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.ui.system.bindings.xml.XmlRoleUi} class.
 */
public final class XmlRoleUiTest
    extends AbstractJaxbTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role user interface builder for use in the fixture. */
    private NonValidatingRoleUiBuilder builder_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlRoleUiTest} class.
     */
    public XmlRoleUiTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a reader for the specified role user interface.
     * 
     * @param roleUi
     *        The role user interface; must not be {@code null}.
     * 
     * @return A reader for the specified role user interface; never {@code
     *         null}.
     */
    /* @NonNull */
    private static Reader createRoleUiReader(
        /* @NonNull */
        final IRoleUi roleUi )
    {
        assert roleUi != null;

        return new StringReader( XmlGameSystemUis.toXml( FakeRootElement.NAME_ROOT, roleUi ) );
    }

    /*
     * @see org.gamegineer.game.internal.ui.system.xml.AbstractJaxbTestCase#getRootElementType()
     */
    @Override
    protected Class<?> getRootElementType()
    {
        return FakeRootElement.class;
    }

    /*
     * @see org.gamegineer.game.internal.ui.system.xml.AbstractJaxbTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        builder_ = new NonValidatingRoleUiBuilder( GameSystemUis.createRoleUi( GameSystems.createUniqueRole() ) );

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
     * Ensures the {@code toRoleUi} method creates the expected role user
     * interface when given a well-formed role user interface.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testToRoleUi_Success()
        throws Exception
    {
        final IRoleUi expectedRoleUi = builder_.toRoleUi();
        final FakeRootElement root = (FakeRootElement)getUnmarshaller().unmarshal( createRoleUiReader( expectedRoleUi ) );
        final XmlRoleUi xmlRoleUi = root.getRoleUi();

        final IRoleUi actualRoleUi = xmlRoleUi.toRoleUi();

        assertRoleUiEquals( expectedRoleUi, actualRoleUi );
    }

    /**
     * Ensures a role user interface fails to be unmarshalled when it does not
     * have an identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoId()
        throws Exception
    {
        builder_.setId( null );

        getUnmarshaller().unmarshal( createRoleUiReader( builder_.toRoleUi() ) );
    }

    /**
     * Ensures a role user interface is successfully unmarshalled when it is
     * completely specified.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnmarshal_Success_Complete()
        throws Exception
    {
        final IRoleUi expectedRoleUi = builder_.toRoleUi();

        final FakeRootElement root = (FakeRootElement)getUnmarshaller().unmarshal( createRoleUiReader( expectedRoleUi ) );
        final IRoleUi actualRoleUi = root.getRoleUi().toRoleUi();

        assertRoleUiEquals( expectedRoleUi, actualRoleUi );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A fake element that serves as the root element for documents under test
     * in the fixture.
     */
    @XmlAccessorType( XmlAccessType.NONE )
    @XmlRootElement( name = "root" )
    private static final class FakeRootElement
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The local name of the root element. */
        static final String NAME_ROOT = "root"; //$NON-NLS-1$

        /** The role user interface. */
        @XmlElement( name = XmlRoleUi.NAME_ROLE, required = true )
        private final XmlRoleUi roleUi_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code FakeRootElement} class.
         */
        private FakeRootElement()
        {
            roleUi_ = null;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the role user interface.
         * 
         * @return The role user interface; never {@code null}.
         */
        /* @NonNull */
        XmlRoleUi getRoleUi()
        {
            return roleUi_;
        }
    }
}
