/*
 * TableRunner.java
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.ui.view.MainFrame;
import org.gamegineer.table.ui.ITableAdvisor;
import org.gamegineer.table.ui.ITableRunner;
import org.gamegineer.table.ui.TableResult;

/**
 * Implementation of {@link org.gamegineer.table.ui.ITableRunner} that executes
 * the {@link org.gamegineer.table.internal.ui.view.MainFrame} table user
 * interface.
 */
@ThreadSafe
public final class TableRunner
    implements ITableRunner
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table advisor. */
    private final ITableAdvisor advisor_;

    /**
     * The running frame window or {@code null} if no frame window is running.
     * This reference must only be manipulated from the Swing event-dispatching
     * thread.
     */
    private MainFrame frame_;

    /** The table result. */
    private final AtomicReference<TableResult> result_;

    /** The runner state. */
    private final AtomicReference<State> state_;

    /** The latch used to signal that the runner has stopped. */
    private final CountDownLatch stopLatch_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableRunner} class.
     * 
     * @param advisor
     *        The table advisor; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code advisor} is {@code null}.
     */
    public TableRunner(
        /* @NonNull */
        final ITableAdvisor advisor )
    {
        assertArgumentNotNull( advisor, "advisor" ); //$NON-NLS-1$

        advisor_ = advisor;
        frame_ = null;
        result_ = new AtomicReference<TableResult>( TableResult.OK );
        state_ = new AtomicReference<State>( State.PRISTINE );
        stopLatch_ = new CountDownLatch( 1 );
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
        assertStateLegal( state_.compareAndSet( State.PRISTINE, State.STARTING ), Messages.TableRunner_state_notPristine );

        try
        {
            openFrameAsync();

            stopLatch_.await();
        }
        catch( final InterruptedException e )
        {
            if( Debug.DEFAULT )
            {
                Debug.trace( "Table runner cancelled." ); //$NON-NLS-1$
            }
        }
        finally
        {
            state_.set( State.STOPPED );

            closeFrameAsync();
        }

        return result_.get();
    }

    /**
     * Closes the running frame.
     * 
     * <p>
     * This method must only be called from the Swing event dispatch thread.
     * </p>
     */
    private void closeFrame()
    {
        assert SwingUtilities.isEventDispatchThread();

        if( frame_ != null )
        {
            if( frame_.isDisplayable() )
            {
                frame_.dispose();
            }
            frame_ = null;
        }
    }

    /**
     * Asynchronously closes the running frame on the Swing event dispatch
     * thread.
     */
    private void closeFrameAsync()
    {
        safeSwingInvokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                closeFrame();
            }
        } );
    }

    /**
     * Opens a new frame.
     * 
     * <p>
     * This method must only be called from the Swing event dispatch thread.
     * </p>
     */
    private void openFrame()
    {
        assert SwingUtilities.isEventDispatchThread();
        assert frame_ == null;

        try
        {
            final WindowListener windowListener = new WindowAdapter()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void windowClosed(
                    @SuppressWarnings( "unused" )
                    final WindowEvent e )
                {
                    if( Debug.DEFAULT )
                    {
                        Debug.trace( "Frame window closed by user." ); //$NON-NLS-1$
                    }
                    stop( TableResult.OK );
                }
            };
            frame_ = new MainFrame( advisor_ );
            frame_.addWindowListener( windowListener );
            frame_.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
            frame_.setVisible( true );

            start();
        }
        catch( final Exception e )
        {
            Loggers.DEFAULT.log( Level.SEVERE, Messages.TableRunner_openFrame_error, e );
            stop( TableResult.FAIL );
        }
    }

    /**
     * Asynchronously opens a new frame on the Swing event dispatch thread.
     */
    private void openFrameAsync()
    {
        safeSwingInvokeLater( new Runnable()
        {
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                openFrame();
            }
        } );
    }

    /**
     * Safely invokes the specified task asynchronously on the Swing event
     * dispatch thread.
     * 
     * <p>
     * This method is required only because
     * {@link SwingUtilities#invokeLater(Runnable)} is not well-behaved if the
     * thread is interrupted while it is executing. When interrupted, it simply
     * swallows the {@code InterruptedException} and does not reset the thread
     * interrupted status. This can lead to a situation where this task does not
     * know that it's been cancelled in the event the interruption just happens
     * to occur while {@code invokeLater} is executing.
     * </p>
     * 
     * <p>
     * Therefore, to work around this use case, we execute {@code invokeLater}
     * on a thread other than the task's thread so it will never see the
     * interruption caused by the executor service in the event of task
     * cancellation.
     * </p>
     * 
     * @param runnable
     *        The task to execute on the Swing event dispatch thread; must not
     *        be {@code null}.
     */
    private static void safeSwingInvokeLater(
        /* @NonNull */
        final Runnable runnable )
    {
        assert runnable != null;

        final Runnable runnableProxy = new Runnable()
        {
            public void run()
            {
                SwingUtilities.invokeLater( runnable );
            }
        };
        new Thread( runnableProxy, "TableRunner-SafeSwingInvokeLater" ).start(); //$NON-NLS-1$
    }

    /**
     * Moves this runner to the {@code STARTED} state.
     * 
     * <p>
     * This method must only be called from the Swing event dispatch thread.
     * </p>
     */
    private void start()
    {
        assert SwingUtilities.isEventDispatchThread();

        // It's possible the runner has already been cancelled by the time we
        // get here.  Therefore, we need to detect such a case and close the
        // frame window that was just opened.
        if( !state_.compareAndSet( State.STARTING, State.STARTED ) )
        {
            closeFrame();
        }
    }

    /**
     * Moves this runner to the {@code STOPPING} state.
     * 
     * <p>
     * This method must only be called from the Swing event dispatch thread.
     * </p>
     * 
     * @param result
     *        The table result; must not be {@code null}.
     */
    private void stop(
        /* @NonNull */
        final TableResult result )
    {
        assert SwingUtilities.isEventDispatchThread();
        assert result != null;

        result_.set( result );
        state_.set( State.STOPPING );
        stopLatch_.countDown();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The state of the runner.
     */
    private enum State
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The runner has never been started. */
        PRISTINE,

        /** The runner is starting. */
        STARTING,

        /** The runner has started. */
        STARTED,

        /** The runner is stopping. */
        STOPPING,

        /** The runner has stopped. */
        STOPPED
    }
}