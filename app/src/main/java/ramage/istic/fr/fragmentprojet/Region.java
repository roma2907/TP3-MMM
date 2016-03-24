package ramage.istic.fr.fragmentprojet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramage on 17/03/16.
 */
public class Region implements Parcelable {
    //nom de la région
    private String name;
    //url sur lequel il faut pointer quand on choisit la région
    private String url;
    //position googlemap de la région
    private LatLng position;


    public Region(String pName,String pUrl,LatLng pPosition){
        url=pUrl;
        name=pName;
        position=pPosition;
    }

    public String getName(){
        return name;
    }

    public LatLng getPosition(){
        return position;
    }

    public String getUrl(){
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Region(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }

    public static final Creator<Region> CREATOR = new Creator<Region>() {
        @Override
        public Region createFromParcel(Parcel in) {
            return new Region(in);
        }

        @Override
        public Region[] newArray(int size) {
            return new Region[size];
        }
    };
}

//classe qui fournit le jeu de données de l'application
class RegionProvider{

    //Génération des régions disponible
    public static List<Region> generateData(){
        List<Region> regions = new ArrayList<Region>();
        regions.add(new Region("Alsace","http://technoresto.org/vdf/alsace/index.shtml",new LatLng(48.2454135,6.4133866)));
        regions.add(new Region("Beaujolais","http://technoresto.org/vdf/beaujolais/index.html",new LatLng(46.083347,4.1063428)));
        regions.add(new Region("Jura","http://technoresto.org/vdf/jura/index.html",new LatLng(46.7828854,5.1674289)));
        regions.add(new Region("Champagne","http://technoresto.org/vdf/champagne/index.html",new LatLng(48.8546136,2.3886813)));
        regions.add(new Region("Savoie","http://technoresto.org/vdf/savoie/index.html",new LatLng(45.4946988,5.8419067)));
        regions.add(new Region("Languedoc-Roussillon","http://technoresto.org/vdf/languedoc/index.html",new LatLng(43.6363165,1.0179663)));
        regions.add(new Region("Bordelais","http://technoresto.org/vdf/bordelais/index.html",new LatLng(44.8638281,-0.656353)));
        regions.add(new Region("Vallée du Rhône","http://technoresto.org/vdf/cotes_du_rhone/index.html",new LatLng(44.7538159,3.1236989)));
        regions.add(new Region("Val de Loire","http://technoresto.org/vdf/val_de_loire/index.html",new LatLng(46.3143467,-1.2043151)));
        regions.add(new Region("Sud Ouest","http://technoresto.org/vdf/sud-ouest/index.html",new LatLng(44.8474999,-1.2044848)));
        regions.add(new Region("Corse","http://technoresto.org/vdf/corse/index.html",new LatLng(42.1771385,7.9260008)));
        regions.add(new Region("Bourgogne","http://technoresto.org/vdf/bourgogne/index.html",new LatLng(47.2744354,3.0584635)));

        return regions;
    }
}
