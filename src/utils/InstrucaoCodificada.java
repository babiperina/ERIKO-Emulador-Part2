package utils;

import java.util.Arrays;

public class InstrucaoCodificada {

	private Object instrucaoCodificada[];
	private boolean isES;

	public InstrucaoCodificada() {
		// TODO Auto-generated constructor stub
	}

	public InstrucaoCodificada(Object[] instrucaoCodificada) {
		this.instrucaoCodificada = instrucaoCodificada;
		isES = false;
	}

	public Object[] getInstrucaoCodificada() {
		return instrucaoCodificada;
	}

	public void setInstrucaoCodificada(Object[] instrucaoCodificada) {
		this.instrucaoCodificada = instrucaoCodificada;
	}

	public boolean isES() {
		return isES;
	}

	public void setES(boolean isES) {
		this.isES = isES;
	}

	@Override
	public String toString() {
		return "InstrucaoCodificada [instrucaoCodificada=" + Arrays.toString(instrucaoCodificada) + ", isES=" + isES
				+ "]";
	}

}
