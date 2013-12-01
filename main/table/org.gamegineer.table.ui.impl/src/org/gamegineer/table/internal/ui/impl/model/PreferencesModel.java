/*
 * PreferencesModel.java
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
 * Created on Aug 14, 2010 at 8:58:48 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.ui.impl.Activator;
import org.gamegineer.table.internal.ui.impl.Loggers;
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

    /** The preference node for the file history. */
    private static final String NODE_FILE_HISTORY = "fileHistory"; //$NON-NLS-1$

    /** The preference node for the frame preferences. */
    private static final String NODE_FRAME = "frame"; //$NON-NLS-1$

    /** The file history preferences. */
    private final FileHistoryPreferences fileHistoryPreferences_;

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
        fileHistoryPreferences_ = new FileHistoryPreferences();
        framePreferences_ = new FramePreferences();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the file history preferences.
     * 
     * @return The file history preferences; never {@code null}.
     */
    /* @NonNull */
    public FileHistoryPreferences getFileHistoryPreferences()
    {
        return fileHistoryPreferences_;
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
            Loggers.getDefaultLogger().severe( NonNlsMessages.PreferencesModel_userPreferences_notAvailable );
            return;
        }

        fileHistoryPreferences_.load( preferences.node( NODE_FILE_HISTORY ) );
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
            Loggers.getDefaultLogger().severe( NonNlsMessages.PreferencesModel_userPreferences_notAvailable );
            return;
        }

        fileHistoryPreferences_.save( preferences.node( NODE_FILE_HISTORY ) );
        framePreferences_.save( preferences.node( NODE_FRAME ) );
    }
}
