package Risk_juego;

import java.io.Serializable;

public class territorio implements Serializable

{
	private static final long serialVersionUID = -1300590308104224863L;
	private String nombre;
	private int jugador;
	private int tropas;
	
	public territorio(String nombre_in)
	{
		this.nombre=nombre_in;
		this.jugador=-1;
		this.tropas=0;
	}
	
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
	
	public boolean equals(territorio t1)
	{
		return this.nombre==t1.nombre;
	}
	
	public void setTerritorioVacio()
	{
		this.jugador = -1;
		this.tropas  = 0;
	}
	
	public boolean estaVacio()
	{
		return (this.jugador == -1 && this.tropas == 0);
	}
	
	public void sumarTropas(int tropas)
	{
		this.tropas = this.tropas +tropas;
	}
	
	public String toString()
	{
		return (this.nombre + "\n" + "Propietario: " + this.jugador + "\n" + "Tropas: " + this.tropas + "\n");
	}
	
}
