package cn.krund.soundlibrary.cache;

import cn.krund.soundlibrary.codec.ISoundCodec;

/**
 * Created by yuzhantao on 2016/1/28 0028.
 * 声音缓存对象
 */
public abstract class SoundCache {
    protected ISoundCodec m_soundCodec;             // 声音编解码器

    /**
     * 缓存构造函数
     * @param codec 编解码器，可为null。
     */
    public SoundCache(ISoundCodec codec){
        this.m_soundCodec=codec;                    // 编解码器
    }

    /**
     * 向缓存中写入数据
     * @param datas
     */
   public abstract void write(byte[] datas,int startPos,int lenght);

    /**
     * 从缓存中读取数据
     * @param len   读取的长度
     * @return
     */
   public abstract byte[] read(int len);
}
