package erkewesa.org.findnlearn.data;

import android.provider.BaseColumns;

public final class StudiengangContract {

    private StudiengangContract(){}

    public static final class StudiengangEntry implements BaseColumns{

        public final static String TABLE_NAME="User";

        public final static String _ID=BaseColumns._ID;

        public final static String COLUMN_RANDOMKEY="RandomKey";

        public final static String COLUMN_USERNAME="Username";
    }
}
