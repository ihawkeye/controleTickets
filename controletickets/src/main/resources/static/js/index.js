$(document).ready(function() {

    function carregarTickets () {
        $.ajax({
            type: "GET",
            url: "/ticket/prioridades",
            contentType: 'application/json',
            success: function (response) {
                $('tbody').empty();
                console.log(response);
                response.forEach(function (ticket, index){
                    adicionarLinhaTabela(ticket);
                    console.log(ticket);
                });
            },
            error: function(xhr, status, error){
                console.error("Erro ao buscar os tickets: ", error);
            }
        });
    }


    function adicionarLinhaTabela(ticket) {
        var editButton = $('<button>').addClass('btn btn-primary').attr({
            'type': 'button',
            'data-bs-toggle': 'modal',
            'data-bs-target': '#modal',
            'id': 'btn-edit'
        }).append($('<i>').addClass('fa fa-edit'));

        var interactButton = $('<button>').addClass('btn').attr({
            'type': 'button',
            'id': 'btn-interact'
        }).append($('<i>').addClass('fa-solid fa-bolt'));


        var newRow = $('<tr>').addClass('table-row').attr('data-id', ticket.id); // Adicionando o atributo data-id
    
        newRow.append(
            $('<td>').text(ticket.cliente.serial),
            $('<td>').text(ticket.cliente.nome),
            $('<td>').text(ticket.numero),
            $('<td>').text(ticket.dataOcorrencia),
            $('<td>').text(ticket.dataUltimaInteracao),
            $('<td>').append(editButton),
            $('<td>').append(interactButton)
        );
    
        $('tbody').append(newRow);
    }

    
    carregarTickets();


    $('tbody').on('click', '#btn-edit', function() {
        var ticketId = $(this).closest('tr').data('id'); // Obtendo o ID do ticket
        // Função para buscar os dados do ticket com o ID especificado e preencher o modal
        buscarTicketExibirModal(ticketId);
    });


    function buscarTicketExibirModal(ticketId) {
        $.ajax({
            url: '/ticket/' + ticketId,
            type: 'GET',
            success: function(ticket) {
                preencherModal(ticket); // Preencher o modal com os dados do ticket
                $('#modal').modal('show'); // Exibir o modal após preencher os dados
            },
            error: function(xhr, status, error) {
                console.error("Erro ao buscar o ticket:", xhr, status, error);
            }
        });
    }


    function preencherModal(ticket) {
        console.log(ticket);
        $('#modalLabel').text('Ticket ' + ticket.numero + ' - Cliente ' + ticket.cliente.nome);
        $('#inputDateUltimaInteracao').val(ticket.dataUltimaInteracao);
        $('#inputDateOcorrencia').val(ticket.dataOcorrencia);
        $('#inputSerial').val(ticket.cliente.serial);
        $('#inputCliente').val(ticket.cliente.nome);
        $('#inputTecnico').val(ticket.tecnico.nome);
        $('#inputTicket').val(ticket.numero);
        $('#inputTipo').val(ticket.tipo);
        $('#inputPrioridade').val(ticket.prioridade);
        $('#inputCategoria').val(ticket.categoria);
        $('#inputVersao').val(ticket.ultimaVersao);
        $('#inputStatus').val(ticket.status);
        $('#inputUltimoTeste').val(ticket.dataUltimoTeste);
        $('#vinicius').prop('checked', ticket.vinicius);
        $('#inputTextOcorrencia').val(ticket.ocorrencia);
        $('#inputTextObservacoes').val(ticket.observacao);

        $('#btnAtualiza').off('click').on('click', function() {
            salvarAlteracoes(ticket.id);
        });
    }

    function salvarAlteracoes(ticketId) {
        var dadosAtualizados = {
            ultimaVersao: $('#inputVersao').val(),
            status: $('#inputStatus').val(),
            dataUltimoTeste: $('#inputUltimoTeste').val(),
            vinicius: $('#vinicius').prop('checked'),
            ocorrencia: $('#inputTextOcorrencia').val(),
            observacao: $('#inputTextObservacoes').val()
        };

        $.ajax({
            url: '/ticket/' + ticketId,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(dadosAtualizados),
            success: function(response) {
                console.log("Sucesso ao atualizar o ticket:", response);
                carregarTickets();
                $('#modal').modal('hide');
                alert('Alterações salvas com sucesso!');
            },
            error: function(xhr, status, error) {
                console.error("Erro ao atualizar o ticket:", error, status, xhr);
            }
        });
    }
    
});