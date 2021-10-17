package jeu;

import java.util.ArrayList;

import plateau.Carte;

public class Main {

	public Main() { this.cartes = new ArrayList<>(); }
	public Main(Main main)
	{
		this.cartes = new ArrayList<>();
		for (int i = 0; i < main.taille(); i++)
			this.ajouter(main.cartes.get(i));
	}
	
	ArrayList<Carte> cartes;
	public static final int TAILLE_MAX = 6;
	
	public int taille() { return cartes.size(); }

	public void ajouter(Carte carte)
	{
		if (this.taille() == TAILLE_MAX)
			return;
		
		this.cartes.add(carte);
		this.trier();
	}

	void echanger(int indexA, int indexB)
	{
		if (indexA > this.taille() || indexB > this.taille())
			return;
		
		if (indexA == indexB)
			return;
		
		int tmp = this.cartes.get(indexA).lireValeur();
		this.cartes.get(indexA).definirValeur(this.cartes.get(indexB).lireValeur());
		this.cartes.get(indexB).definirValeur(tmp);
	}
	
	void trier()
	{
		if (this.taille() == 0)
			return;
		
		for (int index = this.taille() - 1; index > 0; index--)
		{
			if (this.cartes.get(index).lireValeur() < this.cartes.get(index - 1).lireValeur())
				this.echanger(index, index - 1);
		}
	}
	
	public int indexDepuisValeur(int valeur)
	{
		valeur = Carte.encadrerValeur(valeur);
		
		for (int index = 0; index < this.taille(); index++)
		{
			if (this.cartes.get(index).lireValeur() == valeur)
				return index;
		}
		
		return this.taille();
	}
	
 	public Carte tirer(int index)
	{
		Carte carte = new Carte(0);
 	
 		if (index >= this.taille())
 			return carte;
		
		// Récupération de la carte
		carte.definirValeur(this.cartes.get(index).lireValeur());
		this.cartes.remove(index);
		
		return carte;
	}

 	public String toString()
 	{
 		StringBuilder str = new StringBuilder();
 		str.append("{ ");
 		
 		for (int i = 0; i < this.taille(); i++)
 			str.append(this.cartes.get(i) + " ");
 		
 		str.append("}");
 		return str.toString();
 	}
}
