package Risk_juego;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class tablero implements Serializable
{	
	
	private static final long serialVersionUID = 7245126196912939415L;
	private String nombre;
	private ArrayList<territorio> territorios;
	private boolean[][] fronteras;
	private ArrayList<continente> continentes;
	
	public tablero(String nombre_in)
	{
		this.nombre=nombre_in;
		this.territorios = new ArrayList(100);
		this.fronteras = new boolean[100][100];
		int i=0;
		int j=0;
		while(i<100)
		{
			j=0;
			while(j<100)
			{
				this.fronteras[i][j]=false;	
				j++;
			}
			i++;			
		}
		this.continentes = new ArrayList(30);
	}

	public tablero(String nombre_in, ArrayList<territorio> territorios_in)
	{
		this.nombre=nombre_in;
		this.territorios=territorios_in;
		this.fronteras = new boolean[100][100];
		int i=0;
		int j=0;
		while(i<100)
		{
			j=0;
			while(j<100)
			{
				this.fronteras[i][j]=false;	
				j++;
			}
			i++;			
		}
		this.continentes = new ArrayList(30);
	}

	public tablero(String nombre_in, ArrayList<territorio> territorios_in, boolean[][] fronteras_in, ArrayList<continente> continentes_in)
	{
		this.nombre=nombre_in;
		this.territorios=territorios_in;
		this.fronteras=fronteras_in;
		this.continentes=continentes_in;
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
	
	public void eliminarTerritorio(territorio t_in)
	{
		if(this.territorios.contains(t_in))
		{
			int i = this.territorios.indexOf(t_in);
			this.territorios.get(i).setTerritorioVacio();
		}
	}
	
	public void anadirContinente(continente continente_in)
	{
		this.continentes.add(continente_in);
	}
	
	public void eliminarContinente(continente continente_in)
	{
		this.continentes.remove(continente_in);
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
			if(this.territorios.get(i).estaVacio())
				{
					i++;
				}
			else
				{
					if(this.territorios.get(i).getJugador() != ganador)
					{
						return false;
					}
					i++;
				}
		}
		return true;
	}
	
	public boolean haPerdido(int jugador)
	{
		if(this.numeroTerritoriosPoseidos(jugador) == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean batallaGlobal(territorio ta, territorio td, int tropasAtacante)
	{
		if (!this.tieneFrontera(ta, td))
		{
			return false;
		}
		if (ta.getTropas() <= tropasAtacante)
		{
			return false;
		}
		if (ta.getJugador() == td.getJugador())
		{
			return false;
		}
		
		int tropasMinimas = ta.getTropas()-tropasAtacante;
		int tropasRestantes;
		
		while(tropasMinimas < ta.getTropas()  )
		{
			tropasRestantes = this.batallaLocal(ta, td, min(3, ta.getTropas()-tropasMinimas));
			
			//System.out.println(ta.toString());			
			//System.out.println(td.toString());
			//System.out.print("\n\n");
			
			if(tropasMinimas == ta.getTropas())		
			{
				System.out.println("Ataque Fallido");				
				return true;
			}
			if(td.getTropas() == 0)
			{
				System.out.println("Territorio conquistado");
				ta.setTropas(ta.getTropas() - tropasRestantes);
				td.setJugador(ta.getJugador());
				td.setTropas(tropasRestantes);
				return true;
			}
		}
		return false;
	}
	
	public int batallaLocal(territorio ta, territorio td, int tropasAtacante)
	{		
		int tropasDefensor = min(2, td.getTropas());
		int tropasRestantes = 0;
		Random r = new Random();
		
		int ra1=r.nextInt(6)+1;
		int ra2=r.nextInt(6)+1;
		int ra3=r.nextInt(6)+1;
		int rd1=r.nextInt(6)+1;
		int rd2=r.nextInt(6)+1;
				
		
		int da1, da2, dd1, dd2; 
		int bajasA = 0;
		int bajasD = 0;
		
		if(tropasAtacante == 3)
		{
			tropasRestantes++;
			if(tropasDefensor == 2)
			{
				da1 = max(ra1,ra2,ra3);		
				da2 = mid(ra1,ra2,ra3);				
				dd1 = max(rd1,rd2);
				dd2 = min(rd1,rd2);
				
				if(da1 > dd1)
				{
					bajasD++;
					tropasRestantes++;
				}
				else
				{
					bajasA++;
				}
				
				if(da2 > dd2)
				{
					bajasD++;
					tropasRestantes++;
				}
				else
				{
					bajasA++;
				}
			}
			else
			{
				tropasRestantes++;
				da1 = max(ra1,ra2,ra3);
				dd1 = rd1;
				
				if(da1 > dd1)
				{
					bajasD++;
					tropasRestantes++;
					
				}
				else
				{
					bajasA++;
				}
			}												
		}
		if(tropasAtacante == 2)
		{
			if(tropasDefensor == 2)
			{
				da1 = max(ra1,ra2);		
				da2 = min(ra1,ra2);				
				dd1 = max(rd1,rd2);
				dd2 = min(rd1,rd2);
				
				if(da1 > dd1)
				{
					bajasD++;
					tropasRestantes++;
				}
				else
				{
					bajasA++;
				}
				
				if(da2 > dd2)
				{
					bajasD++;
					tropasRestantes++;
				}
				else
				{
					bajasA++;
				}
			}
			else
			{
				tropasRestantes++;
				da1 = max(ra1,ra2);
				dd1 = rd1;
				
				if(da1 > dd1)
				{
					bajasD++;
					tropasRestantes++;
				}
				else
				{
					bajasA++;
				}
			}
		}	
		if(tropasAtacante == 1)
		{
			if(tropasDefensor == 2)
			{
				da1 = ra1;						
				dd1 = max(rd1,rd2);
				dd2 = min(rd1,rd2);	
				
				if(da1 > dd1)
				{
					bajasD++;
					tropasRestantes++;
				}
				else
				{
					bajasA++;
				}
			}
			else
			{
				da1 = ra1;
				dd1 = rd1;
					
				if(da1 > dd1)
				{
					bajasD++;
					tropasRestantes++;
				}
				else
				{
					bajasA++;
				}
			}												
		}
		
		if(bajasA == 0 && bajasD == 0)
		{
			return -1;
		}
		
		ta.setTropas(ta.getTropas()-bajasA);
		td.setTropas(td.getTropas()-bajasD);														
		return tropasRestantes;
	}
	
	public boolean tieneContinete(int jugador, continente c_in)
	{
		return c_in.tieneContinete(jugador);
	}
	
	public int numeroTerritoriosPoseidos(int jugador)
	{
		int i = 0;
		int numTerritorios = 0;
		while(i < this.territorios.size())
		{
			if(this.territorios.get(i).getJugador() == jugador)
			{
				numTerritorios++;
			}
			i++;
		}
		return numTerritorios;
	}
		
	public int calculoNuevasTropas(int jugador)
	{		
		int i = 0;
		int tropasNuevas = 0;		
		
		while(i < this.continentes.size())
		{
			if(this.continentes.get(i).tieneContinete(jugador))
			{
				tropasNuevas = tropasNuevas + this.continentes.get(i).valor();
			}
			i++;
		}
		
		tropasNuevas = tropasNuevas + (this.numeroTerritoriosPoseidos(jugador)/3);
				
		return max(tropasNuevas,3);
	}
	
	public boolean existeCaminoJugador(territorio t1, territorio t2)
	{		
		ArrayList<territorio> L = new ArrayList(100);
		this.calcularTerritoriosConectados(t1, 10 , L);		
		return L.contains(t2);
	}
	
	public void calcularTerritoriosConectados(territorio t, int i, ArrayList<territorio> L)
	{
		if(i >= 0)
		{			
			L.add(t);
			int j = 0;
			while(j < this.territorios.size())
			{
				if		(
						this.tieneFrontera(t, this.territorios.get(j)) && 
						t.getJugador() == this.territorios.get(j).getJugador() && 
						!L.contains(this.territorios.get(j))
						)
				{
					this.calcularTerritoriosConectados(this.territorios.get(j), i-1 , L);
				}
				j++;
			}
		}
	}
	
	public boolean moverTropas(territorio t1, territorio t2, int numTropas)
	{
		if(numTropas >= t1.getTropas())
		{
			return false;
		}
		if(!this.existeCaminoJugador(t1, t2))
		{
			return false;
		}
		else
		{
			t1.setTropas(t1.getTropas() - numTropas);
			t2.setTropas(t2.getTropas() + numTropas);
			return true;
		}
	}

	public ArrayList<territorio> territorios()
	{
		return this.territorios;
	}
	
	public ArrayList<continente> continentes()
	{
		return this.continentes;
	}
	
	public ArrayList<territorio> territoriosJugador(int jugador)
	{
		ArrayList<territorio> L = new ArrayList(100);		
		int i = 0;
		while(i < this.territorios.size())
		{
			if(this.territorios.get(i).getJugador() == jugador)
			{
				L.add(this.territorios.get(i));
			}
			i++;
		}		
		return L;
	}
	
	public ArrayList<territorio> territoriosFronterizosEnemigos(territorio territorioAtacante)
	{
		ArrayList<territorio> L = new ArrayList(100);		
		int i = 0;
		while(i < this.territorios.size())
		{
			if(
					this.territorios.get(i).getJugador() != territorioAtacante.getJugador() &&
					this.tieneFrontera(territorioAtacante, this.territorios().get(i))
			)
			{
				L.add(this.territorios.get(i));
			}
			i++;
		}		
		return L;
	}	

	public boolean[][] fronteras()
	{
		return this.fronteras;
	}
	
	public boolean ponerTropas(int tropas, territorio t)
	{
		if (this.territorios.contains(t))
		{
			t.setTropas(t.getTropas() + tropas);
			return true;
		}
		return false;
	}
	
	public territorio contruirTerritorioExistente(String nombre_territorio)
	{
		int i=0;
		while(i<this.territorios.size())
		{
			if (nombre_territorio.equals(this.territorios.get(i).getNombre()))
			{
				return this.territorios.get(i);
			}
			i++;
		}
		return null;
	}

	public continente contruirContinenteExistente(String nombre_continente)
	{
		int i=0;
		while(i<this.continentes.size())
		{
			if (nombre_continente.equals(this.continentes.get(i).nombre()))
			{
				return this.continentes.get(i);
			}
			i++;
		}
		return null;
	}

	public String toString()
	{
		String s = "Nombre Tablero: " + this.nombre + "\n" + "territorios: " + "\n";
		int i=0;
		while(i<this.territorios.size())
		{
			s = s + this.territorios.get(i);
			i++;
		}		
		i=0;
		s = s + "continentes: " + "\n"; 
		while(i<this.continentes.size())
		{
			s = s + this.continentes.get(i);
			i++;
		}		
		return s;
	}
	
	public int min(int a, int b)
	{
		if(a<= b)
		{
			return a;
		}
		else
		{
			return b;
		}
	}
	
	public int max(int a, int b)
	{
		if(a>=b)
		{
			return a;
		}
		else
		{
			return b;
		}	
	}
	
	public int max(int a, int b, int c)
	{
		return max(max(a,b),c);
	}
	
	public int mid(int a, int b, int c)
	{
		if(a == b || a == c)
		{
			return a;
		}
		if(b == c)
		{
			return b;
		}
		
		int max = max(a,b,c);
		int min = min(min(a,b),c);
		if(a != max && a != min)
		{
			return a;
		}
		if(b != max && a != min)
		{
			return b;
		}
		else
		{
			return c;
		}

	}
	
	
}
