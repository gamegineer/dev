/*
 * FileNameHistoryPreferences.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Aug 14, 2010 at 10:38:33 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.osgi.service.prefs.Preferences;

/**
 * The preference for main model file name history.
 */
@ThreadSafe
public final class FileNameHistoryPreferences
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The capacity of the file name history. */
    private static final int CAPACITY = 4;

    /** The preference key for a history entry. */
    private static final String KEY_ENTRY = "entry"; //$NON-NLS-1$

    /** The file names. */
    @GuardedBy( "lock_" )
    private final Map<String, String> fileNames_ = new LinkedHashMap<String, String>( CAPACITY, 0.75F, true )
    {
        private static final long serialVersionUID = 1L;

        @Override
        protected boolean removeEldestEntry(
            @SuppressWarnings( "unused" )
            final Map.Entry<String, String> eldest )
        {
            return size() > CAPACITY;
        }
    };

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FileNameHistoryPreferences}
     * class.
     */
    public FileNameHistoryPreferences()
    {
        lock_ = new Object();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified file name to the history.
     * 
     * @param fileName
     *        The file name; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code fileName} is {@code null}.
     */
    public void addFileName(
        /* @NonNull */
        final String fileName )
    {
        assertArgumentNotNull( fileName, "fileName" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            fileNames_.put( fileName, fileName );
        }
    }

    /**
     * Gets the collection of file names in the history.
     * 
     * @return The collection of file names in the history; never {@code null}.
     *         The collection is ordered from the oldest file name to the newest
     *         file name.
     */
    /* @NonNull */
    public List<String> getFileNames()
    {
        synchronized( lock_ )
        {
            return new ArrayList<String>( fileNames_.values() );
        }
    }

    /**
     * Loads the file name history preferences from the specified preference
     * node.
     * 
     * @param preferences
     *        The preferences node; must not be {@code null}.
     */
    void load(
        /* @NonNull */
        final Preferences preferences )
    {
        assert preferences != null;

        synchronized( lock_ )
        {
            fileNames_.clear();
            for( int index = 0; true; ++index )
            {
                final String fileName = preferences.get( KEY_ENTRY + index, null );
                if( fileName != null )
                {
                    fileNames_.put( fileName, fileName );
                }
                else
                {
                    break;
                }
            }
        }
    }

    /**
     * Stores the file name history preferences in the specified preference
     * node.
     * 
     * @param preferences
     *        The preferences node; must not be {@code null}.
     */
    void save(
        /* @NonNull */
        final Preferences preferences )
    {
        assert preferences != null;

        synchronized( lock_ )
        {
            int index = 0;
            for( final String fileName : fileNames_.values() )
            {
                preferences.put( KEY_ENTRY + index, fileName );
                ++index;
            }
        }
    }
}
