
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import org.knowm.xchart.*;

public class Main {

    
    public static void main(String args[]){
    	Random random = new Random();
    	String abc = "abcdefghijklmnopqrstuvwxyz";
    	int longitud = 10000;
    	int codificacion = 3;
    	
    	String str = "";
    	for (int i = 0; i < longitud; i++){
    		int select = random.nextInt(abc.length()); 
    		str += abc.charAt(select);
    	}
    	
    	//System.out.println("org: "+str);
    	char[] array = str.toCharArray();
    	
    	Scanner reader = new Scanner(System.in);  // Reading from System.in
    	System.out.print("Cantidad de threads: ");
    	int n = reader.nextInt(); // Scans the next token of the input as an int.
    	reader.close();  
    	
    	String all = "";
    	double[] threads = new double[n];
    	double[] time = new double[n];
    	
    	int avance = 1;//debe llegar a 10
    	System.out.println("|                   |");
    	System.out.print(" ");
    	
    	
    	
    	for (int i = 1; i <= n; i++){
    		Director d = new Director(array,i,codificacion);
            d.start();
            threads[i-1] = (double)i;
            time[i-1] = (double)d.getTime();
            all += "Threads: "+i+"\t"+d.calculateTime()+"\n";
            if ((float)i/n > 0.05*avance){
            	System.out.print("=");
            	avance++;
            }
            if (i != n)
            	array = str.toCharArray();
    	}
    	System.out.println();
    	
    	System.out.println(all);
    	
    	XYChart chart = QuickChart.getChart("Sample Chart", "Cantidad de Threads", "Tiempo en ns", "t(T)", threads, time);

        // Show it
        new SwingWrapper(chart).displayChart();

    	System.out.println("Se ha abierto una nueva ventana");
    	System.out.println("Se ha escrito un nuevo archivo 'cadena.txt' con el resultado");
    	
    	try{
	    	PrintWriter writer = new PrintWriter("cadena.txt", "UTF-8");
	    	writer.println("Original:");
	    	writer.println(str);
	    	writer.println("\nResultado:");
	    	writer.println(String.valueOf(array));
	    	writer.close();
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	
    }

}
