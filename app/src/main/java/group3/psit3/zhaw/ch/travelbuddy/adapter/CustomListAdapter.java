package group3.psit3.zhaw.ch.travelbuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import group3.psit3.zhaw.ch.travelbuddy.R;
import group3.psit3.zhaw.ch.travelbuddy.app.AppController;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private List<Tour> tours;
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity) {
        this.activity = activity;
        this.tours = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return this.tours.size();
    }

    @Override
    public Object getItem(int position) {
        return this.tours.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        if (imageLoader == null) {
            imageLoader = AppController.getInstance().getImageLoader();
        }

        NetworkImageView thumbnail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView description = (TextView) convertView.findViewById(R.id.description);

        // getting tour data for the row
        Tour tour = tours.get(position);

        // attributes
        thumbnail.setImageUrl(tour.getThumbnailUrl(), imageLoader);
        name.setText(tour.getName());
        description.setText(tour.getDescription());

        return convertView;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
        notifyDataSetChanged();
    }

    public List<Tour> getTours() {
        return tours;
    }
}
