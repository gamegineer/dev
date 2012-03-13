/*
 * JFileChooser.java
 * Copyright 2008-2012 Gamegineer.org
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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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

    /**
     * Adds the default extension to the selected file if the extension is
     * absent.
     */
    private void addExtensionIfAbsent()
    {
        final File file = getSelectedFile();
        if( (file == null) || (file.getName().indexOf( '.' ) != -1) )
        {
            return;
        }

        final FileFilter fileFilter = getFileFilter();
        if( !(fileFilter instanceof FileNameExtensionFilter) )
        {
            return;
        }

        final FileNameExtensionFilter fileNameExtensionFilter = (FileNameExtensionFilter)fileFilter;
        setSelectedFile( new File( file.getAbsolutePath() + "." + fileNameExtensionFilter.getExtensions()[ 0 ] ) ); //$NON-NLS-1$
    }

    /*
     * @see javax.swing.JFileChooser#approveSelection()
     */
    @Override
    public void approveSelection()
    {
        addExtensionIfAbsent();

        final File file = getSelectedFile();
        if( file == null )
        {
            return;
        }

        final int dialogType = getDialogType();
        if( dialogType == OPEN_DIALOG )
        {
            if( !file.exists() )
            {
                warnFileNotFound( file );
                return;
            }
        }
        else if( dialogType == SAVE_DIALOG )
        {
            if( file.exists() && !confirmOverwriteFile( file ) )
            {
                return;
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
     * @return {@code true} if the user wishes to overwrite the specified file;
     *         otherwise {@code false}.
     */
    private boolean confirmOverwriteFile(
        /* @NonNull */
        final File file )
    {
        assert file != null;

        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog( //
            this, //
            NlsMessages.JFileChooser_confirmOverwriteFile_message( file ), //
            NlsMessages.JFileChooser_confirmOverwriteFile_title, //
            JOptionPane.YES_NO_OPTION, //
            JOptionPane.WARNING_MESSAGE );
    }

    /**
     * Warns the user that the specified file was not found.
     * 
     * @param file
     *        The file that was not found; must not be {@code null}.
     */
    private void warnFileNotFound(
        /* @NonNull */
        final File file )
    {
        assert file != null;

        JOptionPane.showMessageDialog( //
            this, //
            NlsMessages.JFileChooser_warnFileNotFound_message( file ), //
            getUI().getDialogTitle( this ), //
            JOptionPane.WARNING_MESSAGE );
    }
}
