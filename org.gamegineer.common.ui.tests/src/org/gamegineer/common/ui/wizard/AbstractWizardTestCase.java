/*
 * AbstractWizardTestCase.java
 * Copyright 2008-2011 Gamegineer.org
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

package org.gamegineer.common.ui.wizard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.Collection;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.common.ui.wizard.IWizard} class.
 */
public abstract class AbstractWizardTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The wizard under test in the fixture. */
    private IWizard wizard_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractWizardTestCase} class.
     */
    protected AbstractWizardTestCase()
    {
        super();
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
    /* @NonNull */
    protected abstract IWizard createWizard()
        throws Exception;

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
        wizard_ = createWizard();
        assertNotNull( wizard_ );
    }

    /**
     * Ensures the {@code create} method throws an exception when passed a
     * {@code null} parent.
     */
    @Test( expected = NullPointerException.class )
    public void testCreate_Parent_Null()
    {
        wizard_.create( null );
    }

    /**
     * Ensures the {@code getNextPage} method throws an exception when passed a
     * {@code null} page.
     */
    @Test( expected = NullPointerException.class )
    public void testGetNextPage_Page_Null()
    {
        wizard_.getNextPage( null );
    }

    /**
     * Ensures the {@code getPage} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetPage_Name_Null()
    {
        wizard_.getPage( null );
    }

    /**
     * Ensures the {@code getPages} method returns a copy of the wizard page
     * collection.
     */
    @Test
    public void testGetPages_ReturnValue_Copy()
    {
        final Collection<IWizardPage> pages = wizard_.getPages();
        final Collection<IWizardPage> expectedPages = new ArrayList<IWizardPage>( pages );
        pages.add( EasyMock.createMock( IWizardPage.class ) );

        final Collection<IWizardPage> actualPages = wizard_.getPages();

        assertEquals( expectedPages, actualPages );
    }

    /**
     * Ensures the {@code getPreviousPage} method throws an exception when
     * passed a {@code null} page.
     */
    @Test( expected = NullPointerException.class )
    public void testGetPreviousPage_Page_Null()
    {
        wizard_.getPreviousPage( null );
    }
}
