package fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.dafma.hcare.R;
import com.dafma.hcare.activity.HCareActivity;
import com.dafma.hcare.customView.ListItemAdapter;
import com.dafma.hcare.model.ListItem;

import org.apache.http.client.methods.HttpOptions;

public class DetailsHomeFragment extends Fragment {

    private ListView listview;

    public DetailsHomeFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView)view.findViewById(R.id.nameHouse)).setText(HomeFragment.home);
        ((Switch)view.findViewById(R.id.switchView)).setChecked((HCareActivity.abilitaCasa == 1) ? true : false);
        ((Switch)view.findViewById(R.id.switchView)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HCareActivity.abilitaCasa = (isChecked)?1:0;
                try {
                    HomeFragment.sendGet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((Button)view.findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HCareActivity.tornoACasa = 1;
                try {
                    HomeFragment.sendGet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        listview = (ListView) view.findViewById(R.id.listView);

        ListItem[] values = new ListItem[] {
                new ListItem(R.drawable.home, "Entrata", (HCareActivity.entrataLuce==1)?true:false, 1),
                new ListItem(R.drawable.shower_round, "Bagno", (HCareActivity.bagnoLuceAcqua==1)?true:false, 1),
                new ListItem(R.drawable.home, "Camera", (HCareActivity.tornoACasa==1)?true:false, 1)
        };



        ListItemAdapter adapter = new ListItemAdapter(getActivity(), values);
        listview.setAdapter(adapter);


    }

}
