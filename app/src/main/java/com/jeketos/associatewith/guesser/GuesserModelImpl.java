package com.jeketos.associatewith.guesser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class GuesserModelImpl implements GuesserMVP.GuesserModel {

    private static final String MOVE = "move";
    private GuesserMVP.GuesserPresenter presenter;


    public GuesserModelImpl(GuesserMVP.GuesserPresenter presenter) {

        this.presenter = presenter;
        init();

    }

    private void init() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(MOVE);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.dataReceived(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(valueEventListener);
    }
}
