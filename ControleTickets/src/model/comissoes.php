<?php
require_once '../config/config.php';

class Comissoes {
    private $conn;

    public function __construct() {
        $db = new Database();
        $this->conn = $db->getConnection();
    }

    public function getComissoes($dataInicio, $dataFim) {
        $query = "SELECT c.*, v.NOMEVENDED, (SELECT NOMEVENDED FROM VENDEDOR WHERE CODVENDED = v.VENDED_INDICACAO) AS NOME_VENDED_INDICACAO, v.QNT_INDICACOES, v.VENDED_INDICACAO
        FROM COMISSOESIN c
        JOIN VENDEDOR v ON c.CODVENDED = v.CODVENDED
        WHERE (c.FLAG_PAGAMENTO = 'N' OR c.FLAG_PAGAMENTO IS NULL) and v.QNT_INDICACOES >=0
        AND (c.DATA_VENDA BETWEEN '$dataInicio' AND '$dataFim')";
        
        $stmt = $this->conn->prepare($query);
        $stmt->execute();

        $comissoes = array();

        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $comissao = array(
                'CODCOMISSOES' => isset($row['CODCOMISSOES']) ? $row['CODCOMISSOES'] : null,
                'CODVENDED' => isset($row['CODVENDED']) ? $row['CODVENDED'] : null,
                'DATA_VENDA' => isset($row['DATA_VENDA']) ? $row['DATA_VENDA'] : null,
                'DATA_PAGAMENTO' => isset($row['DATA_PAGAMENTO']) ? $row['DATA_PAGAMENTO'] : null,
                'TIPO_COMISSAO' => isset($row['TIPO_COMISSAO']) ? $row['TIPO_COMISSAO'] : null,
                'VALOR_DA_COMISSAO' => isset($row['VALOR_DA_COMISSAO']) ? $row['VALOR_DA_COMISSAO'] : null,
                'NOMEVENDED' => isset($row['NOMEVENDED']) ? $row['NOMEVENDED'] : null,
                'VENDED_INDICACAO' => isset($row['VENDED_INDICACAO']) ? $row['VENDED_INDICACAO'] : null,
                'NOME_VENDED_INDICACAO' => isset($row['NOME_VENDED_INDICACAO']) ? $row['NOME_VENDED_INDICACAO'] : null,
                'QNT_INDICACOES' => isset($row['QNT_INDICACOES']) ? $row['QNT_INDICACOES'] : null
            );

            $comissoes[] = $comissao;
        }

        return $comissoes;
    }
}

$comissoes = new Comissoes();
$dataInicio = $_GET['data_inicio'];
$dataFim = $_GET['data_fim'];
$response = $comissoes->getComissoes($dataInicio, $dataFim);

echo json_encode($response);
