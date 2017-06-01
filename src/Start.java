import model.Heuristic;
import model.Greedy;
import controller.Controller;


import java.io.IOException;
import java.util.Scanner;

/**
 * @author Mateusz Byczkowski
 * zadanie 10LS "hotel"
 */

public class Start {
	
	public static void main(String[] args) throws IOException {
        
		Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz opcje: 1 - dane wejsciowe z pliku, 2 - generuj losowe dane wejsciowe: \n");
        String answer = scanner.nextLine();
                
        if(answer.equals("1")){
            System.out.println("Podaj nazwe pliku: ");
            String fileName = scanner.nextLine();
            scanner.close();
            
        	Greedy g = new Greedy(fileName);
            Heuristic h = new Heuristic (fileName);
            Controller c = new Controller (g, h);
            c.run2();
            c.run();
        }else if (answer.equals("2")){
            System.out.println("Podaj liczbe osob(n): ");
            int n = scanner.nextInt();
            System.out.println("Podaj gestosc relacji znajomosci (od 1 do n-1): ");
            int p = scanner.nextInt();
            System.out.println("Podaj ilosc prob: ");
            int ilosc = scanner.nextInt();
            
            for (int i=0;i<ilosc;++i){
            
            Controller c = new Controller (n, p);
            c.run2();
            c.run();
            }
            scanner.close();
            return;
        }else {
        	System.out.println("Brak takiej opcji - wybierz ponownie.");
        }

    }
	
}
