package com.vic.BFSOrDFS;

import java.util.HashMap;
import java.util.LinkedList;

public class DFSExample {
    public static void main(String[] args) {
        //构造各顶点
        LinkedList<Character> list_u = new LinkedList<Character>();
        list_u.add('v');
        list_u.add('x');
        LinkedList<Character> list_v = new LinkedList<Character>();
        list_v.add('y');
        LinkedList<Character> list_y = new LinkedList<Character>();
        list_y.add('x');
        LinkedList<Character> list_x = new LinkedList<Character>();
        list_x.add('v');
        LinkedList<Character> list_w = new LinkedList<Character>();
        list_w.add('y');
        list_w.add('z');
        LinkedList<Character> list_z = new LinkedList<Character>();
        //构造图
        HashMap<Character, LinkedList<Character>> graph = new HashMap<Character, LinkedList<Character>>();
        graph.put('u', list_u);
        graph.put('v', list_v);
        graph.put('y', list_y);
        graph.put('x', list_x);
        graph.put('w', list_w);
        graph.put('z', list_z);

        HashMap<Character, Boolean> visited = new HashMap<Character, Boolean>();
        //调用深度优先遍历方法
        dfs(graph, visited);
    }

    private static void dfs(HashMap<Character, LinkedList<Character>> graph, HashMap<Character, Boolean> visited) {
        visit(graph, visited, 'u');// 为了和图中的顺序一样，我认为控制了DFS先访问u节点
        visit(graph, visited, 'w');
    }

    //通过一个全局变量count记录了进入每个节点和离开每个节点的时间
    static int count;
    private static void visit(HashMap<Character, LinkedList<Character>> graph, HashMap<Character, Boolean> visited,
                              char start) {
        if (!visited.containsKey(start)) {
            count++;
            System.out.println("The time into element " + start + ":" + count);// 记录进入该节点的时间
            visited.put(start, true);
            for (char c : graph.get(start)) {
                if (!visited.containsKey(c)) {
                    visit(graph, visited, c);// 递归访问其邻近节点
                }
            }
            count++;
            System.out.println("The time out element " + start + ":" + count);// 记录离开该节点的时间
        }
    }
}

