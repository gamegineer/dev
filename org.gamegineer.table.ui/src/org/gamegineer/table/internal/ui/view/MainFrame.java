/*
 * MainFrame.java
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

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.model.MainModel;
import org.gamegineer.table.ui.ITableAdvisor;

/**
 * The top-level frame.
 */
@NotThreadSafe
public final class MainFrame
    extends JFrame
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 1087139002992381995L;

    /** The model. */
    private final MainModel model_;

    /** The main view. */
    private final MainView mainView_;

    /** The menu bar view. */
    private final MenuBarView menuBarView_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainFrame} class.
     * 
     * @param advisor
     *        The table advisor; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code advisor} is {@code null}.
     */
    public MainFrame(
        /* @NonNull */
        final ITableAdvisor advisor )
    {
        assertArgumentNotNull( advisor, "advisor" ); //$NON-NLS-1$

        model_ = new MainModel( advisor );
        mainView_ = new MainView( model_ );
        menuBarView_ = new MenuBarView( model_ );

        initializeComponent();

        registerActionListeners();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        setJMenuBar( menuBarView_.getMenuBar() );

        setContentPane( mainView_ );

        setTitle( Messages.MainFrame_title );
        setLocationByPlatform( true );
        setSize( 300, 300 );
    }

    /**
     * Registers the action listeners for this component.
     */
    private void registerActionListeners()
    {
        Actions.getExitAction().addActionListener( new ActionListener()
        {
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                dispose();
            }
        } );

        Actions.getOpenAboutDialogAction().addActionListener( new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent e )
            {
                JOptionPane.showMessageDialog( MainFrame.this, Messages.AboutDialog_message( model_.getVersion() ), Messages.AboutDialog_title, JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE );
            }
        } );
    }
}
