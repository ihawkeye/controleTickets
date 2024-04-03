$(document).ready(function() {
    carregarContadores();
  
    $('#salvarAlteracoesBtn').click(function() {
      atualizarRelacoes();
    });
  
    function carregarContadores() {
      $.ajax({
        url: '/comissoes_in/src/model/contadores.php',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
          exibirContadores(response);
        },
        error: function(xhr, status, error) {
          console.log(xhr.responseText);
        }
      });
    }
  
    function exibirContadores(contadores) {
      var tabela = $('#vendedoresTable tbody');
      tabela.empty();
  
      $.each(contadores, function(index, contador) {
        var linha = $('<tr class = "text-center"></tr>');
        linha.append('<td>' + contador.CODVENDED + '</td>');
        linha.append('<td>' + contador.NOMEVENDED + '</td>');
        // linha.append('<td>' + contador.QNT_INDICACOES + '</td>');
        linha.append('<td><select class="form-select relacaoSelect" data-id="' + contador.CODVENDED + '"></select></td>');
        tabela.append(linha);
      });
  
      carregarOpcoesRelacao();
    }
  
    function carregarOpcoesRelacao() {
      $.ajax({
        url: '/comissoes_in/src/model/vendedores.php',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
          exibirOpcoesRelacao(response);
        },
        error: function(xhr, status, error) {
          console.log(xhr.responseText);
        }
      });
    }
  
    function exibirOpcoesRelacao(vendedores) {
      $('.relacaoSelect').each(function() {
        var select = $(this);
        var CODVENDED = select.data('id');
        select.empty();
        select.append('<option value=""> - </option>');
        select.append('<option value="xxx">SEM VENDEDOR VINCULADO</option>'); // xxx = Relação sem vendedor
        $.each(vendedores, function(index, vendedor) {
          select.append('<option value="' + vendedor.CODVENDED + '">' + vendedor.NOMEVENDED + '</option>');
        });
        select.val('');
      });
    }
  
    function atualizarRelacoes() {
      var atualizacoes = [];
  
      $('.relacaoSelect').each(function() {
        var select = $(this);
        var CODVENDED = select.data('id');
        var VENDED_INDICACAO = select.val();
  
        var atualizacao = {
          CODVENDED: CODVENDED,
          VENDED_INDICACAO: VENDED_INDICACAO
        };
  
        atualizacoes.push(atualizacao);
      });
  
      $.ajax({
        url: '/comissoes_in/src/controller/atualizar_relacao.php',
        type: 'POST',
        data: {
          atualizacoes: JSON.stringify(atualizacoes)
        },
        success: function(response) {
          console.log(response);
          carregarContadores();
        },
        error: function(xhr, status, error) {
          console.log(xhr.responseText);
        }
      });
    }
  });




  