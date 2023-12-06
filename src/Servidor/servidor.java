package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Risk_juego.tablero;

public class servidor {

	public static void main(String[] args) {
		
		partida p1 = new partida("partida1", 5000);
		
		p1.start();
		
		
/*		
		ServerSocket ss = null;
		Socket[] socketJugadores = new Socket[10];
		DataOutputStream [] dout = new DataOutputStream[10];
		ObjectOutputStream [] oout = new ObjectOutputStream[10];
		ObjectInputStream [] oit = new ObjectInputStream[10];
		int jugador = 1;
		tablero tablero = null;
		boolean seguirJugando = true;
		
		
		try( 
				FileInputStream f = new FileInputStream("archivos/JuegoBasicoDefinitivo9.risk");
				ObjectInputStream ois0 = new ObjectInputStream(f);
				)
				{
					tablero = (tablero) ois0.readObject();
					System.out.println("tablero leido");
				} catch(IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
		
		
		try
		{
			ss = new ServerSocket(5000);	
			System.out.println("Servidor iniciado");			
			
			while(jugador < 5)
			{
				socketJugadores[jugador] = ss.accept();
				dout [jugador]= new DataOutputStream(socketJugadores[jugador].getOutputStream());
				oout [jugador]= new ObjectOutputStream(socketJugadores[jugador].getOutputStream());
				oit[jugador] = new ObjectInputStream(socketJugadores[jugador].getInputStream());	
				System.out.println("Se ha conectado el jugador " + jugador);
				dout[jugador].writeInt(jugador);
				dout[jugador].flush();
				oout[jugador].writeObject(tablero);
				oout[jugador].flush();
				jugador++;
				
			}
			
			System.out.println("Se han conectado todos los jugadores");
			
			jugador = 1;
			tablero.setTurnoJugador(jugador);
			
			System.out.println(tablero.turnoJugador());
			
			while(seguirJugando)
			{											
				for(int i = 1; i < 5; i++)
				{
					oout[i].writeObject(tablero);
					oout[i].flush();
				}
				System.out.println("turno del jugador " + jugador);			
				try {
					tablero = (tablero) oit[jugador].readObject();
					System.out.println("tablero recibido");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				if(tablero.haGanado(jugador))
				{
					seguirJugando = false;
				}
				jugador = (jugador % 4) + 1;
				tablero.setTurnoJugador(jugador);
												
			}
			
			ss.close();
			
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		
*/		
	}

}
