/*
 * ComponentStrategyRegistry.java
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
 * Created on Aug 3, 2012 at 9:30:10 PM.
 */

package org.gamegineer.table.internal.core.impl;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.common.core.util.registry.AbstractRegistry;
import org.gamegineer.table.core.ComponentStrategies;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IComponentStrategyRegistry;

/**
 * Implementation of {@link IComponentStrategyRegistry}.
 */
@ThreadSafe
public final class ComponentStrategyRegistry
    extends AbstractRegistry<@NonNull ComponentStrategyId, @NonNull IComponentStrategy>
    implements IComponentStrategyRegistry
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategyRegistry}
     * class.
     */
    public ComponentStrategyRegistry()
    {
        registerObject( ComponentStrategies.NULL_COMPONENT );
        registerObject( ComponentStrategies.NULL_CONTAINER );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.AbstractRegistry#getObjectId(java.lang.Object)
     */
    @Override
    protected ComponentStrategyId getObjectId(
        final IComponentStrategy object )
    {
        return object.getId();
    }
}
