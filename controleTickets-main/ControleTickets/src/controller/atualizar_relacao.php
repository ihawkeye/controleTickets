<?php
require_once '../config/config.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $atualizacoes = json_decode($_POST['atualizacoes'], true);

    $db = new Database();
    $conn = $db->getConnection();

    try {
        $conn->beginTransaction();

        foreach ($atualizacoes as $atualizacao) {
            $CODVENDED = $atualizacao['CODVENDED'];
            $VENDED_INDICACAO = $atualizacao['VENDED_INDICACAO'];
        
            if ($VENDED_INDICACAO != '' && $VENDED_INDICACAO != 'xxx') {
                // atualizar a relação com o código do vendedor e o nome do vendedor
                $queryAtualizar = "UPDATE vendedor SET VENDED_INDICACAO = '".$VENDED_INDICACAO."' WHERE CODVENDED = '". $CODVENDED."'";
                $stmtAtualizar = $conn->prepare($queryAtualizar);
                $stmtAtualizar->execute();
            } else if ($VENDED_INDICACAO == 'xxx') {
                $queryAtualizar = "UPDATE vendedor SET QNT_INDICACOES = -2 WHERE CODVENDED = '". $CODVENDED."'"; // xxx = Relação sem vendedor
                $stmtAtualizar = $conn->prepare($queryAtualizar);
                $stmtAtualizar->execute();
            }
        }

        $conn->commit();
        echo 'Relações atualizadas com sucesso';
    } catch (PDOException $e) {
        $conn->rollBack();
        echo 'Erro ao atualizar as relações: ' . $e->getMessage();
    }
}
?>
