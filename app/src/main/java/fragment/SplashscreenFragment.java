package fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dafma.hcare.R;
import com.dafma.hcare.activity.HCareActivity;
import com.tigerlee.widget.ExpandedCircleProgressView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SplashscreenFragment extends Fragment {

    private ExpandedCircleProgressView loadingExpandedCircleProgressView;
    private int progress = 0;
    private int tick = 10;
    private boolean inverse;
    private int puntini = 0;

    public static boolean fileDownload = false;

    public SplashscreenFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getActionBar().hide();
        return inflater.inflate(R.layout.fragment_splashscreen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        loadingExpandedCircleProgressView = (ExpandedCircleProgressView) view.findViewById(R.id.expanded_circle_progress);

        loadingExpandedCircleProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HCareActivity) getActivity()).refindDevice();
            }
        });




        final CountDownTimer timer = new CountDownTimer(1000, tick) {

            public void onTick(long millisUntilFinished) {
                if (!inverse) {
                    loadingExpandedCircleProgressView.setProgress(progress++);
                } else {
                    loadingExpandedCircleProgressView.setProgress(progress--);
                }


            }

            public void onFinish() {
                Log.e("||||1", ((HCareActivity) getActivity()).isConnected()+"");
                Log.e("||||2", fileDownload+"");
                if (((HCareActivity) getActivity()).isConnected()==true && fileDownload==true) {
                    this.cancel();
                    ((HCareActivity) getActivity()).loadHome();
                } else {
                    inverse = !inverse;
                    this.start();
                }
            }
        }.start();



        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                try {
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(params[0]);
                    HttpResponse response = httpClient.execute(httpGet);
                    HttpEntity entity = response.getEntity();

                    BufferedHttpEntity buf = new BufferedHttpEntity(entity);

                    InputStream is = buf.getContent();

                    BufferedReader r = new BufferedReader(new InputStreamReader(is));

                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line + "\n");
                    }
                    String result = total.toString();
                    Log.i("Get URL", "Downloaded string: " + result);
                    return result;
                } catch (Exception e) {
                    Log.e("Get Url", "Error in downloading: " + e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Log.e("#####", result+"");

                String[] splitValue = result.split(",");

                HCareActivity.entrataLuce = Integer.parseInt(splitValue[0]);
                HCareActivity.bagnoLuceAcqua = Integer.parseInt(splitValue[1]);
                HCareActivity.cameraLuce = Integer.parseInt(splitValue[2]);
                HCareActivity.tornoACasa = Integer.parseInt(splitValue[3]);
                HCareActivity.abilitaCasa = Integer.parseInt(splitValue[4]);
                HCareActivity.accendiLuce = Integer.parseInt(splitValue[5]);

                fileDownload = true;

            }
        }.execute("http://192.168.205.36/HCare/data/data.txt");



    }
}
