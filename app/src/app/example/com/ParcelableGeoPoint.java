package app.example.com;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.maps.GeoPoint;

public class ParcelableGeoPoint implements Parcelable {

     private GeoPoint geoPoint;

     public ParcelableGeoPoint(GeoPoint point) {
          geoPoint = point;
     }

     public GeoPoint getGeoPoint() {
          return geoPoint;
     }

     public int describeContents() {
         return 0;
     }

     public void writeToParcel(Parcel out, int flags) {
         out.writeInt(geoPoint.getLatitudeE6());
         out.writeInt(geoPoint.getLongitudeE6());
     }

     public static final Parcelable.Creator<ParcelableGeoPoint> CREATOR
             = new Parcelable.Creator<ParcelableGeoPoint>() {
         public ParcelableGeoPoint createFromParcel(Parcel in) {
             return new ParcelableGeoPoint(in);
         }

         public ParcelableGeoPoint[] newArray(int size) {
             return new ParcelableGeoPoint[size];
         }
     };

     private ParcelableGeoPoint(Parcel in) {
         int lat = in.readInt();
         int lon = in.readInt();
         geoPoint = new GeoPoint(lat, lon);
     }
 }