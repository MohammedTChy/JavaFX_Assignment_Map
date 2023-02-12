//PROG2 VT2020, Inlämmning 2
//Mohammed Tahmid Chowdhury moch8386

public class DescribedPlace extends Place {



    private final String description;


    public DescribedPlace(String commuteType, double x, double y, String name, String description){
        super(commuteType, x, y, name);

        this.description = description;
    }//Public class



    public String getDescription() {
        return description;
    }

    @Override
    public String getInfo() {
        String str = "Name: " + getName() +"["+ getCoordianteX() + "," + getCoordinateY()+"]"+ "\n"+ "Description: "+getDescription();
        return str;
    }

    @Override
    public String toString() {
        String str = getcommuteType()+","+ getCoordianteX()+","+ getCoordinateY()+","+getName()+","+getDescription();
        return str;
    }

    @Override
    public String getBelong() {
        String str = "Named";
        return str;
    }

    @Override
    public String getDescrip() {
        return description;
    }


}
