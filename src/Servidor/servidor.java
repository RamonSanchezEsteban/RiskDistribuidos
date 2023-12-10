package Servidor;


public class servidor {

	public static void main(String[] args) {
		
		System.out.println("numero de partidas: " + args.length);
		
		
		partida[] partidas = new partida[20];
		int l = args.length;		
		for(int i = 0; i < l; i++)
		{
			partidas[i] = new partida( " " + (i+1), Integer.parseInt(args[i]));
			partidas[i].start();
		}		
	}

}
