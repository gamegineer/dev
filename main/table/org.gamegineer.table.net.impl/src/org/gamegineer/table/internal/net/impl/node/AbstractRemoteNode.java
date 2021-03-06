/*
 * AbstractRemoteNode.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on Apr 14, 2011 at 10:43:19 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.Debug;
import org.gamegineer.table.internal.net.impl.Loggers;
import org.gamegineer.table.internal.net.impl.node.common.handlers.ComponentIncrementMessageHandler;
import org.gamegineer.table.internal.net.impl.node.common.handlers.TableMessageHandler;
import org.gamegineer.table.internal.net.impl.node.common.messages.ComponentIncrementMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.GoodbyeMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.TableMessage;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
import org.gamegineer.table.internal.net.impl.transport.IService;
import org.gamegineer.table.internal.net.impl.transport.IServiceContext;
import org.gamegineer.table.internal.net.impl.transport.MessageEnvelope;
import org.gamegineer.table.net.TableNetworkError;

/**
 * Superclass for all remote nodes.
 * 
 * <p>
 * All methods of this class are expected to be invoked on the associated node
 * layer thread except where explicitly noted.
 * </p>
 * 
 * @param <LocalNodeType>
 *        The type of the local table network node.
 * @param <RemoteNodeType>
 *        The type of the remote table network node.
 */
