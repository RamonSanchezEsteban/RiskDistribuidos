package Risk_juego;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

	public static void main(String[] args) 
	{
		
				
		tablero tablero1 = new tablero("");
		
		try( 
		FileInputStream f = new FileInputStream("archivos/JuegoBasicoDefinitivo7.risk");
		ObjectInputStream ois= new ObjectInputStream(f);
		)
		{
			tablero1 = (tablero) ois.readObject();
			System.out.println(tablero1.toString());
		} catch(IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
        Scanner sc = new Scanner(System.in);
        String nombreTerritorio1;
        String nombreTerritorio2;
        String aux;
		int jugador = 1;
		int tropasRecibidas;
		int tropasColocadas;
		int tropasAtacantes;
		int tropasMovidas;
		boolean seguirAtacando = true;
		territorio t1,t2;
		
		System.out.println("Inicio de partida");
		aux = sc.nextLine();
		
		while(true)
		{
			System.out.println("turno del jugador " + jugador);
			if(tablero1.haPerdido(jugador))
			{
				System.out.println("Este jugador ha perdido la partida");			
			}
			else
			{
				
				//Colocacion de tropas
				System.out.print("\n\n");
				
				System.out.println("Fase de colocacion");
				
				System.out.print("\n\n");
				
				tropasRecibidas = tablero1.calculoNuevasTropas(jugador);
				System.out.println(tablero1.territoriosJugador(jugador).toString());
				System.out.println("Recibes las siguintes tropas: " + tropasRecibidas);				
				while(tropasRecibidas != 0)
				{
					System.out.println("Escribe un territorio tuyo para poner tropas:");
					nombreTerritorio1 = sc.nextLine();
					System.out.println("Escribe un numero de tropas a colocar:");
					aux = sc.nextLine();
					tropasColocadas = Integer.parseInt(aux);
					
					t1 = tablero1.contruirTerritorioExistente(nombreTerritorio1);
					
					if(tropasColocadas > tropasRecibidas || tropasColocadas <= 0)
					{
						System.out.println("no tienes tantas tropas o no puedes poner un numero de tropas negativas");
					}
					else
					{
						if(t1.getJugador() == jugador)
						{
							tropasRecibidas = tropasRecibidas-tropasColocadas;
							t1.sumarTropas(tropasColocadas);
						}
						else
						{
							System.out.println("Ese territorio no es tuyo");
						}						
					}
					System.out.println("Tropas Restantes a colocar: " + tropasRecibidas);
				}
				
				System.out.print("\n\n");
				System.out.print("Fase de Colocacion terminada");
				System.out.print("\n\n");
				aux = sc.nextLine();
				
				//Ataque de territorios
				
				System.out.print("\n\n");
				System.out.println("Fase de ataque");
				System.out.print("\n\n");
				aux = sc.nextLine();
				
				while(seguirAtacando)
				{
					System.out.println("ataca un territorio fronterizo a uno de los tuyos:");
					System.out.println(tablero1.territoriosJugador(jugador).toString());
					System.out.println("Escribe el nombre de territorio que ataca:");
					nombreTerritorio1 = sc.nextLine();
					
					t1 = tablero1.contruirTerritorioExistente(nombreTerritorio1);
					
					System.out.println("Posibles territorios a atacar:");					
					System.out.print("\n\n");
					System.out.println(tablero1.territoriosFronterizosEnemigos(t1).toString());
					System.out.print("\n\n");
					
					System.out.println("Escribe el nombre del territorio enemigo que atacas:");
					nombreTerritorio2 = sc.nextLine();
					System.out.println("Escribe el número de tropas con las que atacas:");
					aux = sc.nextLine();
					tropasAtacantes = Integer.parseInt(aux);
										
					t2 = tablero1.contruirTerritorioExistente(nombreTerritorio2);
					
					if	(	
							t1.getJugador() != jugador 		||
							t2.getJugador() == jugador 		||
							!tablero1.tieneFrontera(t1, t2)	||
							tropasAtacantes <= 1
						)
					{
						System.out.println("Datos de ataque incorrectos");
					}
					else
					{
						if(tropasAtacantes >=  t1.getTropas())
						{
							System.out.println("No puedes atacar con tantas tropas");
						}
						else
						{
							tablero1.batallaGlobal(	t1,t2,tropasAtacantes);
							System.out.println(t1.toString());
							System.out.println(t2.toString());							
						}
					}
					
					System.out.println("Quieres seguir atacando? (y/n)");
					aux = sc.nextLine();
					if(!aux.equals("y"))
					{
						seguirAtacando = false;
					}
					
					if(tablero1.haGanado(jugador))
					{
						System.out.println("Has ganado");
						aux = sc.nextLine();
						
						sc.close();
						System.exit(0);
					}
				}
				
				//Fase de reforzar
				
				System.out.println("Fase de reforzar");
				System.out.println("Escribe el territorio del que salen las tropas");
				nombreTerritorio1 = sc.nextLine();
				System.out.println("Escribe el territorio al que van las tropas");
				nombreTerritorio2 = sc.nextLine();
				System.out.println("Escribe el numero de tropas que vas a mover");
				aux = sc.nextLine();
				tropasMovidas = Integer.parseInt(aux);
				
				t1 = tablero1.contruirTerritorioExistente(nombreTerritorio1);
				t2 = tablero1.contruirTerritorioExistente(nombreTerritorio2);
				
				if(tropasMovidas >= t1.getTropas() || tropasMovidas <= 0 )
				{
					System.out.println("numero de tropas incorrecto");
				}
				else
				{
					if(	t1.getJugador() != jugador || ! tablero1.existeCaminoJugador(t1,t2)	)
					{
						System.out.println("territorios incorrectos");
					}
					else
					{
						tablero1.moverTropas(t1, t2, tropasMovidas);
						System.out.println("tropasMovidas");
					}
				}		
			}
			jugador = (jugador % 4) + 1;
		}
				
		
/*
		
		System.out.println("Escribe nombre de tablero: ");
        Scanner sc = new Scanner(System.in);
        String nombreTablero = sc.nextLine();
        String res;
        String aux;
        
        tablero tablero1 = new tablero(nombreTablero);
        
        //Continentes                
        
        System.out.println("Vamos a crear los continentes: ");
        
        ArrayList<continente> listaContinentes = new ArrayList(15);
        
        boolean masContinentes = true;
        String nombreContinente;
        int valorContinente;     
        while(masContinentes)
        {
        	System.out.println("Escribe nombre de continente: ");
        	nombreContinente = sc.nextLine();
        	System.out.println("Escribe valor de continente: ");
        	aux = sc.nextLine();
        	valorContinente = Integer.parseInt(aux);
        	tablero1.anadirContinente(new continente(nombreContinente, valorContinente));
        	System.out.println(valorContinente);
        	System.out.println("Otro Continente? (y) ");
        	res = sc.nextLine();
        	System.out.println(res);
        	if(!res.equals("y"))
        	{
        		masContinentes = false;
        	}        	       	
        }
        
        System.out.println(tablero1.toString());
               
        
        //Territorios
        
        System.out.println("Vamos a crear los territorios: ");
        
        ArrayList<territorio> listaTerritorios = new ArrayList(80);
        boolean masTerritorios = true;
        String nombreTerritorio;
        int jugadorTerritorio;
        int tropasTerritorio;
        while(masTerritorios)
        {
        	System.out.println("Escribe nombre de territorio: ");
        	nombreTerritorio = sc.nextLine();
        	
        	System.out.println("Escribe numero de jugador: ");     	
        	aux = sc.nextLine();
        	jugadorTerritorio = Integer.parseInt(aux);
        	
        	System.out.println("Escribe numero de tropas: ");        	
        	aux = sc.nextLine();       	
        	tropasTerritorio = Integer.parseInt(aux);
        	
        	listaTerritorios.add(new territorio(nombreTerritorio, jugadorTerritorio, tropasTerritorio));
        	
        	System.out.println("Escribe el nombre del continente al que pertenece: ");
        	nombreContinente = sc.nextLine();
        	
        	System.out.println(nombreContinente);
        	
        	territorio t = new territorio(nombreTerritorio, jugadorTerritorio, tropasTerritorio);
        	
        	tablero1.anadirTerritorio(t);
        	tablero1.contruirContinenteExistente(nombreContinente).anadirTerritorio(t);
        	
        	System.out.println("Otro Territorio? (y) ");
        	res = sc.nextLine();
        	if(!res.equals("y"))
        	{
        		masTerritorios = false;
        	}
        }
        
               
        
        //Fronteras
        
        boolean masFronteras = true;
        
        System.out.println("Vamos a crear las fronteras");
        
        while(masFronteras)
        {
        	System.out.println("Escribe el nombre de los territorios con frontera: ");
        	String nombreTerritorio2;
        	nombreTerritorio = sc.nextLine();
        	nombreTerritorio2 = sc.nextLine();
        	
        	tablero1.anadirFrontera(tablero1.contruirTerritorioExistente(nombreTerritorio), tablero1.contruirTerritorioExistente(nombreTerritorio2)); 
        	
        	System.out.println("Otra frontera? (y) ");
        	res = sc.nextLine(); 
        	if(!res.equals("y"))
        	{
        		masFronteras = false;
        	}
        }
        
        sc.close();
        
		
		try( 
		FileOutputStream f = new FileOutputStream("archivos/" + tablero1.getNombre() + ".risk");
		ObjectOutputStream oos= new ObjectOutputStream(f);
		)
		{
			oos.writeObject(tablero1);
			
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
*/		

/*		
		tablero tablero2 = new tablero("");
		
		try( 
		FileInputStream f = new FileInputStream("archivos/juegoBasicoDefinitivo5.risk");
		ObjectInputStream ois= new ObjectInputStream(f);
		)
		{
			tablero2 = (tablero) ois.readObject();
			System.out.println(tablero2.toString());
		} catch(IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		Scanner sc = new Scanner(System.in);
		String nombreTerritorio;
		String res;
		
        boolean masFronteras = true;
        
        System.out.println("Vamos a crear las fronteras");
        
        while(masFronteras)
        {
        	System.out.println("Escribe el nombre de los territorios con frontera: ");
        	String nombreTerritorio2;
        	nombreTerritorio = sc.nextLine();
        	nombreTerritorio2 = sc.nextLine();
        	
        	tablero2.anadirFrontera(tablero2.contruirTerritorioExistente(nombreTerritorio), tablero2.contruirTerritorioExistente(nombreTerritorio2)); 
        	
        	System.out.println("Otra frontera? (y) ");
        	res = sc.nextLine(); 
        	if(!res.equals("y"))
        	{
        		masFronteras = false;
        	}
        }
        
		try( 
		FileOutputStream f = new FileOutputStream("archivos/JuegoBasicoDefinitivo6.risk");
		ObjectOutputStream oos= new ObjectOutputStream(f);
		)
		{
			oos.writeObject(tablero2);
			
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
		sc.close();
*/ 
		
/*		
		tablero tablero2 = new tablero("");
		
		try( 
		FileInputStream f = new FileInputStream("archivos/JuegoBasicoDefinitivo7.risk");
		ObjectInputStream ois= new ObjectInputStream(f);
		)
		{
			tablero2 = (tablero) ois.readObject();
			System.out.println(tablero2.toString());
		} catch(IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		tablero2.contruirTerritorioExistente("Siberia").setJugador(2);
		tablero2.contruirTerritorioExistente("Siberia").setTropas(3);
		tablero2.contruirTerritorioExistente("Yakutsk").setJugador(3);
		tablero2.contruirTerritorioExistente("Yakutsk").setTropas(3);
		
		
		if(tablero2.tieneFrontera(tablero2.contruirTerritorioExistente("Yakutsk"), tablero2.contruirTerritorioExistente("Kamchatka")))
		{
			System.out.println("tiene frontera");
		}
		else
		{
			System.out.println("no tiene frontera");
		}
*/		
	}

}
