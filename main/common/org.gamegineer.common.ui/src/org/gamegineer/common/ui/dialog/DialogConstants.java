/*
 * DialogConstants.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Sep 11, 2010 at 4:08:31 PM.
 */

package org.gamegineer.common.ui.dialog;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful constants for dialogs.
 */
@ThreadSafe
public final class DialogConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Button width in dialog units. */
    public static final int BUTTON_WIDTH = 61;

    /** The identifier for the Cancel button. */
    public static final String CANCEL_BUTTON_ID = "cancel"; //$NON-NLS-1$

    /** The label for the Cancel button. */
    public static final String CANCEL_BUTTON_LABEL = NlsMessages.DialogConstants_cancelButton_label;

    /** Horizontal margin in dialog units. */
    public static final int HORIZONTAL_MARGIN = 7;

    /** Horizontal spacing in dialog units. */
    public static final int HORIZONTAL_SPACING = 4;

    /** Indent in dialog units. */
    public static final int INDENT = 21;

    /** The identifier for the OK button. */
    public static final String OK_BUTTON_ID = "ok"; //$NON-NLS-1$

    /** The label for the OK button. */
    public static final String OK_BUTTON_LABEL = NlsMessages.DialogConstants_okButton_label;

    /** Small indent in dialog units. */
    public static final int SMALL_INDENT = 7;

    /** Vertical margin in dialog units. */
    public static final int VERTICAL_MARGIN = 7;

    /** Vertical spacing in dialog units. */
    public static final int VERTICAL_SPACING = 4;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DialogConstants} class.
     */
    private DialogConstants()
    {
    }
}
