/*
 * StageStrategyFactory.java
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
 * Created on Aug 16, 2009 at 9:21:47 PM.
 */

package org.gamegineer.tictactoe.internal.core.system.stages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.services.component.AbstractComponentFactory;
import org.gamegineer.common.core.services.component.ComponentCreationException;
import org.gamegineer.common.core.services.component.IComponentCreationContext;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute;
import org.gamegineer.common.core.services.component.util.attribute.ComponentCreationContextAttributeAccessor;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor;

/**
 * A component factory for creating instances of the Tic-Tac-Toe stage
 * strategies.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class StageStrategyFactory
    extends AbstractComponentFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of stage strategy class names which this factory can
     * create.
     */
    private static final Collection<String> stageStrategyClassNames_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code StageStrategyFactory} class.
     */
    static
    {
        final List<String> classNameList = new ArrayList<String>();

        classNameList.add( GameStageStrategy.class.getName() );

        stageStrategyClassNames_ = Collections.unmodifiableList( classNameList );
    }

    /**
     * Initializes a new instance of the {@code StageStrategyFactory} class.
     */
    StageStrategyFactory()
    {
        SupportedClassNamesAttribute.INSTANCE.setValue( this, stageStrategyClassNames_ );
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

        if( !stageStrategyClassNames_.contains( className ) )
        {
            throw new IllegalArgumentException( Messages.StageStrategyFactory_createComponent_unsupportedType( className ) );
        }

        try
        {
            return Class.forName( className ).newInstance();
        }
        catch( final ClassNotFoundException e )
        {
            throw new ComponentCreationException( Messages.StageStrategyFactory_createComponent_failed( className ), e );
        }
        catch( final IllegalAccessException e )
        {
            throw new ComponentCreationException( Messages.StageStrategyFactory_createComponent_failed( className ), e );
        }
        catch( final InstantiationException e )
        {
            throw new ComponentCreationException( Messages.StageStrategyFactory_createComponent_failed( className ), e );
        }
    }
}
