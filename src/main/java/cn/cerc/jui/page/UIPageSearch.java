package cn.cerc.jui.page;

import static cn.cerc.jmis.core.ClientDevice.device_ee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.core.CustomHandle;
import cn.cerc.jbean.form.IClient;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.other.MemoryBuffer;
import cn.cerc.jdb.core.DataSet;
import cn.cerc.jmis.form.AbstractForm;
import cn.cerc.jmis.page.AbstractJspPage;
import cn.cerc.jmis.page.ExportFile;
import cn.cerc.jmis.page.IMenuBar;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlContent;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.MutiGrid;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.grid.AbstractGrid;
import cn.cerc.jpage.grid.DataGrid;
import cn.cerc.jpage.grid.MutiPage;
import cn.cerc.jpage.grid.PhoneGrid;
import cn.cerc.jpage.other.HeaderSide;
import cn.cerc.jpage.other.OperaPages;
import cn.cerc.jpage.other.UrlMenu;
import cn.cerc.jui.parts.MainMenu;
import cn.cerc.jui.parts.RightMenus;

/**
 * 主体子页面
 * 
 * @author 张弓
 *
 */
public class UIPageSearch extends AbstractJspPage {
    private MainMenu mainMenu = new MainMenu();
    private MutiPage pages;
    private String searchWaitingId = "";
    private Component content;
    private HeaderSide header;
    private List<HtmlContent> contents = new ArrayList<>();
    private List<HtmlContent> codes1 = new ArrayList<>();

    public UIPageSearch(IForm form) {
        super(form);
        this.addScriptFile("js/jquery.js");
        this.addScriptFile("js/summer.js");
        this.addScriptFile("js/myapp.js");
    }

    public void addExportFile(String service, String key) {
        if (device_ee.equals(this.getForm().getClient().getDevice())) {
            ExportFile item = new ExportFile(service, key);
            this.add("export", item);
        }
    }

    @Override
    public void add(String id, Object value) {
        HttpServletRequest request = getRequest();
        if (value instanceof AbstractGrid) {
            AbstractGrid grid = (AbstractGrid) value;
            request.setAttribute(id, value);
            pages = grid.getPages();
        } else if (value instanceof MutiGrid) {
            MutiGrid<?> grid = (MutiGrid<?>) value;
            request.setAttribute(id, grid.getList());
            pages = grid.getPages();
        } else
            request.setAttribute(id, value);
    }

    @Override
    public void execute() throws ServletException, IOException {
        HttpServletRequest request = getRequest();
        // 添加分页控制
        Component operaPages = null;
        if (pages != null) {
            this.add("pages", pages);
            operaPages = new OperaPages(this.getForm(), pages);
            this.add("_operaPages_", operaPages);
        }
        IForm form = this.getForm();
        CustomHandle sess = (CustomHandle) form.getHandle().getProperty(null);
        if (sess.logon()) {
            List<UrlRecord> rightMenus = mainMenu.getRightMenus();
            RightMenus menus = Application.getBean("RightMenus", RightMenus.class);
            menus.setHandle(form.getHandle());
            for (IMenuBar item : menus.getItems())
                item.enrollMenu(form, rightMenus);
        } else {
            mainMenu.getHomePage().setSite(Application.getAppConfig().getFormWelcome());
        }

        // 系统通知消息
        Component content = this.getContent();
        if (form instanceof AbstractForm) {
            this.add("barMenus", mainMenu.getBarMenus(this.getForm()));
            if (mainMenu.getRightMenus().size() > 0)
                this.add("subMenus", mainMenu.getRightMenus());
            this.header = registerContent(this, content);
        }

        // 右边区域
        Component rightSite = (Component) request.getAttribute("rightSide");
        // 底部
        Component bottom = (Component) request.getAttribute("bottom");

        // 开始输出
        PrintWriter out = getResponse().getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.printf("<title>%s</title>\n", this.getForm().getTitle());
        out.printf("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n");
        out.printf(
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0\"/>\n");
        out.printf("<link href=\"css/style-phone.css\" rel=\"stylesheet\">\n");
        if (!form.getClient().isPhone())
            out.printf("<link href=\"css/style-pc.css\" rel=\"stylesheet\">\n");
        out.print(this.getCss());
        out.print(getScript2(this));
        out.println("<script>");
        out.println("var Application = new TApplication();");
        out.printf("Application.device = '%s';\n", form.getClient().getDevice());

        if (bottom != null)
            out.printf("Application.bottom = '%s';\n", bottom.getId());

        String msg = form.getParam("message", "");
        msg = msg == null ? "" : msg.replaceAll("\r\n", "<br/>");
        out.printf("Application.message = '%s';\n", msg.replace("'", "\\'"));
        out.printf("Application.searchFormId = '%s';\n", this.searchWaitingId);

        out.println("$(document).ready(function() {");
        out.println("Application.init();");
        out.println("});");
        out.println("</script>");
        out.println("</head>");
        out.println("<body>");
        out.println(header);

        out.write("<div class=\"main\">\n");
        if (bottom != null)
            out.write("<div class=\"info-newStyle\">\n");

        if (!form.getClient().isPhone()) {
            if (bottom == null)
                out.println("<div id='msg'></div>");
        } else {
            out.println("<div id='msg'></div>");
            out.println("<span id='back-top' style='display: none'>顶部</span>");
            out.println("<span id='back-bottom' style='display: none'>底部</span>");
        }
        out.println("<div class='leftSide'>");

        if (this.content != null)
            out.print(this.content);

        out.println("</div>");
        out.println("<div class='rightSide'>");

        if (rightSite != null)
            out.print(rightSite);

        if (operaPages != null)
            out.print(operaPages.toString());
        out.println("</div>");

        if (bottom == null) {
            if (operaPages != null)
                out.print(operaPages.toString());
        } else {
            out.print(bottom);
            out.println("</div>");
        }
        out.println("</div>\n");
        out.println("<div class='bottom-space'></div>");
        out.print(this.getContents());
        out.println("</body>");
        out.println("</html>");
    }

