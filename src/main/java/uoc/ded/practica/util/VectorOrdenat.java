package uoc.ded.practica.util;

import uoc.ei.tads.ContenidorAfitat;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.IteradorVectorImpl;

import java.util.Arrays;
import java.util.Comparator;

public class VectorOrdenat<Element> implements ContenidorAfitat<Element> {
    private final int elementsMaxims;
    private final Comparator<Element> comparator;
    private final Element[] elements;
    private int n = 0;

    @SuppressWarnings("unchecked")
    public VectorOrdenat(int elementsMaxims, Comparator<Element> comparator) {
        this.elementsMaxims = elementsMaxims;
        this.comparator = comparator;

        this.elements = (Element[]) new Object[elementsMaxims];
    }

    @Override
    public boolean estaPle() {
        return nombreElems() >= elementsMaxims;
    }


    @Override
    public int nombreElems() {
        return n;
    }

    @Override
    public Iterador<Element> elements() {
        return new IteradorVectorImpl<>(elements, n, 0);
    }

    @Override
    public boolean estaBuit() {
        return false;
    }

    public void addOrUpdate(Element element) {
        int i = binarySearch(element, 0, nombreElems());

        if (i != -1) {
            elements[i] = element;
        } else {
            elements[n] = element;
            n++;
        }

        ordenar();
    }

    private void ordenar() {
        // There's no need to order if only 1 element
        if (nombreElems() > 1) {
            // We sort only the values that are inserted
            Arrays.sort(elements, 0, nombreElems(), comparator);
        }
    }

    private int binarySearch(Element toSearch, int left, int right) {
        if (right < left) {
            return -1;
        }

        int mid = left + (right - left) / 2;

        Element currentElement = elements[mid];

        if (currentElement == null) {
            return -1;
        }

        int compare = comparator.compare(currentElement, toSearch);

        if (compare == 0) {
            return mid;
        }

        if (compare > 0) {
            return binarySearch(toSearch, left, mid - 1);
        }

        return binarySearch(toSearch, mid + 1, right);
    }

    public Element top() {
        return n > 0 ? elements[n - 1] : null;
    }
}
