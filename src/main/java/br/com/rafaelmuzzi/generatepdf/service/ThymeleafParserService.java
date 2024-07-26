package br.com.rafaelmuzzi.generatepdf.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class ThymeleafParserService {

	public byte[] generatePdfFile() {
		try {
			return generatePdfFromHtml(parseThymeleafTemplate());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Falha ao gerar arquivo PDF.");
		}
	}
	
	public byte[] generatePdfFile(Long id) {
		try {
			return generatePdfFromHtml(parseThymeleafTemplate(id));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Falha ao gerar arquivo PDF.");
		}
	}
	
	private String parseThymeleafTemplate() {
	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    templateResolver.setSuffix(".html");
	    templateResolver.setTemplateMode(TemplateMode.HTML);

	    TemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);

	    Context context = new Context();
	    context.setVariable("to", "Baeldung");

	    return templateEngine.process("thymeleaf_template", context);
	}
	
	private String parseThymeleafTemplate(Long id) {
	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    templateResolver.setSuffix(".html");
	    templateResolver.setTemplateMode(TemplateMode.HTML);

	    TemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);

	    Context context = new Context();
	    context.setVariable("to", "Baeldung" + id);

	    return templateEngine.process("thymeleaf_template", context);
	}
	
	private byte[] generatePdfFromHtml(String htmlContent) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    renderer.setDocumentFromString(htmlContent);
	    renderer.layout();
	    renderer.createPDF(byteArrayOutputStream);
	    byte[] byteArray = byteArrayOutputStream.toByteArray();
	    byteArrayOutputStream.close();
	    return byteArray;
	}

}
