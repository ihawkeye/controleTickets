$(document).ready(function () {
    // Função para atribuir classes de cor com base no status do ticket
    function atribuirCor(status, elemento) {
        switch (status) {
            case 'Em análise':
                elemento.addClass('text-bg-primary');
                break;
            case 'Resolvido':
                elemento.addClass('text-bg-success');
                break;
            case 'Não resolvido':
                elemento.addClass('text-bg-danger');
                break;
            case 'Aguardando VVS':
                elemento.addClass('text-bg-warning');
                break;
            case 'Descartado':
                elemento.addClass('text-bg-secondary');
                break;
            default:
                // Se o status não corresponder a nenhum caso acima, use a cor padrão
                elemento.addClass('text-bg-light');
        }
    }

    // Espera a tabela ser totalmente carregada
    $(document).ajaxComplete(function () {
        // Seleciona cada elemento com a classe statusbadge e atribui a cor apropriada
        $('.statusbadge').each(function () {
            // Obter o texto dentro do elemento <td>
            var status = $(this).text().trim();
            // Chamar a função atribuirCor para atribuir a classe apropriada
            atribuirCor(status, $(this));
        });
    });
});
