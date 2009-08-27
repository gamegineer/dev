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
 * Created on Apr 29, 2008 at 10:07:39 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.common.core.services.component.AbstractComponentFactory;
import org.gamegineer.common.core.services.component.ComponentCreationException;
import org.gamegineer.common.core.services.component.IComponentCreationContext;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute;
import org.gamegineer.common.core.services.component.util.attribute.ComponentCreationContextAttributeAccessor;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngine;
import org.gamegineer.engine.core.EngineFactory.InitializationCommandAttribute;

/**
 * A component factory for creating instances of {@code Engine}.
 */
final class EngineFactory
    extends AbstractComponentFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EngineFactory} class.
     */
    EngineFactory()
    {
        SupportedClassNamesAttribute.INSTANCE.setValue( this, IEngine.class.getName() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.IComponentFactory#createComponent(org.gamegineer.common.core.services.component.IComponentCreationContext)
     */
    @Override
    public Object createComponent(
        final IComponentCreationContext context )
        throws ComponentCreationException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final IAttributeAccessor accessor = new ComponentCreationContextAttributeAccessor( context );
        final String className = ClassNameAttribute.INSTANCE.getValue( accessor );
        final ICommand<?> command = InitializationCommandAttribute.INSTANCE.getValue( accessor );

        if( !IEngine.class.getName().equals( className ) )
        {
            throw new IllegalArgumentException( Messages.EngineFactory_createComponent_unsupportedType( className ) );
        }

        try
        {
            return Engine.createEngine( command );
        }
        catch( final EngineException e )
        {
            throw new ComponentCreationException( e );
        }
    }
}
