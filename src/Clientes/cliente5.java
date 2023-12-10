package Clientes;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import Risk_juego.tablero;
import Risk_juego.territorio;

public class cliente5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int jugador = 0;
		tablero tablero1 = null;
        Scanner sc = new Scanner(System.in);
        String nombreTerritorio1;
        String nombreTerritorio2;
        String aux;		
		int tropasRecibidas;
		int tropasColocadas;
		int tropasAtacantes;
		int tropasMovidas;
		boolean seguirAtacando = true;
		boolean seguirReforzando = true;
		boolean seguirJugando = true;
		territorio t1,t2;
		ArrayList<territorio> L = new ArrayList(100);
		
		
		//Inicio
		
		try (Socket s = new Socket("localhost", 5001);
			DataInputStream in = new DataInputStream(s.getInputStream());
			ObjectInputStream oin = new ObjectInputStream(s.getInputStream());
			ObjectOutputStream oout	= new ObjectOutputStream(s.getOutputStream());
			)
		{
			jugador = in.readInt();
			System.out.println("Eres el jugador :" + jugador);
			try {
				tablero1 = (tablero) oin.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Esperando a que se conecten todos los jugadores...");
			
			int aux2 = in.readInt();
			
			System.out.println("Todos jugadores conectados");
			
			System.out.println("(Pulsa intro para continuar)");
			aux= sc.nextLine();
			
			System.out.println(tablero1.toString());

			
		//Partida	
			
			while(seguirJugando)
			{
				tablero1 = (tablero) oin.readObject();	
				System.out.println(tablero1.toString());
				if(tablero1.haGanado(tablero1.turnoJugador()))
				{
					System.out.println("El jugador " + tablero1.turnoJugador() + " ha ganado la partida");
					seguirJugando=false;
				}
				else
				{
					System.out.println("turno del jugador " + tablero1.turnoJugador());
					if(tablero1.turnoJugador() != jugador)
					{
						System.out.println("No es tu turno");			
					}
					else
					{
						if(tablero1.haPerdido(jugador)) 
						{
							System.out.println("Has perdido la partida");
						}
						else
						{
							//Colocacion de tropas
							System.out.print("\n\n");
							System.out.println("Fase de colocacion");
							System.out.print("\n\n");
							System.out.println("(Pulsa intro para continuar)");
							aux= sc.nextLine();
							System.out.println("Estos son tus territorios:");
							
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
							System.out.println("(Pulsa intro para continuar)");
							aux = sc.nextLine();
							
							//Ataque de territorios
							
							System.out.print("\n\n");
							System.out.println("Fase de ataque");
							System.out.print("\n\n");
							System.out.println("(Pulsa intro para continuar)");
							aux = sc.nextLine();

							
							System.out.println("Quieres atacar? (y/n)");
							aux = sc.nextLine();
							if(!aux.equals("y"))
							{
								seguirAtacando = false;
							}
							
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
										tropasAtacantes < 1
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
									System.out.println("(Pulsa intro para continuar)");
									aux = sc.nextLine();

								}
							}
							
							
							//Fase de reforzar
							
							
							
							System.out.println("Fase de reforzar");
							System.out.println("(Pulsa intro para continuar)");							
							aux = sc.nextLine();
							
							while(seguirReforzando)
							{
								System.out.println("Escribe el territorio del que salen las tropas:");
								System.out.println(tablero1.territoriosJugador(jugador).toString());						
								nombreTerritorio1 = sc.nextLine();
								System.out.println("Escribe el territorio al que van las tropas:");
								tablero1.calcularTerritoriosConectados(tablero1.contruirTerritorioExistente(nombreTerritorio1), 10, L);
								System.out.println(L.toString());
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
										seguirReforzando = false;
									}
								}
							}
							
							System.out.println("Turno acabado");
							System.out.println("(Pulsa intro para continuar)");
							aux = sc.nextLine();
							
							oout.writeObject(tablero1);
							oout.flush();
						}																				
					}					
				}
			}

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}