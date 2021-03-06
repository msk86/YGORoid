package org.msk86.ygoroid.views.logo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.views.OnKeyProcessor;
import org.msk86.ygoroid.views.OnMenuProcessor;
import org.msk86.ygoroid.views.YGOView;

public class LogoView extends YGOView {

    YGOActivity context;

    private GestureDetector mGestureDetector;

    Logo[] logos;

    int logoIdx;

    long startTime;

    public LogoView(YGOActivity context) {
        super(context, null);
        this.context = context;
        logos = new Logo[]{
                new Logo(R.raw.msk86_logo),
                new Logo(R.raw.mc_logo, Color.WHITE)
        };
        logoIdx = 0;

        mGestureDetector = new GestureDetector(new LogoGestureListener(this));
    }


    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        super.run();
    }

    @Override
    protected void doDraw(Canvas canvas) {
        Logo currentLogo = logos[logoIdx];
        canvas.drawBitmap(currentLogo.logo, (Utils.screenWidth() - currentLogo.logo.getWidth()) / 2,
                (Utils.screenHeight() - currentLogo.logo.getHeight()) / 2, new Paint());

        long now = System.currentTimeMillis();
        if (now - startTime < currentLogo.stayTime) {
            // do nothing
        } else if (now - startTime - currentLogo.stayTime < currentLogo.intervalTime) {
            // mask
            int alpha = 255 * (int) (now - startTime - currentLogo.stayTime) / currentLogo.intervalTime;
            canvas.drawARGB(alpha, Color.red(currentLogo.maskColor), Color.green(currentLogo.maskColor), Color.blue(currentLogo.maskColor));
        } else {
            canvas.drawARGB(255, Color.red(currentLogo.maskColor), Color.green(currentLogo.maskColor), Color.blue(currentLogo.maskColor));
            nextLogo();
        }
        updateActionTime();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public void nextLogo() {
        if (logoIdx >= logos.length - 1) {
            context.getLogoView().handler.sendEmptyMessage(0);
            pause();
        } else {
            logoIdx++;
            startTime = System.currentTimeMillis();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            context.showDuel();
        }
    };



    @Override
    public OnKeyProcessor getOnKeyProcessor() {
        return null;
    }

    @Override
    public OnMenuProcessor getOnMenuProcessor() {
        return null;
    }

    @Override
    public String getDuelState() {
        return YGOView.DUEL_STATE_LOGO;
    }


    @Override
    public void deallocateMemory() {
    }
}
