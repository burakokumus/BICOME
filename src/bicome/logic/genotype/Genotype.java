package bicome.logic.genotype;
/**
 * lists of possible genotypes of a feature of an organism
 * @author Ege Balcioglu
 * @version 23.4.2018
 */
public enum Genotype
{
   // enum constants
   DOMINANT_HOMOZYGOTE( 'A', 'A' ),
      DOMINANT_HETEROZYGOTE( 'A', 'a' ),
      RECESSIVE_HOMOZYGOTE( 'a', 'a' ),
      /**
       * A constant to indicate that the Feature that this Genotype belongs to does not exist in the Organism that the Feature is supposed to belong to.
       */
      NONE( 'x', 'x' );
   
   // properties
   private char gene1;
   private char gene2;
   
   // enum constructor
   /**
    * Sole constructor
    * @param gene1 the first gene that determines the genotype of this allele.
    * @param gene2 the first gene that determines the genotype of this allele.
    */
   Genotype( char gene1, char gene2 )
   {
      this.gene1 = gene1;
      this.gene2 = gene2;
   }
   
   // methods
   private char[] getGenes()
   {
      char[] result = { gene1, gene2 };
      return result;
   }
   
   /**
    * creates a new Genotype for cross method
    */
   private static Genotype newGenotype( char ch1, char ch2 )
   {
      if ( ch1 > ch2 )
      {
         char tmp;
         tmp = ch2;
         ch2 = ch1;
         ch1 = tmp;
      }
      
      if ( ch1 == 'A' )
      {
         if ( ch2 == 'A' )
         {
            return DOMINANT_HOMOZYGOTE;
         }
         if ( ch2 == 'a' )
         {
            return DOMINANT_HETEROZYGOTE;
         }
      }
      if ( ch1 == 'a' && ch2 == 'a' )
      {
         return RECESSIVE_HOMOZYGOTE;
      }
      return NONE;
   }
   
   /**
    * Randomly returns one of the possible outcomes of the crossbreeding
    * experiment of two genotypes. The possibility of this method to return a
    * particular genotype is proportional to 
    * @param g1 one of the genotypes to be crossed with each other
    * @param g2 one of the genotypes to be crossed with each other
    * @return returns one of the possible outcomes of the crossbreeding
    * experiment of two genotypes.
    */
   // methods
   public static Genotype cross( Genotype g1, Genotype g2 ) 
   {
      int randomResult;
      int resultCount;
      Genotype[] possibleResults;
      
      possibleResults = new Genotype[ 4 ];
      randomResult = (int) ( Math.random() * possibleResults.length );
      resultCount = 0;
      
      if ( g1 == NONE || g2 == NONE )
         return NONE;
      for ( char ch1 : g1.getGenes() )
      {
         for ( char ch2 : g2.getGenes() )
         {
            possibleResults[ resultCount ] = newGenotype( ch1, ch2 );
            resultCount++;
         }
      }
      return possibleResults[ randomResult ];
   }
   
   @Override
   public String toString()
   {
      return "" + gene1 + gene2;
   }
   
}