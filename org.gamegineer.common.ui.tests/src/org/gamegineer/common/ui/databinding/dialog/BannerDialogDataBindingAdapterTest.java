/*
 * BannerDialogDataBindingAdapterTest.java
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
 * Created on Sep 18, 2011 at 4:52:57 PM.
 */

package org.gamegineer.common.ui.databinding.dialog;

import org.eclipse.core.databinding.DataBindingContext;
import org.gamegineer.common.ui.databinding.FakeRealm;
import org.gamegineer.common.ui.dialog.AbstractBannerDialog;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.ui.databinding.dialog.BannerDialogDataBindingAdapter}
 * class.
 */
public final class BannerDialogDataBindingAdapterTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * BannerDialogDataBindingAdapterTest} class.
     */
    public BannerDialogDataBindingAdapterTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new banner dialog stub.
     * 
     * @return A new banner dialog stub; never {@code null}.
     */
    /* @NonNull */
    private static AbstractBannerDialog createBannerDialogStub()
    {
        return new AbstractBannerDialog( null )
        {
            // no overrides
        };
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * banner dialog.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_BannerDialog_Null()
    {
        new BannerDialogDataBindingAdapter( null, new DataBindingContext( new FakeRealm() ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * data binding context.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_DataBindingContext_Null()
    {
        new BannerDialogDataBindingAdapter( createBannerDialogStub(), null );
    }
}
