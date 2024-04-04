<?php
require_once '../config/config.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $db = new Database();
    $conn = $db->getConnection();

    try {
        $conn->beginTransaction();
        
        $codigoVendedor = $_POST['codigo_vendedor'];
        $FLAG_PAGAMENTO = 'S';
        $dataInicio = $_POST['data_inicio'];
        $dataFim = $_POST['data_fim'];

        $queryAtualizar = "UPDATE COMISSOESIN 
            SET FLAG_PAGAMENTO = '$FLAG_PAGAMENTO', DATA_PAGAMENTO = CURRENT_DATE 
            WHERE CODVENDED in (SELECT CODVENDED FROM vendedor WHERE vended_indicacao = '$codigoVendedor') 
            AND FLAG_PAGAMENTO = 'N' 
            AND DATA_VENDA BETWEEN '$dataInicio' AND '$dataFim';";

        $stmtAtualizar = $conn->prepare($queryAtualizar);
        $stmtAtualizar->execute();

        $conn->commit();
        echo 'Pagamento atualizado com sucesso';
    } catch (PDOException $e) {
        $conn->rollBack();
        echo 'Erro ao atualizar o pagamento: ' . $e->getMessage();
    }
}
