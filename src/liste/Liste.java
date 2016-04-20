package liste;

import java.util.ArrayList;

public class Liste
{
  private ArrayList<Object> liste;
  
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
  public void set(int i, Object o){
	  this.liste.set(i, o);
  }
  public int size()
  {
    return this.liste.size();
  }
  
  public String toString()
  {
    return this.liste.toString();
  }
}
