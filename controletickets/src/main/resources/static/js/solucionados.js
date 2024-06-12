$(document).ready(function() {
    var todosOsTickets = [];

    function carregarTickets () {
        $.ajax({
            type: "GET",
            url: "/ticket/solucionado",
            contentType: 'application/json',
            success: function (response) {
                $('tbody').empty();
                todosOsTickets = response;
                response.forEach(function (ticket, index){
                    adicionarLinhaTabela(ticket);
                });
            },
            error: function(xhr, status, error){
                console.error("Erro ao buscar os tickets: ", error);
            }
        });
    }

    function converterData(data) {
        var partesDaData = data.split('-');
        return partesDaData[2] + '/' + partesDaData[1] + '/' + partesDaData[0];
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
            $('<td>').text(ticket.tecnico.nome),
            $('<td>').append(
                $('<span>').text(ticket.status).addClass('statusbadge')
            ),
            $('<td>').text(converterData(ticket.dataOcorrencia)),
            $('<td>').text(converterData(ticket.dataUltimaInteracao)),
            $('<td>').append(editButton),
            $('<td>').append(interactButton)
        );

        $('tbody').append(newRow);
    }

    function filtrar() {
        var filtro = $("#filtro").val();
        var filtroInput = $("#filtroInput").val().toLowerCase();

        if (!filtroInput) {
            // Se o campo de input do filtro estiver vazio ele vai mostrar tods
            $('tbody').empty(); // função para limpar a tabela
            todosOsTickets.forEach(function(ticket) {
                adicionarLinhaTabela(ticket);
            });
            return;
        }

        var ticketsFiltrados = todosOsTickets.filter(function(ticket) {
            switch (filtro) {
                case "ticket":
                    return ticket.numero && ticket.numero.toLowerCase().includes(filtroInput);
                case "serial":
                    return ticket.cliente.serial && ticket.cliente.serial.toLowerCase().includes(filtroInput);
                case "nome":
                    return ticket.cliente.nome && ticket.cliente.nome.toLowerCase().includes(filtroInput);
                default:
                    return false;
            }
        });

        $('tbody').empty();
        ticketsFiltrados.forEach(function(ticket) {
            adicionarLinhaTabela(ticket);
        });
    }

    $('#btn-filtro').click(function() {
        filtrar();
    });


    carregarTickets();

    $('tbody').on('click', '#btn-interact', function() {
        var ticketId = $(this).closest('tr').data('id');
        console.log(ticketId);
        var dataAtual = new Date();

        $.ajax({
            url: '/ticket/' + ticketId + '/interact',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({ dataAtual: dataAtual.toISOString() }),
            success: function(response) {
                carregarTickets();
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Interação concluída!",
                    showConfirmButton: false,
                    timer: 2500
                });
            },
            error: function(xhr, status, error) {
                Swal.fire({
                    position: "center",
                    icon: "error",
                    title: "Erro ao interagir com o ticket:",
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        });
    });

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
        $('#modalLabel').text('Ticket ' + ticket.numero);// + ' - Cliente ' + ticket.cliente.nome);
        $('#inputDateUltimaInteracao').val(converterData(ticket.dataUltimaInteracao));
        $('#inputDateOcorrencia').val(converterData(ticket.dataOcorrencia));
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
        $('#imgTicket').attr('src', 'data:image/png;base64,' + ticket.imagem);

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
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Alterações salvas com sucesso!",
                    showConfirmButton: false,
                    timer: 2500
                });
            },
            error: function(xhr, status, error) {
                Swal.fire({
                    position: "center",
                    icon: "error",
                    title: "Erro ao atualizar o ticket",
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        });
    }

});
