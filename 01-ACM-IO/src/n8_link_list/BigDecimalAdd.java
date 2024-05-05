package n8_link_list;

public class BigDecimalAdd {
    // 链表可以表示大数运算，实现栈，队列
    // lc2.链表实现大数相加，为了方便是倒叙，
    // 输入：l1 = [2,4,3], l2 = [5,6,4]
    // 输出：[7,0,8]
    // 解释：342 + 465 = 807.

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode cur = head;
        int flag = 0;
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + flag;
            if (sum > 9) {
                l1.val = sum - 10;
                flag = 1;
            } else {
                l1.val = sum;
                flag = 0;
            }
            cur.next = l1;
            cur = cur.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            int sum = l1.val + flag;
            if (sum > 9) {
                l1.val = sum - 10;
                flag = 1;
            } else {
                l1.val = sum;
                flag = 0;
            }
            cur.next = l1;
            cur = cur.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            int sum = l2.val + flag;
            if (sum > 9) {
                l2.val = sum - 10;
                flag = 1;
            } else {
                l2.val = sum;
                flag = 0;
            }
            cur.next = l2;
            cur = cur.next;
            l2 = l2.next;
        }
        if (flag == 1) {
            ListNode temp = new ListNode(1);
            cur.next = temp;
            cur = cur.next;
        }
        return head.next;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
