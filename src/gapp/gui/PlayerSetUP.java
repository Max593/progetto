package gapp.gui;

import gapp.ulg.play.PlayerFactories;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerSetUP {

    public static void pSetUPMenu() {
        Label pNameL = new Label("Player Name:"), pTypeL =  new Label("Player Type:"), spacing = new Label("");
        pNameL.setPrefWidth(150); pTypeL.setPrefWidth(150); spacing.setPrefWidth(60);
        HBox fLine = new HBox(10, spacing, pNameL, pTypeL);
        VBox elems = new VBox(10, fLine); elems.setPadding(new Insets(12,12,12,12));

        List<String> gTypes = Arrays.asList(PlayerFactories.availableBoardFactories());
        for(int i = 0; i < Main.playGUI.gF.maxPlayers(); i++) {
            Label pName = new Label("Player "+(i+1)+":"); pName.setPrefWidth(60);
            TextField name = new TextField(); name.setPrefWidth(150);
            ComboBox cb = new ComboBox(); cb.setPrefWidth(150);
            cb.getItems().addAll(gTypes); cb.setValue("Random Player"); cb.getItems().add("Player");

            elems.getChildren().add(new HBox(10, pName, name, cb));
        }

        Button start = new Button("Start");
        Button exit = new Button("Exit"); exit.setOnAction( e -> Main.thestage.close());

        HBox buttons = new HBox(10, start, exit); buttons.setAlignment(Pos.CENTER_RIGHT);
        elems.getChildren().add(buttons);

        //Salvataggio e controllo dei parametri
        start.setOnAction( e -> {
            for(int i = 1; i < elems.getChildren().size(); i++) {
                if(elems.getChildren().get(i).equals(buttons)) { break; }
                String name = ((TextField)((HBox)elems.getChildren().get(i)).getChildren().get(1)).getText();
                String type = (String)((ComboBox)((HBox)elems.getChildren().get(i)).getChildren().get(2)).getValue();
                if(Objects.equals(name, "")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning"); alert.setHeaderText(null); alert.setContentText("Player name not set!");
                    alert.showAndWait();
                    break;
                }

                if(Objects.equals(type, "Player")) { Main.playGUI.setPlayerGUI(i-1, name, GameBoard.humanPlayer()); }
                else { Main.playGUI.setPlayerFactory(i-1, type, name, null); } //Non sono certo del null, potrebbe essere necessario su alcuni tipi di giocatore
            }
            if(!Main.playGUI.enoughPlayers()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!"); alert.setHeaderText(null); alert.setContentText("Not enough players have been set!");
                alert.showAndWait();
            }
            else {
                Main.playGUI.play(0, 0, 0, 0, 0, 0); //Impostazione temporanea
                GameScreen.showGame();
            }
        });

        Main.thestage.setScene(new Scene(elems));
    }
}
