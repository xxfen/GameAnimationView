package xxf.com.gameanimationview;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xxf on 2017/6/23.
 */

public class Myview extends View{
    private Bitmap bitmap;
    private int bmpWidth;
    private int bmpHeight;
    private Handler mHandler;
    private static final int ANIM_NULL = 0;  //动画状态-没有
    private static final int ANIM_CHECK = 1; //动画状态-开启大招
    private int x1, x2;
    private static int state = ANIM_NULL;//状态-默认为 0
    private int lineX = 0;
    private int lineY = 1;

    private int lineX_c = 5;
    private int lineY_c = 6;


    public Myview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.iori1);
        bmpWidth = bitmap.getWidth();
        bmpHeight = bitmap.getHeight();
        x2 = bmpWidth / 2;
        x1 = 0;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 10) {
                    x1 = bmpWidth / 2;
                    x2 = bmpWidth;
                    if (lineX == 5) {
                        lineX = 6;
                        lineY = 7;
                    } else{
                        lineX = 5;
                        lineY = 6;
                    }
                } else if (msg.what == 11) {
                    if (lineX < 7) {
                        lineX++;
                        lineY++;
                    } else {
                        lineX = 0;
                        lineY = 1;
                        if (x1 == 0) {
                            x1 = bmpWidth / 2;
                            x2 = bmpWidth;
                        } else {
                            state = ANIM_NULL;
                            x2 = bmpWidth / 2;
                            x1 = 0;
                        }
                    }
                }
                invalidate();

            }
        };


        new Thread(new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < 200; i++) {
                        if (state == ANIM_NULL) {
                            mHandler.sendEmptyMessage(10);
                            Thread.sleep(100);
                        } else if (state == ANIM_CHECK) {
                            mHandler.sendEmptyMessage(11);
                            Thread.sleep(100);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }).
                start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 将画布坐标系移动到..
        canvas.translate(0, 0);
        Rect src = new Rect(x1, bmpHeight * lineX / 8, x2, bmpHeight * lineY / 8);
        // 指定图片在屏幕上显示的区域
        Rect dst = new Rect(0, 0, getWidth(), getHeight());
        // 绘制图片
        canvas.drawBitmap(bitmap, src, dst, null);


    }
    /*用于测量视图的大小的*/
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // MeasureSpec.getSize(widthMeasureSpec);    //取出宽度的确切数值
        // MeasureSpec.getSize(heightMeasureSpec);    //取出高度的确切数值
    }


    public static void setStart(int ate) {
        state = ate;

    }


}

