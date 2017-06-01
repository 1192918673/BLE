package com.startimes.myble;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.startimes.myble.util.BleUtil;

public class MainActivity extends AppCompatActivity {

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private String TAG = "MainActivity";
    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeAdvertiser mBtAdvertiser;
    private BluetoothManager mBluetoothManager;
    private BlGattServerCallback gaCallback;
    private BluetoothGattServer mGattServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        if (mBluetoothAdapter != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startAdvertise();
            }
        }
    }

    private void init() {
        this.mContext = getApplicationContext();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    /**
     * 开启外围设备模式
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void startAdvertise() {
        if (!BleUtil.isBLESupported(mContext) || !mBluetoothAdapter.isMultipleAdvertisementSupported()) {
            Log.d(TAG, "is not support ble");
            return;
        }

        if (mBtAdvertiser != null) {
            gaCallback = new BlGattServerCallback(mHandler);
            mGattServer = mBluetoothManager.openGattServer(mContext, gaCallback);
//            gaCallback.setupServices(mGattServer);

            mBluetoothAdapter.getBluetoothLeAdvertiser();
        }
    }

    /**
     * GattServerCallback
     */
    class BlGattServerCallback extends BluetoothGattServerCallback {

        public BlGattServerCallback(Handler mHandler) {

        }

        public void setupServices(BluetoothGattServer mGattServer) {

        }
    }
}
