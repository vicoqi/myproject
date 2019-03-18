package com.vic.area;

import lombok.AllArgsConstructor;

/**
 * @Auther: wqp
 * @Date: 2019/3/7 17:03
 * @Description:
 * 面积碰撞检测
 * 参考链接
 *
 *
 * https://blog.csdn.net/dapengbusi/article/details/50516126
 *
 *
 *只需要判断该点是否在上下两条边和左右两条边之间就行，判断一个点是否在两条线段之间夹着，
 * 就转化成，判断一个点是否在某条线段的一边上，就可以利用叉乘的方向性，来判断夹角是否超过了180度 如下图：
 * 只要判断(p1 p2 X p1 p ) * (p3 p4 X p3 p1)  >= 0 就说明p在p1p2,p3p4中间夹着，同理计算另两边就可以了
 *
 * 最后就是只需要判断 (p1 p2 X p1 p ) * (p3 p4 X p3 p1)  >= 0  && (p2 p3 X p2 p ) * (p4 p1 X p4 p) >= 0 ;
 */
public class AreaCollsion {

    public static void main(String[] args) {
        AreaCollsion areaCollsion = new AreaCollsion();
        Point p  = new Point(3, 3);
        System.out.println(areaCollsion.IsPointInMatrix1(p));
    }

    // 计算 |p1 p2| X |p1 p|
    public int GetCross(Point p1, Point p2, Point p) {
        return (p2.x - p1.x) * (p.y - p1.y) - (p.x - p1.x) * (p2.y - p1.y);
    }

    //判断点是否在5X5 以原点为左下角的正方形内（便于测试）
    public boolean IsPointInMatrix(Point p) {
        Point p1  = new Point(0, 5);
        Point p2 = new Point(0, 0);
        Point p3 = new Point(5, 0);
        Point p4 = new Point(5, 5);
        return GetCross(p1, p2, p) * GetCross(p3, p4, p) >= 0 && GetCross(p2, p3, p) * GetCross(p4, p1, p) >= 0;
    }

    public boolean IsPointInMatrix1(Point p) {
        Point p1  = new Point(1, 5);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(5, 1);
        Point p4 = new Point(5, 5);
        return GetCross(p1, p2, p) * GetCross(p3, p4, p) >= 0 && GetCross(p2, p3, p) * GetCross(p4, p1, p) >= 0;
    }

    @AllArgsConstructor
    static class Point {
        int x, y;
    }

    static class Rectangle {

    }
}
