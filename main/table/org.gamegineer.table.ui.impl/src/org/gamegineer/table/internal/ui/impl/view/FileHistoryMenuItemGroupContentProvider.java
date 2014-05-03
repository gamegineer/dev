/*
 * FileHistoryMenuItemGroupContentProvider.java
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
 * Created on Aug 25, 2010 at 9:59:34 PM.
 */

package org.gamegineer.table.internal.ui.impl.view;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.ui.impl.model.MainModel;
import org.gamegineer.table.internal.ui.impl.util.swing.JMenuItemGroup.IContentProvider;
import org.gamegineer.table.internal.ui.impl.util.swing.JMenuItemGroup.MenuItemDescriptor;
import org.gamegineer.table.internal.ui.impl.util.swing.TextUtils;

/**
 * A content provider for the file history menu item group.
 */
@Immutable
final class FileHistoryMenuItemGroupContentProvider
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
     * Initializes a new instance of the
     * {@code FileHistoryMenuItemGroupContentProvider} class.
     * 
     * @param mainModel
     *        The main model; must not be {@code null}.
     */
    FileHistoryMenuItemGroupContentProvider(
        final MainModel mainModel )
    {
        mainModel_ = mainModel;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.impl.util.swing.JMenuItemGroup.IContentProvider#getMenuItemDescriptors()
     */
    @Override
    public Collection<MenuItemDescriptor> getMenuItemDescriptors()
    {
        final List<File> files = mainModel_.getPreferencesModel().getFileHistoryPreferences().getFiles();
        Collections.reverse( files );

        final List<String> fileNames = new ArrayList<>();
        for( final File file : files )
        {
            fileNames.add( file.getName() );
        }

        final Collection<MenuItemDescriptor> menuItemDescriptors = new ArrayList<>();
        for( int index = 0, size = files.size(); index < size; ++index )
        {
            final String path = nonNull( files.get( index ).getAbsolutePath() );
            final String fileName = fileNames.get( index );
            final String label;
            if( Collections.frequency( fileNames, fileName ) > 1 )
            {
                label = TextUtils.shortenPath( path, MAX_PATH_LENGTH );
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
