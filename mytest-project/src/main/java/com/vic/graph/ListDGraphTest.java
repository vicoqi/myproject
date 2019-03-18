package com.vic.graph;

import java.util.Iterator;

/**
 * @Auther: wqp
 * @Date: 2018/12/13 16:34
 * @Description:
 */
public class ListDGraphTest {

    public static void main(String[] args) {
        DGraph<String> mDG = new ListDgraph<>();
        Utils.log("===============add v=================");

        mDG.add("1");
        mDG.add("2");
        mDG.add("3");
        mDG.add("4");
        mDG.add("5");
        mDG.add("6");
        mDG.add("7");
        mDG.add("8");

        Utils.log("===============add edge=================");

        mDG.add(new Edge<String>("1", "2"));
        mDG.add(new Edge<String>("1", "3"));
        mDG.add(new Edge<String>("2", "4"));
        mDG.add(new Edge<String>("2", "5"));
        mDG.add(new Edge<String>("3", "6"));
        mDG.add(new Edge<String>("3", "7"));
        mDG.add(new Edge<String>("4", "8"));
        mDG.add(new Edge<String>("8", "5"));
        mDG.add(new Edge<String>("6", "7"));

        Utils.log("===============test travelling=================");

        Iterator<String> it = mDG.iterator(DGraph.ITERATOR_TYPE_BFS, "1");
        while(it.hasNext()) {
            String s = it.next();
            Utils.log("next : %s", s);
        }

        Utils.log("===============test travelling2=================");

        it = mDG.iterator(DGraph.ITERATOR_TYPE_BFS, "2");
        while(it.hasNext()) {
            String s = it.next();
            Utils.log("next : %s", s);
        }

        Utils.log("===============test others=================");

        mDG.get(0);

        mDG.get(0, 1);

        mDG.remove("6");

        mDG.remove(new Edge<String>("3", "7"));
    }
}
