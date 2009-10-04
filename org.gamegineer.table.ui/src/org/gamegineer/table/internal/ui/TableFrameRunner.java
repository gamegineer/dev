/*
 * TableFrameRunner.java
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
 * Created on Oct 2, 2009 at 11:08:09 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.ui.ITableAdvisor;
import org.gamegineer.table.ui.ITableRunner;
import org.gamegineer.table.ui.TableResult;

/**
 * Implementation of {@link org.gamegineer.table.ui.ITableRunner} for the
 * {@code TableFrame} table user interface class.
 */
@ThreadSafe
public final class TableFrameRunner
    implements ITableRunner
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table advisor. */
    private final ITableAdvisor advisor_;

    /**
     * The running frame window or {@code null} if no frame window is running.
     * This instance must only be manipulated from the Swing event-dispatching
     * thread.
     */
    private TableFrame frame_;

    /** The latch used to signal that the frame window has closed. */
    private final CountDownLatch frameClosedLatch_;

    /**
     * Indicates the state of this runner is pristine. That is, it has never
     * been run.
     */
    private final AtomicBoolean isPristine_;

    /** The table result. */
    private final AtomicReference<TableResult> result_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableFrameRunner} class.
     * 
     * @param advisor
     *        The table advisor; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code advisor} is {@code null}.
     */
    public TableFrameRunner(
        /* @NonNull */
        final ITableAdvisor advisor )
    {
        assertArgumentNotNull( advisor, "advisor" ); //$NON-NLS-1$

        advisor_ = advisor;
        frame_ = null;
        frameClosedLatch_ = new CountDownLatch( 1 );
        isPristine_ = new AtomicBoolean( true );
        result_ = new AtomicReference<TableResult>( TableResult.OK );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.ITableRunner#call()
     */
    public TableResult call()
        throws Exception
    {
        assertStateLegal( isPristine_.compareAndSet( true, false ), Messages.TableFrameRunner_state_notPristine );

        createAndShowFrame();

        waitUntilFrameClosed();

        return result_.get();
    }

    /**
     * Closes the running {@code TableFrame} instance if it is still running.
     */
    private void closeFrame()
    {
        if( !GraphicsEnvironment.isHeadless() )
        {
            SwingUtilities.invokeLater( new Runnable()
            {
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    closeFrameWorker();
                }
            } );
        }
        else
        {
            frameClosedLatch_.countDown();
        }
    }

    /**
     * Closes the running {@code TableFrame} instance if it is still running.
     * 
     * <p>
     * This method must only be called from the Swing event dispatch thread.
     * </p>
     */
    private void closeFrameWorker()
    {
        assert SwingUtilities.isEventDispatchThread();

        if( frame_ != null )
        {
            try
            {
                frame_.dispose();
            }
            catch( final Exception e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.TableFrameRunner_closeFrameWorker_error, e );
                result_.set( TableResult.FAIL );
                frameClosedLatch_.countDown();
            }
            finally
            {
                frame_ = null;
            }
        }
    }

    /**
     * Creates and shows a new {@code TableFrame} instance if one is not already
     * running.
     */
    private void createAndShowFrame()
    {
        if( !GraphicsEnvironment.isHeadless() )
        {
            SwingUtilities.invokeLater( new Runnable()
            {
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    createAndShowFrameWorker();
                }
            } );
        }
    }

    /**
     * Creates and shows a new {@code TableFrame} instance if one is not already
     * running.
     * 
     * <p>
     * This method must only be called from the Swing event dispatch thread.
     * </p>
     */
    private void createAndShowFrameWorker()
    {
        assert SwingUtilities.isEventDispatchThread();

        if( frame_ == null )
        {
            try
            {
                final WindowListener frameClosedListener = new WindowAdapter()
                {
                    @Override
                    @SuppressWarnings( "synthetic-access" )
                    public void windowClosed(
                        @SuppressWarnings( "unused" )
                        final WindowEvent e )
                    {
                        if( Debug.DEFAULT )
                        {
                            Debug.trace( "TableFrame frame window closed." ); //$NON-NLS-1$
                        }
                        frameClosedLatch_.countDown();
                    }
                };
                frame_ = new TableFrame( advisor_ );
                frame_.addWindowListener( frameClosedListener );
                frame_.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
                frame_.setVisible( true );
            }
            catch( final Exception e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.TableFrameRunner_createAndShowFrameWorker_error, e );
                result_.set( TableResult.FAIL );
                frameClosedLatch_.countDown();
            }
        }
    }

    /**
     * Waits until the running {@code TableFrame} instance is closed.
     * 
     * @throws java.lang.InterruptedException
     *         If the current thread was interrupted while waiting and the
     *         running {@code TableFrame} instance could not be closed.
     */
    private void waitUntilFrameClosed()
        throws InterruptedException
    {
        try
        {
            frameClosedLatch_.await();
        }
        catch( final InterruptedException e )
        {
            if( Debug.DEFAULT )
            {
                Debug.trace( "TableFrame runner cancelled; closing frame window." ); //$NON-NLS-1$
            }
            closeFrame();

            frameClosedLatch_.await();
        }
    }
}
