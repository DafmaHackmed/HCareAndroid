package fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dafma.hcare.R;
import com.dafma.hcare.activity.HCareActivity;
import com.dafma.hcare.customView.ListItemAdapter;
import com.dafma.hcare.model.ListItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listview;
    public static String home;


    public HomeFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listview = (ListView) view.findViewById(R.id.listView);


        ListItem[] values = new ListItem[] {
                new ListItem(R.drawable.home, "Colli Albano", false, 0),
                new ListItem(R.drawable.home, "Rogoredo", false, 0),
                new ListItem(R.drawable.home, "San Donato", false, 0)
        };
        ListItemAdapter adapter = new ListItemAdapter(getActivity(), values);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DetailsHomeFragment detailsHomeFragment = new DetailsHomeFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, detailsHomeFragment, DetailsHomeFragment.class.getName()).addToBackStack(HomeFragment.class.getName());
        switch (position){
            case 0:
                home = "Colli Albano";
                break;
            case 1:
                home = "Rogoredo";
                break;
            case 2:
                home = "San Donato";

        }
        ft.commit();
    }


    // HTTP GET request
    public static void sendGet() throws Exception {

        String url = "http://192.168.105.36/HCare/data/save.php?var1="+ HCareActivity.entrataLuce+"&var2="+HCareActivity.bagnoLuceAcqua+"&var3="+HCareActivity.cameraLuce+"&var4="+HCareActivity.tornoACasa+"&var5="+HCareActivity.abilitaCasa+"&var6="+HCareActivity.accendiLuce;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Log.e("Success", "success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("error", "error " + errorResponse);
            }

        });

    }

}
