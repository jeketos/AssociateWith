package com.jeketos.associatewith.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.jeketos.associatewith.Point;
import com.jeketos.associatewith.R;
import com.jeketos.associatewith.di.Injector;
import com.jeketos.associatewith.listener.TouchListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawActivity extends AppCompatActivity implements DrawMVP.DrawView {

//    private static final String TAG = "DrawActivity";
    private Canvas canvas;
    private Paint paint;
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.block_view)
    View blockView;
    DrawMVP.DrawPresenter presenter;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        onTouchListener = new TouchListener(moveListener);
        presenter = Injector.provideDrawPresenter(this);

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
    }


    public void drawPoint(Point point) {
        canvas.drawPoint(point.x, point.y, paint);
        imageView.invalidate();
        presenter.sendPoint(point);
    }


    public void drawLine(float previousX, float previousY, Point point) {
        canvas.drawLine(previousX, previousY, point.x, point.y, paint);
        imageView.invalidate();
        presenter.sendPoint(point);
    }
}
