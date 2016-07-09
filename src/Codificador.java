
import java.util.concurrent.Callable;

public class Codificador implements Callable<char[]> {

	private char[] array;
	private int codificacion;
	private String abc = "abcdefghijklmnopqrstuvwxyz";
	
	public Codificador(char[] array, int codificacion){
		this.array = array;
		this.codificacion = codificacion;
	}
	
	public char[] getArray(){
		return array;
	}

	@Override
	public char[] call() throws Exception {
		// TODO Auto-generated method stub
		for(int i = 0; i<array.length; i++){
			char actual = array[i];
			int indice = abc.indexOf(actual);
			indice = indice + codificacion;
			if (indice >= abc.length()){
				indice = indice-abc.length();
			}
			array[i] = abc.charAt(indice);
		}
		return array;
	}

}
