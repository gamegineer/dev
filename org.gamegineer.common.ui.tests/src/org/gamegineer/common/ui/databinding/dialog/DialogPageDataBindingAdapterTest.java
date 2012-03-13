/*
 * DialogPageDataBindingAdapterTest.java
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
 * Created on Oct 16, 2010 at 9:33:07 PM.
 */

package org.gamegineer.common.ui.databinding.dialog;

import org.easymock.EasyMock;
import org.eclipse.core.databinding.DataBindingContext;
import org.gamegineer.common.ui.databinding.FakeRealm;
import org.gamegineer.common.ui.dialog.IDialogPage;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.ui.databinding.dialog.DialogPageDataBindingAdapter}
 * class.
 */
public final class DialogPageDataBindingAdapterTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code DialogPageDataBindingAdapterTest} class.
     */
    public DialogPageDataBindingAdapterTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * data binding context.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_DataBindingContext_Null()
    {
        new DialogPageDataBindingAdapter( EasyMock.createMock( IDialogPage.class ), null );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * dialog page.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_DialogPage_Null()
    {
        new DialogPageDataBindingAdapter( null, new DataBindingContext( new FakeRealm() ) );
    }
}
