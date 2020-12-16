import numpy as np
import matplotlib.pyplot as plt


# needs a function returns an array of skew.
# this should take in a genome string, and the location of the minimum skew as well as what the minimum skew is

def toPlot(genome):

    skew = 0

    splot = []
    for i in range(len(genome)):

        content = genome[i]
        #print(content == 'G')
        if (content == 'G'):
            #print("G is on")
            skew += 1
            #print(skew)
        elif (content == 'C'):
            #print("C is on")
            skew -= 1
        #print(skew)
        splot.append(skew)

    #print(splot)
    plt.plot(splot)
    plt.show()


if __name__ == '__main__':

    # add the genomes of course
    ##########################################################################
    # sample code
    with open ("C:/Users/Tofu/Documents/B363-project/OH157-genome.txt", "r") as myfile:
        data = myfile.readlines()
    data2 = ' '.join([str(elem) for elem in data])
    ##########################################################################
    toPlot(data2)

    #str = 'Gum'
    #ch = str[0]
    #print(ch == 'G')
