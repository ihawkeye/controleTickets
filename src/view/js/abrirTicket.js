$(document).ready(function(){
    

    var arrayTickets = []
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

         // Validação de campos obrigatórios para preenchimento. 

         if (!serial || !cliente || !ticket || !versao || !ocorrencia) {
            alert('Por favor, preencha todos os campos destacados.');
            return;
        }

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
            DataUltimoTeste: dateUltimoTeste,
            Vinicius: vinicius,
            Ocorrencia: ocorrencia,
            Observacoes: observacoes
        }

        arrayTickets.push(Ticket);
        console.log(Ticket);
        console.log(arrayTickets);
        alert('Ticket criado com sucesso!');

        arrayTickets.forEach(function(Ticket, index){
            console.log(Ticket);
        });

    });

});
