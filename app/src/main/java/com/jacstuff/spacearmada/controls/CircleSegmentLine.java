package com.jacstuff.spacearmada.controls;

public class CircleSegmentLine {

    private final float x1, y1, x2, y2;

    CircleSegmentLine(float circleX, float circleY, float radius, float angle, String label) {
        this.x1 = circleX;
        this.y1 = circleY;
        float pi = (float) Math.PI;
        float angleInRadians = angle * pi / 180.0f;
        this.x2 = (float) (circleX + radius * Math.cos(angleInRadians));
        this.y2 = (float) (circleY + radius * Math.sin(angleInRadians));
    }


    boolean isRightOf(float pointX, float pointY) {
        return (x2 - x1) * (pointY - y1) - ((y2 - y1) * (pointX - x1)) > 0;
    }

}
