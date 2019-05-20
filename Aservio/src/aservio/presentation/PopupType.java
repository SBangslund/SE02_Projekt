package aservio.presentation;

public enum PopupType {
    FAILURE("FF3100"),
    SUCCESS("#33CCCC");

    private String color;

    PopupType(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }
}
