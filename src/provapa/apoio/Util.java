/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provapa.apoio;

import java.sql.Array;
import java.util.ArrayList;

/**
 *
 * @author Leandro Acosta <leandro.acosta292@hotmail.com>
 */
public class Util {

    public static Double DinheiroParaDouble(String valor) {
        valor = valor.replace("R$", "");
        valor = valor.replace(",", ".");
        Double valorDouble = Double.valueOf(valor);
        return valorDouble;
    }

    public static ArrayList<Double> DinheiroParaDouble(ArrayList<String> valor) {
        ArrayList<Double> arrayDouble = new ArrayList();
        for (int i = 0; i < valor.size(); i++) {
            arrayDouble.add(DinheiroParaDouble(valor.get(i)));
        }
        return arrayDouble;
    }

    public static Double somarDouble(ArrayList<Double> valores) {
        Double total = 0.0;
        for (Double d : valores) {
            total += d;
        }
        return limitarPrecisaoDouble(String.valueOf(total), 2);
    }

    public static double limitarPrecisaoDouble(String numeroFormatar, int digitosAposDecimal) {
        int multiplicador = (int) Math.pow(10, digitosAposDecimal);
        double truncated = (double) ((long) ((Double.parseDouble(numeroFormatar)) * multiplicador)) / multiplicador;
        // System.out.println(numeroFormatar + " ==> " + truncated);
        return truncated;
    }

    public static String DoubleParaDinheiro(Double valor) {
        String Svalor = "R$" + String.valueOf(limitarPrecisaoDouble(String.valueOf(valor), 2));
       // Svalor = Svalor.replace("", ",");
        return Svalor;
    }

}
