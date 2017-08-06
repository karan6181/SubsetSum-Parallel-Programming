import edu.rit.pj2.Loop;
import edu.rit.pj2.Section;
import edu.rit.pj2.Task;
import edu.rit.pj2.vbl.IntVbl;

import java.util.*;


/**
 * This class solves the Subset sum problem parallely on multicore
 * @author Alimuddin Khan
 * @author Karan Jariwala
 *
 * @version 12-06-2016
 */
public class SubsetSumSmp
        extends Task {
    // command line arguments
    public Long seed;
    public int N, target;

    // array lists to store the lists
    ArrayList<Integer> W,W1,W2;

    // BitSets to store the subset sums
    public BitSet subSetSum1, subSetSum2;

    /**
     * This is the main method which solves the subset sum
     * problem in sequential manner
     * @param args 1-> seed; 2->Set size; 3-> Target sum
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

        W = new ArrayList<>(set);

        // deciding number of bitsets required
        final int bitSetsRequired = ( max * (max + 1) )/ 2;

        // calculating subset sums of bothe lists in parallel
        parallelDo(new Section() {
            @Override
            public void run() throws Exception {
                //divide the original set into two equal subsets
                W1 = new ArrayList<>(W.subList(0,N/2));

                // initializing array list of bitset to store subset sums
                subSetSum1 = new BitSet(bitSetsRequired);

                // create one tmp array list of bitSets. This will be used in merging process
                BitSet temp1 = new BitSet(bitSetsRequired);

                // initialize firstElement
                subSetSum1.set(0);

                // refers to the current element of the set under considertation
                IntVbl currentElement1 = new IntVbl();
                //long start;

                // getting subset sums of list1
                for (int element : W1) {

                    // taking one element at a time and adding it to all the subset sums
                    // calculated in previous step
                    currentElement1.item = element;

                    // running a parallel for loop to add current element
                    // to all current presetn elements in the subset sums bitSet Array List
                    parallelFor(0, bitSetsRequired*64  - 1).schedule(guided).exec(new Loop() {
                        @Override
                        public void run(int i) throws Exception {
                            if (subSetSum1.get(i) == true) {
                                temp1.set(i + currentElement1.item);
                            }
                        }
                    });

                    // merge temp1 and subsetsum1
                    subSetSum1.or(temp1);

                    // claer temp1 bitset for next iteration
                    temp1.clear();
                }
            }
        }, new Section() {
            @Override
            public void run() throws Exception {
                //divide the original set into two equal subsets
                W2 = new ArrayList<>(W.subList(N/2, W.size()));

                // initializing  bitset to store subset sums
                subSetSum2 = new BitSet(bitSetsRequired);

                // create one tmp array list of bitSets. This will be used in merging process
                BitSet temp2 = new BitSet(bitSetsRequired);

                // initialize first element
                subSetSum2.set(0);

                // referes to the current element of the set under considertation
                IntVbl currentElement2 = new IntVbl();

                // getting subset sums of list2
                for(int element: W2){

                    // taking one element at a time and adding it to
                    // all the subset sums calculated in previous step
                    // calculated in previous step
                    currentElement2.item = element;

                    // running a parallel for loop to add current element
                    // to all current presetn elements in the subset sums bitSet Array List
                    parallelFor(0, bitSetsRequired*64 - 1).schedule(guided).exec(new Loop() {
                        @Override
                        public void run(int i) throws Exception {
                            if (subSetSum2.get(i) == true) {
                                temp2.set(i + currentElement2.item);
                            }
                        }
                    });


                    // merging  temp2 and subsetsum 2
                    subSetSum2.or(temp2);

                    // clear temp2 for next iteration
                    temp2.clear();

                }
            }
        });

        // searching in the 2 list
        System.out.println(Functions.isPresent(subSetSum1, subSetSum2, target));

    }

    /**
     * This method prints the correct usage instruction for this program
     */
    public static void usage() {
        System.err.println ("Usage: java pj2 cores=<k> SubsetSumSmp <seed> <N> <T>");
        System.err.println ("<k> = Number of cores required");
        System.err.println ("<seed> = Random seed (must be a +ve long integer)");
        System.err.println ("<N> = Number of elements in a set(must be a +ve integer)");
        System.err.println ("<T> = Target sum(must be a +ve integer)");
        System.exit (1);
    }


}