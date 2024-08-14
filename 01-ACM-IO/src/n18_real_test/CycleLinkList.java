package n18_real_test;


public class CycleLinkList {

    // lc141，判断是否存在环形链表，O(1)空间复杂度
    //思路
    //如果存在环路，走的快的人一定能追上走的慢的人
    //解题方法
    //快指针每次移动两步，慢指针每次移动一步，如果快指针能追上慢指针，代表有环，否则会到链表末尾即结束

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

    // lc142.给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
    // 链表长度=a(启示长度)+b(环长度)
    // f = 2s = s+nb
    // f=2nb, s=nb
    // k=a+nb, s=nb
    // s+a == 0+a
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                while (slow != head) {
                    slow = slow.next;
                    head = head.next;
                }
                return slow;
            }
        }
        return null;
    }


    // lc160.相交链表找交点
    // 减去|A-B|差值，同一长度，从头在来就可会和
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }
}


class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}