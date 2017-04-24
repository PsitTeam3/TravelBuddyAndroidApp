package group3.psit3.zhaw.ch.travelbuddy.util;

import com.google.android.gms.maps.model.LatLng;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;
import org.junit.Before;
import org.junit.Test;

import static group3.psit3.zhaw.ch.travelbuddy.util.UrlBuilder.BASE_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UrlBuilderTest {

    private UrlBuilder testObj;

    @Before
    public void setUp() {
        this.testObj = UrlBuilder.anUrl();
    }

    @Test
    public void currentRoute() throws Exception {
        assertThat(testObj.currentRoute(new LatLng(12, 32)).build(), is(BASE_URL + "/POI/GetRouteToNextPOI?userID=5&currentLatitude=12.0&currentLongitude=32.0"));
    }

    @Test
    public void allTours() throws Exception {
        assertThat(testObj.allTours().build(), is(BASE_URL + "/tours/gettours"));
    }

    @Test
    public void validatePhoto() throws Exception {
        LatLng target = new LatLng(42, 24);
        assertThat(testObj.validatePhoto(target).build(), is(
                String.format(BASE_URL + "/poi/isnextpoiinrange?latitude=%s&longitude=%s",
                        target.latitude, target.longitude)));
    }

    @Test
    public void startTour() throws Exception {
        LatLng location = new LatLng(42, 24);
        Tour tour = new Tour().setId(3);
        assertThat(testObj.startTour(location, tour).build(), is(
                String.format(BASE_URL + "/usertour/startusertour?userID=%s&tourID=%s&currentLatitude=%s&currentLongitude=%s",
                        5, 3, 42.0, 24.0)));
    }

    @Test
    public void poisOfTour() throws Exception {
        Tour tour = new Tour().setId(42);
        assertThat(testObj.poisOfTour(tour).build(), is(BASE_URL + "/poi/getpoisbytour?id=" + 42));
    }
}