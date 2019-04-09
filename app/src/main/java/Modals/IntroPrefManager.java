package Modals;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class IntroPrefManager {
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String PREF_NAME = "Walshot-Welcome";
    int PRIVATE_MODE = 0;
    Context _context;
    Editor editor;
    SharedPreferences pref;

    public IntroPrefManager(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        this.editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        this.editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return this.pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
