/*
 * TextUtils.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Aug 25, 2010 at 10:24:28 PM.
 */

package org.gamegineer.table.internal.ui.util.swing;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for working with text in the Java Swing
 * framework.
 */
@ThreadSafe
public final class TextUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TextUtils} class.
     */
    private TextUtils()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Shortens the specified path so that it does not exceed the specified
     * maximum length in characters.
     * 
     * <p>
     * The path is shortened one folder at a time by replacing a folder name
     * with an ellipsis ("..."). The path is shortened beginning with the folder
     * in the middle of the path and the then alternating between the parent and
     * child folders until the path reaches the requisite length.
     * </p>
     * 
     * <p>
     * The path prefix and file name will never be removed from the path even if
     * the resulting shortened path exceeds the maximum length.
     * </p>
     * 
     * @param path
     *        The path to shorten; must not be {@code null}.
     * @param maxLength
     *        The maximum length of the shortened path in characters; must not
     *        be negative.
     * 
     * @return The shortened path; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code maxLength} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code path} is {@code null}.
     */
    /* @NonNull */
    public static String shortenPath(
        /* @NonNull */
        final String path,
        final int maxLength )
    {
        assertArgumentNotNull( path, "path" ); //$NON-NLS-1$
        assertArgumentLegal( maxLength >= 0, "maxLength", NonNlsMessages.TextUtils_shortenPath_maxLengthNegative ); //$NON-NLS-1$

        if( path.length() <= maxLength )
        {
            return path;
        }

        final StringBuilder sb = new StringBuilder( path );
        final List<String> pathComponents = new ArrayList<String>( Arrays.asList( path.split( Pattern.quote( File.separator ) ) ) );
        while( pathComponents.size() > 2 )
        {
            sb.setLength( 0 );
            final int size = pathComponents.size();
            final int midIndex = size / 2;
            pathComponents.set( midIndex, "..." ); //$NON-NLS-1$
            for( int index = 0; index < size; ++index )
            {
                sb.append( pathComponents.get( index ) );
                if( index != (size - 1) )
                {
                    sb.append( File.separator );
                }
            }

            final String shortPath = sb.toString();
            if( shortPath.length() <= maxLength )
            {
                return shortPath;
            }

            pathComponents.remove( midIndex );
        }

        return sb.toString();
    }
}
