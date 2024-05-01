$(document).ready(function(){

    $.ajax({
            url: "/tecnicos",
            type: "GET",
            success: function(response) {
                // limpa o select pra não dar problema ao renderizar
                $("#inputTecnico").empty();
                // add os nomes dos técnicos ao select
                response.forEach(function(tecnico) {
                    $("#inputTecnico").append("<option>" + tecnico.nome + "</option>");
                });
            },
            error: function(xhr, status, error) {
                console.error("Erro ao buscar os técnicos:", error);
            }
        });

    $('#abrirTicketBtn').click(() => {

         var serial = $('#inputSerial').val();
         var nome = $('#inputNome').val();
         var tecnico = $('#inputTecnico').val();
         var ticket = $('#inputTicket').val();
         var tipo = $('#inputTipo').val();
         var prioridade = $('#inputPrioridade').val();
         var categoria = $('#inputCategoria').val();
         var versao = $('#inputVersao').val();
         var status =  $('#inputStatus').val();
         var dateUltimoTeste = $('#inputUltimoTeste').val();
         var vinicius = $('#vinicius').val();
         var ocorrencia = $('#inputTextOcorrencia').val();
         var observacoes = $('#inputTextObservacoes').val();

         // pega a data atual (teste)
        var dataAtual = new Date();
        var dataFormatada = `${dataAtual.getDate()}/${dataAtual.getMonth() + 1}/${dataAtual.getFullYear()}`;

         // limpando marcação dos campos 
        $('.required').css({"border": "none"});

        // ids dos campos obrigatórios
        var camposObrigatorios = ['inputSerial', 'inputNome', 'inputTicket', 'inputVersao', 'inputTextOcorrencia'];


         // verifica e destaca os campos não preenchidos
        var camposFaltando = [];
        camposObrigatorios.forEach(function(id) {
            var valor = $('#' + id).val();
            if (!valor) {
                $('#' + id).css({"border": "2px solid red"});
                camposFaltando.push(id);
            }
        });

       if (camposFaltando.length === 0) {

        // Criando objeto Cliente para criar a entidade cliente.

        var clienteData = {
            serial: serial,
            nome: nome
        };

        $.ajax({
            url: '/cliente',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(clienteData),
            success: function(response) {
                console.log('Cliente: ', response);
                // pensar em como retornar o id do cliente necessário para criar o ticket.
            },
            error: function(xhr, status, error) {
                console.error('Erro ao criar o cliente:', error);
            }
        });

        alert('Ticket criado com sucesso!');

        // limpa os campos de input
        $('#inputSerial, #inputNome, #inputTecnico, #inputTicket, #inputTipo, #inputPrioridade, #inputCategoria, #inputVersao, #inputStatus, #inputUltimoTeste, #vinicius, #inputTextOcorrencia, #inputTextObservacoes').val('');


    } else {
        // alerta de campo não preenchido
        alert('Por favor, preencha todos os campos destacados.');
    }

    });

});
