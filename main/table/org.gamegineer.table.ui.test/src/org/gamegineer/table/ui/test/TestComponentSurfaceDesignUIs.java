/*
 * TestComponentSurfaceDesignUIs.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

package org.gamegineer.table.ui.test;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import javax.swing.Icon;
import net.jcip.annotations.ThreadSafe;
import org.easymock.EasyMock;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.test.TestComponentSurfaceDesigns;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;

/**
 * A factory for creating various types of component surface design user
 * interface types suitable for testing.
 */
@ThreadSafe
public final class TestComponentSurfaceDesignUIs
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestComponentSurfaceDesignUIs}
     * class.
     */
    private TestComponentSurfaceDesignUIs()
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
     */
    public static ComponentSurfaceDesignUI cloneComponentSurfaceDesignUI(
        final ComponentSurfaceDesignUI componentSurfaceDesignUI )
    {
        return new ComponentSurfaceDesignUI( componentSurfaceDesignUI.getId(), componentSurfaceDesignUI.getName(), componentSurfaceDesignUI.getIcon() );
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
     */
    public static ComponentSurfaceDesignUI createComponentSurfaceDesignUI(
        final ComponentSurfaceDesign componentSurfaceDesign )
    {
        return new ComponentSurfaceDesignUI( componentSurfaceDesign.getId(), componentSurfaceDesign.getId().toString(), nonNull( EasyMock.createMock( Icon.class ) ) );
    }

    /**
     * Creates a new component surface design user interface with a unique
     * identifier.
     * 
     * @return A new component surface design user interface; never {@code null}
     *         .
     */
    public static ComponentSurfaceDesignUI createUniqueComponentSurfaceDesignUI()
    {
        return createComponentSurfaceDesignUI( TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
    }
}
