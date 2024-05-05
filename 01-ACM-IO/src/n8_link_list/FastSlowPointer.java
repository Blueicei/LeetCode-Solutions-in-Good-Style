package n8_link_list;

public class FastSlowPointer {
    // lc141，判断是否存在环形链表，O(1)空间复杂度
    //思路
    //如果存在环路，走的快的人一定能追上走的慢的人
    //解题方法
    //快指针每次移动两步，慢指针每次移动一步，如果快指针能追上慢指针，代表有环，否则会到链表末尾即结束
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public boolean hasCycle(ListNode head) {
        ListNode cur = head;
        ListNode prev = head;
        while (cur != null) {
            prev = prev.next;
            if (cur.next == null) break;
            cur = cur.next.next;
            if (cur == prev) return true;
        }
        return false;
    }

}
