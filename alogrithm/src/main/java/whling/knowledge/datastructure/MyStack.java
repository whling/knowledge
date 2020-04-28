package whling.knowledge.datastructure;

public class MyStack {
    Object[] ele;

    int size;

    int curPos;

    public MyStack(int size) {
        this.ele = new Object[size];
        this.size = size;
        this.curPos = 0;
    }


    public void push(Object obj){
        if (curPos < 0) {
            curPos = 0;
        }

        if (curPos < size) {
            ele[curPos++] = obj;
            return;
        }
        throw new RuntimeException("over size");
    }

    public Object pop() {
        if (curPos <= 0) {
            curPos = 0;
            return null;
        }
        return ele[--curPos];
    }


    public static void main(String[] args) {
        MyStack myStack = new MyStack(5);

        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);

        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        myStack.push(4);
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());

    }
}
