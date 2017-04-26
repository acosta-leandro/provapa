package provapa.dao;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author pretto
 */
public interface IDAO {

    ArrayList<Object> objs = new ArrayList<>();
    Object obj = new Object();

    public int salvar(Object o);

    public boolean atualizar(Object o);

    public boolean excluir(int id);

    public ArrayList<Object> consultarTodos();

    public ArrayList<Object> consultar(String criterio);

    public Object consultarId(int id);
    
    public Object consultarNome(String nome);
    
}