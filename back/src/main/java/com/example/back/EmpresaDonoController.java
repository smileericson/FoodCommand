package com.example.back;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmpresaDonoController {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtRazaoSocial;
    @FXML
    private TextField txtCnpj;
    @FXML
    private TextField txtNomeFantasia;
    @FXML
    private Label lblEndereco;

    @FXML
    public void buscarCnpj(ActionEvent event) {

        String cnpj = txtCnpj.getText().replaceAll("\\D", "");
        if (cnpj.isEmpty()) {
            showMessage(Alert.AlertType.ERROR, "Informe o CNPJ para buscar.");
            return;
        }

        try {
            URL urlAPI = new URL("http://localhost:8080/cnpj/" + cnpj);
            HttpURLConnection connection = (HttpURLConnection) urlAPI.openConnection();
            connection.setRequestMethod("GET");

            int codigoResposta = connection.getResponseCode();
            InputStream corpo = codigoResposta < 400 ? connection.getInputStream() : connection.getErrorStream();
            String resposta = corpo == null ? "" : new String(corpo.readAllBytes(), StandardCharsets.UTF_8).trim();

            if (codigoResposta == 200) {
                String razaoSocial = lerCampo(resposta, "razaoSocial");
                String nomeFantasia = lerCampo(resposta, "nomeFantasia");

                txtRazaoSocial.setText(razaoSocial);
                txtNomeFantasia.setText(nomeFantasia.trim().isEmpty() ? razaoSocial : nomeFantasia);
                lblEndereco.setText(lerCampo(resposta, "logradouro") + ", " + lerCampo(resposta, "bairro")
                        + " - " + lerCampo(resposta, "cidade") + "/" + lerCampo(resposta, "uf")
                        + " - CEP " + lerCampo(resposta, "cep"));
            } else {
                txtRazaoSocial.setText("");
                txtNomeFantasia.setText("");
                lblEndereco.setText("");
                showMessage(Alert.AlertType.ERROR, "Erro ao buscar CNPJ!\nMotivo: " + resposta);
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Não foi possível conectar ao backend: " + e.getMessage());
        }
    }

    private String lerCampo(String json, String campo) {
        Matcher matcher = Pattern.compile("\"" + campo + "\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").matcher(json);
        if (matcher.find()) {
            return matcher.group(1).replace("\\\"", "\"").replace("\\\\", "\\");
        }
        return "";
    }


    @FXML
    public void salvarEmpresaDono(ActionEvent event) throws Exception {

        //Validar Dados


        String jsonRequest = String.format("{\n" +
                        " \"nome\": \"%s\",\n" +
                        " \"email\": \"%s\",\n" +
                        " \"senha\": \"%s\",\n" +
                        " \"cpf\": \"%s\",\n" +
                        " \"razaoSocial\": \"%s\",\n" +
                        " \"cnpj\": \"%s\",\n" +
                        " \"nomeFantasia\": \"%s\",\n" +
                        " \"secretKey\": \"%s\"\n" +
                        "}", txtNome.getText(),
                txtEmail.getText(),
                txtSenha.getText(),
                txtCpf.getText(),
                txtRazaoSocial.getText(),
                txtCnpj.getText(),
                txtNomeFantasia.getText(),
                "kkkkkk12345678910111213141516");

        int respostaAPI = ExecutaMetodoAPI("http://localhost:8080/usuarios/empresadono",jsonRequest,"POST");
        if(respostaAPI ==200){
            showMessage(Alert.AlertType.INFORMATION,"Empresa e dono salvos com sucesso!!");
            voltar(event);

        }else {
            showMessage(Alert.AlertType.ERROR,"Erro ao salvar Empresa e dono! Código: "+respostaAPI);
        }

    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/back/menu-view.fxml"));

        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

    private int ExecutaMetodoAPI(String url, String json, String protocolohttp) throws Exception {

        URL urlAPI = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlAPI.openConnection();
        connection.setRequestMethod(protocolohttp);

        connection.setDoOutput(true);

        if (!json.isEmpty()) {
            connection.setRequestProperty("Content-Type", "application/json");


        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes());
        }
    }
    return connection.getResponseCode();
    }
    private void showMessage(Alert.AlertType type, String msg){
        Alert alerta =new Alert(type);
        alerta.setTitle("Mensagem do Sistema!");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }

}
