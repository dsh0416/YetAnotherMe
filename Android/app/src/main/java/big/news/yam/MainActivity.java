package big.news.yam;

import android.app.Fragment;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity {

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public LinearLayout drawerRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerRootLayout = (LinearLayout) findViewById(R.id.drawerRootLayout);


        // 设置 Toolbar 替代系统 ActionBar，并在 Android 5.0+ 赋予 elevation = 5 的参数以符合 Material Design
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(android.os.Build.VERSION.SDK_INT>=21){
            toolbar.setElevation(5);
        }


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);


        // Set Status bar and Nav bar to be translucent in KitKat+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = getWindow();

            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            toolbar.setPadding(0, result, 0, 0);
            drawerRootLayout.setPadding(0,result,0,0);
        }

        changeFragment(new NotificationCenterFragment());

    }

    public void changeFragment(Fragment fragment){
        // 切换 Fragment 前执行一次垃圾回收
        System.gc();
        /*
        Bundle args = new Bundle();
        args.putString("user_id",user_id);
        args.putString("mode",mode);
        fragment.setArguments(args);
        */
        getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment).commit();
        drawerLayout.closeDrawer(drawerRootLayout);

    }

}
