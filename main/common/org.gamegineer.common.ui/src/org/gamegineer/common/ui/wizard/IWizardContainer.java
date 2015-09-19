/*
 * IWizardContainer.java
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
 * Created on Sep 20, 2010 at 10:27:12 PM.
 */

package org.gamegineer.common.ui.wizard;

import java.awt.Window;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.ui.operation.IRunnableContext;

/**
 * The user interface container that hosts a wizard.
 */
public interface IWizardContainer
    extends IRunnableContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Activates the specified page in the container.
     * 
     * @param page
     *        A wizard page; must not be {@code null}.
     */
    public void activatePage(
        IWizardPage page );

    /**
     * Presses the container Back button.
     * 
     * <p>
     * This method does nothing if the Back button does not exist or is not
     * enabled.
     * </p>
     */
    public void back();

    /**
     * Presses the container Cancel button.
     * 
     * <p>
     * This method does nothing if the Cancel button does not exist or is not
     * enabled.
     * </p>
     */
    public void cancel();

    /**
     * Presses the container Finish button.
     * 
     * <p>
     * This method does nothing if the Finish button does not exist or is not
     * enabled.
     * </p>
     */
    public void finish();

    /**
     * Gets the active page displayed in the container.
     * 
     * @return The active page displayed in the container or {@code null} if no
     *         page is active.
     */
    public @Nullable IWizardPage getActivePage();

    /**
     * Gets the shell hosting the container.
     * 
     * @return The shell hosting container or {@code null} if the container does
     *         not have a shell.
     */
    public @Nullable Window getShell();

    /**
     * Presses the container Next button.
     * 
     * <p>
     * This method does nothing if the Next button does not exist or is not
     * enabled.
     * </p>
     */
    public void next();

    /**
     * Updates the container banner (title, description, and image) based on the
     * state of the active page.
     */
    public void updateBanner();

    /**
     * Updates the container buttons based on the state of the active page.
     */
    public void updateButtons();

    /**
     * Updates the container message based on the state of the active page.
     */
    public void updateMessage();

    /**
     * Updates the shell hosting the container based on the state of the active
     * wizard.
     */
    public void updateShell();
}
