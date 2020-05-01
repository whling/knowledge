package whling.knowledge.datastructure;

public class DNode<T> {

    public DNode prev;
    public DNode next;

    public T object;

    public DNode() {
    }

    public DNode(T object) {
        this.object = object;
    }
}
