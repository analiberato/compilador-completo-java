package lexico;

//cada token contem um código, o token e o tipo de Token, exemplo palavra reservada, operador
public class Token {
	private int codigo;
	private String tipo;
	private String token;

	public Token(int codigo, String token, String tipo)
	{
		this.codigo = codigo;
		this.token = token;
		this.tipo = tipo;
	}

	public int getCodigo()
	{
		return this.codigo;
	}

	public String getTipo()
	{
		return this.tipo;
	}

	public String getToken()
	{
		return this.token;
	}

	public void setToken(String t)
	{
		this.token = t;
	}
}