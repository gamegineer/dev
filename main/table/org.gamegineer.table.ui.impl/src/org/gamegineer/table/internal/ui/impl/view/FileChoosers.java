/*
 * FileChoosers.java
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
 * Created on Oct 5, 2010 at 9:40:32 PM.
 */

package org.gamegineer.table.internal.ui.impl.view;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.ui.impl.util.swing.JFileChooser;

/**
 * The collection of file choosers available to all views.
 */
@ThreadSafe
final class FileChoosers
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FileChoosers} class.
     */
    private FileChoosers()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the table file chooser.
     * 
     * @param file
     *        The file used to initialize the file chooser.
     * 
     * @return The table file chooser.
     */
    static JFileChooser getTableFileChooser(
        final @Nullable File file )
    {
        final JFileChooser fileChooser = new JFileChooser( file );
        final FileFilter tableFileFilter = new FileNameExtensionFilter( NlsMessages.FileChoosers_fileFilter_table, "ser" ); //$NON-NLS-1$
        fileChooser.addChoosableFileFilter( tableFileFilter );
        fileChooser.setFileFilter( tableFileFilter );
        return fileChooser;
    }
}
