/*
 * IDialogPage.java
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
 * Created on Sep 20, 2010 at 10:21:51 PM.
 */

package org.gamegineer.common.ui.dialog;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A page in a multi-page dialog.
 */
public interface IDialogPage
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the dialog page.
     * 
     * <p>
     * The top-level component created by this method must be accessible via
     * {@link #getContent()}.
     * </p>
     * 
     * @param parent
     *        The parent container for the dialog page.
     */
    public void create(
        Container parent );

    /**
     * Disposes the resources used by the dialog page.
     */
    public void dispose();

    /**
     * Gets the top-level component encapsulating the dialog page content.
     * 
     * @return The top-level component encapsulating the dialog page content or
     *         {@code null} if the dialog page content has not yet been created.
     */
    public @Nullable Component getContent();

    /**
     * Gets the dialog page description.
     * 
     * @return The dialog page description or {@code null} if the dialog page
     *         has no description.
     */
    public @Nullable String getDescription();

    /**
     * Gets the dialog page message.
     * 
     * @return The dialog page message or {@code null} if the dialog page has no
     *         message.
     */
    public @Nullable DialogMessage getMessage();

    /**
     * Gets the dialog page shell.
     * 
     * @return The dialog page shell or {@code null} if the dialog page has no
     *         shell.
     */
    public @Nullable Window getShell();

    /**
     * Gets the dialog page title.
     * 
     * @return The dialog page title or {@code null} if the dialog page has no
     *         title.
     */
    public @Nullable String getTitle();

    /**
     * Sets the dialog page message.
     * 
     * @param message
     *        The dialog page message or {@code null} to clear the message.
     */
    public void setMessage(
        @Nullable DialogMessage message );

    /**
     * Sets the visibility of the dialog page.
     * 
     * @param isVisible
     *        {@code true} to make the dialog page visible or {@code false} to
     *        hide it.
     */
    public void setVisible(
        boolean isVisible );
}
