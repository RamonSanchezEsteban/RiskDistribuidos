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
					this.tablero = (tablero) ois0.readObject();
					System.out.println("partida " + this.nombrePartida + ": " + "tablero leido");
				} catch(IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
		
		
		try
		{
			ss = new ServerSocket(this.host);	
			System.out.println("partida " + this.nombrePartida + ": " + "Servidor iniciado");			
			
			while(jugador < 5)
			{
				socketJugadores[jugador] = ss.accept();
				this.dout [jugador]= new DataOutputStream(this.socketJugadores[jugador].getOutputStream());
				this.oout [jugador]= new ObjectOutputStream(this.socketJugadores[jugador].getOutputStream());
				this.oit[jugador] = new ObjectInputStream(this.socketJugadores[jugador].getInputStream());	
				System.out.println("partida " + this.nombrePartida + ": " + "Se ha conectado el jugador " + this.jugador);
				this.dout[jugador].writeInt(this.jugador);
				this.dout[jugador].flush();
				this.oout[jugador].writeObject(this.tablero);
				this.oout[jugador].flush();
				this.jugador++;
				
			}
			
			for(int i = 1; i < 5; i++)
			{
				this.dout[i].writeInt(0);
				this.dout[i].flush();
			}
			
			System.out.println("partida " + this.nombrePartida + ": " + "Se han conectado todos los jugadores");
			
			this.jugador = 1;
			this.tablero.setTurnoJugador(this.jugador);

			while(this.seguirJugando)
			{											
				for(int i = 1; i < 5; i++)
				{
					this.oout[i].writeObject(this.tablero);
					this.oout[i].flush();
				}
				System.out.println("partida " + this.nombrePartida + ": " + "turno del jugador " + this.jugador);			
				try {
					this.tablero = (tablero) oit[this.jugador].readObject();
					System.out.println("partida " + this.nombrePartida + ": " + "tablero recibido");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				if(this.tablero.haGanado(this.jugador))
				{
					this.seguirJugando = false;
					System.out.println("partida " + this.nombrePartida + ": " + "el jugador " + this.tablero.turnoJugador() + " ha ganado");
				}
				else 
				{
					this.jugador = (this.jugador % 4) + 1;
					this.tablero.setTurnoJugador(this.jugador);
				}
												
			}
			
			for(int i = 1; i < 5; i++)
			{
				this.oout[i].writeObject(this.tablero);
				this.oout[i].flush();
			}
			
			this.ss.close();
			
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		

	}

}
