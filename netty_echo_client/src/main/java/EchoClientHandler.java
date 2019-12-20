import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /*
        채널이 연결되는 순간 발송, writeAndFlush.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        Scanner scanner = new Scanner(System.in);
        System.out.println("전송할 문자를 입력하세요.");
        String msg = scanner.nextLine();
        ctx.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }

    /*
        채널의 반대편에서 오는 메시지를 읽음.
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        System.out.println("Client received: ".concat(in.toString(CharsetUtil.UTF_8)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
