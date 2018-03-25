package elmajdma.movie.ui;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.List;

import elmajdma.movie.R;
import elmajdma.movie.data.local.Movie;
import elmajdma.movie.utils.InternetCheck;
import elmajdma.movie.utils.MovieRecylcerViewAdapter;
import elmajdma.movie.viewmodel.MoviesViewModel;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {
    public final static String MENU_SELECTED = "selected";
    public final static String MOVIE_ID = "movie_id";
    private static final String TOP_RATED = "top_rated";
    private static final String POPULAR = "popular";
    private String selectedCategory = null;
    private MoviesViewModel viewModel;
    private InternetCheck internetCheck;
    private Drawer result = null;
    private AccountHeader headerResult = null;
    private Observer<List<Movie>> moviesObserver;
    private Toolbar mMovieToolbar;
    private RecyclerView moviesRecyclerView;
    private MovieRecylcerViewAdapter movieRecylcerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(mMovieToolbar);
        mMovieToolbar.hideOverflowMenu();
        //viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        setDrawer(savedInstanceState);
        /*internetCheck=new InternetCheck(new InternetCheck.Consumer() {
            @Override
            public void accept(Boolean internet) {
                        if(!internet){
                            moviesRecyclerView.setVisibility(View.INVISIBLE);
                            mMovieToolbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),getString(R.string.no_internet),Toast.LENGTH_LONG).show();


                        }else{
                            if (savedInstanceState == null) {
                                getMovieData(POPULAR);
                            }


                }
            }
        });*/
        //setmHomeFragment();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (selectedCategory == null) {
            outState.putString(MENU_SELECTED, POPULAR);
        } else {
            outState.putString(MENU_SELECTED, selectedCategory);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        selectedCategory = savedInstanceState.getString(MENU_SELECTED);
        //getMovieData(selectedCategory);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void setUpMoviesRecyclerView(List<Movie> movieList, String category) {
        MovieRecylcerViewAdapter.MovieAdapterOnClickHandler mListener = new MovieRecylcerViewAdapter.MovieAdapterOnClickHandler() {
            @Override
            public void onMovieClick(int position, View v) {
                Movie movie = movieList.get(position);
                movieDetailes(movie.getId());

            }
        };
        switch (category) {
            case POPULAR:
                movieRecylcerViewAdapter = new MovieRecylcerViewAdapter(getApplicationContext(), movieList, MovieRecylcerViewAdapter.MOVIE_POPULAR_VIEW, mListener);
                moviesRecyclerView.setAdapter(movieRecylcerViewAdapter);
                break;
            case TOP_RATED:
                movieRecylcerViewAdapter = new MovieRecylcerViewAdapter(getApplicationContext(), movieList, MovieRecylcerViewAdapter.MOVIE_TOP_RATED_VIEW, mListener);
                moviesRecyclerView.setAdapter(movieRecylcerViewAdapter);
                break;
        }


    }

    private void initViews() {
       /* moviesRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        int mNoOfColumns = CalcFittedColumn.calculateNoOfColumns(getApplicationContext());
        GridLayoutManager layoutManager
                = new GridLayoutManager(getApplicationContext(), mNoOfColumns);
        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesRecyclerView.setHasFixedSize(true);*/
        mMovieToolbar = findViewById(R.id.toolbar_movie);
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
                selectedCategory = POPULAR;
                //getMovieData(selectedCategory);
                setRequestedMovieList(selectedCategory);
                return true;
            case R.id.top_rated:
                selectedCategory = TOP_RATED;
                //getMovieData(selectedCategory);
                setRequestedMovieList(selectedCategory);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getMovieData(String movieCategory) {
        viewModel.getmoviesLiveData(movieCategory).observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                setUpMoviesRecyclerView(movies, movieCategory);
            }
        });
    }

    public void movieDetailes(int movieId) {
        Intent details = new Intent(this, MovieDetailsActivity.class);
        details.putExtra(MOVIE_ID, movieId);
        MainActivity.this.startActivity(details);
    }

    private void setmHomeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_loader, new MoviesListFragment()).commit();

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
        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(getDrawerHeader(savedInstanceState))
                .withToolbar(mMovieToolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_movies).withIcon(GoogleMaterial.Icon.gmd_watch).withIdentifier(2).withSelectable(false),
                       new PrimaryDrawerItem().withName(R.string.drawer_item_movies).withIcon(GoogleMaterial.Icon.gmd_screen_lock_landscape).withIdentifier(3).withSelectable(false),
                        ///new PrimaryDrawerItem().withName(R.string.drawer_item_non_translucent_status_drawer).withDescription(R.string.drawer_item_non_translucent_status_drawer_desc).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                       /// new PrimaryDrawerItem().withName(R.string.drawer_item_advanced_drawer).withDescription(R.string.drawer_item_advanced_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5).withSelectable(false),
                       // new PrimaryDrawerItem().withName(R.string.drawer_item_embedded_drawer).withDescription(R.string.drawer_item_embedded_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_battery_full).withIdentifier(7).withSelectable(false),
                        //new PrimaryDrawerItem().withName(R.string.drawer_item_fullscreen_drawer).withDescription(R.string.drawer_item_fullscreen_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_label).withIdentifier(8).withSelectable(false),
                        //new PrimaryDrawerItem().withName(R.string.drawer_item_custom_container_drawer).withDescription(R.string.drawer_item_custom_container_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_my_location).withIdentifier(9).withSelectable(false),
                       // new PrimaryDrawerItem().withName(R.string.drawer_item_menu_drawer).withDescription(R.string.drawer_item_menu_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_filter_list).withIdentifier(10).withSelectable(false),
                       // new PrimaryDrawerItem().withName(R.string.drawer_item_mini_drawer).withDescription(R.string.drawer_item_mini_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_battery_charging_full).withIdentifier(11).withSelectable(false),
                        //new PrimaryDrawerItem().withName(R.string.drawer_item_fragment_drawer).withDescription(R.string.drawer_item_fragment_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_disc_full).withIdentifier(12).withSelectable(false),
                        //new PrimaryDrawerItem().withName(R.string.drawer_item_collapsing_toolbar_drawer).withDescription(R.string.drawer_item_collapsing_toolbar_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_camera_rear).withIdentifier(13).withSelectable(false),
                       // new PrimaryDrawerItem().withName(R.string.drawer_item_persistent_compact_header).withDescription(R.string.drawer_item_persistent_compact_header_desc).withIcon(GoogleMaterial.Icon.gmd_brightness_5).withIdentifier(14).withSelectable(false),
                       // new PrimaryDrawerItem().withName(R.string.drawer_item_crossfade_drawer_layout_drawer).withDescription(R.string.drawer_item_crossfade_drawer_layout_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_format_bold).withIdentifier(15).withSelectable(false),

                        //new ExpandableBadgeDrawerItem().withName("Movies Test").withIcon(GoogleMaterial.Icon.gmd_format_bold).withIdentifier(3).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)).withBadge("100").withSubItems(
                        //new SecondaryDrawerItem().withName("CollapsableItem").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_ac_unit).withIdentifier(2000),
                              //  new SecondaryDrawerItem().withName("CollapsableItem 2").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_format_bold).withIdentifier(2001)
                      //  ),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header_all_entrainment),
                        new ExpandableDrawerItem().withName(R.string.drawer_item_movies).withIcon(FontAwesome.Icon.faw_film).withIdentifier(20).withSelectable(false).withSubItems(
                                new SecondaryDrawerItem().withName(R.string.drawer_item_movies_top_rated).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_poll).withIdentifier(2001),
                                new SecondaryDrawerItem().withName(R.string.drawer_item_movies_popular).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_whatshot).withIdentifier(2002)
                        )

                        /*new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github).withIdentifier(20).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withIdentifier(21).withTag("Bullhorn")*/
                        /*,
                        new DividerDrawerItem(),
                        new SwitchDrawerItem().withName("Switch").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new SwitchDrawerItem().withName("Switch2").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener).withSelectable(false),
                        new ToggleDrawerItem().withName("Toggle").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new DividerDrawerItem(),
                        new SecondarySwitchDrawerItem().withName("Secondary switch").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new SecondarySwitchDrawerItem().withName("Secondary Switch2").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener).withSelectable(false),
                        new SecondaryToggleDrawerItem().withName("Secondary toggle").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)
                        */
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
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
                            } /*else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(DrawerActivity.this, MultiDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(DrawerActivity.this, NonTranslucentDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(DrawerActivity.this, AdvancedActivity.class);
                            } else if (drawerItem.getIdentifier() == 7) {
                                intent = new Intent(DrawerActivity.this, EmbeddedDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 8) {
                                intent = new Intent(DrawerActivity.this, FullscreenDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 9) {
                                intent = new Intent(DrawerActivity.this, CustomContainerActivity.class);
                            } else if (drawerItem.getIdentifier() == 10) {
                                intent = new Intent(DrawerActivity.this, MenuDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 11) {
                                intent = new Intent(DrawerActivity.this, MiniDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 12) {
                                intent = new Intent(DrawerActivity.this, FragmentActivity.class);
                            } else if (drawerItem.getIdentifier() == 13) {
                                intent = new Intent(DrawerActivity.this, CollapsingToolbarActivity.class);
                            } else if (drawerItem.getIdentifier() == 14) {
                                intent = new Intent(DrawerActivity.this, PersistentDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 15) {
                                intent = new Intent(DrawerActivity.this, CrossfadeDrawerLayoutActvitiy.class);
                            } else if (drawerItem.getIdentifier() == 20) {
                                intent = new LibsBuilder()
                                        .withFields(R.string.class.getFields())
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                        .intent(DrawerActivity.this);
                            }
                            if (intent != null) {
                                DrawerActivity.this.startActivity(intent);
                            }*/
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
//              .withShowDrawerUntilDraggedOpened(true)
                .build();
    }

    private AccountHeader getDrawerHeader(Bundle savedInstanceState){
        // Create the AccountHeader
        //headerResult =
                return  new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.material_drawer_badge)
                /*.addProfiles(
                        profile,
                        profile2,
                        profile3,
                        profile4,
                        profile5,
                        profile6,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBar().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(100001)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && profile.getIdentifier() == PROFILE_SETTING) {
                            int count = 100 + headerResult.getProfiles().size() + 1;
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman" + count).withEmail("batman" + count + "@gmail.com").withIcon(R.drawable.profile5).withIdentifier(count);
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })*/
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

}

