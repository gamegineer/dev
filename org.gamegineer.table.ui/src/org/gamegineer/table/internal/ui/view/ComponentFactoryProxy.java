/*
 * ComponentFactoryProxy.java
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
 * Created on Oct 18, 2012 at 10:52:21 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.ui.ComponentFactoryException;
import org.gamegineer.table.ui.IComponentFactory;

/**
 * A proxy for lazily creating a component factory from an extension point.
 */
@NotThreadSafe
final class ComponentFactoryProxy
    implements IComponentFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the configuration element attribute that specifies the
     * component factory class name.
     */
    private final String classNameAttributeName_;

    /** The component factory configuration element. */
    private final IConfigurationElement configurationElement_;

    /**
     * The component factory to which all operations are delegated or
     * {@code null} if the component factory delegate has not yet been created.
     */
    private IComponentFactory delegate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentFactoryProxy} class.
     * 
     * @param configurationElement
     *        The component factory configuration element; must not be
     *        {@code null}.
     * @param classNameAttributeName
     *        The name of the configuration element attribute that specifies the
     *        component factory class name; must not be {@code null}.
     */
    ComponentFactoryProxy(
        /* @NonNull */
        final IConfigurationElement configurationElement,
        /* @NonNull */
        final String classNameAttributeName )
    {
        assert configurationElement != null;
        assert classNameAttributeName != null;

        classNameAttributeName_ = classNameAttributeName;
        configurationElement_ = configurationElement;
        delegate_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.IComponentFactory#createComponent(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    public IComponent createComponent(
        final ITableEnvironment tableEnvironment )
        throws ComponentFactoryException
    {
        assertArgumentNotNull( tableEnvironment, "tableEnvironment" ); //$NON-NLS-1$

        return getDelegate().createComponent( tableEnvironment );
    }

    /**
     * Gets the component factory to which all operations are delegated.
     * 
     * @return The component factory to which all operations are delegated;
     *         never {@code null}.
     * 
     * @throws org.gamegineer.table.ui.ComponentFactoryException
     *         If the component factory delegate is not available.
     */
    /* @NonNull */
    private IComponentFactory getDelegate()
        throws ComponentFactoryException
    {
        if( delegate_ == null )
        {
            try
            {
                delegate_ = (IComponentFactory)configurationElement_.createExecutableExtension( classNameAttributeName_ );
            }
            catch( final CoreException e )
            {
                throw new ComponentFactoryException( NonNlsMessages.ComponentFactoryProxy_getDelegate_createError( configurationElement_.getAttribute( classNameAttributeName_ ) ), e );
            }
        }

        return delegate_;
    }
}
