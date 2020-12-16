import numpy as np
import matplotlib.pyplot as plt

# needs a function that checks the current genome and finds the minimum skew
def minSkew(genome):

    # if the char is G add 1, if it is C sutract 1
    skew = 0 # current skew
    minskew = 0 # current minimum skew
    location = 0 # location of the minSkew
    for i in genome:
        content = genome[i]

        if content == 'G':
            minskew += 1
        elif content == 'C':
            minskew -= 1

        if skew < minskew:
            minskew = skew
            location = i

    return minskew, location

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
