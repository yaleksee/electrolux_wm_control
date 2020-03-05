package com.electrolux.utils;

public class Control {

    private static boolean isReady = false;

    public boolean isProgramSelected(){
        return false;
    }

    public boolean isDoorClosed(){
        return false;
    }

    public boolean isExistingWater(){
        return false;
    }

    public boolean isPowerInNormal(){
        return false;
    }

    public boolean isActivate(){
        return false;
    }

    public boolean isNumberOfWorkCircle(){
        return false;
    }

    public Display activate(){
        return new Display();
    }

    public Display selectProgram(){
        return new Display();
    }

    public Display displayInformation(){
        return new Display();
    }

    public Display startProgramm(){
        return new Display();
    }

    public Display completeProgramm(){
        return new Display();
    }

    public Display startRegimeMode(){
        return new Display();
    }

    public Display completeRegimMode(){
        return new Display();
    }
}
