/*
 * ExtensibleEnumTest.java
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
 * Created on Apr 3, 2012 at 8:26:49 PM.
 */

package org.gamegineer.common.core.util;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
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
        try( final ObjectOutputStream objectOutputStream = new ObjectOutputStream( outputStream ) )
        {
            objectOutputStream.writeObject( MockEnum.CONSTANT_1 );
            objectOutputStream.writeObject( MockEnum.CONSTANT_2 );
            objectOutputStream.writeObject( MockEnum.CONSTANT_3 );
        }

        final Object constant1, constant2, constant3;
        final ByteArrayInputStream inputStream = new ByteArrayInputStream( outputStream.toByteArray() );
        try( final ObjectInputStream objectInputStream = new ObjectInputStream( inputStream ) )
        {
            constant1 = objectInputStream.readObject();
            constant2 = objectInputStream.readObject();
            constant3 = objectInputStream.readObject();
        }

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
        ExtensibleEnum.<@NonNull MockEnum>valueOf( nonNull( MockEnum.class ), "__UNKNOWN_NAME__" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link ExtensibleEnum#valueOf} method returns the correct
     * value when passed a legal name.
     */
    @Test
    public void testValueOf_Name_Legal()
    {
        assertSame( MockEnum.CONSTANT_1, ExtensibleEnum.<@NonNull MockEnum>valueOf( nonNull( MockEnum.class ), MockEnum.CONSTANT_1.name() ) );
        assertSame( MockEnum.CONSTANT_2, ExtensibleEnum.<@NonNull MockEnum>valueOf( nonNull( MockEnum.class ), MockEnum.CONSTANT_2.name() ) );
        assertSame( MockEnum.CONSTANT_3, ExtensibleEnum.<@NonNull MockEnum>valueOf( nonNull( MockEnum.class ), MockEnum.CONSTANT_3.name() ) );
    }

    /**
     * Ensures the {@link ExtensibleEnum#values} method returns the correct
     * values.
     */
    @Test
    public void testValues()
    {
        final List<MockEnum> expectedValues = Arrays.asList( //
            MockEnum.CONSTANT_1, //
            MockEnum.CONSTANT_2, //
            MockEnum.CONSTANT_3 );

        final List<MockEnum> actualValues = ExtensibleEnum.<@NonNull MockEnum>values( nonNull( MockEnum.class ) );

        assertEquals( expectedValues, actualValues );
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
         *        The name of the enum constant.
         * @param ordinal
         *        The ordinal of the enum constant.
         * 
         * @throws java.lang.IllegalArgumentException
         *         If {@code ordinal} is negative.
         */
        private MockEnum(
            final String name,
            final int ordinal )
        {
            super( name, ordinal );
        }
    }
}
