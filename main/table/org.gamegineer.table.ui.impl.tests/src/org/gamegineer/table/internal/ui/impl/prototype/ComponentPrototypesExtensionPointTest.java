/*
 * ComponentPrototypesExtensionPointTest.java
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
 * Created on Oct 9, 2012 at 8:33:25 PM.
 */

package org.gamegineer.table.internal.ui.impl.prototype;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.swing.KeyStroke;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ComponentPrototypesExtensionPoint} class.
 */
public final class ComponentPrototypesExtensionPointTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default component prototype category attribute value. */
    private static final String DEFAULT_COMPONENT_PROTOTYPE_ATTR_CATEGORY = "category"; //$NON-NLS-1$

    /** The default component prototype mnemonic attribute value. */
    private static final String DEFAULT_COMPONENT_PROTOTYPE_ATTR_MNEMONIC = "M"; //$NON-NLS-1$

    /** The default component prototype name attribute value. */
    private static final String DEFAULT_COMPONENT_PROTOTYPE_ATTR_NAME = "name"; //$NON-NLS-1$

    /** The default component prototype factory class attribute value. */
    private static final String DEFAULT_COMPONENT_PROTOTYPE_FACTORY_ATTR_CLASS = "class"; //$NON-NLS-1$

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentPrototypesExtensionPointTest} class.
     */
    public ComponentPrototypesExtensionPointTest()
    {
        mocksControl_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component prototype configuration element.
     * 
     * @param category
     *        The component prototype category.
     * @param mnemonic
     *        The component prototype mnemonic.
     * @param name
     *        The component prototype name.
     * @param factoryClass
     *        The component prototype factory class name.
     * 
     * @return A new component prototype configuration element.
     */
    private IConfigurationElement createComponentPrototypeConfigurationElement(
        final @Nullable String category,
        final @Nullable String mnemonic,
        final @Nullable String name,
        final @Nullable String factoryClass )
    {
        final IMocksControl mocksControl = getMocksControl();
        final IConfigurationElement configurationElement = mocksControl.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getName() ).andReturn( "componentPrototype" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "category" ) ).andReturn( category ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "mnemonic" ) ).andReturn( mnemonic ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "name" ) ).andReturn( name ).anyTimes(); //$NON-NLS-1$

        if( factoryClass != null )
        {
            final IConfigurationElement factoryConfigurationElement = mocksControl.createMock( IConfigurationElement.class );
            EasyMock.expect( factoryConfigurationElement.getName() ).andReturn( "factory" ).anyTimes(); //$NON-NLS-1$
            EasyMock.expect( factoryConfigurationElement.getAttribute( "class" ) ).andReturn( factoryClass ).anyTimes(); //$NON-NLS-1$

            EasyMock.expect( configurationElement.getChildren( "factory" ) ).andReturn( new IConfigurationElement[] { //$NON-NLS-1$
                factoryConfigurationElement
            } ).anyTimes();
        }
        else
        {
            EasyMock.expect( configurationElement.getChildren( "factory" ) ).andReturn( new IConfigurationElement[ 0 ] ).anyTimes(); //$NON-NLS-1$
        }

        return configurationElement;
    }

    /**
     * Creates a new component prototype category configuration element.
     * 
     * @param id
     *        The component prototype category identifier.
     * @param mnemonic
     *        The component prototype category mnemonic.
     * @param name
     *        The component prototype category name.
     * @param parentCategory
     *        The component prototype category parent category.
     * 
     * @return A new component prototype category configuration element.
     */
    private IConfigurationElement createComponentPrototypeCategoryConfigurationElement(
        final @Nullable String id,
        final @Nullable String mnemonic,
        final @Nullable String name,
        final @Nullable String parentCategory )
    {
        final IMocksControl mocksControl = getMocksControl();
        final IConfigurationElement configurationElement = mocksControl.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getName() ).andReturn( "category" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( id ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "mnemonic" ) ).andReturn( mnemonic ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "name" ) ).andReturn( name ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "parentCategory" ) ).andReturn( parentCategory ).anyTimes(); //$NON-NLS-1$
        return configurationElement;
    }

    /**
     * Encodes the specified category path as a string.
     * 
     * @param path
     *        The category path.
     * 
     * @return The encoded category path.
     */
    private static String encodeCategoryPath(
        final List<String> path )
    {
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
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control.
     */
    private IMocksControl getMocksControl()
    {
        return mocksControl_.get();
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
        mocksControl_ = Optional.of( EasyMock.createControl() );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototype} method
     * throws an exception when passed an illegal configuration element that is
     * missing the factory element.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentPrototype_ConfigurationElement_Illegal_MissingFactoryElement()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IConfigurationElement configurationElement = createComponentPrototypeConfigurationElement( //
            DEFAULT_COMPONENT_PROTOTYPE_ATTR_CATEGORY, //
            DEFAULT_COMPONENT_PROTOTYPE_ATTR_MNEMONIC, //
            DEFAULT_COMPONENT_PROTOTYPE_ATTR_NAME, //
            null );
        mocksControl.replay();

        ComponentPrototypesExtensionPointFacade.createComponentPrototype( configurationElement );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototype} method
     * throws an exception when passed an illegal configuration element that is
     * missing the mnemonic attribute.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentPrototype_ConfigurationElement_Illegal_MissingMnemonicAttribute()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IConfigurationElement configurationElement = createComponentPrototypeConfigurationElement( //
            DEFAULT_COMPONENT_PROTOTYPE_ATTR_CATEGORY, //
            null, //
            DEFAULT_COMPONENT_PROTOTYPE_ATTR_NAME, //
            DEFAULT_COMPONENT_PROTOTYPE_FACTORY_ATTR_CLASS );
        mocksControl.replay();

        ComponentPrototypesExtensionPointFacade.createComponentPrototype( configurationElement );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototype} method
     * throws an exception when passed an illegal configuration element that is
     * missing the name attribute.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentPrototype_ConfigurationElement_Illegal_MissingNameAttribute()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IConfigurationElement configurationElement = createComponentPrototypeConfigurationElement( //
            DEFAULT_COMPONENT_PROTOTYPE_ATTR_CATEGORY, //
            DEFAULT_COMPONENT_PROTOTYPE_ATTR_MNEMONIC, //
            null, //
            DEFAULT_COMPONENT_PROTOTYPE_FACTORY_ATTR_CLASS );
        mocksControl.replay();

        ComponentPrototypesExtensionPointFacade.createComponentPrototype( configurationElement );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototype} method
     * creates a component prototype from a legal configuration element that has
     * a category.
     */
    @Test
    public void testCreateComponentPrototype_ConfigurationElement_Legal_Category()
    {
        final IMocksControl mocksControl = getMocksControl();
        final String expectedCategoryId = "categoryId"; //$NON-NLS-1$
        final String encodedExpectedMnemonic = "1"; //$NON-NLS-1$
        final int expectedMnemonic = KeyStroke.getKeyStroke( encodedExpectedMnemonic ).getKeyCode();
        final String expectedName = "name"; //$NON-NLS-1$
        final String expectedFactoryClass = "class"; //$NON-NLS-1$
        final IConfigurationElement configurationElement = createComponentPrototypeConfigurationElement( expectedCategoryId, encodedExpectedMnemonic, expectedName, expectedFactoryClass );
        mocksControl.replay();

        final ComponentPrototype actualValue = ComponentPrototypesExtensionPointFacade.createComponentPrototype( configurationElement );

        mocksControl.verify();
        assertEquals( expectedCategoryId, actualValue.getCategoryId() );
        assertEquals( expectedMnemonic, actualValue.getMnemonic() );
        assertEquals( expectedName, actualValue.getName() );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototype} method
     * creates a component prototype from a legal configuration element that has
     * no category.
     */
    @Test
    public void testCreateComponentPrototype_ConfigurationElement_Legal_NoCategory()
    {
        final IMocksControl mocksControl = getMocksControl();
        final String expectedCategoryId = null;
        final String encodedExpectedMnemonic = "1"; //$NON-NLS-1$
        final int expectedMnemonic = KeyStroke.getKeyStroke( encodedExpectedMnemonic ).getKeyCode();
        final String expectedName = "name"; //$NON-NLS-1$
        final String expectedFactoryClass = "class"; //$NON-NLS-1$
        final IConfigurationElement configurationElement = createComponentPrototypeConfigurationElement( expectedCategoryId, encodedExpectedMnemonic, expectedName, expectedFactoryClass );
        mocksControl.replay();

        final ComponentPrototype actualValue = ComponentPrototypesExtensionPointFacade.createComponentPrototype( configurationElement );

        mocksControl.verify();
        assertEquals( expectedCategoryId, actualValue.getCategoryId() );
        assertEquals( expectedMnemonic, actualValue.getMnemonic() );
        assertEquals( expectedName, actualValue.getName() );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototypeCategory}
     * method throws an exception when passed an illegal configuration element
     * that is missing the identifier attribute.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentPrototypeCategory_ConfigurationElement_Illegal_MissingIdAttribute()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IConfigurationElement configurationElement = createComponentPrototypeCategoryConfigurationElement( null, "mnemonic", "name", "parentCategory" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        mocksControl.replay();

        ComponentPrototypesExtensionPointFacade.createComponentPrototypeCategory( configurationElement );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototypeCategory}
     * method throws an exception when passed an illegal configuration element
     * that is missing the mnemonic attribute.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentPrototypeCategory_ConfigurationElement_Illegal_MissingMnemonicAttribute()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IConfigurationElement configurationElement = createComponentPrototypeCategoryConfigurationElement( "id", null, "name", "parentCategory" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        mocksControl.replay();

        ComponentPrototypesExtensionPointFacade.createComponentPrototypeCategory( configurationElement );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototypeCategory}
     * method throws an exception when passed an illegal configuration element
     * that is missing the name attribute.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateComponentPrototypeCategory_ConfigurationElement_Illegal_MissingNameAttribute()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IConfigurationElement configurationElement = createComponentPrototypeCategoryConfigurationElement( "id", "mnemonic", null, "parentCategory" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        mocksControl.replay();

        ComponentPrototypesExtensionPointFacade.createComponentPrototypeCategory( configurationElement );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototypeCategory}
     * method creates a component prototype category from a legal configuration
     * element that has no parent category.
     */
    @Test
    public void testCreateComponentPrototypeCategory_ConfigurationElement_Legal_NoParentCategory()
    {
        final IMocksControl mocksControl = getMocksControl();
        final String expectedId = "id"; //$NON-NLS-1$
        final String encodedExpectedMnemonic = "1"; //$NON-NLS-1$
        final int expectedMnemonic = KeyStroke.getKeyStroke( encodedExpectedMnemonic ).getKeyCode();
        final String expectedName = "name"; //$NON-NLS-1$
        final List<String> expectedPath = Arrays.asList( expectedId );
        final IConfigurationElement configurationElement = createComponentPrototypeCategoryConfigurationElement( expectedId, encodedExpectedMnemonic, expectedName, null );
        mocksControl.replay();

        final ComponentPrototypeCategory actualValue = ComponentPrototypesExtensionPointFacade.createComponentPrototypeCategory( configurationElement );

        mocksControl.verify();
        assertEquals( expectedId, actualValue.getId() );
        assertEquals( expectedMnemonic, actualValue.getMnemonic() );
        assertEquals( expectedName, actualValue.getName() );
        assertEquals( expectedPath, actualValue.getPath() );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototypeCategory}
     * method creates a component prototype category from a legal configuration
     * element that has a parent category.
     */
    @Test
    public void testCreateComponentPrototypeCategory_ConfigurationElement_Legal_ParentCategory()
    {
        final IMocksControl mocksControl = getMocksControl();
        final String expectedId = "id"; //$NON-NLS-1$
        final String encodedExpectedMnemonic = "1"; //$NON-NLS-1$
        final int expectedMnemonic = KeyStroke.getKeyStroke( encodedExpectedMnemonic ).getKeyCode();
        final String expectedName = "name"; //$NON-NLS-1$
        final List<String> parentPath = Arrays.asList( "ancestor1Id", "ancestor2Id", "ancestor3Id" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        final List<String> expectedPath = new ArrayList<>( parentPath );
        expectedPath.add( expectedId );
        final IConfigurationElement configurationElement = createComponentPrototypeCategoryConfigurationElement( expectedId, encodedExpectedMnemonic, expectedName, encodeCategoryPath( parentPath ) );
        mocksControl.replay();

        final ComponentPrototypeCategory actualValue = ComponentPrototypesExtensionPointFacade.createComponentPrototypeCategory( configurationElement );

        mocksControl.verify();
        assertEquals( expectedId, actualValue.getId() );
        assertEquals( expectedMnemonic, actualValue.getMnemonic() );
        assertEquals( expectedName, actualValue.getName() );
        assertEquals( expectedPath, actualValue.getPath() );
    }
}
