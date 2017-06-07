package componentes;

import principal.Computador;
import utils.Constantes;
import utils.Dado;
import utils.Endereco;
import utils.Sinal;

public class CPU extends Thread {
	

	private Registrador A = new Registrador(Constantes.VALUE_register_A, "A", null);
	private Registrador B = new Registrador(Constantes.VALUE_register_B, "B", null);
	private Registrador C = new Registrador(Constantes.VALUE_register_C, "C", null);
	private Registrador D = new Registrador(Constantes.VALUE_register_D, "D", null);
	private Registrador CI = new Registrador(Constantes.VALUE_register_CI, "CI", -1);
	private Cache cache;
	private boolean rodando = true;
	// Registradores
	// Cache
	
	
	public CPU() {
	}
	
	@Override
	public void run() {
		while(rodando){
			try {
				Computador.tela.escreverNoConsole("CPU Rodando");
				Computador.tela.toNaCpu(true);
				sleep(1000);
				buscar();
				Computador.tela.toNaCpu(false);
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.run();
	}
	
	public void buscar(){
		if(CI.getConteudo()!=-1){
		 Sinal sinal = new Sinal(Constantes.id_CPU, Constantes.id_RAM, Constantes.id_SINAL_LER);
		 Dado dado = new Dado(Constantes.id_DADO_QTDE, Constantes.QTDE_ESP_INST);
		 Endereco endereco = new Endereco(Constantes.id_END_MEM, CI.getConteudo());
		 Computador.barramento.Enfileirar(sinal, dado, endereco);
		}
	}
	
	public void decodificar(){
		
	}
	
	public void realizar(){
		
	}
	
	public Registrador getA() {
		return A;
	}

	public void setA(Registrador a) {
		A = a;
	}

	public Registrador getB() {
		return B;
	}

	public void setB(Registrador b) {
		B = b;
	}

	public Registrador getC() {
		return C;
	}

	public void setC(Registrador c) {
		C = c;
	}

	public Registrador getD() {
		return D;
	}

	public void setD(Registrador d) {
		D = d;
	}

	public int getCI() {
		return CI.getConteudo();
	}

	public void setCI(int cI) {
		CI.setConteudo(cI);
	}
	
	public void parar() {
		rodando = false;
		
	}

	public void retornar() {
		rodando = true;
	}
}
