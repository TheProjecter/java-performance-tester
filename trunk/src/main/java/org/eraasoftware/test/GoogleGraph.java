package org.eraasoftware.test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class GoogleGraph {
	Configuration cfg = new Configuration();

	public static void main(String[] args) {
		Map<String, Object> root = new HashMap<String, Object>();

		new GoogleGraph().applyTemplateSilent("gchart.html", root);
	}

	public void applyTemplate(String templateName, Map<String, Object> root, Writer writer) throws IOException,
			TemplateException {
		cfg.setClassForTemplateLoading(GoogleGraph.class, "");
		Template temp = cfg.getTemplate(templateName);
		root.put("body", templateName);
		temp.process(root, writer);
	}

	public boolean applyTemplateSilent(String templateName, Map<String, Object> root) {
		boolean done = false;
		try {
			Writer writer = new FileWriter("gchart.dest.html");
			applyTemplate(templateName, root, writer);
			done = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return done;
	}

}
