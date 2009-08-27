/*
 * EngineFactory.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jun 28, 2008 at 10:09:06 PM.
 */

package org.gamegineer.engine.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.core.services.component.ComponentCreationContextBuilder;
import org.gamegineer.common.core.services.component.ComponentException;
import org.gamegineer.common.core.services.component.IComponentSpecification;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.specs.ClassNameComponentSpecification;
import org.gamegineer.common.core.services.component.util.attribute.AbstractAttribute;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.engine.internal.core.commands.NullCommand;
import org.gamegineer.engine.internal.core.extensions.statemanager.SetMementoCommand;

/**
 * A factory for creating engines.
 * 
 * <p>
 * In order to be accessible to this factory, an engine implementation must
 * provide an OSGi service that implements
 * {@link org.gamegineer.common.core.services.component.IComponentFactory} which
 * publishes the
 * {@link org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute}
 * attribute with a value that contains the element
 * {@code org.gamegineer.engine.core.IEngine}. The component creation context
 * passed to the factory will publish the
 * {@link EngineFactory.InitializationCommandAttribute} attribute whose value
 * contains an instance of {@code org.gamegineer.engine.core.ICommand}.
 * </p>
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
@ThreadSafe
public final class EngineFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EngineFactory} class.
     */
    private EngineFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new engine with a clean state.
     * 
     * @return A new engine with a clean state; never {@code null}.
     * 
     * @throws org.gamegineer.engine.core.EngineConfigurationException
     *         If an engine that satisfies the requested configuration could not
     *         be created.
     */
    /* @NonNull */
    public static IEngine createEngine()
        throws EngineConfigurationException
    {
        return createEngine( new NullCommand() );
    }

    /**
     * Creates a new engine whose state is initialized using the specified
     * command.
     * 
     * <p>
     * The command used to initialize the engine may freely modify the engine
     * state. It need not be invertible as it will not be added to the engine
     * command history. Thus, any modifications made by the initialization
     * command may not be undone.
     * </p>
     * 
     * @param command
     *        The command used to build the initial engine state; must not be
     *        {@code null}.
     * 
     * @return A new engine whose state is initialized using the specified
     *         command; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code command} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineConfigurationException
     *         If an engine that satisfies the requested configuration could not
     *         be created or an error occurs while executing the specified
     *         command.
     */
    /* @NonNull */
    public static IEngine createEngine(
        /* @NonNull */
        final ICommand<?> command )
        throws EngineConfigurationException
    {
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$

        final String className = IEngine.class.getName();
        final IComponentSpecification specification = new ClassNameComponentSpecification( className );
        final ComponentCreationContextBuilder builder = new ComponentCreationContextBuilder();
        ClassNameAttribute.INSTANCE.setValue( builder, className );
        InitializationCommandAttribute.INSTANCE.setValue( builder, command );

        try
        {
            return (IEngine)Platform.getComponentService().createComponent( specification, builder.toComponentCreationContext() );
        }
        catch( final ComponentException e )
        {
            throw new EngineConfigurationException( Messages.EngineFactory_createEngine_unsupportedConfiguration, e );
        }
    }

    /**
     * Creates a new engine whose state is initialized using the specified
     * memento.
     * 
     * @param memento
     *        The memento representing the initial engine state; must not be
     *        {@code null}.
     * 
     * @return A new engine whose state is initialized using the specified
     *         memento; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineConfigurationException
     *         If an engine that satisfies the requested configuration could not
     *         be created or the specified memento is unsupported.
     */
    /* @NonNull */
    public static IEngine createEngine(
        /* @NonNull */
        final IMemento memento )
        throws EngineConfigurationException
    {
        // TODO: Refactor this method to not rely directly on SetMementoCommand. Using
        // this command directly ties it to a specific IEngine implementation. Use the
        // new initialization command functionality if possible. That may mean we can
        // no longer support setting a memento on a previously-constructed engine.
        // There's probably similar issues with the use of other phantom commands.

        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        final IEngine engine = createEngine();

        try
        {
            engine.executeCommand( new SetMementoCommand( memento ) );
        }
        catch( final EngineException e )
        {
            throw new EngineConfigurationException( Messages.EngineFactory_createEngine_unsupportedConfiguration, e );
        }

        return engine;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * An attribute used to specify the command used to initialize the engine
     * state when a client requests an engine instance to be created.
     */
    public static final class InitializationCommandAttribute
        extends AbstractAttribute<ICommand<?>>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The singleton attribute instance. */
        public static final InitializationCommandAttribute INSTANCE = new InitializationCommandAttribute();

        /** The attribute name. */
        private static final String NAME = "org.gamegineer.engine.core.EngineFactory.initializationCommand"; //$NON-NLS-1$


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code InitializationCommandAttribute} class.
         */
        private InitializationCommandAttribute()
        {
            super( NAME, ICommand.class );
        }
    }
}
