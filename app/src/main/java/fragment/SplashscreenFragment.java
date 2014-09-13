package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dafma.hcare.R;
import com.dafma.hcare.activity.HCareActivity;
import com.tigerlee.widget.ExpandedCircleProgressView;

public class SplashscreenFragment extends Fragment {

    private ExpandedCircleProgressView loadingExpandedCircleProgressView;
    private int progress = 0;
    private int tick = 10;
    private boolean inverse;
    private int puntini = 0;

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


        final CountDownTimer timer = new CountDownTimer(1000, tick) {

            public void onTick(long millisUntilFinished) {
                if (!inverse) {
                    loadingExpandedCircleProgressView.setProgress(progress++);
                } else {
                    loadingExpandedCircleProgressView.setProgress(progress--);
                }


            }

            public void onFinish() {
                if (!((HCareActivity) getActivity()).isConnected()) {
                    inverse = !inverse;
                    this.start();
                } else {
                    this.cancel();
                    ((HCareActivity) getActivity()).loadHome();
                }
            }
        };

        timer.start();


    }
}
