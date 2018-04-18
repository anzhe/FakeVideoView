package xifuyin.tumour.com.a51ehealth.fakevideoview;

/**
 * Created by Administrator on 2018/4/17.
 */

public interface IXVideoView {

    /**
     * 开始播放的方法
     */
    void start();

    /**
     * 设置视频播放的url
     *
     * @param url
     */
    void setUrl(String url);


    /**
     * 获取办法给总时长，毫秒
     *
     * @return 视频总时长ms
     */
    long getDuration();

    /**
     * 获取当前播放的位置，毫秒
     *
     * @return 当前播放位置，ms
     */
    long getCurrentPosition();

    /**
     * 拖动进度掉到指定位置
     */
    void seekTo(long touchProgress);

    /**
     * 获取视频缓存进度，体现在进度条的第二个颜色
     */

    int getBufferPercentage();


    //===============================播放器状态=================================

    boolean isIdle();

    boolean isPreparing();

    boolean isPlaying();


    boolean isCompleted();


}