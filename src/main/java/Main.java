import uoc.ded.practica.exceptions.LimitExceededException;
import uoc.ded.practica.util.Log;
import uoc.ded.practica.util.VectorOrdenat;
import uoc.ei.tads.Iterador;

public class Main {
    public static void main(String[] args) {
        VectorOrdenat<String, String> test = new VectorOrdenat<>(6);

        test.afegir("test-3", "hola-3");
        test.afegir("test-1", "hola-1");
        test.afegir("test-2", "hola-2");
        test.afegir("test-4", "hola-4");
        test.afegir("test-5", "hola-5");
        test.afegir("test-0", "hola-0");

        Iterador<String> elements = test.elements();

        while (elements.hiHaSeguent()) {
            Log.d("Elements", elements.seguent());
        }

        String consultar = test.consultar("test-2");

        Log.d("Consultat 3", consultar);

        test.esborrar("test-4");

        Log.d("ah", test.nombreElems());

        elements = test.elements();

        while (elements.hiHaSeguent()) {
            Log.d("Elements 2", elements.seguent());
        }
    }
}
