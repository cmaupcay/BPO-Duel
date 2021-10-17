package plateau;

public class Carte {
	
	public Carte(int valeur) { this.definirValeur(valeur); }
	
	int valeur;
	public void definirValeur(int valeur) { this.valeur = encadrerValeur(valeur); }
	public int lireValeur() { return this.valeur; }
	
	public static final int VALEUR_MIN = 1;
	public static final int VALEUR_MAX = 60;
	public static int encadrerValeur(int valeur)
	{	
		if (valeur < VALEUR_MIN && valeur != 0)
			return VALEUR_MIN;
		
		if (valeur > VALEUR_MAX)
			return VALEUR_MAX;
		
		return valeur;
	}
	
	public String toString() { return String.format("%02d", this.lireValeur()); }
}
