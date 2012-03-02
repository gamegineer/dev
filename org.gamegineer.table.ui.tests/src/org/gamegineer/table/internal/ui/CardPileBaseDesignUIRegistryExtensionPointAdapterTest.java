/*
 * CardPileBaseDesignUIRegistryExtensionPointAdapterTest.java
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
 * Created on Aug 2, 2010 at 10:35:21 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.internal.ui.util.swing.IconProxy;
import org.gamegineer.table.ui.ICardPileBaseDesignUI;
import org.gamegineer.table.ui.ICardPileBaseDesignUIRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.CardPileBaseDesignUIRegistryExtensionPointAdapter}
 * class.
 */
public final class CardPileBaseDesignUIRegistryExtensionPointAdapterTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The card pile base design user interface registry extension point adapter
     * under test in the fixture.
     */
    private CardPileBaseDesignUIRegistryExtensionPointAdapter cardPileBaseDesignUIRegistryExtensionPointAdapter_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardPileBaseDesignUIRegistryExtensionPointAdapterTest} class.
     */
    public CardPileBaseDesignUIRegistryExtensionPointAdapterTest()
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
        cardPileBaseDesignUIRegistryExtensionPointAdapter_ = new CardPileBaseDesignUIRegistryExtensionPointAdapter();
    }

    /**
     * Ensures the {@code added(IExtension)} method registers all card pile base
     * design user interfaces associated with the specified extensions with the
     * card pile base design user interface registry.
     */
    @Test
    public void testAddedFromExtension()
    {
        final CardPileBaseDesignId expectedId = CardPileBaseDesignId.fromString( "id" ); //$NON-NLS-1$
        final String expectedName = "name"; //$NON-NLS-1$
        final String expectedIconPath = "icons/cardPileBases/default.png"; //$NON-NLS-1$
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IContributor contributor = ContributorFactoryOSGi.createContributor( Activator.getDefault().getBundleContext().getBundle() );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( expectedId.toString() ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "name" ) ).andReturn( expectedName ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "icon" ) ).andReturn( expectedIconPath ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension ).anyTimes();
        EasyMock.expect( configurationElement.getContributor() ).andReturn( contributor ).anyTimes();
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final ICardPileBaseDesignUIRegistry cardPileBaseDesignUIRegistry = mocksControl_.createMock( ICardPileBaseDesignUIRegistry.class );
        final Capture<ICardPileBaseDesignUI> cardPileBaseDesignUICapture = new Capture<ICardPileBaseDesignUI>();
        cardPileBaseDesignUIRegistry.registerCardPileBaseDesignUI( EasyMock.capture( cardPileBaseDesignUICapture ) );
        mocksControl_.replay();
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindCardPileBaseDesignUIRegistry( cardPileBaseDesignUIRegistry );
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );

        cardPileBaseDesignUIRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, cardPileBaseDesignUICapture.getValue().getId() );
        assertEquals( expectedName, cardPileBaseDesignUICapture.getValue().getName() );
        assertTrue( cardPileBaseDesignUICapture.getValue().getIcon() instanceof IconProxy );
    }

    /**
     * Ensures the {@code bindCardPileBaseDesignUIRegistry} method throws an
     * exception when the card pile base design user interface registry is
     * already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindCardPileBaseDesignUIRegistry_AlreadyBound()
    {
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindCardPileBaseDesignUIRegistry( mocksControl_.createMock( ICardPileBaseDesignUIRegistry.class ) );

        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindCardPileBaseDesignUIRegistry( mocksControl_.createMock( ICardPileBaseDesignUIRegistry.class ) );
    }

    /**
     * Ensures the {@code bindCardPileBaseDesignUIRegistry} method throws an
     * exception when passed a {@code null} card pile base design user interface
     * registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindCardPileBaseDesignUIRegistry_CardPileBaseDesignUIRegistry_Null()
    {
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindCardPileBaseDesignUIRegistry( null );
    }

    /**
     * Ensures the {@code bindExtensionRegistry} method throws an exception when
     * the extension registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindExtensionRegistry_AlreadyBound()
    {
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the {@code bindExtensionRegistry} method throws an exception when
     * passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindExtensionRegistry_ExtensionRegistry_Null()
    {
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( null );
    }

    /**
     * Ensures the {@code removed(IExtension)} method unregisters all card pile
     * base design user interfaces associated with the specified extensions from
     * the card pile base design user interface registry.
     */
    @Test
    public void testRemovedFromExtension()
    {
        final CardPileBaseDesignId expectedId = CardPileBaseDesignId.fromString( "id" ); //$NON-NLS-1$
        final String expectedName = "name"; //$NON-NLS-1$
        final String expectedIconPath = "icons/cardPileBases/default.png"; //$NON-NLS-1$
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IContributor contributor = ContributorFactoryOSGi.createContributor( Activator.getDefault().getBundleContext().getBundle() );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( expectedId.toString() ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "name" ) ).andReturn( expectedName ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "icon" ) ).andReturn( expectedIconPath ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension ).anyTimes();
        EasyMock.expect( configurationElement.getContributor() ).andReturn( contributor ).anyTimes();
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final ICardPileBaseDesignUIRegistry cardPileBaseDesignUIRegistry = mocksControl_.createMock( ICardPileBaseDesignUIRegistry.class );
        cardPileBaseDesignUIRegistry.registerCardPileBaseDesignUI( EasyMock.notNull( ICardPileBaseDesignUI.class ) );
        final Capture<ICardPileBaseDesignUI> cardPileBaseDesignUICapture = new Capture<ICardPileBaseDesignUI>();
        cardPileBaseDesignUIRegistry.unregisterCardPileBaseDesignUI( EasyMock.capture( cardPileBaseDesignUICapture ) );
        mocksControl_.replay();
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindCardPileBaseDesignUIRegistry( cardPileBaseDesignUIRegistry );
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        cardPileBaseDesignUIRegistryExtensionPointAdapter_.removed( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, cardPileBaseDesignUICapture.getValue().getId() );
        assertEquals( expectedName, cardPileBaseDesignUICapture.getValue().getName() );
        assertTrue( cardPileBaseDesignUICapture.getValue().getIcon() instanceof IconProxy );
    }

    /**
     * Ensures the {@code unbindCardPileBaseDesignUIRegistry} method throws an
     * exception when passed a card pile base design user interface registry
     * that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindCardPileBaseDesignUIRegistry_CardPileBaseDesignUIRegistry_NotBound()
    {
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindCardPileBaseDesignUIRegistry( mocksControl_.createMock( ICardPileBaseDesignUIRegistry.class ) );

        cardPileBaseDesignUIRegistryExtensionPointAdapter_.unbindCardPileBaseDesignUIRegistry( mocksControl_.createMock( ICardPileBaseDesignUIRegistry.class ) );
    }

    /**
     * Ensures the {@code unbindCardPileBaseDesignUIRegistry} method throws an
     * exception when passed a {@code null} card pile base design user interface
     * registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindCardPileBaseDesignUIRegistry_CardPileBaseDesignUIRegistry_Null()
    {
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.unbindCardPileBaseDesignUIRegistry( null );
    }

    /**
     * Ensures the {@code unbindExtensionRegistry} method throws an exception
     * when passed an extension registry that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_NotBound()
    {
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        cardPileBaseDesignUIRegistryExtensionPointAdapter_.unbindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the {@code unbindExtensionRegistry} method throws an exception
     * when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_Null()
    {
        cardPileBaseDesignUIRegistryExtensionPointAdapter_.unbindExtensionRegistry( null );
    }
}
