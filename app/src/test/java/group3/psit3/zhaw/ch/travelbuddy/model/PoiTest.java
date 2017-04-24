package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.android.gms.maps.model.LatLng;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PoiTest {

    private Poi testObj;
    public static String response = "{\n"+
            "  \"NextPOI\": {\n"+
            "    \"Id\": 15,\n"+
            "    \"Description\": \"Old church, beautiful, has a tower to go up.\",\n"+
            "    \"VisitDuration\": 2400,\n"+
            "    \"Longitude\": 47.379049,\n"+
            "    \"Latitude\": 8.540338\n"+
            "  },\n"+
            "  \"RouteToNextPOI\": [\n"+
            "    {\n"+
            "      \"Longitude\": 8.00001,\n"+
            "      \"Latitude\": 47.99998\n"+
            "    },\n"+
            "    {\n"+
            "      \"Longitude\": 7.99972,\n"+
            "      \"Latitude\": 47.99993\n"+
            "    },\n"+
            "    {\n"+
            "      \"Longitude\": 7.99962,\n"+
            "      \"Latitude\": 47.99992\n"+
            "    },\n"+
            "    {\n"+
            "      \"Longitude\": 7.99953,\n"+
            "      \"Latitude\": 47.99993\n"+
            "    }]\n"+
            "}";

    @Before
    public void setUp() throws Exception {
        this.testObj = PoiResponse.fromJson(response);
    }

    @Test
    public void testParseFromJsonReturnsValidPoi() throws Exception {
        assertThat(testObj.getId(), is(15));
        assertThat(testObj.getDescription(), is("Old church, beautiful, has a tower to go up."));
        assertThat(testObj.getVisitDuration(), is(2400));
        assertThat(testObj.getLongitude(), is(47.379049));
        assertThat(testObj.getLatitude(), is(8.540338));
    }

    @Test
    public void testParseFromJsonHasValidRoute() throws Exception {
        List<LatLng> route = testObj.getRoute();
        assertThat(route.size(), is(4));
        assertThat(route.get(2), is(new LatLng(47.99992, 7.99962)));
    }

}