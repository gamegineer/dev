/*
 * ExtensibleEnumTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Apr 3, 2012 at 8:26:49 PM.
 */

package org.gamegineer.common.core.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.jcip.annotations.Immutable;
import org.junit.Test;

/**
 * A fixture for testing the {@link ExtensibleEnum} class.
 */
public final class ExtensibleEnumTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ExtensibleEnumTest} class.
     */
    public ExtensibleEnumTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ExtensibleEnum#ExtensibleEnum} constructor throws an
     * exception when passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Name_Null()
    {
        new ExtensibleEnum( null, 0 )
        {
            private static final long serialVersionUID = 1L;
        };
    }

    /**
     * Ensures the {@link ExtensibleEnum#ExtensibleEnum} constructor throws an
     * exception when passed an illegal ordinal that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Ordinal_Negative()
    {
        new ExtensibleEnum( "name", -1 ) //$NON-NLS-1$
        {
            private static final long serialVersionUID = 1L;
        };
    }

    /**
     * Ensures instances of the class can be serialized and deserialized while
     * preserving their singleton status.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSerialization()
        throws Exception
    {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream( outputStream );
        objectOutputStream.writeObject( MockEnum.CONSTANT_1 );
        objectOutputStream.writeObject( MockEnum.CONSTANT_2 );
        objectOutputStream.writeObject( MockEnum.CONSTANT_3 );
        objectOutputStream.close();

        final ByteArrayInputStream inputStream = new ByteArrayInputStream( outputStream.toByteArray() );
        final ObjectInputStream objectInputStream = new ObjectInputStream( inputStream );
        final Object constant1 = objectInputStream.readObject();
        final Object constant2 = objectInputStream.readObject();
        final Object constant3 = objectInputStream.readObject();
        objectInputStream.close();

        assertSame( MockEnum.CONSTANT_1, constant1 );
        assertSame( MockEnum.CONSTANT_2, constant2 );
        assertSame( MockEnum.CONSTANT_3, constant3 );
    }

    /**
     * Ensures the {@link ExtensibleEnum#valueOf} method throws an exception
     * when passed an illegal name.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testValueOf_Name_Illegal()
    {
        ExtensibleEnum.valueOf( MockEnum.class, "__UNKNOWN_NAME__" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link ExtensibleEnum#valueOf} method returns the correct
     * value when passed a legal name.
     */
    @Test
    public void testValueOf_Name_Legal()
    {
        assertSame( MockEnum.CONSTANT_1, ExtensibleEnum.valueOf( MockEnum.class, MockEnum.CONSTANT_1.name() ) );
        assertSame( MockEnum.CONSTANT_2, ExtensibleEnum.valueOf( MockEnum.class, MockEnum.CONSTANT_2.name() ) );
        assertSame( MockEnum.CONSTANT_3, ExtensibleEnum.valueOf( MockEnum.class, MockEnum.CONSTANT_3.name() ) );
    }

    /**
     * Ensures the {@link ExtensibleEnum#valueOf} method throws an exception
     * when passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testValueOf_Name_Null()
    {
        ExtensibleEnum.valueOf( MockEnum.class, null );
    }

    /**
     * Ensures the {@link ExtensibleEnum#valueOf} method throws an exception
     * when passed a {@code null} type.
     */
    @Test( expected = NullPointerException.class )
    public void testValueOf_Type_Null()
    {
        ExtensibleEnum.valueOf( null, "name" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link ExtensibleEnum#values} method returns the correct
     * values.
     */
    @Test
    public void testValues()
    {
        final MockEnum[] expectedValues = new MockEnum[] {
            MockEnum.CONSTANT_1, //
            MockEnum.CONSTANT_2, //
            MockEnum.CONSTANT_3
        };

        final MockEnum[] actualValues = ExtensibleEnum.values( MockEnum.class );

        assertArrayEquals( expectedValues, actualValues );
    }

    /**
     * Ensures the {@link ExtensibleEnum#values} method throws an exception when
     * passed a {@code null} type.
     */
    @Test( expected = NullPointerException.class )
    public void testValues_Type_Null()
    {
        ExtensibleEnum.values( null );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A mock implementation of the {@code ExtensibleEnum} class.
     */
    @Immutable
    private static final class MockEnum
        extends ExtensibleEnum
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Serializable class version number. */
        private static final long serialVersionUID = 8069850640402165613L;

        /** The first enum constant. */
        static final MockEnum CONSTANT_1 = new MockEnum( "one", 0 ); //$NON-NLS-1$

        /** The second enum constant. */
        static final MockEnum CONSTANT_2 = new MockEnum( "two", 1 ); //$NON-NLS-1$

        /** The third enum constant. */
        static final MockEnum CONSTANT_3 = new MockEnum( "three", 2 ); //$NON-NLS-1$


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockEnum} class.
         * 
         * @param name
         *        The name of the enum constant; must not be {@code null}.
         * @param ordinal
         *        The ordinal of the enum constant.
         * 
         * @throws java.lang.IllegalArgumentException
         *         If {@code ordinal} is negative.
         * @throws java.lang.NullPointerException
         *         If {@code name} is {@code null}.
         */
        private MockEnum(
            /* @NonNull */
            final String name,
            final int ordinal )
        {
            super( name, ordinal );
        }
    }
}