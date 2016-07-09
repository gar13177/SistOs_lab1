
import java.util.concurrent.Callable;

public class Codificador implements Callable<char[]> {

	private static char[] array;
	private int codificacion;
	private int _inicio = 0;
	private int _final = 0;
	private String abc = "abcdefghijklmnopqrstuvwxyz";
	
	public Codificador(char[] array, int codificacion){
		this.array = array;
		this.codificacion = codificacion;
	}
	
	public Codificador(char[] array, int codificacion, int _inicio, int _final){
		this.array = array;
		this.codificacion = codificacion;
		this._inicio = _inicio;
		this._final = _final;
	}
	
	public char[] getArray(){
		return array;
	}

	@Override
	public char[] call() throws Exception {
		// TODO Auto-generated method stub
		for(int i = _inicio; i<_final; i++){
			char actual = array[i];
			int indice = abc.indexOf(actual);
			indice = indice + codificacion;
			if (indice >= abc.length()){
				indice = indice-abc.length();
			}
			array[i] = abc.charAt(indice);
		}
		return null;
	}

}
