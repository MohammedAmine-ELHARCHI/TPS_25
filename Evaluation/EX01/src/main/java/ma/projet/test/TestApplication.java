package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestApplication {

  public static void main(String[] args)
  {
    // Charger le contexte Spring
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    // Récupérer les services
    CategorieService categorieService = context.getBean(CategorieService.class);
    ProduitService produitService = context.getBean(ProduitService.class);
    CommandeService commandeService = context.getBean(CommandeService.class);
    LigneCommandeService ligneCommandeService = context.getBean(LigneCommandeService.class);

    try {
      System.out.println(" Étape 1 : création des catégories ");
      Categorie cat1 = new Categorie("c1", "l1");
      Categorie cat2 = new Categorie("c2", "L2");
      Categorie cat3 = new Categorie("c3", "L3");

      categorieService.create(cat1);
      categorieService.create(cat2);
      categorieService.create(cat3);
      System.out.println("Les catégories ont été créées avec succès.");

      System.out.println("\nÉtape 2 : création des produits");
      Produit p1 = new Produit("ES12", 120f, cat1);
      Produit p2 = new Produit("ZR85", 100f, cat2);
      Produit p3 = new Produit("EE85", 200f, cat3);

      produitService.create(p1);
      produitService.create(p2);
      produitService.create(p3);

      System.out.println("Les produits ont été créés avec succès.");

      System.out.println("\nÉtape 3 : affichage des produits par catégorie");
      List<Produit> produitsCategorie1 = produitService.findByCategorie(cat1);
      System.out.println("Produits appartenant à la catégorie " + cat1.getLibelle() + " :");
      for (Produit p : produitsCategorie1) {
        System.out.println("  • Réf " + p.getReference() + " — " + p.getPrix() + " DH");
      }

      System.out.println("\nÉtape 4 : création des commandes");
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Commande cmd1 = new Commande(sdf.parse("14/03/2013"));
      Commande cmd2 = new Commande(sdf.parse("20/04/2013"));
      Commande cmd3 = new Commande(sdf.parse("15/05/2013"));

      commandeService.create(cmd1);
      commandeService.create(cmd2);
      commandeService.create(cmd3);
      System.out.println("Les commandes ont été créées avec succès.");

      System.out.println("\nÉtape 5 : création des lignes de commande");
      LigneCommandeProduit lc1 = new LigneCommandeProduit(cmd1, p1, 7);
      LigneCommandeProduit lc2 = new LigneCommandeProduit(cmd1, p2, 14);
      LigneCommandeProduit lc3 = new LigneCommandeProduit(cmd1, p3, 5);


      ligneCommandeService.create(lc1);
      ligneCommandeService.create(lc2);
      ligneCommandeService.create(lc3);

      System.out.println("Les lignes de commande ont été créées avec succès.");

      System.out.println("\nÉtape 6 : affichage des produits liés à une commande");
      System.out.println("Commande : " + cmd1.getId() + "     Date : 14 Mars 2013");
      System.out.println("Liste des produits :");
      System.out.println("Référence\tPrix\t\tQuantité");
      
      List<Object[]> produitsCommande = produitService.findProduitsParCommande(cmd1.getId());
      for (Object[] row : produitsCommande) {
        String reference = (String) row[0];
        float prix = (float) row[1];
        int quantite = (int) row[2];
        System.out.println(reference + "\t\t" + prix + " DH\t" + quantite);
      }

      System.out.println("\nÉtape 7 : produits commandés entre deux dates");
      Date dateDebut = sdf.parse("01/03/2013");
      Date dateFin = sdf.parse("30/04/2013");
      
      List<Produit> produitsEntreDates = produitService.findProduitsCommandesEntreDates(dateDebut, dateFin);
      System.out.println("Liste des produits commandés du " + sdf.format(dateDebut) + " au " + sdf.format(dateFin) + " :");
      for (Produit p : produitsEntreDates) {
        System.out.println("  • Réf " + p.getReference() + " — " + p.getPrix() + " DH");
      }

      System.out.println("\nÉtape 8 : produits dont le prix est supérieur à 100 DH (requête nommée)");
      List<Produit> produitsChers = produitService.findProduitsPrixSuperieur(100f);
      System.out.println("Produits dont le prix dépasse 100 DH :");
      for (Produit p : produitsChers) {
        System.out.println("  • " + p.getReference() + " — " + p.getPrix() + " DH (" + p.getCategorie().getLibelle() + ")");
      }

      System.out.println("\nTous les tests se sont déroulés avec succès !");

    } catch (Exception e) {
      System.err.println("Une erreur s'est produite pendant l'exécution des tests :");
      e.printStackTrace();
    }

    // Fermer le contexte
    ((ClassPathXmlApplicationContext) context).close();
  }
}