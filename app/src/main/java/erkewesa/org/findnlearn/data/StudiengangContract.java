package erkewesa.org.findnlearn.data;

import android.provider.BaseColumns;

public final class StudiengangContract {

    private StudiengangContract(){}

    public static final class StudiengangEntry implements BaseColumns{

        public final static String TABLE_NAME="studiengang";

        public final static String _ID=BaseColumns._ID;

        public final static String COLUMN_STUDIENGANG="studiengang";

        public final static String COLUMN_SEMESTER="semester";
    }
}
