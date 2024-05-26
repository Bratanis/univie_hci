package at.ac.univie.dailykind;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarFragment extends Fragment {

    private static final String TAG = "CalendarFragment";
    private static final int CALENDAR_PERMISSION_CODE = 100;

    private TimePicker timePicker;
    private Button buttonAddEvent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        timePicker = view.findViewById(R.id.timePicker);
        buttonAddEvent = view.findViewById(R.id.buttonAddEvent);

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCalendarPermission()) {
                    addEventToCalendar();
                } else {
                    requestCalendarPermission();
                }
            }
        });

        return view;
    }

    private boolean checkCalendarPermission() {
        int readCalendar = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CALENDAR);
        int writeCalendar = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_CALENDAR);
        return readCalendar == PackageManager.PERMISSION_GRANTED && writeCalendar == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCalendarPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, CALENDAR_PERMISSION_CODE);
    }

    private void addEventToCalendar() {
        Calendar calendar = Calendar.getInstance();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        long startMillis = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        long endMillis = calendar.getTimeInMillis();

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "Be Kind with DailyKind!");
        values.put(CalendarContract.Events.DESCRIPTION, "Bereite dir und deinen Mitmenschen freude indem du deine DailyKind aufgabe erledigst.");
        values.put(CalendarContract.Events.CALENDAR_ID, getPrimaryCalendarId());
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        values.put(CalendarContract.Events.RRULE, "FREQ=DAILY");

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Keine Berechtigung zum Schreiben im Kalender");
            return;
        }

        Uri uri = requireContext().getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);

        if (uri != null) {
            long eventId = ContentUris.parseId(uri);
            Log.i(TAG, "Ereignis hinzugefügt, ID: " + eventId);
            Toast.makeText(getContext(), "Ereignis hinzugefügt", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Fehler beim Hinzufügen des Ereignisses");
            Toast.makeText(getContext(), "Fehler beim Hinzufügen des Ereignisses", Toast.LENGTH_SHORT).show();
        }
    }

    private long getPrimaryCalendarId() {
        String[] projection = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.IS_PRIMARY
        };

        Cursor cursor = requireContext().getContentResolver().query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                CalendarContract.Calendars.IS_PRIMARY + " = 1",
                null,
                null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                long calendarId = cursor.getLong(0);
                cursor.close();
                return calendarId;
            }
            cursor.close();
        }

        // Fallback: Falls kein Primärkalender gefunden wird, den ersten verfügbaren Kalender verwenden
        cursor = requireContext().getContentResolver().query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                null,
                null,
                null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                long calendarId = cursor.getLong(0);
                cursor.close();
                return calendarId;
            }
            cursor.close();
        }

        Log.e(TAG, "Kein Kalender gefunden");
        return -1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALENDAR_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addEventToCalendar();
            } else {
                Toast.makeText(getContext(), "Kalenderberechtigung abgelehnt", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
