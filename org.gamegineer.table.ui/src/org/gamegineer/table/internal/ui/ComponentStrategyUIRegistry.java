/*
 * ComponentStrategyUIRegistry.java
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
 * Created on Sep 27, 2012 at 8:30:25 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.ui.IComponentStrategyUI;
import org.gamegineer.table.ui.IComponentStrategyUIRegistry;

/**
 * Implementation of
 * {@link org.gamegineer.table.ui.IComponentStrategyUIRegistry}.
 */
@ThreadSafe
public final class ComponentStrategyUIRegistry
    implements IComponentStrategyUIRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of component strategy user interfaces directly managed by
     * this object.
     */
    private final ConcurrentMap<ComponentStrategyId, IComponentStrategyUI> componentStrategyUIs_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategyUIRegistry}
     * class.
     */
    public ComponentStrategyUIRegistry()
    {
        componentStrategyUIs_ = new ConcurrentHashMap<ComponentStrategyId, IComponentStrategyUI>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.IComponentStrategyUIRegistry#getComponentStrategyUI(org.gamegineer.table.core.ComponentStrategyId)
     */
    @Override
    public IComponentStrategyUI getComponentStrategyUI(
        final ComponentStrategyId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return componentStrategyUIs_.get( id );
    }

    /*
     * @see org.gamegineer.table.ui.IComponentStrategyUIRegistry#getComponentStrategyUIs()
     */
    @Override
    public Collection<IComponentStrategyUI> getComponentStrategyUIs()
    {
        return new ArrayList<IComponentStrategyUI>( componentStrategyUIs_.values() );
    }

    /*
     * @see org.gamegineer.table.ui.IComponentStrategyUIRegistry#registerComponentStrategyUI(org.gamegineer.table.ui.IComponentStrategyUI)
     */
    @Override
    public void registerComponentStrategyUI(
        final IComponentStrategyUI componentStrategyUI )
    {
        assertArgumentNotNull( componentStrategyUI, "componentStrategyUI" ); //$NON-NLS-1$
        assertArgumentLegal( componentStrategyUIs_.putIfAbsent( componentStrategyUI.getId(), componentStrategyUI ) == null, "componentStrategyUI", NonNlsMessages.ComponentStrategyUIRegistry_registerComponentStrategyUI_componentStrategyUI_registered( componentStrategyUI.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered component strategy user interface '%1$s'", componentStrategyUI.getId() ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.ui.IComponentStrategyUIRegistry#unregisterComponentStrategyUI(org.gamegineer.table.ui.IComponentStrategyUI)
     */
    @Override
    public void unregisterComponentStrategyUI(
        final IComponentStrategyUI componentStrategyUI )
    {
        assertArgumentNotNull( componentStrategyUI, "componentStrategyUI" ); //$NON-NLS-1$
        assertArgumentLegal( componentStrategyUIs_.remove( componentStrategyUI.getId(), componentStrategyUI ), "componentStrategyUI", NonNlsMessages.ComponentStrategyUIRegistry_unregisterComponentStrategyUI_componentStrategyUI_unregistered( componentStrategyUI.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Unregistered component strategy user interface '%1$s'", componentStrategyUI.getId() ) ); //$NON-NLS-1$
    }
}
