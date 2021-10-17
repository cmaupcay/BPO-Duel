package jeu;

import plateau.Carte;
import plateau.Pile;
import plateau.Pioche;

public class Joueur {

	public Joueur(String nom)
	{
		this.nom = nom;
		this.pioche = new Pioche();
		this.main = new Main();
		
		this.pileAscendante = new Pile(true, new Carte(Carte.VALEUR_MIN));
		this.pileDescendante = new Pile(false, new Carte(Carte.VALEUR_MAX));

		for (int i = 0; i < Main.TAILLE_MAX; i++)
			this.piocher();
	}

	
	String nom;
	public String lireNom() { return this.nom; }
	
	Pioche pioche;
	public int taillePioche() { return pioche.taille(); }
	
	Main main;
	public int tailleMain() { return main.taille(); }
	public Carte prendreDansLaMain(int valeur)
	{
		Carte carte = new Carte(0);
		
		if (valeur < Carte.VALEUR_MIN || valeur > Carte.VALEUR_MAX)
			return carte;
		
		int index = this.main.indexDepuisValeur(valeur);
		
		if (index < this.main.taille())
			carte = this.main.tirer(index);
		
		return carte;
	}
	public void mettreDansLaMain(Carte carte) { this.main.ajouter(carte); }
	public Main copierMain() { return new Main(this.main); }
	public void collerMain(Main main) { this.main = new Main(main); }
	
	public Pile pileAscendante;
	public Pile pileDescendante;
	public void collerPiles(Pile[] piles)
	{
		if (piles.length != 2)
			return;
		
		this.pileAscendante = piles[0];
		this.pileDescendante = piles[1];
	}
	
	public boolean piocher()
	{
		if (this.main.taille() == Main.TAILLE_MAX)
			return false;
		
		if (this.pioche.taille() == 0)
			return false;
		
		Carte carte = this.pioche.tirer();
		this.main.ajouter(carte);
		
		return true;
	}

	public static final int FORMAT_NOM = 4;
	
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		
		// Nom
		str.append(this.nom + " ");
		for (int i = this.nom.length(); i < FORMAT_NOM; i++)
			str.append(" ");
		
		// Piles
		str.append(this.pileAscendante);
		str.append(" ");
		str.append(this.pileDescendante);
		str.append(" ");
		
		// Main et Pioche
		str.append("(m");
		str.append(this.main.taille());
		str.append("p");
		str.append(this.pioche.taille());
		str.append(")");
		
		return str.toString();
	}
	
	public String lireMain() { return this.main.toString(); }

}
