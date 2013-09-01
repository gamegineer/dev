/*
 * WizardPageDataBindingAdapterTest.java
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
 * Created on Oct 16, 2010 at 11:08:17 PM.
 */

package org.gamegineer.common.ui.databinding.wizard;

import org.easymock.EasyMock;
import org.eclipse.core.databinding.DataBindingContext;
import org.gamegineer.common.ui.databinding.FakeRealm;
import org.gamegineer.common.ui.wizard.IWizardPage;
import org.junit.Test;

/**
 * A fixture for testing the {@link WizardPageDataBindingAdapter} class.
 */
public final class WizardPageDataBindingAdapterTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code WizardPageDataBindingAdapterTest} class.
     */
    public WizardPageDataBindingAdapterTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the
     * {@link WizardPageDataBindingAdapter#WizardPageDataBindingAdapter}
     * constructor throws an exception when passed a {@code null} data binding
     * context.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_DataBindingContext_Null()
    {
        new WizardPageDataBindingAdapter( EasyMock.createMock( IWizardPage.class ), null );
    }

    /**
     * Ensures the
     * {@link WizardPageDataBindingAdapter#WizardPageDataBindingAdapter}
     * constructor throws an exception when passed a {@code null} wizard page.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_WizardPage_Null()
    {
        new WizardPageDataBindingAdapter( null, new DataBindingContext( new FakeRealm() ) );
    }
}
