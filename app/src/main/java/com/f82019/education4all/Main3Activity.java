package com.f82019.education4all;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.f82019.education4all.camera.Camera2BasicFragment;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class Main3Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final int LOGIN_ACT = 123;
    private AccessTokenTracker accessTokenTracker;

    ImageView profile_img;
    TextView profile_name, profile_email;

    MenuItem nav_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                // currentAccessToken is null if the user is logged out
                if (currentAccessToken != null) {
                    // AccessToken is not null implies user is logged in and hence we sen the GraphRequest
                    useLoginInformation(currentAccessToken);
                }else{
                    Intent intent = new Intent(Main3Activity.this, LoginActivity.class);
                    startActivityForResult(intent, LOGIN_ACT);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                // currentAccessToken is null if the user is logged out
                if (currentAccessToken != null) {
                    // AccessToken is not null implies user is logged in and hence we sen the GraphRequest
                    useLoginInformation(currentAccessToken);
                }else{
                    Intent intent = new Intent(Main3Activity.this, LoginActivity.class);
                    startActivityForResult(intent, LOGIN_ACT);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                // currentAccessToken is null if the user is logged out
                if (currentAccessToken != null) {
                    // AccessToken is not null implies user is logged in and hence we sen the GraphRequest
                    useLoginInformation(currentAccessToken);
                }else{
                    Intent intent = new Intent(Main3Activity.this, LoginActivity.class);
                    startActivityForResult(intent, LOGIN_ACT);
                }
            }
        };



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (navigationView.getHeaderView(0) != null) {
            profile_img = navigationView.getHeaderView(0).findViewById(R.id.imageView_profil);
            profile_name = navigationView.getHeaderView(0).findViewById(R.id.tv_profile_name);
            profile_email = navigationView.getHeaderView(0).findViewById(R.id.tv_profil_email);

            navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_container3, ProfileFragment.newInstance())
                            .commit();

                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                }
            });
        }

        nav_login  = navigationView.getMenu().findItem(R.id.nav_login);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                // currentAccessToken is null if the user is logged out
                if (currentAccessToken != null) {
                    // AccessToken is not null implies user is logged in and hence we sen the GraphRequest
                    nav_login.setTitle("Sign out");
                    useLoginInformation(currentAccessToken);
                } else {
                    //Intent intent = new Intent(Main3Activity.this, LoginActivity.class);
                    //startActivityForResult(intent, LOGIN_ACT);
                    profile_name.setText("Your name");
                    profile_email.setText("Your email");
                    profile_img.setImageResource(R.mipmap.ic_launcher);
                    nav_login.setTitle("Sign in");
                }
            }
        };

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container3, new Camera2BasicFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.nav_login:
                if(AccessToken.getCurrentAccessToken() == null){
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, LOGIN_ACT);
                }else {
                    LoginManager.getInstance().logOut();
                }

                break;
            case R.id.nav_object_detection:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container3, new Camera2BasicFragment())
                        .commit();
                break;
            case R.id.nav_learn_to_write:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container3, (Fragment) Learn2WriteFragment.newInstance())
                        .commit();
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_ACT) {
            Toast.makeText(this, "Login finished!", Toast.LENGTH_LONG).show();
        }
    }

    private void useLoginInformation(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("email");
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");

                    try {
                        Picasso.with(Main3Activity.this)
                                .load(image)
                                .resize(150, 150)
                                .transform(new RoundedCornersTransformation(90, 1))
                                .centerCrop().into(profile_img);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    profile_name.setText(name);
                    profile_email.setText(email);
                    nav_login.setTitle("Sign out");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }

    @Override
    public void onStart() {
        super.onStart();
        //This starts the access token tracking
        accessTokenTracker.startTracking();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        useLoginInformation(accessToken);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // We stop the tracking before destroying the activity
        accessTokenTracker.stopTracking();
    }
}
