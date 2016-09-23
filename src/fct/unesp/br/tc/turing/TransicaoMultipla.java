/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fct.unesp.br.tc.turing;

import java.util.ArrayList;

/**
 *
 * @author leonardo
 */
public class TransicaoMultipla {
    
    private ArrayList<Transicao> listaTransicoes;
    private Integer estadoSucessor;
    
    public TransicaoMultipla(Integer estadoSucessor){
        listaTransicoes = new ArrayList<Transicao>();
        this.estadoSucessor = estadoSucessor;
    }
    
    public Integer getEstadoSucessor() {
	return estadoSucessor;
    }
    
    public void setEstadoSucessor(Integer estadoSucessor) {
        this.estadoSucessor = estadoSucessor;
    }
    
    public void add(Transicao transicao){
        listaTransicoes.add(transicao);
    }
    
    public Transicao getTransicao(Integer fita){
        return listaTransicoes.get(fita);
    }
    
    public boolean validaEntrada(ArrayList<Character> charLidoNasFitas){
        Character charLido;
        for(int transicao = 0;transicao < listaTransicoes.size(); transicao++){
            charLido = listaTransicoes.get(transicao).getLido();
            if(!charLidoNasFitas.get(transicao).equals(charLido))
                return false;
        }
        return true;
            
    }
    
    @Override
    public String toString(){
        String retorno = "";
        int transicao = 0;
        if(listaTransicoes.size() > 0)
            retorno += listaTransicoes.get(transicao++).toString();
        for(;transicao < listaTransicoes.size(); transicao++)
            retorno += " | "+listaTransicoes.get(transicao).toString();
        return retorno;
    }
    
    
    
    
    
}
