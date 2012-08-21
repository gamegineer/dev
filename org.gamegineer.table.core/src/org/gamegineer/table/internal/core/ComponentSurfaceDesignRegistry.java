/*
 * ComponentSurfaceDesignRegistry.java
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
 * Created on Apr 7, 2012 at 9:37:33 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IComponentSurfaceDesignRegistry;
import org.gamegineer.table.internal.core.surfacedesigns.ComponentSurfaceDesigns;

/**
 * Implementation of
 * {@link org.gamegineer.table.core.IComponentSurfaceDesignRegistry}.
 */
@ThreadSafe
public final class ComponentSurfaceDesignRegistry
    implements IComponentSurfaceDesignRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of component surface designs directly managed by this
     * object.
     */
    private final ConcurrentMap<ComponentSurfaceDesignId, ComponentSurfaceDesign> componentSurfaceDesigns_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesignRegistry}
     * class.
     */
    public ComponentSurfaceDesignRegistry()
    {
        componentSurfaceDesigns_ = new ConcurrentHashMap<ComponentSurfaceDesignId, ComponentSurfaceDesign>();

        // FIXME: Hack to ensure programatically registered surface designs are available
        // before bundles at the default start level are running.  If we don't do it here
        // (or via the extension registry), these surface designs won't be available during
        // unit tests.
        registerComponentSurfaceDesign( ComponentSurfaceDesigns.DEFAULT_CARD );
        registerComponentSurfaceDesign( ComponentSurfaceDesigns.DEFAULT_CARD_PILE );
        registerComponentSurfaceDesign( ComponentSurfaceDesigns.DEFAULT_TABLETOP );
        registerComponentSurfaceDesign( ComponentSurfaceDesigns.NULL );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IComponentSurfaceDesignRegistry#getComponentSurfaceDesign(org.gamegineer.table.core.ComponentSurfaceDesignId)
     */
    @Override
    public ComponentSurfaceDesign getComponentSurfaceDesign(
        final ComponentSurfaceDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return componentSurfaceDesigns_.get( id );
    }

    /*
     * @see org.gamegineer.table.core.IComponentSurfaceDesignRegistry#getComponentSurfaceDesigns()
     */
    @Override
    public Collection<ComponentSurfaceDesign> getComponentSurfaceDesigns()
    {
        return new ArrayList<ComponentSurfaceDesign>( componentSurfaceDesigns_.values() );
    }

    /*
     * @see org.gamegineer.table.core.IComponentSurfaceDesignRegistry#registerComponentSurfaceDesign(org.gamegineer.table.core.ComponentSurfaceDesign)
     */
    @Override
    public void registerComponentSurfaceDesign(
        final ComponentSurfaceDesign componentSurfaceDesign )
    {
        assertArgumentNotNull( componentSurfaceDesign, "componentSurfaceDesign" ); //$NON-NLS-1$
        assertArgumentLegal( componentSurfaceDesigns_.putIfAbsent( componentSurfaceDesign.getId(), componentSurfaceDesign ) == null, "componentSurfaceDesign", NonNlsMessages.ComponentSurfaceDesignRegistry_registerComponentSurfaceDesign_componentSurfaceDesign_registered( componentSurfaceDesign.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered component surface design '%1$s'", componentSurfaceDesign.getId() ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IComponentSurfaceDesignRegistry#unregisterComponentSurfaceDesign(org.gamegineer.table.core.ComponentSurfaceDesign)
     */
    @Override
    public void unregisterComponentSurfaceDesign(
        final ComponentSurfaceDesign componentSurfaceDesign )
    {
        assertArgumentNotNull( componentSurfaceDesign, "componentSurfaceDesign" ); //$NON-NLS-1$
        assertArgumentLegal( componentSurfaceDesigns_.remove( componentSurfaceDesign.getId(), componentSurfaceDesign ), "componentSurfaceDesign", NonNlsMessages.ComponentSurfaceDesignRegistry_unregisterComponentSurfaceDesign_componentSurfaceDesign_unregistered( componentSurfaceDesign.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Unregistered component surface design '%1$s'", componentSurfaceDesign.getId() ) ); //$NON-NLS-1$
    }
}
