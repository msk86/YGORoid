package org.msk86.ygoroid.utils;

import java.text.DecimalFormat;

public class FPSMaker
{
    /**
     * 设定动画运行多少帧后统计一次帧数
     */
    public static final int FPS = 8;

    public static final int MAX_FPS = 30;

    private static final int FRAME_PERIOD = 1000 / MAX_FPS;


    /**
     * 换算为运行周期
     * 单位: ns(纳秒)
     */
    public static final long PERIOD = (long) (1.0 / FPS * 1000000000);
    /**
     * FPS最大间隔时间，换算为1s = 10^9ns
     * 单位: ns
     */
    public static long FPS_MAX_INTERVAL = 1000000000L;

    /**
     * 实际的FPS数值
     */
    private double nowFPS = 0.0;

    /**
     * FPS累计用间距时间
     * in ns
     */
    private long interval = 0L;
    private long time;
    private long limitTime;
    private long sleepTime;
    /**
     * 运行桢累计
     */
    private long frameCount = 0;

    /**
     * 格式化小数位数
     */
    private DecimalFormat df = new DecimalFormat("0.0");

    public void limitFPS() {
        long timeDiff = System.currentTimeMillis() - limitTime;
        sleepTime = (int)(FRAME_PERIOD - timeDiff);

        if(sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {}
        }
    }

    /**
     * 制造FPS数据
     *
     */
    public void makeFPS()
    {
        frameCount++;
        interval += PERIOD;
        //当实际间隔符合时间时。
        if (interval >= FPS_MAX_INTERVAL)
        {
            //nanoTime()返回最准确的可用系统计时器的当前值，以毫微秒为单位
            long timeNow = System.nanoTime();
            // 获得到目前为止的时间距离
            long realTime = timeNow - time; // 单位: ns
            //换算为实际的fps数值
            nowFPS = ((double) frameCount / realTime) * FPS_MAX_INTERVAL;

            //变更数值
            frameCount = 0L;
            interval = 0L;
            time = timeNow;
        }
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public void setLimitTime(long limitTime) {
        this.limitTime = limitTime;
    }

    public String getFPS()
    {
        return df.format(nowFPS);
    }
}