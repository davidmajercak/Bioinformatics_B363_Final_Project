import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        Scanner scanner;
        boolean isFirstLine = true;
        String genomeName = "";
        String line = "";
        StringBuilder genome = new StringBuilder();

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

        minSkew(genome);
    }

    private static void minSkew(StringBuilder input)
    {
        int skew = 0;
        int minSkew = 0;
        List<Integer> indexList = new ArrayList<Integer>();

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
    }
}
