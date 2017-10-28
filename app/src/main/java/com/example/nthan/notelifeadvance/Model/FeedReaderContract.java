package com.example.nthan.notelifeadvance.Model;

import android.provider.BaseColumns;

/**
 * Created by nthan on 10/13/2017.
 */

public class FeedReaderContract {
    public FeedReaderContract() {
    }

    public static class FeedContent implements BaseColumns {
        public static final String TABLE_NAME = "content";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENTNOTE = "content_note";
        public static final String COLUMN_NAME_DATECREATENOTE = "date_create";
        public static final String COLUMN_NAME_DATEUPDATELAST = "date_update_last";
        public static final String COLUMN_NAME_COLORNOTE = "color";
    }
}
