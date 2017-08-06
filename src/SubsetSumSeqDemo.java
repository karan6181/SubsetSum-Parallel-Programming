import edu.rit.pj2.Task;
import edu.rit.util.BitSet64;

import java.util.*;

/**
 * This class solves the Subset sum problem sequentially
 * @author Alimuddin Khan
 * @author Karan Jariwala
 *
 * @version 12-06-2016
 */

public class SubsetSumSeqDemo extends Task {
    // command line arguments
    public Long seed;
    public int N, target;

    /**
     * This is the main method which solves the subset sum
     * problem in sequential manner
     * @param args 1-> seed; 2->Set size; 3-> target sum
     */
    public  void main(String[] args) {

        // validating input
        if ( args.length != 4 ) {
            usage();
        }

        // Storing the value from command line to local variables
        try {
            N = Integer.parseInt( args[0] );
            target = Integer.parseInt( args[1] );
            seed = Long.parseLong( args[2] );
        } catch ( NumberFormatException nfe ) {
            usage();
        }

        // Value has to be positive
        if(N <= 0 || target < 0 || seed < 0){
            usage();
        }


        // deciding range for the random number generator
        //int max = 10*N;

        int range = Integer.parseInt(args[3]);

        // deciding range for the random number generator
        //int max = 10*N;
        int max = range;

        Random prng = new Random(seed);
        Set<Integer> set = new HashSet<>();

        // creating N unique numbers
        while(set.size() < N){
            set.add(prng.nextInt(max));
        }

        ArrayList<Integer> W = new ArrayList<>(set);

        //divide the original set into two equal subsets
        ArrayList<Integer> W1 = new ArrayList<>(W.subList(0,N/2));
        ArrayList<Integer> W2 = new ArrayList<>(W.subList(N/2, W.size()));


        // calculating possible subset sums of all the possible subset for lists W1 and W2
        BitSet subsetSum1 = getSubsetSequential(W1, max);
        BitSet subsetSum2 = getSubsetSequential(W2, max);

        // print details of each and every variables for demo purposes
        System.out.print("Input Set: ");
        Functions.printArrayList(W);
        System.out.print("\nFirst Half list: ");
        Functions.printArrayList(W1);
        System.out.println("Second Half list: ");
        Functions.printArrayList(W2);
        System.out.println("\nPossible subset sum from list1: " + subsetSum1);
        System.out.println("Possible subset sum from list2: " + subsetSum2);
        System.out.println("\nTarget sum: " + target);
        System.out.print("Is Target sum found? ");

        // searching in the 2 list
        System.out.println(Functions.isPresent(subsetSum1, subsetSum2, target));
    }

    /**
     * This method create a list of all the possible subset sums
     * from a given list of numbers.
     * @param list      set of numbers
     * @param max       maximum possible element in the set
     * @return  Possible subset sums of the given list
     */
    public static BitSet getSubsetSequential(ArrayList<Integer> list, int max){
        // new initialization
        BitSet bitSet = new BitSet(max*(max + 1)/2);
        //MyBitSetTest.printBitSetDetails(bitSet);

        // create a temp bitSet which will be used in merging section
        BitSet tempBitSet = new BitSet(max*(max + 1)/2);

        // Initialize the first element to 0
        bitSet.set(0);

        // subset sum generation stage
        for(int element: list){

            // taking one element at a time and adding it to all the subset sums
            // calculated in previous step
            for(int i = bitSet.nextSetBit(0); i >=0; i = bitSet.nextSetBit(i+1)){
                tempBitSet.set(i+element);
            }

            // merging section
            bitSet.or(tempBitSet);

            // clear the temp section
            tempBitSet.clear();
        }


        return bitSet;
    }


    /**
     * This method forces the pj2 to use 1 core
     * @return
     */
    protected static int coresRequired() {
        return 1;
    }


    /**
     * This method prints the correct usage instruction for this program
     */
    public static void usage() {
        System.err.println ("Usage: java pj2 SubsetSumSeq <seed> <N> <T>");
        System.err.println ("<seed> = Random seed (must be a +ve long integer)");
        System.err.println ("<N> = Number of elements in a set(must be a +ve integer)");
        System.err.println ("<T> = Target sum(must be a +ve integer)");
        System.exit (1);
    }


}
