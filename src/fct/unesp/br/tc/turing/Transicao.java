package fct.unesp.br.tc.turing;

public class Transicao {
	
    
        public static final int L = -1;
        public static final int S = 0;
        public static final int R = 1;
        
	private Character lido;
	private Character escrito;
	private Integer direcao;
	private boolean diferenteDe;
	
	public Transicao(Character lido, Character escrito, Integer direcao){
		this.lido = lido;
		this.escrito = escrito;
		this.direcao = direcao;
		this.diferenteDe = false;
	}
	
	public Transicao(Character lido, Character escrito, Integer direcao, boolean diferenteDe){
		this.lido = lido;
		this.escrito = escrito;
		this.direcao = direcao;
		this.diferenteDe = diferenteDe;
	}
	
	public Character getLido() {
		return lido;
	}
	public void setLido(Character lido) {
		this.lido = lido;
	}
	public Character getEscrito() {
		return escrito;
	}
	public void setEscrito(Character escrito) {
		this.escrito = escrito;
	}
	public Integer getDirecao() {
		return direcao;
	}
	public void setDirecao(Integer direcao) {
		this.direcao = direcao;
	}
	
	@Override
	public String toString(){
		String retorno = "";
		retorno = retorno.concat(diferenteDe?retorno = "!":"");
		retorno = retorno.concat(lido == ' '?"□":String.valueOf(lido));
		retorno = retorno.concat(";");
		retorno = retorno.concat(escrito == ' '?"□":String.valueOf(escrito));
		retorno = retorno.concat(";");
		retorno = retorno.concat((direcao == L)?"L":((direcao == S)?"S":"R"));
		return retorno;
	}
	
}
