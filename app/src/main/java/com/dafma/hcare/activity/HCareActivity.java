package com.dafma.hcare.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
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

import fragment.HomeFragment;
import fragment.SplashscreenFragment;


public class HCareActivity extends FragmentActivity implements EmpaDataDelegate, EmpaStatusDelegate {

    private EmpaDeviceManager empatica;
    private boolean connected;
    public static Fragment splashscreenFragment = new SplashscreenFragment();
    public static Fragment homeFragment = new HomeFragment();

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

        Log.e("HCare :: initComponents :: v", "end init");
    }

    private void initUI(){
        Log.e("HCare :: initUI :: v", "start init");
        setupActionBar();
        Log.e("HCare :: initUI :: v", "end init");

    }

    private void loadFragments(){

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, splashscreenFragment, SplashscreenFragment.class.getName());
        ft.show(splashscreenFragment);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcare);

        loadFragments();
        initComponents();
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hcare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        Log.e("HCare :: didDiscoverDevice", "Disovered");

        if (canConnect){
            empatica.stopScanning();
            try {
                empatica.connectDevice(device);
            } catch (ConnectionNotAllowedException e) {
                Log.e("HCare :: didDiscoverDevice", "Cannot connect");
            }
        }

    }

    @Override
    public void didRequestEnableBluetooth() {

    }

    public void loadHome(){
        getActionBar().show();
        getFragmentManager().beginTransaction().replace(R.id.container, homeFragment, HomeFragment.class.getName());
        getFragmentManager().beginTransaction().commit();

        Log.e("HCare :: loadHome", "Connected");
    }

}