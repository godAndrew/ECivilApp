package br.almadaapps.civilapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.almadaapps.civilapp.domains.User;
import br.almadaapps.civilapp.fragments.CalendarFragment;
import br.almadaapps.civilapp.fragments.LinksFragment;
import br.almadaapps.civilapp.fragments.MonographsFragment;
import br.almadaapps.civilapp.fragments.NewsFragment;
import br.almadaapps.civilapp.fragments.ScheduleFragment;
import br.almadaapps.civilapp.fragments.SyllabusFragment;
import br.almadaapps.civilapp.utils.GeneralMethods;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    public static final String TAG = "HomeActivity";
    public static final int[] TAB_ICONS =
            {
                    R.drawable.ic_news,
                    R.drawable.ic_clock,
                    R.drawable.ic_description,
                    R.drawable.ic_school,
                    R.drawable.ic_calendar};


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private User userLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl_main_content);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nv_home);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.drawer_news);
        navigationView.setItemTextColor(ContextCompat.getColorStateList(this, R.color.drawer_color));
        navigationView.setItemIconTintList(ContextCompat.getColorStateList(this, R.color.drawer_color));
        navigationView.setBackgroundColor(0xFFFFFFFF);
        initUser(navigationView.getHeaderView(0));

        drawer.openDrawer(Gravity.START, true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setHorizontalScrollBarEnabled(false);
        mTabLayout.setHorizontalFadingEdgeEnabled(true);
//        setupTabIcons();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.dl_main_content);
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            finish();
    }

    public User getUserLogged() {
        return userLogged;
    }

    public void setUserLogged(User userLogged) {
        this.userLogged = userLogged;
    }

    private void initUser(View header) {
        TextView tvName = (TextView) header.findViewById(R.id.tv_name_header);
        TextView tvEmail = (TextView) header.findViewById(R.id.tv_email_header);
        TextView tvCode = (TextView) header.findViewById(R.id.tv_code_header);
        CircleImageView ivProfile = (CircleImageView) header.findViewById(R.id.civ_profile_header);

        User user = getIntent().getParcelableExtra(LoginActivity.KEY_USER_PARCELABLE);
        setUserLogged(user);
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvCode.setText(user.getCode());
        Picasso.with(this).load(user.getProfilePic()).into(ivProfile);
    }

    /*private void setupTabIcons() {
        if (mTabLayout != null) {
            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                mTabLayout.getTabAt(i).setIcon(TAB_ICONS[i]);
            }
        }
    }*/


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
//            FirebaseAuth.getInstance().signOut();
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_news:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.drawer_timetable:
                mViewPager.setCurrentItem(1, true);
                break;
            case R.id.drawer_syllabus:
                mViewPager.setCurrentItem(2, true);
                break;
            case R.id.drawer_monograph:
                mViewPager.setCurrentItem(3, true);
                break;
            case R.id.drawer_calendar:
                mViewPager.setCurrentItem(4, true);
                break;
            case R.id.drawer_links:
                mViewPager.setCurrentItem(5, true);
                break;
            case R.id.drawer_signout:
                GeneralMethods.signOutFinish(this, LoginActivity.class);
                finish();
                break;
            case R.id.drawer_individual:
                Intent intent = new Intent(this, IndividualScheduleActivity.class);
                intent.putExtra(LoginActivity.KEY_USER_PARCELABLE, userLogged);
                startActivity(intent);
                break;
            case R.id.drawer_feedback:
                Intent feedbackIntent = new Intent(Intent.ACTION_SENDTO);
                feedbackIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
                feedbackIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"vinicius.almada@acad.ifma.edu.br"});
                feedbackIntent.putExtra(Intent.EXTRA_SUBJECT, "Sugestões e dúvidas sobre o ECivil App");
                startActivity(feedbackIntent);
                break;
            case R.id.drawer_info:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(R.string.sobreApp);
                alert.setMessage(R.string.message_about_app);
                alert.setNeutralButton(android.R.string.ok, null);
                alert.show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl_main_content);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageScrolled: position=" + position);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nv_home);
        switch (position) {
            case 0:
                navigationView.setCheckedItem(R.id.drawer_news);
                break;
            case 1:
                navigationView.setCheckedItem(R.id.drawer_timetable);
                break;
            case 2:
                navigationView.setCheckedItem(R.id.drawer_syllabus);
                break;
            case 3:
                navigationView.setCheckedItem(R.id.drawer_monograph);
                break;
            case 4:
                navigationView.setCheckedItem(R.id.drawer_calendar);
                break;
            case 5:
                navigationView.setCheckedItem(R.id.drawer_links);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES_FRAGMENT = new String[]{
                getString(R.string.news),
                getString(R.string.timetable),
                getString(R.string.syllabus),
                getString(R.string.monographies),
                getString(R.string.calendar),
                getString(R.string.links)
        };

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                return new NewsFragment();
            } else if (position == 1) {
                return new ScheduleFragment();
            } else if (position == 2) {
                return new SyllabusFragment();
            } else if (position == 3) {
                return new MonographsFragment();
            } else if (position == 4) {
                return new CalendarFragment();
            } else if (position == 5) {
                return new LinksFragment();
            } else {
                return new Fragment();
            }
        }

        @Override
        public int getCount() {
            return TITLES_FRAGMENT.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES_FRAGMENT[position];
        }
    }
}