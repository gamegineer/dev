/*
 * ContainerLayoutRegistry.java
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
 * Created on Aug 9, 2012 at 8:30:21 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerLayoutRegistry;

/**
 * Implementation of {@link IContainerLayoutRegistry}.
 */
@ThreadSafe
public final class ContainerLayoutRegistry
    implements IContainerLayoutRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of container layouts directly managed by this object. */
    private final ConcurrentMap<ContainerLayoutId, IContainerLayout> containerLayouts_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayoutRegistry} class.
     */
    public ContainerLayoutRegistry()
    {
        containerLayouts_ = new ConcurrentHashMap<ContainerLayoutId, IContainerLayout>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IContainerLayoutRegistry#getContainerLayout(org.gamegineer.table.core.ContainerLayoutId)
     */
    @Override
    public IContainerLayout getContainerLayout(
        final ContainerLayoutId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return containerLayouts_.get( id );
    }

    /*
     * @see org.gamegineer.table.core.IContainerLayoutRegistry#getContainerLayouts()
     */
    @Override
    public Collection<IContainerLayout> getContainerLayouts()
    {
        return new ArrayList<IContainerLayout>( containerLayouts_.values() );
    }

    /*
     * @see org.gamegineer.table.core.IContainerLayoutRegistry#registerContainerLayout(org.gamegineer.table.core.IContainerLayout)
     */
    @Override
    public void registerContainerLayout(
        final IContainerLayout containerLayout )
    {
        assertArgumentNotNull( containerLayout, "containerLayout" ); //$NON-NLS-1$
        assertArgumentLegal( containerLayouts_.putIfAbsent( containerLayout.getId(), containerLayout ) == null, "containerLayout", NonNlsMessages.ContainerLayoutRegistry_registerContainerLayout_containerLayout_registered( containerLayout.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered container layout '%1$s'", containerLayout.getId() ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IContainerLayoutRegistry#unregisterContainerLayout(org.gamegineer.table.core.IContainerLayout)
     */
    @Override
    public void unregisterContainerLayout(
        final IContainerLayout containerLayout )
    {
        assertArgumentNotNull( containerLayout, "containerLayout" ); //$NON-NLS-1$
        assertArgumentLegal( containerLayouts_.remove( containerLayout.getId(), containerLayout ), "containerLayout", NonNlsMessages.ContainerLayoutRegistry_unregisterContainerLayout_containerLayout_unregistered( containerLayout.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Unregistered container layout '%1$s'", containerLayout.getId() ) ); //$NON-NLS-1$
    }
}
