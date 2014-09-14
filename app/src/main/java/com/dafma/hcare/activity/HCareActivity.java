package com.dafma.hcare.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dafma.hcare.R;
import com.empatica.empalink.ConnectionNotAllowedException;
import com.empatica.empalink.EmpaDeviceManager;
import com.empatica.empalink.config.EmpaSensorStatus;
import com.empatica.empalink.config.EmpaSensorType;
import com.empatica.empalink.config.EmpaStatus;
import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.empatica.empalink.delegate.EmpaStatusDelegate;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import java.util.List;

import fragment.HomeFragment;
import fragment.SplashscreenFragment;


public class HCareActivity extends FragmentActivity implements EmpaDataDelegate, EmpaStatusDelegate {

    private EmpaDeviceManager empatica;
    private boolean connected;
    public static Fragment splashscreenFragment = new SplashscreenFragment();
    public static Fragment homeFragment = new HomeFragment();

    public static int entrataLuce;
    public static int bagnoLuceAcqua;
    public static int cameraLuce;
    public static int tornoACasa;
    public static int abilitaCasa;
    public static int accendiLuce;

    public boolean getLuceAccesaFatta;

    private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId", ESTIMOTE_PROXIMITY_UUID, null, null);

    private BeaconManager beaconManager;

    public boolean isConnected() {
        return connected;
    }

    private void setupActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        ActionBar.LayoutParams lp1 = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        View customNav = LayoutInflater.from(this).inflate(R.layout.actionbar, null); // layout which contains your button.

        actionBar.setCustomView(customNav, lp1);
    }

    private void initComponents(){
        Log.e("HCare :: initComponents :: v", "start init");

        empatica = new EmpaDeviceManager(HCareActivity.this, HCareActivity.this, HCareActivity.this);
        empatica.authenticateWithAPIKey("fdfe03151260495f854a9338d115d6b2");

        beaconManager = new BeaconManager(this);

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
                Log.d("##########", "Ranged beacons: " + beacons);
                for(Beacon b:beacons){
                    if (isConnected() && SplashscreenFragment.fileDownload) {
                        if (b.getMinor() == 32585 && b.getMajor() == 50651) {
                            if (Utils.computeProximity(b) == Utils.Proximity.IMMEDIATE) {
                                if (!getLuceAccesaFatta) {
                                    try {
                                        accendiLuce = 1;
                                        HomeFragment.sendGet();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    getLuceAccesaFatta = true;
                                }
                            } else {
                                accendiLuce = 0;
                                try {
                                    HomeFragment.sendGet();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                getLuceAccesaFatta = false;
                            }
                        }
                    }
                }
            }
        });

        Log.e("HCare :: initComponents :: v", "end init");
    }

    private void initUI(){
        Log.e("HCare :: initUI :: v", "start init");
        setupActionBar();
        Log.e("HCare :: initUI :: v", "end init");

    }

    @Override
    protected void onStart() {
        super.onStart();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e("##########", "Cannot start ranging", e);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.disconnect();
        empatica.disconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
        } catch (RemoteException e) {
            Log.e("##########", "Cannot stop but it does not matter now", e);
        }
    }

    private void loadFragments(){

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, splashscreenFragment, SplashscreenFragment.class.getName());
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcare);

        loadFragments();
        initUI();
        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void didReceiveGSR(float v, double v2) {
        Log.i("HCare :: didReceiveGSR :: v", v + "");
        Log.i("HCare :: didReceiveGSR :: v2", v + "2");

    }

    @Override
    public void didReceiveBVP(float v, double v2) {
        Log.i("HCare :: didReceiveBVP :: v", v + "");
        Log.i("HCare :: didReceiveBVP :: v2", v + "2");

    }

    @Override
    public void didReceiveIBI(float v, double v2) {
        Log.i("HCare :: didReceiveIBI :: v", v + "");
        Log.i("HCare :: didReceiveIBI :: v2", v + "2");

    }

    @Override
    public void didReceiveTemperature(float v, double v2) {
        Log.i("HCare :: didReceiveTemperature :: v", v + "");
        Log.i("HCare :: didReceiveTemperature :: v2", v + "2");

    }

    @Override
    public void didReceiveAcceleration(int i, int i2, int i3, double v) {
        Log.i("HCare :: didReceiveAcceleration :: v", v + "");
        Log.i("HCare :: didReceiveAcceleration :: v2", v + "2");

    }

    @Override
    public void didReceiveBatteryLevel(float v, double v2) {
        Log.i("HCare :: didReceiveBatteryLevel :: v", v + "");
        Log.i("HCare :: didReceiveBatteryLevel :: v2", v + "2");

    }

    @Override
    public void didUpdateStatus(EmpaStatus empaStatus) {
        switch (empaStatus){
            case READY:
                Log.e("HCare :: didUpdateStatus :: empaStatus", "Ready");
                empatica.startScanning();
                break;
            case CONNECTED:
                connected = true;
                Log.e("HCare :: didDiscoverDevice", "Connected");
                break;
            default:
                break;
        }
    }

    @Override
    public void didUpdateSensorStatus(EmpaSensorStatus empaSensorStatus, EmpaSensorType empaSensorType) {

    }

    @Override
    public void didDiscoverDevice(BluetoothDevice device, int rssi, boolean canConnect) {
        Log.e("HCare :: didDiscoverDevice", "Discovered");

        if (canConnect){
            empatica.stopScanning();
            try {
                empatica.connectDevice(device);
            } catch (ConnectionNotAllowedException e) {
                Log.e("HCare :: didDiscoverDevice", "Cannot connect");
            }
        }

    }

    public void refindDevice(){
        empatica.stopScanning();
        empatica.startScanning();
    }

    @Override
    public void didRequestEnableBluetooth() {

    }

    public void loadHome(){
        getActionBar().show();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, homeFragment, HomeFragment.class.getName());
        ft.commit();

        Log.e("HCare :: loadHome", "Connected");
    }



    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

}