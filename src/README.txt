Step1: Environment setup
		You need pj2 library to be avialble in the classpath
		You can do by any of the following steps based on your linux flavor
			Bash shell
			$ export CLASSPATH=.:/var/tmp/parajava/pj2/pj2.jar
			csh shell:
			$ setenv CLASSPATH .:/var/tmp/parajava/pj2/pj2.jar
		The program need java 1.8 or higher

Step2: Compile the files:
		Go to the source_code directory
		compile all files using following command
				javac *.java

Step3: Running the program
		To run the sequential version of the program, enter the following command:
				Java pj2 SubsetSumSeq <N> <T> <seed> <R>
		To run the parallel version of the program, enter the following command:
				Java pj2 cores=<K> SubsetSumSmp <N> <T> <seed> <R>
		where,
				<K> = Number of cores
				<N> = Number of elements in an input set
				<T> = Target sum
				<seed> = Random seed
				<R> = Range value of the input elements


Source files:
1. SubsetSumSeq.java -> Solves subset sum problem using sequential two list algorithm
2. SubsetSumSmp.java -> Solves subset sum problem using parallel two list algorithm
3. Functions.java -> It has utility functions used by both sequential and parallel approaches
4. SubsetSumSeqDemo.java -> First two files just print true or false regarding the target sum presence. 
							If you need detailed output like what was the generated random set, two generated
							subset sums etc.