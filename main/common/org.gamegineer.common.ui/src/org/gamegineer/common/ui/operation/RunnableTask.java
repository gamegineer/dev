/*
 * RunnableTask.java
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
 * Created on Nov 17, 2010 at 10:24:27 PM.
 */

package org.gamegineer.common.ui.operation;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingWorker;
import net.jcip.annotations.ThreadSafe;

/**
 * An operation to be executed asynchronously within a user interface component.
 * 
 * <p>
 * A task that is interruptible will have its thread interrupted upon
 * cancellation. Otherwise, the task must periodically check its cancelled state
 * and terminate as soon as possible.
 * </p>
 * 
 * @param <T>
 *        The result type returned by the task's {@code doInBackground} and
 *        {@code get} methods.
 * @param <V>
 *        The type used for carrying out intermediate results by the task's
 *        {@code publish} and {@code process} methods.
 */
@ThreadSafe
public abstract class RunnableTask<T, V>
    extends SwingWorker<T, V>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the {@code description} property. */
    public static final String DESCRIPTION_PROPERTY_NAME = "description"; //$NON-NLS-1$

    /** The name of the {@code progress} property. */
    public static final String PROGRESS_PROPERTY_NAME = "progress"; //$NON-NLS-1$

    /** The name of the {@code progressIndeterminate} property. */
    public static final String PROGRESS_INDETERMINATE_PROPERTY_NAME = "progressIndeterminate"; //$NON-NLS-1$

    /** The name of the {@code state} property. */
    public static final String STATE_PROPERTY_NAME = "state"; //$NON-NLS-1$

    /** The current task description. */
    private final AtomicReference<String> description_;

    /** Indicates the task is cancellable. */
    private final boolean isCancellable_;

    /** Indicates the task is interruptible. */
    private final boolean isInterruptible_;

    /** Indicates the task progress is indeterminate. */
    private final AtomicBoolean isProgressIndeterminate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RunnableTask} class that is both
     * cancellable and interruptible.
     */
    protected RunnableTask()
    {
        this( true );
    }

    /**
     * Initializes a new instance of the {@code RunnableTask} class that is
     * interruptible and using the specified cancellable indicator.
     * 
     * @param isCancellable
     *        {@code true} if the task is cancellable; otherwise {@code false}.
     */
    protected RunnableTask(
        final boolean isCancellable )
    {
        this( isCancellable, true );
    }

    /**
     * Initializes a new instance of the {@code RunnableTask} class using the
     * specified cancellable and interruptible indicators.
     * 
     * @param isCancellable
     *        {@code true} if the task is cancellable; otherwise {@code false}.
     * @param isInterruptible
     *        {@code true} if the task is interruptible; otherwise {@code false}
     *        .
     */
    protected RunnableTask(
        final boolean isCancellable,
        final boolean isInterruptible )
    {
        description_ = new AtomicReference<>( "" ); //$NON-NLS-1$
        isCancellable_ = isCancellable;
        isInterruptible_ = isInterruptible;
        isProgressIndeterminate_ = new AtomicBoolean( false );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the current task description.
     * 
     * @return The current task description; never {@code null}.
     */
    public final String getDescription()
    {
        final String description = description_.get();
        assert description != null;
        return description;
    }

    /**
     * Indicates the task is cancellable.
     * 
     * @return {@code true} if the task is cancellable; otherwise {@code false}.
     */
    public final boolean isCancellable()
    {
        return isCancellable_;
    }

    /**
     * Indicates the task is interruptible.
     * 
     * @return {@code true} if the task is interruptible; otherwise
     *         {@code false}.
     */
    public final boolean isInterruptible()
    {
        return isInterruptible_;
    }

    /**
     * Indicates the task progress is indeterminate.
     * 
     * @return {@code true} if the task progress is indeterminate; otherwise
     *         {@code false}.
     */
    public final boolean isProgressIndeterminate()
    {
        return isProgressIndeterminate_.get();
    }

    /**
     * Sets the current task description.
     * 
     * @param description
     *        The current task description; must not be {@code null}.
     */
    protected final void setDescription(
        final String description )
    {
        final String oldDescription = description_.getAndSet( description );
        firePropertyChange( DESCRIPTION_PROPERTY_NAME, oldDescription, description );
    }

    /**
     * Sets a flag that indicates the task progress is indeterminate.
     * 
     * @param isProgressIndeterminate
     *        {@code true} if the task progress is indeterminate; otherwise
     *        {@code false}.
     */
    @SuppressWarnings( "boxing" )
    protected final void setProgressIndeterminate(
        final boolean isProgressIndeterminate )
    {
        final boolean oldIsProgressIndeterminate = isProgressIndeterminate_.getAndSet( isProgressIndeterminate );
        firePropertyChange( PROGRESS_INDETERMINATE_PROPERTY_NAME, oldIsProgressIndeterminate, isProgressIndeterminate );
    }
}
