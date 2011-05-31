/*
 * AbstractMessageHandler.java
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
 * Created on May 30, 2011 at 8:41:41 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.lang.reflect.Method;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * Superclass for all implementations of
 * {@link org.gamegineer.table.internal.net.node.IMessageHandler}.
 * 
 * @param <RemoteNodeControllerType>
 *        The type of the remote node control interface.
 */
@Immutable
public abstract class AbstractMessageHandler<RemoteNodeControllerType extends IRemoteNodeController<?>>
    implements IMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The control interface for the remote node associated with the message
     * handler.
     */
    private final RemoteNodeControllerType remoteNodeController_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractMessageHandler} class.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node associated with the
     *        message handler; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteNodeController} is {@code null}.
     */
    protected AbstractMessageHandler(
        /* @NonNull */
        final RemoteNodeControllerType remoteNodeController )
    {
        assertArgumentNotNull( remoteNodeController, "remoteNodeController" ); //$NON-NLS-1$

        remoteNodeController_ = remoteNodeController;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the control interface for the remote node associated with the
     * message handler.
     * 
     * @return The control interface for the remote node associated with the
     *         message handler.
     */
    /* @NonNull */
    protected final RemoteNodeControllerType getRemoteNodeController()
    {
        return remoteNodeController_;
    }

    /**
     * This method dispatches the incoming message via reflection. It searches
     * for a method with the following signature:
     * 
     * <p>
     * <code>void handleMessage(<i>&lt;concrete message type&gt;</i>)</code>
     * </p>
     * 
     * <p>
     * If such a method is not found, an error message will be sent to the peer
     * remote node indicating the message is unsupported.
     * </p>
     * 
     * @see org.gamegineer.table.internal.net.node.IMessageHandler#handleMessage(org.gamegineer.table.internal.net.transport.IMessage)
     */
    @Override
    public final void handleMessage(
        final IMessage message )
    {
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$

        try
        {
            final Method method = getClass().getDeclaredMethod( "handleMessage", message.getClass() ); //$NON-NLS-1$
            try
            {
                method.setAccessible( true );
                method.invoke( this, message );
            }
            catch( final Exception e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractMessageHandler_handleMessage_unexpectedError, e );
            }
        }
        catch( final NoSuchMethodException e )
        {
            Loggers.getDefaultLogger().severe( Messages.AbstractMessageHandler_messageReceived_unexpectedMessage( this, message ) );

            final ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setCorrelationId( message.getId() );
            errorMessage.setError( TableNetworkError.UNEXPECTED_MESSAGE );
            getRemoteNodeController().sendMessage( errorMessage, null );

            handleUnexpectedMessage();
        }
    }

    /**
     * Invoked when the handler receives an unexpected message.
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     */
    protected void handleUnexpectedMessage()
    {
        // do nothing
    }
}
