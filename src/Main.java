import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args)
    {
        Scanner scanner;
        boolean isFirstLine = true;
        String genomeName = "";
        String line = "";
        StringBuilder genome = new StringBuilder();

        int substringStartBuffer = 200;
        int substringEndBuffer = 200;

        try
        {
            scanner = new Scanner(new File("C:\\Users\\david\\Downloads\\sequence.fasta"));
            while(scanner.hasNextLine())
            {
                line = scanner.nextLine();
                if(isFirstLine)
                {
                    genomeName = line;
                    isFirstLine = false;
                    continue;
                }
                else
                {
                    genome.append(line);
                }
            }

            scanner.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        ArrayList<Integer> indexList = minSkew(genome);

        if(indexList.get(0) - substringStartBuffer < 0)
            substringStartBuffer = 0;
        else
            substringStartBuffer = indexList.get(0) - substringStartBuffer;

        if(indexList.get(indexList.size() - 1) + substringEndBuffer > genome.length())
            substringEndBuffer = genome.length() - 1;
        else
            substringEndBuffer = indexList.get(indexList.size() - 1) + substringEndBuffer;

        String genomeSubstring = genome.substring(substringStartBuffer, substringEndBuffer);

        System.out.println(genomeSubstring);

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