/*
 * ComponentSurfaceDesignUIs.java
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
 * Created on Apr 23, 2012 at 8:11:58 PM.
 */

package org.gamegineer.table.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import javax.swing.Icon;
import net.jcip.annotations.ThreadSafe;
import org.easymock.EasyMock;
import org.gamegineer.table.core.ComponentSurfaceDesigns;
import org.gamegineer.table.core.IComponentSurfaceDesign;

/**
 * A factory for creating various types of component surface design user
 * interface types suitable for testing.
 */
@ThreadSafe
public final class ComponentSurfaceDesignUIs
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesignUIs}
     * class.
     */
    private ComponentSurfaceDesignUIs()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified component surface design user interface.
     * 
     * @param componentSurfaceDesignUI
     *        The component surface design user interface to clone; must not be
     *        {@code null}.
     * 
     * @return A new component surface design user interface; never {@code null}
     *         .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code componentSurfaceDesignUI} is {@code null}.
     */
    /* @NonNull */
    public static IComponentSurfaceDesignUI cloneComponentSurfaceDesignUI(
        /* @NonNull */
        final IComponentSurfaceDesignUI componentSurfaceDesignUI )
    {
        assertArgumentNotNull( componentSurfaceDesignUI, "componentSurfaceDesignUI" ); //$NON-NLS-1$

        return TableUIFactory.createComponentSurfaceDesignUI( componentSurfaceDesignUI.getId(), componentSurfaceDesignUI.getName(), componentSurfaceDesignUI.getIcon() );
    }

    /**
     * Creates a new component surface design user interface for the specified
     * component surface design.
     * 
     * @param componentSurfaceDesign
     *        The component surface design; must not be {@code null}.
     * 
     * @return A new component surface design user interface; never {@code null}
     *         .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code componentSurfaceDesign} is {@code null}.
     */
    /* @NonNull */
    public static IComponentSurfaceDesignUI createComponentSurfaceDesignUI(
        /* @NonNull */
        final IComponentSurfaceDesign componentSurfaceDesign )
    {
        assertArgumentNotNull( componentSurfaceDesign, "componentSurfaceDesign" ); //$NON-NLS-1$

        return TableUIFactory.createComponentSurfaceDesignUI( componentSurfaceDesign.getId(), componentSurfaceDesign.getId().toString(), EasyMock.createMock( Icon.class ) );
    }

    /**
     * Creates a new component surface design user interface with a unique
     * identifier.
     * 
     * @return A new component surface design user interface; never {@code null}
     *         .
     */
    /* @NonNull */
    public static IComponentSurfaceDesignUI createUniqueComponentSurfaceDesignUI()
    {
        return createComponentSurfaceDesignUI( ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
    }
}
