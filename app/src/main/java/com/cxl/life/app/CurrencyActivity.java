package com.cxl.life.app;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.life.R;
import com.cxl.life.util.AUtil;
import com.kyleduo.switchbutton.SwitchButton;

/**
 * @author cxl
 *         通用设置
 */
public class CurrencyActivity extends BaseActivity implements View.OnClickListener {
    private SwitchButton bluetooth,wifi;//wifi控制开关
    private TextView wifiText,bluetoothText;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        initView();
        initData();
    }

    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.currency_toolbar);
        setSupportActionBar(toolbar);

        bluetooth = (SwitchButton) findViewById(R.id.currency_bluetooth);
        bluetoothText = (TextView) findViewById(R.id.currency_bluetooth_text);
        wifi = (SwitchButton) findViewById(R.id.currency_wifi);
        wifiText = (TextView) findViewById(R.id.currency_wifi_text);
    }

    private void initData() {
        //蓝牙块
        if (!AUtil.isSupportBluetooth()) {
            Toast.makeText(this, "未发现蓝牙硬件或驱动", Toast.LENGTH_SHORT).show();
            bluetooth.setEnabled(false);
        }
        if(AUtil.bluetoothIsOpen()) {
            bluetooth.setChecked(true);
            bluetoothText.setText("关闭蓝牙功能");
        }
        bluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!AUtil.bluetoothIsOpen()) {
                        //调用系统弹框，尝试打开蓝牙
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, 101);
                    }
                } else {
                    if (AUtil.bluetoothIsOpen()) {
                        //关闭蓝牙
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        mBluetoothAdapter.disable();
                        bluetoothText.setText("开启蓝牙功能");
                    }
                }
            }
        });
        //WIFI块
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()) {
            wifi.setChecked(true);
            wifiText.setText("关闭WIFI功能");
        }
        //TODO 缺少打开失败与成功的回调
        wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!wifiManager.isWifiEnabled()) {
                        wifiManager.setWifiEnabled(true);
                        wifiText.setText("关闭WIFI功能");
                    }
                } else {
                    if (wifiManager.isWifiEnabled()) {
                        wifiManager.setWifiEnabled(false);
                        wifiText.setText("开启WIFI功能");
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "蓝牙打开成功", Toast.LENGTH_SHORT).show();
                bluetoothText.setText("关闭蓝牙功能");
            } else {
                Toast.makeText(this, "蓝牙打开失败", Toast.LENGTH_SHORT).show();
                bluetooth.setChecked(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            default:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_app:

                break;
        }
    }
}
