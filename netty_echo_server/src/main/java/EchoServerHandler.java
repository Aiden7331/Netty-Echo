import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /*
        채널의 반대편(클라이언트)에서 오는 메시지를 읽음.
        읽은후 ChannelHandlerContext로 반대편으로 메시지 전송.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf)msg;
        System.out.println("Server Received: ".concat(in.toString(CharsetUtil.UTF_8)));
        ctx.write(in);//아웃바운드 메시지를 비우지 않은 채로 받은 메시지를 발신자로 출력.
    }
    /*
        읽기가 완료되었을때 수행.
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
            .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
