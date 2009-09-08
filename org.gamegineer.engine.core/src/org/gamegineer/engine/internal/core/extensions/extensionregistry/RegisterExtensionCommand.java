/*
 * RegisterExtensionCommand.java
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
 * Created on Apr 17, 2008 at 9:59:40 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AbstractInvertibleCommand;
import org.gamegineer.engine.core.CommandBehavior;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IExtension;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry;

/**
 * A command that registers an engine extension.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@CommandBehavior( writeLockRequired = true )
@Immutable
public final class RegisterExtensionCommand
    extends AbstractInvertibleCommand<Void>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension to register. */
    private final IExtension extension_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RegisterExtensionCommand} class.
     * 
     * @param extension
     *        The extension to register; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code extension} is {@code null}.
     */
    public RegisterExtensionCommand(
        /* @NonNull */
        final IExtension extension )
    {
        assertArgumentNotNull( extension, "extension" ); //$NON-NLS-1$

        extension_ = extension;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
     */
    public Void execute(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final IExtensionRegistry registry = context.getExtension( IExtensionRegistry.class );
        if( registry == null )
        {
            throw new EngineException( Messages.Common_extensionRegistryExtension_unavailable );
        }

        registry.registerExtension( context, extension_ );

        return null;
    }

    /*
     * @see org.gamegineer.engine.core.IInvertibleCommand#getInverseCommand()
     */
    public IInvertibleCommand<Void> getInverseCommand()
    {
        return new UnregisterExtensionCommand( extension_ );
    }
}
