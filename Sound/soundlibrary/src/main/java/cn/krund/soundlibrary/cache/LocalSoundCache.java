package cn.krund.soundlibrary.cache;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import cn.krund.soundlibrary.codec.ISoundCodec;

/**
 * Created by yuzhantao on 2016/1/28 0028.
 */
public class LocalSoundCache extends SoundCache {
   // private List<Byte>m_soundDataList;
   private List<byte[]>m_soundDataList;
    /**
     * 缓存构造函数
     *
     * @param codec 编解码器，可以为null。
     */
    public LocalSoundCache(ISoundCodec codec) {
        super(codec);
        m_soundDataList=new ArrayList<>();
    }

    /**
     * 向缓存中写入数据
     * @param datas1
     */
    @Override
   public void write(byte[] datas1,int startPos,int lenght) {
        if(this.m_soundDataList.size()>50) this.m_soundDataList.clear();
        byte[] datas = new byte[lenght];
        System.arraycopy(datas1,startPos,datas,0,datas.length);
        int srcSize = this.m_soundDataList.size();

       // synchronized (this.m_soundDataList) {
            if (this.m_soundCodec != null) {
                // 转码没有处理startPos和lenght参数，需后续解决
                Collection mCoded = this.m_soundCodec.code(datas);
                m_soundDataList.addAll(mCoded);
            } else {
               /* for (int i = startPos; i < startPos + lenght; i++) {
                   // m_soundDataList.add(datas[i]);
                }*/
                this.m_soundDataList.add(datas);
            }
       // }

        Log.i("TAG","list size = " + this.m_soundDataList.size() +
                "src size =" + srcSize +
                " space = "+ (this.m_soundDataList.size()-srcSize));
        Log.i("TAG","datas size = " + datas.length +
                " start = "+ startPos +
        " length = " + lenght);
    }

    /**
     * 从缓存中读取数据
     * @param len   读取的长度
     * @return
     */
    @Override
   public byte[] read(int len) {
        byte[]datas=null;
        //synchronized (m_soundDataList){
//            try {
//                int datalen=Math.min(len,m_soundDataList.size());
//                if (datalen == 0) return  datas;
//                datas=new byte[datalen];
//                for (int i = 0; i < datalen; i++) {
//                    datas[i]=m_soundDataList.get(0);
//                    m_soundDataList.remove(0);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }

            if(this.m_soundDataList.size()>0){
                byte[] ret = this.m_soundDataList.get(0);
                this.m_soundDataList.remove(0);
                return ret;
            }
       // }

      return datas;
    }
}
