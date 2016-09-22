package fct.unesp.br.tc.turing;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;

public class FuncaoPrograma {
	
	private Integer estadoInicial;
	private ArrayList<Integer> estadosFinais;
        private ArrayList<ArrayListMultimap<Integer, Transicao>> arrayDeFitas;
	private ArrayList<Integer> chaveDeEstados;
        private int numeroDeFitas = 0;
        
	public FuncaoPrograma(){
		estadoInicial = -1;
		estadosFinais = new ArrayList<Integer>();
                arrayDeFitas = new ArrayList<ArrayListMultimap<Integer, Transicao>>();
                chaveDeEstados = new ArrayList<Integer>();
	}
        
        public void setNumFitas(int numeroDeFitas){
            this.numeroDeFitas = numeroDeFitas;
            for(int i = 0; i < numeroDeFitas; i++)
                arrayDeFitas.get(i).create();
        }
        
        public ArrayListMultimap<Integer, Transicao> getTransicoesDaFita(int numeroDaFita){
            return arrayDeFitas.get(numeroDaFita);
        }
	
	public ArrayList<Integer> getEstadosFinais(){
		return estadosFinais;
	}
	
	public ArrayList<Integer> getEstados(){
		return chaveDeEstados;
	}
        
        public Transicao[] getArrayTransicao(Integer estado, Character character) throws InvalidCharacterException{
            Transicao[] listaTransicao = new Transicao[numeroDeFitas];
            for(int fitaAtual = 0; fitaAtual < numeroDeFitas; fitaAtual++)
                listaTransicao[fitaAtual] = getTransicao(estado, character, fitaAtual);
            return listaTransicao;
        }
	
	public Transicao getTransicao(Integer estado, Character character, Integer fita) throws InvalidCharacterException{
		List<Transicao> transicoes = arrayDeFitas.get(fita).get(estado);
		Transicao transicaoRetorno = null;
		for(Transicao transicao:transicoes)
			if(transicao.getLido() == character){
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
	
	public void removeTransicao(Integer estado, Transicao[] transicao){
            for(int fitaAtual = 0; fitaAtual < numeroDeFitas; fitaAtual++)
                arrayDeFitas.get(fitaAtual).remove(estado, transicao[fitaAtual]);
	}
	
	public void addTransicao(Integer estado, Transicao[] transicao){
            for(int fitaAtual = 0; fitaAtual < numeroDeFitas; fitaAtual++)
                arrayDeFitas.get(fitaAtual).put(estado, transicao[fitaAtual]);
	}

}
