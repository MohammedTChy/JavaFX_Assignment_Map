//PROG2 VT2020, Inlämmning 2
//Mohammed Tahmid Chowdhury moch8386

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Objects;


public abstract class Place extends Polygon {


    private final Category commuteType;
    private boolean clicked;
    private final double coordianteX;
    private final double coordinateY;
    private final String name;




    public Place(String commuteType, double coordianteX, double y, String name) {
        super(coordianteX, y, coordianteX -15, y-30, coordianteX +15, y-30);

        if(commuteType == null){
            this.commuteType = Category.valueOf("None");
        }else{
            this.commuteType = Category.valueOf(commuteType);
        }
        this.coordianteX = coordianteX;
        this.coordinateY = y;
        this.name= name;
        triangleColour();

    }// Super Place 


    public String getName(){
        return name;
    }


    public void triangleColour(){
        setFill(getColour());
    }//void triangle colour

    public void triangleClicked(){
        setFill(Color.YELLOW);
    }// voiid traingle clicked

    public void hideTriangle(){
        setFill(Color.TRANSPARENT);
    }

    public boolean getClicked(){
        return clicked;
    }

    public void setVisible(){
        clicked =! clicked;
        if (clicked)
            triangleClicked();
        else
            triangleColour();

    }//Olika typer av klick onödig men orkade ta bort för behövde leta ordenligt vart allt ligger och möjligtvis tänka om helt

    public void setVisibleSpecific(boolean on){
        clicked = on;
        if (clicked)
            triangleClicked();
        else
            triangleColour();
    }//set clicked

    public int getCoordianteX(){
        return (int) coordianteX;
    }//X cordinate
    public int getCoordinateY(){
        return (int) coordinateY;
    }//Y coordinate

    public Color getColour() {
        return commuteType.getColor();
    }//get colour

    public String getcommuteType(){
        return commuteType.getTypeName();
    }/// getCommuteType

    public abstract String getInfo();//Sparar den unika sättet den ska visa info

    public abstract String toString();//debugger för att se om den finns där

    public abstract String getBelong();//om objektet är från named eller desribed

    public abstract String getDescrip();//Kunna hämta den lättare när man klickar save

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Double.compare(place.coordianteX, coordianteX) == 0 &&
                Double.compare(place.coordinateY, coordinateY) == 0 &&
                commuteType == place.commuteType &&
                Objects.equals(name, place.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commuteType, coordianteX, coordinateY, name);
    }
}//Class


