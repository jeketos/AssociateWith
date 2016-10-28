package com.jeketos.associatewith.drawer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jeketos.associatewith.Point;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class DrawerModelImpl implements DrawerMVP.DrawerModel {

    private static final String MOVE = "move";
    DrawerMVP.DrawerPresenter presenter;
    private DatabaseReference reference;

    public DrawerModelImpl(DrawerMVP.DrawerPresenter presenter) {
        this.presenter = presenter;
        reference = FirebaseDatabase.getInstance().getReference(MOVE);
    }

    //FIXME
    @Override
    public int getMovesCount() {
        return 0;
    }

    @Override
    public void sendPoint(int movesCount, Point point) {
        Map<String, Object> map = new HashMap<>();
        map.put(Integer.toString(movesCount), point);
        reference.updateChildren(map);
    }

    @Override
    public void clearData() {
        reference.removeValue();
    }
}
