/*
 * ProgressMonitorComponent.java
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
 * Created on Nov 12, 2010 at 10:43:20 PM.
 */

package org.gamegineer.common.ui.wizard;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.ui.layout.PixelConverter;
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

    /** The component controls. */
    @Nullable
    private Controls controls_;

    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ProgressMonitorComponent} class.
     */
    ProgressMonitorComponent()
    {
        controls_ = null;

        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component controls.
     * 
     * @return The component controls; never {@code null}.
     */
    private Controls getControls()
    {
        assert controls_ != null;
        return controls_;
    }

    /**
     * Initializes the component.
     */
    private void initializeComponent()
    {
        setLayout( new BorderLayout() );

        final PixelConverter pixelConverter = new PixelConverter( this );

        final JLabel label = new JLabel();
        label.setBorder( BorderFactory.createEmptyBorder( 0, 0, pixelConverter.convertHeightInDlusToPixels( 2 ), 0 ) );
        add( label, BorderLayout.NORTH );
        final JProgressBar progressBar = new JProgressBar();
        add( progressBar, BorderLayout.SOUTH );

        controls_ = new Controls( label, progressBar );
    }

    /*
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    @SuppressWarnings( "boxing" )
    public void propertyChange(
        @Nullable
        final PropertyChangeEvent event )
    {
        assert event != null;

        final Controls controls = getControls();

        final String propertyName = event.getPropertyName();
        if( RunnableTask.PROGRESS_PROPERTY_NAME.equals( propertyName ) )
        {
            controls.progressBar.setValue( (Integer)event.getNewValue() );
        }
        else if( RunnableTask.PROGRESS_INDETERMINATE_PROPERTY_NAME.equals( propertyName ) )
        {
            controls.progressBar.setIndeterminate( (Boolean)event.getNewValue() );
        }
        else if( RunnableTask.DESCRIPTION_PROPERTY_NAME.equals( propertyName ) )
        {
            controls.label.setText( (String)event.getNewValue() );
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
            final Controls controls = getControls();
            controls.label.setText( "" ); //$NON-NLS-1$
            controls.progressBar.setValue( 0 );
        }

        super.setVisible( isVisible );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The component controls.
     */
    @Immutable
    private static final class Controls
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The task label. */
        final JLabel label;

        /** The task progress indicator. */
        final JProgressBar progressBar;

        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Controls} class.
         * 
         * @param label
         *        The task label; must not be {@code null}.
         * @param progressBar
         *        The task progress indicator; must not be {@code null}.
         */
        Controls(
            final JLabel label,
            final JProgressBar progressBar )
        {
            this.label = label;
            this.progressBar = progressBar;
        }
    }
}
