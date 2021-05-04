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
import android.widget.Toast;

import cat.itb.tanderapp.R;
import cat.itb.tanderapp.activities.MainActivity;

public class SettingsFragment extends Fragment {

    Button logoutButton;
    WelcomeScreen welcomeScreen;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        logoutButton = v.findViewById(R.id.log_out_button);
        welcomeScreen = new WelcomeScreen();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.firebaseAuth.signOut();
                int count = getFragmentManager().getBackStackEntryCount();
                for(int i = 0; i < count; i++){
                    getParentFragmentManager().popBackStack();
                }
                replaceFragment(welcomeScreen);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.topAppBar.setVisibility(View.INVISIBLE);
    }

    private void replaceFragment(Fragment fragment){
        try{
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
        }
    }
}