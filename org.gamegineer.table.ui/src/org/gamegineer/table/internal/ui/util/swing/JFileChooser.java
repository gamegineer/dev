/*
 * JFileChooser.java
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
 * Created on May 13, 2010 at 10:44:42 PM.
 */

package org.gamegineer.table.internal.ui.util.swing;

import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import net.jcip.annotations.NotThreadSafe;

/**
 * A specialization of {@code JFileChooser} that provides standard features that
 * should have been present in the base class.
 */
@NotThreadSafe
public final class JFileChooser
    extends javax.swing.JFileChooser
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -745517225565769929L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code JFileChooser} class using the
     * user's default directory as the current directory and the default file
     * system view.
     */
    public JFileChooser()
    {
        super();
    }

    /**
     * Initializes a new instance of the {@code JFileChooser} class using the
     * specified current directory and the default file system view.
     * 
     * @param currentDirectory
     *        The current directory or {@code null} to use the user's default
     *        directory.
     */
    public JFileChooser(
        /* @Nullable */
        final File currentDirectory )
    {
        super( currentDirectory );
    }

    /**
     * Initializes a new instance of the {@code JFileChooser} class using the
     * specified current directory and the specified file system view.
     * 
     * @param currentDirectory
     *        The current directory or {@code null} to use the user's default
     *        directory.
     * @param fileSystemView
     *        The file system view or {@code null} to use the default file
     *        system view.
     */
    public JFileChooser(
        /* @Nullable */
        final File currentDirectory,
        /* @Nullable */
        final FileSystemView fileSystemView )
    {
        super( currentDirectory, fileSystemView );
    }

    /**
     * Initializes a new instance of the {@code JFileChooser} class using the
     * specified file system view and the user's default directory as the
     * current directory.
     * 
     * @param fileSystemView
     *        The file system view or {@code null} to use the default file
     *        system view.
     */
    public JFileChooser(
        /* @Nullable */
        final FileSystemView fileSystemView )
    {
        super( fileSystemView );
    }

    /**
     * Initializes a new instance of the {@code JFileChooser} class using the
     * specified current directory and the default file system view.
     * 
     * @param currentDirectoryPath
     *        The current directory or {@code null} to use the user's default
     *        directory.
     */
    public JFileChooser(
        /* @Nullable */
        final String currentDirectoryPath )
    {
        super( currentDirectoryPath );
    }

    /**
     * Initializes a new instance of the {@code JFileChooser} class using the
     * specified current directory and the specified file system view.
     * 
     * @param currentDirectoryPath
     *        The current directory or {@code null} to use the user's default
     *        directory.
     * @param fileSystemView
     *        The file system view or {@code null} to use the default file
     *        system view.
     */
    public JFileChooser(
        /* @Nullable */
        final String currentDirectoryPath,
        /* @Nullable */
        final FileSystemView fileSystemView )
    {
        super( currentDirectoryPath, fileSystemView );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see javax.swing.JFileChooser#approveSelection()
     */
    @Override
    public void approveSelection()
    {
        if( getDialogType() == SAVE_DIALOG )
        {
            final File selectedFile = getSelectedFile();
            if( (selectedFile != null) && selectedFile.exists() )
            {
                if( confirmOverwriteFile( selectedFile ) != JOptionPane.YES_OPTION )
                {
                    return;
                }
            }
        }

        super.approveSelection();
    }

    /**
     * Prompts the user to overwrite the specified file.
     * 
     * @param file
     *        The file to be overwritten; must not be {@code null}.
     * 
     * @return {@code JOptionPane.YES_OPTION} if the user wishes to overwrite
     *         the specified file; otherwise {@code JOptionPane.NO_OPTION}.
     */
    private int confirmOverwriteFile(
        /* @NonNull */
        final File file )
    {
        assert file != null;

        return JOptionPane.showConfirmDialog( this, Messages.JFileChooser_confirmOverwriteFile_message( file ), Messages.JFileChooser_confirmOverwriteFile_title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE );
    }
}
