package com.example.back;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UniversalController {

    // Os campos da tela. O nome tem que ser igual ao fx:id do universal-view.fxml
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtSenha;

    // Guardo aqui o que a API respondeu: o código (200, 400...) e o texto (ex.: "CPF Invalido!")
    private int codigoResposta;
    private String corpoResposta;

    // Aqui faz: monta o JSON com os dados da tela e manda para o backend criar o universal.
    // Vai sem token, igual ao Cadastro Admin; o backend confere a secretKey.
    @FXML
    public void salvarUniversal(ActionEvent event) throws Exception {

        String jsonRequest = String.format("{\n" +
                        " \"nome\": \"%s\",\n" +
                        " \"email\": \"%s\",\n" +
                        " \"senha\": \"%s\",\n" +
                        " \"cpf\": \"%s\",\n" +
                        " \"secretKey\": \"%s\"\n" +
                        "}", txtNome.getText(),
                txtEmail.getText(),
                txtSenha.getText(),
                txtCpf.getText(),
                "kkkkkk12345678910111213141516");

        executaMetodoAPI("http://localhost:8080/usuarios/universal", jsonRequest, "POST");

        // Deu certo: mostro a mensagem e volto para o menu.
        // Deu erro: mostro o motivo que a própria API mandou (CPF inválido, secretKey errada...).
        if (codigoResposta == 200) {
            showMessage(Alert.AlertType.INFORMATION, "Usuário universal salvo com sucesso!");
            voltar(event);
        } else {
            showMessage(Alert.AlertType.ERROR, "Erro ao salvar usuário universal!\nMotivo: " + corpoResposta);
        }
    }

    // Aqui faz: volta para a tela do menu
    @FXML
    public void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/back/menu-view.fxml"));

        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    // Aqui faz: faz a chamada HTTP para a API e guarda o código e o texto da resposta.
    // Diferente do UsuarioController: além do código, eu LEIO o texto da resposta.
    // Se deu erro (400 ou mais), o texto vem pelo getErrorStream(); se deu certo, pelo getInputStream().
    private void executaMetodoAPI(String url, String json, String protocoloHttp) throws Exception {

        URL urlAPI = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlAPI.openConnection();
        connection.setRequestMethod(protocoloHttp);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        codigoResposta = connection.getResponseCode();
        InputStream corpo = codigoResposta < 400 ? connection.getInputStream() : connection.getErrorStream();
        corpoResposta = corpo == null ? "" : new String(corpo.readAllBytes(), StandardCharsets.UTF_8).trim();
    }

    private void showMessage(Alert.AlertType type, String msg) {
        Alert alerta = new Alert(type);
        alerta.setTitle("Mensagem do Sistema!");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}