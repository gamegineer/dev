/*
 * FileHistoryPreferences.java
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
 * Created on Aug 14, 2010 at 10:38:33 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.osgi.service.prefs.Preferences;

/**
 * The preference for main model file history.
 */
@ThreadSafe
public final class FileHistoryPreferences
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The capacity of the file history. */
    private static final int CAPACITY = 4;

    /** The preference key for a history entry. */
    private static final String KEY_ENTRY = "entry"; //$NON-NLS-1$

    /** The files. */
    @GuardedBy( "lock_" )
    private final Map<File, File> files_ = new LinkedHashMap<File, File>( CAPACITY, 0.75F, true )
    {
        private static final long serialVersionUID = 1L;

        @Override
        protected boolean removeEldestEntry(
            @SuppressWarnings( "unused" )
            final Map.Entry<File, File> eldest )
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
     * Initializes a new instance of the {@code FileHistoryPreferences} class.
     */
    FileHistoryPreferences()
    {
        lock_ = new Object();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified file to the history.
     * 
     * @param file
     *        The file; must not be {@code null}.
     */
    void addFile(
        /* @NonNull */
        final File file )
    {
        assert file != null;

        synchronized( lock_ )
        {
            files_.put( file, file );
        }
    }

    /**
     * Gets the collection of files in the history.
     * 
     * @return The collection of files in the history; never {@code null}. The
     *         collection is ordered from the oldest file to the newest file.
     */
    /* @NonNull */
    public List<File> getFiles()
    {
        synchronized( lock_ )
        {
            return new ArrayList<>( files_.values() );
        }
    }

    /**
     * Loads the file history preferences from the specified preference node.
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
            files_.clear();
            for( int index = 0; true; ++index )
            {
                final String path = preferences.get( KEY_ENTRY + index, null );
                if( path != null )
                {
                    final File file = new File( path );
                    files_.put( file, file );
                }
                else
                {
                    break;
                }
            }
        }
    }

    /**
     * Removes the specified file from the history.
     * 
     * @param file
     *        The file; must not be {@code null}.
     */
    void removeFile(
        /* @NonNull */
        final File file )
    {
        assert file != null;

        synchronized( lock_ )
        {
            files_.remove( file );
        }
    }

    /**
     * Stores the file history preferences in the specified preference node.
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
            for( final File file : files_.values() )
            {
                preferences.put( KEY_ENTRY + index, file.getAbsolutePath() );
                ++index;
            }
        }
    }
}
