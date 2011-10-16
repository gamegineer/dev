/*
 * AbstractEventHandler.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Jan 7, 2011 at 9:59:16 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import net.jcip.annotations.NotThreadSafe;

/**
 * Superclass for all event handlers in the TCP transport layer
 * Acceptor-Connector pattern implementation.
 */
@NotThreadSafe
abstract class AbstractEventHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The selection key representing the binding between the event handler and
     * an event dispatcher or {@code null} if the event handler is not currently
     * bound to an event dispatcher.
     */
    private SelectionKey selectionKey_;

    /** The event handler state. */
    private State state_;

    /** The transport layer associated with the event handler. */
    private final AbstractTransportLayer transportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractEventHandler} class.
     * 
     * @param transportLayer
     *        The transport layer associated with the event handler; must not be
     *        {@code null}.
     */
    AbstractEventHandler(
        /* @NonNull */
        final AbstractTransportLayer transportLayer )
    {
        assert transportLayer != null;

        selectionKey_ = null;
        state_ = State.PRISTINE;
        transportLayer_ = transportLayer;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the event handler normally.
     */
    final void close()
    {
        close( null );
    }

    /**
     * Closes the event handler due to the specified exception.
     * 
     * @param exception
     *        The exception that caused the event handler to be closed or
     *        {@code null} if the event handler was closed normally.
     */
    abstract void close(
        /* @Nullable */
        Exception exception );

    /**
     * Gets the channel associated with the event handler.
     * 
     * @return The channel associated with the event handler or {@code null} if
     *         the channel is not available.
     */
    /* @Nullable */
    abstract SelectableChannel getChannel();

    /**
     * Gets a bit mask of the channel operations in which the event handler is
     * interested.
     * 
     * @return A bit mask of the channel operations in which the event handler
     *         is interested.
     */
    abstract int getInterestOperations();

    /**
     * Gets the selection key representing the binding between the event handler
     * and an event dispatcher.
     * 
     * @return The selection key representing the binding between the event
     *         handler and an event dispatcher or {@code null} if the event
     *         handler is not currently bound to an event dispatcher.
     */
    /* @Nullable */
    final SelectionKey getSelectionKey()
    {
        assert isTransportLayerThread();

        return selectionKey_;
    }

    /**
     * Gets the event handler state.
     * 
     * @return The event handler state; never {@code null}.
     */
    /* @NonNull */
    final State getState()
    {
        assert isTransportLayerThread();

        return state_;
    }

    /**
     * Gets the transport layer associated with the event handler.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @return The transport layer associated with the event handler; never
     *         {@code null}.
     */
    /* @NonNull */
    final AbstractTransportLayer getTransportLayer()
    {
        return transportLayer_;
    }

    /**
     * Indicates the current thread is the transport layer thread for the
     * transport layer associated with the event handler.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @return {@code true} if the current thread is the transport layer thread
     *         for the transport layer associated with the event handler;
     *         otherwise {@code false}.
     */
    final boolean isTransportLayerThread()
    {
        return transportLayer_.isTransportLayerThread();
    }

    /**
     * Invoked by the event dispatcher immediately before the handler is run.
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     */
    void prepareToRun()
    {
        assert isTransportLayerThread();

        // do nothing
    }

    /**
     * Invoked by the event dispatcher to execute the event handler.
     */
    abstract void run();

    /**
     * Sets the selection key representing the binding between the event handler
     * and an event dispatcher.
     * 
     * @param selectionKey
     *        The selection key representing the binding between the event
     *        handler and an event dispatcher or {@code null} to clear the
     *        binding.
     */
    final void setSelectionKey(
        /* @Nullable */
        final SelectionKey selectionKey )
    {
        assert isTransportLayerThread();

        selectionKey_ = selectionKey;
    }

    /**
     * Sets the event handler state.
     * 
     * @param state
     *        The event handler state; must not be {@code null}.
     */
    final void setState(
        /* @NonNull */
        final State state )
    {
        assert state != null;
        assert isTransportLayerThread();

        state_ = state;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The possible event handler states.
     */
    enum State
    {
        /** The event handler has never been opened. */
        PRISTINE,

        /** The event handler is open. */
        OPEN,

        /** The event handler is closed. */
        CLOSED;
    }
}
