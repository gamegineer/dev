/*
 * ComponentFactoriesExtensionPointTest.java
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
 * Created on Oct 9, 2012 at 8:33:25 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.KeyStroke;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.ComponentFactoriesExtensionPoint}
 * class.
 */
public final class ComponentFactoriesExtensionPointTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentFactoriesExtensionPointTest} class.
     */
    public ComponentFactoriesExtensionPointTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component factory category configuration element.
     * 
     * @param id
     *        The category identifier; may be {@code null}.
     * @param mnemonic
     *        The category mnemonic; may be {@code null}.
     * @param name
     *        The category name; may be {@code null}.
     * @param parentCategory
     *        The category parent category; may be {@code null}.
     * 
     * @return A new component factory category configuration element; never
     *         {@code null}.
     */
    /* @NonNull */
    private IConfigurationElement createCategoryConfigurationElement(
        /* @Nullable */
        final String id,
        /* @Nullable */
        final String mnemonic,
        /* @Nullable */
        final String name,
        /* @Nullable */
        final String parentCategory )
    {
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getName() ).andReturn( "category" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( id ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "mnemonic" ) ).andReturn( mnemonic ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "name" ) ).andReturn( name ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "parentCategory" ) ).andReturn( parentCategory ).anyTimes(); //$NON-NLS-1$
        return configurationElement;
    }

    /**
     * Creates a new component factory configuration element.
     * 
     * @param category
     *        The component factory category; may be {@code null}.
     * @param id
     *        The component factory identifier; may be {@code null}.
     * @param mnemonic
     *        The component factory mnemonic; may be {@code null}.
     * @param name
     *        The component factory name; may be {@code null}.
     * 
     * @return A new component factory configuration element; never {@code null}
     *         .
     */
    /* @NonNull */
    private IConfigurationElement createComponentFactoryConfigurationElement(
        /* @Nullable */
        final String category,
        /* @Nullable */
        final String id,
        /* @Nullable */
        final String mnemonic,
        /* @Nullable */
        final String name )
    {
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getName() ).andReturn( "componentFactory" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "category" ) ).andReturn( category ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( id ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "mnemonic" ) ).andReturn( mnemonic ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "name" ) ).andReturn( name ).anyTimes(); //$NON-NLS-1$
        return configurationElement;
    }

    /**
     * Encodes the specified category path as a string.
     * 
     * @param path
     *        The category path; must not be {@code null}.
     * 
     * @return The encoded category path; never {@code null}.
     */
    /* @NonNull */
    private static String encodeCategoryPath(
        /* @NonNull */
        final List<String> path )
    {
        assert path != null;

        final StringBuilder sb = new StringBuilder();
        final String separator = "/"; //$NON-NLS-1$

        for( final String element : path )
        {
            sb.append( element );
            sb.append( separator );
        }

        if( sb.length() > 0 )
        {
            sb.delete( sb.length() - separator.length(), sb.length() );
        }

        return sb.toString();
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        mocksControl_ = EasyMock.createControl();
    }

    /**
     * Ensures the {@link ComponentFactoriesExtensionPoint#createCategory}
     * method throws an exception when passed an illegal configuration element
     * that is missing the identifier attribute.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCategory_ConfigurationElement_Illegal_MissingIdAttribute()
    {
        final IConfigurationElement configurationElement = createCategoryConfigurationElement( null, "mnemonic", "name", "parentCategory" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        mocksControl_.replay();

        ComponentFactoriesExtensionPointFacade.createCategory( configurationElement );
    }

    /**
     * Ensures the {@link ComponentFactoriesExtensionPoint#createCategory}
     * method throws an exception when passed an illegal configuration element
     * that is missing the mnemonic attribute.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCategory_ConfigurationElement_Illegal_MissingMnemonicAttribute()
    {
        final IConfigurationElement configurationElement = createCategoryConfigurationElement( "id", null, "name", "parentCategory" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        mocksControl_.replay();

        ComponentFactoriesExtensionPointFacade.createCategory( configurationElement );
    }

    /**
     * Ensures the {@link ComponentFactoriesExtensionPoint#createCategory}
     * method throws an exception when passed an illegal configuration element
     * that is missing the name attribute.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCategory_ConfigurationElement_Illegal_MissingNameAttribute()
    {
        final IConfigurationElement configurationElement = createCategoryConfigurationElement( "id", "mnemonic", null, "parentCategory" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        mocksControl_.replay();

        ComponentFactoriesExtensionPointFacade.createCategory( configurationElement );
    }

    /**
     * Ensures the {@link ComponentFactoriesExtensionPoint#createCategory}
     * method creates a component factory category from a legal configuration
     * element that has no parent category.
     */
    @Test
    public void testCreateCategory_ConfigurationElement_Legal_NoParentCategory()
    {
        final String expectedId = "id"; //$NON-NLS-1$
        final String encodedExpectedMnemonic = "1"; //$NON-NLS-1$
        final int expectedMnemonic = KeyStroke.getKeyStroke( encodedExpectedMnemonic ).getKeyCode();
        final String expectedName = "name"; //$NON-NLS-1$
        final List<String> expectedPath = Arrays.asList( expectedId );
        final IConfigurationElement configurationElement = createCategoryConfigurationElement( expectedId, encodedExpectedMnemonic, expectedName, null );
        mocksControl_.replay();

        final ComponentFactoryCategory actualValue = ComponentFactoriesExtensionPointFacade.createCategory( configurationElement );

        mocksControl_.verify();
        assertEquals( expectedId, actualValue.getId() );
        assertEquals( expectedMnemonic, actualValue.getMnemonic() );
        assertEquals( expectedName, actualValue.getName() );
        assertEquals( expectedPath, actualValue.getPath() );
    }

    /**
     * Ensures the {@link ComponentFactoriesExtensionPoint#createCategory}
     * method creates a component factory category from a legal configuration
     * element that has a parent category.
     */
    @Test
    public void testCreateCategory_ConfigurationElement_Legal_ParentCategory()
    {
        final String expectedId = "id"; //$NON-NLS-1$
        final String encodedExpectedMnemonic = "1"; //$NON-NLS-1$
        final int expectedMnemonic = KeyStroke.getKeyStroke( encodedExpectedMnemonic ).getKeyCode();
        final String expectedName = "name"; //$NON-NLS-1$
        final List<String> parentPath = Arrays.asList( "ancestor1Id", "ancestor2Id", "ancestor3Id" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        final List<String> expectedPath = new ArrayList<String>( parentPath );
        expectedPath.add( expectedId );
        final IConfigurationElement configurationElement = createCategoryConfigurationElement( expectedId, encodedExpectedMnemonic, expectedName, encodeCategoryPath( parentPath ) );
        mocksControl_.replay();

        final ComponentFactoryCategory actualValue = ComponentFactoriesExtensionPointFacade.createCategory( configurationElement );

        mocksControl_.verify();
        assertEquals( expectedId, actualValue.getId() );
        assertEquals( expectedMnemonic, actualValue.getMnemonic() );
        assertEquals( expectedName, actualValue.getName() );
        assertEquals( expectedPath, actualValue.getPath() );
    }

    /**
     * Ensures the
     * {@link ComponentFactoriesExtensionPoint#createComponentFactory} method
     * throws an exception when passed an illegal configuration element that is
     * missing the identifier attribute.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentFactory_ConfigurationElement_Illegal_MissingIdAttribute()
    {
        final IConfigurationElement configurationElement = createComponentFactoryConfigurationElement( "category", null, "mnemonic", "name" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        mocksControl_.replay();

        ComponentFactoriesExtensionPointFacade.createComponentFactory( configurationElement );
    }

    /**
     * Ensures the
     * {@link ComponentFactoriesExtensionPoint#createComponentFactory} method
     * throws an exception when passed an illegal configuration element that is
     * missing the mnemonic attribute.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentFactory_ConfigurationElement_Illegal_MissingMnemonicAttribute()
    {
        final IConfigurationElement configurationElement = createComponentFactoryConfigurationElement( "category", "id", null, "name" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        mocksControl_.replay();

        ComponentFactoriesExtensionPointFacade.createComponentFactory( configurationElement );
    }

    /**
     * Ensures the
     * {@link ComponentFactoriesExtensionPoint#createComponentFactory} method
     * throws an exception when passed an illegal configuration element that is
     * missing the name attribute.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentFactory_ConfigurationElement_Illegal_MissingNameAttribute()
    {
        final IConfigurationElement configurationElement = createComponentFactoryConfigurationElement( "category", "id", "mnemonic", null ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        mocksControl_.replay();

        ComponentFactoriesExtensionPointFacade.createComponentFactory( configurationElement );
    }

    /**
     * Ensures the {@link ComponentFactoriesExtensionPoint#createCategory}
     * method creates a component factory category from a legal configuration
     * element that has a category.
     */
    @Test
    public void testCreateComponentFactory_ConfigurationElement_Legal_Category()
    {
        final String expectedCategoryId = "categoryId"; //$NON-NLS-1$
        final String expectedId = "id"; //$NON-NLS-1$
        final String encodedExpectedMnemonic = "1"; //$NON-NLS-1$
        final int expectedMnemonic = KeyStroke.getKeyStroke( encodedExpectedMnemonic ).getKeyCode();
        final String expectedName = "name"; //$NON-NLS-1$
        final IConfigurationElement configurationElement = createComponentFactoryConfigurationElement( expectedCategoryId, expectedId, encodedExpectedMnemonic, expectedName );
        mocksControl_.replay();

        final ComponentFactory actualValue = ComponentFactoriesExtensionPointFacade.createComponentFactory( configurationElement );

        mocksControl_.verify();
        assertEquals( expectedCategoryId, actualValue.getCategoryId() );
        assertEquals( expectedId, actualValue.getId() );
        assertEquals( expectedMnemonic, actualValue.getMnemonic() );
        assertEquals( expectedName, actualValue.getName() );
    }

    /**
     * Ensures the
     * {@link ComponentFactoriesExtensionPoint#createComponentFactory} method
     * creates a component factory from a legal configuration element that has
     * no category.
     */
    @Test
    public void testCreateComponentFactory_ConfigurationElement_Legal_NoCategory()
    {
        final String expectedCategoryId = null;
        final String expectedId = "id"; //$NON-NLS-1$
        final String encodedExpectedMnemonic = "1"; //$NON-NLS-1$
        final int expectedMnemonic = KeyStroke.getKeyStroke( encodedExpectedMnemonic ).getKeyCode();
        final String expectedName = "name"; //$NON-NLS-1$
        final IConfigurationElement configurationElement = createComponentFactoryConfigurationElement( expectedCategoryId, expectedId, encodedExpectedMnemonic, expectedName );
        mocksControl_.replay();

        final ComponentFactory actualValue = ComponentFactoriesExtensionPointFacade.createComponentFactory( configurationElement );

        mocksControl_.verify();
        assertEquals( expectedCategoryId, actualValue.getCategoryId() );
        assertEquals( expectedId, actualValue.getId() );
        assertEquals( expectedMnemonic, actualValue.getMnemonic() );
        assertEquals( expectedName, actualValue.getName() );
    }
}
