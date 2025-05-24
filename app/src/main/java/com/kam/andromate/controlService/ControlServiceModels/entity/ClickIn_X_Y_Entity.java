package com.kam.andromate.controlService.ControlServiceModels.entity;

public class ClickIn_X_Y_Entity implements ControlServiceEntity{

    private final long x;
    private final long y;

    public ClickIn_X_Y_Entity(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

}
