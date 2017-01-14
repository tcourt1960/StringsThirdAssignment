//video Translating to Code in Lesson Finding All Genes in DNA

/**
 * Write a description of AllGenes here.
 * 
 * @author Drew 
 * @version Aug 30, 2016
 */

import edu.duke.*;
public class AllGenes {
    public int findStopCodon(String dnaStr,
                             int startIndex, 
                             String stopCodon){                                 
            int currIndex = dnaStr.indexOf(stopCodon,startIndex+3);
            while (currIndex != -1 ) {
               int diff = currIndex - startIndex;
               if (diff % 3  == 0) {
                   return currIndex;
               }
               else {
                   currIndex = dnaStr.indexOf(stopCodon, currIndex + 1);
               }
            }
            return -1;
        
    }
    public String findGene(String dna, int where) {
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1) {
            return "";
        }
        int taaIndex = findStopCodon(dna,startIndex,"TAA");
        int tagIndex = findStopCodon(dna,startIndex,"TAG");
        int tgaIndex = findStopCodon(dna,startIndex,"TGA");
        int minIndex = 0;
        if (taaIndex == -1 ||
            (tgaIndex != -1 && tgaIndex < taaIndex)) {
            minIndex = tgaIndex;
        }
        else {
            minIndex = taaIndex;
        }
        if (minIndex == -1 ||
            (tagIndex != -1 && tagIndex < minIndex)) {
            minIndex = tagIndex;
        }
        if (minIndex == -1){
            return "";
        }
        return dna.substring(startIndex,minIndex + 3);
    }
    public StorageResource getAllGenes(String dna) {
      
        StorageResource geneList= new StorageResource();
        int foundGenes =0;
        //Set startIndex to 0
      int startIndex = 0;
      //Repeat the following steps
      while ( true ) {
          //Find the next gene after startIndex
          String currentGene = findGene(dna, startIndex);
          //If no gene was found, leave this loop 
          if (currentGene.isEmpty()) {
              break;
          }
          //Print that gene out
         geneList.add(currentGene);
         foundGenes=foundGenes+1;
          //Set startIndex to just past the end of the gene
          startIndex = dna.indexOf(currentGene, startIndex) +
                       currentGene.length();
        }
        System.out.println("Found " + foundGenes + " genes");
        return geneList;
    }
    
    public float cgRatio(String dna) {
        int count = dna.length() - dna.replace("C", "").length();
        count = count + dna.length() - dna.replace("G", "").length();
        //System.out.println("The Count is " + count);
        float ratio = (float)count/dna.length();
        //System.out.println(ratio);
        return ratio;
        

        
    }
    
    
    public float countCTG(String dna) {
        int count =0;
        int startIndex = 0;
        startIndex = dna.indexOf("CTG",startIndex);
        if (startIndex == -1) 
            return 0;
         while (startIndex != -1) {
         count = count + 1;
         startIndex =dna.indexOf("CTG",startIndex+3); 
    }
    return count;
}
    
public void processGenes(StorageResource sr) {
    //print all the strings in sr
    System.out.println("Here are all genes found");
    for(String g:sr.data()) {
            System.out.println(g);   
        }
        
    System.out.println("Here are all genes longer than 60 characters"); 
    int count9=0;
    for(String g:sr.data()) {
        if (g.length()>60){
            count9=count9+1;
            System.out.println(g);
        }
    }
        
    System.out.println("Number of strings longer than 9 chars is " + count9);   
    
    System.out.println("Here are the CG ratios for each gene ");
        count9=0;
    for(String g:sr.data()) {
      if (cgRatio(g)>0.35) count9=count9+1;
      System.out.println(g + "   "  + cgRatio(g));
      System.out.println("There are "+count9+" ratios > 0.35");
        }
 
    int longestGeneLength = 0;
    String longestGene ="";
    for (String g: sr.data()){
        if (g.length()>longestGeneLength) {
            longestGeneLength = g.length();
            longestGene= g;
        }
    }
    System.out.println("The longest gene is "+longestGeneLength);
    
}



    public void testOn(String dna) {
        System.out.println("CTG count is " + countCTG(dna));
        //cgRatio(dna);
        System.out.println("Testing process AllGenes on " + dna);
        StorageResource genes = getAllGenes(dna);
        processGenes(genes);
        }
    
    public void test() {
        //       ATGv__v__v__v__v__v__v__ 
        FileResource fr = new FileResource("GRch38dnapart.fa.txt");
        String dna = fr.asString();
        dna = dna.toUpperCase();
        dna = dna.toUpperCase();
        testOn(dna);

    }
}
