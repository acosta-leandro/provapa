package provapa.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.scene.control.Accordion;
import javafx.scene.control.Menu;
import provapa.ProvaPA;

/**
 * Created by leandro on 05/07/16.
 */
public class principalController implements Initializable {

    private static principalController instance;

    public principalController() {
        instance = this;
    }

    public static principalController getInstance() {
        return instance;
    }

    private boolean cadastrarCategoriaIsAberto = false;
    private boolean cadastrarBloqueioIsAberto = false;

    @FXML
    private Accordion accordion;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MenuItem MenuItemProduto;

    @FXML
    private MenuItem MenuItemCategoria;

    @FXML
    private MenuItem MenuItemGrupo;

    @FXML
    private MenuItem MenuItemSubgrupo;

    @FXML
    private MenuItem MenuItemTipo;

    @FXML
    private MenuItem MenuItemBloqueio;

    @FXML
    private MenuItem MenuItemVenderProduto;

    @FXML
    private MenuItem MenuItemControlarComandas;

    @FXML
    private MenuItem MenuItemFazerPedido;

    @FXML
    private MenuItem MenuItemRelatorios;

    @FXML
    private MenuItem MenuItemSair;

    @FXML
    private Menu menuUsuario;

    @FXML
    public void handleMenuItemCadastrarCategoria() throws IOException {
        if (!cadastrarCategoriaIsAberto) {
            TitledPane cadastrarCategoria = FXMLLoader.load(ProvaPA.class.getResource("view/cadastrarCategoriaProduto.fxml"));
            accordion.getPanes().add(cadastrarCategoria);
            accordion.setExpandedPane(cadastrarCategoria);
            cadastrarCategoriaIsAberto = true;
        }
    }

    @FXML
    public void handleMenuItemCadastrarBloqueio() throws IOException {
        if (!cadastrarBloqueioIsAberto) {
            TitledPane cadastrarBloqueio = FXMLLoader.load(ProvaPA.class.getResource("view/cadastrarBloqueioProduto.fxml"));
            accordion.getPanes().add(cadastrarBloqueio);
            accordion.setExpandedPane(cadastrarBloqueio);
            cadastrarBloqueioIsAberto = true;
        }
    }

    @FXML
    public void handleMenuItemSair() throws IOException {
        System.exit(0);
    }

    public void fecharTittledPane(String tela) {
        if (tela.equals("cadastrarCategoriaProduto")) {
            cadastrarCategoriaIsAberto = false;
        }

        if (tela.equals("cadastrarBloqueioProduto")) {
            cadastrarBloqueioIsAberto = false;
        }
        accordion.getPanes().remove(accordion.getExpandedPane());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
