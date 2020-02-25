package com.example.packageslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "PackageActivity";

    private TextView mPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        mPackages = findViewById(R.id.packages);
        displayApps();
        return;
    }

    private void displayApps() {
        Log.d(TAG, "displayApps()");
        Packages pkg = new Packages();
        ArrayList<PInfo> apps = pkg.getPackages();
        String text = "";
        final int max = apps.size();
        for (int i=0; i<max; i++) {
            PInfo p = apps.get(i);
            text += p.appname + "\t" + p.pname + "\n";
        }
        mPackages.setText(text);
        return;
    }

    public class Packages {

        private ArrayList<PInfo> getPackages() {
            ArrayList<PInfo> apps = getInstalledApps(false);
            /* false = no system packages */
            final int max = apps.size();
            for (int i=0; i<max; i++) {
                apps.get(i).prettyPrint();
            }
            return apps;
        }

        private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
            ArrayList<PInfo> res = new ArrayList<PInfo>();
            List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
            for(int i=0;i<packs.size();i++) {
                PackageInfo p = packs.get(i);
                if ((!getSysPackages) && (p.versionName == null)) {
                    continue ;
                }
                PInfo newInfo = new PInfo();
                newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
                newInfo.pname = p.packageName;
                newInfo.versionName = p.versionName;
                newInfo.versionCode = p.versionCode;
                newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());
                res.add(newInfo);
            }
            return res;
        }

    }

    class PInfo {
        private String appname = "";
        private String pname = "";
        private String versionName = "";
        private int versionCode = 0;
        private Drawable icon;

        private void prettyPrint() {
            Log.d(TAG, appname + "\t" + pname + "\t" + versionName + "\t" + versionCode);
        }
    }

}
