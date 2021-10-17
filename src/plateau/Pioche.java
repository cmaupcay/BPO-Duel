package plateau;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Pioche {

	ArrayList<Carte> cartes;
	
	public Pioche()
	{
		this.cartes = new ArrayList<>();
		this.remplir();
	}
	
	public int taille() { return cartes.size(); }
	
	void remplir()
	{
		cartes.clear();
		
		// Cr�ation du paquet de carte
		ArrayList<Integer> paquet = new ArrayList<>();
		for (int val = Carte.VALEUR_MIN + 1; val < Carte.VALEUR_MAX; val++)
			paquet.add(val);
		
		// M�lange
		int indexAleatoire = 0;
		for (int i = 0; i < Carte.VALEUR_MAX - 2; i++)
		{
			// Index al�atoire
			indexAleatoire = (ThreadLocalRandom.current().nextInt(0, paquet.size()));
			
			// R�cup�ration de la valeur
			Carte c = new Carte(paquet.get(indexAleatoire));
			paquet.remove(indexAleatoire);
			
			this.cartes.add(c);
		}
	}
	
	public Carte tirer()
	{

		Carte carte = new Carte(0);
		
		if (this.taille() > 0)
		{
			// R�cup�ration de la premi�re carte
			carte.definirValeur(this.cartes.get(0).lireValeur());
			this.cartes.remove(0);
		}
		
		return carte;
	}

}
