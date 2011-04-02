/*
 * ClientNetworkServiceHandler.java
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
 * Created on Mar 25, 2011 at 11:28:48 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.messages.HelloResponseMessage;

/**
 * A service handler that represents the client half of the network table
 * protocol.
 */
@ThreadSafe
final class ClientNetworkServiceHandler
    extends AbstractNetworkServiceHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientNetworkServiceHandler}
     * class.
     * 
     * @param networkTable
     *        The network table associated with the service handler; must not be
     *        {@code null}.
     */
    ClientNetworkServiceHandler(
        /* @NonNull */
        final NetworkTable networkTable )
    {
        super( networkTable );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a Begin Authentication Request message.
     * 
     * @param context
     *        The network service context; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    private void handleBeginAuthenticationRequestMessage(
        /* @NonNull */
        final INetworkServiceContext context,
        /* @NonNull */
        final BeginAuthenticationRequestMessage message )
    {
        assert context != null;
        assert message != null;
        assert Thread.holdsLock( getLock() );

        final BeginAuthenticationResponseMessage response = new BeginAuthenticationResponseMessage();
        response.setTag( message.getTag() );
        response.setPlayerName( getNetworkTable().getConfiguration().getLocalPlayerName() );
        // TODO: calculate HMAC using password, challenge, salt, and iterationCount
        response.setResponse( new byte[] {
            7, 6, 5, 4, 3, 2, 1, 0
        } );

        if( !context.sendMessage( response ) )
        {
            context.stopService();
        }
    }

    /**
     * Handles an End Authentication message.
     * 
     * @param context
     *        The network service context; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    @SuppressWarnings( "boxing" )
    private void handleEndAuthenticationMessage(
        /* @NonNull */
        final INetworkServiceContext context,
        /* @NonNull */
        final EndAuthenticationMessage message )
    {
        assert context != null;
        assert message != null;
        assert Thread.holdsLock( getLock() );

        if( message.getException() != null )
        {
            System.out.println( String.format( "ClientNetworkServiceHandler : failed authentication (tag=%d) with exception: ", message.getTag() ) + message.getException() ); //$NON-NLS-1$
            context.stopService();
        }
        else
        {
            System.out.println( String.format( "ClientNetworkServiceHandler : completed authentication successfully (tag=%d): ", message.getTag() ) ); //$NON-NLS-1$
        }
    }

    /**
     * Handles a Hello Response message.
     * 
     * @param context
     *        The network service context; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    @SuppressWarnings( "boxing" )
    private void handleHelloResponseMessage(
        /* @NonNull */
        final INetworkServiceContext context,
        /* @NonNull */
        final HelloResponseMessage message )
    {
        assert context != null;
        assert message != null;
        assert Thread.holdsLock( getLock() );

        if( message.getException() != null )
        {
            System.out.println( String.format( "ClientNetworkServiceHandler : received hello response (tag=%d) with exception: ", message.getTag() ) + message.getException() ); //$NON-NLS-1$
            context.stopService();
        }
        else
        {
            System.out.println( String.format( "ClientNetworkServiceHandler : received hello response (tag=%d) with chosen version '%d'", message.getTag(), message.getChosenProtocolVersion() ) ); //$NON-NLS-1$
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkServiceHandler#messageReceivedInternal(org.gamegineer.table.internal.net.INetworkServiceContext, org.gamegineer.table.internal.net.AbstractMessage)
     */
    @Override
    boolean messageReceivedInternal(
        final INetworkServiceContext context,
        final AbstractMessage message )
    {
        assert context != null;
        assert message != null;
        assert Thread.holdsLock( getLock() );

        if( message instanceof HelloResponseMessage )
        {
            handleHelloResponseMessage( context, (HelloResponseMessage)message );
            return true;
        }
        else if( message instanceof BeginAuthenticationRequestMessage )
        {
            handleBeginAuthenticationRequestMessage( context, (BeginAuthenticationRequestMessage)message );
            return true;
        }
        else if( message instanceof EndAuthenticationMessage )
        {
            handleEndAuthenticationMessage( context, (EndAuthenticationMessage)message );
            return true;
        }

        return false;
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkServiceHandler#peerStoppedInternal(org.gamegineer.table.internal.net.INetworkServiceContext)
     */
    @Override
    void peerStoppedInternal(
        final INetworkServiceContext context )
    {
        assert context != null;
        assert Thread.holdsLock( getLock() );

        getNetworkTable().disconnect();
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkServiceHandler#startedInternal(org.gamegineer.table.internal.net.INetworkServiceContext)
     */
    @Override
    void startedInternal(
        final INetworkServiceContext context )
    {
        assert context != null;
        assert Thread.holdsLock( getLock() );

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setTag( getNextMessageTag() );
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        if( !context.sendMessage( message ) )
        {
            context.stopService();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkServiceHandler#stoppedInternal(org.gamegineer.table.internal.net.INetworkServiceContext)
     */
    @Override
    void stoppedInternal(
        final INetworkServiceContext context )
    {
        assert context != null;
        assert Thread.holdsLock( getLock() );

        getNetworkTable().disconnect();
    }
}
