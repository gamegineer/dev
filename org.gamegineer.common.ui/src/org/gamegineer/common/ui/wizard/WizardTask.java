/*
 * WizardTask.java
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
 * Created on Nov 17, 2010 at 10:24:27 PM.
 */

package org.gamegineer.common.ui.wizard;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingWorker;
import net.jcip.annotations.ThreadSafe;

/**
 * A task to be executed within a wizard.
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
public abstract class WizardTask<T, V>
    extends SwingWorker<T, V>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the {@code description} property. */
    public static final String DESCRIPTION_PROPERTY_NAME = "description"; //$NON-NLS-1$

    /** The name of the {@code progress} property. */
    public static final String PROGRESS_PROPERTY_NAME = "progress"; //$NON-NLS-1$

    /** The name of the {@code state} property. */
    public static final String STATE_PROPERTY_NAME = "state"; //$NON-NLS-1$

    /** The current task description. */
    private final AtomicReference<String> description_;

    /** Indicates the task is cancellable. */
    private final boolean isCancellable_;

    /** Indicates the task is interruptible. */
    private final boolean isInterruptible_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code WizardTask} class that is both
     * cancellable and interruptible.
     */
    protected WizardTask()
    {
        this( true );
    }

    /**
     * Initializes a new instance of the {@code WizardTask} class that is
     * interruptible and using the specified cancellable indicator.
     * 
     * @param isCancellable
     *        {@code true} if the task is cancellable; otherwise {@code false}.
     */
    protected WizardTask(
        final boolean isCancellable )
    {
        this( isCancellable, true );
    }

    /**
     * Initializes a new instance of the {@code WizardTask} class using the
     * specified cancellable and interruptible indicators.
     * 
     * @param isCancellable
     *        {@code true} if the task is cancellable; otherwise {@code false}.
     * @param isInterruptible
     *        {@code true} if the task is interruptible; otherwise {@code false}
     *        .
     */
    protected WizardTask(
        final boolean isCancellable,
        final boolean isInterruptible )
    {
        description_ = new AtomicReference<String>( "" ); //$NON-NLS-1$
        isCancellable_ = isCancellable;
        isInterruptible_ = isInterruptible;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the current task description.
     * 
     * @return The current task description; never {@code null}.
     */
    /* @NonNull */
    public final String getDescription()
    {
        return description_.get();
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
     * @return {@code true} if the task is interruptible; otherwise {@code
     *         false}.
     */
    public final boolean isInterruptible()
    {
        return isInterruptible_;
    }

    /**
     * Sets the current task description.
     * 
     * @param description
     *        The current task description; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code description} is {@code null}.
     */
    protected final void setDescription(
        /* @NonNull */
        final String description )
    {
        assertArgumentNotNull( description, "description" ); //$NON-NLS-1$

        final String oldDescription = description_.getAndSet( description );
        firePropertyChange( DESCRIPTION_PROPERTY_NAME, oldDescription, description );
    }
}
