/*
 * ComponentPrototypeUtils.java
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
 * Created on Oct 24, 2012 at 8:21:13 PM.
 */

package org.gamegineer.table.internal.ui.impl.prototype;

import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;

/**
 * A collection of useful methods for working with component prototypes.
 */
@ThreadSafe
public final class ComponentPrototypeUtils
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the Swing client property used to store the component
     * prototype factory.
     */
    private static final String CLIENT_PROPERTY_COMPONENT_PROTOTYPE_FACTORY = "org.gamegineer.table.ui.impl.prototype.componentPrototypeFactory"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPrototypeUtils} class.
     */
    private ComponentPrototypeUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component prototype factory for the Swing component associated
     * with the specified event.
     * 
     * @param event
     *        The event.
     * 
     * @return The component prototype factory for the Swing component
     *         associated with the specified event or {@code null} if not
     *         specified.
     */
    public static @Nullable IComponentPrototypeFactory getComponentPrototypeFactory(
        final ActionEvent event )
    {
        final Object source = event.getSource();
        if( source instanceof JComponent )
        {
            final Object propertyValue = ((JComponent)source).getClientProperty( CLIENT_PROPERTY_COMPONENT_PROTOTYPE_FACTORY );
            if( propertyValue instanceof IComponentPrototypeFactory )
            {
                return (IComponentPrototypeFactory)propertyValue;
            }
        }

        return null;
    }

    /**
     * Sets the component prototype factory for the specified Swing component.
     * 
     * @param uiComponent
     *        The Swing component.
     * @param componentPrototypeFactory
     *        The component prototype factory.
     */
    static void setComponentPrototypeFactory(
        final JComponent uiComponent,
        final IComponentPrototypeFactory componentPrototypeFactory )
    {
        uiComponent.putClientProperty( CLIENT_PROPERTY_COMPONENT_PROTOTYPE_FACTORY, componentPrototypeFactory );
    }
}
