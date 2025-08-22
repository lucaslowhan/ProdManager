package dev.lucaslowhan.prodmanager.service.relatorio;

import dev.lucaslowhan.prodmanager.domain.relatorio.dto.RelatorioProdutoMovimentadoDTO;
import dev.lucaslowhan.prodmanager.domain.relatorio.dto.RelatorioSaldoTotalEstoqueDTO;
import dev.lucaslowhan.prodmanager.repository.movimentacao.MovimentacaoRepository;
import dev.lucaslowhan.prodmanager.repository.produto.ProdutoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openpdf.text.Document;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RelatorioService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<RelatorioProdutoMovimentadoDTO> produtosMaisMovimentados(){
        return movimentacaoRepository.listarProdutosMaisMovimentados();
    }

    public ResponseEntity<byte[]> gerarRelatorios(String tipo){
        List<RelatorioProdutoMovimentadoDTO> dados = movimentacaoRepository.listarProdutosMaisMovimentados();

        return switch (tipo.toLowerCase()){
            case "excel" -> gerarRelatorioProdutoMaisMovimentadoExcel(dados);
            case "pdf" -> gerarRelatorioProdutoMaisMovimentadoPDF(dados);
            default -> throw new IllegalArgumentException("Tipo de relatório inválido: " + tipo);
        };
    }



    //Gerar Relatorio PDF
    public ResponseEntity<byte[]> gerarRelatorioProdutoMaisMovimentadoPDF(List<RelatorioProdutoMovimentadoDTO> dados) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();
            document.add(new Paragraph("Relatorio de Produtos movimentados"));
            document.add(new Paragraph(" "));


            for (RelatorioProdutoMovimentadoDTO dto : dados) {
                document.add(new Paragraph("ProdutoID: " + dto.produtoId()));
                document.add(new Paragraph("Produto: " + dto.nomeProduto()));
                document.add(new Paragraph("Total Movimentações: " + dto.totalMovimentacoes()));
                document.add(new Paragraph("Ultima Movimentação: " + dto.ultimaMovimentacao()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
                document.add(new Paragraph("-----------------------"));
            }

            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = relatorio_produtos.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);

        }
    }

    //gerarRelatorioExcel
    public ResponseEntity<byte[]> gerarRelatorioProdutoMaisMovimentadoExcel(List<RelatorioProdutoMovimentadoDTO> dados){
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Produtos Movimentados");
            Row headerRow = sheet.createRow(0);
            String[] colunas = {"ProdutoID", "Nome", "Total Movimentações", "Ultima movimentação"};

            for(int i = 0 ; i < colunas.length; i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(colunas[i]);
            }

            int rowNum = 1;
            for(RelatorioProdutoMovimentadoDTO dto : dados){
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(dto.produtoId());
                row.createCell(1).setCellValue(dto.nomeProduto());
                row.createCell(2).setCellValue(dto.totalMovimentacoes());
                row.createCell(3).setCellValue(dto.ultimaMovimentacao().toString());
            }

            for(int i = 0; i < colunas.length; i++){
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_produtos.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(baos.toByteArray());
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao gerar Excel", e);
        }





    }

    public List<RelatorioSaldoTotalEstoqueDTO> saldoTotalEstoque() {
        return produtoRepository.saldoTotalEstoque();
    }
}
