/*
 * SecurityManagerExtension.java
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
 * Created on Apr 1, 2009 at 10:03:15 PM.
 */

package org.gamegineer.engine.internal.core.extensions.securitymanager;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.security.Principal;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.contexts.command.CommandContextBuilder;
import org.gamegineer.engine.core.contexts.command.ICommandContext;
import org.gamegineer.engine.core.extensions.securitymanager.ISecurityManager;
import org.gamegineer.engine.core.extensions.securitymanager.ThreadPrincipals;
import org.gamegineer.engine.internal.core.extensions.AbstractExtension;

// XXX: This public static methods in this class are temporary until the engine
// SPI model is more mature, at which time the engine will simply visit each
// registered extension allowing them to modify the command context as needed.

/**
 * Implementation of the
 * {@link org.gamegineer.engine.core.extensions.securitymanager.ISecurityManager}
 * extension.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class SecurityManagerExtension
    extends AbstractExtension
    implements ISecurityManager
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the user principal command context attribute. */
    private static final String ATTR_USER_PRINCIPAL = "org.gamegineer.engine.internal.core.extensions.securitymanager.userPrincipal"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SecurityManagerExtension} class.
     */
    public SecurityManagerExtension()
    {
        super( ISecurityManager.class );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @throws java.lang.IllegalArgumentException
     *         If the engine context does not contain a command context that
     *         specifies a user principal.
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * 
     * @see org.gamegineer.engine.core.extensions.securitymanager.ISecurityManager#getUserPrincipal(org.gamegineer.engine.core.IEngineContext)
     */
    public Principal getUserPrincipal(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        final Principal userPrincipal = getUserPrincipal( context.getContext( ICommandContext.class ) );
        assertArgumentLegal( userPrincipal != null, "context", Messages.SecurityManagerExtension_userPrincipal_unavailable ); //$NON-NLS-1$
        assertExtensionStarted();

        return userPrincipal;
    }

    /**
     * Gets the user principal from the specified command context.
     * 
     * @param context
     *        The command context; may be {@code null}.
     * 
     * @return The user principal or {@code null} if the command context is
     *         {@code null} or the user principal attribute does not exist in
     *         the command context.
     */
    /* @Nullable */
    private static Principal getUserPrincipal(
        /* @Nullable */
        final ICommandContext context )
    {
        if( context == null )
        {
            return null;
        }

        return (Principal)context.getAttribute( ATTR_USER_PRINCIPAL );
    }

    /**
     * Invoked immediately before a command is submitted to the engine.
     * 
     * @param builder
     *        The command context builder; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code builder} is {@code null}.
     */
    public static void preSubmitCommand(
        /* @NonNull */
        final CommandContextBuilder builder )
    {
        assertArgumentNotNull( builder, "builder" ); //$NON-NLS-1$

        builder.addAttribute( ATTR_USER_PRINCIPAL, ThreadPrincipals.getUserPrincipal() );
    }
}
