package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jui.vcl.Image;
import cn.cerc.jui.vcl.ext.Span;

/**
 * 标题
 * 
 * @author 郭向军
 *
 */
public class Block121 extends Component {
    private Span title = new Span();
    private Image leftImage = new Image();
    private UrlRecord leftUrl = new UrlRecord();
    private UrlRecord rightUrl = new UrlRecord();
    private Span rightText = new Span();

    public Block121(Component owner) {
        super(owner);
    }

    @Override
    public void output(HtmlWriter html) {
        html.println("<!-- %s -->", this.getClass().getName());
        html.print("<header  class='block121'>");
        if (this.leftImage.getSrc() != null && !this.leftImage.getSrc().equals("")) {
            html.print("<a href='%s'  class='arrow-left'>", this.leftUrl.getUrl());
            this.leftImage.output(html);
            html.print("</a>");
        }
        html.print("<h1 class='title'>");
        this.title.output(html);
        html.print("</h1>");
        if (this.rightText.getText() != null && !this.rightText.getText().equals("")) {
            html.print("<a href='%s' class='arrow-right'>", this.rightUrl.getUrl());
            this.rightText.output(html);
            html.print("</a>");
        }
        html.print("</header>");
    }

    public UrlRecord getRightUrl() {
        return rightUrl;
    }

    public Span getRightText() {
        return rightText;
    }

    public Span getTitle() {
        return title;
    }

    public Image getLeftImage() {
        return this.leftImage;
    }

    public UrlRecord getLeftUrl() {
        return leftUrl;
    }

}
