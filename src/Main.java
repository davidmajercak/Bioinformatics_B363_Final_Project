import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main
{

    static HashMap<String, ArrayList<Integer>> kmers = new HashMap<>();
    static HashMap<String, ArrayList<Integer>> reverseComplements = new HashMap<>();

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
            scanner = new Scanner(new File("C:\\Users\\david\\Downloads\\sequence.fasta"));
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

        InitializeKMers(genomeSubstring, kmerSize);

        //printKmers();

        FindMotifs(minKmerOccurences);

    }

    //Finds the most common kmers in the oriC
    private static void FindMotifs(int minOccurences)
    {
        int numberOfOccurences = 0;
        int occurencesInKmers = 0;
        int occurencesInReverseComplements = 0;

        for (String key : kmers.keySet())
        {
            numberOfOccurences = 0;
            occurencesInKmers = 0;
            occurencesInReverseComplements = 0;

            if(kmers.containsKey(key))
                occurencesInKmers = kmers.get(key).size();
            if(reverseComplements.containsKey(key))
                occurencesInReverseComplements = reverseComplements.get(key).size();

            numberOfOccurences = occurencesInKmers + occurencesInReverseComplements;

            if(numberOfOccurences >= minOccurences)
            {
                System.out.println(numberOfOccurences + " " + key);
            }
        }
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

        InitializeReverseComplements(genomeSubstring, kmerSize);
    }

    //Adds all kmers of size kmerSize to a Map (key == kmer, value == arraylist of indexes where the kmer occurs in genomeSubstring)
    private static void InitializeReverseComplements(String genomeSubstring, int kmerSize)
    {
        for(int i = 0; i < genomeSubstring.length() - kmerSize + 1; i++)
        {
            String reverseComplement = complement(genomeSubstring.substring(i, i + kmerSize));

            //If key already exists, add i to the arraylist corresponding to this kmer
            if(reverseComplements.containsKey(reverseComplement))
            {
                reverseComplements.get(reverseComplement).add(i);
            }
            //If key does not yet exist, create an arrayList, add i to it, and then place the list into the map
            else
            {
                ArrayList<Integer> complementList = new ArrayList<>();
                complementList.add(i);
                reverseComplements.put(reverseComplement, complementList);
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
    		result.concat(stack.pop());
    	}
    	return result;
    }
}

// implement basic motif finding, reverse complement 