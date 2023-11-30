package Risk_juego;

import java.io.Serializable;
import java.util.ArrayList;

public class continente implements Serializable
{
	private static final long serialVersionUID = -8228340216688593113L;
	private String nombre;
	private ArrayList<territorio> territorios;
	private int valor;
	
	public continente(String nombre_in, int valor_in)
	{
		this.nombre=nombre_in;
		this.territorios=new ArrayList(100);
		this.valor=valor_in;
	}
	
	public continente(String nombre_in, int valor_in, ArrayList<territorio> territorios_in)
	{
		this.nombre=nombre_in;
		this.territorios=territorios_in;
		this.valor=valor_in;
	}
	
	public void anadirTerritorio(territorio t1)
	{
		if(!this.territorios.contains(t1))
		{
			this.territorios.add(t1);
		}		
	}
	
	public void eliminarTerritorio(territorio t1)
	{
		if(this.territorios.contains(t1))
		{
			this.territorios.remove(t1);
		}		
	}
	
	public ArrayList<territorio> territorios()
	{
		return this.territorios;
	}
	
	public String nombre()
	{
		return this.nombre;
	}
	
	public int valor()
	{
		return this.valor();
	}
	
	public void setValor(int v)
	{
		this.valor = v;
	}
	
	public boolean tieneContinete(int jugador)
	{
		int i = 0;
		while(i < this.territorios.size())
		{
			if(this.territorios.get(i).estaVacio())
				{
					i++;
				}
			else
				{
					if(this.territorios.get(i).getJugador() != jugador)
					{
						return false;
					}
					i++;
				}
		}
		return true;
	}
	
	public boolean equals(continente c1)
	{		
		int i=0;
		if(this.territorios.size() != c1.territorios.size())
		{
			return false;
		}
		while(i<this.territorios.size())
		{
			territorio t1 = this.territorios.get(i);
			if(!c1.territorios.contains(t1))
			{
				return false;
			}
			i++;
		}		
		return true;
	}
	
	public String toString()
	{
		String s = this.nombre + "\n" + "Valor: " + this.valor + "\n" + "territorios: " + "\n";
		int i=0;
		while(i<this.territorios.size())
		{
			s = s + this.territorios.get(i);
			i++;
		}		
		return s;
	}
	
}
