//PROG2 VT2020, Inlämmning 2
//Mohammed Tahmid Chowdhury moch8386

import javafx.scene.paint.Color;

enum Category {

    Bus("Bus",Color.RED), Underground("Underground",Color.BLUE) ,Train("Train",Color.GREEN), None("None", Color.BLACK) ;


    private final Color color;
    private final String name;

    Category(String name ,Color color){

        this.color= color;
        this.name =name;

    }

    public String getTypeName(){
        return name;
    }

    public Color getColor() {
        return color;
    }


}
