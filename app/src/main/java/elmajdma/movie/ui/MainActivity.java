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
    private static final String HOME = "home";
    private static final String FAVORITE = "favorite";
    private String movieCategory = null;
    private Drawer result = null;
    private InternetCheck internetCheck;
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

        if (savedInstanceState == null) {
            setrequestedFragment(TOP_RATED);
        }
    }

    private void initViews() {
        mMovieToolbar = findViewById(R.id.toolbar_movie);

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
                // movieCategory = POPULAR;
                setrequestedFragment(POPULAR);
                return true;
            case R.id.top_rated:
                // movieCategory=TOP_RATED;
                setrequestedFragment(TOP_RATED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                    if (drawerItem.getIdentifier() == 1) {
                        movieCategory = HOME;
                        setrequestedFragment(TOP_RATED);
                    } else if (drawerItem.getIdentifier() == 2001) {
                        movieCategory = TOP_RATED;
                        setrequestedFragment(TOP_RATED);
                    } else if (drawerItem.getIdentifier() == 2002) {
                        movieCategory = POPULAR;
                        setrequestedFragment(POPULAR);
                    } else if (drawerItem.getIdentifier() == 4) {
                        //savedInstanceState.putString(MENU_SELECTED,FAVORITE);
                        //setRequestedMovieList(FAVORITE);
                        movieCategory = FAVORITE;
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

    private void setrequestedFragment(String categorySelected) {
        internetCheck = new InternetCheck(new InternetCheck.Consumer() {
            @Override
            public void accept(Boolean internet) {

                if (!internet) {
                    Fragment fragment = new NoInternetConnectionFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_loader, fragment);
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.commit();

                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(MENU_SELECTED, categorySelected);
                    MoviesListFragment moviesListFragment = new MoviesListFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    moviesListFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_loader, moviesListFragment);
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }

            }
        });

    }

}

