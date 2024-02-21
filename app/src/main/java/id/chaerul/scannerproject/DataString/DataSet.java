package id.chaerul.scannerproject.DataString;

public class DataSet {
    String text;
    int image;
    int id;

    public DataSet(String text, int image,int id){
        this.image = image;
        this.text = text;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }


}
