package ramage.istic.fr.fragmentprojet;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Fragment qui gère la liste des régions.
 */
public class ListRegionFragment extends ListFragment {

    //on récupère la liste des régions
    private List<Region> regions= RegionProvider.generateData();

    //quand on clique sur un item dans la liste
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //on récupère l'activité
        IActivityVin acivity = (IActivityVin) getActivity();
        //on récupère la région
        Region value = regions.get(position);
        //on rafraichit le contenu du fragment web
        acivity.refreshWebContent(value);
    }

    public ListRegionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        //on crée un adapter pour la listview
        RegionAdapter adapter = new RegionAdapter(getContext(), regions);
        setListAdapter(adapter);
        return  view;
    }
}
