$(document).ready(function () {
  $('#filtroBtn').click(function () {
    atualizarRelatorio();
  });

  function atualizarRelatorio() {
    var dataInicio = $('#data_inicio').val();
    var dataFim = $('#data_fim').val();

    $.ajax({
      url: '/comissoes_in/src/model/relatorio.php',
      type: 'POST',
      dataType: 'json',
      data: {
        dataInicio: dataInicio,
        dataFim: dataFim
      },
      success: function (response) {
        console.log(response);
        exibirRelatorio(response);
      },
      error: function (x) {
        console.log(x);
        console.log('Erro ao carregar relatório.');
      }
    });
  }

  function exibirRelatorio(response) {
    var tabela = $('#tablecomissao tbody');
    grupos = {};
    somatorioPorGrupo = {}; // objeto para armazenar o somatório por grupo

    // criar grupos de comissões por NOME_VENDED_INDICACAO
    response.forEach(function (comissao) {
      var nomeVendedorIndicacao = comissao.NOME_VENDED_INDICACAO;

      if (!grupos[nomeVendedorIndicacao]) {
        grupos[nomeVendedorIndicacao] = [];
        somatorioPorGrupo[nomeVendedorIndicacao] = 0;
      }

      grupos[nomeVendedorIndicacao].push(comissao);
      somatorioPorGrupo[nomeVendedorIndicacao] += parseFloat(comissao.VALOR_DA_COMISSAO); // joga o valor da comissão ao somatório do grupo
    });

    // limpar a tabela antes de adicionar os dados agrupados
    tabela.empty();

    // juntar em grupo e exibir na tabela
    for (var nomeVendedorIndicacao in grupos) {
      var comissoesGrupo = grupos[nomeVendedorIndicacao];

      var rowGrupo = $('<tr class="grupo"></tr>');
      var cellNomeVendedorIndicacaoGrupo = $('<td></td>').attr('colspan', 5).text(nomeVendedorIndicacao && nomeVendedorIndicacao != 'null' ? nomeVendedorIndicacao : 'Sem vendedor');
      rowGrupo.append(cellNomeVendedorIndicacaoGrupo);
      tabela.append(rowGrupo);

      comissoesGrupo.forEach(function (comissao) {
        var dataVenda = comissao.DATA_VENDA;
        var dataFormatada = moment(dataVenda).format('DD/MM/YYYY');
        var nomeVendedor = comissao.NOMEVENDED;
        var tipoComissao = comissao.TIPO_COMISSAO;
        var valorComissao = parseFloat(comissao.VALOR_DA_COMISSAO);

        var row = $('<tr class = "text-center"></tr>');
        var cellNomeVendedorIndicacao = $('<td></td>').text('');
        var cellDataVenda = $('<td></td>').text(dataFormatada);
        var cellNomeVendedor = $('<td></td>').text(nomeVendedor);
        var cellTipo = $('<td></td>').text(tipoComissao);
        var cellValor = $('<td></td>').text(valorComissao);
        var cellTotal = $('<td></td>').text('');
        // var cellPagamento = $('<td></td>').text('');

        row.append(cellNomeVendedorIndicacao)
          .append(cellDataVenda)
          .append(cellNomeVendedor)
          .append(cellTipo)
          .append(cellValor)
          .append(cellTotal)
          // .append(cellPagamento);

        tabela.append(row);
      });

      var cellSomatorio = $('<td></td>').text(somatorioPorGrupo[nomeVendedorIndicacao].toFixed(2));
      cellSomatorio.addClass('font-weight-bold text-success');
      rowGrupo.append(cellSomatorio);

    }
  }
});
