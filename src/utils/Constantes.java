package utils;

public class Constantes {

	public static final String ARQUIVO_DE_TEXTO = "assembly";

	public static int id_ES = 37;
	public static int id_CPU = 278;
	public static int id_RAM = 726;

	public static int id_SINAL_LER = 537;
	public static int id_SINAL_ESC = 372;

	public static int SIZE_word = 16; // em bits
	public static int SIZE_ram = 8;
	public static int SIZE_e_s_buffer;
	public static double tamanho_MAX_INSTRUCAO = 1;

	private static void setSizeBuffer() {
		if (SIZE_ram == 8)
			SIZE_e_s_buffer = 4;
		else if (SIZE_ram == 16)
			SIZE_e_s_buffer = 8;
		else if (SIZE_ram == 32)
			SIZE_e_s_buffer = 16;
	}

	public static int WIDTH_barramento = 8; // bits
	public static String limitMemoryDigits;

	public static void setTamanhoMaxInstrucao() {
		tamanho_MAX_INSTRUCAO += WIDTH_barramento / SIZE_word * 3;
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
		setSizeBuffer();
		setTamanhoMaxInstrucao();
		RE_add_mov = "^(add|mov)\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + ")\\s*,"
				+ "\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + "|\\d+)\\s*$";
		RE_inc = "^(inc)\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + ")\\s*$";
		RE_imul = "^(imul)\\s+([a-dA-D]|0x[a-fA-F0-9] " + limitMemoryDigits + ")\\s*," + "\\s+([a-dA-D]|0x[a-fA-F0-9]"
				+ limitMemoryDigits + "|\\d)\\s*," + "\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + "|\\d+)\\s*$";
		 System.out.println(RE_add_mov + "\n" + RE_inc + "\n" + RE_imul);
		System.out.println(
				"LB: " + WIDTH_barramento + " TP: " + SIZE_word + " Tamanho Max Instrução: " + tamanho_MAX_INSTRUCAO);
		switch (SIZE_word) {
		case 16:
			System.out.println("$$$$ Palavra deverá ser SHORT.");
			break;
		case 32:
			System.out.println("$$$$ Palavra deverá ser INT.");
			break;
		case 64:
			System.out.println("$$$$ Palavra deverá ser LONG.");
			break;

		default:
			System.err.println("Erro no tamanho da palavra.");
			System.exit(0);
			break;
		}
	}

}
