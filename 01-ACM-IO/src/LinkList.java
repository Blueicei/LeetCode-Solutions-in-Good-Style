
public class LinkList {
    static class LinkNode{
        int val;
        LinkNode next;

        LinkNode(int val){
            this.val = val;
        }

    }
    static void test(){
        System.out.println("hello");
    }
    public void merge(LinkNode h1, LinkNode h2){
        LinkNode temp = new LinkNode(0);
        LinkNode head = temp;
        while(h1!=null && h2!= null){
            if(h1.val<h2.val){
                head.next = h1;
                h1 = h1.next;
            } else{
                head.next = h2;
                h2 = h2.next;
            }
            head = head.next;
        }
        if(h1!=null){
            head.next=h1;
        }
        if(h2!=null){
            head.next=h2;
        }
        temp = temp.next;
        while(temp!=null){
            System.out.println(temp.val);
            temp = temp.next;
        }


    }

    public static void main(String[] args){
        test();
        int[] arr1 = {1,2,4};
        int[] arr2 = {1,3,4,6};

        LinkNode h1 = new LinkNode(0);
        LinkNode t1 = h1;
        for(int x:arr1){
            LinkNode n = new LinkNode(x);
            t1.next = n;
            t1 = t1.next;
        }
        LinkNode h2 = new LinkNode(0);
        LinkNode t2 = h2;
        for(int x:arr2){
            LinkNode n = new LinkNode(x);
            t2.next = n;
            t2 = t2.next;
        }
        h1=h1.next;
        h2=h2.next;
        LinkList linkList = new LinkList();
        linkList.merge(h1, h2);
    }
}
