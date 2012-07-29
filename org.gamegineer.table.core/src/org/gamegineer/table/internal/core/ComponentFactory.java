/*
 * ComponentFactory.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jul 27, 2012 at 10:43:08 PM.
 */

package org.gamegineer.table.internal.core;

import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;

/**
 * A factory for creating table components.
 */
@ThreadSafe
final class ComponentFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the memento attribute that stores the component class name. */
    private static final String CLASS_NAME_MEMENTO_ATTRIBUTE_NAME = "componentFactory.className"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentFactory} class.
     */
    private ComponentFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a component for the specified table environment from the
     * specified memento.
     * 
     * @param tableEnvironment
     *        The table environment; must not be {@code null}.
     * @param memento
     *        The component memento; must not be {@code null}.
     * 
     * @return A new component; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static Component createComponent(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableEnvironment != null;
        assert memento != null;

        final Component component;

        final String className = MementoUtils.getAttribute( memento, CLASS_NAME_MEMENTO_ATTRIBUTE_NAME, String.class );
        if( className.equals( Card.class.getName() ) )
        {
            component = new Card( tableEnvironment );
        }
        else if( className.equals( CardPile.class.getName() ) )
        {
            component = new CardPile( tableEnvironment );
        }
        else if( className.equals( Tabletop.class.getName() ) )
        {
            component = new Tabletop( tableEnvironment );
        }
        else
        {
            throw new MementoException( NonNlsMessages.ComponentFactory_createComponent_unknownComponentType );
        }

        component.setMemento( memento );

        return component;
    }

    /**
     * Creates a new empty memento for the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @return A new empty memento for the specified component; never
     *         {@code null}.
     */
    /* @NonNull */
    static Map<String, Object> createMemento(
        /* @NonNull */
        final Component component )
    {
        assert component != null;

        final Map<String, Object> memento = new HashMap<String, Object>();
        memento.put( CLASS_NAME_MEMENTO_ATTRIBUTE_NAME, component.getClass().getName() );
        return memento;
    }
}
