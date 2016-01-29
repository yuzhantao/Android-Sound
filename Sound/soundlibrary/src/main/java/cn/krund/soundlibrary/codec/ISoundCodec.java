package cn.krund.soundlibrary.codec;

import java.util.Collection;

/**
 * Created by yuzhantao on 2016/1/28 0028.
 * 声音编解码器
 */
public interface ISoundCodec {
    /**
     * 编码
     * @param src
     * @param params
     * @return
     */
    Collection code(byte[] src, Object... params);

    /**
     * 解码
     * @param src
     * @param params
     * @return
     */
    byte[] decode(byte[] src,Object... params);
}
