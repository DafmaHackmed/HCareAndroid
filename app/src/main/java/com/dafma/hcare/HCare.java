package com.dafma.hcare;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.empatica.empalink.ConnectionNotAllowedException;
import com.empatica.empalink.EmpaDeviceManager;
import com.empatica.empalink.config.EmpaSensorStatus;
import com.empatica.empalink.config.EmpaSensorType;
import com.empatica.empalink.config.EmpaStatus;
import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.empatica.empalink.delegate.EmpaStatusDelegate;


public class HCare extends Activity implements EmpaDataDelegate, EmpaStatusDelegate {

    private EmpaDeviceManager empatica;


    private void initComponents(){
        Log.d("HCare :: initComponents :: v", "start init");
        empatica = new EmpaDeviceManager(this, this, this);
        empatica.authenticateWithAPIKey("fdfe03151260495f854a9338d115d6b2");
        Log.d("HCare :: initComponents :: v", "end init");
    }

    private void initUI(){
        Log.d("HCare :: initUI :: v", "start init");
        Log.d("HCare :: initUI :: v", "end init");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcare);

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
        Log.d("HCare :: didReceiveGSR :: v", v + "");
        Log.d("HCare :: didReceiveGSR :: v2", v + "2");

    }

    @Override
    public void didReceiveBVP(float v, double v2) {
        Log.d("HCare :: didReceiveBVP :: v", v + "");
        Log.d("HCare :: didReceiveBVP :: v2", v + "2");

    }

    @Override
    public void didReceiveIBI(float v, double v2) {
        Log.d("HCare :: didReceiveIBI :: v", v + "");
        Log.d("HCare :: didReceiveIBI :: v2", v + "2");

    }

    @Override
    public void didReceiveTemperature(float v, double v2) {
        Log.d("HCare :: didReceiveTemperature :: v", v + "");
        Log.d("HCare :: didReceiveTemperature :: v2", v + "2");

    }

    @Override
    public void didReceiveAcceleration(int i, int i2, int i3, double v) {
        Log.d("HCare :: didReceiveAcceleration :: v", v + "");
        Log.d("HCare :: didReceiveAcceleration :: v2", v + "2");

    }

    @Override
    public void didReceiveBatteryLevel(float v, double v2) {
        Log.d("HCare :: didReceiveBatteryLevel :: v", v + "");
        Log.d("HCare :: didReceiveBatteryLevel :: v2", v + "2");

    }

    @Override
    public void didUpdateStatus(EmpaStatus empaStatus) {
        switch (empaStatus){
            case READY:
                Log.d("HCare :: didUpdateStatus :: empaStatus", "Ready");
                empatica.startScanning();
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
        Log.d("HCare :: didDiscoverDevice", "Disovered");

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
}
