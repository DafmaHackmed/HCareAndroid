package com.dafma.hcare.customView;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.dafma.hcare.R;
import com.dafma.hcare.activity.HCareActivity;
import com.dafma.hcare.model.ListItem;

import fragment.HomeFragment;

/**
 * Created by antonioscardigno on 14/09/14.
 */
public class ListItemAdapter extends ArrayAdapter<ListItem> {


    private final Context context;
    private final ListItem[] values;

    public ListItemAdapter(Context context, ListItem[] values) {
        super(context, R.layout.list_view_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        Switch switchValue = (Switch) rowView.findViewById(R.id.switchView);
        switch (position){
            case 0:
                switchValue.setChecked((HCareActivity.entrataLuce == 1)?true:false);
                switchValue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        HCareActivity.entrataLuce = (isChecked)?1:0;
                        try {
                            HomeFragment.sendGet();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 1:
                switchValue.setChecked((HCareActivity.bagnoLuceAcqua == 1)?true:false);
                switchValue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        HCareActivity.bagnoLuceAcqua = (isChecked)?1:0;
                        try {
                            HomeFragment.sendGet();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 2:
                switchValue.setChecked((HCareActivity.cameraLuce == 1)?true:false);
                switchValue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        HCareActivity.cameraLuce = (isChecked)?1:0;
                        try {
                            HomeFragment.sendGet();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
        textView.setText(values[position].getText());
        imageView.setImageDrawable(context.getResources().getDrawable(values[position].getImage()));

        if (values[position].getType() == 0){
            switchValue.setVisibility(View.GONE);
        }else{
            switchValue.setVisibility(View.VISIBLE);
        }

        return rowView;
    }

}
