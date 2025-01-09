import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        int taille = 0;
        int nbTentative = 0;
        System.out.println("Bienvenue sur le Mastermind !");
        System.out.println("Les indices pour chaque chiffre seront :");
        System.out.println("0 : Le chiffre n'existe pas.");
        System.out.println("1 : Le chiffre existe mais n'est pas à la bonne place.");
        System.out.println("2 : Le chiffre est correct et bien placé.");
        System.out.print("Voulez vous choisir la taille de la sequence secrete ? (o/n) : ");
        taille = getTaille(clavier, taille);
        System.out.print("Voulez vous choisir le nombre de proposition possible ? (o/n) : ");
        String choix = clavier.next();
        if (choix.equals("n") || choix.equals("non")) {
            nbTentative = 12;
        } else if (choix.equals("o") || choix.equals("oui")) {
            System.out.println("Veuillez choisir le nombre de proposition souhaité");
            nbTentative = clavier.nextInt();
        }
        boolean sequenceTrouve = false;
        int[] sequenceSecrete = new int[taille];
        // on va appeler notre fonction qui remplis le tableau
        remplissageTableau(sequenceSecrete);
        //initialisation et declaration des historique
        int[][] historiqueProposition = new int[nbTentative][taille];
        int[][] historiqueIndices = new int[nbTentative][taille];
        // boucle de jeu
        for (int tentative = 0; tentative < nbTentative; tentative++) {

            int[] propositionFaite = new int[taille];
            System.out.println("Il vous reste " + (nbTentative - tentative) + " de tentative");
            // on demande la proposition et on la stock dans un tableau
            System.out.println("Veuillez saisir votre proposition de " + taille + " chiffres (séparés par des espaces) : ");
            for (int j = 0; j < taille; j++) {
                propositionFaite[j] = clavier.nextInt();
            }
            //boucle pour l'historique des proposition
            for (int i = 0; i < taille; i++) {
                historiqueProposition[tentative][i] = propositionFaite[i];
            }
            //initialisation d'un tableau pour les indices
            int[] indices = new int[propositionFaite.length];
            // appele d'une fonction pour verifier si la proposition est juste
            verificationSequence(sequenceSecrete, propositionFaite, indices);
            // boucle pour l'historique des indices
            for (int i = 0; i < taille; i++) {
                historiqueIndices[tentative][i] = indices[i];
            }
            //affichage des historique
            for (int i = 0; i <= tentative; i++) {
                System.out.print("Proposition " + (i + 1) + ": ");
                for (int j = 0; j < taille; j++) {
                    System.out.print(historiqueProposition[i][j] + " ");
                }
                System.out.print("| Indices : ");
                for (int j = 0; j < taille; j++) {
                    System.out.print(historiqueIndices[i][j] + " ");
                }
                System.out.println();
            }
            // a chaque tour la on retire 1 au nb de tentative
            sequenceTrouve = true;
            for (int i = 0; i < taille; i++) {
                if (indices[i] != 2) {
                    sequenceTrouve = false;
                    break;
                }
            }
            if (sequenceTrouve) {
                System.out.println("Bravo vous avez trouvez le sequence secrete !");
                break;
            }
        }

        if(!sequenceTrouve){
            System.out.println("Dommage vous n'avez trouvez le sequence !");
            System.out.print("La sequence etait : ");
            for(int i=0; i<taille; i++) {
                System.out.println(sequenceSecrete[i]+" ");
            }
        }


    }

    private static int getTaille(Scanner clavier, int taille) {
        String choix = clavier.nextLine();
        if (choix.equals("n") || choix.equals("non")) {
            taille = 5;
        } else if (choix.equals("o")|| choix.equals("oui"))  {
            System.out.println("Veuillez choisir votre taille desirée");
            taille =clavier.nextInt();
        }
        return taille;
    }

    public static void remplissageTableau(int[] sequenceSecrete) {
        /* boucle pour remplir le tableau */
        for(int i=0; i<sequenceSecrete.length; i++) {
            boolean unique;
            int nb;
            do {
                // la variable nb est un nb genere entre 1 et 9
                nb = (int) (Math.random() * 9) + 1;
                unique = true;
                for (int j = 0; j < i; j++) {
                    if (sequenceSecrete[j] == nb) {
                        unique = false;
                        break;
                    }
                }
            } while (!unique);// boucle tant que unique est false
            sequenceSecrete[i] = nb; // on rempli le tableau
        }
    }
    public static void verificationSequence(int[] sequenceSecrete, int[] propositionFaite, int[]indices) {

        /* rajout d'un tableau pour ne pas compter deux fois un meme nb */
        boolean [] compter = new boolean[sequenceSecrete.length];
        for(int i = 0; i < propositionFaite.length; i++) {
            // condition pour verifier les chiffres bien placé
            if(propositionFaite[i]== sequenceSecrete[i] ){
                indices[i] =2;
                compter[i] = true;
            }
            else {
                indices[i] = 0; // Initialise à 0
            }
        }
        // verification des mal placé
        for (int i = 0; i < sequenceSecrete.length; i++) {
            if(indices[i] == 0){
                for(int j = 0; j < sequenceSecrete.length; j++) {
                    if(!compter[j] && propositionFaite[i]== sequenceSecrete[j]){
                        indices[i]=1;
                        compter[j] = true;
                        break;
                    }
                }

            }
        }
    }
}