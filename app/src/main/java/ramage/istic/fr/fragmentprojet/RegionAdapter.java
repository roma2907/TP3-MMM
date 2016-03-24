package ramage.istic.fr.fragmentprojet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramage on 17/03/16.
 */
public class RegionAdapter extends ArrayAdapter<Region> {


    public RegionAdapter(Context context, List<Region> regions) {
        super(context, 0, regions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Region region = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_region, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.nameRegion);
        // Populate the data into the template view using the data object
        tvName.setText(region.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}
