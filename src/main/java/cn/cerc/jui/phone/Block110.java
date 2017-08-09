package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Button;
import cn.cerc.jpage.vcl.Span;
import cn.cerc.jpage.vcl.TextBox;

public class Block110 extends Component {
    private Span label = new Span();
    private TextBox input = new TextBox();
    private Button search = new Button();

    /**
     * 文本 + 输入框 + 查询按钮
     * 
     * @param owner
     *            内容显示区
     */
    public Block110(Component owner) {
        super(owner);
        label.setText("(label)");
        search.setText("查询");
    }

    @Override
    public void output(HtmlWriter html) {
        html.println("<!-- %s -->", this.getClass().getName());
        html.print("<div class='block110'>");
        label.output(html);
        input.output(html);
        search.output(html);
        html.println("</div>");
    }

    public Span getLabel() {
        return label;
    }

    public TextBox getInput() {
        return input;
    }

    public Button getSearch() {
        return search;
    }
}
