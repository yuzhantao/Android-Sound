package cn.krund.soundlibrary;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;

import cn.krund.soundlibrary.cache.SoundCache;

/**
 * Created by yuzhantao on 2016/1/28 0028.
 * 声音的播放器
 */
public class SoundPlayer {
    private AudioTrack m_out_trk;                  // 播放对象
    protected SoundCache m_soundCache;             // 声音缓存
    private boolean isKeepruning;                   // 是否播放
    /**
     * 声音播放器构造函数
     * @param cache     用来读取声音数据的缓存对象
     */
    public SoundPlayer(SoundCache cache){
        this.m_soundCache = cache;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (this.m_out_trk!=null){
            m_out_trk.release();
        }
    }


    /**
     * 播放声音
     */
    public void play(){

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                      int out_buf_size = AudioTrack.getMinBufferSize(8000,
                                AudioFormat.CHANNEL_IN_LEFT,
                                AudioFormat.ENCODING_PCM_16BIT);
                        m_out_trk = new AudioTrack(AudioManager.STREAM_SYSTEM, 8000,
                                AudioFormat.CHANNEL_IN_LEFT,
                                AudioFormat.ENCODING_PCM_16BIT,
                                out_buf_size,
                                AudioTrack.MODE_STREAM);
                        isKeepruning=true;
                        m_out_trk.setVolume(100f);
                        m_out_trk.play();
                        while (isKeepruning){
                            try {
                                byte [] m_read_buf=m_soundCache.read(out_buf_size);
                                if(m_read_buf==null){
                                    Thread.sleep(50);
                                    continue;
                                }

                                m_out_trk.write(m_read_buf,0,m_read_buf.length);

                            }catch (Exception e){
                                e.getMessage();
                            }
                        }
                        m_out_trk.stop();
                    }
                }
        ).start();
    }

    /**
     * 停止播放声音
     */
    public void stop(){
        isKeepruning=false;
    }
}
