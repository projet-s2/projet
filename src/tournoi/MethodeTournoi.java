package tournoi;

import java.util.ArrayList;
import java.util.Collections;

/** La classe contient la/les méthodes statiques utiles pour le tournoi, notamment la génération de Paires
 * Created by Martineau on 11/12/2016.
 */
public class MethodeTournoi {

    /**
     * Appelée pour créer la liste des paires d'un tournoi
     * On attribue un partenaire uniquement aux joueurs actifs (ceux qui sont disponibles pour jouer)
     * On cherche d'abord à faire jouer ceux qui ont le moins participé
     * On cherche ensuite à faire jouer les joueurs qui n'ont pas joué au tour d'avant (les prios)
     * On fait ensuite jouer les autres joueurs
     */

    public static ArrayList<Paire> creerPaire(ArrayList<Joueur> allJoueurs) {
        /**
         *(Prio, Prio + !jouéEnsemble + !ancienneté) Boucle 1
         *(Prio, Prio + !jouéEnsemble + ancienneté) Boucle 2
         *(Prio, Prio + jouéEnsemble + !ancienneté) Boucle 3
         *(Prio, Prio + jouéEnsemble + ancienneté) Boucle 4
         *
         *(Prio, !Prio + !jouéEnsemble + !ancienneté) Boucle 5
         *(Prio, !Prio + !jouéEnsemble + ancienneté) Boucle 6
         *(Prio, !Prio + jouéEnsemble + !ancienneté) Boucle 7
         *(Prio, !Prio + jouéEnsemble + ancienneté) Boucle 8
         *
         *(!Prio, !Prio + !jouéEnsemble + !ancienneté) Boucle 9
         *(!Prio, !Prio + !jouéEnsemble + ancienneté) Boucle 10
         *(!Prio, !Prio + jouéEnsemble + !ancienneté) Boucle 11
         *(!Prio, !Prio + jouéEnsemble + ancienneté) Boucle 12
         */
        ArrayList<Paire> res = new ArrayList<>();

        double perfMoyenne; //Le score de performance moyen de tous les joueurs du tournoi
        int sommePerf = 0;
        for(Joueur joueur : allJoueurs) {
            sommePerf += joueur.getPerf();
        }

        perfMoyenne = (sommePerf / allJoueurs.size()) * 2;

        //On parcourt les deux listes de joueurs et on crée les paires en conséquence
        //On récupères les listes des nouveaux joueurs actifs et des anciens joueurs actif

        ArrayList<Joueur> nouveauxJoueursActifs = new ArrayList<>();
        ArrayList<Joueur> anciensJoueursActifs = new ArrayList<>();

        //ArrayList des nouveaux joueurs actifs
        for (Joueur joueur : allJoueurs) {
            if (joueur.getNouveau() && joueur.peutJouer()) {
                nouveauxJoueursActifs.add(joueur);
            }
        }

        //ArrayList des anciens joueurs actifs
        for (Joueur joueur : allJoueurs) {
            if (!joueur.getNouveau() && joueur.peutJouer()) {
                anciensJoueursActifs.add(joueur);
            }
        }

        //Création de l'ArrayList des joueurs Prio
        ArrayList<Joueur> joueursPrios = new ArrayList<>();
        for (Joueur nouveauJoueur : nouveauxJoueursActifs) {
            if (nouveauJoueur.getPrio()) {
                joueursPrios.add(nouveauJoueur);
            }
        }
        for (Joueur ancienJoueur : anciensJoueursActifs) {
            if (ancienJoueur.getPrio()) {
                joueursPrios.add(ancienJoueur);
            }
        }

        //Création de l'ArrayList des joueurs non Prio
        ArrayList<Joueur> joueursNonPrios = new ArrayList<>();
        for (Joueur nouveauJoueur : nouveauxJoueursActifs) {
            if (!nouveauJoueur.getPrio()) {
                joueursNonPrios.add(nouveauJoueur);
            }
        }
        for (Joueur ancienJoueur : anciensJoueursActifs) {
            if (!ancienJoueur.getPrio()) {
                joueursNonPrios.add(ancienJoueur);
            }
        }

        //en triant les listes en fonction du nombres de match joué en parcourant les liste on prendra en premier des
        // les joueur avec le moins de matchs a leurs actif
        Collections.sort(anciensJoueursActifs, new ComparateurjoueurParNbMatches());
        Collections.sort(nouveauxJoueursActifs, new ComparateurjoueurParNbMatches());

        Paire paireChoisie; //La paire qui a la perf la plus proche de la moyenne
        //La différence de score entre la perf moyenne et celle de la paire (on prend 20 parce qu'on est sûr que c'est
        //supérieur à la plus grosse différence possible
        double differencePerfChoisie = 20.0;

        // Boucle n°1
        for (Joueur joueur1 : joueursPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire() &&
                            joueur1.estCompatibleAvec(joueur2) && //Pas déjà joué ensemble
                            (joueur1.getNouveau()!= joueur2.getNouveau())) { //Pas la même ancienneté

                        //Si la difference de score entre la perf moyenne et celle des deux joueurs est inférieure à celle choisie pour le moment
                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("1");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        //Boucle n°2
        differencePerfChoisie = 20.0;
        for (Joueur joueur1 : joueursPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire() &&
                            joueur1.estCompatibleAvec(joueur2)) { //Pas déjà joué ensemble

                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("2");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        //Boucle n°3
        differencePerfChoisie = 20.0;
        for (Joueur joueur1 : joueursPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire() &&
                            (joueur1.getNouveau()!= joueur2.getNouveau())) { //Pas la même ancienneté

                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("3");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        //Boucle n°4
        differencePerfChoisie = 20.0;
        for (Joueur joueur1 : joueursPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire()) {

                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("4");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        //Les paires de Prio-Non prio
        //Boucle n°5
        differencePerfChoisie = 20.0;
        for (Joueur joueur1 : joueursPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursNonPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire() &&
                            joueur1.estCompatibleAvec(joueur2) && //Pas déjà joué ensemble
                            (joueur1.getNouveau()!= joueur2.getNouveau())) { //Pas la même ancienneté

                        //Si la difference de score entre la perf moyenne et celle des deux joueurs est inférieure à celle choisie pour le moment
                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("5");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        //Boucle n°6
        differencePerfChoisie = 20.0;
        for (Joueur joueur1 : joueursPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursNonPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire() &&
                            joueur1.estCompatibleAvec(joueur2)) { //Pas déjà joué ensemble

                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("6");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        //Boucle n°7
        differencePerfChoisie = 20.0;
        for (Joueur joueur1 : joueursPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursNonPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire() &&
                            (joueur1.getNouveau()!= joueur2.getNouveau())) { //Pas la même ancienneté

                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("7");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        //Boucle n°8
        differencePerfChoisie = 20.0;
        for (Joueur joueur1 : joueursPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursNonPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire()) {

                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("8");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        //Les paires de non Prio-Non prio

        // Boucle n°9
        for (Joueur joueur1 : joueursNonPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursNonPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire() &&
                            joueur1.estCompatibleAvec(joueur2) && //Pas déjà joué ensemble
                            (joueur1.getNouveau()!= joueur2.getNouveau())) { //Pas la même ancienneté

                        //Si la difference de score entre la perf moyenne et celle des deux joueurs est inférieure à celle choisie pour le moment
                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("9");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        //Boucle n°10
        differencePerfChoisie = 20.0;
        for (Joueur joueur1 : joueursNonPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursNonPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire() &&
                            joueur1.estCompatibleAvec(joueur2)) { //Pas déjà joué ensemble

                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("10");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        //Boucle n°11
        differencePerfChoisie = 20.0;
        for (Joueur joueur1 : joueursNonPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursNonPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire() &&
                            (joueur1.getNouveau()!= joueur2.getNouveau())) { //Pas la même ancienneté

                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("11");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        //Boucle n°12
        differencePerfChoisie = 20.0;
        for (Joueur joueur1 : joueursNonPrios) {
            paireChoisie = null;
            if (!joueur1.getDansPaire()) {
                for(Joueur joueur2 : joueursNonPrios) {
                    if(!joueur1.equals(joueur2) &&
                            !joueur2.getDansPaire()) {

                        if(Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf())) < differencePerfChoisie) {
                            paireChoisie = new Paire(joueur1, joueur2);
                            differencePerfChoisie = Math.abs(perfMoyenne - (joueur1.getPerf()+joueur2.getPerf()));
                        }
                    }
                }
                if (paireChoisie != null) {
                    System.out.println("12");
                    res.add(new Paire(paireChoisie.getJoueur1(), paireChoisie.getJoueur2()));
                    paireChoisie.getJoueur1().setDansPaire(true);
                    paireChoisie.getJoueur2().setDansPaire(true);
                }
            }
        }

        return res;
    }
}
