package cat.itb.tanderapp.helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cat.itb.tanderapp.activities.MainActivity;
import cat.itb.tanderapp.model.User;

import static cat.itb.tanderapp.activities.MainActivity.currentUser;
import static cat.itb.tanderapp.activities.MainActivity.rewind;
import static cat.itb.tanderapp.activities.MainActivity.users;
import static cat.itb.tanderapp.fragments.HomeFragment.adapter;

public class DatabaseHelper {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef = database.getReference("User");
    private static List<User> pendingToSelect = new ArrayList<>();
    private static User user;
    private static User userToModify;

    public static void findUsers(String myGender, String myPreference, int ageMin, int ageMax, int myAge) {
        if (myPreference.equals("All")) {
            findAll(ageMin, ageMax, myAge, myGender);
        } else {
            findByGender(myGender, myPreference, ageMin, ageMax, myAge);
        }
    }
    public static void findAll(int ageMin, int ageMax, int myAge, String myGender){

//    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
//    reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                        String userId = ds.getKey();
//                        DatabaseReference userIdRef = FirebaseDatabase.getInstance().getReference().child("User");
//                        userIdRef.addChildEventListener(new ChildEventListener() {
//                            @Override
//                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                                User user = dataSnapshot.child("name").getValue(User.class);
//                                users.add(user);
//                                adapter.notifyDataSetChanged();
//
//                            }
//
//                            @Override
//                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                            }
//
//                            @Override
//                            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                            }
//
//                            @Override
//                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//
//                    }
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot.exists());
                for (DataSnapshot child : snapshot.getChildren()) {
                    String name = child.child("name").getValue().toString();
                    System.out.println(name);
                    String id = child.child("id").getValue().toString();
                    int age = Integer.parseInt(child.child("age").getValue().toString());
                    int ageMax = Integer.parseInt(child.child("ageMax").getValue().toString());
                    int ageMin = Integer.parseInt(child.child("ageMin").getValue().toString());
                    String email = child.child("email").getValue().toString();
                    String gender = child.child("gender").getValue().toString();
                    String preference = child.child("preference").getValue().toString();
                    String description = child.child("description").getValue().toString();
                    User u = new User(id, email, name, age, gender, preference, ageMin, ageMax);
                    u.setDescription(description);
                    ArrayList<String> urls = new ArrayList<>();
                    if (child.child("urls").getValue() != null) {
                        urls = (ArrayList<String>) child.child("urls").getValue();
                    }
                    u.setUrls(urls);
                    ArrayList<String> likesID = new ArrayList<>();
                    if (child.child("likesID").getValue() != null) {
                        likesID = (ArrayList<String>) child.child("likesID").getValue();
                    }
                    u.setLikesID(likesID);
                    ArrayList<String> matchesID = new ArrayList<>();
                    if (child.child("matchesID").getValue() != null) {
                        matchesID = (ArrayList<String>) child.child("matchesID").getValue();
                    }
                    u.setMatchesID(matchesID);
                    pendingToSelect.add(u);
                    Log.d("FIREBASE", "USUARIO AÑADIDO");
                    filterUsers(pendingToSelect, myGender, ageMin, ageMax, myAge);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void findByGender(String myGender, String myPreference, int ageMin, int ageMax, int myAge) {
        myRef.orderByChild("gender").equalTo(myPreference).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    String name = child.child("name").getValue().toString();
                    System.out.println(name);
                    String id = child.child("id").getValue().toString();
                    int age = Integer.parseInt(child.child("age").getValue().toString());
                    int ageMax = Integer.parseInt(child.child("ageMax").getValue().toString());
                    int ageMin = Integer.parseInt(child.child("ageMin").getValue().toString());
                    String email = child.child("email").getValue().toString();
                    String gender = child.child("gender").getValue().toString();
                    String preference = child.child("preference").getValue().toString();
                    String description = child.child("description").getValue().toString();
                    User u = new User(id, email, name, age, gender, preference, ageMin, ageMax);
                    u.setDescription(description);
                    ArrayList<String> urls = new ArrayList<>();
                    urls = (ArrayList<String>) child.child("urls").getValue();
                    u.setUrls(urls);
                    ArrayList<String> likesID = new ArrayList<>();
                    likesID = (ArrayList<String>) child.child("likesID").getValue();
                    u.setLikesID(likesID);
                    ArrayList<String> matchesID = new ArrayList<>();
                    matchesID = (ArrayList<String>) child.child("matchesID").getValue();
                    u.setMatchesID(matchesID);
                    pendingToSelect.add(u);
                    System.out.println(u);
                    Log.d("FIREBASE", "USUARIO AÑADIDO");
                    filterUsers(pendingToSelect, myGender, ageMin, ageMax, myAge);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    public static void filterUsers(List<User> userss, String myGender, int ageMin, int ageMax, int myAge) {
        users = new ArrayList<>();
        for (User u : userss) {
            System.out.println("estoy aqui");
            System.out.println("Su nombre:"+u.getName());
            if (u.getPreference().equals(myGender)) {
                System.out.println("su preferencia:"+u.getPreference());
                System.out.println("Gender pasado");
                if (u.getAge() <= ageMax && u.getAge() >= ageMin) {
                    System.out.println("Age pasado");
                    if (myAge >= u.getAgeMin() && myAge <= u.getAgeMax()) {
                        System.out.println("AGE 2 PASADO");
                        users.add(u);
                        System.out.println("Usuario añadido");
                    }
                }
            }

        }
    }

    public static void addUser(User u) {
        String key = myRef.push().getKey();
        u.setId(key);
        myRef.child(key).setValue(u);
    }

    public static void findByEmail(String email) {
        myRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    String name = child.child("name").getValue().toString();
                    String id = child.child("id").getValue().toString();
                    int age = Integer.parseInt(child.child("age").getValue().toString());
                    int ageMax = Integer.parseInt(child.child("ageMax").getValue().toString());
                    int ageMin = Integer.parseInt(child.child("ageMin").getValue().toString());
                    String email = child.child("email").getValue().toString();
                    String gender = child.child("gender").getValue().toString();
                    String preference = child.child("preference").getValue().toString();
                    String description = child.child("description").getValue().toString();
                    currentUser = new User(id, email, name, age, gender, preference, ageMin, ageMax);
                    currentUser.setDescription(description);
                    ArrayList<String> urls = new ArrayList<>();
                    urls = (ArrayList<String>) child.child("urls").getValue();
                    currentUser.setUrls(urls);
                    ArrayList<String> likesID = new ArrayList<>();
                    likesID = (ArrayList<String>) child.child("likesID").getValue();
                    currentUser.setLikesID(likesID);
                    ArrayList<String> matchesID = new ArrayList<>();
                    matchesID = (ArrayList<String>) child.child("matchesID").getValue();
                    currentUser.setMatchesID(matchesID);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void findById(String id){
            myRef.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot child : snapshot.getChildren()) {
                        String name = child.child("name").getValue().toString();
                        String id = child.child("id").getValue().toString();
                        int age = Integer.parseInt(child.child("age").getValue().toString());
                        int ageMax = Integer.parseInt(child.child("ageMax").getValue().toString());
                        int ageMin = Integer.parseInt(child.child("ageMin").getValue().toString());
                        String email = child.child("email").getValue().toString();
                        String gender = child.child("gender").getValue().toString();
                        String preference = child.child("preference").getValue().toString();
                        String description = child.child("description").getValue().toString();
                        userToModify = new User(id, email, name, age, gender, preference, ageMin, ageMax);
                        userToModify.setDescription(description);
                        ArrayList<String> urls = new ArrayList<>();
                        urls = (ArrayList<String>) child.child("urls").getValue();
                        userToModify.setUrls(urls);
                        ArrayList<String> likesID = new ArrayList<>();
                        likesID = (ArrayList<String>) child.child("likesID").getValue();
                        userToModify.setLikesID(likesID);
                        ArrayList<String> matchesID = new ArrayList<>();
                        matchesID = (ArrayList<String>) child.child("matchesID").getValue();
                        userToModify.setMatchesID(matchesID);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    public static void setDescription(String id, String description){
        myRef.child(id).child("description").setValue(description);
    }
    public static void setGender(String id, String gender){
        myRef.child(id).child("gender").setValue(gender);
    }
    public static void setPreference(String id, String preference){
        myRef.child(id).child("preference").setValue(preference);
    }
    public static void setAgeRange(String id, int ageMin, int ageMax){
        myRef.child(id).child("ageMin").setValue(ageMin);
        myRef.child(id).child("ageMax").setValue(ageMax);
    }
    public static void setImage(String id, String url){
        List <String> urls = new ArrayList();
        urls.add(url);
        myRef.child(id).child("urls").setValue(urls);
    }
    
    public static void like(String myId, String otherId){
        List <String> likes = new ArrayList();
        findById(otherId);
        likes = userToModify.getLikesID();
        likes.add(myId);
        myRef.child(otherId).child("likes").setValue(likes);
        checkMatch(myId);
    }

    public static void dislike(String myId, String otherId) {
    }

    public static void superlike(String myId, String otherId) {
        List <String> likes = new ArrayList();
        findById(otherId);
        likes = userToModify.getLikesID();
        likes.add(myId);
        myRef.child(otherId).child("likes").setValue(likes);
        checkMatch(myId);
    }

    public static void checkMatch(String myId){
        findById(myId);
        List <String> likes = new ArrayList<>();
        likes = userToModify.getLikesID();
        List <String> matches = userToModify.getMatchesID();
        List <String> otherLikes = new ArrayList<>();
        List <String> otherMatches = new ArrayList<>();
        for(String id : likes){
             findById(id);
             otherLikes = userToModify.getLikesID();
             otherMatches = userToModify.getMatchesID();
             for(String id2: otherLikes){
                 if(id2.equals(myId)){
                matches.add(id);
                    likes.remove(likes.indexOf(id));
                    otherMatches.add(myId);
                    otherLikes.remove(otherLikes.indexOf(id2));
                     }
             }
             myRef.child(id).child("likesID").setValue(otherLikes);
            myRef.child(id).child("matchesID").setValue(otherMatches);
        }
        myRef.child(myId).child("likesID").setValue(likes);
        myRef.child(myId).child("matchesID").setValue(matches);
    }
}

