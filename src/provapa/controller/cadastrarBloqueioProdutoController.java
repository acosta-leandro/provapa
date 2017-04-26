package provapa.controller;

import provapa.dao.BloqueioDao;
import provapa.entidades.Bloqueio;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import jidefx.scene.control.decoration.DecorationPane;
import provapa.apoio.Validation;

/**
 * Created by leandro on 04/07/16.
 */
public class cadastrarBloqueioProdutoController implements Initializable {

    BloqueioDao bloqueioDao = new BloqueioDao();
    private static principalController princCont = principalController.getInstance();
    private Bloqueio bloqueio = new Bloqueio();
    private List<Bloqueio> listBloqueio;
    private ObservableList<Bloqueio> observableListBloqueio;
    private boolean atualizando = false;

    @FXML
    private TableView<Bloqueio> tblBloqueio;
    @FXML
    private TableColumn<Bloqueio, Integer> tblColId;
    @FXML
    private TableColumn<Bloqueio, String> tblColBloqueio;
    @FXML
    private Button btnNovo;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnRemover;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnFechar;
    @FXML
    private Label lblBloqueioId;
    @FXML
    private Label lblId;
    @FXML
    private TextField tfdBloqueio;
    @FXML
    private TextField tfdPesquisa;
    @FXML
    private TitledPane titledPane;
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane anchor;
    @FXML
    private ComboBox cmbEstado;
    @FXML
    private TableColumn<Bloqueio, String> tblColEstado;

    @FXML
    private void handleBtnPesquisar() {
        ArrayList bloqueios = new ArrayList();
        if (tfdPesquisa.getText().equals("")) {
            bloqueios = bloqueioDao.consultarTodos();
        } else {
            bloqueios = bloqueioDao.consultar(tfdPesquisa.getText());
        }
        for (int i = 0; i < bloqueios.size(); i++) {
            Bloqueio tmpBloqueio = (Bloqueio) bloqueios.get(i);
        }
        ObservableList<Bloqueio> listBloqueios = FXCollections.observableArrayList(bloqueios);
        tblBloqueio.setItems(listBloqueios);
    }

    @FXML
    private void handleBtnRemover() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Excluir Bloqueio");
        alert.setHeaderText("Deseja excluir '" + bloqueio.getBloqueio() + "' ?");
        alert.setContentText("Tem certeza?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            bloqueioDao.excluir(Integer.valueOf(bloqueio.getId()));
            handleBtnCancelar();
            handleBtnPesquisar();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    @FXML
    private void handleBtnConfirmar() {
        if (atualizando) {
            bloqueioDao.atualizar(bloqueio);
        } else {
            bloqueioDao.salvar(bloqueio);
        }
        atualizando = false;
        handleBtnCancelar();
    }

    @FXML
    private void handleBtnCancelar() {
        tblBloqueio.getSelectionModel().clearSelection();
        lblId.setText("X");
        tfdBloqueio.setText("");
        tblBloqueio.setDisable(false);
        btnPesquisar.setDisable(false);
        handleBtnPesquisar();
    }

    @FXML
    private void handleBtnAtualizar() {
        tblBloqueio.setDisable(false);
        btnPesquisar.setDisable(false);
        bloqueioDao.atualizar(bloqueio);
        handleBtnPesquisar();
    }

    @FXML
    private void handleBtnFechar() {
        princCont.fecharTittledPane("cadastrarBloqueioProduto");
    }

    private void configuraColunas() {
        tblColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblColBloqueio.setCellValueFactory(new PropertyValueFactory<>("bloqueio"));
        tblColEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        handleBtnPesquisar();
    }

    // configura a lógica da tela
    private void configuraBindings() {
        //bids de campos
        bloqueio.idProperty().bind(lblId.textProperty());
        bloqueio.bloqueioProperty().bind(tfdBloqueio.textProperty());
        // quando algo é selecionado na tabela, preenchemos os campos de entrada com os valores para o 
        tblBloqueio.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<Bloqueio>() {
            @Override
            public void changed(ObservableValue<? extends Bloqueio> observable, Bloqueio oldValue, Bloqueio newValue) {
                if (newValue != null) {
                    tfdBloqueio.textProperty().bindBidirectional(newValue.bloqueioProperty());
                    lblId.textProperty().bind(Bindings.convert(newValue.idProperty()));
                    tblBloqueio.setDisable(true);
                    btnPesquisar.setDisable(true);
                    atualizando = true;
                    cmbEstado.getSelectionModel().select(newValue.getEstado());
                } else {
                    lblId.textProperty().unbind();
                    tfdBloqueio.textProperty().unbind();
                    tfdBloqueio.setText("");
                    lblId.setText("X");
                }
            }
        });
        cmbEstado.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    bloqueio.setEstado((String) cmbEstado.getSelectionModel().getSelectedItem());
                }
            }
        });
    }

    private void validar() {
        DecorationPane decorationPane = new DecorationPane(anchor);
        root.getChildren().add(decorationPane);
        Validation.validate(tfdBloqueio, Validation.VARCHAR25);
    }

    private void liberarBotoes() {
        btnConfirmar.disableProperty().bind(Validation.validGroup.not());
        //     btnRemover.disableProperty().bind(tblBloqueio.getSelectionModel().selectedItemProperty().isNull());
        btnRemover.setDisable(true);
    }

    private void popularCmbEstado() {
        ObservableList<String> estados = FXCollections.observableArrayList();
        estados.add("Ativo");
        estados.add("Desativado");
        cmbEstado.getItems().addAll(estados);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validar();
        liberarBotoes();
        configuraColunas();
        configuraBindings();
        popularCmbEstado();
        cmbEstado.getSelectionModel().selectFirst();
    }
}
