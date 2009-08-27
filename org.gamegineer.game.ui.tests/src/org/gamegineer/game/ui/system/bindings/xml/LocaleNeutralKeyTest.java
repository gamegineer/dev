/*
 * LocaleNeutralKeyTest.java
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
 * Created on Feb 28, 2009 at 9:29:43 PM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.ui.system.bindings.xml.LocaleNeutralKey} class.
 */
public final class LocaleNeutralKeyTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A decorated locale-neutral key. */
    private static final String DECORATED_KEY = "%key"; //$NON-NLS-1$

    /** An undecorated locale-neutral key. */
    private static final String UNDECORATED_KEY = "key"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocaleNeutralKeyTest} class.
     */
    public LocaleNeutralKeyTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code decorateKey} method does not modify a decorated key.
     */
    @Test
    public void testDecorateKey_Key_Decorated()
    {
        final String decoratedKey = LocaleNeutralKey.decorateKey( DECORATED_KEY );

        assertEquals( DECORATED_KEY, decoratedKey );
    }

    /**
     * Ensures the {@code decorateKey} method throws an exception when passed a
     * {@code null} key.
     */
    @Test( expected = NullPointerException.class )
    public void testDecorateKey_Key_Null()
    {
        LocaleNeutralKey.decorateKey( null );
    }

    /**
     * Ensures the {@code decorateKey} method decorates an undecorated key.
     */
    @Test
    public void testDecorateKey_Key_Undecorated()
    {
        final String decoratedKey = LocaleNeutralKey.decorateKey( UNDECORATED_KEY );

        assertEquals( DECORATED_KEY, decoratedKey );
    }

    /**
     * Ensures the {@code isDecoratedKey} method correctly identifies a
     * decorated key.
     */
    @Test
    public void testIsDecoratedKey_Key_Decorated()
    {
        final boolean isDecoratedKey = LocaleNeutralKey.isDecoratedKey( DECORATED_KEY );

        assertTrue( isDecoratedKey );
    }

    /**
     * Ensures the {@code isDecoratedKey} method throws an exception when passed
     * a {@code null} key.
     */
    @Test( expected = NullPointerException.class )
    public void testIsDecoratedKey_Key_Null()
    {
        LocaleNeutralKey.isDecoratedKey( null );
    }

    /**
     * Ensures the {@code isDecoratedKey} method correctly identifies an
     * undecorated key.
     */
    @Test
    public void testIsDecoratedKey_Key_Undecorated()
    {
        final boolean isDecoratedKey = LocaleNeutralKey.isDecoratedKey( UNDECORATED_KEY );

        assertFalse( isDecoratedKey );
    }

    /**
     * Ensures the {@code undecorateKey} method undecorates a decorated key.
     */
    @Test
    public void testUndecorateKey_Key_Decorated()
    {
        final String undecoratedKey = LocaleNeutralKey.undecorateKey( DECORATED_KEY );

        assertEquals( UNDECORATED_KEY, undecoratedKey );
    }

    /**
     * Ensures the {@code undecorateKey} method throws an exception when passed
     * a {@code null} key.
     */
    @Test( expected = NullPointerException.class )
    public void testUndecorateKey_Key_Null()
    {
        LocaleNeutralKey.undecorateKey( null );
    }

    /**
     * Ensures the {@code undecorateKey} method does not modify an undecorated
     * key.
     */
    @Test
    public void testUndecorateKey_Key_Undecorated()
    {
        final String undecoratedKey = LocaleNeutralKey.undecorateKey( UNDECORATED_KEY );

        assertEquals( UNDECORATED_KEY, undecoratedKey );
    }
}
