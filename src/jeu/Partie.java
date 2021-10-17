package jeu;

import java.util.ArrayList;
import java.util.Scanner;

import plateau.Carte;
import plateau.Pile;

public class Partie {
	
	public static final int NB_JOUEURS = 2;
	
	public Partie()
	{
		this.joueurs = new ArrayList<Joueur>();
		this.terminee = false;
		this.nombreCoup = 0;
	}
	
	ArrayList<Joueur> joueurs;
	public boolean nouveauJoueur(String nom)
	{
		if (this.joueurs.size() == NB_JOUEURS)
			return false;
		
		this.joueurs.add(new Joueur(nom));
		return true;
	}
	
	public String nomJoueur(int index) 
	{
		if (index >= this.joueurs.size())
			return "erreur";
		
		return this.joueurs.get(index).nom;
	}
	public String infosJoueur(int index)
	{
		if (index >= this.joueurs.size())
			return "erreur";
		
		return this.joueurs.get(index).toString();
	}	
	public String mainJoueur(int index)
	{
		if (index >= this.joueurs.size())
			return "erreur";
		
		return this.joueurs.get(index).lireMain();
	}
	
	boolean terminee;
	public boolean estTerminee() { return this.terminee; }
	
	public String prefix = "> ";
	public String prefixErreur = "#> ";
	
	public static final char MODE_ADVERSAIRE = '\'';

	static int reconnaitreExpression(String coup, int index)
	{
		if (index + 2 >= coup.length())
			return 0;

		int	longueurExpression = 0;
		
		if (coup.charAt(index + 2) == Pile.ASCENDANT || coup.charAt(index + 2) == Pile.DESCENDANT)
		{
			longueurExpression += 3;
			
			if (index + 3 < coup.length())
			{
				if (coup.charAt(index + 3) == MODE_ADVERSAIRE)
					longueurExpression++;
			}
		}
		
		return longueurExpression;
	}
	
	static boolean jouerCarte(Carte carte, char dest, Pile[] piles, boolean modeAdversaire)
	{	
		if (dest == Pile.ASCENDANT)
			return piles[0].ajouterCarte(carte, modeAdversaire);
		else if (dest == Pile.DESCENDANT)
			return piles[1].ajouterCarte(carte, modeAdversaire);
		
		return false;
	}
	
	int jouerCoup(String coup)
	{
		if (coup.length() < 3)
			return Main.TAILLE_MAX;
		
		int longueurExpression = 0;
		boolean modeAdversaireUtilise = false;
		int cartesPosees = 0;
		Carte carte;
		
		Main mainJoueur = new Main(this.joueurs.get(this.indexJoueurCourant()).copierMain());
		
		Pile[] pilesJoueurCourant = { 
				this.joueurs.get(this.indexJoueurCourant()).pileAscendante,
				this.joueurs.get(this.indexJoueurCourant()).pileDescendante
		};
		Pile[] pilesProchainJoueur = { 
				this.joueurs.get(this.indexProchainJoueur()).pileAscendante,
				this.joueurs.get(this.indexProchainJoueur()).pileDescendante
		};
		
		for (int i = 0; i < coup.length(); i++)
		{	
			if (coup.charAt(i) == ' ')
				continue;
			
			longueurExpression = reconnaitreExpression(coup, i);
			
			if (longueurExpression > 2)
			{
				int valeur = Integer.parseInt(coup.substring(i, i + 2));
				int index = mainJoueur.indexDepuisValeur(valeur);
				carte = mainJoueur.tirer(index);
				
				if (carte.lireValeur() != 0)
				{	
					boolean modeAdversaire = false;
					Pile[] piles = pilesJoueurCourant;
					
					if (longueurExpression == 4 && !modeAdversaireUtilise)
					{
						piles = pilesProchainJoueur;
						modeAdversaire = true;
						modeAdversaireUtilise = true;
					}
					
					if (jouerCarte(carte, coup.charAt(i + 2), piles, modeAdversaire))
					{
						i = i + longueurExpression - 1;
						cartesPosees++;
						continue;
					}
					else
					{
						System.err.println("Impossible de poser la carte " + carte + " sur la pile demandée.");
						System.err.println("ASC : " + piles[0] + " DESC : " + piles[1]);
						return Main.TAILLE_MAX;
					}
				}
				
				System.err.println("Saisie : " + coup.charAt(i) + " était inattendu.");
				return Main.TAILLE_MAX;
			}
			
			if (i > 0 && cartesPosees == 0)
				break;
		}
		
		if (cartesPosees > 0)
		{
			this.joueurs.get(this.indexJoueurCourant()).collerMain(mainJoueur);
			this.joueurs.get(this.indexJoueurCourant()).collerPiles(pilesJoueurCourant);
			
			if (modeAdversaireUtilise)
			{
				this.joueurs.get(this.indexProchainJoueur()).collerPiles(pilesProchainJoueur);
				for (int i = this.joueurs.get(this.indexJoueurCourant()).tailleMain(); i < Main.TAILLE_MAX; i++)
				{
					this.joueurs.get(this.indexJoueurCourant()).piocher();
					this.nbCartesPiochees++;
				}
			}
			else
			{
				for (int i = 0; i < 2; i++)
				{
					if (this.joueurs.get(this.indexJoueurCourant()).piocher())
						this.nbCartesPiochees++;
				}
			}
		}
		else
			return Main.TAILLE_MAX;
		
		return cartesPosees;
	}
	
	static Scanner entree = new Scanner(System.in);
	
	int coupJoueur()
	{
		int cartesPosees = 0;
		String coup = "";
		
		while (cartesPosees == Main.TAILLE_MAX || cartesPosees == 0)
		{
			if (cartesPosees == Main.TAILLE_MAX)
				System.out.print(this.prefixErreur);
			else
				System.out.print(this.prefix);
			
			coup = entree.nextLine();

			cartesPosees = this.jouerCoup(coup);
		}
		
		return cartesPosees;
	}
	
	public static final int NB_CARTES_POSEES_MIN = 2;
	
	public int nombreCoup;
	public int indexJoueurCourant() { return this.nombreCoup % NB_JOUEURS; }
	public int indexProchainJoueur() { return (this.nombreCoup + 1) % NB_JOUEURS; }
	
	int nbCartesPosees = 0;
	public int cartesPosees() { return this.nbCartesPosees; }
	int nbCartesPiochees = 0;
	public int cartesPiochees() { return this.nbCartesPiochees; }
	
	public void coupSuivant()
	{
		if (this.estTerminee() || this.joueurs.size() < NB_JOUEURS)
			return;
		
		if (this.joueurs.get(this.indexJoueurCourant()).tailleMain() < NB_CARTES_POSEES_MIN)
		{
			this.terminee = true;
			this.nombreCoup++;
			return;
		}
		
		this.nbCartesPiochees = 0;
		this.nbCartesPosees = this.coupJoueur();
		
		if (this.nbCartesPosees < NB_CARTES_POSEES_MIN)
		{
			this.terminee = true;
		}
		else if (this.joueurs.get(this.indexJoueurCourant()).tailleMain() == 0 && this.joueurs.get(this.indexJoueurCourant()).taillePioche() == 0)
		{
			this.terminee = true;
			return;
		}
			
		this.nombreCoup++;
	}

}
