package uz.intership.rest;

import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.intership.dto.ResponseDto;
import uz.intership.repository.ProductRepository;
import uz.intership.servise.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class FileResources {
    private final FileService fileService;
    @GetMapping("excel")
    @PreAuthorize("hasAnyAuthority('UPDATE')")
    public ResponseDto<String> exelGeneration() throws IOException {
        return fileService.exelCreate();
    }
    @GetMapping("pdf")
    @PreAuthorize("hasAnyAuthority('UPDATE')")
    public ResponseDto<String> pdfGeneration() throws DocumentException, FileNotFoundException {
        return fileService.pdfGeneration();
    }
}
