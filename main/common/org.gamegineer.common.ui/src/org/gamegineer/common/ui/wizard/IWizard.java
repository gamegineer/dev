/*
 * IWizard.java
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
 * Created on Sep 19, 2010 at 8:50:15 PM.
 */

package org.gamegineer.common.ui.wizard;

import java.awt.Container;
import java.util.Collection;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A wizard.
 */
public interface IWizard
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds pages to the wizard before it is displayed.
     */
    public void addPages();

    /**
     * Indicates the wizard is complete and can be finished without further user
     * interaction.
     * 
     * @return {@code true} if the wizard can finish; otherwise {@code false}.
     */
    public boolean canFinish();

    /**
     * Creates the wizard.
     * 
     * @param parent
     *        The parent container for the wizard.
     */
    public void create(
        Container parent );

    /**
     * Disposes the resources used by the wizard.
     */
    public void dispose();

    /**
     * Gets the container hosting the wizard.
     * 
     * @return The container hosting the wizard or {@code null} if the wizard
     *         has not yet been added to a container.
     */
    public @Nullable IWizardContainer getContainer();

    /**
     * Gets the successor of the specified page in the wizard sequence.
     * 
     * @param page
     *        A wizard page.
     * 
     * @return The successor of the specified page in the wizard sequence or
     *         {@code null} if none.
     */
    public @Nullable IWizardPage getNextPage(
        IWizardPage page );

    /**
     * Gets the page with specified name in the wizard.
     * 
     * @param name
     *        The page name.
     * 
     * @return The page with the specified name or {@code null} if no such page
     *         exists.
     */
    public @Nullable IWizardPage getPage(
        String name );

    /**
     * Gets the count of pages in the wizard.
     * 
     * @return The count of pages in the wizard.
     */
    public int getPageCount();

    /**
     * Gets the collection of pages in the wizard.
     * 
     * @return The collection of pages in the wizard.
     */
    public Collection<IWizardPage> getPages();

    /**
     * Gets the predecessor of the specified page in the wizard sequence.
     * 
     * @param page
     *        A wizard page.
     * 
     * @return The predecessor of the specified page in the wizard sequence or
     *         {@code null} if none.
     */
    public @Nullable IWizardPage getPreviousPage(
        IWizardPage page );

    /**
     * Gets the first page to be displayed in the wizard.
     * 
     * @return The first page to be displayed in the wizard or {@code null} if
     *         none.
     */
    public @Nullable IWizardPage getFirstPage();

    /**
     * Gets the wizard title.
     * 
     * @return The wizard title or {@code null} if the wizard has no title.
     */
    public @Nullable String getTitle();

    /**
     * Indicates the wizard needs Previous and Next buttons.
     * 
     * @return {@code true} if the wizard needs Previous and Next buttons;
     *         otherwise {@code false}.
     */
    public boolean needsPreviousAndNextButtons();

    /**
     * Indicates the wizard needs a progress monitor.
     * 
     * @return {@code true} if the wizard needs a progress monitor; otherwise
     *         {@code false}.
     */
    public boolean needsProgressMonitor();

    /**
     * Performs the appropriate actions when the wizard is cancelled.
     * 
     * @return {@code true} if the cancellation request was accepted or
     *         {@code false} if the cancellation request was refused.
     */
    public boolean performCancel();

    /**
     * Performs the appropriate actions when the wizard is finished.
     * 
     * @return {@code true} if the finish request was accepted or {@code false}
     *         if the finish request was refused.
     */
    public boolean performFinish();

    /**
     * Sets the container hosting the wizard.
     * 
     * @param container
     *        The container hosting the wizard or {@code null} to clear it.
     */
    public void setContainer(
        @Nullable IWizardContainer container );
}
