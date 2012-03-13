/*
 * CardPileBaseDesignRegistryExtensionPointAdapterTest.java
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
 * Created on Jul 27, 2010 at 10:16:31 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.ICardPileBaseDesign;
import org.gamegineer.table.core.ICardPileBaseDesignRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.CardPileBaseDesignRegistryExtensionPointAdapter}
 * class.
 */
public final class CardPileBaseDesignRegistryExtensionPointAdapterTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The card pile base design registry extension point adapter under test in
     * the fixture.
     */
    private CardPileBaseDesignRegistryExtensionPointAdapter cardPileBaseDesignRegistryExtensionPointAdapter_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardPileBaseDesignRegistryExtensionPointAdapterTest} class.
     */
    public CardPileBaseDesignRegistryExtensionPointAdapterTest()
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
        cardPileBaseDesignRegistryExtensionPointAdapter_ = new CardPileBaseDesignRegistryExtensionPointAdapter();
    }

    /**
     * Ensures the {@code added(IExtension)} method registers all card pile base
     * designs associated with the specified extensions with the card pile base
     * design registry.
     */
    @Test
    public void testAddedFromExtension()
    {
        final CardPileBaseDesignId expectedId = CardPileBaseDesignId.fromString( "expected-id" ); //$NON-NLS-1$
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
        final ICardPileBaseDesignRegistry cardPileBaseDesignRegistry = mocksControl_.createMock( ICardPileBaseDesignRegistry.class );
        final Capture<ICardPileBaseDesign> cardPileBaseDesignCapture = new Capture<ICardPileBaseDesign>();
        cardPileBaseDesignRegistry.registerCardPileBaseDesign( EasyMock.capture( cardPileBaseDesignCapture ) );
        mocksControl_.replay();
        cardPileBaseDesignRegistryExtensionPointAdapter_.bindCardPileBaseDesignRegistry( cardPileBaseDesignRegistry );
        cardPileBaseDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );

        cardPileBaseDesignRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, cardPileBaseDesignCapture.getValue().getId() );
        assertEquals( expectedWidth, cardPileBaseDesignCapture.getValue().getSize().width );
        assertEquals( expectedHeight, cardPileBaseDesignCapture.getValue().getSize().height );
    }

    /**
     * Ensures the {@code bindCardPileBaseDesignRegistry} method throws an
     * exception when the card pile base design registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindCardPileBaseDesignRegistry_AlreadyBound()
    {
        cardPileBaseDesignRegistryExtensionPointAdapter_.bindCardPileBaseDesignRegistry( mocksControl_.createMock( ICardPileBaseDesignRegistry.class ) );

        cardPileBaseDesignRegistryExtensionPointAdapter_.bindCardPileBaseDesignRegistry( mocksControl_.createMock( ICardPileBaseDesignRegistry.class ) );
    }

    /**
     * Ensures the {@code bindCardPileBaseDesignRegistry} method throws an
     * exception when passed a {@code null} card pile base design registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindCardPileBaseDesignRegistry_CardPileBaseDesignRegistry_Null()
    {
        cardPileBaseDesignRegistryExtensionPointAdapter_.bindCardPileBaseDesignRegistry( null );
    }

    /**
     * Ensures the {@code bindExtensionRegistry} method throws an exception when
     * the extension registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindExtensionRegistry_AlreadyBound()
    {
        cardPileBaseDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        cardPileBaseDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the {@code bindExtensionRegistry} method throws an exception when
     * passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindExtensionRegistry_ExtensionRegistry_Null()
    {
        cardPileBaseDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( null );
    }

    /**
     * Ensures the {@code removed(IExtension)} method unregisters all card pile
     * base designs associated with the specified extensions from the card pile
     * base design registry.
     */
    @Test
    public void testRemovedFromExtension()
    {
        final CardPileBaseDesignId expectedId = CardPileBaseDesignId.fromString( "expected-id" ); //$NON-NLS-1$
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
        final ICardPileBaseDesignRegistry cardPileBaseDesignRegistry = mocksControl_.createMock( ICardPileBaseDesignRegistry.class );
        cardPileBaseDesignRegistry.registerCardPileBaseDesign( EasyMock.notNull( ICardPileBaseDesign.class ) );
        final Capture<ICardPileBaseDesign> cardPileBaseDesignCapture = new Capture<ICardPileBaseDesign>();
        cardPileBaseDesignRegistry.unregisterCardPileBaseDesign( EasyMock.capture( cardPileBaseDesignCapture ) );
        mocksControl_.replay();
        cardPileBaseDesignRegistryExtensionPointAdapter_.bindCardPileBaseDesignRegistry( cardPileBaseDesignRegistry );
        cardPileBaseDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );
        cardPileBaseDesignRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        cardPileBaseDesignRegistryExtensionPointAdapter_.removed( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, cardPileBaseDesignCapture.getValue().getId() );
        assertEquals( expectedWidth, cardPileBaseDesignCapture.getValue().getSize().width );
        assertEquals( expectedHeight, cardPileBaseDesignCapture.getValue().getSize().height );
    }

    /**
     * Ensures the {@code unbindCardPileBaseDesignRegistry} method throws an
     * exception when passed a card pile base design registry that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindCardPileBaseDesignRegistry_CardPileBaseDesignRegistry_NotBound()
    {
        cardPileBaseDesignRegistryExtensionPointAdapter_.bindCardPileBaseDesignRegistry( mocksControl_.createMock( ICardPileBaseDesignRegistry.class ) );

        cardPileBaseDesignRegistryExtensionPointAdapter_.unbindCardPileBaseDesignRegistry( mocksControl_.createMock( ICardPileBaseDesignRegistry.class ) );
    }

    /**
     * Ensures the {@code unbindCardPileBaseDesignRegistry} method throws an
     * exception when passed a {@code null} card pile base design registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindCardPileBaseDesignRegistry_CardPileBaseDesignRegistry_Null()
    {
        cardPileBaseDesignRegistryExtensionPointAdapter_.unbindCardPileBaseDesignRegistry( null );
    }

    /**
     * Ensures the {@code unbindExtensionRegistry} method throws an exception
     * when passed an extension registry that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_NotBound()
    {
        cardPileBaseDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        cardPileBaseDesignRegistryExtensionPointAdapter_.unbindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the {@code unbindExtensionRegistry} method throws an exception
     * when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_Null()
    {
        cardPileBaseDesignRegistryExtensionPointAdapter_.unbindExtensionRegistry( null );
    }
}
