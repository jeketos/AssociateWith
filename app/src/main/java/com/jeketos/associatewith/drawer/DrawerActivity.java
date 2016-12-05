package com.jeketos.associatewith.drawer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import com.jeketos.associatewith.Point;
import com.jeketos.associatewith.R;
import com.jeketos.associatewith.di.Injector;
import com.jeketos.associatewith.guesser.chat.ChatAdapter;
import com.jeketos.associatewith.guesser.chat.IChatItem;
import com.jeketos.associatewith.listener.TouchListener;
import com.jeketos.associatewith.util.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity implements DrawerMVP.DrawerView {

//    private static final String TAG = "DrawerActivity";
    private Canvas canvas;
    private Paint paint;
    @BindView(R.id.image_view)
    ImageView imageView;
    DrawerMVP.DrawerPresenter presenter;
    @BindView(R.id.recycler_view)
    RecyclerView chatRecyclerView;

    private TouchListener.MoveListener moveListener = new TouchListener.MoveListener() {
        @Override
        public void actionDown(Point point) {
            drawPoint(point);
        }

        @Override
        public void actionMove(float previousX, float previousY, Point point) {
            drawLine(previousX, previousY, point);
        }

        @Override
        public void actionUp(float previousX, float previousY, Point point) {
            drawLine(previousX, previousY, point);
        }
    };
    private TouchListener onTouchListener;
    private ChatAdapter chatAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        onTouchListener = new TouchListener(moveListener);
        presenter = Injector.INSTANCE.provideDrawPresenter(this);

    }

    @Override
     public void init() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Bitmap bitmap = Bitmap.createBitmap(displayMetrics.widthPixels,displayMetrics.heightPixels,
                Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);
        imageView.setImageBitmap(bitmap);
        imageView.setOnTouchListener(onTouchListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter();
        chatRecyclerView.setAdapter(chatAdapter);
    }

    @Override
    public void addChatItem(IChatItem chatItem) {
        chatAdapter.updateItems(chatItem);
    }

    @Override
    public void clearChat() {
        chatAdapter.updateItems(null);
    }

    @Override
    public void showChooseWordDialog(CharSequence[] words) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_word);
        builder.setCancelable(false);
        builder.setItems(words, (dialogInterface, i) -> {
            presenter.saveWord(words[i]);
            dialogInterface.dismiss();
        });
        builder.create().show();
    }

    @Override
    public void showWinnerDialog(String name) {
        AlertDialog.Builder builder = DialogUtils.createWinnerDialog(this,name,null);
        builder.create();
        builder.show();
    }


    public void drawPoint(Point point) {
        canvas.drawPoint(point.getX(), point.getY(), paint);
        imageView.invalidate();
        presenter.sendPoint(point);
    }


    public void drawLine(float previousX, float previousY, Point point) {
        canvas.drawLine(previousX, previousY, point.getX(), point.getY(), paint);
        imageView.invalidate();
        presenter.sendPoint(point);
    }
}
