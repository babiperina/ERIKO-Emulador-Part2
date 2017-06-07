package utils;

public class Constantes {

	public static final String ARQUIVO_DE_TEXTO = "assembly";

	public static int id_ES = 37;
	public static int id_CPU = 278;
	public static int id_RAM = 726;

	public static int id_SINAL_LER = 2;
	public static int id_SINAL_ESC = 3;
	public static int id_SINAL_OK = 1;
	public static int id_SINAL_ERROR = -1;

	public static int id_DADO_QTDE = 5;
	public static int id_DADO_DADO = 4;
	public static int id_DADO_VAZIO = -1;

	public static int id_END_MEM = 6;
	public static int id_END_VAZIO = -1;

	public static int SIZE_word = 32; // em bits
	public static int SIZE_ram = 16; // em kb
	// public static int SIZE_ram = 32; //em kb
	// public static int SIZE_ram = 64; //em kb
	// public static int SIZE_ram = 128; //em kb
	public static int SIZE_e_s_buffer;
	public static int TAM_MAX_INST;
	public static int QTDE_ESP_INST;
	public static int QTDE_INST_BUFFER = 4;

	private static void setSizeBuffer() {
		QTDE_ESP_INST = TAM_MAX_INST;
		SIZE_e_s_buffer = QTDE_ESP_INST * QTDE_INST_BUFFER;
	}

	public static int WIDTH_barramento = 16; // bits
	public static String limitMemoryDigits;

	public static void setTamanhoMaxInstrucao() {

		switch (WIDTH_barramento) {
		case 8:
			TAM_MAX_INST = 4;
			break;
		case 16:
			TAM_MAX_INST = 8;
			break;
		case 32:
			TAM_MAX_INST = 16;
			break;

		default:
			System.err.println("Tamanho de largura de barramento inválido.");
			System.exit(0);
			break;
		}

	}

	private static void setDigitsLimitMemory() {
		if (WIDTH_barramento / 4 == 1) {
			limitMemoryDigits = "{1}";
		} else {
			limitMemoryDigits = "{1," + (WIDTH_barramento / 4) + "}";
		}
	}

	// Register value
	public static int VALUE_register_A = 300;
	public static int VALUE_register_B = 400;
	public static int VALUE_register_C = 500;
	public static int VALUE_register_D = 600;
	public static int VALUE_register_CI = -5;

	// Instructions
	public static String mov = "mov";
	public static String add = "add";
	public static String inc = "inc";
	public static String imul = "imul";

	// Instructions value 3 a 6
	// r - register ; m - memory ; i - immediate
	public static int VALUE_mov_r_from_r = 311;
	public static int VALUE_mov_r_from_i = 313;
	public static int VALUE_mov_m_from_i = 323;
	public static int VALUE_mov_r_from_m = 312;
	public static int VALUE_mov_m_from_r = 321;
	public static int VALUE_mov_m_from_m = 322;
	public static int VALUE_add_r_from_r = 411;
	public static int VALUE_add_r_from_i = 413;
	public static int VALUE_add_m_from_i = 423;
	public static int VALUE_add_r_from_m = 412;
	public static int VALUE_add_m_from_r = 421;
	public static int VALUE_add_m_from_m = 422;
	public static int VALUE_inc_r = 501;
	public static int VALUE_inc_m = 502;
	public static int VALUE_imul_r_r_r = 6111;
	public static int VALUE_imul_r_r_m = 6112;
	public static int VALUE_imul_r_r_i = 6113;
	public static int VALUE_imul_r_m_r = 6121;
	public static int VALUE_imul_r_m_m = 6122;
	public static int VALUE_imul_r_m_i = 6123;
	public static int VALUE_imul_r_i_r = 6131;
	public static int VALUE_imul_r_i_m = 6132;
	public static int VALUE_imul_r_i_i = 6133;
	public static int VALUE_imul_m_r_r = 6211;
	public static int VALUE_imul_m_r_m = 6212;
	public static int VALUE_imul_m_r_i = 6213;
	public static int VALUE_imul_m_m_r = 6221;
	public static int VALUE_imul_m_m_m = 6222;
	public static int VALUE_imul_m_m_i = 6223;
	public static int VALUE_imul_m_i_r = 6231;
	public static int VALUE_imul_m_i_m = 6232;
	public static int VALUE_imul_m_i_i = 6233;

	// Instructions REgex
	public static String RE_add_mov;
	public static String RE_inc;
	public static String RE_imul;

	// Componentes REgex
	public static String RE_register = "[A-D]";
	public static String RE_memory = "0x[a-fA-F0-9]" + "";
	public static String RE_immediate = "\\d+";

	static {
		setDigitsLimitMemory();
		setTamanhoMaxInstrucao();
		setSizeBuffer();
		RE_add_mov = "^(add|mov)\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + ")\\s*,"
				+ "\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + "|\\d+)\\s*$";
		RE_inc = "^(inc)\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + ")\\s*$";
		RE_imul = "^(imul)\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + ")\\s*," + "\\s+([a-dA-D]|0x[a-fA-F0-9]"
				+ limitMemoryDigits + "|\\d+)\\s*," + "\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + "|\\d+)\\s*$";

	}

}
