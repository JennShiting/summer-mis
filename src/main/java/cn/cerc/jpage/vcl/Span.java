package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class Span extends Component {
    private String text;
    private String role;
    private String onclick;

    public Span() {
        super();
    }

    public Span(Component owner) {
        super(owner);
    }

    @Override
    public void output(HtmlWriter html) {
        html.print("<span");
        if (getId() != null)
            html.print(" id='%s'", this.getId());
        if (role != null)
            html.print(" role='%s'", this.role);
        if (onclick != null)
            html.print(" onclick='%s'", this.onclick);
        html.print(">");
        html.print(text);
        html.println("</span>");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setText(String format, Object... args) {
        this.text = String.format(format, args);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }
}
