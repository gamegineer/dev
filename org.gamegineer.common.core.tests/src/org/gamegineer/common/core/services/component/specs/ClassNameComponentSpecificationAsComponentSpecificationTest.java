/*
 * ClassNameComponentSpecificationAsComponentSpecificationTest.java
 * Copyright 2008 Gamegineer.org
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
 * Created on May 17, 2008 at 1:29:39 PM.
 */

package org.gamegineer.common.core.services.component.specs;

import org.gamegineer.common.core.services.component.AbstractComponentSpecificationTestCase;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.common.core.services.component.IComponentSpecification;
import org.gamegineer.common.core.services.component.MockComponentFactory;
import org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.core.services.component.specs.ClassNameComponentSpecification}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.services.component.IComponentSpecification}
 * interface.
 */
public final class ClassNameComponentSpecificationAsComponentSpecificationTest
    extends AbstractComponentSpecificationTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ClassNameComponentSpecificationAsComponentSpecificationTest}
     * class.
     */
    public ClassNameComponentSpecificationAsComponentSpecificationTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.AbstractComponentSpecificationTestCase#createComponentSpecification()
     */
    @Override
    protected IComponentSpecification createComponentSpecification()
    {
        return new ClassNameComponentSpecification( String.class.getName() );
    }

    /*
     * @see org.gamegineer.common.core.services.component.AbstractComponentSpecificationTestCase#createMatchingComponentFactory()
     */
    @Override
    protected IComponentFactory createMatchingComponentFactory()
    {
        final MockComponentFactory factory = new MockComponentFactory();
        SupportedClassNamesAttribute.INSTANCE.setValue( factory, String.class.getName() );
        return factory;
    }

    /*
     * @see org.gamegineer.common.core.services.component.AbstractComponentSpecificationTestCase#createNonMatchingComponentFactory()
     */
    @Override
    protected IComponentFactory createNonMatchingComponentFactory()
    {
        final MockComponentFactory factory = new MockComponentFactory();
        SupportedClassNamesAttribute.INSTANCE.setValue( factory, Integer.class.getName() );
        return factory;
    }
}
