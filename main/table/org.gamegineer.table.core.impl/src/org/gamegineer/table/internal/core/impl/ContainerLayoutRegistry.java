/*
 * ContainerLayoutRegistry.java
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
 * Created on Aug 9, 2012 at 8:30:21 PM.
 */

package org.gamegineer.table.internal.core.impl;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.common.core.util.registry.AbstractRegistry;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.ContainerLayouts;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerLayoutRegistry;

/**
 * Implementation of {@link IContainerLayoutRegistry}.
 */
@ThreadSafe
public final class ContainerLayoutRegistry
    extends AbstractRegistry<@NonNull ContainerLayoutId, @NonNull IContainerLayout>
    implements IContainerLayoutRegistry
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayoutRegistry} class.
     */
    public ContainerLayoutRegistry()
    {
        registerObject( ContainerLayouts.NULL );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.AbstractRegistry#getObjectId(java.lang.Object)
     */
    @Override
    protected ContainerLayoutId getObjectId(
        final IContainerLayout object )
    {
        return object.getId();
    }
}
