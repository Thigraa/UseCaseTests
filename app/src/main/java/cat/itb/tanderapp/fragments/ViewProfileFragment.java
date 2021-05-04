package cat.itb.tanderapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;
import java.util.List;

import cat.itb.tanderapp.R;
import cat.itb.tanderapp.activities.MainActivity;
import cat.itb.tanderapp.helper.CardStackViewAdapter;
import cat.itb.tanderapp.model.User;

import static cat.itb.tanderapp.activities.MainActivity.currentUser;


public class ViewProfileFragment extends Fragment implements CardStackListener {

    CardStackView cardStackView;
    CardStackViewAdapter adapter;
    CardStackLayoutManager manager;
    public ViewProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_profile, container, false);
        cardStackView = v.findViewById(R.id.cardStackViewProfile);
        List<User> userInfo = new ArrayList<>();
        userInfo.add(currentUser);
        manager = new CardStackLayoutManager(getContext(), this);
        adapter = new CardStackViewAdapter(userInfo);
                cardStackView.setLayoutManager(manager);
                cardStackView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.topAppBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {

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
}