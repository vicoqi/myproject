package com.vic.algorithm.bizer;

import lombok.AllArgsConstructor;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;

import java.util.LinkedList;

/**
 * @author: wangqp
 * @create: 2020-07-01 15:16
 */
public class CurveTest {
    public void c() {
        //定义向量1
        RealVector value1 = new ArrayRealVector(new Double[]{
                2d,2d,3d
        });
        //定义向量2
        RealVector value2 = new ArrayRealVector(new Double[]{
                3d,3d,3d
        });
        //取向量的模
        System.out.println(value1.getNorm());
        //向量相加
        System.out.println(value1.add(value2));
        //向量相减
        System.out.println(value1.subtract(value2));
        //向量相除
        System.out.println(value1.ebeDivide(value2));
        //向量相乘
        System.out.println(value1.ebeMultiply(value2));
        //向量点积
        System.out.println(value1.dotProduct(value2));
        //向量之间的距离
        System.out.println(value1.getDistance(value2));
        //向量的cos值
        System.out.println(value1.cosine(value2));
        //向量的维度
        System.out.println(value1.getDimension());
        //向量的三个值相加
        System.out.println(value1.getL1Norm());
    }

    public void a(){
        //定义向量1
        RealVector value1 = new ArrayRealVector(new Double[]{
                0d,0d,2d,2d
        });

        //定义向量2
        RealVector value2 = new ArrayRealVector(new Double[]{
                1d,1d,3d,3d
        });
        //取向量的模
        System.out.println(value1.getNorm());
        //向量相加
        System.out.println(value1.add(value2));
        //向量相减
        System.out.println(value1.subtract(value2));
        //向量相除
        System.out.println(value1.ebeDivide(value2));
        //向量相乘
        System.out.println(value1.ebeMultiply(value2));
        //向量点积
        System.out.println(value1.dotProduct(value2));
        //向量之间的距离
        System.out.println(value1.getDistance(value2));
        //向量的cos值
        System.out.println(value1.cosine(value2));
        //向量的维度
        System.out.println(value1.getDimension());
        //向量的三个值相加
        System.out.println(value1.getL1Norm());
    }
    @Test
    public void test() {
        LinkedList<CvPoint> bezierPoint = new LinkedList<>();
        CvPoint[] controlPoint = new CvPoint[4];
//        controlPoint[0] = new CvPoint(50, 60); //起点
//        controlPoint[1] = new CvPoint(130, 200); //控制点
//        controlPoint[2] = new CvPoint(300, 360); //控制点
//        controlPoint[3] = new CvPoint(400, 600); //终点
        controlPoint[0] = new CvPoint(0, 0); //起点
        controlPoint[1] = new CvPoint(50, 50); //控制点
        controlPoint[2] = new CvPoint(50, 50); //控制点
        controlPoint[3] = new CvPoint(100, 0); //终点
        int n = controlPoint.length - 1; //
        int i, r;
        float u;

        // u的步长决定了曲线点的精度
        for (u = 0; u <= 1; u += 0.25) {

            CvPoint[] p = new CvPoint[n + 1];
            for (i = 0; i <= n; i++) {
                p[i] = new CvPoint(controlPoint[i].x, controlPoint[i].y);
            }

            for (r = 1; r <= n; r++) {
                for (i = 0; i <= n - r; i++) {
                    p[i].x = (int) ((1 - u) * p[i].x + u * p[i + 1].x);
                    p[i].y = (int) ((1 - u) * p[i].y + u * p[i + 1].y);
                }
            }
            bezierPoint.add(p[0]);
        }

        for (CvPoint point : bezierPoint) {
            System.out.println(point.x + "," + point.y);
        }
    }

    @AllArgsConstructor
    private class CvPoint {
        public int x;
        public int y;
    }
}

