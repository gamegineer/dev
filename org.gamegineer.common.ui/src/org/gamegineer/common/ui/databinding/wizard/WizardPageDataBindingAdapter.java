/*
 * WizardPageDataBindingAdapter.java
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
 * Created on Oct 16, 2010 at 11:06:21 PM.
 */

package org.gamegineer.common.ui.databinding.wizard;

import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.DataBindingContext;
import org.gamegineer.common.ui.databinding.dialog.DialogPageDataBindingAdapter;
import org.gamegineer.common.ui.wizard.IWizardPage;

/**
 * Adapts the validation result from a data binding context to a wizard page.
 */
@NotThreadSafe
public class WizardPageDataBindingAdapter
    extends DialogPageDataBindingAdapter
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code WizardPageDataBindingAdapter}
     * class.
     * 
     * @param wizardPage
     *        The wizard page; must not be {@code null}.
     * @param dataBindingContext
     *        The data binding context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code wizardPage} or {@code dataBindingContext} is {@code
     *         null}.
     */
    public WizardPageDataBindingAdapter(
        /* @NonNull */
        final IWizardPage wizardPage,
        /* @NonNull */
        final DataBindingContext dataBindingContext )
    {
        super( wizardPage, dataBindingContext );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the wizard page.
     * 
     * @return The wizard page; never {@code null}.
     */
    /* @NonNull */
    protected final IWizardPage getWizardPage()
    {
        return (IWizardPage)getDialogPage();
    }

    /*
     * @see org.gamegineer.common.ui.databinding.dialog.DialogPageDataBindingAdapter#handleValidationStatusChanged()
     */
    @Override
    protected void handleValidationStatusChanged()
    {
        super.handleValidationStatusChanged();

        getWizardPage().setComplete( !isCurrentValidationStatusFatal() );
    }
}
