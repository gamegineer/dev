/*
 * ComponentPrototypeFactoryProxy.java
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
 * Created on Oct 18, 2012 at 10:52:21 PM.
 */

package org.gamegineer.table.internal.ui.impl.prototype;

import java.util.List;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.ui.prototype.ComponentPrototypeFactoryException;
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;

/**
 * A proxy for lazily creating a component prototype factory from an extension
 * point.
 */
@NotThreadSafe
final class ComponentPrototypeFactoryProxy
    implements IComponentPrototypeFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component prototype factory configuration element. */
    private final IConfigurationElement configurationElement_;

    /**
     * The component prototype factory to which all operations are delegated or
     * {@code null} if the component prototype factory delegate has not yet been
     * created.
     */
    @Nullable
    private IComponentPrototypeFactory delegate_;

    /**
     * The name of the configuration element property (attribute or child
     * element) that specifies the component prototype factory executable
     * extension.
     */
    private final String propertyName_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPrototypeFactoryProxy}
     * class.
     * 
     * @param configurationElement
     *        The component prototype factory configuration element; must not be
     *        {@code null}.
     * @param propertyName
     *        The name of the configuration element property (attribute or child
     *        element) that specifies the component prototype factory executable
     *        extension; must not be {@code null}.
     */
    ComponentPrototypeFactoryProxy(
        final IConfigurationElement configurationElement,
        final String propertyName )
    {
        configurationElement_ = configurationElement;
        delegate_ = null;
        propertyName_ = propertyName;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.prototype.IComponentPrototypeFactory#createComponentPrototype(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    public List<IComponent> createComponentPrototype(
        final ITableEnvironment tableEnvironment )
        throws ComponentPrototypeFactoryException
    {
        return getDelegate().createComponentPrototype( tableEnvironment );
    }

    /**
     * Gets the component prototype factory to which all operations are
     * delegated.
     * 
     * @return The component prototype factory to which all operations are
     *         delegated; never {@code null}.
     * 
     * @throws org.gamegineer.table.ui.prototype.ComponentPrototypeFactoryException
     *         If the component prototype factory delegate is not available.
     */
    private IComponentPrototypeFactory getDelegate()
        throws ComponentPrototypeFactoryException
    {
        if( delegate_ == null )
        {
            try
            {
                delegate_ = (IComponentPrototypeFactory)configurationElement_.createExecutableExtension( propertyName_ );
            }
            catch( final CoreException e )
            {
                throw new ComponentPrototypeFactoryException( NonNlsMessages.ComponentPrototypeFactoryProxy_getDelegate_createError( configurationElement_ ), e );
            }
        }

        assert delegate_ != null;
        return delegate_;
    }
}
