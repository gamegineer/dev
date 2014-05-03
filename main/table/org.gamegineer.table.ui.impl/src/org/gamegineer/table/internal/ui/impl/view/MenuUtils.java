/*
 * MenuUtils.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Dec 27, 2011 at 8:19:09 PM.
 */

package org.gamegineer.table.internal.ui.impl.view;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A collection of useful methods for menus.
 */
@ThreadSafe
final class MenuUtils
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default menu listener. */
    private static final MenuListener DEFAULT_MENU_LISTENER = createDefaultMenuListener();

    /** The default popup menu listener. */
    private static final PopupMenuListener DEFAULT_POPUP_MENU_LISTENER = createDefaultPopupMenuListener();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MenuUtils} class.
     */
    private MenuUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the default menu listener.
     * 
     * @return The default menu listener; never {@code null}.
     */
    private static MenuListener createDefaultMenuListener()
    {
        return new MenuListener()
        {
            @Override
            public void menuCanceled(
                @Nullable
                @SuppressWarnings( "unused" )
                final MenuEvent event )
            {
                // do nothing
            }

            @Override
            public void menuDeselected(
                @Nullable
                @SuppressWarnings( "unused" )
                final MenuEvent event )
            {
                // do nothing
            }

            @Override
            public void menuSelected(
                @Nullable
                @SuppressWarnings( "unused" )
                final MenuEvent event )
            {
                Actions.updateAll();
            }
        };
    }

    /**
     * Creates the default popup menu listener.
     * 
     * @return The default popup menu listener; never {@code null}.
     */
    private static PopupMenuListener createDefaultPopupMenuListener()
    {
        return new PopupMenuListener()
        {
            @Override
            public void popupMenuCanceled(
                @Nullable
                @SuppressWarnings( "unused" )
                final PopupMenuEvent event )
            {
                // do nothing
            }

            @Override
            public void popupMenuWillBecomeInvisible(
                @Nullable
                @SuppressWarnings( "unused" )
                final PopupMenuEvent event )
            {
                // do nothing
            }

            @Override
            public void popupMenuWillBecomeVisible(
                @Nullable
                @SuppressWarnings( "unused" )
                final PopupMenuEvent event )
            {
                Actions.updateAll();
            }
        };
    }

    /**
     * Gets the default menu listener.
     * 
     * <p>
     * The default menu listener ensures the state of all actions is updated
     * before the menu is displayed.
     * </p>
     * 
     * @return The default menu listener; never {@code null}.
     */
    static MenuListener getDefaultMenuListener()
    {
        return DEFAULT_MENU_LISTENER;
    }

    /**
     * Gets the default popup menu listener.
     * 
     * <p>
     * The default popup menu listener ensures the state of all actions is
     * updated before the popup menu is displayed.
     * </p>
     * 
     * @return The default popup menu listener; never {@code null}.
     */
    static PopupMenuListener getDefaultPopupMenuListener()
    {
        return DEFAULT_POPUP_MENU_LISTENER;
    }
}
