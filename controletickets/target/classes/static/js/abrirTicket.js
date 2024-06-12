$(document).ready(function(){
    var base64Strings = [];
    $.ajax({
        url: "/tecnicos",
        type: "GET",
        success: function(response) {
            $("#inputTecnico").empty();
            response.forEach(function(tecnico) {
                $("#inputTecnico").append("<option>" + tecnico.nome + "</option>");
            });
        },
        error: function(xhr, status, error) {
            console.error("Erro ao buscar os técnicos:", error);
        }
    });
    $('#inputImagem').change(function() {
        var files = this.files;
        for (var i = 0; i < files.length; i++) {
            (function(file) {
                var reader = new FileReader();
                reader.onload = function() {
                    var base64String = reader.result.split(',')[1];
                    base64Strings.push(base64String);
                    console.log('Imagem convertida para base64:', base64String);
                    if (base64Strings.length === files.length) {
                        console.log('Todas as imagens convertidas:', base64Strings);
                    }
                };
                reader.readAsDataURL(file);
            })(files[i]);
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

        var dataAtual = new Date();

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
                dataOcorrencia: dataAtual,
                dataUltimaInteracao: dataAtual,
                dataUltimoTeste: dataUltimoTeste,
                vinicius: vinicius,
                ocorrencia: ocorrencia,
                observacao: observacao,
                imagem: base64Strings
            };

            console.log(formData);

            $.ajax({
                url: '/ticket',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function(response) {
                    console.log('Ticket criado com sucesso', response);
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: "Ticket criado com sucesso",
                        showConfirmButton: false,
                        timer: 1500
                    });
                },
                error: function(xhr, status, error) {
                    console.error('Erro ao criar ticket', error, xhr, status);
                    Swal.fire({
                        position: "center",
                        icon: "error",
                        title: "Erro ao criar ticket",
                        showConfirmButton: false,
                        timer: 1500
                    });
                }
            });


            // limpa os campos de input
            $('#inputImagem, #inputSerial, #inputNome, #inputTecnico, #inputTicket, #inputTipo, #inputPrioridade, #inputCategoria, #inputVersao, #inputStatus, #inputUltimoTeste, #vinicius, #inputTextOcorrencia, #inputTextObservacao').val('');

        } else {
            // alerta de campo não preenchido
            Swal.fire({
                position: "center",
                icon: "warning",
                title: "Campos não preenchidos",
                showConfirmButton: false,
                timer: 1500
            });
        }
    });
});