/*
 * CommandContextBuilderTest.java
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
 * Created on May 3, 2009 at 10:17:26 PM.
 */

package org.gamegineer.engine.core.contexts.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.contexts.command.CommandContextBuilder}
 * class.
 */
public final class CommandContextBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command context builder under test in the fixture. */
    private CommandContextBuilder m_builder;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandContextBuilderTest}
     * class.
     */
    public CommandContextBuilderTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a legal attribute collection.
     * 
     * @return A legal attribute collection; never {@code null}.
     */
    /* @NonNull */
    private static Map<String, Object> createLegalAttributes()
    {
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put( "name1", new Object() ); //$NON-NLS-1$
        attributes.put( "name2", new Object() ); //$NON-NLS-1$
        attributes.put( "name3", new Object() ); //$NON-NLS-1$
        return attributes;
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
        m_builder = new CommandContextBuilder();
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
        m_builder = null;
    }

    /**
     * Ensures the {@code addAttribute} method adds an attribute to the
     * resulting context when the attribute is absent.
     */
    @Test
    public void testAddAttribute_Attribute_Absent()
    {
        final Map<String, Object> expectedAttributes = createLegalAttributes();

        for( final Map.Entry<String, Object> entry : expectedAttributes.entrySet() )
        {
            m_builder.addAttribute( entry.getKey(), entry.getValue() );
        }

        final ICommandContext context = m_builder.toCommandContext();
        for( final Map.Entry<String, Object> entry : expectedAttributes.entrySet() )
        {
            assertEquals( entry.getValue(), context.getAttribute( entry.getKey() ) );
        }
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when the
     * attribute is already present.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddAttribute_Attribute_Present()
    {
        final String name = "name"; //$NON-NLS-1$
        final Object value = new Object();
        m_builder.addAttribute( name, value );

        m_builder.addAttribute( name, value );
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testAddAttribute_Name_Null()
    {
        m_builder.addAttribute( null, new Object() );
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when passed a
     * {@code null} value.
     */
    @Test( expected = NullPointerException.class )
    public void testAddAttribute_Value_Null()
    {
        m_builder.addAttribute( "name", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code addAttribute} method returns the same builder
     * instance.
     */
    @Test
    public void testAddAttribute_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.addAttribute( "name", new Object() ) ); //$NON-NLS-1$
    }
}
