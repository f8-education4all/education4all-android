package com.f82019.education4all;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.f82019.education4all.adapters.AdapterWord;
import com.f82019.education4all.dialogs.DialogWord;
import com.f82019.education4all.models.Dictionary;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }


    static final int LOGIN_ACT = 123;
    private AccessTokenTracker accessTokenTracker;

    ImageView profile_img;
    TextView tv_name, tv_email;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profile_img = getView().findViewById(R.id.img_frag_profil);
        tv_name = getView().findViewById(R.id.tv_frag_profile_name);
        tv_email = getView().findViewById(R.id.tv_frag_profil_email);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                // currentAccessToken is null if the user is logged out
                if (currentAccessToken != null) {
                    // AccessToken is not null implies user is logged in and hence we sen the GraphRequest
                    useLoginInformation(currentAccessToken);
                }
            }
        };


        List<Dictionary> words = new ArrayList<>();
        words.add(new Dictionary("Sofa", "A place where people can seat", null));
        words.add(new Dictionary("cup", "1. A small vessel, used commonly to drink from; as, a tin cup, a silver cup, a wine cup; especially, in modern times, the pottery or porcelain vessel, commonly with a handle, used with a saucer in drinking tea, coffee, and the like.", null));

        AdapterWord adapter = new AdapterWord(getContext(), android.R.layout.simple_list_item_1, words);
        ((GridView) getView().findViewById(R.id.grid_profil_words))
                .setAdapter(adapter);

        ((GridView) getView().findViewById(R.id.grid_profil_words))
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Dictionary selectedWord = (Dictionary) parent.getItemAtPosition(position);
                        DialogWord dialog = DialogWord.newInstance(selectedWord.getWord(), selectedWord.getDescription());
                        dialog.show(getFragmentManager(), "WORD_DETAILS");
                    }
                });


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
                        Picasso.with(getContext())
                                .load(image)
                                .resize(150, 150)
                                .transform(new RoundedCornersTransformation(90, 1))
                                .centerCrop().into(profile_img);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    tv_name.setText(name);
                    tv_email.setText(email);


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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
