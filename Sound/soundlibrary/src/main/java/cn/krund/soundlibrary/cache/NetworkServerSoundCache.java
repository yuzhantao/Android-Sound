package cn.krund.soundlibrary.cache;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import cn.krund.soundlibrary.codec.ISoundCodec;

/**
 * Created by yuzhantao on 2016/1/28 0028.
 * 通过网络传输来实现的声音缓存-服务端
 */
public class NetworkServerSoundCache extends LocalSoundCache {

    private int m_serverPort;
    private ServerSocket m_serverSocket;

    /**
     * 缓存构造函数
     *
     * @param codec 编解码器，可为null。
     */
    public NetworkServerSoundCache(ISoundCodec codec,int serverPort) {
        super(codec);
        this.m_serverPort=serverPort;
        waitForSocket();
    }

    /**
     * 向缓存中写入数据
     * @param datas
     */
    @Override
   public void write(byte[] datas,int startPos,int lenght) {
        super.write(datas,startPos,lenght);
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

    private void waitForSocket(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            m_serverSocket=new ServerSocket(m_serverPort);
                            Socket socket = m_serverSocket.accept();
                            byte [] in_datas=new byte[2048];
                            DataInputStream stream=new DataInputStream(socket.getInputStream());
                            while (true) {
                                int realSize = stream.read(in_datas, 0, in_datas.length);
                                write(in_datas, 0, realSize);
                            }
                        }catch (IOException e){
                            e.getMessage();
                        }
                    }
                }
        ).start();
    }
}
