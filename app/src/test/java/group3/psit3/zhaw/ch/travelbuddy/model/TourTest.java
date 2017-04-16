package group3.psit3.zhaw.ch.travelbuddy.model;

import group3.psit3.zhaw.ch.travelbuddy.MockData;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TourTest {

    @Test
    public void testParseTourListYieldsValidTourList() throws Exception {
        String mockResponse = MockData.TOUR_RESPONSE;
        Tour tour1 = new Tour()
                .setName("Zürich Historical City Tour")
                .setDescription("Tour through the main historical attractions in the city of Zürich")
                .setDetailDescription("The Tour starts at Zürich's main station and heads to the old town. The first destination inside the old town is the Fraumünster church. There you can climb up the 40 meters high tower and enjoy a stunning view over Zürich. And so on and so on until it's night time to visit Langstrasse!!!");
        Tour tour2 = new Tour()
                .setName("Full day tour: Old town and the mountain 'Üetliberg'")
                .setDescription("Lorem ipsum bla bla fasel fasel")
                .setDetailDescription("Jetzt magi langsam nüm und mir gönd di guetä ideeä uuuus ;-) Und wenn du das lisisch, bisch au e armi Sau!!!");
        List<Tour> tours = Tour.listFromJson(mockResponse);

        assertThat(tours.size(), is(2));

        assertThat(tours.get(0).getName(), is(tour1.getName()));
        assertThat(tours.get(0).getDescription(), is(tour1.getDescription()));
        assertThat(tours.get(0).getDetailDescription(), is(tour1.getDetailDescription()));

        assertThat(tours.get(1).getName(), is(tour2.getName()));
        assertThat(tours.get(1).getDescription(), is(tour2.getDescription()));
        assertThat(tours.get(1).getDetailDescription(), is(tour2.getDetailDescription()));
    }

}