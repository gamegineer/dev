/*
 * AbstractWizardTestCase.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Sep 24, 2010 at 8:56:17 PM.
 */

package org.gamegineer.common.ui.wizard.test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.easymock.EasyMock;
import org.gamegineer.common.ui.wizard.IWizard;
import org.gamegineer.common.ui.wizard.IWizardPage;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IWizard} interface.
 */
public abstract class AbstractWizardTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The wizard under test in the fixture. */
    private Optional<IWizard> wizard_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractWizardTestCase} class.
     */
    protected AbstractWizardTestCase()
    {
        wizard_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the wizard to be tested.
     * 
     * @return The wizard to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract IWizard createWizard()
        throws Exception;

    /**
     * Gets the wizard under test in the fixture.
     * 
     * @return The wizard under test in the fixture; never {@code null}.
     */
    protected final IWizard getWizard()
    {
        return wizard_.get();
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        wizard_ = Optional.of( createWizard() );
    }

    /**
     * Ensures the {@link IWizard#getPages} method returns a copy of the wizard
     * page collection.
     */
    @Test
    public void testGetPages_ReturnValue_Copy()
    {
        final IWizard wizard = getWizard();
        final Collection<IWizardPage> pages = wizard.getPages();
        final Collection<IWizardPage> expectedPages = new ArrayList<>( pages );
        pages.add( EasyMock.createMock( IWizardPage.class ) );

        final Collection<IWizardPage> actualPages = wizard.getPages();

        assertEquals( expectedPages, actualPages );
    }
}
