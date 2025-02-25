package edu.pidev3a32.controllers;

import edu.pidev3a32.entities.Commande;
import edu.pidev3a32.services.CommandeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class CommandeController {

    @FXML private ListView<String> commandeList;

    private final CommandeService commandeService = new CommandeService();

    @FXML
    private void goBack(ActionEvent event) {
        loadScene(event, "/views/MainView.fxml");
    }

    @FXML
    public void initialize() {
        loadCommandes();
    }

    private void loadCommandes() {
        commandeList.getItems().clear();
        List<Commande> commandes = commandeService.getAllCommandes();
        for (Commande commande : commandes) {
            commandeList.getItems().add("Commande #" + commande.getIdCommande() + " - " + commande.getStatus());
        }
    }

    @FXML
    private void supprimerCommande() {
        String selectedCommande = commandeList.getSelectionModel().getSelectedItem();
        if (selectedCommande == null) {
            showAlert("Erreur", "Veuillez sélectionner une commande à supprimer.");
            return;
        }

        int idCommande = Integer.parseInt(selectedCommande.split("#")[1].split(" - ")[0].trim());

        if (commandeService.getCommandeById(idCommande) != null) {
            commandeService.supprimerCommande(idCommande);
            showAlert("Succès", "Commande supprimée avec succès.");
            loadCommandes();
        } else {
            showAlert("Erreur", "Commande non trouvée.");
        }
    }

    @FXML
    private void refreshCommandes() {
        loadCommandes();
        showAlert("Info", "Liste des commandes rafraîchie avec succès !");
    }

    @FXML
    public void gotoAj(ActionEvent event) {
        loadScene(event, "/views/AjoutCommande.fxml");
    }

    @FXML
    public void gotomodif(ActionEvent event) {
        loadScene(event, "/views/modifierCommande.fxml");
    }

    private void loadScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la vue demandée.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
