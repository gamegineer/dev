/*
 * AbstractStringBundleTestCase.java
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
 * Created on Feb 28, 2009 at 9:19:10 PM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.ui.system.bindings.xml.IStringBundle} interface.
 */
public abstract class AbstractStringBundleTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A string bundle key used for testing. */
    private static final String TEST_KEY = "key"; //$NON-NLS-1$

    /** A string bundle value used for testing. */
    private static final String TEST_VALUE = "value"; //$NON-NLS-1$

    /** The string bundle under test in the fixture. */
    private IStringBundle m_bundle;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractStringBundleTestCase}
     * class.
     */
    protected AbstractStringBundleTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the string bundle to be tested.
     * 
     * @param entries
     *        The string bundle entries; must not be {@code null}.
     * 
     * @return The string bundle to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code entries} is {@code null}.
     */
    /* @NonNull */
    protected abstract IStringBundle createStringBundle(
        /* @NonNull */
        Map<String, String> entries )
        throws Exception;

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
        m_bundle = createStringBundle( Collections.singletonMap( TEST_KEY, TEST_VALUE ) );
        assertNotNull( m_bundle );
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
        m_bundle = null;
    }

    /**
     * Ensures the {@code containsKey} method returns {@code false} when the key
     * is absent.
     */
    @Test
    public void testContainsKey_Key_Absent()
    {
        final boolean isKeyPresent = m_bundle.containsKey( "unknown" ); //$NON-NLS-1$

        assertFalse( isKeyPresent );
    }

    /**
     * Ensures the {@code containsKey} method throws an exception when passed a
     * {@code null} key.
     */
    @Test( expected = NullPointerException.class )
    public void testContainsKey_Key_Null()
    {
        m_bundle.containsKey( null );
    }

    /**
     * Ensures the {@code containsKey} method returns {@code true} when the key
     * is present.
     */
    @Test
    public void testContainsKey_Key_Present()
    {
        final boolean isKeyPresent = m_bundle.containsKey( TEST_KEY );

        assertTrue( isKeyPresent );
    }

    /**
     * Ensures the {@code getKeys} method returns a collection containing all
     * the keys in the bundle when the bundle contains at least one entry.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetKeys_OneOrMoreEntries()
    {
        final Set<String> keys = m_bundle.getKeys();

        assertEquals( 1, keys.size() );
        assertTrue( keys.contains( TEST_KEY ) );
    }

    /**
     * Ensures the {@code getKeys} method returns an immutable collection.
     */
    @Test
    public void testGetKeys_ReturnValue_Immutable()
    {
        final Set<String> keys = m_bundle.getKeys();

        assertImmutableCollection( keys );
    }

    /**
     * Ensures the {@code getKeys} method does not return {@code null}.
     */
    @Test
    public void testGetKeys_ReturnValue_NonNull()
    {
        final Set<String> keys = m_bundle.getKeys();

        assertNotNull( keys );
    }

    /**
     * Ensures the {@code getKeys} method returns an empty collection when the
     * bundle contains zero entries.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetKeys_ZeroEntries()
        throws Exception
    {
        final IStringBundle bundle = createStringBundle( Collections.<String, String>emptyMap() );

        final Set<String> keys = bundle.getKeys();

        assertEquals( 0, keys.size() );
    }

    /**
     * Ensures the {@code getString} method returns {@code null} when the key is
     * absent.
     */
    @Test
    public void testGetString_Key_Absent()
    {
        final String actualValue = m_bundle.getString( "unknown" ); //$NON-NLS-1$

        assertNull( actualValue );
    }

    /**
     * Ensures the {@code getString} method throws an exception when passed a
     * {@code null} key.
     */
    @Test( expected = NullPointerException.class )
    public void testGetString_Key_Null()
    {
        m_bundle.getString( null );
    }

    /**
     * Ensures the {@code getString} method returns the associated value when
     * the key is present.
     */
    @Test
    public void testGetString_Key_Present()
    {
        final String actualValue = m_bundle.getString( TEST_KEY );

        assertEquals( actualValue, TEST_VALUE );
    }
}
