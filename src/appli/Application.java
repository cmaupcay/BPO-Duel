/**
 * 
 */
package appli;

import jeu.Partie;

public class Application {
	
	public static void main(String[] args) {

		Partie partie = new Partie();
		partie.nouveauJoueur("NORD");
		partie.nouveauJoueur("SUD");

		int indexJoueurCourant = 0;
		while (!partie.estTerminee())
		{
			indexJoueurCourant = partie.indexJoueurCourant();
			
			System.out.println(partie.infosJoueur(0));
			System.out.println(partie.infosJoueur(1));
			
			System.out.print("cartes " + partie.nomJoueur(indexJoueurCourant) + " ");
			System.out.println(partie.mainJoueur(indexJoueurCourant));
			
			partie.coupSuivant();
			
			System.out.print(partie.cartesPosees() + " cartes posées, ");
			System.out.println(partie.cartesPiochees() + " cartes piochées");
		}
		
		int indexGagnant = partie.indexJoueurCourant();
		
		System.out.println("partie finie, " + partie.nomJoueur(indexGagnant) + " a gagné");
		
	}

}
