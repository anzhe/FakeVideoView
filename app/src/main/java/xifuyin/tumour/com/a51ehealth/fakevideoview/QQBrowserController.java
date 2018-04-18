package xifuyin.tumour.com.a51ehealth.fakevideoview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/4/17.
 */

public class QQBrowserController extends BaseController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    //控件对象
    public ImageView cover;
    public ImageView back;
    public TextView title;
    public ImageView share;
    public ImageView tiny_window;
    public ImageView menu;
    public LinearLayout top;
    public ImageView lock;
    public ImageView restart_or_pause;
    public TextView position;
    public SeekBar seek;
    public TextView duration;
    public ImageView full_screen;
    public LinearLayout bottom;
    public ImageView center_start;
    private ProgressBar progress_bar;

    public QQBrowserController(@NonNull Context context) {
        super(context);
        init();
    }


    //打入布局，布局是仿照QQ浏览器布局书写的
    private void init() {
        View qq_controller = LayoutInflater.from(mContext).inflate(R.layout.qq_controller, this, true);
        this.cover = qq_controller.findViewById(R.id.cover);
        this.back = qq_controller.findViewById(R.id.back);
        this.title = qq_controller.findViewById(R.id.title);
        this.share = qq_controller.findViewById(R.id.share);
        this.tiny_window = qq_controller.findViewById(R.id.tiny_window);
        this.menu = qq_controller.findViewById(R.id.menu);
        this.top = qq_controller.findViewById(R.id.top);
        this.lock = qq_controller.findViewById(R.id.lock);
        this.restart_or_pause = qq_controller.findViewById(R.id.restart_or_pause);
        this.position = qq_controller.findViewById(R.id.position);
        this.seek = qq_controller.findViewById(R.id.seek);
        this.duration = qq_controller.findViewById(R.id.duration);
        this.full_screen = qq_controller.findViewById(R.id.full_screen);
        this.bottom = qq_controller.findViewById(R.id.bottom);
        this.center_start = qq_controller.findViewById(R.id.center_start);
        this.progress_bar = qq_controller.findViewById(R.id.progress_bar);

        //中心按钮的点击事件
        center_start.setOnClickListener(this);
        back.setOnClickListener(this);
        share.setOnClickListener(this);
        tiny_window.setOnClickListener(this);
        menu.setOnClickListener(this);
        lock.setOnClickListener(this);
        restart_or_pause.setOnClickListener(this);
        full_screen.setOnClickListener(this);

        seek.setOnSeekBarChangeListener(this);

        //整个布局的点击事件
        this.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == center_start) {//播放按钮被点击

            if (xVideoView.isIdle()) { //判断是否是播放未开始
                xVideoView.start();//调用自定义播放器的开始方法
            }
        } else if (v == back) {//返回按钮被点击

            Toast.makeText(mContext, "返回按钮被点击", Toast.LENGTH_SHORT).show();

        } else if (v == share) {//分享按钮被点击

            Toast.makeText(mContext, "分享按钮被点击", Toast.LENGTH_SHORT).show();

        } else if (v == tiny_window) {//小屏按钮被点击

            Toast.makeText(mContext, "小屏按钮被点击", Toast.LENGTH_SHORT).show();

        } else if (v == menu) {//菜单被点击

            Toast.makeText(mContext, "菜单被点击", Toast.LENGTH_SHORT).show();

        } else if (v == lock) {//锁定控制器被点击

            Toast.makeText(mContext, "锁定控制器被点击", Toast.LENGTH_SHORT).show();

        } else if (v == restart_or_pause) {//开始暂停被点击

            Toast.makeText(mContext, "开始暂停被点击", Toast.LENGTH_SHORT).show();

        } else if (v == full_screen) {//全屏被点击

            Toast.makeText(mContext, "全屏被点击", Toast.LENGTH_SHORT).show();

        } else if (v == this) {//整个控制器被点击

            Toast.makeText(mContext, "整个控制器被点击", Toast.LENGTH_SHORT).show();
        }


    }


    @Override//根据标记的播放状态去更新对应的UI
    public void onPlayStateChanged(int state) {
        switch (state) {

            case Constants.STATE_IDLE://默认状态
                cover.setVisibility(VISIBLE);
                center_start.setVisibility(VISIBLE);

                break;
            case Constants.STATE_PREPARING://正在准备播放状态，对应去更新UI
                cover.setVisibility(GONE);
                center_start.setVisibility(GONE);
                progress_bar.setVisibility(VISIBLE);
                break;

            case Constants.STATE_PREPARED://播放器准备就绪，准备播放
                progress_bar.setVisibility(GONE);

                startUpdateProgressTimer();//开启定时器，会调用更新进度

                break;

            case Constants.STATE_PLAYING://播放器正在播放
                top.setVisibility(VISIBLE);
                bottom.setVisibility(VISIBLE);
                lock.setVisibility(VISIBLE);

                break;


            case Constants.STATE_COMPLETED://播放完成后更新Ui

                cancelUpdateProgressTimer();//取消更新进度的方法，否则视频播放完成，更新进度的定时器还是会走，浪费cpu,这个是父类的方法

                break;


        }

    }


    /**
     * 当视频准备就绪后，设置页面和视频进度有关的UI,因为进度是事实更新的，所以这里使用定时器，
     * 每隔一秒去请求一次数据，因为这个所有子类都有的功能，所以定时器写到了父类
     * <p>
     * 这个方法每个一秒会调用一次，因为是时时更新进度的方法
     */
    protected void updateProgress() {
        //获取播放器解析到的当前进度和整个视频长度，还有缓存进度
        long currentPosition = xVideoView.getCurrentPosition();
        long durationPosition = xVideoView.getDuration();
        int bufferPercentage = xVideoView.getBufferPercentage();
        //设置数据到控件
        position.setText(Utils.formatTime(currentPosition));
        duration.setText(Utils.formatTime(durationPosition));
        int progress = (int) (100 * currentPosition / durationPosition);//计算进度百分比，转换层int，因为下边接受的就是int类型的值
        seek.setProgress(progress);//设置进度条
        seek.setSecondaryProgress(bufferPercentage);//设置缓存百分比
        Log.e("rrrrrrrrrrrrr", bufferPercentage + "tttttttttttttttt");

    }


    //==============================================底部进度条拖动的回调=========================================================
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        long touchProgress = (progress * xVideoView.getDuration() / 100);
        xVideoView.seekTo(touchProgress);
    }
}