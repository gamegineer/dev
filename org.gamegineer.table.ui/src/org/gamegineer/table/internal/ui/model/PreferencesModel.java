/*
 * PreferencesModel.java
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
 * Created on Aug 14, 2010 at 8:58:48 PM.
 */

package org.gamegineer.table.internal.ui.model;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.Loggers;
import org.osgi.service.prefs.Preferences;

/**
 * The preferences model.
 */
@ThreadSafe
public final class PreferencesModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The preference node for the file name history. */
    private static final String NODE_FILE_NAME_HISTORY = "fileNameHistory"; //$NON-NLS-1$

    /** The preference node for the frame preferences. */
    private static final String NODE_FRAME = "frame"; //$NON-NLS-1$

    /** The file name history preferences. */
    private final FileNameHistoryPreferences fileNameHistoryPreferences_;

    /** The frame window preferences. */
    private final FramePreferences framePreferences_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PreferencesModel} class.
     */
    public PreferencesModel()
    {
        fileNameHistoryPreferences_ = new FileNameHistoryPreferences();
        framePreferences_ = new FramePreferences();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the file name history preferences.
     * 
     * @return The file name history preferences; never {@code null}.
     */
    /* @NonNull */
    public FileNameHistoryPreferences getFileNameHistoryPreferences()
    {
        return fileNameHistoryPreferences_;
    }

    /**
     * Gets the frame window preferences.
     * 
     * @return The frame window preferences; never {@code null}.
     */
    /* @NonNull */
    public FramePreferences getFramePreferences()
    {
        return framePreferences_;
    }

    /**
     * Loads the preferences model from persistent storage.
     */
    public void load()
    {
        final Preferences preferences = Activator.getDefault().getUserPreferences( PreferencesModel.class );
        if( preferences == null )
        {
            Loggers.getDefaultLogger().severe( Messages.PreferencesModel_userPreferences_notAvailable );
            return;
        }

        fileNameHistoryPreferences_.load( preferences.node( NODE_FILE_NAME_HISTORY ) );
        framePreferences_.load( preferences.node( NODE_FRAME ) );
    }

    /**
     * Stores the preferences model to persistent storage.
     */
    public void save()
    {
        final Preferences preferences = Activator.getDefault().getUserPreferences( PreferencesModel.class );
        if( preferences == null )
        {
            Loggers.getDefaultLogger().severe( Messages.PreferencesModel_userPreferences_notAvailable );
            return;
        }

        fileNameHistoryPreferences_.save( preferences.node( NODE_FILE_NAME_HISTORY ) );
        framePreferences_.save( preferences.node( NODE_FRAME ) );
    }
}
