package cn.krund.soundlibrary.cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import cn.krund.soundlibrary.codec.ISoundCodec;

/**
 * Created by yuzhantao on 2016/1/28 0028.
 * 通过网络传输来实现的声音缓存-客户端
 */
public class NetworkClientSoundCache extends LocalSoundCache {

    private int m_serverPort;
    private String m_serverIp;
    /**
     * 缓存构造函数
     *
     * @param codec 编解码器，可为null。
     */
    public NetworkClientSoundCache(ISoundCodec codec,String serverIp,int serverPort) {
        super(codec);
        this.m_serverIp=serverIp;
        this.m_serverPort=serverPort;
        sendSound();
    }

    /**
     * 向缓存中写入数据
     * @param datas
     */
    @Override
    public  void write(byte[] datas,int startPos,int lenght) {
        super.write(datas, startPos, lenght);
    }

    /**
     * 从缓存中读取数据
     * @param len   读取的长度
     * @return
     */
    @Override
   public byte[] read(int len) {
        return super.read(len);
    }
    private void sendSound(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Socket socket=new Socket();
                        try {
                            socket.connect(new InetSocketAddress(m_serverIp,m_serverPort),5000);
                            OutputStream stream=socket.getOutputStream();
                            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            while (true){
                                byte [] readCache= read(2048);
                                if (readCache!=null && readCache.length>0) {
                                    stream.write(readCache, 0, readCache.length);
                                }else {
                                   Thread.sleep(20);
                                }
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            socket=null;
                        }
                    }
                }
        ).start();
    }
}
