package elmajdma.movie.ui;

import android.arch.lifecycle.LifecycleObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import elmajdma.movie.R;
import elmajdma.movie.utils.InternetCheck;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {
    public final static String MENU_SELECTED = "selected";
    private static final String TOP_RATED = "top_rated";
    private static final String POPULAR = "popular";
    private Drawer result = null;
    private Toolbar mMovieToolbar;
    private FrameLayout fragmentLoader;
    private TextView noInternetMassage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(mMovieToolbar);
        mMovieToolbar.hideOverflowMenu();
        setDrawer(savedInstanceState);
        InternetCheck internetCheck = new InternetCheck(new InternetCheck.Consumer() {
            @Override
            public void accept(Boolean internet) {
                if (!internet) {
                    fragmentLoader.setVisibility(View.INVISIBLE);
                    noInternetMassage.setText(R.string.no_internet);
                    noInternetMassage.setVisibility(View.VISIBLE);
                } else {
                    if (savedInstanceState == null) {
                        //this may appear redundant I wil develop it later to show tv or combination of tv +movie
                        setRequestedMovieList(TOP_RATED);
                    }
                }
            }
        });


    }

    private void initViews() {
        mMovieToolbar = findViewById(R.id.toolbar_movie);
        noInternetMassage = findViewById(R.id.tv_no_iternet_connection);
        noInternetMassage.setVisibility(View.GONE);
        fragmentLoader = findViewById(R.id.fragment_loader);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.popular:
                //selectedCategory = POPULAR;
                setRequestedMovieList(POPULAR);
                return true;
            case R.id.top_rated:

                setRequestedMovieList(TOP_RATED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void setRequestedMovieList(String category) {
        Bundle bundle = new Bundle();
        bundle.putString(MENU_SELECTED, category);
        MoviesListFragment moviesListFragment = new MoviesListFragment();
        moviesListFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_loader, moviesListFragment).commit();
    }

    private void setDrawer(Bundle savedInstanceState) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        //PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home);
        //SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_settings);
        //create the drawer and remember the `Drawer` result object
        DrawerBuilder drawerBuilder = new DrawerBuilder();
        drawerBuilder.withActivity(this);
        drawerBuilder.withAccountHeader(getDrawerHeader(savedInstanceState));
        drawerBuilder.withToolbar(mMovieToolbar);
        drawerBuilder.addDrawerItems(
                new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1).withSelectable(false),
                new PrimaryDrawerItem().withName(R.string.drawer_item_favoirte).withIcon(GoogleMaterial.Icon.gmd_favorite).withIdentifier(4).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                new SectionDrawerItem().withName(R.string.drawer_item_section_header_all_entrainment),
                new ExpandableDrawerItem().withName(R.string.drawer_item_movies).withIcon(FontAwesome.Icon.faw_film).withIdentifier(20).withSelectable(false).withSubItems(
                        new SecondaryDrawerItem().withName(R.string.drawer_item_movies_top_rated).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_poll).withIdentifier(2001),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_movies_popular).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_whatshot).withIdentifier(2002)
                ));
        drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                //check if the drawerItem is set.
                //there are different reasons for the drawerItem to be null
                //--> click on the header
                //--> click on the footer
                //those items don't contain a drawerItem

                if (drawerItem != null) {

                    if (drawerItem.getIdentifier() == 2001) {
                        setRequestedMovieList(TOP_RATED);
                    } else if (drawerItem.getIdentifier() == 2002) {
                        setRequestedMovieList(POPULAR);
                    } else if (drawerItem.getIdentifier() == 4) {
                        setFavoirteFragment();
                    }
                }

                return false;
            }
        });
        drawerBuilder.withSavedInstance(savedInstanceState);
        drawerBuilder.withShowDrawerOnFirstLaunch(true);
        drawerBuilder.withCloseOnClick(true);
        drawerBuilder.build();
    }

    private AccountHeader getDrawerHeader(Bundle savedInstanceState){
        // Create the AccountHeader in the future I will implement this section
        //headerResult =
                return  new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.material_drawer_badge)
                .withSavedInstance(savedInstanceState)
                .build();

    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


    private void setFavoirteFragment() {
        Fragment fragment = new FavoriteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_loader, fragment);
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}

