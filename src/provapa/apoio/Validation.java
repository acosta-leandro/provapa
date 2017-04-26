package provapa.apoio;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import jidefx.scene.control.decoration.DecorationUtils;
import jidefx.scene.control.decoration.Decorator;
import jidefx.scene.control.field.FormattedTextField;
import jidefx.scene.control.field.verifier.IntegerRangePatternVerifier;

/**
 *
 * Esta classe irá facilitar algumas questões de validação e decoração do JideFX
 *
 * @author Jean Carlo Galliani
 */
public class Validation {

    /**
     * Está propriedade indica se todos os campos estão validados
     */
    public static SimpleBooleanProperty validGroup = new SimpleBooleanProperty(false);

    // Expressões regulares, corrija ou adicione novas de acordo com sua necessidade
    public static String EMAIL = "[a-z]\\w+(|\\.|-|_)\\w+@\\w+\\.[a-z]+(|\\.[a-z]{2,3})";
    public static String VARCHAR25 = ".{1,25}";
    public static String TELEFONE = ".{13}";
    public static String CPF = "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}";
    public static String CNPJ = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}/[0-9]{4}-[0-9]{2}";
    public static String INTEIRO = "[0-9]{1,9}";
    public static String MONEY = "[R][$](([1-9]+\\.?\\d*)|([0]\\.\\d*)|[0])";
    public static String DOUBLE = "[0-9]{1,6}(|((\\.|)([0-9]{1,2})))";
    public static String SIGLA = ".{1,3}";

    /**
     * Configura campo FormattedTextField para se comportar com DDD e telefone.
     * Possui máscara e decoração.
     *
     * @param field
     */
    public static void toTelefoneField(FormattedTextField field) {
        field.getPatternVerifiers().put("hack", new IntegerRangePatternVerifier(-1, -1));
        field.getPatternVerifiers().put("ddd", new IntegerRangePatternVerifier(0, 99));
        field.getPatternVerifiers().put("number", new IntegerRangePatternVerifier(0, 9999));
        // este tal hack tem a função de deixar transparente um problema do JideFX 0.9.1
        // se quiser ver o que acontece, apague o hack do setPattern
        field.setPattern("hack(ddd)number-number");

        validate(field, TELEFONE);
    }

    /**
     *
     * Configura FormattedTextField em campo inteiro. Incluso decoração e
     * máscara
     *
     * @param field
     */
    public static void toIntegerField(FormattedTextField field) {
        field.getPatternVerifiers().put("int", new IntegerRangePatternVerifier(0, 999999999));
        field.setPattern("int");

        validate(field, INTEIRO);
    }

    public static Decorator error() {
        return new Decorator(new ImageView(new Image("/jidefx/scene/control/decoration/overlay_error.png")));
    }

    public static Decorator ok() {
        return new Decorator(new ImageView(new Image("/jidefx/scene/control/decoration/overlay_correct.png")));
    }

    // Verifica se todos os campos validados estão ok... Se sim a propriedade é true
    public static SimpleBooleanProperty validateGroup(Pane pane) {

        for (Node node : pane.getChildren()) {
            if (node instanceof Control) {
                if (node.getUserData() instanceof Boolean) {
                    if (!((boolean) node.getUserData())) {
                        validGroup.setValue(false);
                        return validGroup;
                    }
                }
            }
        }
        validGroup.setValue(true);
        return validGroup;
    }

    /**
     *
     * Adiciona decoração validando se o combobox estiver null
     *
     * @param field
     */
    public static void validate(ComboBox field) {
        field.setUserData(false);
        field.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                addDecoration(newValue != null, field);
            }
        });

        addDecoration(field.getValue() != null, field);
    }

    /**
     *
     * Valida campo de texto adicionando decoração de acordo com a Expressão
     * regular passada por parâmetro
     *
     * @param field
     * @param pattern
     */
    public static void validate(TextField field, String pattern) {
        field.setUserData(false);
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                addDecoration(newValue.matches(pattern), field);
            }
        });

        addDecoration(field.getText().matches(pattern), field);
    }

    public static void validateOr(TextField field, String pattern, String pattern2) {
        field.setUserData(false);
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches(pattern) || newValue.matches(pattern2)) {
                   // System.out.println("teste");
                    addDecoration(true, field);
                }
            }
        });
     //   System.out.println("teste1");
        addDecoration(field.getText().matches(pattern)||field.getText().matches(pattern2), field);
    }

    // Adiciona a decoração ao campo...
    private static void addDecoration(boolean b, Node field) {
        if (b) {
            DecorationUtils.install(field, ok());
            field.setUserData(true);
        } else {
            DecorationUtils.install(field, error());
            field.setUserData(false);
        }
        validateGroup((Pane) field.getParent());
    }

    //validar sempre true
    public static void validateTrue(TextField field) {
        addDecoration(true, field);
    }

}
