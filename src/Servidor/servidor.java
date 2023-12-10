package Servidor;


public class servidor {

	public static void main(String[] args) {
		
		partida p1 = new partida("1", 5000);
		partida p2 = new partida("2", 5001);
		
		p1.start();	
		p2.start();
	}

}
