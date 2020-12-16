import numpy as np
import matplotlib.pyplot as plt


# needs a function returns an array of skew.
# this should take in a genome string, and the location of the minimum skew as well as what the minimum skew is

def toPlot(genome, name):

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
    plt.suptitle(name)
    plt.show()


if __name__ == '__main__':

    # add the genomes of course
    ##########################################################################
    # sample code
    with open ("C:/Users/Tofu/Documents/B363-project/OH157-genome.txt", "r") as myfile:
        data1 = myfile.readlines()
    OH1507 = ' '.join([str(elem) for elem in data1])

    with open ("C:/Users/Tofu/Documents/B363-project/O145-H11-genome.txt", "r") as myfile:
        data2 = myfile.readlines()
    O145 = ' '.join([str(elem) for elem in data2])

    with open ("C:/Users/Tofu/Documents/B363-project/O121-H19-genome.txt", "r") as myfile:
        data3 = myfile.readlines()
    O121 = ' '.join([str(elem) for elem in data3])

    with open ("C:/Users/Tofu/Documents/B363-project/O103-H2-genome.txt", "r") as myfile:
        data4 = myfile.readlines()
    O103 = ' '.join([str(elem) for elem in data4])

    with open ("C:/Users/Tofu/Documents/B363-project/O26-H11-genome.txt", "r") as myfile:
        data5 = myfile.readlines()
    O26 = ' '.join([str(elem) for elem in data5])

    ##########################################################################
    # might want to test each plot separately to save memory

    #toPlot(OH1507, "O1507")

    #toPlot(O145, "O145")

    toPlot(O121, "O121")

    #toPlot(O103, "O103")

    #toPlot(O26, "O26")
