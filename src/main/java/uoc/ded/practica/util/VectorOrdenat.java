package uoc.ded.practica.util;

import uoc.ei.tads.ClauValor;
import uoc.ei.tads.ContenidorAfitat;
import uoc.ei.tads.DiccionariVectorImpl;

import java.util.Arrays;
import java.util.Comparator;

public class VectorOrdenat<Clau extends String, Element> extends DiccionariVectorImpl<Clau, Element> implements ContenidorAfitat<Element> {
    private final int elementsMaxims;

    public VectorOrdenat(int elementsMaxims) {
        super(elementsMaxims);
        this.elementsMaxims = elementsMaxims;
    }

    @Override
    public boolean estaPle() {
        return nombreElems() >= elementsMaxims;
    }

    @Override
    public Element consultar(Clau clau) {
        int i = binarySearch(clau, 0, n);

        if (i == -1) {
            return null;
        }

        return this.diccionari[i].getValor();
    }

    @Override
    public boolean hiEs(Clau clau) {
        return consultar(clau) != null;
    }

    @Override
    public void afegir(Clau clau, Element obj) {
        super.afegir(clau, obj);

        ordenar();
    }

    @Override
    public Element esborrar(Clau clau) {
        int i = binarySearch(clau, 0, n);

        ClauValor<Clau, Element> clauElementClauValor = diccionari[i];
        Element element = clauElementClauValor.getValor();

        // We replace the last value with the toBeRemoved one
        // and then we remove the last element in the list
        ClauValor<Clau, Element> clauElementClauValor1 = diccionari[n-1];
        diccionari[i] = clauElementClauValor1;
        diccionari[n-1] = null;
        n--;

        ordenar();

        return element;
    }

    private void ordenar() {
        // There's no need to order if only 1 element
        if (nombreElems() > 1) {
            // We sort only the values that are inserted
            Arrays.sort(
                    diccionari,
                    0,
                    n,
                    Comparator.comparing(ClauValor::getClau)
            );
        }
    }

    private int binarySearch(Clau key, int left, int right) {
        if (right < 0) {
            return -1;
        }

        int mid = left + (right - left) / 2;

        if (diccionari[mid].getClau().equals(key)) {
            return mid;
        }

        if (diccionari[mid].getClau().compareTo(key) > 0) {
            return binarySearch(key, left, mid - 1);
        } else {
            return binarySearch(key, mid + 1, right);
        }
    }
}
