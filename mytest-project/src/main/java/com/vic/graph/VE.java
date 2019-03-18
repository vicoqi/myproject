package com.vic.graph;

import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: wqp
 * @Date: 2018/12/13 17:22
 * @Description:
 */
/**
 * 顶点对象，其中有对应的顶点以及从以此顶点为起点的边
 */
public class VE<V> {
    /**此顶点*/
    private V v;
    /**以此顶点为起点的边的集合，是一个列表，列表的每一项是一条边*/
    private List<Edge<V>> mEdgeList;

    /**
     * 构造一个新的顶点对象
     * @param v
     */
    public VE(V v) {
        this.v = v;
        this.mEdgeList = new LinkedList<Edge<V>>();
        Utils.log("VE construct : %s", v);
    }

    @Override
    public String toString() {
        String ret = String.format("v : %s , list len : %s",
                v, mEdgeList.size());
        return ret;
    }

    public V getV() {
        return v;
    }

    public List<Edge<V>> getmEdgeList() {
        return mEdgeList;
    }

    /**
     * 将一条边添加到边集合中
     * @param e
     */
    public void addEdge(Edge<V> e) {
        Utils.log("add edge : %s", e);
        if(getEdge(e.getDest()) == null) {
            mEdgeList.add(e);
        } else {
            Utils.log("edge exist : %s", e);
        }
    }

    /**
     * 读取某条边
     * @param dest
     * @return
     */
    public Edge<V> getEdge(V dest) {
        Edge<V> ret = null;
        if(dest != null) {
            for(Edge<V> edge : mEdgeList) {
                if(edge.getDest() != null &&
                        dest.equals(edge.getDest())) {
                    Utils.log("get edge : %s", edge);
                    ret = edge;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * 移除某条边
     * @param dest
     * @return
     */
    public Edge<V> removeEdge(V dest) {
        Edge<V> ret = null;
        if(dest != null) {
            for(Edge<V> edge : mEdgeList) {
                if(edge.getDest() != null &&
                        dest.equals(edge.getDest())) {
                    Utils.log("remove edge : %s", edge);
                    ret = edge;
                    mEdgeList.remove(edge);
                    break;
                }
            }
        }
        return ret;
    }
}
