package n8_link_list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRUCache {
    // 快慢指针技巧,确切地说，叫「同步指针」可能更好一些。两个指针变量同步移动。快慢指针的前进方向相同，且它们步伐的「差」是恒定的
    // 也就是prev和next，这也是一般head不保存第一个，而在头部预留出一个空节点，就是为了能获取到prev
    // 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。

    class ListNode{
        int key;
        int val;
        ListNode next;
        ListNode(int k, int v){
            key = k;
            val = v;
        }
    }

    int size;
    ListNode head;

    public LRUCache(int capacity) {
        size = capacity;
        ListNode h = new ListNode(-1, -1);
        ListNode cur = h;
        while(capacity-->0){
            ListNode n = new ListNode(-1, -1);
            cur.next = n;
            cur = cur.next;
        }
        head = h;
    }

    public int get(int key) {
        ListNode h = head.next;
        ListNode cur = head.next;
        ListNode prev = head;
        while(cur != null && cur.key != -1){
            if(cur.key == key){
                break;
            }
            prev = prev.next;
            cur = cur.next;
        }
        if(cur == null || cur.key == -1) {
            return -1;
        }
        if (cur != h){
            prev.next = prev.next.next;
            cur.next = h;
            head.next = cur;
        }
        return cur.val;
    }

    public void put(int key, int value) {
        ListNode h = head.next;
        ListNode cur = head.next;
        ListNode prev = head;
        while(cur.next != null && cur.key != key && cur.key != -1){
            prev = prev.next;
            cur = cur.next;
        }
        cur.key = key;
        cur.val = value;
        if (cur != h){
            prev.next = prev.next.next;
            cur.next = h;
            head.next = cur;
        }
    }
    public void print() {
        ListNode cur = head.next;
        while(cur != null){
            System.out.print(cur.key + ":" + cur.val +" ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.print();
        lRUCache.put(2, 6); // 缓存是 {1=1}
        lRUCache.put(1, 5); // 缓存是 {1=1, 2=2}
        lRUCache.print();
        lRUCache.put(1, 2); // 缓存是 {1=1, 2=2}
        lRUCache.print();
    }
}
