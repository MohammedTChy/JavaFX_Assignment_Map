//PROG2 VT2020, Inlämmning 2
//Mohammed Tahmid Chowdhury moch8386

public class NamedPlace extends Place {

    public NamedPlace(String commuteType, double x, double y, String name){
        super(commuteType,x, y, name);
    }


    @Override
    public String getInfo() {
        String str = "Name: " + getName() +"["+ getCoordianteX() + "," + getCoordinateY()+"]";
        return str;
    }

    @Override
    public String toString() {
        String str = getcommuteType()+","+ getCoordianteX()+","+ getCoordinateY()+","+getName();
        return str;
    }

    @Override
    public String getBelong() {
        String str = "Named";
        return str;
    }

    @Override
    public String getDescrip() {
        return null;
    }


}//Public class


