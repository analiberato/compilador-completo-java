package semantico;

/**
 * Cria uma lista de Instruções onde vão ser salvas as Instruções do codigo inserido pelo usuario
 *
 */

class Instrucoes
{
	public Tipos[] areaInstrucoes = new Tipos[1000];
	public int LC;

	Instrucoes() {
		for (int i = 0; i < 1000; i++)
			this.areaInstrucoes[i] = new Tipos();
	}
}