/*
 * TextUtilsTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Aug 25, 2010 at 10:27:34 PM.
 */

package org.gamegineer.table.internal.ui.impl.util.swing;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.junit.Test;

/**
 * A fixture for testing the {@link TextUtils} class.
 */
public final class TextUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TextUtilsTest} class.
     */
    public TextUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Normalizes the specified Windows file system path to the host operating
     * system conventions.
     * 
     * @param path
     *        The Windows file system path; must not be {@code null}.
     * 
     * @return The normalized file system path; never {@code null}.
     */
    /* @NonNull */
    private static String normalizePath(
        /* @NonNull */
        final String path )
    {
        assert path != null;

        return path.replace( '\\', File.separatorChar );
    }

    /**
     * Ensures the {@link TextUtils#shortenPath} method correctly shortens an
     * absolute path that has two path components.
     */
    @Test
    public void testShortenPath_Components_2()
    {
        final String path = normalizePath( "C:\\filename.ext" ); //$NON-NLS-1$

        assertEquals( normalizePath( "C:\\filename.ext" ), TextUtils.shortenPath( path, 8 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\filename.ext" ), TextUtils.shortenPath( path, 14 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TextUtils#shortenPath} method correctly shortens an
     * absolute path that has three path components.
     */
    @Test
    public void testShortenPath_Components_3()
    {
        final String path = normalizePath( "C:\\dir1\\filename.ext" ); //$NON-NLS-1$

        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 8 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 14 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 19 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\filename.ext" ), TextUtils.shortenPath( path, 20 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TextUtils#shortenPath} method correctly shortens an
     * absolute path that has four path components.
     */
    @Test
    public void testShortenPath_Components_4()
    {
        final String path = normalizePath( "C:\\dir1\\dir2\\filename.ext" ); //$NON-NLS-1$

        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 8 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 14 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 19 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\...\\filename.ext" ), TextUtils.shortenPath( path, 24 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\dir2\\filename.ext" ), TextUtils.shortenPath( path, 25 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TextUtils#shortenPath} method correctly shortens an
     * absolute path that has five path components.
     */
    @Test
    public void testShortenPath_Components_5()
    {
        final String path = normalizePath( "C:\\dir1\\dir2\\dir3\\filename.ext" ); //$NON-NLS-1$

        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 8 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 14 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 19 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\...\\filename.ext" ), TextUtils.shortenPath( path, 24 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\...\\dir3\\filename.ext" ), TextUtils.shortenPath( path, 29 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\dir2\\dir3\\filename.ext" ), TextUtils.shortenPath( path, 30 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TextUtils#shortenPath} method correctly shortens an
     * absolute path that has six path components.
     */
    @Test
    public void testShortenPath_Components_6()
    {
        final String path = normalizePath( "C:\\dir1\\dir2\\dir3\\dir4\\filename.ext" ); //$NON-NLS-1$

        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 8 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 14 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 19 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\...\\filename.ext" ), TextUtils.shortenPath( path, 24 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\...\\dir4\\filename.ext" ), TextUtils.shortenPath( path, 29 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\dir2\\...\\dir4\\filename.ext" ), TextUtils.shortenPath( path, 34 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\dir2\\dir3\\dir4\\filename.ext" ), TextUtils.shortenPath( path, 35 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TextUtils#shortenPath} method correctly shortens an
     * absolute path that has seven path components.
     */
    @Test
    public void testShortenPath_Components_7()
    {
        final String path = normalizePath( "C:\\dir1\\dir2\\dir3\\dir4\\dir5\\filename.ext" ); //$NON-NLS-1$

        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 8 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 14 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\...\\filename.ext" ), TextUtils.shortenPath( path, 19 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\...\\filename.ext" ), TextUtils.shortenPath( path, 24 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\...\\dir5\\filename.ext" ), TextUtils.shortenPath( path, 29 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\dir2\\...\\dir5\\filename.ext" ), TextUtils.shortenPath( path, 34 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\dir2\\...\\dir4\\dir5\\filename.ext" ), TextUtils.shortenPath( path, 39 ) ); //$NON-NLS-1$
        assertEquals( normalizePath( "C:\\dir1\\dir2\\dir3\\dir4\\dir5\\filename.ext" ), TextUtils.shortenPath( path, 40 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TextUtils#shortenPath} method throws an exception when
     * passed a negative maximum length.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testShortenPath_MaxLength_Negative()
    {
        TextUtils.shortenPath( "path", -1 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TextUtils#shortenPath} method throws an exception when
     * passed a {@code null} path.
     */
    @Test( expected = NullPointerException.class )
    public void testShortenPath_Path_Null()
    {
        TextUtils.shortenPath( null, 0 );
    }
}
