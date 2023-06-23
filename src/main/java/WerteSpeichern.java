public class WerteSpeichern {

    private String landesCode; // Der gespeicherte Ländercode
    private String zeitSpeichern; // Die gespeicherte Zeit
    private String gmtZeitSpeichern; // Die gespeicherte GMT-Zeit

    private String LandesName;

    public WerteSpeichern() {
        // Konstruktor
    }
    public String getLandesName() {
        return LandesName;
    }

    public void setLandesName(String landesName) {
        LandesName = landesName;
    }

    private int numberSpeicher;



    public String getLandesCode() {
        return landesCode;
    }

    public void setLandesCode(String landesCode) {
        this.landesCode = landesCode;
    }

    public String getZeitSpeichern() {
        return zeitSpeichern;
    }

    public void setZeitSpeichern(String zeitSpeichern) {
        this.zeitSpeichern = zeitSpeichern;
    }

    public String getGmtZeitSpeichern() {
        return gmtZeitSpeichern;
    }

    public void setGmtZeitSpeichern(String gmtZeitSpeichern) {
        this.gmtZeitSpeichern = gmtZeitSpeichern;
    }

    public int getNumberSpeicher() {
        return numberSpeicher;
    }

    public void setNumberSpeicher(int numberSpeicher) {
        this.numberSpeicher = numberSpeicher;
    }
}

