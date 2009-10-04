/*
 * TableFrame.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Sep 18, 2009 at 10:09:51 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.ui.ITableAdvisor;

/**
 * The top-level frame that encapsulates the table user interface.
 */
@NotThreadSafe
public final class TableFrame
    extends JFrame
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 1087139002992381995L;

    /** The table advisor. */
    private final ITableAdvisor advisor_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableFrame} class.
     * 
     * @param advisor
     *        The table advisor; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code advisor} is {@code null}.
     */
    public TableFrame(
        /* @NonNull */
        final ITableAdvisor advisor )
    {
        assertArgumentNotNull( advisor, "advisor" ); //$NON-NLS-1$

        advisor_ = advisor;

        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Initializes the component.
     */
    private void initializeComponent()
    {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu( Messages.TableFrame_menu_file_text );
        fileMenu.setMnemonic( Messages.toMnemonic( Messages.TableFrame_menu_file_mnemonic ) );
        final JMenuItem exitMenuItem = new JMenuItem( Messages.TableFrame_menu_file_exit_text );
        exitMenuItem.setMnemonic( Messages.toMnemonic( Messages.TableFrame_menu_file_exit_mnemonic ) );
        exitMenuItem.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                dispose();
            }
        } );
        fileMenu.add( exitMenuItem );
        menuBar.add( fileMenu );
        final JMenu helpMenu = new JMenu( Messages.TableFrame_menu_help_text );
        helpMenu.setMnemonic( Messages.toMnemonic( Messages.TableFrame_menu_help_mnemonic ) );
        final JMenuItem aboutMenuItem = new JMenuItem( Messages.TableFrame_menu_help_about_text );
        aboutMenuItem.setMnemonic( Messages.toMnemonic( Messages.TableFrame_menu_help_about_mnemonic ) );
        aboutMenuItem.addActionListener( new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                JOptionPane.showMessageDialog( TableFrame.this, Messages.TableFrame_about_message( advisor_.getApplicationVersion() ), Messages.TableFrame_about_title, JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE );
            }
        } );
        helpMenu.add( aboutMenuItem );
        menuBar.add( helpMenu );
        setJMenuBar( menuBar );

        setTitle( Messages.TableFrame_title );
        setLocationByPlatform( true );
        setSize( 300, 300 );
    }
}
