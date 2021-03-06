package cn.nukkit.window;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Itsu
 * 
 * @param id ウィンドウid
 * @param title タイトル
 * @param content 内容となる文章
 * @param upButtonText 上となるボタンのテキスト
 * @param downButtonText 下となるボタンのテキスト
 * 
 * <h3>モダルフォームウィンドウ - Jupiter ModalForm API</h3>
 * <p>このクラスはシンプルなフォーム型ウィンドウを提供します。</p>
 * <p>ウィンドウの作成には他のウィンドウと被らないid、タイトル、内容として表示される文章、上ボタンの
 * テキスト、下ボタンのテキストをそれぞれ渡す必要があります。二つのボタンはそれぞれはい/いいえを書くことが
 * 想定されています。また、このウィンドウを作成すると上からタイトル、文章、ボタン、ボタンの順に表示されます。</p>
 * <p>レスポンスの取得にはgetResponses()を使用します。戻り値はMap<Integer, Object>ですが、このクラスの場合は
 * いかなる場合であっても戻り値のサイズ(getResponses().size())の値は1となります。これは二つのボタンしか
 * ないためです。そのため、結果を取得するにはgetResponses().get(0)を使用することになります。これを使用して
 * 取得した場合にはObjectが返ってきますが、実際にはbooleanのため、キャストが可能です。なお、どのボタンを押しても
 * 戻ってくるのはfalseとなります。</p>
 * 
 * <p>Jupiter Project by JupiterDevelopmentTeam</p>
 * 
 * @see ModalFormWindow#getResponses()
 */

public class ModalFormWindow extends FormWindow {

    private int id;
    private String content = "ModalFormWindow text";
    private String upButtonText = "はい";
    private String downButtonText = "いいえ";
    
    private boolean data;
    private List<Object> datas;

    public ModalFormWindow (int id, String title, String content, String upButtonText, String downButtonText) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.upButtonText = upButtonText;
        this.downButtonText = downButtonText;
    }

    /**
     * 
     * @author itsu
     * @return id(int)
     * 
     * <h3>getId() - Jupiter ModalForm API</h3>
     * <p>このメソッドではウィンドウidを取得します。</p>
     * 
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     * 
     */
    
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * 
     * @author itsu
     * @return jsonデータ(String)
     * 
     * <h3>toJson() - Jupiter ModalForm API</h3>
     * <p>ウィンドウをJSONデータ化します。</p>
     * 
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     * 
     */

    @Override
    public String toJson() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("type", WindowType.TYPE_MODAL);
        data.put("title", title);
        data.put("content", content);
        data.put("button1", upButtonText);
        data.put("button2", downButtonText);

        return gson.toJson(data);
    }

    /**
     * @return Map<Integer, Object> レスポンス
     * @author itsu
     * 
     * <h3>getResponses() - Jupiter ModalForm API</h3>
     * <p>このメソッドではレスポンスを取得します。
     * 返り値の0のキーが上のボタン、1のキーが下のボタンとなります。</p>
     * 
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     * 
     */
    @Override
    public Map<Integer, Object> getResponses() {
        Map<Integer, Object> out = new LinkedHashMap<Integer, Object>();
        out.put(0, data);
        out.put(1, !data);
        return out;
    }

    @Override
    public Boolean getResponse() {
        return this.data;
    }

    @Override
    public void setResponse(String data) {
        this.data = Boolean.valueOf(data.trim());
    }
}
