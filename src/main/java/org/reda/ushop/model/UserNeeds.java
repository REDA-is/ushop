package org.reda.ushop.model;

import org.antlr.v4.runtime.misc.NotNull;

public class UserNeeds {
    @NotNull
    private float wantsPump;
    private float wantsToDry;
    private float isTired;
    private float wantsToBulk;
    @Override
    public String toString() {
        return "UserNeeds{" +
                "wantsPump=" + wantsPump +
                ", wantsToDry=" + wantsToDry +
                ", isTired=" + isTired +
                ", wantsToBulk=" + wantsToBulk +
                '}';
    }

    // Getters and setters
    public float getWantsPump() { return wantsPump; }
    public void setWantsPump(float wantsPump) { this.wantsPump = wantsPump; }

    public float getWantsToDry() { return wantsToDry; }
    public void setWantsToDry(float wantsToDry) { this.wantsToDry = wantsToDry; }

    public float getIsTired() { return isTired; }
    public void setIsTired(float isTired) { this.isTired = isTired; }

    public float getWantsToBulk() { return wantsToBulk; }
    public void setWantsToBulk(float wantsToBulk) { this.wantsToBulk = wantsToBulk; }
}
