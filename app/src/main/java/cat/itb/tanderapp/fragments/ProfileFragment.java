package cat.itb.tanderapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cat.itb.tanderapp.R;
import cat.itb.tanderapp.helper.DatabaseHelper;
import cat.itb.tanderapp.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

import static cat.itb.tanderapp.activities.MainActivity.firebaseAuth;
import static cat.itb.tanderapp.activities.MainActivity.currentUser;
import static cat.itb.tanderapp.activities.MainActivity.topAppBar;


public class ProfileFragment extends Fragment {

    CircleImageView profileImage;
    TextView profileUsername;
    FloatingActionButton edit_profile_button, settings_button;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        edit_profile_button = v.findViewById(R.id.profile_edit);
        settings_button = v.findViewById(R.id.profile_settings);
        profileImage = v.findViewById(R.id.profile_image);
        profileUsername = v.findViewById(R.id.profile_username);
        profileUsername.setText(currentUser.getName() + ", " + currentUser.getAge());

        if (currentUser.getUrls().get(0).equals("")) {

            Glide.with(profileImage).load(R.drawable.blank_profile_image_jpeg).into(profileImage);
        } else {
            Glide.with(profileImage).load(currentUser.getUrls().get(0)).into(profileImage);
        }

        edit_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditInfoFragment();
                try{
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        });

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SettingsFragment();
                try{
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        });
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topAppBar.setVisibility(View.VISIBLE);
    }
}