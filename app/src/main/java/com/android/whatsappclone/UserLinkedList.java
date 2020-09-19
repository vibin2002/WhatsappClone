package com.android.whatsappclone;

public class UserLinkedList {
    Node head;
    int count = 0;

    public void insertItem(Users item) {
        if (count == 0) {
            head = new Node();
            head.user = item;
            head.link = null;
            count++;
        } else {
            Node n = new Node();
            n.user = item;
            n.link = head;
            head = n;
        }
    }

    public Users getUserr(int n) {
        Node z = head;
        for (int i = 0; i < n; i++) {
            z = z.link;
        }
        return z.user;
    }

    class Node {
        private Users user;
        private Node link;
    }
}

