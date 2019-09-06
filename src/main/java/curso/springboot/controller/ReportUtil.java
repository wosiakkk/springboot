package curso.springboot.controller;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/*Classe que será usada para gerar todos os relatórios da aplicação*/


@Component
public class ReportUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*Retorna nosso PDF ewm Byte para download no navegador. A lista será lista de objetos que serão impressos,
	 * é genérica por isso não é declado o tipo de dados que ela recebe, a string reletório é o nome do relatorio
	 * a ser gerado , e o servlet context é necessário para acessar os arquivos jasper nos diretórios do projeto.
	 * Com isso, é possível imprimri qualquer tipo de relatório, já que a classe está com os métodos genéricos*/
	public byte[] gerarRelatorio(List listaDados, String relatorio,
			ServletContext servletContext) throws Exception {
		
		//criando a impressão do relatório
		
		//cria a lista de dados para o relatório com nossa lista de objetos para imprimir
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
		
		/*Carregar o caminho do arquivo jasper compilado*/
		String caminhoJasper = servletContext.getRealPath("relatorios") 
				+ File.separator + relatorio + ".jasper";
		
		/*Carrega o arquivo Jasper passando os dados. passando o caminho e os dados,
		 * como não há um hashmap de parâmetro, apenas inicia um novo hash no segundo parametro do fillReport*/
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap<>(),jrbcds);
		/*Exporta para byte para fazer o download do pdf*/
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}
}
