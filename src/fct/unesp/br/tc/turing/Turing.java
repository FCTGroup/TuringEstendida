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
                arrayDeFitas = ArrayListMultimap.create();
	}
        
        public ArrayListMultimap<Integer, Character> getArrayDeFitas(){
            return arrayDeFitas;
        }
	
	public void reseta(){
		estadoAtual = Programa.getEstadoInicial();
                for(int fitaAtual = 0; fitaAtual < Programa.getNumFitas(); fitaAtual++)
                    posicaoFita[fitaAtual] = 0;
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
	
	public int[] iteraPrograma() throws InvalidCharacterException, InvalidPositionException, FinalNodeException{
		if(Programa.isEstadoFinal(estadoAtual))
			throw new FinalNodeException();               
                
                ArrayList<Character> entrada = new ArrayList<Character>();             
                
                int retorno[] = new int[Programa.getNumFitas()];
                
                for(int fitaAtual = 0; fitaAtual < posicaoFita.length; fitaAtual++){
                    if(posicaoFita[fitaAtual] == arrayDeFitas.get(fitaAtual).size())
                        arrayDeFitas.put(fitaAtual, ' ');
                    try{
                        entrada.add(arrayDeFitas.get(fitaAtual).get(posicaoFita[fitaAtual]));
                    }catch(IndexOutOfBoundsException e){
                        entrada.add(' ');
                    }
                }
                
                TransicaoMultipla transicao = Programa.getTransicao(estadoAtual, entrada);
                
                List<Character> fita;
                for(int fitaAtual = 0; fitaAtual < Programa.getNumFitas(); fitaAtual++){
                    fita = arrayDeFitas.get(fitaAtual);
                    fita.set(posicaoFita[fitaAtual], 
                            transicao.getTransicao(fitaAtual).getEscrito());
                    
                    posicaoFita[fitaAtual] += transicao.getTransicao(fitaAtual).getDirecao();
                    
                    retorno[fitaAtual] = transicao.getTransicao(fitaAtual).getDirecao();
                }
                    
		estadoAtual = transicao.getEstadoSucessor();
                
                return retorno;
	}
	
	/*public void salvaPrograma(String url){
		//escrever em um xml
	}*/
	
	public void carregaFita(String fita){
            try{
                arrayDeFitas.get(0).clear();
            }catch(NullPointerException e){
                
            }finally{
                for(char character:fita.toCharArray())
                    arrayDeFitas.put(0, character);   
            }
	}
	
	public void imprime(Integer numeroDaFita){
		for(Character character:arrayDeFitas.get(numeroDaFita))
                    System.out.print(character);
	}

}
