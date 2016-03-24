package ramage.istic.fr.fragmentprojet;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements IActivityVin, OnMapReadyCallback {

    private ListRegionFragment listFragment;
    private WebFragment webFragment;
    private Region currentRegionMap;

    public static final String ARG_REGION="region";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //on set la vue
        setContentView(R.layout.activity_main);

        //fragment existant seulement si on est en mode tablette
        View fragmentContainer = findViewById(R.id.fragmentContainer);

        //on crée le fragment qui affiche la liste des régions
        listFragment=new ListRegionFragment();

        //on rajoute la liste des régions à l'activité dans tous les cas
        final FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
       transaction.add(R.id.mainLayout,listFragment);

        //si l'identifiant du fragment existe on rajoute le fragment
        if(fragmentContainer != null){
            //on crée le fragment web et on l'ajoute à l'activité
            createWebFragment(R.id.fragmentContainer);
            transaction.add(R.id.fragmentContainer,webFragment);
        }

        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refreshWebContent(Region region) {
        //vérification si on est en mode tablette
        View view = findViewById(R.id.fragmentContainer);

        //on actualise la région que l'on est en train de consulter
        currentRegionMap = region;
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();

        //si on est en mode smartphone
        if(view == null){
            //création du web fragment
            createWebFragment(R.id.mainLayout);

            //on lui passe en arguments la région sur laquelleon vien de cliquer
            Bundle args = new Bundle();
            args.putParcelable(ARG_REGION, region);
            webFragment.setArguments(args);

            //on remplace le fragments des régions par le fragments web
            transaction.replace(R.id.mainLayout, webFragment).addToBackStack("listRegion");
        }else{
            //si on est en mode tablette
            if(webFragment == null){
                //si le web fragment est nul ca veut dire que l'on est en mode google map
                //on recrée un web fragment
                createWebFragment(R.id.fragmentContainer);

                //on lui passe en argument la région où l'on veut aller
                Bundle args = new Bundle();
                args.putParcelable(ARG_REGION, region);
                webFragment.setArguments(args);

                //on remplace la google map par le fragment web
                transaction.replace(R.id.fragmentContainer, webFragment).addToBackStack("webFragment");
            }else {
                //si on était deja en mode web on change juste la région
                webFragment.selectRegion(region.getUrl());
            }
        }
        //mis à jour des fragments de l'activité
        transaction.commit();
    }

    /**
     * Cette méthode crée le web fragment et crée la méthode qui sera appelé quand on cliquera sur le bouton localiser.
     * @param replace : identifiant du layout où l'on va placer le web fragment
     */
    private void createWebFragment( final int replace){
        //instanciation du web fragment
        webFragment = new WebFragment();

        final OnMapReadyCallback mapReadyCallbac = this;
        //on set le clickListener appelé quand on clique sur le bouton
        webFragment.setClickListener(new WebFragment.ClickListener() {
            @Override
            public void onLocate() {
                //création du fragment google map
                SupportMapFragment supportMapFragment = new SupportMapFragment();
                supportMapFragment.getMapAsync(mapReadyCallbac);

                //on remplace le web fragment par le fragment google map
                getSupportFragmentManager().beginTransaction().replace(replace,supportMapFragment).addToBackStack("webFragment").commit();
                //on met web fragment à null pour savoir que l'on est en mode googlemap
                webFragment = null;
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //ajoute un marqueur sur la région courante et place la caméra dessus
        googleMap.addMarker(new MarkerOptions().position(currentRegionMap.getPosition()).title(currentRegionMap.getName()));
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(currentRegionMap.getPosition(),7,0,0)));
    }
}
