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
        var nome_cliente = $('#inputNome').val();
        var nome_tecnico = $('#inputTecnico').val();
        var numero = $('#inputTicket').val();
        var tipo = $('#inputTipo').val();
        var prioridade = $('#inputPrioridade').val();
        var categoria = $('#inputCategoria').val();
        var ultimaVersao = $('#inputVersao').val();
        var status =  $('#inputStatus').val();
        var dataUltimoTeste = $('#inputUltimoTeste').val();
        var vinicius = $('#vinicius').val();
        var ocorrencia = $('#inputTextOcorrencia').val();
        var observacao = $('#inputTextObservacao').val();

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

        // na esquerda: nome do campo no BD
        var formData = {
            cliente: {
                serial: serial,
                nome: nome_cliente
            },
            tecnico: {
                nome: nome_tecnico
            },
            numero: numero,
            tipo: tipo,
            prioridade: prioridade,
            categoria: categoria,
            ultimaVersao: ultimaVersao,
            status: status,
            dataUltimoTeste: dataUltimoTeste,
            vinicius: vinicius,
            ocorrencia: ocorrencia,
            observacao: observacao
        };

        console.log(formData);

        $.ajax({
            url: '/ticket',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log('Ticket criado com sucesso', response);
            },
            error: function(xhr, status, error) {
                console.error('Erro ao criar ticket', error)
            }
        });

        alert('Ticket criado com sucesso!');

        // limpa os campos de input
        $('#inputSerial, #inputNome, #inputTecnico, #inputTicket, #inputTipo, #inputPrioridade, #inputCategoria, #inputVersao, #inputStatus, #inputUltimoTeste, #vinicius, #inputTextOcorrencia, #inputTextObservacao').val('');

    } else {
        // alerta de campo não preenchido
        alert('Por favor, preencha todos os campos destacados.');
    }

    });

});
