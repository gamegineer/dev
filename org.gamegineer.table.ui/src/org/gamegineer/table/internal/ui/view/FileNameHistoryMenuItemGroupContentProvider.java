/*
 * FileNameHistoryMenuItemGroupContentProvider.java
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
 * Created on Aug 25, 2010 at 9:59:34 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.ui.model.MainModel;
import org.gamegineer.table.internal.ui.util.swing.SwingTextUtilities;
import org.gamegineer.table.internal.ui.util.swing.JMenuItemGroup.IContentProvider;
import org.gamegineer.table.internal.ui.util.swing.JMenuItemGroup.MenuItemDescriptor;

/**
 * A content provider for the file name history menu item group.
 */
@Immutable
final class FileNameHistoryMenuItemGroupContentProvider
    implements IContentProvider
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The maximum path length to display in characters. */
    private static final int MAX_PATH_LENGTH = 32;

    /** The main model. */
    private final MainModel mainModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * FileNameHistoryMenuItemGroupContentProvider} class.
     * 
     * @param mainModel
     *        The main model; must not be {@code null}.
     */
    FileNameHistoryMenuItemGroupContentProvider(
        /* @NonNull */
        final MainModel mainModel )
    {
        assert mainModel != null;

        mainModel_ = mainModel;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.util.swing.JMenuItemGroup.IContentProvider#getMenuItemDescriptors()
     */
    @Override
    public Collection<MenuItemDescriptor> getMenuItemDescriptors()
    {
        final List<String> paths = mainModel_.getPreferencesModel().getFileNameHistoryPreferences().getFileNames();
        Collections.reverse( paths );

        final List<String> fileNames = new ArrayList<String>();
        for( final String path : paths )
        {
            fileNames.add( new File( path ).getName() );
        }

        final Collection<MenuItemDescriptor> menuItemDescriptors = new ArrayList<MenuItemDescriptor>();
        for( int index = 0, size = paths.size(); index < size; ++index )
        {
            final String path = paths.get( index );
            final String fileName = fileNames.get( index );
            final String label;
            if( Collections.frequency( fileNames, fileName ) > 1 )
            {
                label = SwingTextUtilities.shortenPath( path, MAX_PATH_LENGTH );
            }
            else
            {
                label = fileName;
            }

            menuItemDescriptors.add( new MenuItemDescriptor( label, path ) );
        }

        return menuItemDescriptors;
    }
}
