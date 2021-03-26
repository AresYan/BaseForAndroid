package com.yz.base.netty;

import com.yz.base.utils.MyLogger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.timeout.IdleStateHandler;

public abstract class BaseWebSocketClient extends BaseSocketClient {

    private URI uri;

    public void init(String uri) {
        try {
            this.uri = new URI(uri);
            super.init();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    protected abstract void onTextWebSocketFrame(TextWebSocketFrame frame);

    protected abstract void onBinaryWebSocketFrame(BinaryWebSocketFrame frame);

    @Override
    protected void connect() {
        if (isNetError()) {
            retry();
            return;
        }
        try {
            if(isStart){
                return;
            }
            isStart=true;
            if(mGroup==null){
                mGroup = new NioEventLoopGroup();
            }
            BaseWebSocketClientHandler handler = new BaseWebSocketClientHandler(WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, false, new DefaultHttpHeaders()));
            handler.setListener(new BaseWebSocketClientHandler.BaseWebSocketListener(){
                @Override
                public void connected() {
                    MyLogger.d("=====BaseWebSocketClient connected");
                    register();
                }
                @Override
                public void disconnect() {
                    MyLogger.w("=====BaseWebSocketClient disconnect");
                    retry();
                }
                @Override
                public void onTextWebSocketFrame(TextWebSocketFrame frame) {
                    BaseWebSocketClient.this.onTextWebSocketFrame(frame);
                }
                @Override
                public void onBinaryWebSocketFrame(BinaryWebSocketFrame frame) {
                    BaseWebSocketClient.this.onBinaryWebSocketFrame(frame);
                }
            });
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(mGroup);
            bootstrap.option(ChannelOption.SO_TIMEOUT, SO_TIMEOUT);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HttpClientCodec(), new HttpObjectAggregator(1024*1024*10), handler);
                    ch.pipeline().addLast(new IdleStateHandler(5, 5, 7, TimeUnit.SECONDS));
                }
            });
            mChannel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
            handler.handshakeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            retry();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    if(mGroup!=null){
                        mGroup.shutdownGracefully();
                        mGroup=null;
                    }
                }
            }));
        }
    }

}
