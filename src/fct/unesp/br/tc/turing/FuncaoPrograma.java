package fct.unesp.br.tc.turing;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;

public class FuncaoPrograma {
	
	private Integer estadoInicial;
	private ArrayList<Integer> estadosFinais;
        private ArrayListMultimap<Integer, TransicaoMultipla> listaTransicoes;
	private ArrayList<Integer> chaveDeEstados;
        private int numeroDeFitas = 0;
        
	public FuncaoPrograma(){
		estadoInicial = -1;
		estadosFinais = new ArrayList<Integer>();
                chaveDeEstados = new ArrayList<Integer>();
                listaTransicoes.create();
                
	}
        
        public void setNumFitas(int numeroDeFitas){
            this.numeroDeFitas = numeroDeFitas;
        }
        
        public int getNumFitas(){
            return numeroDeFitas;
        }
	
	public ArrayList<Integer> getEstadosFinais(){
		return estadosFinais;
	}
	
	public ArrayList<Integer> getEstados(){
		return chaveDeEstados;
	}
	
	public TransicaoMultipla getTransicao(Integer estado, ArrayList<Character> charLidoNasFitas) throws InvalidCharacterException{
		TransicaoMultipla transicaoRetorno = null;
		for(TransicaoMultipla transicao:listaTransicoes.get(estado))
			if(transicao.validaEntrada(charLidoNasFitas)){
				transicaoRetorno = transicao;
			}
		if(transicaoRetorno == null)
			throw new InvalidCharacterException();
                
                return transicaoRetorno;
	}
	
	public void setEstadoInicial(Integer estadoInicial){
		this.estadoInicial = estadoInicial;
	}
	
	public Integer getEstadoInicial(){
		return estadoInicial;
	}
	
	public void addEstadoFinal(Integer estadoFinal){
		this.estadosFinais.add(estadoFinal);
	}
	
	public void removeEstadoFinal(Integer estadoFinal){
		this.estadosFinais.remove(estadoFinal);
	}
	
	public boolean isEstadoFinal(Integer estado){
		return estadosFinais.contains(estado);
	}
	
	public void removeTransicao(Integer estado, TransicaoMultipla transicao){
            listaTransicoes.remove(estado, transicao);
	}
	
	public void addTransicao(Integer estado, TransicaoMultipla transicao){
            listaTransicoes.put(estado, transicao);
	}

}
