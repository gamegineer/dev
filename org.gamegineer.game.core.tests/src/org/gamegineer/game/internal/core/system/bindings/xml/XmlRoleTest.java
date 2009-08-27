/*
 * XmlRoleTest.java
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
 * Created on Jan 17, 2009 at 10:11:56 PM.
 */

package org.gamegineer.game.internal.core.system.bindings.xml;

import static org.gamegineer.game.core.system.Assert.assertRoleEquals;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.game.core.system.NonValidatingRoleBuilder;
import org.gamegineer.game.core.system.bindings.xml.XmlGameSystems;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.bindings.xml.XmlRole} class.
 */
public final class XmlRoleTest
    extends AbstractJaxbTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role builder for use in the fixture. */
    private NonValidatingRoleBuilder m_builder;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlRoleTest} class.
     */
    public XmlRoleTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a reader for the specified role.
     * 
     * @param role
     *        The role; must not be {@code null}.
     * 
     * @return A reader for the specified role; never {@code null}.
     */
    /* @NonNull */
    private static Reader createRoleReader(
        /* @NonNull */
        final IRole role )
    {
        assert role != null;

        return new StringReader( XmlGameSystems.toXml( FakeRootElement.NAME_ROOT, role ) );
    }

    /*
     * @see org.gamegineer.game.internal.core.system.xml.AbstractJaxbTestCase#getRootElementType()
     */
    @Override
    protected Class<?> getRootElementType()
    {
        return FakeRootElement.class;
    }

    /*
     * @see org.gamegineer.game.internal.core.system.xml.AbstractJaxbTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        m_builder = new NonValidatingRoleBuilder( GameSystems.createUniqueRole() );

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
     * Ensures the {@code toRole} method creates the expected role when given a
     * well-formed role.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testToRole_Success()
        throws Exception
    {
        final IRole expectedRole = m_builder.toRole();
        final FakeRootElement root = (FakeRootElement)getUnmarshaller().unmarshal( createRoleReader( expectedRole ) );
        final XmlRole xmlRole = root.getRole();

        final IRole actualRole = xmlRole.toRole();

        assertRoleEquals( expectedRole, actualRole );
    }

    /**
     * Ensures a role fails to be unmarshalled when it does not have an
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

        getUnmarshaller().unmarshal( createRoleReader( m_builder.toRole() ) );
    }

    /**
     * Ensures a role is successfully unmarshalled when it is completely
     * specified.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnmarshal_Success_Complete()
        throws Exception
    {
        final IRole expectedRole = m_builder.toRole();

        final FakeRootElement root = (FakeRootElement)getUnmarshaller().unmarshal( createRoleReader( expectedRole ) );
        final IRole actualRole = root.getRole().toRole();

        assertRoleEquals( expectedRole, actualRole );
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

        /** The role. */
        @XmlElement( name = XmlRole.NAME_ROLE, required = true )
        private final XmlRole m_role;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code FakeRootElement} class.
         */
        private FakeRootElement()
        {
            m_role = null;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the role.
         * 
         * @return The role; never {@code null}.
         */
        /* @NonNull */
        XmlRole getRole()
        {
            return m_role;
        }
    }
}
