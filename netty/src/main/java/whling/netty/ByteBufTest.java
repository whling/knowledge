package whling.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 *  https://blog.csdn.net/qq_32360995/article/details/91442316
 *
 * <--被废弃的-->readindex<--readable-->writeindex<--writeable-->capacity<--scalable-->max capacity
 *
 * 二进制数据抽象ByteBuf是字节容器,容器里面的的数据分为三个部分,
 * 第一个部分是已经丢弃的字节,这部分数据是无效的;
 * 第二部分是可读字节,这部分数据是ByteBuf的主体数据,从ByteBuf里面读取的数据都来自这一部分;
 * 最后一部分的数据是可写字节,所有写到 ByteBuf的数据都会写到这一段.最后一部分虚线表示的是该ByteBuf最多还能扩容多少容量.这三部分内容是被两个指针给划分出来的,从左到右依次是读指针(readerIndex)、写指针(writerIndex),然后还有容量capacity表示ByteBuf底层内存的总容量.
 * 读数据是从ByteBuf里每读取一个字节,readerIndex自增1,ByteBuf里面共有writerIndex-readerIndex个字节可读, 由此推论当readerIndex与writerIndex相等的时候,ByteBuf不可读.
 * 写数据是从writerIndex指向的部分开始写,每写一个字节,writerIndex 自增1,直到增加至容量capacity,此时表示ByteBuf不可写.
 * ByteBuf里容量上限maxCapacity表示当向ByteBuf写数据时容量capacity不足进行扩容,直至扩容到最大容量maxCapacity,超过 maxCapacity报错.
 * 推荐API:markReaderIndex()&resetReaderIndex()/markWriterIndex()& resetWriterIndex():前者表示把当前的读/写指针保存起来,后者表示把当前的读/写指针恢复到之前保存的值,常见使用场景为解析自定义协议的数据包.
 * 解析数据注意:get/set***()方法不会改变读写指针,read/write***()方法会改变读写指针.
 */
public class ByteBufTest {

    /**
     * retain和release方法
     *
     * 由于 Netty 使用了堆外内存，而堆外内存是不被 jvm 直接管理的，也就是说申请到的内存无法被垃圾回收器直接回收，所以需要我们手动回收。有点类似于c语言里面，申请到的内存必须手工释放，否则会造成内存泄漏。
     *
     * Netty 的 io.netty.buffer.ByteBuf 是通过引用计数的方式管理的，如果一个 ByteBuf 没有地方被引用到，需要回收底层内存。默认情况下，当创建完一个 ByteBuf，它的引用为1，然后每次调用 retain() 方法， 它的引用就加一， release() 方法原理是将引用计数减一，减完之后如果发现引用计数为0，则直接回收 ByteBuf 底层的内存。
     */

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);

        print("allocate ByteBuf(9, 100)", buffer);

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", buffer);

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写, 写完 int 类型之后，写指针增加4
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // write 方法改变写指针, 写完之后写指针等于 capacity 的时候，buffer 不可写
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        // write 方法改变写指针，写的时候发现 buffer 不可写则开始扩容，扩容之后 capacity 随即改变
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // get 方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        print("getByte()", buffer);


        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        // read 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);

    }

    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ===========" + action + "============");
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());
        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("isWritable(): " + buffer.isWritable());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();
    }
}
