/*
 * LocaleNeutralKey.java
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
 * Created on Feb 26, 2009 at 11:13:21 PM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for working with locale-neutral keys as used
 * by the XML bindings for game system user interface types.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class LocaleNeutralKey
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The prefix of a decorated locale-neutral key. */
    private static final String DECORATED_KEY_PREFIX = "%"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocaleNeutralKey} class.
     */
    private LocaleNeutralKey()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Decorates the specified locale-neutral key.
     * 
     * @param key
     *        The locale-neutral key; must not be {@code null}.
     * 
     * @return The decorated locale-neutral key; never {@code null}. If the key
     *         is already decorated, it will be returned unchanged.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code key} is {@code null}.
     */
    /* @NonNull */
    public static String decorateKey(
        /* @NonNull */
        final String key )
    {
        assertArgumentNotNull( key, "key" ); //$NON-NLS-1$

        if( isDecoratedKey( key ) )
        {
            return key;
        }

        return DECORATED_KEY_PREFIX + key;
    }

    /**
     * Indicates the specified locale-neutral key is decorated.
     * 
     * @param key
     *        The locale-neutral key; must not be {@code null}.
     * 
     * @return The decorated locale-neutral key; never {@code null}. If the key
     *         is already decorated, it will be returned unchanged.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code key} is {@code null}.
     */
    public static boolean isDecoratedKey(
        /* @NonNull */
        final String key )
    {
        assertArgumentNotNull( key, "key" ); //$NON-NLS-1$

        return key.startsWith( DECORATED_KEY_PREFIX );
    }

    /**
     * Undecorates the specified locale-neutral key.
     * 
     * @param key
     *        The locale-neutral key; must not be {@code null}.
     * 
     * @return The undecorated locale-neutral key; never {@code null}. If the
     *         key is not decorated, it will be returned unchanged.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code key} is {@code null}.
     */
    /* @NonNull */
    public static String undecorateKey(
        /* @NonNull */
        final String key )
    {
        assertArgumentNotNull( key, "key" ); //$NON-NLS-1$

        if( !isDecoratedKey( key ) )
        {
            return key;
        }

        return key.substring( DECORATED_KEY_PREFIX.length() );
    }
}
