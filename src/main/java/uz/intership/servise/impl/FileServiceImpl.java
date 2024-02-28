package uz.intership.servise.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final ProductRepository productRepository;
    @Override
    public ResponseDto<String> exelCreate(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(" Product Data ");
        XSSFRow row;

        Map<String, Object[]> productData = new TreeMap<String, Object[]>();

        List<Product> productList = productRepository.findAll();
        productData.put("1", new Object[] { "Id", "Name", "Price", "Amount", "Description", "isActive", "createBy", "createAt", "updateBy", "updateAt"});
        for (int i = 0; i < productList.size(); i++){
            productData.put(String.valueOf(i+2),
                    new Object[] { String.valueOf(productList.get(i).getId()), productList.get(i).getName(), String.valueOf(productList.get(i).getPrice()),
                            String.valueOf(productList.get(i).getAmount()),productList.get(i).getDescription(),String.valueOf(productList.get(i).getIsActive()),
                            String.valueOf(productList.get(i).getCreateBy()),String.valueOf(productList.get(i).getCreateAt()),
                            String.valueOf(productList.get(i).getUpdateBy()),String.valueOf(productList.get(i).getUpdateAt())

            });
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
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath("upload_excel", ".xlsx"));
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            return ResponseDto.<String>builder()
                    .code(-2)
                    .info("Error with " + e.getMessage())
                    .data("Excel file is not created")
                    .success(false)
                    .build();
        }


        return ResponseDto.<String>builder()
                .code(0)
                .info("OK")
                .data("Excel file is created")
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<String> pdfGeneration(){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath("upload_pdf", ".pdf")));
            document.open();

            PdfPTable table = new PdfPTable(10);
            Stream.of("Id", "Name", "Price", "Amount", "Description", "IsActive", "CreateBy", "CreateAt", "UpdateBy", "UpdateAt")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.GREEN);
                        header.setPhrase(new Phrase(columnTitle));
                        table.addCell(header);
                    });
            List<Product> productList = productRepository.findAll();
            for (Product product : productList) {
                table.addCell(String.valueOf(product.getId()));
                table.addCell(product.getName());
                table.addCell(String.valueOf(product.getPrice()));
                table.addCell(String.valueOf(product.getAmount()));
                table.addCell(product.getDescription());
                table.addCell(String.valueOf(product.getIsActive()));
                table.addCell(String.valueOf(product.getCreateBy()));
                table.addCell(String.valueOf(product.getCreateAt()));
                table.addCell(String.valueOf(product.getUpdateBy()));
                table.addCell(String.valueOf(product.getUpdateAt()));
            }

            document.add(table);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            return ResponseDto.<String>builder()
                    .code(-2)
                    .info("Error with " + e.getMessage())
                    .data("Pdf file is not created")
                    .success(false)
                    .build();
        }


        return ResponseDto.<String>builder()
                .code(0)
                .info("OK")
                .data("Pdf file is created")
                .success(true)
                .build();
    }
    public static String filePath(String folder,String ext){
        LocalDate localDate = LocalDate.now();
        String path = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        java.io.File file = new java.io.File(folder + "/"+ path);
        if (!file.exists()){
            file.mkdirs();
        }
        String uuid = UUID.randomUUID().toString();
        return file.getPath() + "\\"+ System.currentTimeMillis() + ext;
    }
}
