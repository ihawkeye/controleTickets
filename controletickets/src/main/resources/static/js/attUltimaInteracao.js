$(document).ready(function(){

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
                    console.log("Sucesso ao interagir com o ticket:", response);
                    carregarTickets();
                    alert('Interação concluída!');
                },
                error: function(xhr, status, error) {
                    console.error("Erro ao interagir com o ticket:", error);
                }
            });
        });

})