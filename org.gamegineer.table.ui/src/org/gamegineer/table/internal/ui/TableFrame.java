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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.ui.ITableAdvisor;
import org.gamegineer.table.ui.TableResult;

// TODO: Consider splitting the runner into a separate class depending on how
// the processing of command line arguments is to be handled.

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
     *        The able advisor; must not be {@code null}.
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
        advisor_.getApplicationVersion(); // TEMP
        setTitle( Messages.TableFrame_title );
        setSize( 300, 300 );
    }

    /**
     * Creates a runner for this table.
     * 
     * @return A runner for this table; never {@code null}.
     */
    /* @NonNull */
    public Callable<TableResult> createRunner()
    {
        return new Callable<TableResult>()
        {
            @SuppressWarnings( "synthetic-access" )
            public TableResult call()
                throws Exception
            {
                return TableFrame.this.run();
            }
        };
    }

    /**
     * Runs this table.
     * 
     * @return The table result; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    private TableResult run()
        throws Exception
    {
        final CountDownLatch windowClosedLatch = new CountDownLatch( 1 );
        final WindowListener windowsClosedListener = new WindowAdapter()
        {
            @Override
            public void windowClosed(
                @SuppressWarnings( "unused" )
                final WindowEvent e )
            {
                if( Debug.DEFAULT )
                {
                    Debug.trace( "TableFrame window closed" ); //$NON-NLS-1$
                }
                windowClosedLatch.countDown();
            }
        };
        addWindowListener( windowsClosedListener );
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        setVisible( true );

        try
        {
            windowClosedLatch.await();
        }
        catch( final InterruptedException e )
        {
            if( Debug.DEFAULT )
            {
                Debug.trace( "TableFrame runner cancelled; signalling window to close" ); //$NON-NLS-1$
            }
            dispose();
            windowClosedLatch.await();
        }

        return TableResult.OK;
    }
}
