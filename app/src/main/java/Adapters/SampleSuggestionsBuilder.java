package Adapters;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.cryse.widget.persistentsearch.SearchItem;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;

public class SampleSuggestionsBuilder implements SearchSuggestionsBuilder {
    private static final String PREF_NAME = "myPreference";
    private Context mContext;
    private List<SearchItem> mHistorySuggestions = new ArrayList();

    /* renamed from: Adapters.SampleSuggestionsBuilder$1 */
    class C07531 extends TypeToken<ArrayList<String>> {
        C07531() {
        }
    }

    public SampleSuggestionsBuilder(Context context) {
        this.mContext = context;
        createHistorys();
    }

    private void createHistorys() {
        if (this.mContext.getSharedPreferences(PREF_NAME, 0).contains("history")) {
            ArrayList<String> searchHistory = new ArrayList();
            searchHistory = getArrayList("history");
            Collections.reverse(searchHistory);
            for (String country : (String[]) searchHistory.toArray(new String[searchHistory.size()])) {
                this.mHistorySuggestions.add(new SearchItem(country, country, 0));
            }
        }
    }

    public Collection<SearchItem> buildEmptySearchSuggestion(int maxCount) {
        List<SearchItem> items = new ArrayList();
        if (this.mHistorySuggestions != null) {
            items.addAll(this.mHistorySuggestions);
        }
        return items;
    }

    public Collection<SearchItem> buildSearchSuggestion(int maxCount, String query) {
        List<SearchItem> items = new ArrayList();
        for (SearchItem item : this.mHistorySuggestions) {
            if (item.getValue().startsWith(query)) {
                items.add(item);
            }
        }
        return items;
    }

    public void saveArrayList(ArrayList<String> list, String key) {
        Editor editor = this.mContext.getSharedPreferences(PREF_NAME, 0).edit();
        editor.putString(key, new Gson().toJson(list));
        editor.apply();
    }

    public ArrayList<String> getArrayList(String key) {
        return (ArrayList) new Gson().fromJson(this.mContext.getSharedPreferences(PREF_NAME, 0).getString(key, null), new C07531().getType());
    }
}
