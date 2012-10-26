/*
 * ComponentPrototypeUtils.java
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
 * Created on Oct 24, 2012 at 8:21:13 PM.
 */

package org.gamegineer.table.internal.ui.prototype;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.IComponentFactory;

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
     * The name of the Swing client property used to store the prototype
     * component factory.
     */
    private static final String CLIENT_PROPERTY_COMPONENT_FACTORY = "org.gamegineer.table.ui.prototype.componentFactory"; //$NON-NLS-1$


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
     * Gets the prototype component factory for the Swing component associated
     * with the specified event.
     * 
     * @param event
     *        The event; must not be {@code null}.
     * 
     * @return The prototype component factory for the Swing component
     *         associated with the specified event or {@code null} if not
     *         specified.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    /* @Nullable */
    public static IComponentFactory getComponentFactory(
        /* @NonNull */
        final ActionEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        final Object source = event.getSource();
        if( source instanceof JComponent )
        {
            final Object propertyValue = ((JComponent)source).getClientProperty( CLIENT_PROPERTY_COMPONENT_FACTORY );
            if( propertyValue instanceof IComponentFactory )
            {
                return (IComponentFactory)propertyValue;
            }
        }

        return null;
    }

    /**
     * Sets the prototype component factory for the specified Swing component.
     * 
     * @param uiComponent
     *        The Swing component; must not be {@code null}.
     * @param componentFactory
     *        The prototype component factory; must not be {@code null}.
     */
    static void setComponentFactory(
        /* @NonNull */
        final JComponent uiComponent,
        /* @NonNull */
        final IComponentFactory componentFactory )
    {
        assert uiComponent != null;
        assert componentFactory != null;

        uiComponent.putClientProperty( CLIENT_PROPERTY_COMPONENT_FACTORY, componentFactory );
    }
}
