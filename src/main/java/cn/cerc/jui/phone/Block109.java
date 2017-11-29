package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jui.vcl.Image;
import cn.cerc.jui.vcl.TextBox;
import cn.cerc.jui.vcl.ext.Span;

public class Block109 extends Component {
    private Span label = new Span();
    private TextBox input = new TextBox();
    private Image select = new Image();

    /**
     * 文本 + 输入框 + 弹窗选择按钮
     * 
     * @param owner
     *            内容显示区
     */
    public Block109(Component owner) {
        super(owner);
        label.setText("(label)");
        select.setSrc("jui/phone/block109-select.png");
    }

    @Override
    public void output(HtmlWriter html) {
        html.println("<!-- %s -->", this.getClass().getName());
        html.print("<div class='block109'>");
        label.output(html);
        input.output(html);
        select.output(html);
        html.println("</div>");
    }

    public Span getLabel() {
        return label;
    }

    public TextBox getInput() {
        return input;
    }

    public Image getSelect() {
        return select;
    }
}
