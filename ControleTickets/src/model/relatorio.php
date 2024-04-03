<?php
require_once '../config/config.php';

class ComissoesFiltro
{
    private $conn;

    public function __construct()
    {
        $db = new Database();
        $this->conn = $db->getConnection();
    }

    public function getComissoesFiltro($dataInicio, $dataFim)
    {
        $query = "SELECT c.*, v.NOMEVENDED, (SELECT NOMEVENDED FROM VENDEDOR WHERE CODVENDED = v.VENDED_INDICACAO) AS NOME_VENDED_INDICACAO, v.QNT_INDICACOES, v.VENDED_INDICACAO
        FROM COMISSOESIN c
        JOIN VENDEDOR v ON c.CODVENDED = v.CODVENDED
        WHERE c.FLAG_PAGAMENTO = 'S' AND c.DATA_PAGAMENTO BETWEEN '$dataInicio' AND '$dataFim'";
        
        $stmt = $this->conn->prepare($query);
        $stmt->execute();

        $comissoesFiltro = array();

        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $comissaoFiltro = array(
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
            $comissoesFiltro[] = $comissaoFiltro;
        }

        return $comissoesFiltro;
    }
}

$dataInicio = $_POST['dataInicio'];
$dataFim = $_POST['dataFim'];

$comissoesFiltro = new ComissoesFiltro();
$response = $comissoesFiltro->getComissoesFiltro($dataInicio, $dataFim);

echo json_encode($response);
