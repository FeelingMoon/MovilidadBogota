package co.edu.unbosque.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import co.edu.unbosque.daos.MultaDAO;
import co.edu.unbosque.daos.MultasEstaticasDAO;
import co.edu.unbosque.daos.PersonaDAO;
import co.edu.unbosque.persistence.MultaDTO;
import co.edu.unbosque.persistence.MultasEstaticasDTO;
import co.edu.unbosque.persistence.PersonaDTO;
import co.edu.unbosque.persistence.VehiculoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("PDFBean")
@RequestScoped
public class PDFBean {

	private static double SMLDV = 38666.67;

	private MultaDAO multa;
	private MultasEstaticasDAO multaS;
	private PersonaDAO personas;
	private Document document;
	private ByteArrayOutputStream baos;
	private ByteArrayInputStream bais;
	private StreamedContent fileM, fileMS, filePV, filePM;

	@SuppressWarnings("unused")
	private void generarPDFM() {
		try {
			multa = new MultaDAO();
			List<MultaDTO> tmp = multa.getAll();
			document = new Document();
			document.setPageSize(PageSize.A4.rotate());
			baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			table.addCell(new Phrase("Id", font));
			table.addCell(new Phrase("Codigo", font));
			table.addCell(new Phrase("Valor", font));
			table.addCell(new Phrase("Fecha", font));
			table.addCell(new Phrase("Pagado", font));
			double cont = 0;
			for (MultaDTO mt : tmp) {
				PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(mt.getId())));
				PdfPCell cellCodigo = new PdfPCell(new Phrase(mt.getCodigo()));
				PdfPCell cellFecha = new PdfPCell(new Phrase(mt.getFechaIngreso().toString()));
				cont += Integer.parseInt(mt.getValor().split(" ")[0]) * SMLDV;
				PdfPCell cellValor = new PdfPCell(new Phrase(mt.getValor()));
				PdfPCell cellPagado = new PdfPCell(new Phrase("No"));
				if (mt.isPagado()) {
					cellPagado = new PdfPCell(new Phrase("Si"));
				}
				table.addCell(cellId);
				table.addCell(cellCodigo);
				table.addCell(cellValor);
				table.addCell(cellFecha);
				table.addCell(cellPagado);
			}
			PdfPCell cellRecaS = new PdfPCell(new Phrase("El dinero total recaudado sera de: ", font));
			cellRecaS.setColspan(3);
			PdfPCell cellRecaV = new PdfPCell(new Phrase(String.valueOf(Math.round(cont))));
			cellRecaV.setColspan(2);
			table.addCell(cellRecaS);
			table.addCell(cellRecaV);
			document.add(table);
			document.close();
			bais = new ByteArrayInputStream(baos.toByteArray());
			fileM = DefaultStreamedContent.builder().stream(() -> bais).contentType("application/pdf")
					.name("Multas.pdf").build();
		} catch (Exception e) {
			fileM = null;
		}
	}

	@SuppressWarnings("unused")
	private void generarPDFMS() {
		try {
			multaS = new MultasEstaticasDAO();
			List<MultasEstaticasDTO> tmp = multaS.getAllMultasEstaticas();
			document = new Document();
			document.setPageSize(PageSize.A4.rotate());
			baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			PdfPTable table = new PdfPTable(5);
			table.setWidths(new float[] { 1, 1, 5, 1, 1 });
			table.setWidthPercentage(100);
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			table.addCell(new Phrase("Id", font));
			table.addCell(new Phrase("Codigo", font));
			table.addCell(new Phrase("Descripcion", font));
			table.addCell(new Phrase("Valor", font));
			table.addCell(new Phrase("Inmovilizacion", font));
			for (MultasEstaticasDTO mt : tmp) {
				PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(mt.getId())));
				PdfPCell cellCodigo = new PdfPCell(new Phrase(mt.getCodigo()));
				PdfPCell cellDescripcion = new PdfPCell(new Phrase(mt.getDescripcion()));
				PdfPCell cellValor = new PdfPCell(new Phrase(mt.getMulta()));
				PdfPCell cellInmovilizacion = new PdfPCell(new Phrase("No"));
				if (mt.isInmovilizacion()) {
					cellInmovilizacion = new PdfPCell(new Phrase("Si"));
				}
				table.addCell(cellId);
				table.addCell(cellCodigo);
				table.addCell(cellDescripcion);
				table.addCell(cellValor);
				table.addCell(cellInmovilizacion);
			}
			document.add(table);
			document.close();
			bais = new ByteArrayInputStream(baos.toByteArray());
			fileMS = DefaultStreamedContent.builder().stream(() -> bais).contentType("application/pdf")
					.name("MultasPosibles.pdf").build();
		} catch (Exception e) {
			fileMS = null;
		}
	}

	@SuppressWarnings("unused")
	private void generarPDFPV() {
		try {
			personas = new PersonaDAO();
			List<PersonaDTO> tmp = personas.getAll();
			document = new Document();
			document.setPageSize(PageSize.A4.rotate());
			baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1, 2, 2, 2, 2, 5 });
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			table.addCell(new Phrase("Id", font));
			table.addCell(new Phrase("Nombre", font));
			table.addCell(new Phrase("Documento", font));
			table.addCell(new Phrase("Fecha de expedicion", font));
			table.addCell(new Phrase("Fecha de nacimiento", font));
			table.addCell(new Phrase("Vehiculos", font));
			for (PersonaDTO pr : tmp) {
				PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(pr.getId())));
				PdfPCell cellNombre = new PdfPCell(new Phrase(pr.getNombre()));
				PdfPCell cellDocumento = new PdfPCell(new Phrase(String.valueOf(pr.getDocumento())));
				PdfPCell cellFechaExpedicion = new PdfPCell(new Phrase(pr.getFechaExpedicion().toString()));
				PdfPCell cellFechaNacimiento = new PdfPCell(new Phrase(pr.getFechaNacimiento().toString()));
				PdfPCell cellVehiculo = new PdfPCell();
				if (pr.getVehiculos() != null && !pr.getVehiculos().isEmpty()) {
					PdfPTable tableVehiculos = new PdfPTable(3);
					tableVehiculos.addCell(new Phrase("Placa", font));
					tableVehiculos.addCell(new Phrase("Marca", font));
					tableVehiculos.addCell(new Phrase("Color", font));
					for (VehiculoDTO vh : pr.getVehiculos()) {
						PdfPCell cellPlaca = new PdfPCell(new Phrase(vh.getPlaca()));
						PdfPCell cellMarca = new PdfPCell(new Phrase(vh.getMarca()));
						PdfPCell cellColor = new PdfPCell(new Phrase(vh.getColor()));
						tableVehiculos.addCell(cellPlaca);
						tableVehiculos.addCell(cellMarca);
						tableVehiculos.addCell(cellColor);
					}
					cellVehiculo.addElement(tableVehiculos);
				} else {
					cellVehiculo = new PdfPCell(new Phrase("No tiene vehiculos"));
				}
				// Agregar las celdas a la tabla
				table.addCell(cellId);
				table.addCell(cellNombre);
				table.addCell(cellDocumento);
				table.addCell(cellFechaExpedicion);
				table.addCell(cellFechaNacimiento);
				table.addCell(cellVehiculo);
			}
			document.add(table);
			document.close();
			bais = new ByteArrayInputStream(baos.toByteArray());
			filePV = DefaultStreamedContent.builder().stream(() -> bais).contentType("application/pdf")
					.name("PersonasConVehiculos.pdf").build();
		} catch (Exception e) {
			filePV = null;
		}
	}

	@SuppressWarnings("unused")
	private void generarPDFPM() {
		try {
			personas = new PersonaDAO();
			List<PersonaDTO> tmp = personas.getAll();
			document = new Document();
			document.setPageSize(PageSize.A4.rotate());
			baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1, 2, 2, 2, 2, 5 });
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			table.addCell(new Phrase("Id", font));
			table.addCell(new Phrase("Nombre", font));
			table.addCell(new Phrase("Documento", font));
			table.addCell(new Phrase("Fecha de expedicion", font));
			table.addCell(new Phrase("Fecha de nacimiento", font));
			table.addCell(new Phrase("Multas", font));
			for (PersonaDTO pr : tmp) {
				PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(pr.getId())));
				PdfPCell cellNombre = new PdfPCell(new Phrase(pr.getNombre()));
				PdfPCell cellDocumento = new PdfPCell(new Phrase(String.valueOf(pr.getDocumento())));
				PdfPCell cellFechaExpedicion = new PdfPCell(new Phrase(pr.getFechaExpedicion().toString()));
				PdfPCell cellFechaNacimiento = new PdfPCell(new Phrase(pr.getFechaNacimiento().toString()));
				PdfPCell cellMultas = new PdfPCell();
				if (pr.getVehiculos() != null && !pr.getVehiculos().isEmpty()) {
					PdfPTable tableMultas = new PdfPTable(4);
					tableMultas.addCell(new Phrase("Codigo", font));
					tableMultas.addCell(new Phrase("Valor", font));
					tableMultas.addCell(new Phrase("Fecha", font));
					tableMultas.addCell(new Phrase("Pagado", font));
					boolean conf = true;
					for (VehiculoDTO vh : pr.getVehiculos()) {
						if (vh.getMultas() != null && !vh.getMultas().isEmpty()) {
							for (MultaDTO mt : vh.getMultas()) {
								PdfPCell cellCodigo = new PdfPCell(new Phrase(mt.getCodigo()));
								PdfPCell cellValor = new PdfPCell(new Phrase(mt.getValor()));
								PdfPCell cellFecha = new PdfPCell(new Phrase(mt.getFechaIngreso().toString()));
								PdfPCell cellPagado = new PdfPCell(new Phrase("No"));
								if (mt.isPagado()) {
									cellPagado = new PdfPCell(new Phrase("Si"));
								}
								tableMultas.addCell(cellCodigo);
								tableMultas.addCell(cellValor);
								tableMultas.addCell(cellFecha);
								tableMultas.addCell(cellPagado);
								conf = false;
							}
						} else {
							continue;
						}
					}
					if (conf) {
						cellMultas = new PdfPCell(new Phrase("No tiene multas"));
					} else {
						cellMultas.addElement(tableMultas);
					}
				} else {
					cellMultas = new PdfPCell(new Phrase("No tiene multas"));
				}
				// Agregar las celdas a la tabla
				table.addCell(cellId);
				table.addCell(cellNombre);
				table.addCell(cellDocumento);
				table.addCell(cellFechaExpedicion);
				table.addCell(cellFechaNacimiento);
				table.addCell(cellMultas);
			}
			document.add(table);
			document.close();
			bais = new ByteArrayInputStream(baos.toByteArray());
			filePM = DefaultStreamedContent.builder().stream(() -> bais).contentType("application/pdf")
					.name("PersonasConMultas.pdf").build();
		} catch (Exception e) {
			filePM = null;
		}
	}

	public StreamedContent getFileM() {
		generarPDFM();
		return fileM;
	}

	public void setFileM(StreamedContent fileM) {
		this.fileM = fileM;
	}

	public StreamedContent getFilePV() {
		generarPDFPV();
		return filePV;
	}

	public void setFilePV(StreamedContent filePV) {
		this.filePV = filePV;
	}

	public StreamedContent getFilePM() {
		generarPDFPM();
		return filePM;
	}

	public void setFilePM(StreamedContent filePM) {
		this.filePM = filePM;
	}

	public StreamedContent getFileMS() {
		generarPDFMS();
		return fileMS;
	}

	public void setFileMS(StreamedContent fileMS) {
		this.fileMS = fileMS;
	}

}
