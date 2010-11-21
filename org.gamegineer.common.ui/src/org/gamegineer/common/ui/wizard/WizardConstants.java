/*
 * WizardConstants.java
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
 * Created on Nov 18, 2010 at 9:04:52 PM.
 */

package org.gamegineer.common.ui.wizard;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful constants for wizards.
 */
@ThreadSafe
public final class WizardConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The identifier for the Back button. */
    public static final String BACK_BUTTON_ID = "back"; //$NON-NLS-1$

    /** The identifier for the Finish button. */
    public static final String FINISH_BUTTON_ID = "finish"; //$NON-NLS-1$

    /** The identifier for the Next button. */
    public static final String NEXT_BUTTON_ID = "next"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code WizardConstants} class.
     */
    private WizardConstants()
    {
        super();
    }
}
