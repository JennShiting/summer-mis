package cn.cerc.jui.phone;

import cn.cerc.jbean.form.IPage;
import cn.cerc.jmis.form.AbstractForm;

public class FrmPhoneSample extends AbstractForm {

    @Override
    public IPage execute() throws Exception {
        PhonePage page = new PhonePage(this);

        new Block101(page.getContent());
        new Block102(page.getContent());
        new Block103(page.getContent());
        new Block104(page.getContent());
        new Block105(page.getContent());
        new Block106(page.getContent());
        new Block107(page.getContent());
        new Block108(page.getContent());
        new Block109(page.getContent());
        new Block110(page.getContent());
        new Block111(page.getContent());
        new Block112(page.getContent());
        new Block113(page.getContent());
        new Block114(page.getContent());
        new Block115(page.getContent());
        new Block116(page.getContent());
        new Block117(page.getContent());
        new Block118(page.getContent());
        new Block119(page.getContent());
        new Block120(page.getContent());
        new Block121(page.getContent());
        new Block123(page.getContent());
        new Block124(page.getContent());
        new Block125(page.getContent());
        new Block126(page.getContent());
        new Block127(page.getContent());

        new Block201(page.getContent());

        new Block301(page.getContent());
        new Block302(page.getContent());
        new Block303(page.getContent());
        new Block304(page.getContent());
        new Block305(page.getContent());
        new Block306(page.getContent());
        new Block307(page.getContent());

        new Block401(page.getContent());
        new Block402(page.getContent());

        new Block601(page.getContent());
        new Block602(page.getContent());
        new Block603(page.getContent());
        new Block604(page.getContent());

        new Block901(page.getContent());
        new Block902(page.getContent());

        new Block991(page.getContent());
        Block992 b992 = new Block992(page.getContent());
        b992.addButton("进入系统");
        b992.addButton("进入系统");
        b992.addButton("进入系统");
        b992.addButton("进入系统");
        return page;
    }

    @Override
    public boolean logon() {
        return true;
    }
}
