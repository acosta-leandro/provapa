package provapa.controller;

import provapa.dao.CategoriaDao;
import provapa.entidades.Categoria;
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
public class cadastrarCategoriaProdutoController implements Initializable {

    CategoriaDao categoriaDao = new CategoriaDao();
    private static principalController princCont = principalController.getInstance();
    private Categoria categoria = new Categoria();
    private List<Categoria> listCategoria;
    private ObservableList<Categoria> observableListCategoria;
    private boolean atualizando = false;

    @FXML
    private TableView<Categoria> tblCategoria;
    @FXML
    private TableColumn<Categoria, Integer> tblColId;
    @FXML
    private TableColumn<Categoria, String> tblColCategoria;
    @FXML
    private Button btnNovo;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnRemover;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnFechar;
    @FXML
    private Label lblCategoriaId;
    @FXML
    private Label lblId;
    @FXML
    private TextField tfdCategoria;
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
    private TableColumn<Categoria, String> tblColEstado;

    @FXML
    private void handleBtnPesquisar() {
        ArrayList categorias = new ArrayList();
        if (tfdPesquisa.getText().equals("")) {
            categorias = categoriaDao.consultarTodos();
        } else {
            categorias = categoriaDao.consultar(tfdPesquisa.getText());
        }
        for (int i = 0; i < categorias.size(); i++) {
            Categoria tmpCategoria = (Categoria) categorias.get(i);
        }
        ObservableList<Categoria> listCategorias = FXCollections.observableArrayList(categorias);
        tblCategoria.setItems(listCategorias);
    }

    @FXML
    private void handleBtnRemover() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Excluir Categoria");
        alert.setHeaderText("Deseja excluir '" + categoria.getCategoria() + "' ?");
        alert.setContentText("Tem certeza?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            categoriaDao.excluir(Integer.valueOf(categoria.getId()));
            handleBtnCancelar();
            handleBtnPesquisar();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    @FXML
    private void handleBtnConfirmar() {
        if (atualizando) {
            categoriaDao.atualizar(categoria);
        } else {
            categoriaDao.salvar(categoria);
        }
        atualizando = false;
        handleBtnCancelar();
    }

    @FXML
    private void handleBtnCancelar() {
        tblCategoria.getSelectionModel().clearSelection();
        lblId.setText("X");
        tfdCategoria.setText("");
        tblCategoria.setDisable(false);
        btnPesquisar.setDisable(false);
        handleBtnPesquisar();
    }

    @FXML
    private void handleBtnFechar() {
        princCont.fecharTittledPane("cadastrarCategoriaProduto");
    }

    private void configuraColunas() {
        tblColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblColCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tblColEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        handleBtnPesquisar();
    }

    // configura a lógica da tela
    private void configuraBindings() {
        //bids de campos
        categoria.idProperty().bind(lblId.textProperty());
        categoria.categoriaProperty().bind(tfdCategoria.textProperty());
        // quando algo é selecionado na tabela, preenchemos os campos de entrada com os valores para o 
        tblCategoria.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<Categoria>() {
            @Override
            public void changed(ObservableValue<? extends Categoria> observable, Categoria oldValue, Categoria newValue) {
                if (newValue != null) {
                    tfdCategoria.textProperty().bindBidirectional(newValue.categoriaProperty());
                    lblId.textProperty().bind(Bindings.convert(newValue.idProperty()));
                    tblCategoria.setDisable(true);
                    btnPesquisar.setDisable(true);
                    atualizando = true;
                    cmbEstado.getSelectionModel().select(newValue.getEstado());
                } else {
                    lblId.textProperty().unbind();
                    tfdCategoria.textProperty().unbind();
                    tfdCategoria.setText("");
                    lblId.setText("X");
                }
            }
        });
        cmbEstado.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    categoria.setEstado((String) cmbEstado.getSelectionModel().getSelectedItem());
                }
            }
        });
    }

    private void validar() {
        DecorationPane decorationPane = new DecorationPane(anchor);
        root.getChildren().add(decorationPane);
        Validation.validate(tfdCategoria, Validation.VARCHAR25);
    }

    private void liberarBotoes() {
        btnConfirmar.disableProperty().bind(Validation.validGroup.not());
        //   btnRemover.disableProperty().bind(tblCategoria.getSelectionModel().selectedItemProperty().isNull());
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
