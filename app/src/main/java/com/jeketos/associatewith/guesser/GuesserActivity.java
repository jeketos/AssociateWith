package com.jeketos.associatewith.guesser;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import com.jeketos.associatewith.Point;
import com.jeketos.associatewith.R;
import com.jeketos.associatewith.di.Injector;
import com.jeketos.associatewith.guesser.chat.ChatAdapter;
import com.jeketos.associatewith.guesser.chat.IChatItem;
import com.jeketos.associatewith.util.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class GuesserActivity extends AppCompatActivity implements GuesserMVP.GuesserView {

    GuesserMVP.GuesserPresenter presenter;
    private Canvas canvas;
    private Paint paint;
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.recycler_view)
    RecyclerView chatRecyclerView;
    @BindView(R.id.edit_text)
    EditText editText;
    private ChatAdapter chatAdapter;

    @OnClick(R.id.send) void OnSendClick(){
        String message = editText.getText().toString();
        if(!TextUtils.isEmpty(message)){
            presenter.sendMessage(message);
            editText.setText(null);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guesser);
        ButterKnife.bind(this);
        presenter = Injector.provideGuesserPresenter(this);
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter();
        chatRecyclerView.setAdapter(chatAdapter);
    }

    @Override
    public void draw(float previousX, float previousY, Point point) {
        switch (point.getMotionEvent()){
            case MotionEvent.ACTION_DOWN:
                drawPoint(point);
                break;
            default:
                drawLine(previousX, previousY, point);
                break;
        }
    }

    @Override
    public void clearBoard() {
        init();
    }

    @Override
    public void clearChat() {
        chatAdapter.updateItems(null);
    }

    @Override
    public void addChatItem(IChatItem item) {
        chatAdapter.updateItems(item);
    }

    @Override
    public void showWinnerDialog(String name, String word) {
        AlertDialog.Builder builder = DialogUtils.createWinnerDialog(this, name, word);
        builder.create();
        builder.show();
    }


    public void drawPoint(Point point) {
        canvas.drawPoint(point.getX(), point.getY(), paint);
        imageView.invalidate();
    }


    public void drawLine(float previousX, float previousY, Point point) {
        canvas.drawLine(previousX, previousY, point.getX(), point.getY(), paint);
        imageView.invalidate();
    }
}
