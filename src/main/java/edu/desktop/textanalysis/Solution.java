package edu.desktop.textanalysis;

// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.String;
import java.util.StringTokenizer;
import java.util.Vector;

// CLASS BEGINS, THIS CLASS IS REQUIRED
public class Solution {
    static int total_words_entered = 0;
    static int count = 0;
    public static void main(String args[]) {
        String contents = null;
        String exclusion = null;
        List<String> exclusion_list = new ArrayList<String>();
        List<String> most_frequent = new ArrayList<String>();
        Iterator<String> mf_iterator = null;

        if (args.length != 2) {
            System.err.printf( "Program require a text file argument followed by excluded words file argument.\n" );
            System.exit( -1 );
        }

        try {
            contents = new String( Files.readAllBytes( Paths.get( args[0] ) ) );
        } catch (IOException e) {
            e.printStackTrace();
            System.err.printf( "Unable to open file %s\n", args[0] );
            System.exit( -1 );
        }

        try {
            exclusion = new String( Files.readAllBytes( Paths.get( args[1] ) ) );
        } catch (IOException e) {
            e.printStackTrace();
            System.err.printf( "Unable to open file %s\n", args[1] );
            System.exit( -2 );
        }

        StringTokenizer st = new StringTokenizer( exclusion );
        while (st.hasMoreTokens()) {
            exclusion_list.add( st.nextToken() );
        }

        most_frequent = retrieveMostFrequentlyUsedWords( contents, exclusion_list );

        System.out.println("Total words processed: " + total_words_entered);

        if (most_frequent == null) {
            System.out.printf( "No most frequently used words found.\n" );
        } else if (most_frequent.size() == 1) {
            System.out.printf( "Most frequently used word (%d) is \"%s\".\n", count, most_frequent.get( 0 ) );
        } else {
            System.out.printf( "Most frequently used words (%d) are: \n", count );
            mf_iterator = most_frequent.iterator();
            while (mf_iterator.hasNext()) {
                System.out.println( mf_iterator.next() );
            }
        }
    }

    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    static List<String> retrieveMostFrequentlyUsedWords(String literatureText,
                                                        List<String> wordsToExclude) {
        // WRITE YOUR CODE HERE
        List<String> textTokens = null;
        HashMap<String, Integer> textMap = new HashMap<>();
        Iterator<String> exclude_itrtr = wordsToExclude.iterator();
        String excluded_word = null;
        String text_word = null;
        StringTokenizer st = new StringTokenizer( literatureText );
        Integer value = null;
        Vector<String> most_common = new Vector<String>();

        while (exclude_itrtr.hasNext()) {
            excluded_word = exclude_itrtr.next();
            excluded_word = excluded_word.toLowerCase();
            textMap.put( excluded_word, new Integer( -1 ) );
        }

        while (st.hasMoreTokens()) {
            text_word = st.nextToken();
            text_word = text_word.toLowerCase();
            text_word = text_word.replaceAll("[`().,;:!?'\"]", "");
            value = textMap.get( text_word );
            total_words_entered += 1;
            if (value == null) {
                textMap.put( text_word, new Integer( 1 ) );
                if (count == 0) {
                    most_common.clear();
                    most_common.add( text_word );
                    count = 1;
                } else if (count == 1) {
                    most_common.add( text_word );
                }
            } else if (value > 0) {
                int temp = Integer.valueOf( value ) + 1;
                textMap.put( text_word,
                        new Integer( temp ) );
                if (temp > count) {
                    count = temp;
                    most_common.clear();
                    most_common.add( text_word );
                } else if (temp == count) {
                    most_common.add( text_word );
                }
            }
        }
        return new ArrayList<String>( most_common );
    }
    // METHOD SIGNATURE ENDS
}
