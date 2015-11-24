package com.blackboard.api.core.impl.bcrypt;

// Copyright (c) 2006 Damien Miller <djm@mindrot.org>
//
// Permission to use, copy, modify, and distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

import junit.framework.TestCase;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

/**
 * JUnit unit tests for BCrypt routines
 *
 * @author Damien Miller
 * @version 0.2
 */
public class TestBCrypt
        extends TestCase
{
    String test_vectors[][] = {
            { "",
                    "$2a$06$DCq7YPn5Rq63x1Lad4cll.",
                    "$2a$06$DCq7YPn5Rq63x1Lad4cll.TV4S6ytwfsfvkgY8jIucDrjc8deX1s." },
            { "",
                    "$2a$08$HqWuK6/Ng6sg9gQzbLrgb.",
                    "$2a$08$HqWuK6/Ng6sg9gQzbLrgb.Tl.ZHfXLhvt/SgVyWhQqgqcZ7ZuUtye" },
            { "",
                    "$2a$10$k1wbIrmNyFAPwPVPSVa/ze",
                    "$2a$10$k1wbIrmNyFAPwPVPSVa/zecw2BCEnBwVS2GbrmgzxFUOqW9dk4TCW" },
            { "",
                    "$2a$12$k42ZFHFWqBp3vWli.nIn8u",
                    "$2a$12$k42ZFHFWqBp3vWli.nIn8uYyIkbvYRvodzbfbK18SSsY.CsIQPlxO" },
            { "a",
                    "$2a$06$m0CrhHm10qJ3lXRY.5zDGO",
                    "$2a$06$m0CrhHm10qJ3lXRY.5zDGO3rS2KdeeWLuGmsfGlMfOxih58VYVfxe" },
            { "a",
                    "$2a$08$cfcvVd2aQ8CMvoMpP2EBfe",
                    "$2a$08$cfcvVd2aQ8CMvoMpP2EBfeodLEkkFJ9umNEfPD18.hUF62qqlC/V." },
            { "a",
                    "$2a$10$k87L/MF28Q673VKh8/cPi.",
                    "$2a$10$k87L/MF28Q673VKh8/cPi.SUl7MU/rWuSiIDDFayrKk/1tBsSQu4u" },
            { "a",
                    "$2a$12$8NJH3LsPrANStV6XtBakCe",
                    "$2a$12$8NJH3LsPrANStV6XtBakCez0cKHXVxmvxIlcz785vxAIZrihHZpeS" },
            { "abc",
                    "$2a$06$If6bvum7DFjUnE9p2uDeDu",
                    "$2a$06$If6bvum7DFjUnE9p2uDeDu0YHzrHM6tf.iqN8.yx.jNN1ILEf7h0i" },
            { "abc",
                    "$2a$08$Ro0CUfOqk6cXEKf3dyaM7O",
                    "$2a$08$Ro0CUfOqk6cXEKf3dyaM7OhSCvnwM9s4wIX9JeLapehKK5YdLxKcm" },
            { "abc",
                    "$2a$10$WvvTPHKwdBJ3uk0Z37EMR.",
                    "$2a$10$WvvTPHKwdBJ3uk0Z37EMR.hLA2W6N9AEBhEgrAOljy2Ae5MtaSIUi" },
            { "abc",
                    "$2a$12$EXRkfkdmXn2gzds2SSitu.",
                    "$2a$12$EXRkfkdmXn2gzds2SSitu.MW9.gAVqa9eLS1//RYtYCmB1eLHg.9q" },
            { "abcdefghijklmnopqrstuvwxyz",
                    "$2a$06$.rCVZVOThsIa97pEDOxvGu",
                    "$2a$06$.rCVZVOThsIa97pEDOxvGuRRgzG64bvtJ0938xuqzv18d3ZpQhstC" },
            { "abcdefghijklmnopqrstuvwxyz",
                    "$2a$08$aTsUwsyowQuzRrDqFflhge",
                    "$2a$08$aTsUwsyowQuzRrDqFflhgekJ8d9/7Z3GV3UcgvzQW3J5zMyrTvlz." },
            { "abcdefghijklmnopqrstuvwxyz",
                    "$2a$10$fVH8e28OQRj9tqiDXs1e1u",
                    "$2a$10$fVH8e28OQRj9tqiDXs1e1uxpsjN0c7II7YPKXua2NAKYvM6iQk7dq" },
            { "abcdefghijklmnopqrstuvwxyz",
                    "$2a$12$D4G5f18o7aMMfwasBL7Gpu",
                    "$2a$12$D4G5f18o7aMMfwasBL7GpuQWuP3pkrZrOAnqP.bmezbMng.QwJ/pG" },
            { "~!@#$%^&*()      ~!@#$%^&*()PNBFRD",
                    "$2a$06$fPIsBO8qRqkjj273rfaOI.",
                    "$2a$06$fPIsBO8qRqkjj273rfaOI.HtSV9jLDpTbZn782DC6/t7qT67P6FfO" },
            { "~!@#$%^&*()      ~!@#$%^&*()PNBFRD",
                    "$2a$08$Eq2r4G/76Wv39MzSX262hu",
                    "$2a$08$Eq2r4G/76Wv39MzSX262huzPz612MZiYHVUJe/OcOql2jo4.9UxTW" },
            { "~!@#$%^&*()      ~!@#$%^&*()PNBFRD",
                    "$2a$10$LgfYWkbzEvQ4JakH7rOvHe",
                    "$2a$10$LgfYWkbzEvQ4JakH7rOvHe0y8pHKF9OaFgwUZ2q7W2FFZmZzJYlfS" },
            { "~!@#$%^&*()      ~!@#$%^&*()PNBFRD",
                    "$2a$12$WApznUOJfkEGSmYRfnkrPO",
                    "$2a$12$WApznUOJfkEGSmYRfnkrPOr466oFDCaj4b6HY3EXGvfxm43seyhgC" },
    };


    /**
     * Entry point for unit tests
     *
     * @param args unused
     */
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(TestBCrypt.class);
    }


    /**
     * Test method for 'BCrypt.hashpw(String, String)'
     */
    public void testHashpw()
    {
        System.out.print("BCrypt.hashpw(): ");
        for (String[] test_vector : test_vectors)
        {
            String plain = test_vector[0];
            String salt = test_vector[1];
            String expected = test_vector[2];
            String hashed = BCrypt.hashpw(plain, salt);
            assertEquals(hashed, expected);
            System.out.print(".");
        }
        System.out.println("");
    }


    /**
     * Test method for 'BCrypt.gensalt()'
     */
    public void testGensalt()
    {
        System.out.print("BCrypt.gensalt(): ");
        for (int i = 0; i < test_vectors.length; i += 4)
        {
            String plain = test_vectors[i][0];
            String salt = BCrypt.gensalt();
            String hashed1 = BCrypt.hashpw(plain, salt);
            String hashed2 = BCrypt.hashpw(plain, hashed1);
            assertEquals(hashed1, hashed2);
            System.out.print(".");
        }
        System.out.println("");
    }


    /**
     * Test method for 'BCrypt.checkpw(String, String)' expecting success
     */
    public void testCheckpw_success()
    {
        System.out.print("BCrypt.checkpw w/ good passwords: ");
        for (String[] test_vector : test_vectors)
        {
            String plain = test_vector[0];
            String expected = test_vector[2];
            assertTrue(BCrypt.checkpw(plain, expected));
            System.out.print(".");
        }
        System.out.println("");
    }


    /**
     * Test method for 'BCrypt.checkpw(String, String)' expecting failure
     */
    public void testCheckpw_failure()
    {
        System.out.print("BCrypt.checkpw w/ bad passwords: ");
        for (int i = 0; i < test_vectors.length; i++)
        {
            int broken_index = (i + 4) % test_vectors.length;
            String plain = test_vectors[i][0];
            String expected = test_vectors[broken_index][2];
            assertFalse(BCrypt.checkpw(plain, expected));
            System.out.print(".");
        }
        System.out.println("");
    }


    /**
     * Test for correct hashing of non-US-ASCII passwords
     */
    public void testInternationalChars()
    {
        System.out.print("BCrypt.hashpw w/ international chars: ");
        String pw1 = "ππππππππ";
        String pw2 = "????????";

        String h1 = BCrypt.hashpw(pw1, BCrypt.gensalt());
        assertFalse(BCrypt.checkpw(pw2, h1));
        System.out.print(".");

        String h2 = BCrypt.hashpw(pw2, BCrypt.gensalt());
        assertFalse(BCrypt.checkpw(pw1, h2));
        System.out.print(".");
        System.out.println("");
    }


    public void testSimplePasswords()
    {
        System.out.print("Test Simple Passwords: ");

        String simplePasswords[] = { "", "Password1", "abc123", "a", "ukalltheway", "goBigBlu3!" };
        for (int i = 0; i < simplePasswords.length; i++)
        {
            runHashAndIsValidTestForVariousEncryptionRounds(simplePasswords[i]);
        }

        System.out.println("");
    }


    public void testLongPasswords()
    {
        System.out.print("Test Long Passwords: ");

        String longPasswords[] = {
                "Noooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo!",
                "Yeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahhh!",
                "You'll find the grail in the castle arrgggggggggggggggggggggggggggggggghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh!" };
        for (String longPassword : longPasswords)
        {
            runHashAndIsValidTestForVariousEncryptionRounds(longPassword);
        }

        System.out.println("");
    }


    //note: had to follow these instructions to get stuff to compile on my Win7 machine: http://whatiscomingtomyhead.wordpress.com/2012/01/02/get-rid-of-unmappable-character-for-encoding-cp1252-once-and-for-all/
    public void testSpecialCharacterPasswords()
    {
        System.out.print("Test Special Character Passwords: ");

        String specialCharPasswords[] = { "\t", "has a newline in it \n blah", "\0",
                "Śtiḷl tr̾ͪ̀́͘y̶̧̨̱̹̭ͧinǥ to ġęt ᵺê han͛ͪ̈g of twe͖͉̩̟͛͆̾ͫ̑͆̍ͫͥͨḙͯ̿̔͑̾̾ting wít̨̥̫͎h a ḟo̗uᶇẗaiṋ p҉̯͈͕en.",
                "Je ne suis pas fatigué", "Il ne sera pas là", "¡Hola!", "¿Y Tú?", "Bis später!",
                "ようこそいらっしゃいました" };
        for (String specialCharPassword : specialCharPasswords)
        {
            runHashAndIsValidTestForVariousEncryptionRounds(specialCharPassword);
        }

        System.out.println("");
    }


    public void testPrecalculatedHashes()
    {
        System.out.print("Test Pre-calculated hashes: ");

        runPrecalculatedHashTest("Password1", 9, "$2a$09$CmaUTtldsNCjYXpTikhnRuNHIj5BK236TCnlohzFKtncF9xjBvUwu");
        runPrecalculatedHashTest("it's_just_a_string", 8, "$2a$08$ZBNPFt4fk4/d7NqwofJiCuS7sAQc0KbXTY4jgYo6KFFWae7IX5huW");
        runPrecalculatedHashTest("abc", 8, "$2a$08$Ro0CUfOqk6cXEKf3dyaM7OhSCvnwM9s4wIX9JeLapehKK5YdLxKcm");
        runPrecalculatedHashTest("Noooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo!", 4, "$2a$04$vgIsSb2s7cKVc9sEpCuwi.KWHwie/TzR0NgaNUAyWTp4z23pPWbZe");
        runPrecalculatedHashTest("has a newline in it \n blah", 5, "$2a$05$Pa3E9c/YzrKxBEqyluT3Z..HBqlPZs1hIuv4zIdAco.KUM1pK58R2");
        runPrecalculatedHashTest("Śtiḷl tr̾ͪ̀́͘y̶̧̨̱̹̭ͧinǥ to ġęt ᵺê han͛ͪ̈g of twe͖͉̩̟͛͆̾ͫ̑͆̍ͫͥͨḙͯ̿̔͑̾̾ting wít̨̥̫͎h a ḟo̗uᶇẗaiṋ p҉̯͈͕en.", 6, "$2a$06$qGxvg6.Kwax2fCmnQ5UCmOoilgXfYpoTAMZbpopjs6QOtSsnxSwDO");

        System.out.println("");
    }


    public void testHashingTimeGrowsWithNumberOfRounds()
    {
        System.out.print("Test Hashing Time Grows With Cost: ");
        String plainTextPassword = java.util.UUID.randomUUID().toString();

        //run through once for each value of the cost parameter and generate a password just to eliminate startup time from the actual test
        for (int i = 6; i <= 10; i++)
        {
            BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(i));
        }

        long lasttime = 0;

        for (int i = 6; i <= 10; i++)
        {
            long before = System.currentTimeMillis();
            BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(i));
            long after = System.currentTimeMillis();
            long timediff = after - before;
            assertTrue(timediff > lasttime);
            lasttime = timediff;
            System.out.print(".");
        }
        System.out.println("");
    }


    // There is a known limitation that the BCrypt algorithm only cares about the first 72 bytes of the password
    // See: https://github.com/ncb000gt/node.bcrypt.js/blob/master/src/node_blf.h#L59
    public void testThatDemonstratesBCryptOnlyUsesFirst72BytesOfPassword()
    {
        System.out.print("Test Limitations of Length of Password: ");

        String stringOf72Bytes = "___hello_i_am_72_chars_of___________________________________________data";
        assertTrue(stringOf72Bytes.length() == 72);

        String stringOf71Bytes = "___hello_i_am_71_chars_of__________________________________________data";
        assertTrue(stringOf71Bytes.length() == 71);

        String validHashForStringWith72Bytes = "$2a$10$.z73An.eUqQFDOlKHjNMPO6rA0LqRJiFVzr753sKj9sEnvaz/Ix0K";
        String validHashForStringWith71Bytes = "$2a$10$59w/ESAcGZ1m6wsR7yYR4O.IlFDj9AO3.I.oF22rvWE1pcQ4u1o.K";

        assertTrue(BCrypt.checkpw(stringOf72Bytes, validHashForStringWith72Bytes));
        assertTrue(BCrypt.checkpw(stringOf71Bytes, validHashForStringWith71Bytes));

        //add something random to the 72 byte string and make sure it's still valid with the other hash
        assertTrue(BCrypt.checkpw(
                stringOf72Bytes + java.util.UUID.randomUUID().toString(), validHashForStringWith72Bytes));

        //add something random to the 71 byte string and make sure it's NOT still valid with the other hash
        assertFalse(BCrypt.checkpw(
                stringOf71Bytes + java.util.UUID.randomUUID().toString(), validHashForStringWith71Bytes));

        System.out.print(".");

        System.out.println("");
    }


    /**
     * @implSpec This function has been changed slightly from that which the author created.  I decreased the
     * minimum amount of processing time to better reflect the processing speed of my server.
     * @since 11/21/15 10:48 PM
     */
    public void testThatPreferredNumberOfRoundsRequiresAtLeastATenthOfASecond()
    {
        System.out.print("Test Default Cost Requires at least .1 seconds: ");
        String plainTextPassword = java.util.UUID.randomUUID().toString();
        long before = System.currentTimeMillis();
        BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
        long after = System.currentTimeMillis();

        long millisSpent = after - before;
        assertTrue(millisSpent >= 50);
        System.out.print(".");
        System.out.println("");
    }


    /**
     * This is a helper function that 1) iterates through several different cheap cost factors 2) hashes the
     * given plain text password 3) verifies that the given hash passes the IsValidPassword method 4) verifies
     * the hash is exactly 60 characters long
     *
     * @param plainTextInput Plain text input representing a password
     */
    private void runHashAndIsValidTestForVariousEncryptionRounds(String plainTextInput)
    {
        for (int i = 4; i <= 8; i++)
        {
            String hash = BCrypt.hashpw(plainTextInput, BCrypt.gensalt(i));

            assertTrue(BCrypt.checkpw(plainTextInput, hash));

            //all the hashes should be 60 characters in length.  No more, no less.  The number of the characters shall be 60.
            assertTrue(hash.length() == 60);

            System.out.print(".");
        }
    }


    private void runPrecalculatedHashTest(String plainTextInput, int cost, String hashThatShouldVerify)
    {
        String newHash = BCrypt.hashpw(plainTextInput, BCrypt.gensalt(cost));

        //make sure the new hash doesn't match the old one, but that both still work
        assertFalse(Objects.equals(hashThatShouldVerify, newHash));
        assertTrue(BCrypt.checkpw(plainTextInput, hashThatShouldVerify));
        assertTrue(BCrypt.checkpw(plainTextInput, newHash));

        System.out.print(".");
    }
}