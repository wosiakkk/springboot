$(document).ready(
		function() {

			function limpa_formulário_cep() {
				// Limpa valores do formulário de cep.
				$("#rua").val("");
				$("#bairro").val("");
				$("#cidade").val("");
				$("#uf").val("");
				$("#ibge").val("");
			}

			//Quando o campo cep perde o foco.
			$("#cep").blur(
					function() {

						//Nova variável "cep" somente com dígitos.
						var cep = $(this).val().replace(/\D/g, '');

						//Verifica se campo cep possui valor informado.
						if (cep != "") {

							//Expressão regular para validar o CEP.
							var validacep = /^[0-9]{8}$/;

							//Valida o formato do CEP.
							if (validacep.test(cep)) {

								//Preenche os campos com "..." enquanto consulta webservice.
								$("#rua").val("...");
								$("#bairro").val("...");
								$("#cidade").val("...");
								$("#uf").val("...");
								$("#ibge").val("...");

								//Consulta o webservice viacep.com.br/
								$.getJSON("https://viacep.com.br/ws/" + cep
										+ "/json/?callback=?", function(dados) {

									if (!("erro" in dados)) {
										//Atualiza os campos com os valores da consulta.
										/*Código add inserido: add classe active no label do materialize para realizar
										o efeito aumoticamente de transição do label preenchido pelo js*/
										$("#rua").val(dados.logradouro);
										$("#labelrua").addClass('active');
										$("#bairro").val(dados.bairro);
										$("#labelbairro").addClass('active');
										$("#cidade").val(dados.localidade);
										$("#labelcidade").addClass('active');
										$("#uf").val(dados.uf);
										$("#labeluf").addClass('active');
										$("#ibge").val(dados.ibge);
										$("#labelibge").addClass('active');
									} //end if.
									else {
										//CEP pesquisado não foi encontrado.
										limpa_formulário_cep();
										alert("CEP não encontrado.");
									}
								});
							} //end if.
							else {
								//cep é inválido.
								limpa_formulário_cep();
								alert("Formato de CEP inválido.");
							}
						} //end if.
						else {
							//cep sem valor, limpa formulário.
							limpa_formulário_cep();
						}
					});
		});
