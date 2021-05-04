package cat.itb.tanderapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cat.itb.tanderapp.R;
import cat.itb.tanderapp.activities.MainActivity;
import cat.itb.tanderapp.helper.DatabaseHelper;

import static cat.itb.tanderapp.activities.MainActivity.firebaseAuth;

public class EditInfoFragment extends Fragment {

    BottomNavigationView selectOption;
    public EditInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_info, container, false);
        selectOption = v.findViewById(R.id.top_app_edit_bar);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        Fragment fragment = new EditPhotoProfileFragment();
        transaction.replace(R.id.editInfoContent, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
        selectOption.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.edit_page:
                        DatabaseHelper.findByEmail(firebaseAuth.getCurrentUser().getEmail());
                        Fragment fragment = new EditPhotoProfileFragment();
                        transaction.replace(R.id.editInfoContent, fragment, fragment.getClass().getSimpleName());
                        transaction.commit();
                        return true;
                    case R.id.preview_page:
                        DatabaseHelper.findByEmail(firebaseAuth.getCurrentUser().getEmail());
                        Fragment fragment2 = new ViewProfileFragment();
                        transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.editInfoContent, fragment2, fragment2.getClass().getSimpleName());
                        transaction.commit();
                        return true;
                }
                return true;
            }

        });
        selectOption.setSelectedItemId(R.id.edit_page);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.topAppBar.setVisibility(View.INVISIBLE);
    }
}