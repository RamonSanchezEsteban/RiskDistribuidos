package Risk_juego;

public class territorio {
	
	private String nombre;
	private int jugador;
	private int tropas;
	
	public territorio(String nombre_in, int jugador_in, int tropas_in)
	{
		this.nombre = nombre_in;
		this.jugador = jugador_in;
		this.tropas = tropas_in;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public int getJugador()
	{
		return this.jugador;
	}
	
	public int getTropas()
	{
		return this.tropas;
	}
	
	public void setJugador(int jugador_in)
	{
		this.jugador=jugador_in;
	}
	
	public void setTropas(int tropas_in)
	{
		this.tropas=tropas_in;
	}	
	
}
