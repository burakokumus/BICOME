package world;
/**
 * @author Mert Aslan, Ege Balc�o�lu
 * @version 27.4.2018
 */
public class Organism 
{
   private int cooldown;
   private boolean pregnant;
   private int age;
   
   public Organism( /* stub */ )
   {
      // stub
      cooldown = 0;
      age = 1;
      pregnant = false;
   }
   
   public void increaseCooldown()
   {
      if ( cooldown % 3 == 0 && cooldown != 0)
      {
         cooldown = 0;
         setReproductionCooldown( false );
      }
      else
         cooldown++;
   }
   
   public void age()
   {
      age++;
   }
   
   public void setReproductionCooldown( Boolean state )
   {
      pregnant = state;
   }
   
   public boolean canReproduce()
   {
      return !pregnant;      
   }
   
   public Organism reproduce(Organism o)
   {
      Organism offspring;
      
      this.setReproductionCooldown( true );
      o.setReproductionCooldown( true );
      
      offspring = new Organism( /* stub */ );
      
      // stub
      
      return offspring;
      
   }
}
