package group3.psit3.zhaw.ch.travelbuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import group3.psit3.zhaw.ch.travelbuddy.R;
import group3.psit3.zhaw.ch.travelbuddy.model.Route;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private List<Route> routes;
    private LayoutInflater inflater;

    public CustomListAdapter(Activity activity, List<Route> routes) {
        this.activity = activity;
        this.routes = routes;
    }

    @Override
    public int getCount() {
        return this.routes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.routes.get(position);
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

        TextView name = (TextView) convertView.findViewById(R.id.name);

        // getting route data for the row
        Route route = routes.get(position);

        // name
        name.setText(route.getName());

        return convertView;
    }
}
