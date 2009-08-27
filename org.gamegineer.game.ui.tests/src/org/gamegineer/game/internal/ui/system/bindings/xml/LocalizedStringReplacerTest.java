/*
 * LocalizedStringReplacerTest.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Feb 28, 2009 at 9:25:02 PM.
 */

package org.gamegineer.game.internal.ui.system.bindings.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.gamegineer.game.ui.system.bindings.xml.FakeStringBundle;
import org.gamegineer.game.ui.system.bindings.xml.LocaleNeutralKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.ui.system.bindings.xml.LocalizedStringReplacer}
 * class.
 */
public final class LocalizedStringReplacerTest
    extends AbstractJaxbTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The locale-neutral key for localizable string property A. */
    private static final String PROPERTY_KEY_A = "propertyA"; //$NON-NLS-1$

    /** The locale-neutral key for localizable string property B. */
    private static final String PROPERTY_KEY_B = "propertyB"; //$NON-NLS-1$

    /** The locale-neutral key for non-localizable string property F. */
    private static final String PROPERTY_KEY_F = "propertyF"; //$NON-NLS-1$

    /** The localized value for localizable string property A. */
    private static final String PROPERTY_VALUE_A = "localizedValueA"; //$NON-NLS-1$

    /** The localized value for localizable string property B. */
    private static final String PROPERTY_VALUE_B = "localizedValueB"; //$NON-NLS-1$

    /** The localized value for non-localizable string property F. */
    private static final String PROPERTY_VALUE_F = "localizedValueF"; //$NON-NLS-1$

    /** The localized string replacer under test in the fixture. */
    private LocalizedStringReplacer m_replacer;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalizedStringReplacerTest}
     * class.
     */
    public LocalizedStringReplacerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.internal.core.system.xml.AbstractJaxbTestCase#getRootElementType()
     */
    @Override
    protected Class<?> getRootElementType()
    {
        return FakeRootElement.class;
    }

    /*
     * @see org.gamegineer.game.internal.core.system.xml.AbstractJaxbTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        final Map<String, String> entries = new HashMap<String, String>();
        entries.put( PROPERTY_KEY_A, PROPERTY_VALUE_A );
        entries.put( PROPERTY_KEY_B, PROPERTY_VALUE_B );
        entries.put( PROPERTY_KEY_F, PROPERTY_VALUE_F );
        m_replacer = new LocalizedStringReplacer( new FakeStringBundle( entries ) );

        super.setUp();
    }

    /*
     * @see org.gamegineer.game.internal.core.system.xml.AbstractJaxbTestCase#tearDown()
     */
    @After
    @Override
    public void tearDown()
        throws Exception
    {
        super.tearDown();

        m_replacer = null;
    }

    /**
     * Ensures the {@code afterUnmarshal} method throws an exception when passed
     * a {@code null} target.
     */
    @Test( expected = NullPointerException.class )
    public void testAfterUnmarshal_Target_Null()
    {
        m_replacer.afterUnmarshal( null, new Object() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * string bundle.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Bundle_Null()
    {
        new LocalizedStringReplacer( null );
    }

    /**
     * Ensures an unmarshalled object with a localizable string property that
     * contains a locale-neutral key is replaced with the corresponding
     * localized string.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnmarshal()
        throws Exception
    {
        final String PROPERTY_VALUE_D = "nonlocalizedValueD"; //$NON-NLS-1$
        final String PROPERTY_KEY_E = "unknownLocalizedValueE"; //$NON-NLS-1$
        final String PROPERTY_VALUE_E = LocaleNeutralKey.decorateKey( PROPERTY_KEY_E );
        final StringBuilder sb = new StringBuilder();
        sb.append( String.format( "<root A='%1$s'>", LocaleNeutralKey.decorateKey( PROPERTY_KEY_A ) ) ); //$NON-NLS-1$
        sb.append( String.format( "  <B>%1$s</B>", LocaleNeutralKey.decorateKey( PROPERTY_KEY_B ) ) ); //$NON-NLS-1$
        sb.append( "  <C>0</C>" ); //$NON-NLS-1$
        sb.append( String.format( "  <D>%1$s</D>", PROPERTY_VALUE_D ) ); //$NON-NLS-1$
        sb.append( String.format( "  <E>%1$s</E>", LocaleNeutralKey.decorateKey( PROPERTY_KEY_E ) ) ); //$NON-NLS-1$
        sb.append( String.format( "  <F>%1$s</F>", LocaleNeutralKey.decorateKey( PROPERTY_KEY_F ) ) ); //$NON-NLS-1$
        sb.append( "</root>" ); //$NON-NLS-1$
        getUnmarshaller().setListener( m_replacer );

        final FakeRootElement root = (FakeRootElement)getUnmarshaller().unmarshal( new StringReader( sb.toString() ) );

        assertNotNull( root );
        assertEquals( PROPERTY_VALUE_A, root.getPropertyA() );
        assertEquals( PROPERTY_VALUE_B, root.getPropertyB() );
        assertEquals( Integer.valueOf( 0 ), root.getPropertyC() );
        assertEquals( PROPERTY_VALUE_D, root.getPropertyD() );
        assertEquals( PROPERTY_VALUE_E, root.getPropertyE() );
        assertEquals( LocaleNeutralKey.decorateKey( PROPERTY_KEY_F ), root.getPropertyF() );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A fake element that serves as the root element for documents under test
     * in the fixture.
     */
    @XmlAccessorType( XmlAccessType.NONE )
    @XmlRootElement( name = "root" )
    private static final class FakeRootElement
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Localizable string property A. */
        @Localizable
        @XmlAttribute( name = "A", required = true )
        private final String m_propertyA;

        /** Localizable string property B. */
        @Localizable
        @XmlElement( name = "B", required = true )
        private final String m_propertyB;

        /** Non-localizable integer property C. */
        @XmlElement( name = "C", required = true )
        private final Integer m_propertyC;

        /** Localizable string property D. */
        @Localizable
        @XmlElement( name = "D", required = true )
        private final String m_propertyD;

        /** Localizable string property E. */
        @Localizable
        @XmlElement( name = "E", required = true )
        private final String m_propertyE;

        /** Non-localizable string property F. */
        @XmlElement( name = "F", required = true )
        private final String m_propertyF;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code FakeRootElement} class.
         */
        private FakeRootElement()
        {
            m_propertyA = null;
            m_propertyB = null;
            m_propertyC = null;
            m_propertyD = null;
            m_propertyE = null;
            m_propertyF = null;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the value of localizable string property A.
         * 
         * @return The value of localizable string property A; may be
         *         {@code null}.
         */
        /* @Nullable */
        String getPropertyA()
        {
            return m_propertyA;
        }

        /**
         * Gets the value of localizable string property B.
         * 
         * @return The value of localizable string property B; may be
         *         {@code null}.
         */
        /* @Nullable */
        String getPropertyB()
        {
            return m_propertyB;
        }

        /**
         * Gets the value of non-localizable integer property C.
         * 
         * @return The value of non-localizable integer property C; may be
         *         {@code null}.
         */
        /* @Nullable */
        Integer getPropertyC()
        {
            return m_propertyC;
        }

        /**
         * Gets the value of localizable string property D.
         * 
         * @return The value of localizable string property D; may be
         *         {@code null}.
         */
        /* @Nullable */
        String getPropertyD()
        {
            return m_propertyD;
        }

        /**
         * Gets the value of localizable string property E.
         * 
         * @return The value of localizable string property E; may be
         *         {@code null}.
         */
        /* @Nullable */
        String getPropertyE()
        {
            return m_propertyE;
        }

        /**
         * Gets the value of non-localizable string property F.
         * 
         * @return The value of non-localizable string property F; may be
         *         {@code null}.
         */
        /* @Nullable */
        String getPropertyF()
        {
            return m_propertyF;
        }
    }
}
