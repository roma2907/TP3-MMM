package ramage.istic.fr.fragmentprojet;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;


/**
 * Fragment web qui permet de visualiser le site web lier à la région choisi
 */
public class WebFragment extends Fragment implements IWebVinFragment {

    private ClickListener clickListener;

    @Override
    public void selectRegion(String url) {
        //on récupère la webview
        WebView webview = (WebView) getView().findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        //on set l'url
        webview.loadUrl(url);
        //on rend le bouton localiser visible s'il ne l'était pas
        ((Button) getView().findViewById(R.id.buttonLocalisation)).setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //lorsque l'acivité est crée, on récupère les arguments s'il y en a et on sélectionne la région choisi
        Bundle args=getArguments();

        if(args != null){
            //sélection de la région dans le web view
            selectRegion(((Region)args.getParcelable(MainActivity.ARG_REGION)).getUrl());
        }
        //on récupère le bouton localiser
        Button button = (Button) getView().findViewById(R.id.buttonLocalisation);
        //lorsqu'on clique sur le bouton
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on affiche google map et on se positionne sur la région sélectionné
                clickListener.onLocate();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    public void setClickListener(ClickListener pClickListener){
        clickListener=pClickListener;
    }

    public interface ClickListener{
        public void onLocate();
    }
}
