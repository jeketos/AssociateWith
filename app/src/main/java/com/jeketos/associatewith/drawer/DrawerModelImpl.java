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
    private DatabaseReference referenceMove;
    private DatabaseReference referenceChat;

    public DrawerModelImpl(DrawerMVP.DrawerPresenter presenter) {
        this.presenter = presenter;
        referenceMove = FirebaseDatabase.getInstance().getReference(MOVE);
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
        referenceMove.updateChildren(map);
    }

    @Override
    public void clearData() {
        referenceMove.removeValue();
    }
}
