package plateau;

public class Pile {
	
	public Pile(boolean ascendante, Carte premiereCarte)
	{
		this.ascendante = ascendante;
		this.carteSuperieur = premiereCarte;
	}
	public Pile(Pile pile)
	{
		this.ascendante = pile.sens();
		this.carteSuperieur = new Carte(pile.valeurSuperieur());
	}
	
	boolean ascendante;
	public boolean sens() { return this.ascendante; }
	
	Carte carteSuperieur;
	public int valeurSuperieur() { return this.carteSuperieur.lireValeur(); }
	
	boolean verifierCarte(Carte carte, boolean modeAdversaire)
	{
		if (carte.lireValeur() == this.carteSuperieur.lireValeur())
			return false;
		
		// Sens
		if (this.ascendante)
		{
			if (carte.lireValeur() > this.carteSuperieur.lireValeur() ^ modeAdversaire)
				return true;
		}
		else
		{
			if (carte.lireValeur() < this.carteSuperieur.lireValeur() ^ modeAdversaire)
				return true;
		}
		
		// Dizaine
		if (carte.lireValeur() % 10 == this.carteSuperieur.lireValeur() % 10)
			return true;
		
		return false;
		
	}
	
	public boolean ajouterCarte(Carte carte, boolean modeAdversaire)
	{
		if (this.verifierCarte(carte, modeAdversaire))
		{
			this.carteSuperieur.definirValeur(carte.lireValeur());
			return true;
		}
		
		return false;
	}
	
	public static final char ASCENDANT = '^';
	public static final char DESCENDANT = 'v';
	
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		
		if (this.ascendante)
			str.append(ASCENDANT);
		else
			str.append(DESCENDANT);
		
		str.append("[");
		str.append(this.carteSuperieur);
		str.append("]");
		
		return str.toString();
	}
	
}
