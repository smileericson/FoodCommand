package com.example.back;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class TipoRestauranteController {

    @FXML

    private TextField txtNome;

    @FXML

    protected void SalvarTipoRestaurante(ActionEvent event) throws Exception {

        String jsonRequest = String.format("{\"nome\": \"%s\",\n" +
                "  \"secretKey\": \"%s\"}",txtNome.getText(),"kkkkkk12345678910111213141516");

        int respostaApi =
                executaMetodoAPI("http://localhost:8080/tiporestaurante/criar",jsonRequest, "POST");

        if (respostaApi == 200){
            showMessage(Alert.AlertType.INFORMATION, "Comunicação salva com sucesso!");
            voltar(event);
        }else {
            showMessage(Alert.AlertType.ERROR, "Erro ao salvar comunicação!");
        }
    }// fechao o salvarTipoRestaurante

    @FXML
    protected void voltar(ActionEvent event) throws IOException {
    // Carrega o outro arquivo FXML (a tela de menu) e cria uma nova "Scene" (cena) com ele.
        FXMLLoader loader =
            new FXMLLoader(getClass().getResource("/com/example/back/menu-view.fxml"));

        Scene scene = new Scene(loader.load());
    // Pega a janela (Stage) atual a partir de quem disparou o evento (o botão clicado) e troca
    // a cena dela pela nova. É assim que a navegação entre telas funciona no JavaFX (sem rota/URL
    // como no Next.js - aqui é "troca a Scene dentro da mesma Stage").
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }    //fecha o "voltar"
    private int executaMetodoAPI(String url, String json, String protocoloHttp) throws Exception {

        URL urlAPI = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) urlAPI.openConnection();
        connection.setRequestMethod(protocoloHttp);
        connection.setDoOutput(true);

    if (!json.isEmpty()) {
        connection.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes());
        }
    }
        return connection.getResponseCode();
    } //fecha o "executaMetodoAPI"

    private void showMessage(Alert.AlertType type, String msg){
        Alert alerta =new Alert(type);
        alerta.setTitle("Mensagem do Sistema!");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    } //fecha o "showMessage"
}

