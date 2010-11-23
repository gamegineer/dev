/*
 * IWizardContainer.java
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
 * Created on Sep 20, 2010 at 10:27:12 PM.
 */

package org.gamegineer.common.ui.wizard;

import java.awt.Window;

/**
 * The user interface container that hosts a wizard.
 */
public interface IWizardContainer
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Activates the specified page in the container.
     * 
     * @param page
     *        A wizard page; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code page} is {@code null}.
     */
    public void activatePage(
        /* @NonNull */
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
     * Executes the specified wizard task asynchronously.
     * 
     * <p>
     * If the task is not cancelled and completes without error, the task result
     * is interpreted as a wizard button identifier. If the result is not
     * {@code null}, the specified button is pressed just as if the user had
     * pressed it. If the result is {@code null}, no action is taken.
     * </p>
     * 
     * @param task
     *        The wizard task to execute; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code task} is {@code null}.
     */
    public void executeTask(
        /* @NonNull */
        WizardTask<?, ?> task );

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
    /* @Nullable */
    public IWizardPage getActivePage();

    /**
     * Gets the shell hosting the container.
     * 
     * @return The shell hosting container or {@code null} if the container does
     *         not have a shell.
     */
    /* @Nullable */
    public Window getShell();

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
