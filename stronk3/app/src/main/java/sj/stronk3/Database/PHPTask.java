package sj.stronk3.Database;

/**
 * Created by Corsair on 6-6-2016.
 */
public enum PHPTask {
    INSERT_WEIGHT("http://sjacobs.me/stronk/insertweight.php", new String[]{"weight"});

    private String url;
    private String[] params;
    public static int count = PHPTask.values().length;

    PHPTask(String url, String[] params) {
        this.url = url;
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public String[] getParams() {
        return params;
    }
}
