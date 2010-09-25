/*
 * AbstractWizardAsWizardTest.java
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
 * Created on Sep 24, 2010 at 9:00:48 PM.
 */

package org.gamegineer.common.ui.wizard;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.ui.wizard.AbstractWizard} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.common.ui.wizard.IWizard} interface.
 */
public final class AbstractWizardAsWizardTest
    extends AbstractWizardTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractWizardAsWizardTest}
     * class.
     */
    public AbstractWizardAsWizardTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.wizard.AbstractWizardTestCase#createWizard()
     */
    @Override
    protected IWizard createWizard()
    {
        return new AbstractWizard()
        {
            // no overrides
        };
    }
}
