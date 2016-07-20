package com.victorvieira.lifeway.apresentacao.extras;

import java.util.ArrayList;

public class AdapterGroup {

    ArrayList<ListaAlimentosAdapter> adapters = new ArrayList<ListaAlimentosAdapter>();

    public AdapterGroup() { super(); }

    public void addAdapter(ListaAlimentosAdapter adapter) {
        adapters.add(adapter);
    }

    public ListaAlimentosAdapter getAdapter(int index){
        return adapters.get(index);
    }

    public ListaAlimentosAdapter getLastAdapter() { return adapters.get(adapters.size() - 1); }

    public ListaAlimentosAdapter getBeforeLastAdapter() { return adapters.get(adapters.size() -2); }

    public void clearAdapters() {
        for(int i = 0; i < adapters.size() - 2; i++) {
            adapters.remove(i);
        }
    }

}
