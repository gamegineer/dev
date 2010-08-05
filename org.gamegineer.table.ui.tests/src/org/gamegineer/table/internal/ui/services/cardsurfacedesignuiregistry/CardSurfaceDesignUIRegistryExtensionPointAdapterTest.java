/*
 * CardSurfaceDesignUIRegistryExtensionPointAdapterTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Aug 4, 2010 at 10:32:57 PM.
 */

package org.gamegineer.table.internal.ui.services.cardsurfacedesignuiregistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.internal.ui.BundleConstants;
import org.gamegineer.table.internal.ui.util.swing.IconProxy;
import org.gamegineer.table.ui.ICardSurfaceDesignUI;
import org.gamegineer.table.ui.services.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.services.cardsurfacedesignuiregistry.CardSurfaceDesignUIRegistryExtensionPointAdapter}
 * class.
 */
public final class CardSurfaceDesignUIRegistryExtensionPointAdapterTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The card surface design user interface registry extension point adapter
     * under test in the fixture.
     */
    private CardSurfaceDesignUIRegistryExtensionPointAdapter cardSurfaceDesignUIRegistryExtensionPointAdapter_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardSurfaceDesignUIRegistryExtensionPointAdapterTest} class.
     */
    public CardSurfaceDesignUIRegistryExtensionPointAdapterTest()
    {
        super();
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
        cardSurfaceDesignUIRegistryExtensionPointAdapter_ = new CardSurfaceDesignUIRegistryExtensionPointAdapter();
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        cardSurfaceDesignUIRegistryExtensionPointAdapter_ = null;
        mocksControl_ = null;
    }

    /**
     * Ensures the {@code added(IExtension)} method registers all card surface
     * design user interfaces associated with the specified extensions with the
     * card surface design user interface registry.
     */
    @Test
    public void testAddedFromExtension()
    {
        final CardSurfaceDesignId expectedId = CardSurfaceDesignId.fromString( "id" ); //$NON-NLS-1$
        final String expectedName = "name"; //$NON-NLS-1$
        final String expectedIconPath = "icons/cardSurfaces/back-blue.png"; //$NON-NLS-1$
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( expectedId.toString() ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "name" ) ).andReturn( expectedName ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "icon" ) ).andReturn( expectedIconPath ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension ).anyTimes();
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final ICardSurfaceDesignUIRegistry cardSurfaceDesignUIRegistry = mocksControl_.createMock( ICardSurfaceDesignUIRegistry.class );
        final Capture<ICardSurfaceDesignUI> cardSurfaceDesignUICapture = new Capture<ICardSurfaceDesignUI>();
        cardSurfaceDesignUIRegistry.registerCardSurfaceDesignUI( EasyMock.capture( cardSurfaceDesignUICapture ) );
        mocksControl_.replay();
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindCardSurfaceDesignUIRegistry( cardSurfaceDesignUIRegistry );
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );

        cardSurfaceDesignUIRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, cardSurfaceDesignUICapture.getValue().getId() );
        assertEquals( expectedName, cardSurfaceDesignUICapture.getValue().getName() );
        assertTrue( cardSurfaceDesignUICapture.getValue().getIcon() instanceof IconProxy );
    }

    /**
     * Ensures the {@code bindCardSurfaceDesignUIRegistry} method throws an
     * exception when the card surface design user interface registry is already
     * bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindCardSurfaceDesignUIRegistry_AlreadyBound()
    {
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindCardSurfaceDesignUIRegistry( mocksControl_.createMock( ICardSurfaceDesignUIRegistry.class ) );

        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindCardSurfaceDesignUIRegistry( mocksControl_.createMock( ICardSurfaceDesignUIRegistry.class ) );
    }

    /**
     * Ensures the {@code bindCardSurfaceDesignUIRegistry} method throws an
     * exception when passed a {@code null} card surface design user interface
     * registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindCardSurfaceDesignUIRegistry_CardSurfaceDesignUIRegistry_Null()
    {
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindCardSurfaceDesignUIRegistry( null );
    }

    /**
     * Ensures the {@code bindExtensionRegistry} method throws an exception when
     * the extension registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindExtensionRegistry_AlreadyBound()
    {
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the {@code bindExtensionRegistry} method throws an exception when
     * passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindExtensionRegistry_ExtensionRegistry_Null()
    {
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( null );
    }

    /**
     * Ensures the {@code removed(IExtension)} method unregisters all card
     * surface design user interfaces associated with the specified extensions
     * from the card surface design user interface registry.
     */
    @Test
    public void testRemovedFromExtension()
    {
        final CardSurfaceDesignId expectedId = CardSurfaceDesignId.fromString( "id" ); //$NON-NLS-1$
        final String expectedName = "name"; //$NON-NLS-1$
        final String expectedIconPath = "icons/cardSurfaces/back-blue.png"; //$NON-NLS-1$
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( expectedId.toString() ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "name" ) ).andReturn( expectedName ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "icon" ) ).andReturn( expectedIconPath ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension ).anyTimes();
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final ICardSurfaceDesignUIRegistry cardSurfaceDesignUIRegistry = mocksControl_.createMock( ICardSurfaceDesignUIRegistry.class );
        cardSurfaceDesignUIRegistry.registerCardSurfaceDesignUI( EasyMock.notNull( ICardSurfaceDesignUI.class ) );
        final Capture<ICardSurfaceDesignUI> cardSurfaceDesignUICapture = new Capture<ICardSurfaceDesignUI>();
        cardSurfaceDesignUIRegistry.unregisterCardSurfaceDesignUI( EasyMock.capture( cardSurfaceDesignUICapture ) );
        mocksControl_.replay();
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindCardSurfaceDesignUIRegistry( cardSurfaceDesignUIRegistry );
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        cardSurfaceDesignUIRegistryExtensionPointAdapter_.removed( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, cardSurfaceDesignUICapture.getValue().getId() );
        assertEquals( expectedName, cardSurfaceDesignUICapture.getValue().getName() );
        assertTrue( cardSurfaceDesignUICapture.getValue().getIcon() instanceof IconProxy );
    }

    /**
     * Ensures the {@code unbindCardSurfaceDesignUIRegistry} method throws an
     * exception when passed a card surface design user interface registry that
     * is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindCardSurfaceDesignUIRegistry_CardSurfaceDesignUIRegistry_NotBound()
    {
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindCardSurfaceDesignUIRegistry( mocksControl_.createMock( ICardSurfaceDesignUIRegistry.class ) );

        cardSurfaceDesignUIRegistryExtensionPointAdapter_.unbindCardSurfaceDesignUIRegistry( mocksControl_.createMock( ICardSurfaceDesignUIRegistry.class ) );
    }

    /**
     * Ensures the {@code unbindCardSurfaceDesignUIRegistry} method throws an
     * exception when passed a {@code null} card surface design user interface
     * registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindCardSurfaceDesignUIRegistry_CardSurfaceDesignUIRegistry_Null()
    {
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.unbindCardSurfaceDesignUIRegistry( null );
    }

    /**
     * Ensures the {@code unbindExtensionRegistry} method throws an exception
     * when passed an extension registry that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_NotBound()
    {
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        cardSurfaceDesignUIRegistryExtensionPointAdapter_.unbindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the {@code unbindExtensionRegistry} method throws an exception
     * when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_Null()
    {
        cardSurfaceDesignUIRegistryExtensionPointAdapter_.unbindExtensionRegistry( null );
    }
}
