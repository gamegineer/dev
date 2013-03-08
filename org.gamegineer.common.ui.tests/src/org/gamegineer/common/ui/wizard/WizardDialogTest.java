/*
 * WizardDialogTest.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Sep 24, 2010 at 10:13:00 PM.
 */

package org.gamegineer.common.ui.wizard;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.ui.wizard.WizardDialog} class.
 */
public final class WizardDialogTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code WizardDialogTest} class.
     */
    public WizardDialogTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link WizardDialog#WizardDialog} constructor throws an
     * exception when passed a {@code null} wizard.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Wizard_Null()
    {
        new WizardDialog( null, null );
    }
}
