package cat.itb.tanderapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cat.itb.tanderapp.R;
import cat.itb.tanderapp.activities.MainActivity;
import cat.itb.tanderapp.helper.DatabaseHelper;
import cat.itb.tanderapp.model.User;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;
import static cat.itb.tanderapp.activities.MainActivity.currentUser;
import static cat.itb.tanderapp.activities.MainActivity.firebaseAuth;
import static cat.itb.tanderapp.activities.MainActivity.topAppBar;

public class EditPhotoProfileFragment extends Fragment {

    CircleImageView profileImage;
    String nombreImagen;
    byte[] thumb_byte;
    Bitmap thumb_bitmap;
    File url;
    FloatingActionButton uploadImage;
    StorageReference storageReference;
    public static Uri downloadUri;
    String fotolink;
    Button saveButton;
    TextView textViewage;
    BottomNavigationView topAppBar;
    private com.google.android.material.slider.RangeSlider rangeAge;
    EditText description;
    Spinner spinnerGender, spinnerPreference;

    public EditPhotoProfileFragment() {
        // Required empty public constructor
    }


    public static EditPhotoProfileFragment newInstance(String param1, String param2) {
        EditPhotoProfileFragment fragment = new EditPhotoProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_profile_photo, container, false);
        profileImage = v.findViewById(R.id.profile_image);
        storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");
        uploadImage = v.findViewById(R.id.uploadButton);
        saveButton = v.findViewById(R.id.save_changes_button);
        spinnerGender = v.findViewById(R.id.spinner_gender);
        spinnerPreference = v.findViewById(R.id.spinner_preferences);
        description = v.findViewById(R.id.editTextAboutMe);
        rangeAge = v.findViewById(R.id.age_slider);
        textViewage = v.findViewById(R.id.textView_age);
        topAppBar = v.findViewById(R.id.top_app_edit_bar);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // CropImage.startPickImageActivity(getContext(), EditPhotoProfileFragment.this);
                uploadImage.setVisibility(View.VISIBLE);
            }
        });
        String getDescription = currentUser.getDescription();
        String getGender = currentUser.getGender();
        String getPreference = currentUser.getPreference();
        int getAgeMin = currentUser.getAgeMin();
        int getAgeMax = currentUser.getAgeMax();

        if (!currentUser.getUrls().get(0).equals("")) {
            Glide.with(profileImage).load(currentUser.getUrls().get(0)).into(profileImage);
        } else {
            Glide.with(profileImage).load(R.drawable.blank_profile_image_jpeg).into(profileImage);
        }

        if (getDescription.equals("")) {
            description.setText("");
        } else {
            description.setText(getDescription);
        }


        if (getGender.equals("Male")) {
            spinnerGender.setSelection(1);
        } else if (getGender.equals("Female")){
            spinnerGender.setSelection(2);
        }else if (getGender.equals("Other")) {
            spinnerGender.setSelection(3);
        }

        if (getPreference.equals("Male")) {
            spinnerPreference.setSelection(1);
        } else if (getPreference.equals("Female")){
            spinnerPreference.setSelection(2);
        }else if (getPreference.equals("Other")) {
            spinnerPreference.setSelection(3);
        }else if (getPreference.equals("All")) {
            spinnerPreference.setSelection(4);
        }

        textViewage.setText(getAgeMin + " - " + getAgeMax);
        rangeAge.setValues((float) getAgeMin, (float) getAgeMax);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprimirImagen();
                colgarImagen();
                System.out.println(fotolink);
                Toast.makeText(getContext(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show();

            }
        });
        

        rangeAge.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                textViewage.setText(rangeAge.getValues().get(0).intValue()+" - "+rangeAge.getValues().get(1).intValue());
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                User u = new User("key", spinnerGender.getSelectedItem().toString(), spinnerPreference.getSelectedItem().toString(), rangeAge.getValues().get(0).intValue(), rangeAge.getValues().get(1).intValue(), image);
                DatabaseHelper.setDescription(currentUser.getId(), description.getText().toString());
                DatabaseHelper.setGender(currentUser.getId(), spinnerGender.getSelectedItem().toString());
                DatabaseHelper.setPreference(currentUser.getId(), spinnerPreference.getSelectedItem().toString());
                DatabaseHelper.setAgeRange(currentUser.getId(), rangeAge.getValues().get(0).intValue(), rangeAge.getValues().get(1).intValue());
//                DatabaseHelper.setImage(currentUser.getId(), profileImage);
                DatabaseHelper.findByEmail(currentUser.getEmail());
                replaceFragment(new ProfileFragment());
            }
        });
        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK){
            Uri imageUri = CropImage.getPickImageResultUri(getContext(), data);
            recortarImagen(imageUri);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                url = new File(resultUri.getPath());
                Picasso.with(getContext()).load(url).into(profileImage);
            }
        }
    }

    private void recortarImagen(Uri imagenUri){
        CropImage.activity(imagenUri).setGuidelines(CropImageView.Guidelines.ON)
                .setRequestedSize(1080, 1920)
                .setAspectRatio(1,1).start(getContext(), EditPhotoProfileFragment.this);
    }


    private void comprimirImagen(){
        try {
            thumb_bitmap = new Compressor(getContext())
                    .setMaxHeight(1920)
                    .setMaxWidth(1080)
                    .setQuality(100)
                    .compressToBitmap(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        thumb_byte = byteArrayOutputStream.toByteArray();
    }

    private void colgarImagen(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        nombreImagen = timestamp + ".jpg";
        StorageReference ref = storageReference.child(nombreImagen);
        UploadTask uploadTask = ref.putBytes(thumb_byte);
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw Objects.requireNonNull(task.getException());
                }
                return  ref.getDownloadUrl();
            }

        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                downloadUri = task.getResult();
                fotolink = downloadUri.toString();
                DatabaseHelper.setImage(currentUser.getId(), fotolink);
                uploadImage.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.topAppBar.setVisibility(View.INVISIBLE);
        uploadImage.setVisibility(View.INVISIBLE);
    }

    private void replaceFragment(Fragment fragment){
        try{
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
        }
    }
}