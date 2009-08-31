/*
 * StringBundleFactoryTest.java
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
 * Created on Feb 28, 2009 at 9:23:19 PM.
 */

package org.gamegineer.game.internal.ui.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.gamegineer.game.ui.system.bindings.xml.IStringBundle;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.ui.system.bindings.xml.StringBundleFactory}
 * class.
 */
public final class StringBundleFactoryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The string bundle key used for testing. */
    private static final String TEST_KEY = "__testKey__"; //$NON-NLS-1$

    /** The test locale that specifies language and country. */
    private static final Locale TEST_LOCALE_COUNTRY = new Locale( "en", "US" ); //$NON-NLS-1$ //$NON-NLS-2$

    /** The test locale that specifies language only. */
    private static final Locale TEST_LOCALE_LANGUAGE = new Locale( "en" ); //$NON-NLS-1$

    /** The test locale that specifies the root locale. */
    private static final Locale TEST_LOCALE_ROOT = Locale.ROOT;

    /** The test locale that specifies language, country, and variant. */
    private static final Locale TEST_LOCALE_VARIANT = new Locale( "en", "US", "variant" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    /** The prefix for string bundle values used for testing. */
    private static final String TEST_VALUE_PREFIX = "value"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StringBundleFactoryTest} class.
     */
    public StringBundleFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the expected value associated with {@code TEST_KEY} in the string
     * bundle node associated with the specified locale.
     * 
     * @param locale
     *        The string bundle node locale; must not be {@code null}.
     * 
     * @return The expected value associated with {@code TEST_KEY} in the string
     *         bundle node associated with the specified locale; never
     *         {@code null}.
     */
    /* @NonNull */
    private static String getNodeValue(
        /* @NonNull */
        final Locale locale )
    {
        assert locale != null;

        return String.format( "%1$s_%2$s_%3$s_%4$s", TEST_VALUE_PREFIX, locale.getLanguage(), locale.getCountry(), locale.getVariant() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createStringBundle} method properly consults the
     * string bundle node resolver when passed a locale that specifies the
     * language and country.
     */
    @Test
    public void testCreateStringBundle_Locale_Country_ResolverConsulted()
    {
        final MockStringBundleResolver resolver = new MockStringBundleResolver();

        StringBundleFactory.createStringBundle( resolver, TEST_LOCALE_COUNTRY );

        final Set<Locale> queriedLocales = resolver.getQueriedLocales();
        assertFalse( queriedLocales.contains( TEST_LOCALE_VARIANT ) );
        assertTrue( queriedLocales.contains( TEST_LOCALE_COUNTRY ) );
        assertTrue( queriedLocales.contains( TEST_LOCALE_LANGUAGE ) );
        assertTrue( queriedLocales.contains( TEST_LOCALE_ROOT ) );
    }

    /**
     * Ensures the {@code createStringBundle} method properly consults the
     * string bundle node resolver when passed a locale that specifies the
     * language.
     */
    @Test
    public void testCreateStringBundle_Locale_Language_ResolverConsulted()
    {
        final MockStringBundleResolver resolver = new MockStringBundleResolver();

        StringBundleFactory.createStringBundle( resolver, TEST_LOCALE_LANGUAGE );

        final Set<Locale> queriedLocales = resolver.getQueriedLocales();
        assertFalse( queriedLocales.contains( TEST_LOCALE_VARIANT ) );
        assertFalse( queriedLocales.contains( TEST_LOCALE_COUNTRY ) );
        assertTrue( queriedLocales.contains( TEST_LOCALE_LANGUAGE ) );
        assertTrue( queriedLocales.contains( TEST_LOCALE_ROOT ) );
    }

    /**
     * Ensures the {@code createStringBundle} method throws an exception if
     * passed a {@code null} locale.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateStringBundle_Locale_Null()
    {
        StringBundleFactory.createStringBundle( createDummy( StringBundleFactory.IStringBundleResolver.class ), null );
    }

    /**
     * Ensures the {@code createStringBundle} method properly consults the
     * string bundle node resolver when passed the root locale.
     */
    @Test
    public void testCreateStringBundle_Locale_Root_ResolverConsulted()
    {
        final MockStringBundleResolver resolver = new MockStringBundleResolver();

        StringBundleFactory.createStringBundle( resolver, TEST_LOCALE_ROOT );

        final Set<Locale> queriedLocales = resolver.getQueriedLocales();
        assertFalse( queriedLocales.contains( TEST_LOCALE_VARIANT ) );
        assertFalse( queriedLocales.contains( TEST_LOCALE_COUNTRY ) );
        assertFalse( queriedLocales.contains( TEST_LOCALE_LANGUAGE ) );
        assertTrue( queriedLocales.contains( TEST_LOCALE_ROOT ) );
    }

    /**
     * Ensures the {@code createStringBundle} method properly consults the
     * string bundle node resolver when passed a locale that specifies the
     * language, country, and variant.
     */
    @Test
    public void testCreateStringBundle_Locale_Variant_ResolverConsulted()
    {
        final MockStringBundleResolver resolver = new MockStringBundleResolver();

        StringBundleFactory.createStringBundle( resolver, TEST_LOCALE_VARIANT );

        final Set<Locale> queriedLocales = resolver.getQueriedLocales();
        assertTrue( queriedLocales.contains( TEST_LOCALE_VARIANT ) );
        assertTrue( queriedLocales.contains( TEST_LOCALE_COUNTRY ) );
        assertTrue( queriedLocales.contains( TEST_LOCALE_LANGUAGE ) );
        assertTrue( queriedLocales.contains( TEST_LOCALE_ROOT ) );
    }

    /**
     * Ensures the {@code createStringBundle} method throws an exception if
     * passed a {@code null} resolver.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateStringBundle_Resolver_Null()
    {
        StringBundleFactory.createStringBundle( null, Locale.ROOT );
    }

    /**
     * Ensures the {@code createStringBundle} method creates a string bundle
     * that returns the proper value for a key whose value is absent.
     */
    @Test
    public void testCreateStringBundle_ReturnValue_KeyAbsent()
    {
        final FakeStringBundleResolver resolver = new FakeStringBundleResolver();
        resolver.setStringBundleEntries( TEST_LOCALE_VARIANT, Collections.<String, String>emptyMap() );
        resolver.setStringBundleEntries( TEST_LOCALE_COUNTRY, Collections.<String, String>emptyMap() );
        resolver.setStringBundleEntries( TEST_LOCALE_LANGUAGE, Collections.<String, String>emptyMap() );
        resolver.setStringBundleEntries( TEST_LOCALE_ROOT, Collections.<String, String>emptyMap() );

        final IStringBundle bundle = StringBundleFactory.createStringBundle( resolver, TEST_LOCALE_VARIANT );

        assertNull( bundle.getString( TEST_KEY ) );
    }

    /**
     * Ensures the {@code createStringBundle} method creates a string bundle
     * that returns the proper value for a key whose value is present in the
     * country node.
     */
    @Test
    public void testCreateStringBundle_ReturnValue_KeyPresent_CountryNode()
    {
        final FakeStringBundleResolver resolver = new FakeStringBundleResolver();
        resolver.setStringBundleEntries( TEST_LOCALE_VARIANT, Collections.<String, String>emptyMap() );
        resolver.setStringBundleEntries( TEST_LOCALE_COUNTRY, Collections.singletonMap( TEST_KEY, getNodeValue( TEST_LOCALE_COUNTRY ) ) );
        resolver.setStringBundleEntries( TEST_LOCALE_LANGUAGE, Collections.singletonMap( TEST_KEY, getNodeValue( TEST_LOCALE_LANGUAGE ) ) );
        resolver.setStringBundleEntries( TEST_LOCALE_ROOT, Collections.singletonMap( TEST_KEY, getNodeValue( TEST_LOCALE_ROOT ) ) );

        final IStringBundle bundle = StringBundleFactory.createStringBundle( resolver, TEST_LOCALE_VARIANT );

        assertEquals( getNodeValue( TEST_LOCALE_COUNTRY ), bundle.getString( TEST_KEY ) );
    }

    /**
     * Ensures the {@code createStringBundle} method creates a string bundle
     * that returns the proper value for a key whose value is present in the
     * language node.
     */
    @Test
    public void testCreateStringBundle_ReturnValue_KeyPresent_LanguageNode()
    {
        final FakeStringBundleResolver resolver = new FakeStringBundleResolver();
        resolver.setStringBundleEntries( TEST_LOCALE_VARIANT, Collections.<String, String>emptyMap() );
        resolver.setStringBundleEntries( TEST_LOCALE_COUNTRY, Collections.<String, String>emptyMap() );
        resolver.setStringBundleEntries( TEST_LOCALE_LANGUAGE, Collections.singletonMap( TEST_KEY, getNodeValue( TEST_LOCALE_LANGUAGE ) ) );
        resolver.setStringBundleEntries( TEST_LOCALE_ROOT, Collections.singletonMap( TEST_KEY, getNodeValue( TEST_LOCALE_ROOT ) ) );

        final IStringBundle bundle = StringBundleFactory.createStringBundle( resolver, TEST_LOCALE_VARIANT );

        assertEquals( getNodeValue( TEST_LOCALE_LANGUAGE ), bundle.getString( TEST_KEY ) );
    }

    /**
     * Ensures the {@code createStringBundle} method creates a string bundle
     * that returns the proper value for a key whose value is present in the
     * root node.
     */
    @Test
    public void testCreateStringBundle_ReturnValue_KeyPresent_RootNode()
    {
        final FakeStringBundleResolver resolver = new FakeStringBundleResolver();
        resolver.setStringBundleEntries( TEST_LOCALE_VARIANT, Collections.<String, String>emptyMap() );
        resolver.setStringBundleEntries( TEST_LOCALE_COUNTRY, Collections.<String, String>emptyMap() );
        resolver.setStringBundleEntries( TEST_LOCALE_LANGUAGE, Collections.<String, String>emptyMap() );
        resolver.setStringBundleEntries( TEST_LOCALE_ROOT, Collections.singletonMap( TEST_KEY, getNodeValue( TEST_LOCALE_ROOT ) ) );

        final IStringBundle bundle = StringBundleFactory.createStringBundle( resolver, TEST_LOCALE_VARIANT );

        assertEquals( getNodeValue( TEST_LOCALE_ROOT ), bundle.getString( TEST_KEY ) );
    }

    /**
     * Ensures the {@code createStringBundle} method creates a string bundle
     * that returns the proper value for a key whose value is present in the
     * variant node.
     */
    @Test
    public void testCreateStringBundle_ReturnValue_KeyPresent_VariantNode()
    {
        final FakeStringBundleResolver resolver = new FakeStringBundleResolver();
        resolver.setStringBundleEntries( TEST_LOCALE_VARIANT, Collections.singletonMap( TEST_KEY, getNodeValue( TEST_LOCALE_VARIANT ) ) );
        resolver.setStringBundleEntries( TEST_LOCALE_COUNTRY, Collections.singletonMap( TEST_KEY, getNodeValue( TEST_LOCALE_COUNTRY ) ) );
        resolver.setStringBundleEntries( TEST_LOCALE_LANGUAGE, Collections.singletonMap( TEST_KEY, getNodeValue( TEST_LOCALE_LANGUAGE ) ) );
        resolver.setStringBundleEntries( TEST_LOCALE_ROOT, Collections.singletonMap( TEST_KEY, getNodeValue( TEST_LOCALE_ROOT ) ) );

        final IStringBundle bundle = StringBundleFactory.createStringBundle( resolver, TEST_LOCALE_VARIANT );

        assertEquals( getNodeValue( TEST_LOCALE_VARIANT ), bundle.getString( TEST_KEY ) );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A fake string bundle resolver implementation.
     */
    private static final class FakeStringBundleResolver
        implements StringBundleFactory.IStringBundleResolver
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The collection of string bundles. */
        private final Map<Locale, Map<String, String>> m_bundles;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code FakeStringBundleResolver}
         * class.
         */
        FakeStringBundleResolver()
        {
            m_bundles = new HashMap<Locale, Map<String, String>>();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.game.internal.core.system.xml.StringBundleFactory.IStringBundleResolver#getStringBundleEntries(java.util.Locale)
         */
        public Map<String, String> getStringBundleEntries(
            final Locale locale )
        {
            assertArgumentNotNull( locale, "locale" ); //$NON-NLS-1$

            final Map<String, String> entries = m_bundles.get( locale );
            if( entries == null )
            {
                return Collections.emptyMap();
            }

            return entries;
        }

        /**
         * Sets the string bundle entries for the specified locale.
         * 
         * @param locale
         *        The string bundle locale; must not be {@code null}.
         * @param entries
         *        The string bundle entries; must not be {@code null}.
         */
        void setStringBundleEntries(
            /* @NonNull */
            final Locale locale,
            /* @NonNull */
            final Map<String, String> entries )
        {
            assert locale != null;
            assert entries != null;

            m_bundles.put( locale, new HashMap<String, String>( entries ) );
        }
    }

    /**
     * A mock string bundle resolver implementation.
     */
    private static final class MockStringBundleResolver
        implements StringBundleFactory.IStringBundleResolver
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The set of locales that were queried for a string bundle. */
        private final Set<Locale> m_queriedLocales;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockStringBundleResolver}
         * class.
         */
        MockStringBundleResolver()
        {
            m_queriedLocales = new HashSet<Locale>();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the set of locales that were queried for a string bundle.
         * 
         * @return The set of locales that were queried for a string bundle;
         *         never {@code null}.
         */
        /* @NonNull */
        Set<Locale> getQueriedLocales()
        {
            return new HashSet<Locale>( m_queriedLocales );
        }

        /*
         * @see org.gamegineer.game.internal.core.system.xml.StringBundleFactory.IStringBundleResolver#getStringBundleEntries(java.util.Locale)
         */
        public Map<String, String> getStringBundleEntries(
            final Locale locale )
        {
            assertArgumentNotNull( locale, "locale" ); //$NON-NLS-1$

            m_queriedLocales.add( locale );

            return Collections.emptyMap();
        }
    }
}
