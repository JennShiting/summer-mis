package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class CodeNameField extends AbstractField {
	private String nameField;
	private String param;

	public CodeNameField(Component owner, String name, String field) {
		super(owner, name, 0);
		this.setField(field);
	}

	@Override
	public void updateField() {
		if (dataSource != null) {
			dataSource.updateValue(this.getId(), this.getField());
			dataSource.updateValue(getNameField(), getNameField());
		}
	}

	@Override
	public String getText(Record dataSet) {
		if (dataSet == null)
			return null;
		if (buildText != null) {
			HtmlWriter html = new HtmlWriter();
			buildText.outputText(dataSet, html);
			return html.toString();
		}
		return dataSet.getString(getField());
	}

	@Override
	public void output(HtmlWriter html) {
		Record record = dataSource != null ? dataSource.getDataSet().getCurrent() : null;
		if (this.isHidden()) {
			html.print("<input");
			html.print(" type=\"hidden\"");
			html.print(" name=\"%s\"", this.getId());
			html.print(" id=\"%s\"", this.getId());
			String value = this.getText(record);
			if (value != null)
				html.print(" value=\"%s\"", value);
			html.println("/>");
		} else {
			html.println("<label for=\"%s\">%s</label>", this.getId(), this.getName() + "：");

			html.print("<input");
			html.print(" type=\"hidden\"");
			html.print(" name=\"%s\"", this.getId());
			html.print(" id=\"%s\"", this.getId());
			String codeValue = this.getText(record);
			if (codeValue != null)
				html.print(" value=\"%s\"", codeValue);
			html.println("/>");

			html.print("<input");
			html.print(" type=\"text\"");
			html.print(" name=\"%s\"", getNameField());
			html.print(" id=\"%s\"", getNameField());
			String nameValue = null;
			if (record != null) {
				nameValue = record.getString(getNameField());
				if (nameValue != null)
					html.print(" value=\"%s\"", nameValue);
			}
			if (this.isReadonly())
				html.print(" readonly=\"readonly\"");
			if (this.isAutofocus())
				html.print(" autofocus");
			if (this.isRequired())
				html.print(" required");
			if (this.getPlaceholder() != null)
				html.print(" placeholder=\"%s\"", this.getPlaceholder());
			html.println("/>");

			html.print("<span>");
			if (this.getDialog() != null) {
				if (this.getParam() != null)
					html.print("<a href=\"javascript:%s('%s,%s','%s')\">", getDialog().getDialogfun(), getId(),
							getNameField(), getParam());
				else
					html.print("<a href=\"javascript:%s('%s,%s')\">", getDialog().getDialogfun(), getId(),
							getNameField());
				html.print("<img src=\"images/searchIocn.png\">");
				html.print("</a>");
			}
			html.print("</span>");
		}
	}

	public String getNameField() {
		if (nameField != null)
			return nameField;
		return this.getField() + "_name";
	}

	public CodeNameField setNameField(String nameField) {
		this.nameField = nameField;
		return this;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
