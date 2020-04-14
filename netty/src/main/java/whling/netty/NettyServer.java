package whling.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyServer {

    /**
     * 创建两个NioEventLoopGroup,这两个对象可以看做是传统IO编程模型的两大线程组,boosGroup表示监听端口,创建新连接的线程组,workerGroup表示处理每一条连接的数据读写的线程组
     * 创建引导类 ServerBootstrap进行服务端的启动工作,通过.group(boosGroup, workerGroup)给引导类配置两大线程定型引导类的线程模型指定服务端的IO模型为NIO,通过.channel(NioServerSocketChannel.class)来指定IO模型
     * 调用childHandler()方法给引导类创建ChannelInitializer定义后续每条连接的数据读写,业务处理逻辑,泛型参数NioSocketChannel是Netty对NIO类型的连接的抽象,而NioServerSocketChannel也是对NIO类型的连接的抽象
     * serverBootstrap.bind()是异步的方法调用之后是立即返回的,返回值是ChannelFuture,给ChannelFuture添加监听器GenericFutureListener,在GenericFutureListener的operationComplete方法里面监听端口是否绑定成功
     * childHandler()用于指定处理新连接数据的读写处理逻辑,handler()用于指定在服务端启动过程中的一些逻辑
     * attr()方法给服务端的channel即NioServerSocketChannel指定一些自定义属性,通过channel.attr()取出该属性,给NioServerSocketChannel维护一个map
     * childAttr()方法给每一条连接指定自定义属性,通过channel.attr()取出该属性
     * childOption()方法给每条连接设置一些TCP底层相关的属性:
     * ChannelOption.SO_KEEPALIVE表示是否开启TCP底层心跳机制,true为开启
     * ChannelOption.SO_REUSEADDR表示端口释放后立即就可以被再次使用,因为一般来说,一个端口释放后会等待两分钟之后才能再被使用
     * ChannelOption.TCP_NODELAY表示是否开始Nagle算法,true表示关闭,false表示开启,通俗地说,如果要求高实时性,有数据发送时就马上发送,就关闭,如果需要减少发送次数减少网络交互就开启
     * option()方法给服务端channel设置一些TCP底层相关的属性:
     * ChannelOption.SO_BACKLOG表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁,服务器处理创建新连接较慢,适当调大该参数
     *
     * @param args
     */

    public static void main(String[] args) throws Exception {

        /**
         * Netty 抽象出两组线程池，BossGroup 专门负责接收客户端连接，WorkerGroup 专门负责网络读写操作。
         * NioEventLoop 表示一个不断循环执行处理任务的线程，每个 NioEventLoop 都有一个 selector，用于监听绑定 在其上的 socket 网络通道。
         * NioEventLoop 内部采用串行化设计，从消息的读取->解码->处理->编码->发送，始终由 IO 线程 NioEventLoop 负责
         *
         *  NioEventLoopGroup 下包含多个 NioEventLoop
         *  每个 NioEventLoop 中包含有一个 Selector，一个 taskQueue
         *  每个 NioEventLoop 的 Selector 上可以注册监听多个 NioChannel
         *  每个 NioChannel 只会绑定在唯一的 NioEventLoop 上
         *  每个 NioChannel 都绑定有一个自己的 ChannelPipeline
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();

            /**
             * ChannelOption.SO_BACKLOG
             * 对应 TCP/IP 协议 listen 函数中的 backlog 参数，用来初始化服务器可连接队列大小。服
             * 务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接。多个客户
             * 端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog 参数指定
             * 了队列的大小。
             */

            /**
             * tcp粘包拆包：tcp滑动窗口机制，可以提高数据传输性能，但是当一次发送的数据大于或者小于tcp buffer的大小时，
             * 就会出现粘包半包现象
             *
             * 使用自定义协议 + 编解码器 来解决
             * 关键就是要解决 服务器端每次读取数据长度的问题, 这个问题解决，就不会出现服务器多读或少读数据的问题，从而避免的TCP 粘包、拆包 。
             *
             * netty内置的几种解决tcp粘包拆包问题的编解码器：
             *  将FixedLengthFrameDecoder和FixedLengthFrameEncoder添加到pipeline中，指定长度，不足不空格
             *  将DelimiterBasedFrameDecoder添加到pipeline中，指定响应数据最后添加分隔符
             *  将JsonObjectDecoder指定传输数据为json
             *  将LineBasedFrameDecoder基于换行，判断看是否有"\n"或者"\r\n",如果有就以此位置为结束位置（LineBasedFrameDecoder+StringDecoder）
             *
             * 使用{@link io.netty.handler.codec.ByteToMessageDecoder}和{@link io.netty.handler.codec.MessageToByteEncoder}手动解析数据
             *
             * {@link io.netty.handler.codec.ReplayingDecoder}不用手动判断数据包能读多少的情况。ReplayingDecoder扩展了ByteToMessageDecoder类，使用这个类，我们不必调用readableBytes()方法。
             * 参数S指定了用户状态管理的类型，其中Void代表不需要状态管理。
             * ReplayingDecoder使用方便，但它也有一些局限性：
             *
             * 并不是所有的 ByteBuf 操作都被支持，如果调用了一个不被支持的方法，将会抛出一个 UnsupportedOperationException。
             * ReplayingDecoder 在某些情况下可能稍慢于 ByteToMessageDecoder，例如网络缓慢并且消息格式复杂时，消息会被拆成了多个碎片，速度变慢
             *
             *
             */

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //加入一个netty 提供 IdleStateHandler
                            /*
                            说明
                            1. IdleStateHandler 是netty 提供的处理空闲状态的处理器
                            2. long readerIdleTime : 表示多长时间没有读, 就会发送一个心跳检测包检测是否连接
                            3. long writerIdleTime : 表示多长时间没有写, 就会发送一个心跳检测包检测是否连接
                            4. long allIdleTime : 表示多长时间没有读写, 就会发送一个心跳检测包检测是否连接

                            5. 文档说明
                            triggers an {@link IdleStateEvent} when a {@link Channel} has not performed
                             * read, write, or both operation for a while.
                            6. 当 IdleStateEvent 触发后 , 就会传递给管道 的下一个handler去处理
                             通过调用(触发)下一个handler 的 userEventTiggered , 在该方法中去处理 IdleStateEvent(读空闲，写空闲，读写空闲)
                             */
                            ch.pipeline().addLast(new IdleStateHandler(7000, 7000, 10, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new IdleHandler())
                                    .addLast(new NettyServerHandler());
                        }
                    });

            ChannelFuture cf = serverBootstrap.bind(6668).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("start success");
//                        future.channel().closeFuture().sync();
                    }
                }
            });
            cf.channel().closeFuture().sync();


        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
