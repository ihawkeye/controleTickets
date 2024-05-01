$(document).ready(function() {
    var arrayTickets = JSON.parse(sessionStorage.getItem('arrayTickets'));

    if(arrayTickets) {
        arrayTickets.forEach(function(ticket, index){
            adicionarLinhaTabela(ticket, index);
        });
    }

    // Adicionando evento de clique aos botões #btn-edit usando delegação de eventos
    $('tbody').on('click', '#btn-edit', function() {
        var index = $(this).closest('tr').data('index'); // Obtendo o índice do ticket na array
        var ticket = arrayTickets[index]; // Obtendo o ticket correspondente
        preencherModal(ticket);
    });

    // Função para adicionar uma nova linha na tabela com os dados do ticket
    function adicionarLinhaTabela(ticket, index) {

        var editButton = $('<button>').addClass('btn').attr('type', 'button').attr({'id': 'btn-edit', "data-bs-toggle": "modal", "data-bs-target": "#modal"}).html('<i class="fa fa-edit"></i>');

        var interactButton = $('<button>').addClass('btn').attr('type', 'button').attr('id', 'btn-interact').html('<i class="fa-solid fa-bolt"></i>');

        var newRow = $('<tr>').addClass('table-row').attr({"data-index": index}); // Adicionando o atributo data-index
        
        newRow.append(
            $('<td>').text(ticket.Serial),
            $('<td>').text(ticket.Cliente),
            $('<td>').text(ticket.Ticket),
            $('<td>').text(ticket.DataOcorrencia),
            $('<td>').text(ticket.DataUltimaInteracao),
            $('<td>').append(editButton),
            $('<td>').append(interactButton)
        );

        $('tbody').append(newRow);
    }

    // função para preencher os campos do modal com os dados do ticket
    function preencherModal(ticket) {
        $('#modalLabel').text('Ticket ' + ticket.Ticket + ' - Cliente ' + ticket.Cliente);
        $('#inputDateUltimaInteracao').val(ticket.DataUltimaInteracao);
        $('#inputDateOcorrencia').val(ticket.DataOcorrencia);
        $('#inputSerial').val(ticket.Serial);
        $('#inputCliente').val(ticket.Cliente);
        $('#inputTecnico').val(ticket.Tecnico);
        $('#inputTicket').val(ticket.Ticket);
        $('#inputTipo').val(ticket.Tipo);
        $('#inputPrioridade').val(ticket.Prioridade);
        $('#inputCategoria').val(ticket.Categoria);
        $('#inputVersao').val(ticket.Versao);
        $('#inputStatus').val(ticket.Status);
        $('#inputUltimoTeste').val(ticket.DataUltimoTeste);
        $('#vinicius').prop('checked', ticket.Vinicius === 'checked');
        $('#inputTextOcorrencia').val(ticket.Ocorrencia);
        $('#inputTextObservacoes').val(ticket.Observacoes);
    }
});
