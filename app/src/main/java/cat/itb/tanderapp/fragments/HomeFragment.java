package cat.itb.tanderapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cat.itb.tanderapp.R;
import cat.itb.tanderapp.activities.MainActivity;
import cat.itb.tanderapp.helper.CardStackViewAdapter;
import cat.itb.tanderapp.helper.DatabaseHelper;
import cat.itb.tanderapp.model.User;

import static cat.itb.tanderapp.activities.MainActivity.currentUser;
import static cat.itb.tanderapp.activities.MainActivity.firebaseAuth;
import static cat.itb.tanderapp.activities.MainActivity.otherUser;
import static cat.itb.tanderapp.activities.MainActivity.topAppBar;
import static cat.itb.tanderapp.activities.MainActivity.users;


public class HomeFragment extends Fragment implements CardStackListener {
    private CardStackView cardStackView;
    private CardStackLayoutManager manager;
    public static CardStackViewAdapter adapter;
    public static int position;
    ImageView imageView;

    private FloatingActionButton rewind, dislike, superlike, like, boost;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper.findAll(currentUser.getAgeMin(), currentUser.getAgeMax(), currentUser.getAgeMin(), currentUser.getGender());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        position = 0;

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Calendar c = Calendar.getInstance();
        c.set(2001, 6, 14);
        List<String> urls = new ArrayList<>();
        urls.add("https://images.unsplash.com/photo-1481349518771-20055b2a7b24?ixid=MXwxMjA3fDB8MHxzZWFyY2h8M3x8cmFuZG9tfGVufDB8fDB8&ixlib=rb-1.2.1&w=1000&q=80");
        imageView = v.findViewById(R.id.item_image);
        rewind = v.findViewById(R.id.rewindFAB);
        dislike = v.findViewById(R.id.dislikeFAB);
        superlike = v.findViewById(R.id.superlikeFAB);
        like = v.findViewById(R.id.likeFAB);
        boost = v.findViewById(R.id.boostFAB);
        cardStackView = v.findViewById(R.id.cardStackView);
        manager = new CardStackLayoutManager(getContext(), this);
        adapter = new CardStackViewAdapter(users);
        adapter.notifyDataSetChanged();
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        setupCardStackView();
        System.out.println("========================================="+users+"================================================");
        manager.setStackFrom(StackFrom.Top);
        manager.setVisibleCount(4);
        manager.setTranslationInterval(8.0f);
//        setupButton();
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder().setDirection(Direction.Right).setDuration(Duration.Normal.duration).build();
                manager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
                position++;
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder().setDirection(Direction.Left).setDuration(Duration.Normal.duration).build();
                manager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
                position++;
            }
        });

        superlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder().setDirection(Direction.Top).setDuration(Duration.Normal.duration).build();
                manager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
                position++;
            }
        });

        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardStackView.rewind();
                position--;
            }
        });
        topAppBar.setVisibility(View.VISIBLE);
        return v;
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
//        if (direction == Direction.Right) {
//            DatabaseHelper.like(currentUser.getId(), otherUser.getId());
//        } else if (direction == Direction.Left) {
//            DatabaseHelper.dislike(currentUser.getId(), otherUser.getId());
//        } else if (direction == Direction.Top) {
//            DatabaseHelper.superlike(currentUser.getId(), otherUser.getId());
//        }
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }
    private void setupCardStackView(){
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setCanScrollVertical(true);
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        cardStackView.setItemAnimator(itemAnimator);
    }
}
