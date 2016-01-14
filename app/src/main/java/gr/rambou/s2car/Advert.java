package gr.rambou.s2car;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

@ParseClassName("Advert")
public class Advert extends ParseObject {

    public boolean isFavorite = false;

    public String getVehicleType() {
        return getString("VehicleType");
    }

    public void setVehicleType(String value) {
        put("VehicleType", value);
    }

    public String getVehicleBrand() {
        return getString("VehicleBrand");
    }

    public void setVehicleBrand(String value) {
        put("VehicleBrand", value);
    }

    public String getVehicleModel() {
        return getString("VehicleModel");
    }

    public void setVehicleModel(String value) {
        put("VehicleModel", value);
    }

    public String getVehiclePurchaseYear() {
        return getString("VehiclePurchaseYear");
    }

    public void setVehiclePurchaseYear(String value) {
        put("VehiclePurchaseYear", value);
    }

    public int getVehicleKm() {
        return getInt("VehicleKm");
    }

    public void setVehicleKm(int value) {
        put("VehicleKm", value);
    }

    public int getVehicleCc() {
        return getInt("VehicleCc");
    }

    public void setVehicleCc(int value) {
        put("VehicleCc", value);
    }

    public int getVehicleHp() {
        return getInt("VehicleHp");
    }

    public void setVehicleHp(int value) {
        put("VehicleHp", value);
    }

    public String getVehicleFuel() {
        return getString("VehicleFuel");
    }

    public void setVehicleFuel(String value) {
        put("VehicleFuel", value);
    }

    public String getVehiclePrice() {
        return getString("VehiclePrice");
    }

    public void setVehiclePrice(String value) {
        put("VehiclePrice", value);
    }

    public String getVehicleDescription() {
        return getString("VehicleDescription");
    }

    public void setVehicleDescription(String value) {
        put("VehicleDescription", value);
    }

    public String getAdvertType() {
        return getString("AdvertType");
    }

    public void setAdvertType(String value) {
        put("AdvertType", value);
    }

    public ParseGeoPoint getLocation() {
        return (get("coords") == null) ? new ParseGeoPoint() : (ParseGeoPoint) get("coords");
    }

    public void setLocation(ParseGeoPoint value) {
        put("coords", value);
    }

    public byte[] getPhoto() {
        try {
            return getParseFile("Photo").getData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setPhoto(ParseFile file) {
        put("Photo", file);
    }

    public String toString() {
        return
                getVehicleKm() + "," +
                        getVehicleType() + "," +
                        getVehiclePurchaseYear() + "," +
                        getVehiclePrice() + "," +
                        getVehicleModel() + "," +
                        getVehicleHp() + "," +
                        getVehicleFuel() + "," +
                        getVehicleDescription() + "," +
                        getVehicleCc() + "," +
                        getVehicleBrand() + "," +
                        getLocation() + "," +
                        getAdvertType();
    }
}