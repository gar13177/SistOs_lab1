
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Director {
	
	private char[] array;
	private char[] narray;
	private int thr_count;
	private ExecutorService es;
	private int codificacion = 2;
	private List<Callable<char[]>> list;
	private long estimatedTime;
	
	public Director(char[] array, int threads){
		this.array = array;
		this.thr_count = threads;
		this.es = Executors.newFixedThreadPool(threads);
		this.list = new ArrayList<Callable<char[]>>();
		
	}
	
	public Director(char[] array, int threads, int codificacion){
		this.array = array;
		this.thr_count = threads;
		this.es = Executors.newFixedThreadPool(threads);
		this.list = new ArrayList<Callable<char[]>>();
		this.codificacion = codificacion;
		
	}
	
	public void start(){
		long startTime = System.nanoTime();
		int sub_array = array.length/thr_count;
		int first_pos = 0;//posicion inicial
		int last_pos = sub_array;//posicion final
		
		for(int i = 0; i < thr_count; i++){
			
			if (i == thr_count-1){//si ya estoy en el ultimo
				last_pos = array.length;
			}
			
			char[] temp = Arrays.copyOfRange(array, first_pos, last_pos);
			Callable<char[]> callable = new Codificador(temp,codificacion);
			list.add(callable);
			
			first_pos = last_pos;
			last_pos += sub_array;
		}
		
		List<Future<char[]>> answers;
		try{
			answers = es.invokeAll(list);
			narray = new char[array.length];
			int i = 0;
			for (Future<char[]> future : answers){
				for (int j = 0; j<future.get().length; j++){
					narray[i] = future.get()[j];
					i++;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		estimatedTime = System.nanoTime()-startTime;
		es.shutdown();
	}
	
	public char[] getArray(){
		return narray;
	}
	
	public String calculateTime(){
		long nanoTime = estimatedTime;
		String str = "Estimated time: ";
		long hr, min, sec, msec, nsec, time = 0;
		if ((hr = TimeUnit.NANOSECONDS.toHours(nanoTime))>0){
			str += ""+hr+"hr ";
			time = time+hr*60;
		}
		if ((min = TimeUnit.NANOSECONDS.toMinutes(nanoTime))>0){
			min = min-time;
			str += ""+min+"min ";
			time = (time + min)*60;
		}
		if ((sec = TimeUnit.NANOSECONDS.toSeconds(nanoTime))>0){
			sec = sec-time;
			str += ""+sec+"s ";
			time = (time + sec)*1000;
		}
		if ((msec = TimeUnit.NANOSECONDS.toMillis(nanoTime))>0){
			msec = msec-time;
			str += ""+msec+"ms ";
			time = (time + msec)*1000000;
		}
		if ((nsec = TimeUnit.NANOSECONDS.toNanos(nanoTime))>0)
			nsec = nsec-time;
			str += ""+nsec+"ns ";
			
		return str;
	}
	
	public long getTime(){
		return estimatedTime;
	}

}
