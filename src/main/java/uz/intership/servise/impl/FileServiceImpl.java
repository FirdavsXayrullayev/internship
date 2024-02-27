package uz.intership.servise.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.intership.dto.ProductDto;
import uz.intership.dto.ResponseDto;
import uz.intership.model.Product;
import uz.intership.repository.ProductRepository;
import uz.intership.servise.FileService;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final ProductRepository productRepository;
    @Override
    public ResponseDto<String> exelCreate() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(" Product Data ");
        XSSFRow row;

        Map<String, Object[]> productData = new TreeMap<String, Object[]>();

        List<Product> productList = productRepository.findAll();
        productData.put("1", new Object[] { "Id", "Name", "Price", "Amount", "Description" });
        for (int i = 0; i < productList.size(); i++){
            productData.put(String.valueOf(i+2), new Object[] { String.valueOf(productList.get(i).getId()), productList.get(i).getName(), String.valueOf(productList.get(i).getPrice()),String.valueOf(productList.get(i).getAmount()),productList.get(i).getDescription() });
        }

        Set<String> keyid = productData.keySet();

        int rowid = 0;

        for (String key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = productData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }

        FileOutputStream out = new FileOutputStream(
                new File("C:/Users/User/Desktop/product.xlsx"));

        workbook.write(out);
        out.close();
        return ResponseDto.<String>builder()
                .code(0)
                .info("OK")
                .data("Excel file is created")
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<String> pdfGeneration() throws DocumentException {
        String file
                = "C:/EXAMPLES/itextExamples/addingTableToPDF.pdf";

        // Step-1 Creating a PdfDocument object
//        PdfDocument pdfDoc
//                = new PdfDocument(new PdfWriter(file));
//
//        // Step-2 Creating a Document object
//        Document doc = new Document(pdfDoc);
//
//        // Step-3 Creating a table
//        PdfPTable table = new PdfPTable(2);
//
//        // Step-4 Adding cells to the table
//        table.addCell(new Cell().add("Name"));
//        table.addCell(new Cell().add("Raju"));
//        table.addCell(new Cell().add("Id"));
//        table.addCell(new Cell().add("1001"));
//        table.addCell(new Cell().add("Designation"));
//        table.addCell(new Cell().add("Programmer"));

        // Step-6 Adding Table to document
//        doc.add(table);
//
//        // Step-7 Closing the document
//        doc.close();
        return ResponseDto.<String>builder()
                .code(0)
                .info("OK")
                .data("Pdf file is created")
                .success(true)
                .build();;
    }
}
