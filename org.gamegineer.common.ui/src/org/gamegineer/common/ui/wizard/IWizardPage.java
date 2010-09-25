/*
 * IWizardPage.java
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
 * Created on Sep 20, 2010 at 10:19:10 PM.
 */

package org.gamegineer.common.ui.wizard;

import org.gamegineer.common.ui.dialog.IDialogPage;

/**
 * A page in a wizard.
 */
public interface IWizardPage
    extends IDialogPage
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates the wizard can move to the next page.
     * 
     * @return {@code true} if the wizard can move to the next page; otherwise
     *         {@code false}.
     */
    public boolean canMoveToNextPage();

    /**
     * Gets the unique name of the page within the wizard.
     * 
     * @return The unique name of the page within the wizard; never {@code null}
     *         .
     */
    /* @NonNull */
    public String getName();

    /**
     * Gets the next page in the wizard sequence.
     * 
     * @return The next page in the wizard sequence or {@code null} if none.
     */
    /* @Nullable */
    public IWizardPage getNextPage();

    /**
     * Gets the previous page in the wizard sequence.
     * 
     * @return The previous page in the wizard sequence or {@code null} if none.
     */
    /* @Nullable */
    public IWizardPage getPreviousPage();

    /**
     * Gets the wizard that hosts the page.
     * 
     * @return The wizard that hosts the page or {@code null} if the page has
     *         not yet been added to a wizard.
     */
    /* @Nullable */
    public IWizard getWizard();

    /**
     * Indicates the page is complete.
     * 
     * <p>
     * This information is typically used by the wizard to help it decide when
     * the wizard is finished.
     * </p>
     * 
     * @return {@code true} if the page is complete; otherwise {@code false}.
     */
    public boolean isComplete();

    /**
     * Sets the previous page in the wizard sequence.
     * 
     * <p>
     * This method should only be invoked by the wizard container.
     * </p>
     * 
     * @param page
     *        The previous page in the wizard sequence or {@code null} if none.
     */
    public void setPreviousPage(
        /* @Nullable */
        IWizardPage page );

    /**
     * Sets the wizard that hosts the page.
     * 
     * <p>
     * Once associated with a wizard, a page cannot be hosted by a different
     * wizard.
     * </p>
     * 
     * @param wizard
     *        The wizard that hosts the page; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the page is already hosted by a wizard.
     * @throws java.lang.NullPointerException
     *         If {@code wizard} is {@code null}.
     */
    public void setWizard(
        /* @NonNull */
        IWizard wizard );
}