    private HtmlWriter getScript2(AbstractJspPage page) {
        HtmlWriter html = new HtmlWriter();

        // 加入脚本文件
        for (String file : page.getScriptFiles()) {
            html.println("<script src=\"%s\"></script>", file);
        }
        // 加入脚本代码
        List<HtmlContent> scriptCodes = page.getScriptCodes();
        if (codes1.size() > 0 || scriptCodes.size() > 0) {
            html.println("<script>");
            for (HtmlContent func : codes1) {
                func.output(html);
            }
            if (scriptCodes.size() > 0) {
                html.println("$(function(){");
                for (HtmlContent func : scriptCodes) {
                    func.output(html);
                }
                html.println("});");
            }
            html.println("</script>");
        }
        return html;
    }

    public static HeaderSide registerContent(AbstractJspPage page, Component content) {
        HeaderSide header = null;
        HttpServletRequest request = page.getRequest();
        boolean _showMenu_ = "true".equals(page.getForm().getParam("showMenus", "true"));
        if (_showMenu_) {
            header = new HeaderSide();
            Component left = header.getLeft();
            @SuppressWarnings("unchecked")
            List<UrlRecord> barMenus = (List<UrlRecord>) request.getAttribute("barMenus");
            if (barMenus == null) {
                new UrlMenu(left, "首页", "/");
                new UrlMenu(left, "刷新", "javascript:history.go(-1);");
            } else {
                for (UrlRecord menu : barMenus) {
                    new UrlMenu(left, menu.getName(), menu.getUrl());
                }
            }

            Component right = header.getRight();
            @SuppressWarnings("unchecked")
            List<UrlRecord> subMenus = (List<UrlRecord>) request.getAttribute("subMenus");
            if (subMenus != null) {
                int i = subMenus.size() - 1;
                while (i > -1) {
                    UrlRecord menu = subMenus.get(i);
                    new UrlMenu(right, menu.getName(), menu.getUrl());
                    i--;
                }
            }
        }

        if (content != null) {
            request.setAttribute(content.getId(), content);
            for (Component component : content.getComponents()) {
                request.setAttribute(component.getId(), component);
            }
        }
        return header;
    }

    public void appendContent(HtmlContent content) {
        contents.add(content);
    }

    public HtmlWriter getContents() {
        HtmlWriter html = new HtmlWriter();
        if (contents.size() == 0)
            return html;
        for (HtmlContent content : contents)
            content.output(html);
        return html;
    }

    public UIPanelHorizontal createSearch(MemoryBuffer buff) {
        UIPanelHorizontal search = new UIPanelHorizontal(this.getContent(), this.getRequest());
        search.setBuffer(buff);
        this.setSearchWaitingId(search.getId());
        return search;
    }

    public AbstractGrid createGrid(Component owner, DataSet dataSet) {
        IClient info = this.getForm().getClient();
        AbstractGrid grid = info.isPhone() ? new PhoneGrid(this.getForm(), owner) : new DataGrid(this.getForm(), owner);
        grid.setDataSet(dataSet);
        pages = grid.getPages();
        return grid;
    }

    public void addDefineScript(HtmlContent scriptCode) {
        codes1.add(scriptCode);
    }

    public List<HtmlContent> getCodes1() {
        return codes1;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public String getSearchWaitingId() {
        return searchWaitingId;
    }

    public void setSearchWaitingId(String searchWaitingId) {
        this.searchWaitingId = searchWaitingId;
    }

    public Component getContent() {
        if (content == null)
            content = new Component(this);
        return content;
    }

    public void setContent(Component content) {
        this.content = content;
    }
}
