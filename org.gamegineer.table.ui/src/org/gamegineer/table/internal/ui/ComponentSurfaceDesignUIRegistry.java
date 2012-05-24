/*
 * ComponentSurfaceDesignUIRegistry.java
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
 * Created on Apr 23, 2012 at 8:22:23 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;
import org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry;

/**
 * Implementation of
 * {@link org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry}.
 */
@ThreadSafe
public final class ComponentSurfaceDesignUIRegistry
    implements IComponentSurfaceDesignUIRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of component surface design user interfaces directly
     * managed by this object.
     */
    private final ConcurrentMap<ComponentSurfaceDesignId, ComponentSurfaceDesignUI> componentSurfaceDesignUIs_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignUIRegistry} class.
     */
    public ComponentSurfaceDesignUIRegistry()
    {
        componentSurfaceDesignUIs_ = new ConcurrentHashMap<ComponentSurfaceDesignId, ComponentSurfaceDesignUI>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry#getComponentSurfaceDesignUI(org.gamegineer.table.core.ComponentSurfaceDesignId)
     */
    @Override
    public ComponentSurfaceDesignUI getComponentSurfaceDesignUI(
        final ComponentSurfaceDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return componentSurfaceDesignUIs_.get( id );
    }

    /*
     * @see org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry#getComponentSurfaceDesignUIs()
     */
    @Override
    public Collection<ComponentSurfaceDesignUI> getComponentSurfaceDesignUIs()
    {
        return new ArrayList<ComponentSurfaceDesignUI>( componentSurfaceDesignUIs_.values() );
    }

    /*
     * @see org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry#registerComponentSurfaceDesignUI(org.gamegineer.table.ui.ComponentSurfaceDesignUI)
     */
    @Override
    public void registerComponentSurfaceDesignUI(
        final ComponentSurfaceDesignUI componentSurfaceDesignUI )
    {
        assertArgumentNotNull( componentSurfaceDesignUI, "componentSurfaceDesignUI" ); //$NON-NLS-1$
        assertArgumentLegal( componentSurfaceDesignUIs_.putIfAbsent( componentSurfaceDesignUI.getId(), componentSurfaceDesignUI ) == null, "componentSurfaceDesignUI", NonNlsMessages.ComponentSurfaceDesignUIRegistry_registerComponentSurfaceDesignUI_componentSurfaceDesignUI_registered( componentSurfaceDesignUI.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered component surface design user interface '%1$s'", componentSurfaceDesignUI.getId() ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry#unregisterComponentSurfaceDesignUI(org.gamegineer.table.ui.ComponentSurfaceDesignUI)
     */
    @Override
    public void unregisterComponentSurfaceDesignUI(
        final ComponentSurfaceDesignUI componentSurfaceDesignUI )
    {
        assertArgumentNotNull( componentSurfaceDesignUI, "componentSurfaceDesignUI" ); //$NON-NLS-1$
        assertArgumentLegal( componentSurfaceDesignUIs_.remove( componentSurfaceDesignUI.getId(), componentSurfaceDesignUI ), "componentSurfaceDesignUI", NonNlsMessages.ComponentSurfaceDesignUIRegistry_unregisterComponentSurfaceDesignUI_componentSurfaceDesignUI_unregistered( componentSurfaceDesignUI.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Unregistered component surface design user interface '%1$s'", componentSurfaceDesignUI.getId() ) ); //$NON-NLS-1$
    }
}
