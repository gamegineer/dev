/*
 * NlsMessages.java
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
 * Created on Sep 16, 2011 at 8:48:20 PM.
 */

package org.gamegineer.table.internal.ui.impl.dialogs.selectremoteplayer;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the package.
 */
@ThreadSafe
final class NlsMessages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- Model ------------------------------------------------------------

    /** A remote player is not selected. */
    public static String Model_remotePlayer_notSelected;

    // --- SelectRemotePlayerDialog -----------------------------------------

    /** The dialog banner title. */
    public static String SelectRemotePlayerDialog_bannerTitle;

    /** The dialog description. */
    public static String SelectRemotePlayerDialog_description;

    /** The remote players label mnemonic. */
    public static String SelectRemotePlayerDialog_remotePlayersLabel_mnemonic;

    /** The remote players label text. */
    public static String SelectRemotePlayerDialog_remotePlayersLabel_text;

    /** The dialog title. */
    public static String SelectRemotePlayerDialog_title;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code NlsMessages} class.
     */
    static
    {
        NLS.initializeMessages( NlsMessages.class.getName(), NlsMessages.class );
    }

    /**
     * Initializes a new instance of the {@code NlsMessages} class.
     */
    private NlsMessages()
    {
    }
}
