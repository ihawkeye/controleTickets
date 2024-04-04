$(document).ready(function () {
  var grupos = {};
  $('#filtroBtn').click(function () {
    var dataInicio = $('#data_inicio').val();
    var dataFim = $('#data_fim').val();
    carregarComissoes(dataInicio, dataFim);
  });
  // função para carregar os dados de comissões via AJAX
  function carregarComissoes(dataInicio, dataFim) {
    $.ajax({
      url: '/comissoes_in/src/model/comissoes.php',
      type: 'GET',
      data: { data_inicio: dataInicio, data_fim: dataFim }, // Passa as datas como parâmetros
      dataType: 'json',
      success: function (response) {
        console.log(response);
        exibirComissoes(response);
      },
      error: function () {
        console.log('Erro ao carregar comissões.');
      }
    });

  }

  // função para exibir as comissões na tabela
  function exibirComissoes(response) {
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
        var cellPagamento = $('<td></td>').text('');

        row.append(cellNomeVendedorIndicacao)
          .append(cellDataVenda)
          .append(cellNomeVendedor)
          .append(cellTipo)
          .append(cellValor)
          .append(cellTotal)
          .append(cellPagamento);

        tabela.append(row);
      });

      var cellSomatorio = $('<td></td>').text(somatorioPorGrupo[nomeVendedorIndicacao].toFixed(2));
      cellSomatorio.addClass('font-weight-bold text-success');
      rowGrupo.append(cellSomatorio);

      var cellBotaoGrupo = $('<td></td>');
      var botaoGrupo = $('<button>Pagar</button>').addClass('btn btn-success btn-atualizar-pagamento-grupo').data('codigo_vendedor', comissoesGrupo[0].VENDED_INDICACAO);
      cellBotaoGrupo.append(botaoGrupo);
      rowGrupo.append(cellBotaoGrupo);
    }

    $('.btn-atualizar-pagamento-grupo').click(function () {
      var codigoVendedor = $(this).data('codigo_vendedor');
      var dataInicio = $('#data_inicio').val();
      var dataFim = $('#data_fim').val();
      Swal.fire({
        title: 'Deseja confirmar o pagamento?',
        text: "A alteração será definitiva",
        icon: 'warning',
        showCancelButton: false,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmar pagamento'
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire(
            'Pagamento realizado com sucesso!',
            '',
            'success'
          )
          atualizarPagamentoGrupo(codigoVendedor, dataInicio, dataFim);
        }
      });
    });
  }

  // var codigoVendedor = $(this).data('codigo-vendedor');
  // atualizarPagamentoGrupo(codigoVendedor);

  // função para atualizar o pagamento das comissões do grupo
  function atualizarPagamentoGrupo(codigoVendedor, dataInicio, dataFim) {
    $.ajax({
      url: '/comissoes_in/src/controller/atualizar_pagamento.php',
      type: 'POST',
      data: { codigo_vendedor: codigoVendedor, data_inicio: dataInicio, data_fim: dataFim }, // Passa as datas como parâmetros
      success: function (response) {
        console.log(response);
        // Atualiza a tabela de comissões depois da atualização ser concluída
        carregarComissoes(dataInicio, dataFim);
      },
      error: function () {
        console.log('Erro ao atualizar pagamento.');
      }
    });
  }

  // função para carregar as comissões ao carregar a página
  carregarComissoes();
});
