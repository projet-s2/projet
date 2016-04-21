package liste;

import java.util.ArrayList;

public class Liste
{
  private ArrayList<Object> liste;
  
  public Liste(Liste l1){
	   this.liste = new ArrayList(l1.getArrayList());
  }
  public Liste()
  {
    this.liste = new ArrayList();
  }
  
  public void add(Object paramObject)
  {
    this.liste.add(paramObject);
  }
  
  public Object get(int paramInt)
  {
    try
    {
      return this.liste.get(paramInt);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {}
    return null;
  }
  
  public boolean remove(Object paramObject)
  {
    return this.liste.remove(paramObject);
  }
  public int size()
  {
    return this.liste.size();
  }
  
  public void set(int i, Object o){
	  this.liste.set(i, o);
  }
  
  public Liste melangerListe(){
	  Liste melange = new Liste();
	  int indice;
	  while(this.liste.size() > 0){
		 indice = (int) Math.round(Math.random()*(this.liste.size()-1));
		 melange.add(this.liste.get(indice));
		 this.liste.remove(this.liste.get(indice));
	  }
	  return melange;
  }
  public ArrayList<Object> getArrayList(){
	  return this.liste;
  }
 
  public String toString()
  {
    return this.liste.toString();
  }
}
