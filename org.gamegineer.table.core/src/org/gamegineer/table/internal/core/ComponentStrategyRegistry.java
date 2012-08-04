/*
 * ComponentStrategyRegistry.java
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
 * Created on Aug 3, 2012 at 9:30:10 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IComponentStrategyRegistry;

/**
 * Implementation of {@link IComponentStrategyRegistry}.
 */
@ThreadSafe
public final class ComponentStrategyRegistry
    implements IComponentStrategyRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of component strategies directly managed by this object. */
    private final ConcurrentMap<ComponentStrategyId, IComponentStrategy> componentStrategies_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategyRegistry}
     * class.
     */
    public ComponentStrategyRegistry()
    {
        componentStrategies_ = new ConcurrentHashMap<ComponentStrategyId, IComponentStrategy>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IComponentStrategyRegistry#getComponentStrategy(org.gamegineer.table.core.ComponentStrategyId)
     */
    @Override
    public IComponentStrategy getComponentStrategy(
        final ComponentStrategyId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return componentStrategies_.get( id );
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategyRegistry#getComponentStrategies()
     */
    @Override
    public Collection<IComponentStrategy> getComponentStrategies()
    {
        return new ArrayList<IComponentStrategy>( componentStrategies_.values() );
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategyRegistry#registerComponentStrategy(org.gamegineer.table.core.IComponentStrategy)
     */
    @Override
    public void registerComponentStrategy(
        final IComponentStrategy componentStrategy )
    {
        assertArgumentNotNull( componentStrategy, "componentStrategy" ); //$NON-NLS-1$
        assertArgumentLegal( componentStrategies_.putIfAbsent( componentStrategy.getId(), componentStrategy ) == null, "componentStrategy", NonNlsMessages.ComponentStrategyRegistry_registerComponentStrategy_componentStrategy_registered( componentStrategy.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered component strategy '%1$s'", componentStrategy.getId() ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategyRegistry#unregisterComponentStrategy(org.gamegineer.table.core.IComponentStrategy)
     */
    @Override
    public void unregisterComponentStrategy(
        final IComponentStrategy componentStrategy )
    {
        assertArgumentNotNull( componentStrategy, "componentStrategy" ); //$NON-NLS-1$
        assertArgumentLegal( componentStrategies_.remove( componentStrategy.getId(), componentStrategy ), "componentStrategy", NonNlsMessages.ComponentStrategyRegistry_unregisterComponentStrategy_componentStrategy_unregistered( componentStrategy.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Unregistered component strategy '%1$s'", componentStrategy.getId() ) ); //$NON-NLS-1$
    }
}
