package tournoi;

public class Terrain{
  private int numero;
  private Match match;

  public Terrain(int numero, Match match){
    this.numero=numero;
    this.match=match;
  }

  public Terrain(int numero){
    this.numero=numero;
    this.match=null;
  }

  public void setNumero(int numero){
    this.numero=numero;
  }

  public void setMatch(Match match){
    this.match=match;
  }

  public int getNumero(){
    return this.numero;
  }

  public Match getMatch(){
    return this.match;
  }
}
