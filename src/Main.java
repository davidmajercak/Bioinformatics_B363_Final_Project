import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main
{

    static TreeMap<String, ArrayList<Integer>> kmers = new TreeMap<>();
    static TreeMap<String, ArrayList<Integer>> reverseComplements = new TreeMap<>();

    public static void main(String[] args)
    {
        Scanner scanner;
        boolean isFirstLine = true;
        String genomeName = "";
        String line = "";
        StringBuilder genome = new StringBuilder();

        int substringStartBuffer = 200;
        int substringEndBuffer = 200;

        int kmerSize = 9;
        int minKmerOccurences = 2;

        try
        {
            scanner = new Scanner(new File("C:\\Users\\david\\Downloads\\sequence O157 H7.fasta"));
            while (scanner.hasNextLine())
            {
                line = scanner.nextLine();
                if (isFirstLine)
                {
                    genomeName = line;
                    isFirstLine = false;
                    continue;
                } else
                {
                    genome.append(line);
                }
            }

            scanner.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        ArrayList<Integer> indexList = minSkew(genome);

        if (indexList.get(0) - substringStartBuffer < 0)
            substringStartBuffer = 0;
        else
            substringStartBuffer = indexList.get(0) - substringStartBuffer;

        if (indexList.get(indexList.size() - 1) + substringEndBuffer > genome.length())
            substringEndBuffer = genome.length() - 1;
        else
            substringEndBuffer = indexList.get(indexList.size() - 1) + substringEndBuffer;

        String genomeSubstring = genome.substring(substringStartBuffer, substringEndBuffer);

        System.out.println(genomeSubstring);

        //InitializeKMers(genomeSubstring, kmerSize);
        //printKmers();
        //FindMotifs(minKmerOccurences);

        for(int i = 3; i <= 12; i++)
        {
            System.out.println("Kmers of size " + i);
            kmerSize = i;
            kmers.clear();
            InitializeKMers(genomeSubstring, kmerSize);
            FindMotifs(minKmerOccurences);
            System.out.println();
        }

    }

    //Finds the most common kmers in the oriC
    private static void FindMotifs(int minOccurences)
    {
        int numberOfOccurences = 0;
        int kmerOccurences = 0;
        int reverseComplementOccurences = 0;

        for (String key : kmers.keySet())
        {
            numberOfOccurences = 0;
            kmerOccurences = 0;
            reverseComplementOccurences = 0;

            //Check if kmers contains the kmer and set kmerOccurences
            if(kmers.containsKey(key))
                kmerOccurences = kmers.get(key).size();
            //Check if kmers contains the complement and set reverseComplementOccurences
            if(kmers.containsKey(complement(key)))
                reverseComplementOccurences += kmers.get(complement(key)).size();

            numberOfOccurences = kmerOccurences + reverseComplementOccurences;

            numberOfOccurences += findNumOccurencesFromNeighborhood(key);
            numberOfOccurences += findNumOccurencesFromNeighborhood(complement(key));

            if(numberOfOccurences >= minOccurences)
            {
                System.out.println(numberOfOccurences + " " + key);
            }
        }
    }

    private static int findNumOccurencesFromNeighborhood(String kmer)
    {
        ArrayList<String> list = generateNeighborhood(kmer);
        int neighborhoodOccurences = 0;

        for(int i = 0; i < list.size(); i++)
        {
            if(kmers.containsKey(list.get(i)))
            {
                neighborhoodOccurences += kmers.get(list.get(i)).size();
            }
        }

        return neighborhoodOccurences;
    }

    private static ArrayList<String> generateNeighborhood(String s)
    {
        Character[] nucleotides = {'A', 'T', 'G', 'C'};

        ArrayList<String> result = new ArrayList<>();

        //Create a StringBuilder version of the passed in string
        StringBuilder temp = new StringBuilder(s);

        for(int i = 0; i < s.length(); i++)
        {
            for(int n = 0; n < nucleotides.length; n++)
            {
                temp = new StringBuilder(s);
                temp.setCharAt(i, nucleotides[n]);

                if(!temp.toString().equals(s))
                    result.add(temp.toString());
            }
        }

        return result;
    }

    //Adds all kmers of size kmerSize to a Map (key == kmer, value == arraylist of indexes where the kmer occurs in genomeSubstring)
    private static void InitializeKMers(String genomeSubstring, int kmerSize)
    {
        for(int i = 0; i < genomeSubstring.length() - kmerSize + 1; i++)
        {
            String kmer = genomeSubstring.substring(i, i + kmerSize);

            //If key already exists, add i to the arraylist corresponding to this kmer
            if(kmers.containsKey(kmer))
            {
                kmers.get(kmer).add(i);
            }
            //If key does not yet exist, create an arrayList, add i to it, and then place the list into the map
            else
            {
                ArrayList<Integer> kmerList = new ArrayList<>();
                kmerList.add(i);
                kmers.put(kmer, kmerList);
            }
        }
    }

    private static void printKmers()
    {
        for (String key : kmers.keySet())
        {
            System.out.print(key + " ");

            ArrayList list = kmers.get(key);
            System.out.print(list.toString());

            System.out.println();
        }

    }

    private static ArrayList<Integer> minSkew(StringBuilder input)
    {
        int skew = 0;
        int minSkew = 0;
        ArrayList<Integer> indexList = new ArrayList<Integer>();

        //Calculate skew and find minSkew
        for(int i = 0; i < input.length(); i++)
        {
            if(input.charAt(i) == 'C')
                skew -= 1;
            else if(input.charAt(i) == 'G')
                skew += 1;

            if(skew < minSkew)
            {
                minSkew = skew;
                indexList.clear();
                indexList.add(i + 1);
            }
            else if (skew == minSkew)
            {
                indexList.add(i + 1);
            }
        }

        //print out the list of minSkew indices
        for(int i = 0; i < indexList.size(); i++)
        {
            System.out.println(indexList.get(i));
        }

        return indexList;
    }
    
    // code that returns the reverse complement of a string
    private static String complement(String item) {
    	// the reverse complement (RC) of A is T and C is G
    	
    	Stack<String> stack = new Stack<String>();
    	int count = item.length(); // useful in the 
    	
    	for(int i = 0; i < count; i++) {
    		char a = item.charAt(i);
    		
    		switch (a) {
    		
    		case 'A': stack.add("T");
    				break;
    		case 'T': stack.add("A");
					break;
    		case 'G': stack.add("C");
    				break;
    		case 'C': stack.add("G");
					break;
					
    		}

    	}
    	String result = "";
    	while(!stack.isEmpty()) {
    		result += stack.pop();
    	}
    	return result;
    }
}
