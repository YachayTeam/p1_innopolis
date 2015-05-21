package innopolis.test;
/**
 * Permite instanciar objetos tipo <code>Punto</code>
 * 
 * @author Lucho
 * @version 1.0 
 */
public class Punto {
	
	/*campo que almacena la variable X*/	
	private int coordenadaX;
	
	/*campo que almacena la variable Y*/
	private int coordenadaY;
	
	/**
	 * Inicializa valores (0,0) en una instancia tipo <code>Punto</code>
	 */
	
	public Punto(){
		
		coordenadaX = 0;
		coordenadaY = 0;
	}
	
	/**
	 * Inicializa valores dados una instancia tipo <code>Punto</code>
	 * 
	 * @param initialCoordenadaX valor para la coordenada X
	 * @param initialCoordenadaY valor para la coordenada Y
	 */
	
	public Punto(int initialCoordenadaX, int initialCoordenadaY){
		
		coordenadaX = initialCoordenadaX;
		coordenadaY = initialCoordenadaY;
	}

	/**
	 * Devuelve valor de x
	 * 
	 * @return valor de la coordenadaX
	 */
	public int getCoordenadaX() {
		
		return coordenadaX;
	}

	/**
	 * Asigna in valor de x
	 * 
	 * @param valueCoordenadaX valor para la coordenadaX
	 */
	public void setCoordenadaX(int valueCoordenadaX) {
		
		this.coordenadaX = valueCoordenadaX;
	}

	/**
	 * Devuelve valor de y
	 * 
	 * @return the coordenadaY
	 */
	public int getCoordenadaY() {
		
		return coordenadaY;
	}

	/**
	 * Asigna in valor de y
	 * 
	 * @param valueCoordenadaY valor para la coordenadaY
	 */
	public void setCoordenadaY(int valueCoordenadaY) {
		
		this.coordenadaY = valueCoordenadaY;
	}
	
	/**
	 * Imprime asteriscos de acuerdo al numero de x
	 */
	public void estrellar(){
		
		for(int i=0; i<coordenadaX;i++){
			
			System.out.println("*");
		}
	}

}
