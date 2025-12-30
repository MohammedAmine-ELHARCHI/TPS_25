package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestApplication {

    public static void main(String[] args) {
        // Charger le contexte Spring
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Récupérer les services
        HommeService hommeService = context.getBean(HommeService.class);
        FemmeService femmeService = context.getBean(FemmeService.class);
        MariageService mariageService = context.getBean(MariageService.class);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            Femme spouse1 = new Femme("EL BACHIRI", "HIND", "0610101010", "Casablanca", sdf.parse("15/05/1970"));
            Femme spouse2 = new Femme("TALIBI", "MERYEM", "0620202020", "Rabat", sdf.parse("20/08/1975"));
            Femme spouse3 = new Femme("KASSIMI", "HABIBA", "0630303030", "Fes", sdf.parse("10/03/1978"));
            Femme spouse4 = new Femme("SEFRI", "JALILA", "0640404040", "Marrakech", sdf.parse("25/12/1968"));
            Femme spouse5 = new Femme("MOUNIB", "SARAH", "0650505050", "Tanger", sdf.parse("30/07/1980"));
            Femme spouse6 = new Femme("HADDAD", "MALIKA", "0660606060", "Agadir", sdf.parse("18/11/1972"));
            Femme spouse7 = new Femme("MANSOURI", "ZOHRA", "0670707070", "Kenitra", sdf.parse("05/02/1985"));
            Femme spouse8 = new Femme("AZZOUZI", "GHIZLANE", "0680808080", "Meknes", sdf.parse("22/09/1976"));
            Femme spouse9 = new Femme("MEHDI", "HASNAA", "0690909090", "Oujda", sdf.parse("14/04/1982"));
            Femme spouse10 = new Femme("RIZKI", "SANAE", "0601010101", "Tetouan", sdf.parse("08/06/1979"));

            femmeService.create(f1);
            femmeService.create(f2);
            femmeService.create(f3);
            femmeService.create(f4);
            femmeService.create(f5);
            femmeService.create(f6);
            femmeService.create(f7);
            femmeService.create(f8);
            femmeService.create(f9);
            femmeService.create(f10);
            System.out.println("10 femmes créées avec succès!");

           Homme partner1 = new Homme("FASSI", "ADNANE", "0798765432", "Casablanca", sdf.parse("12/01/1965"));
            Homme partner2 = new Homme("CHAFIK", "REDOUANE", "0787654321", "Rabat", sdf.parse("28/03/1970"));
            Homme partner3 = new Homme("MEZIANE", "AMINE", "0776543210", "Fes", sdf.parse("17/07/1968"));
            Homme partner4 = new Homme("GUEZZAR", "YASSINE", "0765432109", "Marrakech", sdf.parse("03/11/1975"));
            Homme partner5 = new Homme("BOUZIDI", "TARIQ", "0754321098", "Tanger", sdf.parse("21/09/1972"));

            hommeService.create(h1);
            hommeService.create(h2);
            hommeService.create(h3);
            hommeService.create(h4);
            hommeService.create(h5);
            System.out.println("5 hommes créés avec succès!");

          Mariage union_A = new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0, h1, f4);
            Mariage union_B = new Mariage(sdf.parse("03/09/1990"), null, 4, h1, f1);
            Mariage union_C = new Mariage(sdf.parse("03/09/1995"), null, 2, h1, f2);
            Mariage union_D = new Mariage(sdf.parse("04/11/2000"), null, 3, h1, f3);

            Mariage union_E = new Mariage(sdf.parse("10/06/1992"), sdf.parse("15/08/1995"), 1, h2, f5);
            Mariage union_F = new Mariage(sdf.parse("20/09/1996"), null, 2, h3, f5);

            Mariage union_G = new Mariage(sdf.parse("15/03/1993"), sdf.parse("10/07/1998"), 0, h4, f6);
            Mariage union_H = new Mariage(sdf.parse("25/11/2000"), null, 3, h5, f6);

            Mariage union_I = new Mariage(sdf.parse("12/04/1994"), null, 2, h2, f7);
            Mariage union_J = new Mariage(sdf.parse("08/08/1997"), null, 1, h3, f8);
            Mariage union_K = new Mariage(sdf.parse("19/02/1999"), null, 2, h4, f9);
            Mariage union_L = new Mariage(sdf.parse("30/05/2001"), null, 1, h5, f10);

            mariageService.create(m1);
            mariageService.create(m2);
            mariageService.create(m3);
            mariageService.create(m4);
            mariageService.create(m5);
            mariageService.create(m6);
            mariageService.create(m7);
            mariageService.create(m8);
            mariageService.create(m9);
            mariageService.create(m10);
            mariageService.create(m11);
            mariageService.create(m12);
            System.out.println("Mariages créés avec succès!");

           List<Femme> listeDeFemmes = femmeService.findAll();
            System.out.println("\n--- Affichage Complet des Épouses ---");
            for (Femme fItem : listeDeFemmes) {
                System.out.println("-> " + fItem.getPrenom() + " " + fItem.getNom() +
                                            " (Date de naissance : " + sdf.format(fItem.getDateNaissance()) + ")");
            }

            Femme laPlusVieille = femmeService.findFemmeLaPlusAgee();
            if (laPlusVieille != null) {
                System.out.println("\n** La doyenne du groupe est : " + laPlusVieille.getPrenom() + " " +
                                            laPlusVieille.getNom() + " (Née le : " +
                                            sdf.format(laPlusVieille.getDateNaissance()) + ") **");
            }

            Date debutIntervalle = sdf.parse("01/01/1990");
            Date finIntervalle = sdf.parse("31/12/1999");
            List<Femme> conjointsParPeriode = hommeService.findEpousesEntreDeuxDates(h1.getId(), debutIntervalle, finIntervalle);
            System.out.println("\nConjointes de " + h1.getPrenom() + " " + h1.getNom() +
                                     " entre " + sdf.format(debutIntervalle) + " et " + sdf.format(finIntervalle) + " :");
            for (Femme fItem : conjointsParPeriode) {
                System.out.println(" \t- " + fItem.getPrenom() + " " + fItem.getNom());
            }

            Date debutPeriodeEnfant = sdf.parse("01/01/1989");
            Date finPeriodeEnfant = sdf.parse("31/12/2005");
            int totalEnfants = femmeService.countEnfantsFemmeEntreDates(f1.getId(), debutPeriodeEnfant, finPeriodeEnfant);
            System.out.println("\nBilan des enfants de " + f1.getPrenom() + " " + f1.getNom() +
                                     " (période : " + sdf.format(debutPeriodeEnfant) + " à " + sdf.format(finPeriodeEnfant) +
                                     ") : " + totalEnfants + " enfants.");

            List<Femme> marieesMultiples = femmeService.findFemmesMarieesAuMoinsDeuxFois();
            System.out.println("\nListe des femmes polygames (au moins 2 mariages) :");
            for (Femme fItem : marieesMultiples) {
                System.out.println(" \t-> " + fItem.getPrenom() + " " + fItem.getNom());
            }

            Date dateStartCount = sdf.parse("01/01/1989");
            Date dateEndCount = sdf.parse("31/12/2005");
            int hommesQuatreFemmes = hommeService.countHommesMarieA4FemmesEntreDates(dateStartCount, dateEndCount);
            System.out.println("\nTotal des hommes ayant épousé 4 femmes entre " +
                                     sdf.format(dateStartCount) + " et " + sdf.format(dateEndCount) + " : " + hommesQuatreFemmes);

            Homme manDetails = hommeService.findById(h1.getId());
            List<Mariage> listUnions = hommeService.findMariagesByHomme(h1.getId());

            System.out.println("\n*** Historique des unions pour " + manDetails.getPrenom() + " " + manDetails.getNom() + " ***");

            System.out.println("\n\t- Unions en cours -");
            int indexActuel = 1;
            for (Mariage mUnion : listUnions) {
                if (mUnion.getDateFin() == null) {
                    System.out.println("\t  " + indexActuel + ". Épouse : " + mUnion.getFemme().getPrenom() +
                                             " " + mUnion.getFemme().getNom() +
                                             " | Depuis le : " + sdf.format(mUnion.getDateDebut()) +
                                             " | Progéniture : " + mUnion.getNbrEnfant());
                    indexActuel++;
                }
            }

            System.out.println("\n\t- Unions terminées (séparation) -");
            int indexTermine = 1;
            for (Mariage mUnion : listUnions) {
                if (mUnion.getDateFin() != null) {
                    System.out.println("\t  " + indexTermine + ". Épouse : " + mUnion.getFemme().getPrenom() +
                                             " " + mUnion.getFemme().getNom() +
                                             " | Début : " + sdf.format(mUnion.getDateDebut()));
                    System.out.println("\t  \t\tFin le : " + sdf.format(mUnion.getDateFin()) +
                                             " | Nombre d'enfants : " + mUnion.getNbrEnfant());
                    indexTermine++;
                }
            }


        } catch (Exception e) {
            System.err.println("!!! Problème survenu lors de l'exécution des scénarios !!!");
            e.printStackTrace();
        }

        // Fermer le contexte
        ((ClassPathXmlApplicationContext) context).close();
    }
}
