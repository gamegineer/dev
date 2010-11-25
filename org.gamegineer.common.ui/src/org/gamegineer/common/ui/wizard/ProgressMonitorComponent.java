/*
 * ProgressMonitorComponent.java
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
 * Created on Nov 12, 2010 at 10:43:20 PM.
 */

package org.gamegineer.common.ui.wizard;

import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.dialog.DialogUtils;
import org.gamegineer.common.ui.operation.RunnableTask;

/**
 * A progress monitor component used to display progress within a wizard
 * container.
 */
@NotThreadSafe
final class ProgressMonitorComponent
    extends JPanel
    implements PropertyChangeListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 5081928693810072186L;

    /** The task label. */
    private JLabel label_;

    /** The task progress indicator. */
    private JProgressBar progressBar_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ProgressMonitorComponent} class.
     */
    ProgressMonitorComponent()
    {
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
        setLayout( new BorderLayout() );

        final FontMetrics fontMetrics = getFontMetrics( getFont() );

        label_ = new JLabel();
        label_.setBorder( BorderFactory.createEmptyBorder( 0, 0, DialogUtils.convertHeightInDlusToPixels( fontMetrics, 2 ), 0 ) );
        add( label_, BorderLayout.NORTH );
        progressBar_ = new JProgressBar();
        add( progressBar_, BorderLayout.SOUTH );
    }

    /*
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    @SuppressWarnings( "boxing" )
    public void propertyChange(
        final PropertyChangeEvent event )
    {
        if( RunnableTask.PROGRESS_PROPERTY_NAME.equals( event.getPropertyName() ) )
        {
            progressBar_.setValue( (Integer)event.getNewValue() );
        }
        else if( RunnableTask.DESCRIPTION_PROPERTY_NAME.equals( event.getPropertyName() ) )
        {
            label_.setText( (String)event.getNewValue() );
        }
    }

    /*
     * @see javax.swing.JComponent#setVisible(boolean)
     */
    @Override
    public void setVisible(
        final boolean isVisible )
    {
        if( isVisible )
        {
            label_.setText( "" ); //$NON-NLS-1$
            progressBar_.setValue( 0 );
        }

        super.setVisible( isVisible );
    }
}