@NotThreadSafe
public abstract class AbstractRemoteNode<LocalNodeType extends INode<RemoteNodeType>, RemoteNodeType extends IRemoteNode>
    implements IRemoteNode, IRemoteNodeController<LocalNodeType>, IService
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The error that caused the connection to the remote node to be closed or
     * {@code null} if the connection to the remote node was closed normally.
     */
    private @Nullable TableNetworkError closeError_;

    /**
     * The collection of message handlers for correlated messages. The key is
     * the message (request) identifier. The value is the message handler.
     */
    private final Map<Integer, IMessageHandler> correlatedMessageHandlers_;

    /** The local table network node. */
    private final LocalNodeType localNode_;

    /** The next available message identifier. */
    private int nextId_;

    /** The node layer. */
    private final INodeLayer nodeLayer_;

    /**
     * The name of the remote player or {@code null} if the player has not yet
     * been authenticated.
     */
    private @Nullable String playerName_;

    /**
     * The network service context or {@code null} if the network is not
     * connected.
     */
    private @Nullable IServiceContext serviceContext_;

    /** The table associated with the remote node. */
    private final INetworkTable table_;

    /**
     * The collection of message handlers for uncorrelated messages. The key is
     * the message type. The value is the message handler.
     */
    private final Map<Class<? extends IMessage>, IMessageHandler> uncorrelatedMessageHandlers_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRemoteNode} class.
     * 
     * @param nodeLayer
     *        The node layer.
     * @param node
     *        The local table network node.
     */
    protected AbstractRemoteNode(
        final INodeLayer nodeLayer,
        final LocalNodeType node )
    {
        assert nodeLayer.isNodeLayerThread();

        closeError_ = null;
        correlatedMessageHandlers_ = new HashMap<>();
        localNode_ = node;
        nextId_ = getInitialMessageId();
        nodeLayer_ = nodeLayer;
        playerName_ = null;
        serviceContext_ = null;
        table_ = new RemoteNetworkTable( this );
        uncorrelatedMessageHandlers_ = new IdentityHashMap<>();

        registerUncorrelatedMessageHandler( ComponentIncrementMessage.class, ComponentIncrementMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( ErrorMessage.class, ErrorMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( TableMessage.class, TableMessageHandler.INSTANCE );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.node.IRemoteNodeController#bind(java.lang.String)
     */
    @Override
    public final void bind(
        final String playerName )
    {
        assertStateLegal( serviceContext_ != null, NonNlsMessages.AbstractRemoteNode_closed );
        assertStateLegal( playerName_ == null, NonNlsMessages.AbstractRemoteNode_bound );
        assert isNodeLayerThread();

        playerName_ = playerName;
        localNode_.bindRemoteNode( getThisAsRemoteNodeType() );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.IRemoteNodeController#close(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    public final void close(
        final @Nullable TableNetworkError error )
    {
        assertStateLegal( serviceContext_ != null, NonNlsMessages.AbstractRemoteNode_closed );
        assert isNodeLayerThread();

        // Do not overwrite the original error that caused the remote node to be closed
        if( closeError_ == null )
        {
            closeError_ = error;
        }

        assert serviceContext_ != null;
        serviceContext_.stopService();
    }

    /**
     * Invoked when the remote node has been closed.
     * 
     * <p>
     * Subclasses may override but the superclass version must be called.
     * </p>
     * 
     * @param error
     *        The error that caused the remote node to be closed or {@code null}
     *        if the remote node was closed normally.
     */
    protected void closed(
        @SuppressWarnings( "unused" )
        final @Nullable TableNetworkError error )
    {
        assert isNodeLayerThread();

        if( playerName_ != null )
        {
            localNode_.unbindRemoteNode( getThisAsRemoteNodeType() );
            playerName_ = null;
        }
    }

    /**
     * Extracts the message from the specified message envelope.
     * 
     * @param messageEnvelope
     *        The message envelope.
     * 
     * @return The message extracted from the specified message envelope or
     *         {@code null} if the message envelope contains an unknown message.
     */
    private static @Nullable IMessage extractMessage(
        final MessageEnvelope messageEnvelope )
    {
        try
        {
            return messageEnvelope.getMessage();
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractRemoteNode_extractMessage_deserializationError( messageEnvelope ), e );
            return null;
        }
        catch( final ClassNotFoundException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractRemoteNode_extractMessage_deserializationError( messageEnvelope ), e );
            return null;
        }
    }

    /**
     * Gets the initial message identifier.
     * 
     * @return The initial message identifier.
     */
    private static int getInitialMessageId()
    {
        return IMessage.MINIMUM_ID + (int)(Math.random() * (IMessage.MAXIMUM_ID - IMessage.MINIMUM_ID));
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.IRemoteNodeController#getLocalNode()
     */
    @Override
    public final LocalNodeType getLocalNode()
    {
        return localNode_;
    }

    /**
     * Gets the next available message identifier.
     * 
     * @return The next available message identifier.
     */
    private int getNextMessageId()
    {
        assert isNodeLayerThread();

        final int id = nextId_;
        if( ++nextId_ > IMessage.MAXIMUM_ID )
        {
            nextId_ = IMessage.MINIMUM_ID;
        }

        return id;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.IRemoteNode#getPlayerName()
     */
    @Override
    public final String getPlayerName()
    {
        assert isNodeLayerThread();

        assertStateLegal( playerName_ != null, NonNlsMessages.AbstractRemoteNode_playerNotAuthenticated );
        assert playerName_ != null;
        return playerName_;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.IRemoteNode#getTable()
     * @see org.gamegineer.table.internal.net.impl.node.IRemoteNodeController#getTable()
     */
    @Override
    public final INetworkTable getTable()
    {
        return table_;
    }

    /**
     * Gets a reference to this remote node as the type of remote node expected
     * by the local node.
     * 
     * @return A reference to this remote node as the type of remote node
     *         expected by the local node.
     */
    protected abstract RemoteNodeType getThisAsRemoteNodeType();

    /*
     * @see org.gamegineer.table.internal.net.impl.node.IRemoteNode#goodbye()
     */
    @Override
    public final void goodbye()
    {
        assert isNodeLayerThread();

        final GoodbyeMessage message = new GoodbyeMessage();
        sendMessage( message, null );
        close( null );
    }

    /**
     * Indicates the current thread is the node layer thread.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @return {@code true} if the current thread is the node layer thread;
     *         otherwise {@code false}.
     */
    protected final boolean isNodeLayerThread()
    {
        return nodeLayer_.isNodeLayerThread();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.IService#messageReceived(org.gamegineer.table.internal.net.impl.transport.MessageEnvelope)
     */
    @Override
    @SuppressWarnings( "boxing" )
    public final void messageReceived(
        final MessageEnvelope messageEnvelope )
    {
        assert isNodeLayerThread();

        final IMessage message = extractMessage( messageEnvelope );
        if( message != null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, //
                String.format( "Received message '%s' (id=%d, correlation-id=%d) from remote node '%s'", //$NON-NLS-1$
                    message.getClass().getName(), //
                    message.getId(), //
                    message.getCorrelationId(), //
                    playerName_ ) );

            final IMessageHandler messageHandler;
            if( message.getCorrelationId() != IMessage.NULL_CORRELATION_ID )
            {
                messageHandler = correlatedMessageHandlers_.remove( message.getCorrelationId() );
            }
            else
            {
                messageHandler = uncorrelatedMessageHandlers_.get( message.getClass() );
            }

            if( messageHandler != null )
            {
                ThreadPlayer.setPlayerName( playerName_ );
                try
                {
                    messageHandler.handleMessage( this, message );
                }
                finally
                {
                    ThreadPlayer.setPlayerName( null );
                }
            }
            else
            {
                Loggers.getDefaultLogger().warning( NonNlsMessages.AbstractRemoteNode_messageReceived_unhandledMessage( message ) );
                if( !(message instanceof ErrorMessage) )
                {
                    sendErrorMessage( TableNetworkError.UNHANDLED_MESSAGE, message.getId() );
                }
            }
        }
        else
        {
            sendErrorMessage( TableNetworkError.UNKNOWN_MESSAGE, messageEnvelope.getHeader().getId() );
        }
    }

    /**
     * Invoked when the remote node has been opened.
     * 
     * <p>
     * Subclasses may override but the superclass version must be called.
     * </p>
     */
    protected void opened()
    {
        assert isNodeLayerThread();

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.IService#peerStopped()
     */
    @Override
    public final void peerStopped()
    {
        assert isNodeLayerThread();

        close( TableNetworkError.UNEXPECTED_PEER_TERMINATION );
    }

    /**
     * Registers the specified message handler to handle uncorrelated messages
     * of the specified type.
     * 
     * @param type
     *        The message type.
     * @param messageHandler
     *        The message handler.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If an uncorrelated message handler is already registered for the
     *         specified message type.
     */
    protected final void registerUncorrelatedMessageHandler(
        final Class<? extends IMessage> type,
        final IMessageHandler messageHandler )
    {
        assert isNodeLayerThread();

        assertArgumentLegal( !uncorrelatedMessageHandlers_.containsKey( type ), "type", NonNlsMessages.AbstractRemoteNode_registerUncorrelatedMessageHandler_messageTypeRegistered ); //$NON-NLS-1$
        uncorrelatedMessageHandlers_.put( type, messageHandler );
    }

    /**
     * Sends an error message to the remote peer with the specified attributes.
     * 
     * @param error
     *        The error that occurred.
     * @param correlationId
     *        The message correlation identifier.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code correlationId} is less than {@link IMessage#MINIMUM_ID}
     *         or greater than {@link IMessage#MAXIMUM_ID} or not equal to
     *         {@link IMessage#NULL_CORRELATION_ID} .
     */
    private void sendErrorMessage(
        final TableNetworkError error,
        final int correlationId )
    {
        assert isNodeLayerThread();

        final ErrorMessage message = new ErrorMessage();
        message.setCorrelationId( correlationId );
        message.setError( error );
        sendMessage( message, null );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.IRemoteNodeController#sendMessage(org.gamegineer.table.internal.net.impl.transport.IMessage, org.gamegineer.table.internal.net.impl.node.IMessageHandler)
     */
    @Override
    @SuppressWarnings( "boxing" )
    public final void sendMessage(
        final IMessage message,
        final @Nullable IMessageHandler messageHandler )
    {
        assert isNodeLayerThread();

        assertStateLegal( serviceContext_ != null, NonNlsMessages.AbstractRemoteNode_closed );
        message.setId( getNextMessageId() );
        assert serviceContext_ != null;
        serviceContext_.sendMessage( message );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, //
            String.format( "Sent message '%s' (id=%d, correlation-id=%d)", //$NON-NLS-1$
                message.getClass().getName(), //
                message.getId(), //
                message.getCorrelationId() ) );

        if( messageHandler != null )
        {
            correlatedMessageHandlers_.put( message.getId(), messageHandler );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.IService#started(org.gamegineer.table.internal.net.impl.transport.IServiceContext)
     */
    @Override
    public final void started(
        final IServiceContext context )
    {
        assert isNodeLayerThread();

        serviceContext_ = context;
        opened();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.IService#stopped(java.lang.Exception)
     */
    @Override
    public final void stopped(
        final @Nullable Exception exception )
    {
        assert isNodeLayerThread();

        // Do not overwrite the original error that caused the remote node to be closed
        if( (exception != null) && (closeError_ == null) )
        {
            closeError_ = TableNetworkError.TRANSPORT_ERROR;
        }

        closed( closeError_ );
        serviceContext_ = null;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A message handler for the {@link ErrorMessage} class.
     */
    @Immutable
    @SuppressWarnings( "rawtypes" )
    private static final class ErrorMessageHandler
        extends AbstractMessageHandler<IRemoteNodeController>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The singleton instance of this class. */
        static final ErrorMessageHandler INSTANCE = new ErrorMessageHandler();


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ErrorMessageHandler} class.
         */
        private ErrorMessageHandler()
        {
            super( IRemoteNodeController.class );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Handles an {@code ErrorMessage} message.
         * 
         * @param remoteNodeController
         *        The control interface for the remote node that received the
         *        message.
         * @param message
         *        The message.
         */
        @SuppressWarnings( {
            "static-method", "unused"
        } )
        private void handleMessage(
            final IRemoteNodeController<@NonNull ?> remoteNodeController,
            final ErrorMessage message )
        {
            Loggers.getDefaultLogger().warning( NonNlsMessages.ErrorMessageHandler_handleMessage_errorReceived( message.getError() ) );
        }
    }
}
