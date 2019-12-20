import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

    private int port;

    public EchoServer(int port){
        this.port = port;
    }


    public static void main(String[] args) throws Exception{
        int port = 9999;
        new EchoServer(port).start();
    }

    public void start() throws Exception{
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup(); //클라이언트와 데이터 송수신 및 처리 담당
        try{
            ServerBootstrap b = new ServerBootstrap(); //연결 요청을 수신하는 포트를 바인딩
            /* 서버측 포트 바인딩 설정 */
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))//소켓 주소 설정
                    .childHandler(new ChannelInitializer<SocketChannel>() {//데이터 처리 설정
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);//파이프라인에 serverHandler 추가
                        }
                    });
            /*
            서버를 비동기식으로 바인딩
            sync()는 바인딩이 될때까지 대기한다는 뜻.
             */
            ChannelFuture future = b.bind().sync();

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("Server bound");
                    }else{
                        System.out.println("Bound Attempt Failed");
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
            /*
            채널의 CloseFuture를 얻고 완료될때까지
            현재 스레드는 블로킹 상태 전환
             */
            future.channel().closeFuture().sync();
        }finally{
            /*
            4way handshaking으로 우아하게 종료.
            모든 리소스 해제
            sync()는 같이 끝낸다는 뜻.
             */
            group.shutdownGracefully().sync();
        }

    }
}
