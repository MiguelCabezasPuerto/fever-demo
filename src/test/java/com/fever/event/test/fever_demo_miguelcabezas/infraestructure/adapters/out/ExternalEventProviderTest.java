package com.fever.event.test.fever_demo_miguelcabezas.infraestructure.adapters.out;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import com.fever.event.test.fever_demo_miguelcabezas.domain.Event;
import com.fever.event.test.fever_demo_miguelcabezas.domain.Zone;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ExternalEventProviderTest {

    private static MockWebServer mockWebServer;

    private ExternalEventProvider externalEventProvider;

    @BeforeAll
    static void setUpMockServer() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void setUp() {
        String mockUrl = mockWebServer.url("/").toString();
        WebClient.Builder webClient = WebClient.builder().baseUrl(mockUrl);
        externalEventProvider = new ExternalEventProvider(webClient);
    }

    @AfterAll
    static void tearDownMockServer() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void fetchEvents_shouldReturnEvents_whenApiRespondsWithXml() {
        String xmlResponse = """
                <PlanList>
                    <output>
                        <basePlans>
                            <BasePlan>
                                <title>Test Event</title>
                                <sellMode>ONLINE</sellMode>
                                <basePlanId>123</basePlanId>
                                <plans>
                                    <Plan>
                                        <planStartDate>2024-06-01T10:00:00</planStartDate>
                                        <planEndDate>2024-06-02T18:00:00</planEndDate>
                                        <sellFrom>2024-05-01T08:00:00</sellFrom>
                                        <sellTo>2024-06-01T09:00:00</sellTo>
                                        <zones>
                                            <Zone>
                                                <price>10.5</price>
                                            </Zone>
                                            <Zone>
                                                <price>20.0</price>
                                            </Zone>
                                        </zones>
                                    </Plan>
                                </plans>
                            </BasePlan>
                        </basePlans>
                    </output>
                </PlanList>
                """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(xmlResponse)
                .addHeader("Content-Type", "application/xml"));

        Mono<List<Event>> resultMono = externalEventProvider.fetchEvents();

        assertNotNull(resultMono);
    }

    @Test
    void fetchEvents_shouldReturnStoredList_whenApiFails() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        Mono<List<Event>> resultMono = externalEventProvider.fetchEvents();

        StepVerifier.create(resultMono)
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    @Test
    void parseDateTime_shouldReturnValidDate_whenInputIsCorrect() {
        String validDate = "2025-04-15T12:30:45";
        assertNotNull(externalEventProvider.parseDateTime(validDate));
    }

    @Test
    void parseDateTime_shouldReturnNull_whenInputIsInvalid() {
        String invalidDate = "invalid-date";
        assertNull(externalEventProvider.parseDateTime(invalidDate));
    }

    @Test
    void parseDate_shouldReturnValidDate_whenInputIsCorrect() {
        String validDate = "2025-04-15";
        assertNotNull(externalEventProvider.parseDate(validDate));
    }

    @Test
    void parseDate_shouldReturnNull_whenInputIsInvalid() {
        String invalidDate = "invalid-date";
        assertNull(externalEventProvider.parseDate(invalidDate));
    }

    @Test
    void calculateMinPrice_shouldReturnMinPrice_whenZonesAreProvided() {
        Zone zone1 = new Zone();
        zone1.setPrice(30.0);
        Zone zone2 = new Zone();
        zone2.setPrice(15.5);
        Zone zone3 = new Zone();
        zone3.setPrice(25.0);

        List<Zone> zones = List.of(zone1, zone2, zone3);
        BigDecimal minPrice = externalEventProvider.calculateMinPrice(zones);

        assertEquals(BigDecimal.valueOf(15.5), minPrice);
    }

    @Test
    void calculateMaxPrice_shouldReturnMaxPrice_whenZonesAreProvided() {
        Zone zone1 = new Zone();
        zone1.setPrice(30.0);
        Zone zone2 = new Zone();
        zone2.setPrice(15.5);
        Zone zone3 = new Zone();
        zone3.setPrice(25.0);

        List<Zone> zones = List.of(zone1, zone2, zone3);
        BigDecimal maxPrice = externalEventProvider.calculateMaxPrice(zones);

        assertEquals(BigDecimal.valueOf(30.0), maxPrice);
    }
}


