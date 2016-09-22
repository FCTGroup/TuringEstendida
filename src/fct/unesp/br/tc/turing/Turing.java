package fct.unesp.br.tc.turing;

import com.google.common.collect.ArrayListMultimap;
import java.util.ArrayList;
import java.util.List;

public class Turing {
	
        private ArrayListMultimap<Integer, Character> arrayDeFitas;
	private FuncaoPrograma Programa;
	private Integer estadoAtual;
	private Integer[] posicaoFita;
	
	public Turing(FuncaoPrograma programa){
		this.Programa = programa;
		estadoAtual = programa.getEstadoInicial();
                posicaoFita = new Integer[programa.getNumFitas()];
	}
	
	public void reseta(){
		estadoAtual = Programa.getEstadoInicial();
                for(int fitaAtual = 0; fitaAtual < Programa.getNumFitas(); fitaAtual++)
                    posicaoFita[fitaAtual] = 1;
	}
	
	public Integer getEstadoAtual(){
		return estadoAtual;
	}
        
        public FuncaoPrograma getFuncaoPrograma(){
            return Programa;
        }
	
	/*public void carregaPrograma(String url){
		//ler de um xml
		Programa = new FuncaoPrograma();
		//Programa.addTransicao(1, new Transicao('a', 'b', 1, estadoSucessor));
		estadoAtual = Programa.getEstadoInicial();
	}*/
	
	public void executaPrograma() throws InvalidCharacterException, InvalidPositionException, FinalNodeException{
		if(Programa == null)
			throw new NullPointerException();
		
		while(true){
			iteraPrograma();
		}
		
	}
	
	public void iteraPrograma() throws InvalidCharacterException, InvalidPositionException, FinalNodeException{
		if(Programa.isEstadoFinal(estadoAtual))
			throw new FinalNodeException();               
                
                ArrayList<Character> entrada = new ArrayList<Character>();             
                
                for(int fitaAtual = 0; fitaAtual < posicaoFita.length; fitaAtual++){
                    if(posicaoFita[fitaAtual] == arrayDeFitas.get(fitaAtual).size())
                        arrayDeFitas.put(fitaAtual, ' ');
                    else if(posicaoFita[fitaAtual] == 0)
                        arrayDeFitas.get(fitaAtual).add(0, ' ');
                    entrada.add(arrayDeFitas.get(fitaAtual).get(posicaoFita[fitaAtual]));
                }
                
                TransicaoMultipla transicao = Programa.getTransicao(estadoAtual, entrada);
                
                List<Character> fita;
                for(int fitaAtual = 0; fitaAtual < Programa.getNumFitas(); fitaAtual++){
                    fita = arrayDeFitas.get(fitaAtual);
                    fita.set(posicaoFita[fitaAtual], 
                            transicao.getTransicao(fitaAtual).getEscrito());
                    
                    posicaoFita[fitaAtual] += transicao.getTransicao(fitaAtual).getDirecao();
                }
                    
		estadoAtual = transicao.getEstadoSucessor();
	}
	
	/*public void salvaPrograma(String url){
		//escrever em um xml
	}*/
	
	public void carregaFita(String fita, Integer numeroDaFita){
		arrayDeFitas.get(numeroDaFita).clear();
		for(char character:fita.toCharArray())
			arrayDeFitas.put(numeroDaFita, character);
	}
	
	public void imprime(Integer numeroDaFita){
		for(Character character:arrayDeFitas.get(numeroDaFita))
                    System.out.print(character);
	}

}
