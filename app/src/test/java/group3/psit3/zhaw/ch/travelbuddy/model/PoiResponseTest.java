package group3.psit3.zhaw.ch.travelbuddy.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PoiResponseTest {

    private PoiResponse testObj;
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
        Poi poi = testObj.getPoi();

        assertThat(poi.getId(), is(15));
        assertThat(poi.getDescription(), is("Old church, beautiful, has a tower to go up."));
        assertThat(poi.getVisitDuration(), is(2400));
        assertThat(poi.getLongitude(), is(47.379049));
        assertThat(poi.getLatitude(), is(8.540338));
    }

    @Test
    public void testParseFromJsonHasValidRoute() throws Exception {
        Route route = testObj.getRoute();
        assertThat(route.getPoints().size(), is(4));
        assertThat(route.getPoints().get(2), is(new LatLong(47.99992, 7.99962)));
    }

}