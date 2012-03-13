/*
 * CardSurfaceDesignRegistryExtensionPointAdapterTest.java
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
 * Created on Jul 31, 2010 at 10:24:11 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.gamegineer.table.core.ICardSurfaceDesignRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.CardSurfaceDesignRegistryExtensionPointAdapter}
 * class.
 */
public final class CardSurfaceDesignRegistryExtensionPointAdapterTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The card surface design registry extension point adapter under test in
     * the fixture.
     */
    private CardSurfaceDesignRegistryExtensionPointAdapter cardSurfaceDesignRegistryExtensionPointAdapter_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardSurfaceDesignRegistryExtensionPointAdapterTest} class.
     */
    public CardSurfaceDesignRegistryExtensionPointAdapterTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        cardSurfaceDesignRegistryExtensionPointAdapter_ = new CardSurfaceDesignRegistryExtensionPointAdapter();
    }

    /**
     * Ensures the {@code added(IExtension)} method registers all card surface
     * designs associated with the specified extensions with the card surface
     * design registry.
     */
    @Test
    public void testAddedFromExtension()
    {
        final CardSurfaceDesignId expectedId = CardSurfaceDesignId.fromString( "expected-id" ); //$NON-NLS-1$
        final int expectedWidth = 2112;
        final int expectedHeight = 42;
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( expectedId.toString() ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "width" ) ).andReturn( String.valueOf( expectedWidth ) ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "height" ) ).andReturn( String.valueOf( expectedHeight ) ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } );
        final ICardSurfaceDesignRegistry cardSurfaceDesignRegistry = mocksControl_.createMock( ICardSurfaceDesignRegistry.class );
        final Capture<ICardSurfaceDesign> cardSurfaceDesignCapture = new Capture<ICardSurfaceDesign>();
        cardSurfaceDesignRegistry.registerCardSurfaceDesign( EasyMock.capture( cardSurfaceDesignCapture ) );
        mocksControl_.replay();
        cardSurfaceDesignRegistryExtensionPointAdapter_.bindCardSurfaceDesignRegistry( cardSurfaceDesignRegistry );
        cardSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );

        cardSurfaceDesignRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, cardSurfaceDesignCapture.getValue().getId() );
        assertEquals( expectedWidth, cardSurfaceDesignCapture.getValue().getSize().width );
        assertEquals( expectedHeight, cardSurfaceDesignCapture.getValue().getSize().height );
    }

    /**
     * Ensures the {@code bindCardSurfaceDesignRegistry} method throws an
     * exception when the card surface design registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindCardSurfaceDesignRegistry_AlreadyBound()
    {
        cardSurfaceDesignRegistryExtensionPointAdapter_.bindCardSurfaceDesignRegistry( mocksControl_.createMock( ICardSurfaceDesignRegistry.class ) );

        cardSurfaceDesignRegistryExtensionPointAdapter_.bindCardSurfaceDesignRegistry( mocksControl_.createMock( ICardSurfaceDesignRegistry.class ) );
    }

    /**
     * Ensures the {@code bindCardSurfaceDesignRegistry} method throws an
     * exception when passed a {@code null} card surface design registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindCardSurfaceDesignRegistry_CardSurfaceDesignRegistry_Null()
    {
        cardSurfaceDesignRegistryExtensionPointAdapter_.bindCardSurfaceDesignRegistry( null );
    }

    /**
     * Ensures the {@code bindExtensionRegistry} method throws an exception when
     * the extension registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindExtensionRegistry_AlreadyBound()
    {
        cardSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        cardSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the {@code bindExtensionRegistry} method throws an exception when
     * passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindExtensionRegistry_ExtensionRegistry_Null()
    {
        cardSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( null );
    }

    /**
     * Ensures the {@code removed(IExtension)} method unregisters all card
     * surface designs associated with the specified extensions from the card
     * surface design registry.
     */
    @Test
    public void testRemovedFromExtension()
    {
        final CardSurfaceDesignId expectedId = CardSurfaceDesignId.fromString( "expected-id" ); //$NON-NLS-1$
        final int expectedWidth = 2112;
        final int expectedHeight = 42;
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( expectedId.toString() ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "width" ) ).andReturn( String.valueOf( expectedWidth ) ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "height" ) ).andReturn( String.valueOf( expectedHeight ) ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final ICardSurfaceDesignRegistry cardSurfaceDesignRegistry = mocksControl_.createMock( ICardSurfaceDesignRegistry.class );
        cardSurfaceDesignRegistry.registerCardSurfaceDesign( EasyMock.notNull( ICardSurfaceDesign.class ) );
        final Capture<ICardSurfaceDesign> cardSurfaceDesignCapture = new Capture<ICardSurfaceDesign>();
        cardSurfaceDesignRegistry.unregisterCardSurfaceDesign( EasyMock.capture( cardSurfaceDesignCapture ) );
        mocksControl_.replay();
        cardSurfaceDesignRegistryExtensionPointAdapter_.bindCardSurfaceDesignRegistry( cardSurfaceDesignRegistry );
        cardSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );
        cardSurfaceDesignRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        cardSurfaceDesignRegistryExtensionPointAdapter_.removed( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, cardSurfaceDesignCapture.getValue().getId() );
        assertEquals( expectedWidth, cardSurfaceDesignCapture.getValue().getSize().width );
        assertEquals( expectedHeight, cardSurfaceDesignCapture.getValue().getSize().height );
    }

    /**
     * Ensures the {@code unbindCardSurfaceDesignRegistry} method throws an
     * exception when passed a card surface design registry that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindCardSurfaceDesignRegistry_CardSurfaceDesignRegistry_NotBound()
    {
        cardSurfaceDesignRegistryExtensionPointAdapter_.bindCardSurfaceDesignRegistry( mocksControl_.createMock( ICardSurfaceDesignRegistry.class ) );

        cardSurfaceDesignRegistryExtensionPointAdapter_.unbindCardSurfaceDesignRegistry( mocksControl_.createMock( ICardSurfaceDesignRegistry.class ) );
    }

    /**
     * Ensures the {@code unbindCardSurfaceDesignRegistry} method throws an
     * exception when passed a {@code null} card surface design registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindCardSurfaceDesignRegistry_CardSurfaceDesignRegistry_Null()
    {
        cardSurfaceDesignRegistryExtensionPointAdapter_.unbindCardSurfaceDesignRegistry( null );
    }

    /**
     * Ensures the {@code unbindExtensionRegistry} method throws an exception
     * when passed an extension registry that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_NotBound()
    {
        cardSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        cardSurfaceDesignRegistryExtensionPointAdapter_.unbindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the {@code unbindExtensionRegistry} method throws an exception
     * when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_Null()
    {
        cardSurfaceDesignRegistryExtensionPointAdapter_.unbindExtensionRegistry( null );
    }
}
