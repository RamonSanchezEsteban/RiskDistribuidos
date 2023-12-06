package Servidor;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Risk_juego.tablero;

public class partida extends Thread{
	
	private String nombrePartida;
	private ServerSocket ss;
	private Socket[] socketJugadores;
	private DataOutputStream [] dout;
	private ObjectOutputStream [] oout;
	private ObjectInputStream [] oit;
	private int jugador;
	private tablero tablero;
	private boolean seguirJugando;
	private int host;
	
	public partida(String nombre, int host)
	{
		this.nombrePartida=nombre;
		this.ss = null;
		this.socketJugadores = new Socket[10];
		this.dout = new DataOutputStream[10];
		this.oout = new ObjectOutputStream[10];
		this.oit = new ObjectInputStream[10];
		this.jugador = 1;
		this.tablero = null;
		this.seguirJugando = true;
		this.host = host;
	}
	
	public void run()
	{
		try( 
				FileInputStream f = new FileInputStream("archivos/JuegoBasicoDefinitivo9.risk");
				ObjectInputStream ois0 = new ObjectInputStream(f);
				)
				{
					tablero = (tablero) ois0.readObject();
					System.out.println("partida " + this.nombrePartida + ": " + "tablero leido");
				} catch(IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
		
		
		try
		{
			ss = new ServerSocket(5000);	
			System.out.println("partida " + this.nombrePartida + ": " + "Servidor iniciado");			
			
			while(jugador < 5)
			{
				socketJugadores[jugador] = ss.accept();
				dout [jugador]= new DataOutputStream(socketJugadores[jugador].getOutputStream());
				oout [jugador]= new ObjectOutputStream(socketJugadores[jugador].getOutputStream());
				oit[jugador] = new ObjectInputStream(socketJugadores[jugador].getInputStream());	
				System.out.println("partida " + this.nombrePartida + ": " + "Se ha conectado el jugador " + jugador);
				dout[jugador].writeInt(jugador);
				dout[jugador].flush();
				oout[jugador].writeObject(tablero);
				oout[jugador].flush();
				jugador++;
				
			}
			
			System.out.println("partida " + this.nombrePartida + ": " + "Se han conectado todos los jugadores");
			
			jugador = 1;
			tablero.setTurnoJugador(jugador);

			while(seguirJugando)
			{											
				for(int i = 1; i < 5; i++)
				{
					oout[i].writeObject(tablero);
					oout[i].flush();
				}
				System.out.println("partida " + this.nombrePartida + ": " + "turno del jugador " + jugador);			
				try {
					tablero = (tablero) oit[jugador].readObject();
					System.out.println("partida " + this.nombrePartida + ": " + "tablero recibido");
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
			
		

	}

}
