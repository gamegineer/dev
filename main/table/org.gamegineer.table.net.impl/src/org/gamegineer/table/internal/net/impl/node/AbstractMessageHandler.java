/*
 * AbstractMessageHandler.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.node;

import java.lang.reflect.Method;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.table.internal.net.impl.Loggers;
import org.gamegineer.table.internal.net.impl.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * Superclass for all implementations of {@link IMessageHandler}.
 * 
 * @param <RemoteNodeControllerType>
 *        The type of the remote node control interface.
 */
@Immutable
public abstract class AbstractMessageHandler<@NonNull RemoteNodeControllerType extends IRemoteNodeController<@NonNull ?>>
    implements IMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The type of the remote node controller. */
    private final Class<RemoteNodeControllerType> remoteNodeControllerType_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractMessageHandler} class.
     * 
     * @param remoteNodeControllerType
     *        The type of the remote node controller.
     */
    protected AbstractMessageHandler(
        final Class<RemoteNodeControllerType> remoteNodeControllerType )
    {
        remoteNodeControllerType_ = remoteNodeControllerType;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This method dispatches the incoming message via reflection. It searches
     * for a method with the following signature:
     * 
     * <p>
     * <code>void handleMessage(<i>&lt;remote node controller type&gt;</i>, <i>&lt;concrete message type&gt;</i>)</code>
     * </p>
     * 
     * <p>
     * If such a method is not found, an error message will be sent to the peer
     * remote node indicating the message is unsupported.
     * </p>
     * 
     * @see org.gamegineer.table.internal.net.impl.node.IMessageHandler#handleMessage(org.gamegineer.table.internal.net.impl.node.IRemoteNodeController,
     *      org.gamegineer.table.internal.net.impl.transport.IMessage)
     */
    @Override
    public final void handleMessage(
        final IRemoteNodeController<@NonNull ?> remoteNodeController,
        final IMessage message )
    {
        try
        {
            final Method method = getClass().getDeclaredMethod( "handleMessage", remoteNodeControllerType_, message.getClass() ); //$NON-NLS-1$
            try
            {
                method.setAccessible( true );
                method.invoke( this, remoteNodeController, message );
            }
            catch( final Exception e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractMessageHandler_handleMessage_unexpectedError, e );
            }
        }
        catch( @SuppressWarnings( "unused" ) final NoSuchMethodException e )
        {
            Loggers.getDefaultLogger().severe( NonNlsMessages.AbstractMessageHandler_messageReceived_unexpectedMessage( this, message ) );

            final ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setCorrelationId( message.getId() );
            errorMessage.setError( TableNetworkError.UNEXPECTED_MESSAGE );
            remoteNodeController.sendMessage( errorMessage, null );

            handleUnexpectedMessage( remoteNodeControllerType_.cast( remoteNodeController ) );
        }
    }

    /**
     * Invoked when the handler receives an unexpected message.
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     * 
     * @param remoteNodeController
     *        The control interface for the remote node that received the
     *        message.
     */
    protected void handleUnexpectedMessage(
        @SuppressWarnings( "unused" )
        final RemoteNodeControllerType remoteNodeController )
    {
        // do nothing
    }
}
