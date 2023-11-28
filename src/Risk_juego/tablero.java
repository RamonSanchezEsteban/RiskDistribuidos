package Risk_juego;

import java.util.ArrayList;
import java.util.HashMap;

public class tablero 
{	
	private String nombre;
	private ArrayList<territorio> territorios;
	private boolean[][] fronteras;
	
	public tablero(String nombre_in)
	{
		this.nombre=nombre_in;
		this.territorios = new ArrayList(100);
		this.fronteras = new boolean[100][100];
	}

	public tablero(String nombre_in, ArrayList<territorio> territorios_in)
	{
		this.nombre=nombre_in;
		this.territorios=territorios_in;
		this.fronteras = new boolean[100][100];		
	}

	public tablero(String nombre_in, ArrayList<territorio> territorios_in, boolean[][] fronteras_in)
	{
		this.nombre=nombre_in;
		this.territorios=territorios_in;
		this.fronteras=fronteras_in;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public void anadirTerritorio(territorio t_in)
	{
		if(!this.territorios.contains(t_in))
		{
			this.territorios.add(t_in);
		}		
	}
	
	public void anadirFrontera(territorio t1, territorio t2)
	{
		if(this.territorios.contains(t1) && this.territorios.contains(t2))
		{
			this.fronteras[this.territorios.indexOf(t1)][this.territorios.indexOf(t2)]=true;
			this.fronteras[this.territorios.indexOf(t2)][this.territorios.indexOf(t1)]=true;
		}
	}
	
	public void quitarFrontera(territorio t1, territorio t2)
	{
		if(this.territorios.contains(t1) && this.territorios.contains(t2))
		{
			this.fronteras[this.territorios.indexOf(t1)][this.territorios.indexOf(t2)]=false;
			this.fronteras[this.territorios.indexOf(t2)][this.territorios.indexOf(t1)]=false;
		}
	}
	
	public boolean tieneFrontera(territorio t1, territorio t2)
	{
		if(this.territorios.contains(t1) && this.territorios.contains(t2))
		{
			return this.fronteras[this.territorios.indexOf(t1)][this.territorios.indexOf(t2)];
		}
		else
		{
			return false;
		}		
	}
	
	public boolean haGanado(int ganador)
	{
		int i = 0;
		while(i < this.territorios.size())
		{
			if(this.territorios.get(i).getJugador() != ganador)
			{
				return false;
			}
			i++;
		}
		return true;
	}
	
	
}
