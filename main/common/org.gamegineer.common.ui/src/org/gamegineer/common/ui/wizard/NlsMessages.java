/*
 * NlsMessages.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Sep 25, 2010 at 9:19:42 PM.
 */

package org.gamegineer.common.ui.wizard;

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

    // --- WizardDialog -----------------------------------------------------

    /** The label for the Back button. */
    public static String WizardDialog_backButton_label = ""; //$NON-NLS-1$

    /** The mnemonic for the Back button. */
    public static String WizardDialog_backButton_mnemonic = ""; //$NON-NLS-1$

    /** The label for the Finish button. */
    public static String WizardDialog_finishButton_label = ""; //$NON-NLS-1$

    /** The mnemonic for the Finish button. */
    public static String WizardDialog_finishButton_mnemonic = ""; //$NON-NLS-1$

    /** The label for the Next button. */
    public static String WizardDialog_nextButton_label = ""; //$NON-NLS-1$

    /** The mnemonic for the Next button. */
    public static String WizardDialog_nextButton_mnemonic = ""; //$NON-NLS-1$


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
