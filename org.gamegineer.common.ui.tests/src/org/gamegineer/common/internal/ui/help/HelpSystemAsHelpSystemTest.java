/*
 * HelpSystemAsHelpSystemTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jan 4, 2012 at 8:33:37 PM.
 */

package org.gamegineer.common.internal.ui.help;

import org.gamegineer.common.ui.help.AbstractHelpSystemTestCase;
import org.gamegineer.common.ui.help.IHelpSystem;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.ui.help.HelpSystem} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.common.ui.help.IHelpSystem} interface.
 */
public final class HelpSystemAsHelpSystemTest
    extends AbstractHelpSystemTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelpSystemAsHelpSystemTest}
     * class.
     */
    public HelpSystemAsHelpSystemTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.help.AbstractHelpSystemTestCase#createHelpSystem()
     */
    @Override
    protected IHelpSystem createHelpSystem()
    {
        return new HelpSystem();
    }
}
