/*
 * StringBundleFactory.java
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
 * Created on Feb 26, 2009 at 11:41:50 PM.
 */

package org.gamegineer.game.internal.ui.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.game.ui.system.bindings.xml.IStringBundle;

/**
 * A factory for creating string bundles.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class StringBundleFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** An empty string bundle. */
    public static final IStringBundle EMPTY_STRING_BUNDLE = createEmptyStringBundle();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StringBundleFactory} class.
     */
    private StringBundleFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an empty string bundle.
     * 
     * @return An empty string bundle; never {@code null}.
     */
    /* @NonNull */
    private static IStringBundle createEmptyStringBundle()
    {
        final IStringBundleResolver resolver = new IStringBundleResolver()
        {
            public Map<String, String> getStringBundleEntries(
                final Locale locale )
            {
                assertArgumentNotNull( locale, "locale" ); //$NON-NLS-1$

                return Collections.emptyMap();
            }
        };
        return createStringBundle( resolver, Locale.ROOT );
    }

    /**
     * Creates a string bundle for the specified locale.
     * 
     * <p>
     * This method loads strings for the specified locale and all its parent
     * locales. The locales are traversed in the following order:
     * </p>
     * 
     * <ul>
     * <li>language, country, variant</li>
     * <li>language, country, ""</li>
     * <li>language, "", ""</li>
     * <li>"", "", ""</li>
     * </ul>
     * 
     * <p>
     * Strings from more specific locales take precedence over strings from more
     * generic locales.
     * </p>
     * 
     * @param resolver
     *        The string bundle resolver; must not be {@code null}.
     * @param locale
     *        The string bundle locale; must not be {@code null}.
     * 
     * @return The string bundle for the specified locale; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code resolver} or {@code locale} is {@code null}.
     */
    /* @NonNull */
    public static IStringBundle createStringBundle(
        /* @NonNull */
        final IStringBundleResolver resolver,
        /* @NonNull */
        final Locale locale )
    {
        assertArgumentNotNull( resolver, "resolver" ); //$NON-NLS-1$
        assertArgumentNotNull( locale, "locale" ); //$NON-NLS-1$

        final Map<String, String> entries = new HashMap<String, String>();
        for( final Locale componentLocale : getComponentLocales( locale ) )
        {
            entries.putAll( resolver.getStringBundleEntries( componentLocale ) );
        }

        return new StringBundle( entries );
    }

    /**
     * Gets the collection of component string bundle locales that must be
     * traversed to build the composite string bundle for the specified locale.
     * 
     * @param locale
     *        The composite string bundle locale; must not be {@code null}.
     * 
     * @return The collection of component string bundle locales that must be
     *         traversed to build the composite string bundle for the specified
     *         locale; never {@code null}. The locales in the returned
     *         collection are ordered from most general to most specific.
     *         Therefore, a string from a locale at the end of the collection
     *         should override a string from a locale at the beginning of the
     *         collection.
     */
    /* @NonNull */
    private static Collection<Locale> getComponentLocales(
        /* @NonNull */
        final Locale locale )
    {
        assert locale != null;

        final String language = locale.getLanguage();
        final String country = locale.getCountry();
        final String variant = locale.getVariant();

        final Collection<Locale> locales = new ArrayList<Locale>( 4 );
        locales.add( Locale.ROOT );

        if( !language.isEmpty() )
        {
            locales.add( new Locale( language ) );

            if( !country.isEmpty() )
            {
                locales.add( new Locale( language, country ) );

                if( !variant.isEmpty() )
                {
                    locales.add( new Locale( language, country, variant ) );
                }
            }
        }

        return locales;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A string bundle resolver.
     */
    public interface IStringBundleResolver
    {
        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the string bundle entries for the specified locale.
         * 
         * @param locale
         *        The string bundle locale; must not be {@code null}.
         * 
         * @return The string bundle entries for the specified locale; never
         *         {@code null}. If there is no string bundle for the specified
         *         locale, an empty collection should be returned.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code locale} is {@code null}.
         */
        /* @NonNull */
        public Map<String, String> getStringBundleEntries(
            /* @NonNull */
            Locale locale );
    }
}
