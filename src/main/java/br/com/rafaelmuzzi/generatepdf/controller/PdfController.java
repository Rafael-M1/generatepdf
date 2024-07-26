package br.com.rafaelmuzzi.generatepdf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelmuzzi.generatepdf.service.ThymeleafParserService;

@RestController
@RequestMapping("/pdf")
public class PdfController {

	private ThymeleafParserService service;

	@Autowired
	public PdfController(ThymeleafParserService service) {
		this.service = service;
	}
	
	@GetMapping(value = {"/download/", "/download", "/download/{id}"})
	public ResponseEntity<byte[]> downloadPdf(@PathVariable(required = false) Long id) {
		byte[] content = null;
		if (id != null) {
			content = service.generatePdfFile(id);
		} else {
			content = service.generatePdfFile();
		}
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=%s", "teste.pdf"))
				.body(content);
	}
	
}
