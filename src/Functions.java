import java.util.ArrayList;
import java.util.BitSet;

/**
 * This class has some useful functions for debugging
 * and illustration purpose
 * @author Alimuddin Khan
 * @author Karan Jariwala
 *
 * @version 12-06-2016
 */
public class Functions {

    /**
     * This method prints the given array list
     * @param list
     */
    public static void printArrayList(ArrayList<Integer> list){
        System.out.printf("{");
        for(int e : list){
            System.out.printf("%5d", e);
        }
        System.out.println(" }");
    }

    /**
     * This method searches in the two subset sum list for
     * the target sum
     * @param subsetSums1     first subset sum list
     * @param subsetSums2     second subset sum list
     * @param targetSum target
     * @return  true or false depeneding on the elements presence
     */
    public static boolean isPresent(BitSet subsetSums1, BitSet subsetSums2,
                                       int targetSum){

        int m = subsetSums1.nextSetBit(0);
        int n = subsetSums2.previousSetBit(subsetSums2.size());
        //System.out.println("k = " + m + "\nl = " + n);

        // going through each element in subset sums list 1 and subset sums list2
        while(m >= 0 && n >=0){

            if( targetSum == m || targetSum == n || targetSum == (m + n)){
                return true;
            }else if (targetSum > (m + n)){
                m = subsetSums1.nextSetBit(m+1);
            }else if (targetSum < (m +n)){
                n = subsetSums2.previousSetBit(n-1);
            }
        }
        return false;
    }


}
