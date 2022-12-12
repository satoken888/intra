package jp.co.kawakyo.intra.Utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GoogleCalendarUtils {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final String SERVICE_CREDENTIALS_FILE_PATH = "client_secrets.json";
    private static final String CALENDAR_ID = "bocr12n5mikihkremtql5e2t30@group.calendar.google.com";

    /**
     * Googleの認証情報取得処理
     * @param HTTP_TRANSPORT
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private static HttpRequestInitializer getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException, GeneralSecurityException {

        URL url = ClassLoader.getSystemResource(SERVICE_CREDENTIALS_FILE_PATH);
        URI uri = URI.create(url.toString());
        Path path = Paths.get(uri);

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(path.toString()))
                .createScoped(Collections.singleton(CalendarScopes.CALENDAR_EVENTS));

        return new HttpCredentialsAdapter(credentials);
    }

    /**
     * 直近10イベント情報出力する（現在は標準出力）
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String viewEvents10() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, GsonFactory.getDefaultInstance(),
                getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list(CALENDAR_ID).setMaxResults(10)
                .setTimeMin(now).setOrderBy("startTime").setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }

        return "ok";
    }

    /**
     * イベント追加
     * 
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String addEvent() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, new GsonFactory(), getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        EventDateTime startEventDateTime = new EventDateTime().setDateTime(new DateTime(new Date())); // イベント開始日時
        EventDateTime endEventDateTime = new EventDateTime().setDateTime(new DateTime(new Date())); // イベント終了日時

        String summary = "テスト";
        String description = "テスト";

        Event event = new Event()
                .setSummary(summary)
                .setDescription(description)
                .setColorId("2") // green
                .setStart(startEventDateTime)
                .setEnd(endEventDateTime);

        event = service.events().insert(CALENDAR_ID, event).execute();
        return event.getId();
    }
}