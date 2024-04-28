$(document).ready(function(){

    var storedTickets = JSON.parse(sessionStorage.getItem('arrayTickets'));
    
    var arrayTickets = storedTickets ? storedTickets : [];

    console.log(arrayTickets);

    $('#abrirTicketBtn').click(() => { 
        
         var serial = $('#inputSerial').val();
         var cliente = $('#inputCliente').val();
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

         // Obter a data atual
        var dataAtual = new Date();
        var dataFormatada = `${dataAtual.getDate()}/${dataAtual.getMonth() + 1}/${dataAtual.getFullYear()}`;

         // limpando marcação dos campos 
        $('.required').css({"border": "none"});

        // ids dos campos obrigatórios
        var camposObrigatorios = ['inputSerial', 'inputCliente', 'inputTicket', 'inputVersao', 'inputTextOcorrencia'];


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
        // criação do ticket
        var Ticket = {
            Serial: serial,
            Cliente: cliente,
            Tecnico: tecnico,
            Ticket: ticket,
            Tipo: tipo,
            Prioridade: prioridade,
            Categoria: categoria,
            Versao: versao,
            Status: status,
            DataUltimaInteracao: dataFormatada,
            DataOcorrencia: dataFormatada,
            DataUltimoTeste: dateUltimoTeste,
            Vinicius: vinicius,
            Ocorrencia: ocorrencia,
            Observacoes: observacoes
        }

        arrayTickets.push(Ticket);
        console.log(Ticket);
        console.log(arrayTickets);
        alert('Ticket criado com sucesso!');
        
        $('#inputSerial, #inputCliente, #inputTecnico, #inputTicket, #inputTipo, #inputPrioridade, #inputCategoria, #inputVersao, #inputStatus, #inputUltimoTeste, #vinicius, #inputTextOcorrencia, #inputTextObservacoes').val('');

        arrayTickets.forEach(function(Ticket, index){
            console.log(Ticket);
        });

        sessionStorage.setItem('arrayTickets', JSON.stringify(arrayTickets));

    } else {
        // alerta de campo não preenchido
        alert('Por favor, preencha todos os campos destacados.');
    }

    });

});
