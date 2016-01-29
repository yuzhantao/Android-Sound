package cn.krund.soundlibrary;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import cn.krund.soundlibrary.cache.SoundCache;

/**
 * Created by yuzhantao on 2016/1/28 0028.
 * 声音采集器
 */
public class SoundCollector {
    private final static int SOUND_RATE=8000;       // 声音采样率

    protected SoundCache m_soundCache;             // 声音缓存
    private AudioRecord m_in_rec;                  // 录音对象
    protected boolean isKeepruning;

    /**
     * 声音采集构造函数
     * @param cache     用来读取声音数据的缓存对象
     */
    public SoundCollector(SoundCache cache){
        this.m_soundCache = cache;                  // 音频缓存
    }
    /**
     * 开始采集声音
     */
    public void startCollect(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                           int m_in_buf_size=AudioRecord.getMinBufferSize(SOUND_RATE,
                                   AudioFormat.CHANNEL_IN_MONO,
                                   AudioFormat.ENCODING_PCM_16BIT);     // 获取采集的最小长度
                            m_in_rec=new AudioRecord(MediaRecorder.AudioSource.MIC,
                                    SOUND_RATE,
                                    AudioFormat.CHANNEL_IN_MONO,
                                    AudioFormat.ENCODING_PCM_16BIT,
                                    m_in_buf_size);                     // 实例化录音对象
                            byte[] m_in_bytes=new byte[160];            // 读取的音频长度
                            m_in_rec.startRecording();
                            isKeepruning=true;
                            while (isKeepruning){
                               int realSize = m_in_rec.read(m_in_bytes, 0, m_in_bytes.length);      // 从设备读取采集到数据
                                m_soundCache.write(m_in_bytes,0,realSize);         // 讲采集的音频数据写入缓存
                            }
                            m_in_rec.stop();                            // 停止播放

                        }catch (Exception e){
                                e.printStackTrace();
                        }
                    }
                }
        ).start();


    }

    /**
     * 停止采集声音
     */
    public void stopCollect(){
            isKeepruning=false;
    }
}
