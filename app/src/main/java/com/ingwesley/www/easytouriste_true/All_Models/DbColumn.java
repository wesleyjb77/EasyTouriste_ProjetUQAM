package com.ingwesley.www.easytouriste_true.All_Models;

import android.provider.BaseColumns;

/**
 * Created by delaroy on 5/11/17.
 */
public class DbColumn {

    public static final class Endroits_Column implements BaseColumns {

        public static final String TABLE_NAME = "endroits";
        public static final String NOM_END = "NOM_END";
        public static final String ILLUSTRATION_END = "ILLUSTRATION_END";
        public static final String DESCRIPTION_END = "DESCRIPTION_END";
        public static final String ADRESSE_END = "ADRESSE_END";
        public static final String TELEPHONE = "TELEPHONE";
        public static final String EMAIL = "EMAIL";
    }
}
