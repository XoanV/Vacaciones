package org.afdt.vacaciones.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExportacionService {

	public ResponseEntity<byte[]> exportar(String contenido, String titulo, String formato) {
		switch (formato.toLowerCase()) {
		case "word":
			return generarWord(contenido, titulo);
		case "excel":
			return generarExcel(contenido, titulo);
		case "txt":
			return generarTxt(contenido, titulo);
		case "odt":
			return generarOdt(contenido, titulo);
		default:
			throw new IllegalArgumentException("Formato no válido: " + formato);
		}
	}

	private ResponseEntity<byte[]> generarWord(String contenido, String titulo) {
		try (XWPFDocument documento = new XWPFDocument(); ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
			XWPFParagraph parrafoTitulo = documento.createParagraph();
			XWPFRun runTitulo = parrafoTitulo.createRun();
			runTitulo.setText(titulo);
			runTitulo.setBold(true);
			runTitulo.setFontSize(16);
			String[] lineas = contenido.split("\\r?\\n");
			for (String linea : lineas) {
				XWPFParagraph parrafo = documento.createParagraph();
				XWPFRun run = parrafo.createRun();
				run.setText(linea);
			}
			documento.write(salida);
			return respuesta(salida.toByteArray(), titulo, "docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		} catch (Exception e) {
			throw new RuntimeException("Error generando el documento Word", e);
		}
	}

	private ResponseEntity<byte[]> generarExcel(String contenido, String titulo) {
		try (XSSFWorkbook libro = new XSSFWorkbook(); ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
			XSSFSheet hoja = libro.createSheet("Listado");
			String[] lineas = contenido.split("\\r?\\n");
			int fila = 0;
			for (String linea : lineas) {
				Row row = hoja.createRow(fila++);
				row.createCell(0).setCellValue(linea);
			}
			hoja.autoSizeColumn(0);
			libro.write(salida);
			return respuesta(salida.toByteArray(), titulo, "xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		} catch (Exception e) {
			throw new RuntimeException("Error generando el documento Excel", e);
		}
	}

	private ResponseEntity<byte[]> generarTxt(String contenido, String titulo) {
		String texto = titulo + System.lineSeparator() + System.lineSeparator() + contenido;
		byte[] datos = texto.getBytes(StandardCharsets.UTF_8);
		return respuesta(datos, titulo, "txt", "text/plain;charset=UTF-8");
	}

	private ResponseEntity<byte[]> respuesta(byte[] datos, String titulo, String extension, String tipoContenido) {
		String nombreArchivo = titulo.replaceAll("[\\\\/:*?\"<>|]", "").replaceAll("\\s+", "_");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(tipoContenido));
		headers.setContentDisposition(
				ContentDisposition.attachment().filename(nombreArchivo + "." + extension).build());
		return new ResponseEntity<>(datos, headers, HttpStatus.OK);
	}

	private ResponseEntity<byte[]> generarOdt(String contenido, String titulo) {

		try (ByteArrayOutputStream salida = new ByteArrayOutputStream();
				java.util.zip.ZipOutputStream zip = new java.util.zip.ZipOutputStream(salida)) {

			ZipEntry mimetype = new ZipEntry("mimetype");

			zip.putNextEntry(mimetype);

			zip.write("application/vnd.oasis.opendocument.text".getBytes(StandardCharsets.UTF_8));

			zip.closeEntry();

			String contentXml = generarContentXml(contenido, titulo);

			ZipEntry content = new ZipEntry("content.xml");

			zip.putNextEntry(content);

			zip.write(contentXml.getBytes(StandardCharsets.UTF_8));

			zip.closeEntry();

			zip.finish();

			return respuesta(salida.toByteArray(), titulo, "odt", "application/vnd.oasis.opendocument.text");

		} catch (Exception e) {

			throw new RuntimeException("Error generando el documento ODT", e);
		}
	}

	private String generarContentXml(String contenido, String titulo) {

		StringBuilder xml = new StringBuilder();

		xml.append("""
				<?xml version="1.0" encoding="UTF-8"?>
				<office:document-content
				    xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
				    xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
				    xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0"
				    office:version="1.2">

				<office:automatic-styles>
				</office:automatic-styles>

				<office:body>
				<office:text>
				""");

		xml.append("<text:h text:outline-level=\"1\">").append(escapeXml(titulo)).append("</text:h>");

		String[] lineas = contenido.split("\\r?\\n");

		for (String linea : lineas) {

			xml.append("<text:p>").append(escapeXml(linea)).append("</text:p>");
		}

		xml.append("""
				</office:text>
				</office:body>
				</office:document-content>
				""");

		return xml.toString();
	}

	private String escapeXml(String texto) {

		return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
				.replace("'", "&apos;");
	}
}